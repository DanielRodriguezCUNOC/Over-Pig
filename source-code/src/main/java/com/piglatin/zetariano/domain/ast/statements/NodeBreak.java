package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeBreak extends NodeStatement {
    public NodeBreak(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "Break";
    }

    @Override
    public String getTipoNodo() {
        return "Break";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitBreak(this);
    }
}
