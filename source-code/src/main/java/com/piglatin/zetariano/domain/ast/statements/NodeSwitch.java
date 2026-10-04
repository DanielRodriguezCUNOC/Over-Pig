package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NodeSwitch extends NodeStatement {
    private NodeExpression expression;
    private List<NodeCase> cases;
    private NodeBlock defaultBlock;

    public NodeSwitch(NodeExpression expression, List<NodeCase> cases, NodeBlock defaultBlock, int line, int column) {
        super(line, column);
        this.expression = expression;
        this.cases = cases;
        this.defaultBlock = defaultBlock;
    }

    @Override
    public String toString() {
        return "Switch";
    }

    @Override
    public String getTipoNodo() {
        return "Switch";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitSwitch(this);
    }
}
