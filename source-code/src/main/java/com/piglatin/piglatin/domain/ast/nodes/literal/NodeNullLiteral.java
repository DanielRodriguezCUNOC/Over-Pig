package com.piglatin.piglatin.domain.ast.nodes.literal;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;

@Getter
public class NodeNullLiteral extends ASTNode {

    public NodeNullLiteral(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public String getTipoNodo() {
        return "Null Literal";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitNullLiteral(this);
    }
}