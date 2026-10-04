package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeFieldAccess extends ASTNode {

    private String fieldName;
    private ASTNode currentNode;

    public NodeFieldAccess() {
        this(null, "", 0, 0);
    }

    public NodeFieldAccess(int line, int column) {
        this(null, "", line, column);
    }

    public NodeFieldAccess(ASTNode currentNode, String fieldName, int line, int column) {
        super(line, column);
        this.currentNode = currentNode;
        this.fieldName = fieldName;
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
