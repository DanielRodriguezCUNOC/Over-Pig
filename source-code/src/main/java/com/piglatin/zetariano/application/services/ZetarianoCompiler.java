package com.piglatin.zetariano.application.services;

import com.piglatin.common.application.dto.*;
import com.piglatin.common.application.ports.input.CompilerUseCase;
import com.piglatin.common.infrastructure.codegen.C3DContext;
import com.piglatin.common.infrastructure.codegen.C3DToCConverter;
import com.piglatin.zetariano.domain.ast.principal.NodeProgram;
import com.piglatin.zetariano.domain.ast.statements.NodeClassDeclaration;
import com.piglatin.zetariano.domain.ast.visitor.ZetarianoASTBuilder;
import com.piglatin.zetariano.domain.semantic.ZetarianoSemanticAnalyzer;
import com.piglatin.zetariano.domain.semantic.SemanticContext;
import com.piglatin.zetariano.domain.symboltable.ZetarianoSymbolTable;
import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;
import com.piglatin.zetariano.infrastructure.codegen.c3d.ClassLayout;
import com.piglatin.zetariano.infrastructure.codegen.c3d.ZetarianoC3DVisitor;
import com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoLexer;
import com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
        ClassLayout classLayout = new ClassLayout();

        //* Load AST of sibling classes
        List<NodeProgram> siblingPrograms = new ArrayList<>();
        if (request.getProjectDirectory() != null) {
            siblingPrograms = loadSiblingClasses(request.getProjectDirectory(), fileName);
        }

        List<NodeProgram> allPrograms = new ArrayList<>(siblingPrograms);
        if (ast != null) allPrograms.add(ast);

        //? Pre register of classes and layouts
        for (NodeProgram program: allPrograms){
            for (NodeClassDeclaration cls: getClassesFromProgram(program)){
                typeTable.registerClass(cls.getName(), cls.getSuperClass());
                classLayout.registerClass(cls);
            }
        }

        //? Semantic analysis of sibling classes
        for (NodeProgram siblingProgram: siblingPrograms){
            try {
                ZetarianoSemanticAnalyzer siblingAnalyzer = new ZetarianoSemanticAnalyzer(fileName);
                siblingAnalyzer.analyze(siblingProgram, typeTable, symbolTable);
            } catch (Exception e) {
                System.err.println("[ZetarianoCompiler] Error analizando clase hermana: " + e.getMessage());
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

        //? C3D Generation and C Code Generation
        C3DContext c3dContext = new C3DContext();
        ZetarianoC3DVisitor c3dVisitor = new ZetarianoC3DVisitor(c3dContext, classLayout, symbolTable, typeTable);

        //* Generte c3d FOR ALL SIBLING CLASSES
        for (NodeProgram siblingProgram: siblingPrograms){
            c3dVisitor.generate(siblingProgram);
        }

        //* Generate c3d for the objetive file
        if (ast != null){
            List<NodeClassDeclaration> astClasses = getClassesFromProgram(ast);
            if (!astClasses.isEmpty()) c3dVisitor.generateEntryPoint(astClasses.get(0));
            c3dVisitor.generate(ast);

        }

        String c3dCode = dumpC3D(c3dContext);
        String cCode = C3DToCConverter.convertToC(c3dContext);

        GeneretedCodeDTO generatedCode = new GeneretedCodeDTO();
        return new CompileResponseDTO(true, errors, generatedCode, null, System.currentTimeMillis() - startTime);    }

    private String dumpC3D(C3DContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (var q : ctx.getQuadruples()) {
            sb.append(q.toString()).append("\n");
        }
        return sb.toString();
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