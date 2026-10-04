package com.piglatin;

import com.piglatin.common.application.ports.input.CompilerUseCase;
import com.piglatin.common.application.ports.input.ExecutionCCode;
import com.piglatin.ui.infrastructure.config.ApplicationConfig;
import com.piglatin.ui.infrastructure.facade.LanguageCompilerFacadeImpl;
import com.piglatin.ui.infrastructure.facade.LanguajeCompilerFacade;
import com.piglatin.ui.infrastructure.views.CodeEditorView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            CompilerUseCase compiler = ApplicationConfig.createCompiler();
            ExecutionCCode executor = ApplicationConfig.createExecutionCCode();
            LanguajeCompilerFacade facade = new LanguageCompilerFacadeImpl(compiler, executor);
            CodeEditorView view = new CodeEditorView(facade);
            view.setVisible(true);
        });
    }
}