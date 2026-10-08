package com.piglatin.piglatin.application.services;

import com.piglatin.common.application.dto.*;
import com.piglatin.common.application.ports.input.CompilerUseCase;
import com.piglatin.common.application.ports.output.ErrorReporter;
import com.piglatin.common.infrastructure.codegen.C3DContext;
import com.piglatin.common.infrastructure.codegen.C3DToCConverter;
import com.piglatin.common.infrastructure.codegen.Quadruple;
import com.piglatin.piglatin.application.dto.ParserResultDTO;
import com.piglatin.piglatin.domain.ast.principal.NodeProgram;
import com.piglatin.piglatin.domain.symboltable.PigLatinSymbolTable;
import com.piglatin.piglatin.infrastructure.codegen.c3d.PigLatinC3DVisitor;
import com.piglatin.piglatin.infrastructure.parser.PigLatinServiceAnalyzer;
import com.piglatin.y.domain.ast.visitor.YASTBuilder;
import com.piglatin.y.domain.semantic.YSemanticAnalyzer;
import com.piglatin.y.domain.symboltable.YSymbolTable;
import com.piglatin.y.domain.types.YTypeTable;
import com.piglatin.zetariano.domain.ast.statements.NodeClassDeclaration;
import com.piglatin.zetariano.domain.ast.visitor.ZetarianoASTBuilder;
import com.piglatin.zetariano.domain.semantic.ZetarianoSemanticAnalyzer;
import com.piglatin.zetariano.domain.symboltable.Symbol;
import com.piglatin.zetariano.domain.symboltable.ZetarianoSymbolTable;
import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class PigLatinCompiler implements CompilerUseCase {

    private final PigLatinServiceAnalyzer parser;
    private final PigLatinTreeMapperService treeMapper;
    private final PigLatinSemanticAnalyzer semanticAnalyzer;
    private final ErrorReporter errorReporter;

    public PigLatinCompiler(
            PigLatinServiceAnalyzer parser,
            PigLatinTreeMapperService treeMapper,
            PigLatinSemanticAnalyzer semanticAnalyzer,
            ErrorReporter errorReporter
    ) {
        this.parser = parser;
        this.treeMapper = treeMapper;
        this.semanticAnalyzer = semanticAnalyzer;
        this.errorReporter = errorReporter;
    }

    /**
     * Contenedor de hermanos: .z por un lado, .y por otro.
     * Las tablas de Y viven aquí porque el SemanticAnalyzer de Y crea sus
     * propias tablas internamente y las expone vía SemanticContext.
     */

    @Override
    public CompileResponseDTO compile(CompileRequestDTO request) {
        long startTime = System.currentTimeMillis();

        ParserResultDTO parserResult = parser.executeAnalysis(request.getSourceCode());

        List<CompilationErrorDTO> errors = new ArrayList<>(
                convertParserErrors(parserResult.getErrorsList(), request)
        );

        if (!errors.isEmpty() || parserResult.getParseTree() == null) {
            if (parserResult.getParseTree() == null && errors.isEmpty()) {
                errors.add(new CompilationErrorDTO(
                        CompilationStage.SYNTACTIC_ANALYSIS,
                        "Cannot generate syntax tree.", 1, 1, request.getFileName()));
            }
            errorReporter.reportAll(errors);
            return buildResponse(false, errors, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), startTime);
        }

        try {
            // === AST de PigLatin ===
            NodeProgram ast = treeMapper.buildAST(parserResult);

            List<CustomErrorDTO> semanticErrors = semanticAnalyzer.analyze(ast);
            List<CompilationErrorDTO> semErrors = convertSemanticErrors(semanticErrors, request);
            errors.addAll(semErrors);

            if (!errors.isEmpty()) {
                errorReporter.reportAll(semErrors);
                return buildResponse(false, errors, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), startTime);
            }

            // === Cargar tablas de hermanos ANTES de validar modo ===
            ZetarianoTypeTable zTypeTable =
                    new ZetarianoTypeTable();
            ZetarianoSymbolTable zSymbolTable =
                    new ZetarianoSymbolTable();
            com.piglatin.zetariano.infrastructure.codegen.c3d.ClassLayout zLayout =
                    new com.piglatin.zetariano.infrastructure.codegen.c3d.ClassLayout();

            Siblings siblings = new Siblings();
            if (request.getProjectDirectory() != null) {
                siblings = loadSiblingClasses(
                        request.getProjectDirectory(),
                        request.getCurrentFileName(),
                        zTypeTable, zSymbolTable, zLayout);
            }

            // Extracción de datos de tablas
            List<SymbolInfoDTO> symbols = extractSymbols(zSymbolTable, siblings.ySymbolTable);
            List<TypeInfoDTO> types = extractTypes(zTypeTable, siblings.yTypeTable);
            List<ScopeInfoDTO> scopes = extractScopes(zSymbolTable, siblings.ySymbolTable);

            if (request.getMode() == CompilationMode.VALIDATE_ONLY) {
                return buildResponse(true, errors, null, symbols, types, scopes, startTime);
            }

            // === C3D ===
            C3DContext c3dContext = new C3DContext();

            // Emitir PigLatin PRIMERO
            PigLatinC3DVisitor pigVisitor = new PigLatinC3DVisitor(
                    c3dContext, zLayout, zSymbolTable, zTypeTable, siblings.ySymbolTable);
            ast.accept(pigVisitor);

            // Emitir hermanos .z
            for (com.piglatin.zetariano.domain.ast.principal.NodeProgram z : siblings.zPrograms) {
                try {
                    if (z == null || z.getClasses() == null || z.getClasses().isEmpty()) continue;
                    com.piglatin.zetariano.infrastructure.codegen.c3d.ZetarianoC3DVisitor zVisitor =
                            new com.piglatin.zetariano.infrastructure.codegen.c3d.ZetarianoC3DVisitor(
                                    c3dContext, zLayout, zSymbolTable, zTypeTable);
                    zVisitor.generate(z);
                } catch (Exception e) {
                    System.err.println("[PigLatinCompiler] Error emitiendo C3D de hermano .z: "
                            + e.getClass().getSimpleName() + ": " + e.getMessage());
                }
            }

            // Emitir hermanos .y
            for (com.piglatin.y.domain.ast.principal.NodeProgram y : siblings.yPrograms) {
                try {
                    if (y == null) continue;
                    com.piglatin.y.infrastructure.codegen.c3d.YC3DVisitor yVisitor =
                            new com.piglatin.y.infrastructure.codegen.c3d.YC3DVisitor(
                                    c3dContext, siblings.yStructLayout,
                                    siblings.ySymbolTable, siblings.yTypeTable);
                    y.accept(yVisitor);
                } catch (Exception e) {
                    System.err.println("[PigLatinCompiler] Error emitiendo C3D de hermano .y: "
                            + e.getClass().getSimpleName() + ": " + e.getMessage());
                }
            }

            // Serializar C3D
            StringBuilder sb = new StringBuilder();
            for (Quadruple q : c3dContext.getQuadruples()) {
                sb.append(q.toString()).append("\n");
            }
            String c3dCode = sb.toString();
            String cCode = C3DToCConverter.convertToC(c3dContext);

            GeneretedCodeDTO generatedCode = new GeneretedCodeDTO(c3dCode, cCode, "main");
            System.out.println("=== DEBUG COMPILER ===");
            System.out.println("Símbolos extraídos: " + symbols.size());
            System.out.println("Tipos extraídos: " + types.size());
            System.out.println("Scopes extraídos: " + scopes.size());
            return buildResponse(true, errors, generatedCode, symbols, types, scopes, startTime);

        } catch (Exception e) {
            errors.add(new CompilationErrorDTO(
                    CompilationStage.SEMANTIC_ANALYSIS,
                    "Internal error during analysis: " + e.getMessage(),
                    1, 1, request.getFileName()));
            errorReporter.reportAll(errors);
            return buildResponse(false, errors, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), startTime);
        }
    }


    /**
     * Recorre el directorio del proyecto, carga .z y .y.
     * - Los .z se analizan con tablas de Zetariano (pasadas como argumento).
     * - Los .y crean sus propias tablas internamente; el Siblings las captura.
     */
    private Siblings loadSiblingClasses(
            String projectDirectory,
            String currentFileName,
            ZetarianoTypeTable zTypeTable,
            ZetarianoSymbolTable zSymbolTable,
            com.piglatin.zetariano.infrastructure.codegen.c3d.ClassLayout zLayout) {

        Siblings result = new Siblings();

        File dir = new File(projectDirectory);
        if (!dir.exists() || !dir.isDirectory()) return result;

        File[] files = dir.listFiles((d, name) ->
                (name.endsWith(".z") || name.endsWith(".y")) && !name.equals(currentFileName));
        if (files == null) return result;

        for (File file : files) {
            try {
                String code = Files.readString(file.toPath());

                if (file.getName().endsWith(".z")) {
                    loadZFile(code, currentFileName, zTypeTable, zSymbolTable, zLayout, result);
                } else if (file.getName().endsWith(".y")) {
                    loadYFile(code, file.getName(), result);
                }
            } catch (Exception e) {
                System.err.println("[PigLatinCompiler/loadSiblingClasses] Skipping "
                        + file.getName() + ": " + e.getClass().getSimpleName()
                        + ": " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * Carga un hermano .z, corre la semántica de Zetariano con las tablas
     * compartidas y registra la clase en ClassLayout.
     */
    private void loadZFile(String code, String currentFileName,
                           ZetarianoTypeTable zTypeTable,
                           ZetarianoSymbolTable zSymbolTable,
                           com.piglatin.zetariano.infrastructure.codegen.c3d.ClassLayout zLayout,
                           Siblings result) {

        org.antlr.v4.runtime.CharStream input =
                org.antlr.v4.runtime.CharStreams.fromString(code);

        com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoLexer lexer =
                new com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoLexer(input);
        lexer.removeErrorListeners();

        org.antlr.v4.runtime.CommonTokenStream tokens =
                new org.antlr.v4.runtime.CommonTokenStream(lexer);

        com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParser parser =
                new com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParser(tokens);
        parser.removeErrorListeners();

        org.antlr.v4.runtime.tree.ParseTree tree = parser.compilationUnit();
        ZetarianoASTBuilder builder =
                new ZetarianoASTBuilder();

        Object built = builder.visit(tree);
        if (!(built instanceof com.piglatin.zetariano.domain.ast.principal.NodeProgram program)) return;
        if (program.getClasses() == null || program.getClasses().isEmpty()) return;

        for (NodeClassDeclaration cls: program.getClasses()){
            if (cls !=null){
                zTypeTable.registerClass(cls.getName(), cls.getSuperClass());
                zLayout.registerClass(cls);
            }
        }

        ZetarianoSemanticAnalyzer zAnalyzer =
                new ZetarianoSemanticAnalyzer(currentFileName);
        zAnalyzer.analyze(program, zTypeTable, zSymbolTable);

        result.zPrograms.add(program);
    }

    private void loadYFile(String code, String fileName, Siblings result) {

        org.antlr.v4.runtime.CharStream input =
                org.antlr.v4.runtime.CharStreams.fromString(code);

        com.piglatin.y.infrastructure.parser.generated.YLexer lexer =
                new com.piglatin.y.infrastructure.parser.generated.YLexer(input);
        lexer.removeErrorListeners();

        org.antlr.v4.runtime.CommonTokenStream tokens =
                new org.antlr.v4.runtime.CommonTokenStream(lexer);

        com.piglatin.y.infrastructure.parser.generated.YParser parser =
                new com.piglatin.y.infrastructure.parser.generated.YParser(tokens);
        parser.removeErrorListeners();

        org.antlr.v4.runtime.tree.ParseTree tree = parser.program();
        YASTBuilder builder =
                new YASTBuilder();

        Object built = builder.visit(tree);
        if (!(built instanceof com.piglatin.y.domain.ast.principal.NodeProgram program)) return;

        // Registrar structs en yStructLayout y pre-registrar en
        //    yTypeTable local, ANTES del analyze.
        if (program.getStructures() != null) {
            for (com.piglatin.y.domain.ast.statements.NodeStructureDefinition s : program.getStructures()) {
                if (s == null) continue;
                result.yStructLayout.registerStruct(s);
                result.yTypeTable.registerStruct(s.getName());
            }
        }

        // Analyze .
        YSemanticAnalyzer yAnalyzer =
                new YSemanticAnalyzer(fileName);
        com.piglatin.y.domain.semantic.SemanticContext yCtx = yAnalyzer.analyze(program);

        // Capturar las tablas resultantes. Reemplazan a las del Siblings.
        if (yCtx != null) {
            if (yCtx.getSymbolTable() != null) {
                result.ySymbolTable = yCtx.getSymbolTable();
            }
            if (yCtx.getTypeTable() != null) {
                // Preservar structs ya registradas
                if (program.getStructures() != null) {
                    for (com.piglatin.y.domain.ast.statements.NodeStructureDefinition s : program.getStructures()) {
                        if (s != null) yCtx.getTypeTable().registerStruct(s.getName());
                    }
                }
                result.yTypeTable = yCtx.getTypeTable();
            }
        }

        result.yPrograms.add(program);
    }

    // ============================================================
    // Conversión de errores
    // ============================================================

    private List<CompilationErrorDTO> convertParserErrors(
            List<CustomErrorDTO> parserErrors,
            CompileRequestDTO request
    ) {
        List<CompilationErrorDTO> errors = new ArrayList<>();
        if (parserErrors == null) return errors;

        for (CustomErrorDTO error : parserErrors) {
            CompilationStage stage;
            if (error.message().startsWith("Lexical Error:")) {
                stage = CompilationStage.LEXICAL_ANALYSIS;
            } else {
                stage = CompilationStage.SYNTACTIC_ANALYSIS;
            }
            errors.add(new CompilationErrorDTO(
                    stage, error.message(), error.line(), error.column(), request.getFileName()));
        }
        return errors;
    }

    private List<CompilationErrorDTO> convertSemanticErrors(
            List<CustomErrorDTO> semanticErrors,
            CompileRequestDTO request
    ) {
        List<CompilationErrorDTO> errors = new ArrayList<>();
        if (semanticErrors == null) return errors;

        for (CustomErrorDTO error : semanticErrors) {
            errors.add(new CompilationErrorDTO(
                    CompilationStage.SEMANTIC_ANALYSIS,
                    error.message(), error.line(), error.column(), request.getFileName()));
        }
        return errors;
    }

    private CompileResponseDTO buildResponse(
            boolean success,
            List<CompilationErrorDTO> errors,
            long startTime
    ) {
        return buildResponse(success, errors, null, startTime);
    }

    private CompileResponseDTO buildResponse(
            boolean success,
            List<CompilationErrorDTO> errors,
            GeneretedCodeDTO generatedCode,
            long startTime
    ) {
        return new CompileResponseDTO(
                success, errors, generatedCode, null,
                System.currentTimeMillis() - startTime);
    }

    private CompileResponseDTO buildResponse(
            boolean success,
            List<CompilationErrorDTO> errors,
            GeneretedCodeDTO generatedCode,
            List<SymbolInfoDTO> symbols,
            List<TypeInfoDTO> types,
            List<ScopeInfoDTO> scopes,
            long startTime
    ) {
        CompileResponseDTO response = new CompileResponseDTO(
                success, errors, generatedCode, null,
                System.currentTimeMillis() - startTime);

        response.setSymbols(symbols != null ? symbols : new ArrayList<>());
        response.setTypes(types != null ? types : new ArrayList<>());
        response.setScopes(scopes != null ? scopes : new ArrayList<>());

        return response;
    }

    private List<SymbolInfoDTO> extractSymbols(
            ZetarianoSymbolTable zSymbolTable,
            YSymbolTable ySymbolTable) {

        List<SymbolInfoDTO> list = new ArrayList<>();

        // Símbolos de PigLatin
        if (this.semanticAnalyzer != null && this.semanticAnalyzer.getSymbolTable() != null) {
            PigLatinSymbolTable pigST = this.semanticAnalyzer.getSymbolTable();
            if (pigST.getAllScopes() != null) {
                for (var scope : pigST.getAllScopes()) {
                    if (scope.getSymbols() != null) {
                        for (var sym : scope.getSymbols().values()) {
                            list.add(new SymbolInfoDTO(
                                    sym.getId(),
                                    sym.getName(),
                                    sym.getType() != null ? sym.getType() : "UNKNOWN",
                                    scope.getName(),
                                    sym.getLine()
                            ));
                        }
                    }
                }
            }
        }

        // Símbolos de Zetariano
        if (zSymbolTable != null && zSymbolTable.getAllSymbols() != null) {
            for (Symbol sym : zSymbolTable.getAllSymbols()) {
                list.add(new SymbolInfoDTO(
                        sym.getId(),
                        sym.getName(),
                        sym.getType(),
                        sym.getScope(),
                        sym.getLine()
                ));
            }
        }

        // Símbolos de Y (.y)
        if (ySymbolTable != null && ySymbolTable.getAllSymbols() != null) {
            for (com.piglatin.y.domain.symboltable.Symbol sym : ySymbolTable.getAllSymbols()) {
                list.add(new SymbolInfoDTO(
                        sym.getId(),
                        sym.getName(),
                        sym.getType(),
                        sym.getScope(),
                        sym.getLine()
                ));
            }
        }

        return list;
    }

    private List<TypeInfoDTO> extractTypes(
            ZetarianoTypeTable zTypeTable,
            YTypeTable yTypeTable) {

        List<TypeInfoDTO> list = new ArrayList<>();

        // Tipos de PigLatin
        if (this.semanticAnalyzer != null && this.semanticAnalyzer.getTypeTable() != null) {
            var pigTT = this.semanticAnalyzer.getTypeTable();

            if (pigTT.getPrimitiveTypes() != null) {
                for (String typeName : pigTT.getPrimitiveTypes()) {
                    list.add(new TypeInfoDTO(typeName, "PRIMITIVE", 0));
                }
            }
            if (pigTT.getCustomTypes() != null) {
                for (String typeName : pigTT.getCustomTypes()) {
                    list.add(new TypeInfoDTO(typeName, "CUSTOM", 0));
                }
            }
        }

        // Tipos de Zetariano (.z)
        if (zTypeTable != null) {
            if (zTypeTable.getPrimitiveTypesView() != null) {
                for (String typeName : zTypeTable.getPrimitiveTypesView()) {
                    list.add(new TypeInfoDTO(typeName, "PRIMITIVE", 0));
                }
            }
            if (zTypeTable.getUserDefinedTypesView() != null) {
                for (String typeName : zTypeTable.getUserDefinedTypesView()) {
                    list.add(new TypeInfoDTO(typeName, "CLASS", 0));
                }
            }
        }

        // Tipos de Y (.y)
        if (yTypeTable != null) {
            if (yTypeTable.getPrimitiveTypesView() != null) {
                for (String typeName : yTypeTable.getPrimitiveTypesView()) {
                    list.add(new TypeInfoDTO(typeName, "PRIMITIVE", 0));
                }
            }
            if (yTypeTable.getUserDefinedTypesView() != null) {
                for (String typeName : yTypeTable.getUserDefinedTypesView()) {
                    list.add(new TypeInfoDTO(typeName, "STRUCT", 0));
                }
            }
        }

        return list;
    }

    private List<ScopeInfoDTO> extractScopes(
            ZetarianoSymbolTable zSymbolTable,
            YSymbolTable ySymbolTable) {

        List<ScopeInfoDTO> list = new ArrayList<>();

        // Ámbitos de PigLatin
        if (this.semanticAnalyzer != null && this.semanticAnalyzer.getSymbolTable() != null) {
            var pigST = this.semanticAnalyzer.getSymbolTable();

            if (pigST.getAllScopes() != null) {
                for (com.piglatin.piglatin.domain.symboltable.Scope scope : pigST.getAllScopes()) {
                    String scopeId = String.valueOf(scope.getId());
                    String parentName = (scope.getParent() != null && scope.getParent().getName() != null)
                            ? scope.getParent().getName()
                            : "None";
                    String description = scope.toString();

                    list.add(new ScopeInfoDTO(scopeId, parentName, description));
                }
            }
        }

        // Ámbito Global para Zetariano (.z)
        if (zSymbolTable != null) {
            list.add(new ScopeInfoDTO("0", "None", "global (Zetariano)"));
        }

        // Ámbito Global para Y (.y)
        if (ySymbolTable != null) {
            list.add(new ScopeInfoDTO("0", "None", "global (Y)"));
        }

        return list;
    }
}