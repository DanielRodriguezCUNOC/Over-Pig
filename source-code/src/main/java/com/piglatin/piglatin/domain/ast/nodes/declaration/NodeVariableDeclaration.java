package com.piglatin.piglatin.domain.ast.nodes.declaration;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represent a variable declaration production:
 * ESTO ID (: tipo)? expresion
 */
@Getter
@Setter
public class NodeVariableDeclaration extends NodeDeclaration {

    private String identifier;
    private String type;
    //* initialization expression
    private ASTNode initializer;

    public NodeVariableDeclaration(){
        this(null, null, null, 0, 0);
    }

    public NodeVariableDeclaration(int line, int column){
        this(null, null, null, line, column);
    }

    public NodeVariableDeclaration(String identifier,
                                   String type,
                                   ASTNode initializer,
                                   int line, int column) {
        super(line, column);
        this.identifier = identifier;
        this.type = type;
        this.initializer = initializer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("esto ").append(identifier);
        if(type != null) sb.append(" : ").append(type);
        sb.append(" ").append(initializer != null ? initializer.toString() : "null");
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Variable Declaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitVariableDeclaration(this);
    }
}
