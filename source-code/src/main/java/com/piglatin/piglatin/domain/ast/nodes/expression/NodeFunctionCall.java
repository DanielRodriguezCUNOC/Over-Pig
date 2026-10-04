package com.piglatin.piglatin.domain.ast.nodes.expression;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a function call production
 * ID (argumentList?)
 */
@Getter
@Setter
public class NodeFunctionCall extends ASTNode {

    //* The name of the function being called
    private String functionName;

    //* List of arguments passed to the function
    private List<ASTNode> arguments;

    //* The current node
    private ASTNode currentNode;

    public NodeFunctionCall() {
        this(null, "", new ArrayList<>(), 0, 0);
    }

    public NodeFunctionCall(int line, int column) {
        this(null, "", new ArrayList<>(), line, column);
    }

    public NodeFunctionCall(ASTNode current, String functionName, List<ASTNode> arguments, int line, int column) {
        super(line, column);
        this.functionName = functionName;
        this.arguments = arguments;
        this.currentNode = current;
    }

    public void addArguments(ASTNode argument) {
        if (this.arguments == null) this.arguments = new ArrayList<>();
        this.arguments.add(argument);
    }

    public boolean hasArguments(){
        return arguments != null && !arguments.isEmpty();
    }

    public int getArgumentCount(){
        return arguments != null ? arguments.size() : 0;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(functionName).append("(");

        if(arguments != null && !arguments.isEmpty()){
            for (int i = 0; i < arguments.size(); i++) {
                sb.append(arguments.get(i).toString());
                if(i < arguments.size() - 1) sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Function Call";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitFunctionCall(this);
    }
}
