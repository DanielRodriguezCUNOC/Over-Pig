package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeFieldDeclaration extends ASTNode {

    private String type;
    private String fieldName;
    private boolean isArray;
    private int arraySize;

    public NodeFieldDeclaration() {
        this("", "", false, 0, 0, 0);
    }

    public NodeFieldDeclaration(int line, int column) {
        this("", "", false, 0, line, column);
    }

    public NodeFieldDeclaration(String type, String fieldName, boolean isArray, int arraySize, int line, int column) {
        super(line, column);
        this.type = type;
        this.fieldName = fieldName;
        this.isArray = isArray;
        this.arraySize = arraySize;
    }

    @Override
    public String toString() {
        if (isArray) {
            return type + " " + fieldName + "[" + arraySize + "]";
        }
        return type + " " + fieldName;
    }

    @Override
    public String getTipoNodo() {
        return "Field Declaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitFieldDeclaration(this);
    }
}
