package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class NodeLvalue extends NodeExpression {
    public NodeLvalue(int line, int column) {
        super(line, column);
    }
}
