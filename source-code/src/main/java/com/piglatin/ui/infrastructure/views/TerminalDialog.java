package com.piglatin.ui.infrastructure.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TerminalDialog extends JDialog {

    private final Process process;
    private final JTextArea outputArea;
    private final JTextField inputField;
    private final PrintWriter stdin;

    private final Color bgDark = new Color(30, 30, 30);
    private final Color fgText = new Color(220, 220, 220);
    private final Color orangeAccent = new Color(255, 140, 0);
    private final Color redError = new Color(255, 100, 100);

    public TerminalDialog(JFrame parent, Process process) {
        super(parent, "Terminal", false);
        this.process = process;
        this.stdin = new PrintWriter(
                new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8), true);

        setSize(850, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(bgDark);
        outputArea.setForeground(fgText);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        outputArea.setCaretColor(orangeAccent);

        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBorder(new EmptyBorder(4, 4, 4, 4));

        inputField = new JTextField();
        inputField.setBackground(bgDark);
        inputField.setForeground(fgText);
        inputField.setCaretColor(orangeAccent);
        inputField.setFont(new Font("Consolas", Font.PLAIN, 13));
        inputField.addActionListener(e -> {
            String text = inputField.getText();
            inputField.setText("");
            stdin.println(text);
        });

        JButton btnClose = new JButton("Cerrar");
        btnClose.addActionListener(e -> dispose());

        JButton btnKill = new JButton("Forzar cierre");
        btnKill.addActionListener(e -> {
            process.destroyForcibly();
            dispose();
        });

        JPanel bottom = new JPanel(new BorderLayout(4, 4));
        bottom.setBorder(new EmptyBorder(4, 4, 4, 4));
        bottom.add(inputField, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnKill);
        buttons.add(btnClose);
        bottom.add(buttons, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        startReader(process.getInputStream(), false);
        startReader(process.getErrorStream(), true);
        startProcessWatcher();

        SwingUtilities.invokeLater(inputField::requestFocusInWindow);
    }

    private void startReader(InputStream in, boolean isError) {
        Thread t = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    final String text = line + "\n";
                    SwingUtilities.invokeLater(() -> {
                        if (isError) {
                            outputArea.append(text);
                        } else {
                            outputArea.append(text);
                        }
                        outputArea.setCaretPosition(outputArea.getDocument().getLength());
                    });
                }
            } catch (IOException ignored) {
            }
        }, isError ? "term-err" : "term-out");
        t.setDaemon(true);
        t.start();
    }

    private void startProcessWatcher() {
        Thread t = new Thread(() -> {
            try {
                int exitCode = process.waitFor();
                SwingUtilities.invokeLater(() -> {
                    outputArea.append("\n[Proceso terminado con código " + exitCode + "]\n");
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                    inputField.setEnabled(false);
                });
            } catch (InterruptedException ignored) {
            }
        }, "term-watcher");
        t.setDaemon(true);
        t.start();
    }
}