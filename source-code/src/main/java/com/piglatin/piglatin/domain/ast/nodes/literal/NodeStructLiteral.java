package com.piglatin.piglatin.domain.ast.nodes.literal;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a struct literal expression production:
 * ID? { expression (COMMA expression)* }
 */

@Getter
@Setter
public class NodeStructLiteral extends ASTNode {

    //* The name of the struct type (optional)
    private String structName;

    //* List of field initializers for the struct
    private List<ASTNode> values;

    public NodeStructLiteral() {
        this(null, new ArrayList<>(), 0, 0);
    }

    public NodeStructLiteral(int line, int column) {
        this(null, new ArrayList<>(), line, column);
    }

    public NodeStructLiteral(String structName, List<ASTNode> values, int line, int column){
        super(line, column);
        this.structName = structName;
        this.values = values != null ? values : new ArrayList<>();
    }

    public void addValue(ASTNode value){
        if(this.values == null) this.values = new ArrayList<>();
        this.values.add(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if(structName != null && !structName.isEmpty()) sb.append(structName).append(" ");

        sb.append("{ ");
        if (values != null && !values.isEmpty()) {
            for (int i = 0; i < values.size(); i++) {
                sb.append(values.get(i).toString());
                if (i != values.size() - 1) sb.append(", ");
            }
        }
        sb.append(" }");
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Struct Literal";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitStructLiteral(this);
    }
}
