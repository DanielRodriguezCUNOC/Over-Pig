package com.piglatin.y.domain.ast.principal;

import com.piglatin.y.domain.ast.statements.NodeStructureDefinition;
import com.piglatin.y.domain.ast.statements.NodeFunctionDefinition;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeProgram extends ASTNode {

    private List<NodeStructureDefinition> structures;
    private List<NodeFunctionDefinition> functions;

    public NodeProgram() {
        this(new ArrayList<>(), new ArrayList<>(), 0, 0);
    }

    public NodeProgram(int line, int column) {
        this(new ArrayList<>(), new ArrayList<>(), line, column);
    }

    public NodeProgram(
                       List<NodeStructureDefinition> structures,
                       List<NodeFunctionDefinition> functions,
                       int line, int column) {
        super(line, column);
        this.structures = structures != null ? structures : new ArrayList<>();
        this.functions = functions != null ? functions : new ArrayList<>();
    }

    public void addStructure(NodeStructureDefinition structure) {
        if (structure != null) {
            this.structures.add(structure);
        }
    }

    public void addFunction(NodeFunctionDefinition function) {
        if (function != null) {
            this.functions.add(function);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (structures != null && !structures.isEmpty()) {
            sb.append(" === Structure Definitions ===\n");
            for (NodeStructureDefinition struct : structures) {
                sb.append(struct.toString()).append("\n");
            }
        }
        if (functions != null && !functions.isEmpty()) {
            sb.append(" === Function Definitions ===\n");
            for (NodeFunctionDefinition func : functions) {
                sb.append(func.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Program";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitProgram(this);
    }
}
