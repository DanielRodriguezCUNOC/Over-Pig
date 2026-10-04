lexer grammar ZetarianoLexer;

// Keywords
PUBLIC: 'public';
CLASS: 'class';
INT: 'int';
DOUBLE: 'double';
CHAR_TYPE: 'char';
BOOLEAN: 'boolean';
STRING_TYPE: 'String';
VOID: 'void';
NEW: 'new';
NULL: 'null';
IF: 'if';
ELSE: 'else';
SWITCH: 'switch';
CASE: 'case';
DEFAULT: 'default';
BREAK: 'break';
CONTINUE: 'continue';
RETURN: 'return';
FOR: 'for';
WHILE: 'while';
DO: 'do';
PRINTLN: 'println';
PRINT: 'print';
READLN: 'readln';
TRUE: 'true';
FALSE: 'false';

// Operators
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
MOD: '%';
INC: '++';
DEC: '--';
EQUAL: '==';
NOTEQUAL: '!=';
LESS: '<';
GREATER: '>';
LESSEQUAL: '<=';
GREATEREQUAL: '>=';
AND: '&&';
OR: '||';
NOT: '!';
ASSIGN: '=';
ADD_ASSIGN: '+=';
SUB_ASSIGN: '-=';
MULT_ASSIGN: '*=';
QUESTION: '?';
COLON: ':';

// Punctuation
SEMICOLON: ';';
COMMA: ',';
DOT: '.';
LEFT_PAREN: '(';
RIGHT_PAREN: ')';
LEFT_BRACE: '{';
RIGHT_BRACE: '}';
LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';

// Literals
INTEGER: [0-9]+;
DECIMAL: [0-9]+ '.' [0-9]+;
CHAR: '\'' ( '\\' . | ~['\\] ) '\'';
STRING: '"' ( '\\' . | ~["\\] )* '"';
ID: [a-zA-Z_][a-zA-Z0-9_]*;

// Comments
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;

// Ignore
WS: [ \t\r\n]+ -> skip;
