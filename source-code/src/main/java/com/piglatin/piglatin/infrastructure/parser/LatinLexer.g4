lexer grammar LatinLexer;

// Rules of tokens (Terminals)

// Comments
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '##' (~'#' | '#' ~'#')* '##' -> skip;

// Aritmetic
PLUS: '+';
MINUS: '-';
MULT: '*';
SPLIT: '/';

// Declaration blocks
VARIABILES_INIT: 'VARIABILES>';
MAIOR_INIT: 'MAIOR>';
FINIS_EOF: 'FINIS';

// Relational
IDENTIC: '==';
DIFF: '!=';
MAJORTO: '>=';
MINORTO: '<=';
MINOR: '<';
MAJOR: '>';
ASSIGN: '=';

// Logical
AND: '&&';
OR: '||';

// Negation
NOT: 'non';

// Add/Subtract one unit
ADD: '++';
SUB: '--';

// Punctuation
COLON: ':';
SEMICOLON: ';';
COMMA: ',';
DOT: '.';

// Structural
LEFT_CLASP: '[';
RIGHT_CLASP: ']';
LEFT_BRACE: '{';
RIGHT_BRACE: '}';
LEFT_PAREN: '(';
RIGHT_PAREN: ')';

// Reserved Words
ESTO: 'esto';
NUMERUS: 'numerus';
TEXTUM: 'textum';
DECIMALIS: 'decimalis';
BOOL: 'bool';
LITTERA: 'littera';
VERUM: 'verum';
FALSUS: 'falsus';
SERIES: 'series';
FINIS: 'finis';
SI: 'si';
ALITER: 'aliter';
DUM: 'dum';
FACERE: 'facere';
PER: 'per';
PERGE: 'perge';
INTERRUMPE: 'interrumpe';
VARIABILES: 'VARIABILES';
MAIOR: 'MAIOR';
IMPORT: 'import';
NOVUS: 'novus';
LEERE: '<<';
IMPREMERE: '>>';

// Identifiers
ID: [a-zA-Z_][a-zA-Z0-9_]*;

// Literal Values
DECIMAL: [0-9]+ '.' [0-9]+;
INTEGER: [0-9]+;
STRING: '"' ( '\\' . | ~["\\] )* '"';
CHAR: '\'' ( '\\' . | ~['\\] ) '\'';

// Ignore
WS: [ \t\r\n]+ -> skip;







