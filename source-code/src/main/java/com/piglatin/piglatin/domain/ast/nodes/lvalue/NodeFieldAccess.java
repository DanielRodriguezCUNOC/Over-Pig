package com.piglatin.piglatin.domain.ast.nodes.lvalue;

import com.piglatin.piglatin.domain.ast.nodes.expression.NodeIdentifier;
import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a field access suffix production:
 * DOT ID
 */

@Getter
@Setter
public class NodeFieldAccess extends ASTNode {

    //* The name of the field being accessed
    private String fieldName;

    //* The current node identifier
    private ASTNode currentNode;

    public NodeFieldAccess(){
        this(null, null, 0, 0);
    }

    public NodeFieldAccess(int line, int column){
        this(null, null, line, column);
    }

    public NodeFieldAccess(ASTNode currentNode, String fieldName, int line, int column){
        super(line, column);
        this.fieldName = fieldName;
        this.currentNode = currentNode;
    }

    @Override
    public String toString() {
        return "." + fieldName;
    }

    @Override
    public String getTipoNodo() {
        return "Field Access";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitFieldAccess(this);
    }
}
