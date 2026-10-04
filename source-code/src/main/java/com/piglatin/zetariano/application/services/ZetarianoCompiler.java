package com.piglatin.zetariano.application.services;

import com.piglatin.common.application.dto.*;
import com.piglatin.common.application.ports.input.CompilerUseCase;
import com.piglatin.common.infrastructure.codegen.C3DContext;
import com.piglatin.common.infrastructure.codegen.C3DToCConverter;
import com.piglatin.common.infrastructure.codegen.Quadruple;
import com.piglatin.zetariano.domain.ast.principal.NodeProgram;
import com.piglatin.zetariano.domain.ast.statements.NodeClassDeclaration;
import com.piglatin.zetariano.domain.ast.visitor.ASTBuilder;
import com.piglatin.zetariano.domain.semantic.SemanticAnalyzer;
import com.piglatin.zetariano.domain.semantic.SemanticContext;
import com.piglatin.zetariano.domain.symboltable.SymbolTable;
import com.piglatin.zetariano.domain.types.TypeTable;
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

        CharStream input = CharStreams.fromString(request.getSourceCode() != null ? request.getSourceCode() : "");
        ZetarianoLexer lexer = new ZetarianoLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                errors.add(new CompilationErrorDTO(CompilationStage.LEXICAL_ANALYSIS, "Lexical Error: " + msg, line, charPositionInLine, request.getFileName()));
            }
        });

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZetarianoParser parser = new ZetarianoParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                errors.add(new CompilationErrorDTO(CompilationStage.SYNTACTIC_ANALYSIS, "Syntax Error: " + msg, line, charPositionInLine, request.getFileName()));
            }
        });

        ParseTree parseTree;
        try {
            parseTree = parser.program();
        } catch (Exception e) {
            errors.add(new CompilationErrorDTO(CompilationStage.SYNTACTIC_ANALYSIS, "Fatal Error: " + e.getMessage(), 0, 0, request.getFileName()));
            return new CompileResponseDTO(false, errors, null, null, System.currentTimeMillis() - startTime);
        }

        if (!errors.isEmpty()) {
            return new CompileResponseDTO(false, errors, null, null, System.currentTimeMillis() - startTime);
        }

        ASTBuilder astBuilder = new ASTBuilder();
        NodeProgram ast = (NodeProgram) astBuilder.visit(parseTree);

        TypeTable typeTable = new TypeTable();
        SymbolTable symbolTable = new SymbolTable();
        ClassLayout classLayout = new ClassLayout();

        List<NodeProgram> siblingPrograms = new ArrayList<>();
        if (request.getProjectDirectory() != null) {
            siblingPrograms = loadSiblingClasses(request.getProjectDirectory(), request.getCurrentFileName());
        }

        for (NodeProgram siblingProgram : siblingPrograms) {
            try {
                if (siblingProgram == null || siblingProgram.getClassDeclaration() == null) continue;
                typeTable.registerClass(siblingProgram.getClassDeclaration().getName());
                SemanticAnalyzer siblingAnalyzer = new SemanticAnalyzer(request.getFileName());
                SemanticContext siblingContext = siblingAnalyzer.analyze(siblingProgram, typeTable, symbolTable);
                classLayout.registerClass(siblingProgram.getClassDeclaration());
                if (siblingContext.hasErrors()) {
                    System.err.println("[ZetarianoCompiler] Clase hermana '"
                            + siblingProgram.getClassDeclaration().getName() + "' con errores semánticos, se continúa.");
                }
            } catch (Exception e) {
                System.err.println("[ZetarianoCompiler] Error procesando clase hermana: "
                        + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }

        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(request.getFileName());
        SemanticContext context = semanticAnalyzer.analyze(ast, typeTable, symbolTable);

        if (context.getErrorReporter() != null && context.getErrorReporter().hasErrors()) {
            for (CompilationErrorDTO err : context.getErrorReporter().getErrors()) {
                errors.add(err);
            }
        }

        if (!errors.isEmpty()) {
            return new CompileResponseDTO(false, errors, null, null, System.currentTimeMillis() - startTime);
        }

        if (ast.getClassDeclaration() != null) {
            classLayout.registerClass(ast.getClassDeclaration());
        }

        // --- Generación de C3D---
        C3DContext c3dContext = new C3DContext();
        ZetarianoC3DVisitor visitor = new ZetarianoC3DVisitor(c3dContext, classLayout, symbolTable, typeTable);


        if (ast.getClassDeclaration() != null) {
            visitor.generateEntryPoint(ast.getClassDeclaration());
        }

        for (NodeProgram siblingProgram : siblingPrograms) {
            if (siblingProgram != null && siblingProgram.getClassDeclaration() != null) {
                visitor.generate(siblingProgram);
            }
        }

        visitor.generate(ast);


        String c3dCode = dumpC3D(c3dContext);
        String cCode = C3DToCConverter.convertToC(c3dContext);

        GeneretedCodeDTO generatedCode = new GeneretedCodeDTO(c3dCode, cCode, "main");
        return new CompileResponseDTO(true, errors, generatedCode, null, System.currentTimeMillis() - startTime);
    }

    private String dumpC3D(C3DContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (var q : ctx.getQuadruples()) {
            sb.append(q.toString()).append("\n");
        }
        return sb.toString();
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

                ParseTree tree = parser.program();
                ASTBuilder builder = new ASTBuilder();
                NodeProgram program = (NodeProgram) builder.visit(tree);

                if (program != null && program.getClassDeclaration() != null) {
                    siblings.add(program);
                }
            } catch (Exception e) {
                System.err.println("[loadSiblingClasses] Skipping " + file.getName()
                        + " due to: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        return siblings;
    }
}