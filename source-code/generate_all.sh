#!/bin/bash

# Guardar la ruta raíz absoluta para volver siempre al mismo punto
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BASE_PATH="src/main/java/com/piglatin"

echo "Iniciando generación de todos los parsers"

# 1. Generar parser para Y
echo "======================================"
echo " Generando parser para LENGUAJE Y"
echo "======================================"
cd "$ROOT_DIR/$BASE_PATH/y/infrastructure/parser" || exit 1
chmod +x generate.sh
./generate.sh

# 2. Generar parser para Zetariano
echo "======================================"
echo " Generando parser para ZETARIANO"
echo "======================================"
cd "$ROOT_DIR/$BASE_PATH/zetariano/infrastructure/parser" || exit 1
chmod +x generate.sh
./generate.sh

# 3. Generar parser para Pig Latin
echo "======================================"
echo " Generando parser para PIG LATIN"
echo "======================================"
cd "$ROOT_DIR/$BASE_PATH/piglatin/infrastructure/parser" || exit 1
chmod +x generate.sh
./generate.sh

# Regresar a la raíz del proyecto
cd "$ROOT_DIR" || exit 1

echo "======================================"
echo "Todos los parsers han sido generados exitosamente!"