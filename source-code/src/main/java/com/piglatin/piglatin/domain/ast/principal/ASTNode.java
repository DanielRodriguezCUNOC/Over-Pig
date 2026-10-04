package com.piglatin.piglatin.domain.ast.principal;

import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ASTNode {

    private int line;
    private int column;


    public ASTNode() {
        this.line = 0;
        this.column = 0;
    }

    public ASTNode(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public abstract String toString();
    public abstract String getTipoNodo();
    public abstract <T> T accept(Visitor<T> visitor);
    public String getLocation(){
        return "line: " +  line + ", column: " + column;
    }
}
