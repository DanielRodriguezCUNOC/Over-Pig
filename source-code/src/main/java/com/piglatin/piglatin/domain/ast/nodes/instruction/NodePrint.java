package com.piglatin.piglatin.domain.ast.nodes.instruction;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a print statement productions:
 * IMPREMERE printItem (IMPREMERE printItem)* ;
 */
@Getter
@Setter
public class NodePrint extends ASTNode {

    //* List of items to be printed
    private List<ASTNode> printItems;

    public NodePrint(){
        this(new ArrayList<>(), 0, 0);
    }

    public NodePrint(int line, int column){
        this(new ArrayList<>(), line, column);
    }

    public NodePrint(List<ASTNode> printItems, int line, int column){
        super(line, column);
        this.printItems = printItems != null ? printItems : new ArrayList<>();
    }

    public void addPrintItem(ASTNode item){
        if (this.printItems == null) this.printItems = new ArrayList<>();
        this.printItems.add(item);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(">>");

        if(printItems != null && !printItems.isEmpty()){
            for (int i = 0; i < printItems.size(); i++) {
                sb.append(printItems.get(i).toString());
                if(i < printItems.size() - 1) sb.append(" >>");
            }
        }
        sb.append(";");
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Print";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitPrint(this);
    }
}
