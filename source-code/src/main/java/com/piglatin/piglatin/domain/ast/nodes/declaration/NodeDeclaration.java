package com.piglatin.piglatin.domain.ast.nodes.declaration;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;

/**
 * This class allows that other nodes declare identifier (variables)
 */
public abstract class NodeDeclaration extends ASTNode {

    public NodeDeclaration() {super();}

    public NodeDeclaration(int line, int column) {super(line,column);}
}
