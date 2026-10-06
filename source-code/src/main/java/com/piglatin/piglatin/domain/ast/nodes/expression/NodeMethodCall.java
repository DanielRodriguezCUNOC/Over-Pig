package com.piglatin.piglatin.domain.ast.nodes.expression;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;

import java.util.List;

@Getter
public class NodeMethodCall extends ASTNode {

    private final ASTNode target;
    private final List<ASTNode> arguments;

    public NodeMethodCall(ASTNode target, List<ASTNode> arguments, int line, int column) {
        super(line, column);
        this.target = target;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return target.toString() + "(" + arguments + ")";
    }

    @Override
    public String getTipoNodo() {
        return "Method Call";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitMethodCall(this);
    }
}