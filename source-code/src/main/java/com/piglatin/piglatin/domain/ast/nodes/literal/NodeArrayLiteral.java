package com.piglatin.piglatin.domain.ast.nodes.literal;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an array literal expression production:
 * { expression (COMMA expression)* }
 */
@Getter
@Setter
public class NodeArrayLiteral extends ASTNode {

    //* Lisst of values in the array literal
    private List<ASTNode> values;

    public NodeArrayLiteral() {
        this(new ArrayList<>(), 0, 0);
    }

    public NodeArrayLiteral(int line, int column) {
        this(new ArrayList<>(), line, column);
    }

    public NodeArrayLiteral(List<ASTNode> values, int line, int column) {
        super(line, column);
        this.values = values != null ? values : new ArrayList<>();
    }

    public void addValue(ASTNode value) {
        if (values == null) this.values = new ArrayList<>();
        this.values.add(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        
        if(values != null && !values.isEmpty()) {
            for (int i = 0; i < values.size(); i++) {
                sb.append(values.get(i).toString());
                if (i < values.size() - 1) sb.append(", ");
            }
        }
        sb.append(" }");
        return "";
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
