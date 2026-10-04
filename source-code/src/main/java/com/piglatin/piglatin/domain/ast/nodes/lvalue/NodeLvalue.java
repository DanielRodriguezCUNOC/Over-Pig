package com.piglatin.piglatin.domain.ast.nodes.lvalue;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a left-hand value (lvalue) production:
 * ID lvalueSufix*
 */

@Getter
@Setter
public class NodeLvalue extends ASTNode {

    //* Tha base identifier of the lvalue
    private String identifier;
    //* List of suffixies that follow the base identifier
    private List<ASTNode> suffixes;

    public NodeLvalue(){
        this(null, new ArrayList<>(), 0, 0);
    }

    public NodeLvalue(int line, int column){
        this(null, new ArrayList<>(), line, column);
    }

    public NodeLvalue(String identifier, List<ASTNode> suffixes,  int line, int column) {
        super(line, column);
        this.identifier = identifier;
        this.suffixes = suffixes;
    }

    public void addSuffix(ASTNode suffix){
        if(suffixes == null) this.suffixes = new ArrayList<>();
        this.suffixes.add(suffix);
    }

    public boolean hasSuffixes() {
        return suffixes != null && !suffixes.isEmpty();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(identifier);

        if(suffixes != null) {
            for(ASTNode suffix : suffixes) {
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
