package com.piglatin.zetariano.application.services;

import com.piglatin.common.application.dto.*;
import com.piglatin.common.application.ports.input.CompilerUseCase;
import com.piglatin.common.infrastructure.codegen.ir.C3DContext;
import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.riscv.C3DToRiscVConverter;
import com.piglatin.zetariano.domain.ast.principal.NodeProgram;
import com.piglatin.zetariano.domain.ast.statements.NodeClassDeclaration;
import com.piglatin.zetariano.domain.ast.visitor.ZetarianoASTBuilder;
import com.piglatin.zetariano.domain.semantic.ZetarianoSemanticAnalyzer;
import com.piglatin.zetariano.domain.semantic.SemanticContext;
import com.piglatin.zetariano.domain.symboltable.ZetarianoSymbolTable;
import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;
import com.piglatin.zetariano.infrastructure.codegen.c3d.ZetarianoC3DVisitor;
import com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoLexer;
import com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZetarianoCompiler implements CompilerUseCase {

    @Override
    public CompileResponseDTO compile(CompileRequestDTO request) {
        long startTime = System.currentTimeMillis();
        List<CompilationErrorDTO> errors = new ArrayList<>();

        String fileName = request.getFileName();
        CharStream input = CharStreams.fromString(request.getSourceCode() != null ? request.getSourceCode() : "");

        //* Lexical analysis
        ZetarianoLexer lexer = new ZetarianoLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                errors.add(new CompilationErrorDTO(CompilationStage.LEXICAL_ANALYSIS, "Lexical Error: " + msg, line, charPositionInLine, fileName));
            }
        });

        //* Syntactic analysis
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZetarianoParser parser = new ZetarianoParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                errors.add(new CompilationErrorDTO(CompilationStage.SYNTACTIC_ANALYSIS, "Syntax Error: " + msg, line, charPositionInLine, fileName));
            }
        });

        ParseTree tree;
        try {
            tree = parser.compilationUnit();
        } catch (Exception e) {
            errors.add(new CompilationErrorDTO(CompilationStage.SYNTACTIC_ANALYSIS, "Parsing Error: " + e.getMessage(), 0, 0, fileName));
            return new CompileResponseDTO(false, errors, null, null, System.currentTimeMillis() - startTime);
        }

        if (!errors.isEmpty()) {
            return new CompileResponseDTO(false, errors, null, null, System.currentTimeMillis() - startTime);
        }

        //* Build AST
        ZetarianoASTBuilder astBuilder = new ZetarianoASTBuilder();
        NodeProgram ast = (NodeProgram) astBuilder.visit(tree);

        ZetarianoTypeTable typeTable = new ZetarianoTypeTable();
        ZetarianoSymbolTable symbolTable = new ZetarianoSymbolTable();

        //* Load AST of sibling classes
        List<NodeProgram> siblingPrograms = new ArrayList<>();
        if (request.getProjectDirectory() != null) {
            siblingPrograms = loadSiblingClasses(request.getProjectDirectory(), fileName);
        }

        List<NodeProgram> allPrograms = new ArrayList<>(siblingPrograms);
        if (ast != null) allPrograms.add(ast);

        //? Pre register of classes
        for (NodeProgram program: allPrograms){
            for (NodeClassDeclaration cls: getClassesFromProgram(program)){
                typeTable.registerClass(cls.getName(), cls.getSuperClass());
            }
        }

        //? Semantic analysis of sibling classes
        for (NodeProgram siblingProgram: siblingPrograms){
            // Z-10: use sibling fileName for error attribution (stored in NodeProgram label via listFiles order)
            // We cannot recover original filename here, so use a helper that passes it from loadSiblingClasses
            try {
                ZetarianoSemanticAnalyzer siblingAnalyzer = new ZetarianoSemanticAnalyzer(fileName);
                SemanticContext siblingCtx = siblingAnalyzer.analyze(siblingProgram, typeTable, symbolTable);
                if (siblingCtx.getErrorReporter() != null && siblingCtx.getErrorReporter().hasErrors()) {
                    errors.addAll(siblingCtx.getErrorReporter().getErrors()); // Z-10: propagate, not silence
                }
            } catch (Exception e) {
                errors.add(new CompilationErrorDTO(CompilationStage.SEMANTIC_ANALYSIS,
                        "[Sibling] Exception: " + e.getMessage(), 0, 0, fileName));
            }
        }

        //? Semantic analysis of the main program
        ZetarianoSemanticAnalyzer semanticAnalyzer = new ZetarianoSemanticAnalyzer(fileName);
        SemanticContext context = semanticAnalyzer.analyze(ast, typeTable, symbolTable);

        if (context.getErrorReporter() != null && context.getErrorReporter().hasErrors()) {
            errors.addAll(context.getErrorReporter().getErrors());
        }

        if (!errors.isEmpty()) {
            return new CompileResponseDTO(false, errors, null, null, System.currentTimeMillis() - startTime);
        }

        // Compile (solo semántica): no se genera C3D ni RISC-V
        if (request.getMode() == CompilationMode.VALIDATE_ONLY) {
            return new CompileResponseDTO(true, errors, null, null, System.currentTimeMillis() - startTime);
        }

        //? C3D Generation and RISC-V Generation
        C3DContext c3dContext = new C3DContext();
        ZetarianoC3DVisitor c3dVisitor = new ZetarianoC3DVisitor(c3dContext, symbolTable, typeTable);

        //* Generte c3d for sibling classes.
        for (NodeProgram siblingProgram: siblingPrograms){
            c3dVisitor.generate(siblingProgram);
        }

        //* Generate c3d for the objetive file
        if (ast != null){
            List<NodeClassDeclaration> astClasses = getClassesFromProgram(ast);
            if (!astClasses.isEmpty()) c3dVisitor.generateEntryPoint(astClasses.get(0));
            c3dVisitor.generate(ast);

        }

        //* C3D -> RISC-V. Un solo visitor atendió a los hermanos y a la clase principal,
        //* así que strings (str_N) y vtables salen de él.
        List<Instruction> instructions = c3dContext.getInstructions();
        Map<String, String> strings = c3dVisitor.getStringLiterals();
        Map<String, List<String>> vtables = c3dVisitor.getVtables();

        C3DToRiscVConverter riscv = new C3DToRiscVConverter(instructions, strings, vtables);
        String asm = riscv.convert();

        String c3dCode = dumpC3D(c3dContext);

        GeneretedCodeDTO generatedCode = new GeneretedCodeDTO();
        generatedCode.setC3dCode(c3dCode);
        generatedCode.setAsmCode(asm);
        generatedCode.setEntryPoint("main");
        return new CompileResponseDTO(true, errors, generatedCode, null, System.currentTimeMillis() - startTime);
    }

    private String dumpC3D(C3DContext ctx) {
        return ctx.getInstructions().stream()
                .map(Instruction::toC3D)
                .collect(Collectors.joining("\n"));
    }

    private List<NodeClassDeclaration> getClassesFromProgram(NodeProgram program) {
        if (program == null || program.getClasses() == null) {
            return List.of();
        }
        return program.getClasses();
    }

    private List<NodeProgram> loadSiblingClasses(String projectDirectory, String currentFileName) {
        List<NodeProgram> siblings = new ArrayList<>();
        File dir = new File(projectDirectory);
        if (!dir.exists() || !dir.isDirectory()) return siblings;

        File[] files = dir.listFiles((d, name) -> name.endsWith(".z") && !name.equals(currentFileName));
        if (files == null) return siblings;

        for (File file : files) {
            try {
                String siblingCode = Files.readString(file.toPath());
                CharStream input = CharStreams.fromString(siblingCode);
                ZetarianoLexer lexer = new ZetarianoLexer(input);
                lexer.removeErrorListeners();
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                ZetarianoParser parser = new ZetarianoParser(tokens);
                parser.removeErrorListeners();

                ParseTree tree = parser.compilationUnit();
                ZetarianoASTBuilder builder = new ZetarianoASTBuilder();
                NodeProgram program = (NodeProgram) builder.visit(tree);

                if (program != null && !getClassesFromProgram(program).isEmpty()) {
                    siblings.add(program);
                }
            } catch (Exception e) {
                System.err.println("[loadSiblingClasses] Omitiendo " + file.getName()
                        + " por error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        return siblings;
    }
}