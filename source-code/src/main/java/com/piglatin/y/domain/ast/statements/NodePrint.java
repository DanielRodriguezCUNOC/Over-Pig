package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NodePrint extends ASTNode {

    private List<ASTNode> expressions;

    public NodePrint() {
        this(null, 0, 0);
    }

    public NodePrint(int line, int column) {
        this(null, line, column);
    }

    public NodePrint(List<ASTNode> expressions, int line, int column) {
        super(line, column);
        this.expressions = expressions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("imprimir(");
        if (expressions != null) {
            for (int i = 0; i < expressions.size(); i++) {
                sb.append(expressions.get(i).toString());
                if (i < expressions.size() - 1) sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Print";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitPrint(this);
    }
}
