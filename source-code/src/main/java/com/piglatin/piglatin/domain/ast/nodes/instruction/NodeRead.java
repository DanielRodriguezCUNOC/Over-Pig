package com.piglatin.piglatin.domain.ast.nodes.instruction;

import com.piglatin.piglatin.domain.ast.nodes.lvalue.NodeLvalue;
import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a read statement production:
 * lvalue LEERE ;
 */
@Getter
@Setter
public class NodeRead extends ASTNode {

    //* The target where the read value
    private NodeLvalue target;

    public NodeRead() {
        this(null, 0, 0);
    }

    public NodeRead(int line, int column) {
        this(null, line, column);
    }

    public NodeRead(NodeLvalue target, int line, int column) {
        super(line, column);
        this.target = target;
    }

    @Override
    public String toString() {
        return target.toString() + "<<";
    }

    @Override
    public String getTipoNodo() {
        return "Read";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitRead(this);
    }
}
