package com.piglatin.piglatin.domain.ast.nodes.instruction;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a break statement production:
 * INTERRUMPRE ;
 */
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
        return "interrumpe;";
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
