package com.piglatin.piglatin.application.services;

import com.piglatin.common.application.dto.CompilationErrorDTO;
import com.piglatin.y.domain.symboltable.YSymbolTable;
import com.piglatin.y.domain.types.YTypeTable;
import com.piglatin.zetariano.domain.ast.principal.NodeProgram;
import com.piglatin.zetariano.infrastructure.codegen.c3d.VtableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de hermanos: .z y .y
 */
public class Siblings {
    List<NodeProgram> zPrograms = new ArrayList<>();
    List<com.piglatin.y.domain.ast.principal.NodeProgram> yPrograms = new ArrayList<>();

    VtableLayout zLayout;

    List<CompilationErrorDTO> errors = new ArrayList<>();

    YSymbolTable ySymbolTable =
            new YSymbolTable();
    YTypeTable yTypeTable =
            new YTypeTable();
    com.piglatin.y.infrastructure.codegen.c3d.StructLayout yStructLayout =
            new com.piglatin.y.infrastructure.codegen.c3d.StructLayout();
}