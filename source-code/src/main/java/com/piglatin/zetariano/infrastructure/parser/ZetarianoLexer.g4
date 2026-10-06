lexer grammar ZetarianoLexer;

CLASS        : 'class' ;
PUBLIC       : 'public' ;
PRIVATE      : 'private' ;
PROTECTED    : 'protected' ;
EXTENDS      : 'extends' ;
OVERRIDE     : '@Override' ;
THIS         : 'this' ;
RETURN       : 'return' ;
IF           : 'if' ;
ELSE         : 'else' ;
FOR          : 'for' ;
WHILE        : 'while' ;
DO           : 'do' ;
SWITCH       : 'switch' ;
CASE         : 'case' ;
BREAK        : 'break' ;
DEFAULT      : 'default' ;
CONTINUE     : 'continue' ;
NEW          : 'new' ;
NULL         : 'null' ;

INT          : 'int' ;
CHAR         : 'char' ;
BOOLEAN      : 'boolean' ;
STRING       : 'String' ;
VOID         : 'void' ;

TRUE         : 'true' ;
FALSE        : 'false' ;

ASSIGN       : '=' ;
ADD_ASSIGN   : '+=' ;
SUB_ASSIGN   : '-=' ;
MUL_ASSIGN   : '*=' ;
DIV_ASSIGN   : '/=' ;
MOD_ASSIGN   : '%=' ;

PLUS         : '+' ;
MINUS        : '-' ;
STAR         : '*' ;
SLASH        : '/' ;
PERCENT      : '%' ;

INC          : '++' ;
DEC          : '--' ;

EQUAL        : '==' ;
NOTEQUAL     : '!=' ;
LE           : '<=' ;
GE           : '>=' ;
LT           : '<' ;
GT           : '>' ;

AND          : '&&' ;
OR           : '||' ;
NOT          : '!' ;

QUESTION     : '?' ;
COLON        : ':' ;

LPAREN       : '(' ;
RPAREN       : ')' ;
LBRACK       : '[' ;
RBRACK       : ']' ;
LBRACE       : '{' ;
RBRACE       : '}' ;
SEMI         : ';' ;
COMMA        : ',' ;
DOT          : '.' ;

INT_LITERAL    : [0-9]+ ;
CHAR_LITERAL   : '\'' ( '\\' [btnfr"'\\] | ~['\\\r\n] ) '\'' ;
STRING_LITERAL : '"' ( '\\' [btnfr"'\\] | ~["\\\r\n] )* '"' ;

IDENTIFIER     : [a-zA-Z_] [a-zA-Z0-9_]* ;

WS             : [ \t\r\n]+ -> skip ;
LINE_COMMENT   : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT  : '/*' .*? '*/' -> skip ;