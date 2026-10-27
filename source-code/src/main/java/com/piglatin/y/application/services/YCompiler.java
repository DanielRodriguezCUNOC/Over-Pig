package com.piglatin.y.application.services;

import com.piglatin.common.application.dto.*;
import com.piglatin.common.application.ports.input.CompilerUseCase;
import com.piglatin.common.infrastructure.codegen.ir.C3DContext;
import com.piglatin.common.infrastructure.codegen.ir.C3DEmitter;
import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.instructions.calls.ReturnVoid;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.Label;
import com.piglatin.common.infrastructure.codegen.riscv.C3DToRiscVConverter;
import com.piglatin.y.domain.ast.principal.NodeProgram;
import com.piglatin.y.domain.ast.visitor.YASTBuilder;
import com.piglatin.y.domain.semantic.YSemanticAnalyzer;
import com.piglatin.y.domain.semantic.SemanticContext;
import com.piglatin.y.domain.symboltable.FunctionSymbol;
import com.piglatin.y.infrastructure.codegen.c3d.StructLayout;
import com.piglatin.y.infrastructure.codegen.c3d.YC3DVisitor;
import com.piglatin.y.infrastructure.parser.generated.YLexer;
import com.piglatin.y.infrastructure.parser.generated.YParser;
import com.piglatin.zetariano.domain.ast.visitor.ZetarianoASTBuilder;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class YCompiler implements CompilerUseCase {

    @Override
    public CompileResponseDTO compile(CompileRequestDTO request) {
        long startTime = System.currentTimeMillis();
        List<CompilationErrorDTO> errors = new ArrayList<>();

        CharStream input = CharStreams.fromString(request.getSourceCode() != null ? request.getSourceCode() : "");
        YLexer lexer = new YLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                errors.add(new CompilationErrorDTO(CompilationStage.LEXICAL_ANALYSIS,
                        "Lexical Error: " + msg, line, charPositionInLine, request.getFileName()));
            }
        });

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        YParser parser = new YParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                errors.add(new CompilationErrorDTO(CompilationStage.SYNTACTIC_ANALYSIS,
                        "Syntax Error: " + msg, line, charPositionInLine, request.getFileName()));
            }
        });

        ParseTree parseTree;
        try {
            parseTree = parser.program();
        } catch (Exception e) {
            errors.add(new CompilationErrorDTO(CompilationStage.SYNTACTIC_ANALYSIS,
                    "Fatal Error: " + e.getMessage(), 0, 0, request.getFileName()));
            return new CompileResponseDTO(false, errors, null, null,
                    System.currentTimeMillis() - startTime);
        }

        if (!errors.isEmpty()) {
            return new CompileResponseDTO(false, errors, null, null,
                    System.currentTimeMillis() - startTime);
        }

        YASTBuilder astBuilder = new YASTBuilder();
        NodeProgram ast = (NodeProgram) astBuilder.visit(parseTree);

        YSemanticAnalyzer semanticAnalyzer = new YSemanticAnalyzer(request.getFileName());
        SemanticContext context = semanticAnalyzer.analyze(ast);

        if (context.getErrorReporter() != null && context.getErrorReporter().hasErrors()) {
            for (CustomErrorDTO err : context.getErrorReporter().getErrors()) {
                errors.add(new CompilationErrorDTO(CompilationStage.SEMANTIC_ANALYSIS,
                        err.message(), err.line(), err.column(), request.getFileName()));
            }
        }

        if (!errors.isEmpty()) {
            return new CompileResponseDTO(false, errors, null, null,
                    System.currentTimeMillis() - startTime);
        }

        if (request.getMode() == CompilationMode.VALIDATE_ONLY) {
            return new CompileResponseDTO(true, errors, null, null,
                    System.currentTimeMillis() - startTime);
        }

        // --- C3D ---
        C3DContext c3dContext = new C3DContext();
        StructLayout structLayout = new StructLayout();

        // main: call main_0; ret
        c3dContext.emit(new Label("main"));
        FunctionSymbol mainFn = context.getSymbolTable().lookupFunction("main", 0);
        if (mainFn != null) {
            new C3DEmitter(c3dContext).emitCallVoid("main_0", 0, 0);
        }
        c3dContext.emit(new ReturnVoid());

        YC3DVisitor yVisitor = new YC3DVisitor(
                c3dContext,
                structLayout,
                context.getSymbolTable(),
                context.getTypeTable());
        ast.accept(yVisitor);

        if (request.getProjectDirectory() != null) {
            List<com.piglatin.zetariano.domain.ast.principal.NodeProgram> zSiblings =
                    loadZSiblingClasses(request.getProjectDirectory(), request.getCurrentFileName());
            for (com.piglatin.zetariano.domain.ast.principal.NodeProgram z : zSiblings) {
                try {
                    if (z == null || z.getClasses() == null || z.getClasses().isEmpty()) continue;
                    System.err.println("[YCompiler] Hermano .z detectado: "
                            + z.getClasses().get(0).getName()
                            + " (no emitido, requiere tablas de Zetariano)");
                } catch (Exception e) {
                    System.err.println("[YCompiler] Error con hermano .z: "
                            + e.getClass().getSimpleName() + ": " + e.getMessage());
                }
            }
        }

        // C3D -> RISC-V
        List<Instruction> instructions = c3dContext.getInstructions();
        Map<String, String> strings = Map.of();
        Map<String, List<String>> vtables = Map.of();

        C3DToRiscVConverter riscv = new C3DToRiscVConverter(instructions, strings, vtables);
        String asm = riscv.convert();

        String c3dCode = instructions.stream()
                .map(Instruction::toC3D)
                .collect(Collectors.joining("\n"));

        GeneretedCodeDTO generatedCode = new GeneretedCodeDTO();
        generatedCode.setC3dCode(c3dCode);
        generatedCode.setAsmCode(asm);
        generatedCode.setEntryPoint("main");
        return new CompileResponseDTO(true, errors, generatedCode, null,
                System.currentTimeMillis() - startTime);
    }

    private List<com.piglatin.zetariano.domain.ast.principal.NodeProgram> loadZSiblingClasses(
            String projectDirectory, String currentFileName) {

        List<com.piglatin.zetariano.domain.ast.principal.NodeProgram> siblings = new ArrayList<>();
        File dir = new File(projectDirectory);
        if (!dir.exists() || !dir.isDirectory()) return siblings;

        File[] files = dir.listFiles((d, name) ->
                name.endsWith(".z") && !name.equals(currentFileName));
        if (files == null) return siblings;

        for (File file : files) {
            try {
                String code = Files.readString(file.toPath());
                org.antlr.v4.runtime.CharStream in =
                        org.antlr.v4.runtime.CharStreams.fromString(code);

                com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoLexer lexer =
                        new com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoLexer(in);
                lexer.removeErrorListeners();

                org.antlr.v4.runtime.CommonTokenStream tok =
                        new org.antlr.v4.runtime.CommonTokenStream(lexer);

                com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParser parser =
                        new com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParser(tok);
                parser.removeErrorListeners();

                org.antlr.v4.runtime.tree.ParseTree tree = parser.compilationUnit();
                ZetarianoASTBuilder builder =
                        new ZetarianoASTBuilder();

                Object built = builder.visit(tree);
                if (built instanceof com.piglatin.zetariano.domain.ast.principal.NodeProgram program) {
                    if (program.getClasses() != null && !program.getClasses().isEmpty()) {
                        siblings.add(program);
                    }
                }
            } catch (Exception e) {
                System.err.println("[YCompiler/loadZSiblingClasses] Skipping " + file.getName()
                        + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        return siblings;
    }
}