package com.piglatin.piglatin.domain.ast.nodes.expression;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an object instantiation expression production:
 * novus ID ( argumentList? )
 */
@Getter
@Setter
public class NodeNewInstance extends ASTNode {

    //* The name of the class being instantiated
    private String className;

    //* List of arguments passed to the constructor
    private List<ASTNode> arguments;

    public NodeNewInstance() {
        this(null, new ArrayList<>(), 0, 0);
    }

    public NodeNewInstance(int line, int column) {
        this(null, new ArrayList<>(), line, column);
    }

    public NodeNewInstance(String className, List<ASTNode> arguments, int line, int column) {
        super(line, column);
        this.className = className;
        this.arguments = arguments != null ? arguments : new ArrayList<>();
    }

    public void addArgument(ASTNode argument) {
        if (this.arguments == null) {
            this.arguments = new ArrayList<>();
        }
        this.arguments.add(argument);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("novus ").append(className).append("(");
        if (arguments != null && !arguments.isEmpty()) {
            for (int i = 0; i < arguments.size(); i++) {
                sb.append(arguments.get(i).toString());
                if (i != arguments.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "New Instance";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitNewInstance(this);
    }
}