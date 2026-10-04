package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeContinue extends ASTNode {

    public NodeContinue() {
        super(0, 0);
    }

    public NodeContinue(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "continuar";
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
