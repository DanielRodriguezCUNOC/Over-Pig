package com.piglatin.piglatin.domain.ast.nodes.declaration;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a array declaration production:
 * SERIES ID [entero] : tipo ({valores})?
 */

@Getter
@Setter
public class NodeArrayDeclaration extends NodeDeclaration {

    private String identifier;
    private String elementType;
    private List<ASTNode> initialValues;
    private int size;
    private ASTNode sizeExpression;

    public NodeArrayDeclaration(){
        this(null, null, null, new ArrayList<>(), 0, 0);
    }

    public NodeArrayDeclaration(int line, int column) {
        this(null, null, null, new ArrayList<>(), line, column);
    }

    public NodeArrayDeclaration(String identifier, ASTNode sizeExpression, String elementType, List<ASTNode> initialValues ,int line, int column) {
        super(line, column);
        this.identifier = identifier;
        this.sizeExpression = sizeExpression;
        this.elementType = elementType;
        this.initialValues = initialValues != null ? initialValues : new ArrayList<>();
    }

    public NodeArrayDeclaration(String identifier, int size, String elementType,
                                List<ASTNode> initialValues, int line, int column) {
        super(line, column);
        this.identifier = identifier;
        this.size = size;
        this.elementType = elementType;
        this.initialValues = initialValues != null ? initialValues : new ArrayList<>();
        this.sizeExpression = null;
    }

    public int getEvaluatedSize(){
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("series ").append(identifier).append("[");
        if (sizeExpression != null) {
            sb.append(sizeExpression.toString());
        } else {
            sb.append(size);
        }
        sb.append("]");
        if (elementType != null) sb.append(" : ").append(elementType);
        if (!initialValues.isEmpty()) {
            sb.append(" = {");
            for (int i = 0; i < initialValues.size(); i++) {
                sb.append(initialValues.get(i).toString());
                if (i < initialValues.size() - 1) sb.append(", ");
            }
            sb.append("}");
        }
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Array Declaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitArrayDeclaration(this);
    }
}
