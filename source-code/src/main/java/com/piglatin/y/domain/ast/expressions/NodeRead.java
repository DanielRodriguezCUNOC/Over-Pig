package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeRead extends ASTNode {

    public NodeRead() {
        super(0, 0);
    }

    public NodeRead(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "leer()";
    }

    @Override
    public String getTipoNodo() {
        return "Read";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitRead(this);
    }
}
