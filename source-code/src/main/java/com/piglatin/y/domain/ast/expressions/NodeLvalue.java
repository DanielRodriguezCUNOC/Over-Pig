package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeLvalue extends NodeExpression {

    private String identifier;
    private List<ASTNode> suffixes;

    public NodeLvalue() {
        this("", new ArrayList<>(), 0, 0);
    }

    public NodeLvalue(int line, int column) {
        this("", new ArrayList<>(), line, column);
    }

    public NodeLvalue(String identifier, List<ASTNode> suffixes, int line, int column) {
        super(line, column);
        this.identifier = identifier;
        this.suffixes = suffixes != null ? suffixes : new ArrayList<>();
    }

    public void addSuffix(ASTNode suffix) {
        if (suffixes == null) {
            this.suffixes = new ArrayList<>();
        }
        this.suffixes.add(suffix);
    }

    public boolean hasSuffixes() {
        return suffixes != null && !suffixes.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(identifier);
        if (suffixes != null) {
            for (ASTNode suffix : suffixes) {
                sb.append(suffix.toString());
            }
        }
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Lvalue";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitLvalue(this);
    }
}
