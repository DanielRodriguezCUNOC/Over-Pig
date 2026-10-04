package com.piglatin.piglatin.domain.ast.nodes.expression;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;

public abstract class NodeExpression extends ASTNode {

    public NodeExpression(){
        super();
    }

    public NodeExpression(int line,  int column){
        super(line, column);
    }
}
