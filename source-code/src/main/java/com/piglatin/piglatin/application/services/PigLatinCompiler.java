package com.piglatin.piglatin.application.services;

import com.piglatin.common.application.dto.*;
import com.piglatin.common.application.ports.input.CompilerUseCase;
import com.piglatin.common.application.ports.output.ErrorReporter;
import com.piglatin.common.infrastructure.codegen.ir.C3DContext;
import com.piglatin.common.infrastructure.codegen.ir.C3DEmitter;
import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.Label;
import com.piglatin.common.infrastructure.codegen.ir.instructions.calls.ReturnVoid;
import com.piglatin.common.infrastructure.codegen.riscv.C3DToRiscVConverter;
import com.piglatin.piglatin.application.dto.ParserResultDTO;
import com.piglatin.piglatin.domain.ast.nodes.NodeImport;
import com.piglatin.piglatin.domain.ast.principal.NodeProgram;
import com.piglatin.piglatin.domain.semantic.ImportedSymbols;
import com.piglatin.piglatin.domain.symboltable.PigLatinSymbolTable;
import com.piglatin.piglatin.infrastructure.codegen.c3d.PigLatinC3DVisitor;
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
import com.piglatin.zetariano.infrastructure.codegen.c3d.VtableLayout;
import com.piglatin.zetariano.infrastructure.codegen.c3d.ZetarianoC3DVisitor;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            //? AST de PigLatin
            NodeProgram ast = treeMapper.buildAST(parserResult);

            //? Cargar tablas de hermanos ANTES de validar modo
            ZetarianoTypeTable zTypeTable =
                    new ZetarianoTypeTable();
            ZetarianoSymbolTable zSymbolTable =
                    new ZetarianoSymbolTable();

            Siblings siblings = new Siblings();
            if (request.getProjectDirectory() != null) {
                siblings = loadSiblingClasses(
                        request.getProjectDirectory(),
                        request.getCurrentFileName(),
                        request.getFileName(),
                        ast.getImports(),
                        zTypeTable, zSymbolTable);
            }

            if (!siblings.errors.isEmpty()) {
                errors.addAll(siblings.errors);
                errorReporter.reportAll(siblings.errors);
                return buildResponse(false, errors, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), startTime);
            }

            siblings.zLayout = new VtableLayout(zSymbolTable, zTypeTable);
            ImportedSymbols imported = new SiblingImportedSymbols(
                    siblings.zLayout, zTypeTable,
                    siblings.ySymbolTable, siblings.yTypeTable, siblings.yStructLayout);

            List<CustomErrorDTO> semanticErrors = semanticAnalyzer.analyze(ast, imported);
            List<CompilationErrorDTO> semErrors = convertSemanticErrors(semanticErrors, request);
            errors.addAll(semErrors);

            if (!errors.isEmpty()) {
                errorReporter.reportAll(semErrors);
                return buildResponse(false, errors, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), startTime);
            }

            // Extracción de datos de tablas
            List<SymbolInfoDTO> symbols = extractSymbols(zSymbolTable, siblings.ySymbolTable);
            List<TypeInfoDTO> types = extractTypes(zTypeTable, siblings.yTypeTable);
            List<ScopeInfoDTO> scopes = extractScopes(zSymbolTable, siblings.ySymbolTable);

            if (request.getMode() == CompilationMode.VALIDATE_ONLY) {
                return buildResponse(true, errors, null, symbols, types, scopes, startTime);
            }

            //* C3D
            C3DContext c3dContext = new C3DContext();

            //   main: call main_0; ret
            // final al exit de RARS -> li a7, 10; ecall
            ctxEmitEntryPoint(c3dContext);

            // Emitir PigLatin primero (main_0)
            PigLatinC3DVisitor pigVisitor = new PigLatinC3DVisitor(
                    c3dContext, siblings.zLayout, zSymbolTable, zTypeTable, siblings.ySymbolTable,
                    siblings.yStructLayout);
            ast.accept(pigVisitor);

            // Emitir hermanos .z con una sola instancia del visitor, así comparten el mapa de
            // strings (str_N) y de vtables
            ZetarianoC3DVisitor zVisitor =
                    new ZetarianoC3DVisitor(c3dContext, zSymbolTable, zTypeTable);
            for (com.piglatin.zetariano.domain.ast.principal.NodeProgram z : siblings.zPrograms) {
                try {
                    if (z == null || z.getClasses() == null || z.getClasses().isEmpty()) continue;
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

            // Datos para la sección .data. Prefijos distintos por lenguaje.
            // str_N (Zetariano), plstr_N (PigLatin)
            Map<String, String> strings = new LinkedHashMap<>();
            strings.putAll(pigVisitor.getStringLiterals());
            strings.putAll(zVisitor.getStringLiterals());
            Map<String, List<String>> vtables = zVisitor.getVtables();

            // C3D -> RISC-V
            List<Instruction> instructions = c3dContext.getInstructions();
            C3DToRiscVConverter riscv = new C3DToRiscVConverter(instructions, strings, vtables);
            String asm = riscv.convert();

            // Serializar C3D
            String c3dCode = c3dContext.getInstructions().stream()
                    .map(Instruction::toC3D)
                    .collect(Collectors.joining("\n"));

            GeneretedCodeDTO generatedCode = new GeneretedCodeDTO();
            generatedCode.setC3dCode(c3dCode);
            generatedCode.setAsmCode(asm);
            generatedCode.setEntryPoint("main");
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

    private void ctxEmitEntryPoint(C3DContext c3dContext) {
        c3dContext.emit(new Label("main"));
        new C3DEmitter(c3dContext).emitCallVoid("main_0", 0, 0);
        c3dContext.emit(new ReturnVoid());
    }

    /**
     * Carga los .z y .y del proyecto.
     *
     * Primero se resuelven los  import del .pig respecto al directorio del proyecto
     * en el orden en que aparecen. Así se encuentran los archivos en subcarpetas y se cargan en un orden
     * determinista
     * Después se recorre el propio directorio buscando .z / .y sueltos
     */
    private Siblings loadSiblingClasses(
            String projectDirectory,
            String currentFileName,
            String reportFileName,
            List<NodeImport> imports,
            ZetarianoTypeTable zTypeTable,
            ZetarianoSymbolTable zSymbolTable) {

        Siblings result = new Siblings();

        File dir = new File(projectDirectory);
        if (!dir.exists() || !dir.isDirectory()) return result;

        java.util.Set<String> loaded = new java.util.HashSet<>();
        List<File> toLoad = new ArrayList<>();

        if (imports != null) {
            for (NodeImport imp : imports) {
                File f = resolveImportFile(dir, imp);
                if (f == null) {
                    if (isFileImport(imp)) {
                        result.errors.add(new CompilationErrorDTO(
                                CompilationStage.SEMANTIC_ANALYSIS,
                                "Cannot resolve import '" + String.join(".", imp.getPath())
                                        + "': no such file in the project",
                                imp.getLine(), imp.getColumn(), reportFileName));
                    }
                    continue;
                }
                if (!f.getName().equals(currentFileName) && addIfNew(f, loaded)) {
                    toLoad.add(f);
                }
            }
        }

        File[] files = dir.listFiles((d, name) ->
                (name.endsWith(".z") || name.endsWith(".y")) && !name.equals(currentFileName));
        if (files != null) {
            for (File f : files) {
                if (addIfNew(f, loaded)) toLoad.add(f);
            }
        }

        for (File file : toLoad) {
            try {
                String code = Files.readString(file.toPath());

                if (file.getName().endsWith(".z")) {
                    loadZFile(code, file.getName(), zTypeTable, zSymbolTable, result);
                } else if (file.getName().endsWith(".y")) {
                    loadYFile(code, file.getName(), result);
                }
            } catch (Exception e) {
                // Antes se descartaba con un println: ahora el archivo roto se reporta como error.
                result.errors.add(new CompilationErrorDTO(
                        CompilationStage.SEMANTIC_ANALYSIS,
                        "Cannot analyze " + file.getName() + ": " + describe(e),
                        1, 1, file.getName()));
            }
        }
        return result;
    }

    /** true si el import apunta a un archivo: ruta con al menos dos segmentos que termina en .z o .y */
    private boolean isFileImport(NodeImport imp) {
        if (imp == null || imp.getPath() == null || imp.getPath().size() < 2) return false;
        String ext = imp.getPath().get(imp.getPath().size() - 1);
        return "z".equals(ext) || "y".equals(ext);
    }

    private String describe(Exception e) {
        return e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
    }

    /**
     * Devuelve null si el import no tiene extensión .z/.y o el archivo no existe.
     */
    private File resolveImportFile(File baseDir, NodeImport imp) {
        if (!isFileImport(imp)) return null;
        List<String> path = imp.getPath();
        String ext = path.get(path.size() - 1);

        File current = baseDir;
        for (int i = 0; i < path.size() - 2; i++) {
            current = new File(current, path.get(i));
        }
        File file = new File(current, path.get(path.size() - 2) + "." + ext);
        return file.isFile() ? file : null;
    }

    private boolean addIfNew(File file, java.util.Set<String> loaded) {
        try {
            return loaded.add(file.getCanonicalPath());
        } catch (java.io.IOException e) {
            return loaded.add(file.getAbsolutePath());
        }
    }

    /**
     * Carga un hermano .z y corre la semántica de Zetariano con las tablas compartidas.
     * El VtableLayout se crea al terminar de cargar todos los .z
     */
    private void loadZFile(String code, String fileName,
                           ZetarianoTypeTable zTypeTable,
                           ZetarianoSymbolTable zSymbolTable,
                           Siblings result) {

        org.antlr.v4.runtime.CharStream input =
                org.antlr.v4.runtime.CharStreams.fromString(code);

        com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoLexer lexer =
                new com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new SiblingErrorListener(fileName, CompilationStage.LEXICAL_ANALYSIS, result.errors));

        org.antlr.v4.runtime.CommonTokenStream tokens =
                new org.antlr.v4.runtime.CommonTokenStream(lexer);

        com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParser parser =
                new com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new SiblingErrorListener(fileName, CompilationStage.SYNTACTIC_ANALYSIS, result.errors));

        int errorsBefore = result.errors.size();
        org.antlr.v4.runtime.tree.ParseTree tree = parser.compilationUnit();
        if (result.errors.size() > errorsBefore) return; // no se analiza un arbol roto
        ZetarianoASTBuilder builder =
                new ZetarianoASTBuilder();

        Object built = builder.visit(tree);
        if (!(built instanceof com.piglatin.zetariano.domain.ast.principal.NodeProgram program)) return;
        if (program.getClasses() == null || program.getClasses().isEmpty()) return;

        for (NodeClassDeclaration cls: program.getClasses()){
            if (cls !=null){
                zTypeTable.registerClass(cls.getName(), cls.getSuperClass());
            }
        }

        ZetarianoSemanticAnalyzer zAnalyzer =
                new ZetarianoSemanticAnalyzer(fileName);
        zAnalyzer.analyze(program, zTypeTable, zSymbolTable);

        result.zPrograms.add(program);
    }

    private void loadYFile(String code, String fileName, Siblings result) {

        org.antlr.v4.runtime.CharStream input =
                org.antlr.v4.runtime.CharStreams.fromString(code);

        com.piglatin.y.infrastructure.parser.generated.YLexer lexer =
                new com.piglatin.y.infrastructure.parser.generated.YLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new SiblingErrorListener(fileName, CompilationStage.LEXICAL_ANALYSIS, result.errors));

        org.antlr.v4.runtime.CommonTokenStream tokens =
                new org.antlr.v4.runtime.CommonTokenStream(lexer);

        com.piglatin.y.infrastructure.parser.generated.YParser parser =
                new com.piglatin.y.infrastructure.parser.generated.YParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new SiblingErrorListener(fileName, CompilationStage.SYNTACTIC_ANALYSIS, result.errors));

        int errorsBefore = result.errors.size();
        org.antlr.v4.runtime.tree.ParseTree tree = parser.program();
        if (result.errors.size() > errorsBefore) return; // no se analiza un arbol roto
        YASTBuilder builder =
                new YASTBuilder();

        Object built = builder.visit(tree);
        if (!(built instanceof com.piglatin.y.domain.ast.principal.NodeProgram program)) return;

        // Registrar structs en yStructLayout y pre-registrar en
        //    yTypeTable local, antes del analsis.
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

        // Capturar las tablas resultantes.
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

    /** Recoge los errores lexicos / sintacticos de un .z o .y con el nombre de su archivo. */
    private static final class SiblingErrorListener extends org.antlr.v4.runtime.BaseErrorListener {
        private final String fileName;
        private final CompilationStage stage;
        private final List<CompilationErrorDTO> sink;

        SiblingErrorListener(String fileName, CompilationStage stage, List<CompilationErrorDTO> sink) {
            this.fileName = fileName;
            this.stage = stage;
            this.sink = sink;
        }

        @Override
        public void syntaxError(org.antlr.v4.runtime.Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine, String msg,
                                org.antlr.v4.runtime.RecognitionException e) {
            sink.add(new CompilationErrorDTO(stage, msg, line, charPositionInLine, fileName));
        }
    }

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

        // Símbolos de Y
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

        // Tipos de Zetariano
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

        // Tipos de Y
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

        // Ámbito Global para Zetariano
        if (zSymbolTable != null) {
            list.add(new ScopeInfoDTO("0", "None", "global (Zetariano)"));
        }

        // Ámbito Global para Y
        if (ySymbolTable != null) {
            list.add(new ScopeInfoDTO("0", "None", "global (Y)"));
        }
        return list;
    }
}