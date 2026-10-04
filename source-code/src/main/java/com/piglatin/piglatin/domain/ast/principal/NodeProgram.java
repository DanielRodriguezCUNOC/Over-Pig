package com.piglatin.piglatin.domain.ast.principal;


import com.piglatin.piglatin.domain.ast.nodes.NodeImport;
import com.piglatin.piglatin.domain.ast.nodes.declaration.NodeDeclaration;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
/**
 * Represent the structure of the program
 */

@Getter
@Setter
public class NodeProgram extends ASTNode{

    //* This represents the section VARIABILES>
    private List<NodeDeclaration> globalDeclarations;
    //* This represents the section MAIOR>
    private List<ASTNode> mainInstructions;
    //* This represents the section of imports
    private List<NodeImport> imports;

    public NodeProgram() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0, 0);
    }

    public NodeProgram(int line, int column) {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), line, column);
    }

    public NodeProgram(List<NodeDeclaration> globalDeclarations,
                       List<ASTNode> mainInstructions,
                       List<NodeImport> imports,
                       int line, int column) {
        super(line, column);
        this.globalDeclarations = globalDeclarations != null ? globalDeclarations : new ArrayList<>();
        this.mainInstructions = mainInstructions != null ? mainInstructions : new ArrayList<>();
        this.imports = imports != null ? imports : new ArrayList<>();
    }

   public void addGlobalDeclaration(NodeDeclaration declaration) {
        if(declaration != null) this.globalDeclarations.add(declaration);
   }

   public void addMainInstruction(ASTNode instruction) {
        if(instruction != null) this.mainInstructions.add(instruction);
   }

   public void addImport(NodeImport importNode) {
        if(importNode != null) this.imports.add(importNode);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(" === Global Declarations ===\n");
        for(NodeDeclaration statement : globalDeclarations) {
            sb.append(statement.toString()).append("\n");
        }

        sb.append(" === Main Instructions ===\n");
        for(ASTNode statement : mainInstructions) {
            sb.append(statement.toString()).append("\n");
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
