#!/bin/bash

# Ruta al JAR de ANTLR
ANTLR_JAR="/home/clare/antlr4/antlr-4.13.2-complete.jar"

# Gramáticas
GRAMMAR_LEXER="YLexer.g4"
GRAMMAR_PARSER="YParser.g4"

# Paquete donde ANTLR4 generara los archivos
PACKAGE="com.piglatin.y.infrastructure.parser.generated"

# Directorio de salida
OUTPUT_DIR="generated"

# Eliminar directorio antiguo para evitar basura
rm -rf "$OUTPUT_DIR"

echo "======================================"
echo " Generando parser para LENGUAJE Y?"
echo "======================================"

java -jar "$ANTLR_JAR" \
    -visitor \
    -listener \
    -long-messages \
    -package "$PACKAGE" \
    -o "$OUTPUT_DIR" \
    "$GRAMMAR_LEXER" \
    "$GRAMMAR_PARSER"

echo "Parser para Y? generado en $OUTPUT_DIR"