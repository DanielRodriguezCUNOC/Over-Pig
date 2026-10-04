package com.piglatin.piglatin.domain.ast.nodes;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an import statement production:
 * import ID (DOT ID)*
 */
@Getter
@Setter
public class NodeImport extends ASTNode {

    //* The list of path components
    private List<String> path;

    public NodeImport() {
        this(new ArrayList<>(), 0, 0);
    }

    public NodeImport(int line, int column) {
        this(new ArrayList<>(), line, column);
    }

    public NodeImport(List<String> path, int line, int column) {
        super(line, column);
        this.path = path != null ? path : new ArrayList<>();
    }

    public void addPathComponent(String component) {
        if (this.path == null) {
            this.path = new ArrayList<>();
        }
        this.path.add(component);
    }

    /**
     * Gets the file name
     */
    public String getFileName() {
        if (path == null || path.isEmpty()) {
            return "";
        }
        return path.get(path.size() - 1);
    }

    /**
     * Gets the file extension
     */
    public String getFileExtension() {
        String fileName = getFileName();
        if (fileName.isEmpty()) {
            return "";
        }
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot > 0 && lastDot < fileName.length() - 1) {
            return fileName.substring(lastDot + 1);
        }
        return "";
    }

    /**
     * Gets the import path as a string separated by dots
     */
    public String getPathAsString() {
        return String.join(".", path);
    }

    @Override
    public String toString() {
        return "import " + getPathAsString() + ";";
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