package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeParameter extends ASTNode {

    private String type;
    private String name;
    private boolean isArray;
    private boolean isStruct;

    public NodeParameter() {
        this("", "", false, false, 0, 0);
    }

    public NodeParameter(int line, int column) {
        this("", "", false, false, line, column);
    }

    public NodeParameter(String type, String name, boolean isArray, boolean isStruct, int line, int column) {
        super(line, column);
        this.type = type;
        this.name = name;
        this.isArray = isArray;
        this.isStruct = isStruct;
    }

    @Override
    public String toString() {
        if (isArray) {
            return "[] " + type + " " + name;
        }
        if (isStruct) {
            return "{} " + type + " " + name;
        }
        return type + " " + name;
    }

    @Override
    public String getTipoNodo() {
        return "Parameter";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitParameter(this);
    }
}
