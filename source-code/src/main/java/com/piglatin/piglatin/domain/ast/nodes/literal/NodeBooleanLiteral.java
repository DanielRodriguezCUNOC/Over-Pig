package com.piglatin.piglatin.domain.ast.nodes.literal;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a boolean literal production:
 * VERUM | FALSUS
 */

@Getter
@Setter
public class NodeBooleanLiteral extends ASTNode {

    //* Boolean value
    private boolean value;

    public NodeBooleanLiteral() {
        this(false, 0, 0);
    }

    public NodeBooleanLiteral(int line, int column) {
        this(false, line, column);
    }

    public NodeBooleanLiteral(boolean value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    @Override
    public String toString() {
        return value ? "verum" : "falsus";
    }

    @Override
    public String getTipoNodo() {
        return "Boolean Literal";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitBooleanLiteral(this);
    }
}
