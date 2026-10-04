package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeReturn extends ASTNode {

    private ASTNode expression;

    public NodeReturn() {
        this(null, 0, 0);
    }

    public NodeReturn(int line, int column) {
        this(null, line, column);
    }

    public NodeReturn(ASTNode expression, int line, int column) {
        super(line, column);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "retornar" + (expression != null ? " " + expression.toString() : "");
    }

    @Override
    public String getTipoNodo() {
        return "Return";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitReturn(this);
    }
}
