package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NodeImport extends NodeStatement {
    private String path;

    public NodeImport(String path, int line, int column) {
        super(line, column);
        this.path = path;
    }

    @Override
    public String toString() {
        return "Import: " + path;
    }

    @Override
    public String getTipoNodo() {
        return "Import";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitImport(this);
    }
}
