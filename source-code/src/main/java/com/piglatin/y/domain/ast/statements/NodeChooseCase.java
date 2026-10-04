package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeChooseCase extends ASTNode {

    private ASTNode value;
    private NodeBlock block;
    private boolean isDefault;

    public NodeChooseCase() {
        this(null, null, false, 0, 0);
    }

    public NodeChooseCase(int line, int column) {
        this(null, null, false, line, column);
    }

    public NodeChooseCase(ASTNode value, NodeBlock block, boolean isDefault, int line, int column) {
        super(line, column);
        this.value = value;
        this.block = block;
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isDefault) {
            sb.append("    siempre:\n");
        } else {
            sb.append("    caso ").append(value.toString()).append(":\n");
        }
        if (block != null) {
            sb.append(block.toString());
        }
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Choose Case";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitChooseCase(this);
    }
}
