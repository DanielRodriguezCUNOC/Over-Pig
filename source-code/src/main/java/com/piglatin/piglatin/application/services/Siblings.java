package com.piglatin.piglatin.application.services;

import com.piglatin.zetariano.domain.ast.principal.NodeProgram;

import java.util.ArrayList;
import java.util.List;

public class Siblings {
    List<NodeProgram> zPrograms = new ArrayList<>();
    List<com.piglatin.y.domain.ast.principal.NodeProgram> yPrograms = new ArrayList<>();

    com.piglatin.y.domain.symboltable.SymbolTable ySymbolTable =
            new com.piglatin.y.domain.symboltable.SymbolTable();
    com.piglatin.y.domain.types.TypeTable yTypeTable =
            new com.piglatin.y.domain.types.TypeTable();
    com.piglatin.y.infrastructure.codegen.c3d.StructLayout yStructLayout =
            new com.piglatin.y.infrastructure.codegen.c3d.StructLayout();
}
