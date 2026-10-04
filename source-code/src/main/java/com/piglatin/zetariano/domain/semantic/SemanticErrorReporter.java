package com.piglatin.zetariano.domain.semantic;

import com.piglatin.common.application.dto.CompilationErrorDTO;
import com.piglatin.common.application.dto.CompilationStage;
import com.piglatin.common.application.ports.output.ErrorReporter;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;

import javax.print.attribute.standard.Severity;
import java.util.ArrayList;
import java.util.List;

public class SemanticErrorReporter implements ErrorReporter {

    private final String fileName;
    private final List<CompilationErrorDTO> errors;

    public SemanticErrorReporter(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            fileName = "<unknown>";
        }
        this.fileName = fileName;
        this.errors = new ArrayList<>();
    }


    public void reportError(String message, int line, int column) {
        report(new CompilationErrorDTO(
                CompilationStage.SEMANTIC_ANALYSIS,
                message,
                Math.max(line, 0),
                Math.max(column, 0),
                fileName
        ));
    }

    public void reportError(String message, ASTNode node) {
        int line = (node != null) ? node.getLine() : 0;
        int column = (node != null) ? node.getColumn() : 0;
        reportError(message, line, column);
    }

    public void reportWarning(String message, int line, int column) {
        reportError("[WARNING] " + message, line, column);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public int getErrorsCount() {
        return errors.size();
    }

    public String getFileName() {
        return fileName;
    }


    @Override
    public void report(CompilationErrorDTO error) {
        if (error != null) errors.add(error);
    }

    @Override
    public void reportAll(List<CompilationErrorDTO> incoming) {
        if (incoming != null) errors.addAll(incoming);
    }

    @Override
    public List<CompilationErrorDTO> getErrors() {
        return new ArrayList<>(errors);
    }

    @Override
    public boolean hasFatalErrors() {
        return !errors.isEmpty();
    }

    @Override
    public void clear() {
        errors.clear();
    }


    @Override
    public String toString() {
        if (errors.isEmpty()) return "No semantic errors found";

        StringBuilder sb = new StringBuilder();
        sb.append("Semantic Errors: (").append(errors.size()).append(")\n");
        for (CompilationErrorDTO e : errors) {
            sb.append(" [").append(e.getLine())
                    .append(":").append(e.getColumn())
                    .append("] ").append(e.getMessage())
                    .append("\n");
        }
        return sb.toString();
    }
}