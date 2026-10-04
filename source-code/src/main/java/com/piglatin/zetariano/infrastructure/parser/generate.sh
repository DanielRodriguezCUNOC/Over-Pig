#!/bin/bash

ANTLR_JAR="/home/clare/antlr4/antlr-4.13.2-complete.jar"

GRAMMAR_LEXER="ZetarianoLexer.g4"
GRAMMAR_PARSER="ZetarianoParser.g4"
PACKAGE="com.piglatin.zetariano.infrastructure.parser.generated"
OUTPUT_DIR="generated"

rm -rf "$OUTPUT_DIR"

echo "======================================"
echo " Generando parser para ZETARIANO"
echo "======================================"

java -jar "$ANTLR_JAR" \
    -visitor \
    -listener \
    -long-messages \
    -package "$PACKAGE" \
    -o "$OUTPUT_DIR" \
    "$GRAMMAR_LEXER" \
    "$GRAMMAR_PARSER"

echo "Parser para Zetariano generado en $OUTPUT_DIR"