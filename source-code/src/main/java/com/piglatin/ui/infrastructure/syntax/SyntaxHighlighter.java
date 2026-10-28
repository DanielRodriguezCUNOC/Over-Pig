package com.piglatin.ui.infrastructure.syntax;

import com.piglatin.piglatin.infrastructure.parser.generated.LatinLexer;
import com.piglatin.y.infrastructure.parser.generated.YLexer;
import com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoLexer;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.List;

public class SyntaxHighlighter {

    // Paleta de Colores estilo VS Code Dark+
    private static final Color COLOR_KEYWORD  = new Color(197, 134, 192); // Púrpura (si, class, return, definir...)
    private static final Color COLOR_TYPE     = new Color(78, 201, 176);  // Turquesa / Cian (int, float, bool, numerus...)
    private static final Color COLOR_STRING   = new Color(206, 145, 120); // Naranja suave (Strings y Chars)
    private static final Color COLOR_NUMBER   = new Color(181, 206, 168); // Verde pastel (Enteros y Decimales)
    private static final Color COLOR_COMMENT  = new Color(106, 153, 85);  // Verde comentario
    private static final Color COLOR_OPERATOR = new Color(212, 212, 212); // Blanco grisáceo
    private static final Color COLOR_DEFAULT  = new Color(220, 220, 220); // Texto base

    public static void applyHighlighting(JTextPane textPane, String fileExtension) {
        StyledDocument doc = textPane.getStyledDocument();
        String text;
        try {
            text = doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            return;
        }

        if (text.isEmpty()) return;

        List<? extends Token> tokens = tokenize(text, fileExtension);
        if (tokens == null) return;

        SwingUtilities.invokeLater(() -> {
            int caretPosition = textPane.getCaretPosition();

            Style defaultStyle = doc.addStyle("Default", null);
            StyleConstants.setForeground(defaultStyle, COLOR_DEFAULT);
            doc.setCharacterAttributes(0, text.length(), defaultStyle, true);

            for (Token token : tokens) {
                int start = token.getStartIndex();
                int stop = token.getStopIndex();
                int length = (stop - start) + 1;
                if (length <= 0 || start < 0) continue;

                Color color = getColorForToken(fileExtension, token.getType());
                if (color.equals(COLOR_DEFAULT)) continue;

                Style style = doc.addStyle("Style_" + token.getType(), null);
                StyleConstants.setForeground(style, color);

                doc.setCharacterAttributes(start, length, style, true);
            }

            if (caretPosition <= doc.getLength()) {
                textPane.setCaretPosition(caretPosition);
            }
        });
    }

    private static List<? extends Token> tokenize(String text, String ext) {
        try {
            if (ext.equalsIgnoreCase("pig") || ext.equalsIgnoreCase("pi")) {
                LatinLexer lexer = new LatinLexer(CharStreams.fromString(text));
                return lexer.getAllTokens();
            } else if (ext.equalsIgnoreCase("z")) {
                ZetarianoLexer lexer = new ZetarianoLexer(CharStreams.fromString(text));
                return lexer.getAllTokens();
            } else if (ext.equalsIgnoreCase("y")) {
                YLexer lexer = new YLexer(CharStreams.fromString(text));
                return lexer.getAllTokens();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static Color getColorForToken(String ext, int tokenType) {
        if (ext.equalsIgnoreCase("pig") || ext.equalsIgnoreCase("pi")) {
            return getPigLatinColor(tokenType);
        } else if (ext.equalsIgnoreCase("z")) {
            return getZetarianoColor(tokenType);
        } else if (ext.equalsIgnoreCase("y")) {
            return getYColor(tokenType);
        }
        return COLOR_DEFAULT;
    }

    private static Color getPigLatinColor(int type) {
        switch (type) {
            case LatinLexer.LINE_COMMENT:
            case LatinLexer.BLOCK_COMMENT:
                return COLOR_COMMENT;

            case LatinLexer.STRING:
            case LatinLexer.CHAR:
                return COLOR_STRING;

            case LatinLexer.DECIMAL:
            case LatinLexer.INTEGER:
                return COLOR_NUMBER;

            case LatinLexer.NUMERUS:
            case LatinLexer.TEXTUM:
            case LatinLexer.DECIMALIS:
            case LatinLexer.BOOL:
            case LatinLexer.LITTERA:
                return COLOR_TYPE;

            case LatinLexer.SI:
            case LatinLexer.ALITER:
            case LatinLexer.DUM:
            case LatinLexer.FACERE:
            case LatinLexer.PER:
            case LatinLexer.PERGE:
            case LatinLexer.INTERRUMPE:
            case LatinLexer.IMPORT:
            case LatinLexer.NOVUS:
            case LatinLexer.ESTO:
            case LatinLexer.VERUM:
            case LatinLexer.FALSUS:
            case LatinLexer.NULL:
            case LatinLexer.FINIS:
                return COLOR_KEYWORD;

            default:
                return COLOR_DEFAULT;
        }
    }

    private static Color getZetarianoColor(int type) {
        switch (type) {
            case ZetarianoLexer.LINE_COMMENT:
            case ZetarianoLexer.BLOCK_COMMENT:
                return COLOR_COMMENT;

            case ZetarianoLexer.STRING_LITERAL:
            case ZetarianoLexer.CHAR_LITERAL:
                return COLOR_STRING;

            case ZetarianoLexer.INT_LITERAL:
            case ZetarianoLexer.DOUBLE_LITERAL:
                return COLOR_NUMBER;

            case ZetarianoLexer.INT:
            case ZetarianoLexer.FLOAT:
            case ZetarianoLexer.CHAR:
            case ZetarianoLexer.BOOLEAN:
            case ZetarianoLexer.STRING:
            case ZetarianoLexer.VOID:
                return COLOR_TYPE;

            case ZetarianoLexer.CLASS:
            case ZetarianoLexer.PUBLIC:
            case ZetarianoLexer.PRIVATE:
            case ZetarianoLexer.PROTECTED:
            case ZetarianoLexer.EXTENDS:
            case ZetarianoLexer.OVERRIDE:
            case ZetarianoLexer.THIS:
            case ZetarianoLexer.SUPER:
            case ZetarianoLexer.RETURN:
            case ZetarianoLexer.IF:
            case ZetarianoLexer.ELSE:
            case ZetarianoLexer.FOR:
            case ZetarianoLexer.WHILE:
            case ZetarianoLexer.DO:
            case ZetarianoLexer.SWITCH:
            case ZetarianoLexer.CASE:
            case ZetarianoLexer.BREAK:
            case ZetarianoLexer.DEFAULT:
            case ZetarianoLexer.CONTINUE:
            case ZetarianoLexer.NEW:
            case ZetarianoLexer.NULL:
            case ZetarianoLexer.TRUE:
            case ZetarianoLexer.FALSE:
                return COLOR_KEYWORD;

            default:
                return COLOR_DEFAULT;
        }
    }

    private static Color getYColor(int type) {
        switch (type) {
            case YLexer.LINE_COMMENT:
            case YLexer.BLOCK_COMMENT:
                return COLOR_COMMENT;

            case YLexer.STRING:
            case YLexer.CHAR:
                return COLOR_STRING;

            case YLexer.INTEGER:
            case YLexer.FLOAT:
                return COLOR_NUMBER;

            case YLexer.ENTERO:
            case YLexer.FLOTANTE:
            case YLexer.CARACTER:
            case YLexer.CADENA:
            case YLexer.BOOL_TYPE:
            case YLexer.ESTRUCTURA:
                return COLOR_TYPE;

            case YLexer.ESTRUCTURAS_HEADER:
            case YLexer.FUNCIONES_HEADER:
            case YLexer.SI:
            case YLexer.ENTONCES:
            case YLexer.SINO:
            case YLexer.CONTRARIO:
            case YLexer.ELEGIR:
            case YLexer.CASO:
            case YLexer.SIEMPRE:
            case YLexer.PARA:
            case YLexer.MIENTRAS:
            case YLexer.HACER:
            case YLexer.DEFINIR:
            case YLexer.RETORNAR:
            case YLexer.ROMPER:
            case YLexer.CONTINUAR:
            case YLexer.IMPRIMIR:
            case YLexer.LEER:
            case YLexer.VERDADERO:
            case YLexer.FALSO:
                return COLOR_KEYWORD;

            default:
                return COLOR_DEFAULT;
        }
    }
}