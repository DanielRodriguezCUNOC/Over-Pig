package com.piglatin.piglatin.domain.ast.nodes.literal;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a decimal literal production:
 * DECIMAL
 */
@Getter
@Setter
public class NodeDecimalLiteral extends ASTNode {

    //* The decimal values convert to double
    private double value;

    public NodeDecimalLiteral() {
        this(0.0, 0, 0);
    }

    public NodeDecimalLiteral(int line, int column) {
        this(0.0, line, column);
    }

    public NodeDecimalLiteral(double value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String getTipoNodo() {
        return "Decimal Literal";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitDecimalLiteral(this);
    }
}
