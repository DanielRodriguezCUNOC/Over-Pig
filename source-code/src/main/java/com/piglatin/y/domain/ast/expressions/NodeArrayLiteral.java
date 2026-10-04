package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeArrayLiteral extends NodeExpression {

    private List<ASTNode> elements;

    public NodeArrayLiteral() {
        this(new ArrayList<>(), 0, 0);
    }

    public NodeArrayLiteral(int line, int column) {
        this(new ArrayList<>(), line, column);
    }

    public NodeArrayLiteral(List<ASTNode> elements, int line, int column) {
        super(line, column);
        this.elements = elements != null ? elements : new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i).toString());
            if (i < elements.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Array Literal";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitArrayLiteral(this);
    }
}
