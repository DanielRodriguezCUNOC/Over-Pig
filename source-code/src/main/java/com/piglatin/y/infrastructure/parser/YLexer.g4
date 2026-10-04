lexer grammar YLexer;

//* This tokens are called virtual tokens, the parer use them for generrate blocks
tokens { INDENT, DEDENT }

@members{
//* Stack for tracking the indentation levels
private java.util.LinkedList<Integer> indents = new java.util.LinkedList<>(java.util.Arrays.asList(0));

//* Queue for emittting multiple tokens
private java.util.Queue<Token> pendingTokens = new java.util.LinkedList<>();

@Override
public Token nextToken() {
    //* If there are queued tokens, return first
    if (!pendingTokens.isEmpty()) {
        return pendingTokens.poll();
    }

    //* Extract next token
    Token t = super.nextToken();

    //* Handle EOF:
    if (t.getType() == EOF) {
        //* Empty the queue amitting DEDENTS tokens
        while (indents.size() > 1) {
            indents.pop();
            pendingTokens.add(commonToken(YLexer.DEDENT, ""));
        }

        if (!pendingTokens.isEmpty()) {
            //* Add EOF token to the queue
            pendingTokens.add(t);
            return pendingTokens.poll();

        }
    }
    return t;
}

//* Method to create sintetic tokens
private Token commonToken(int type, String text) {
    int stop = this.getCharIndex() - 1;
    int start = text.isEmpty() ? stop : stop - text.length() + 1;
    return new org.antlr.v4.runtime.CommonToken(this._tokenFactorySourcePair, type,
            org.antlr.v4.runtime.Lexer.DEFAULT_TOKEN_CHANNEL, start, stop);

   }


//* Calculate spaces and emit INDENT/DEDENT tokens
private void processNewLine(String text) {
//Delete line breaks for count tabs and spaces
String spaces = text.replaceAll("[\r\n]+", "");
int indent = 0;
for (char c : spaces.toCharArray()) {
    //* tabs = 4 spaces
    if (c == '\t') indent += 4;
     else indent++;
   }

   int currentIndent = indents.peek();
    if (indent > currentIndent) {
    indents.push(indent);
    pendingTokens.add(commonToken(YLexer.INDENT, ""));
    }else{
        while (indent < currentIndent) {
            indents.pop();
            pendingTokens.add(commonToken(YLexer.DEDENT, ""));
            currentIndent = indents.peek();

        }
    }
 }
}


// Sections
ESTRUCTURAS_HEADER: '%estructuras';
FUNCIONES_HEADER: '%funciones';

// Keywords
ENTERO: 'entero';
FLOTANTE: 'flotante';
CARACTER: 'caracter';
CADENA: 'cadena';
BOOL_TYPE: 'bool';
SI: 'si';
ENTONCES: 'entonces';
SINO: 'sino';
CONTRARIO: 'contrario';
ELEGIR: 'elegir';
CASO: 'caso';
SIEMPRE: 'siempre';
PARA: 'para';
MIENTRAS: 'mientras';
HACER: 'hacer';
DEFINIR: 'definir';
RETORNAR: 'retornar';
ROMPER: 'romper';
CONTINUAR: 'continuar';
IMPRIMIR: 'imprimir';
LEER: 'leer';
VERDADERO: 'verdadero';
FALSO: 'falso';
ESTRUCTURA: 'estructura';

// Operators
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
EQUAL: '==';
NOTEQUAL: '!=';
LESS: '<';
GREATER: '>';
AND: '&&';
OR: '||';
NOT: '!';
INC: '++';
DEC: '--';
ASSIGN: '=';
ARROW: '->';

// Punctuation
COLON: ':';
COMMA: ',';
SEMICOLON: ';';
DOT: '.';
LEFT_PAREN: '(';
RIGHT_PAREN: ')';
LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';
LEFT_BRACE: '{';
RIGHT_BRACE: '}';

// Literals
INTEGER: [0-9]+;
FLOAT: [0-9]+ '.' [0-9]+;
CHAR: '\'' ( '\\' . | ~['\r\n\\] ) '\'';
STRING: '"' ( '\\' . | ~["\r\n\\] )* '"';
ID: [a-zA-Z_][a-zA-Z0-9_]*;

// Comments
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;

// Whitespace and tabs
WS: [ \t]+ -> skip;

// Catch the line break and tabs/spaces for the new line
NEWLINE: ('\r'? '\n')+ [ \t]* { processNewLine(getText()); };
