package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeBreak extends ASTNode {

    public NodeBreak() {
        super(0, 0);
    }

    public NodeBreak(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "romper";
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
