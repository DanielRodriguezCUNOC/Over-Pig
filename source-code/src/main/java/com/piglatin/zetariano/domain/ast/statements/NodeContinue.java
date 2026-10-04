package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeContinue extends NodeStatement {
    public NodeContinue(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "Continue";
    }

    @Override
    public String getTipoNodo() {
        return "Continue";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitContinue(this);
    }
}
