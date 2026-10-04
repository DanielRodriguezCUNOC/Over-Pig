package com.piglatin.y.domain.semantic;

import com.piglatin.common.application.dto.CustomErrorDTO;
import com.piglatin.y.domain.ast.principal.ASTNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SemanticErrorReporter {

    private String fileName;
    private final List<CustomErrorDTO> errors;

    public SemanticErrorReporter() {
        this.errors = new ArrayList<>();
    }

    public SemanticErrorReporter(String fileName) {
        this.fileName = (fileName == null || fileName.isBlank()) ? "<unknown>" : fileName;
        this.errors = new ArrayList<>();
    }

    public void reportError(String message, int line, int column) {
        errors.add(new CustomErrorDTO(line, column, message));
    }

    public void reportError(String message, ASTNode node) {
        errors.add(new CustomErrorDTO(node.getLine(), node.getColumn(), message));
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public boolean hasFatalErrors() {
        return hasErrors();
    }

    public int getErrorsCount() {
        return errors.size();
    }

    public void clear() {
        errors.clear();
    }

    @Override
    public String toString() {
        if (errors.isEmpty()) return "No semantic errors found";

        StringBuilder sb = new StringBuilder();
        sb.append("Semantic Errors: (").append(errors.size()).append(")\n");
        for (CustomErrorDTO error : errors) {
            sb.append(" [").append(error.line())
                    .append(":").append(error.column())
                    .append("] ").append(error.message()).append("\n");
        }
        return sb.toString();
    }
}