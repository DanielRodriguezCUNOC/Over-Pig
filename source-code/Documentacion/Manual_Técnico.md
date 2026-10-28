# Manual Técnico - Traductor Codex Latinux

### _Universidad San Carlos de Guatemala - Division Ciencias de la Ingeniería - Centro Universitario de Occidente (CUNOC)_

### _Organización de Lenguajes y Compiladores 2_

---

## 1. Tecnologías utilizadas

- ANTLR4 -> generación de gramatica
- Intellij IDE versión 2025.2.6.2 (Para mayor compatibilidad con plugin de ANTLR4 en Intellij IDE)
- Java version 21.0.11-temurin
- Maven version 3.9.12 (non_canonical)
- Sistema Operativo: Solus Linux

## 2. Arquitectura del Sistema

El traductor está diseñado siguiendo el patrón clásico de pipeline para procesadores de lenguajes. El flujo de procesamiento se divide en etapas desacopladas que transforman el código fuente a el lenguje destino (PigLatin).

```text
[Código Fuente] ──> [ Analizador Léxico ] ──> (Lista de Tokens)
                            │
                            ▼
                    [Analizador Sintáctico] ──> (Lista de errores)
                            │
                            ▼
                    [ Analizador Semántico ] ──> (Lista de errores)
                            │
                            ▼
                    [Resultado / Traducción]
```

---

## 3. Diagrama de clases

Este diagrama representa el AST personalizado (no el generado por ANTRL4). Puede encontrar el diagrama dentro del directorio resources.
![Diagrama](resources/AST_Diagram.png)

## 4. Especificaciones del Lenguaje

- ### **Palabras reservadas y Símbolos**

```jflex

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
NULL: 'null';
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

```

- ### **Gramática:**
  Esta gramática está basada en el lenguaje de **Latín**, es un lenguaje de tipado estático y secuencial.

```bnf
(*
  Los terminales en MAYÚSCULAS corresponden a tokens del lexer.
*)

parser grammar LatinParser;

options { tokenVocab = LatinLexer; }

// Start Program
program
    : importList?
      (VARIABILES_INIT globalDeclarations)?
      MAIOR_INIT mainInstructions
      (FINIS_EOF | FINIS) SEMICOLON? EOF
    ;

importList
    : importStatement+
    ;

importStatement
    : IMPORT ID (DOT ID)* SEMICOLON?
    ;

globalDeclarations
    : ( globalDeclaration )*
    ;

globalDeclaration
    : declaration                                                   #GlobalDeclarationVariable
    | arrayDeclaration                                              #GlobalDeclarationArray
    ;

mainInstructions
    : instruction*
    ;

instruction
    : assignment                                                      #InstructionAssignment
    | readStatement                                                   #InstructionRead
    | printStatement                                                  #InstructionPrint
    | ifStatement                                                     #InstructionIf
    | whileStatement                                                  #InstructionWhile
    | doWhileStatement                                                #InstructionDoWhile
    | forStatement                                                    #InstructionFor
    | jumpStatement                                                   #InstructionJump
    | declaration                                                     #InstructionDeclaration
    | arrayDeclaration                                                #InstructionArrayDeclaration
    | expression SEMICOLON?                                           #InstructionExpression
    ;

// Variable Declaration
declaration
    : ESTO ID COLON type (expression)? SEMICOLON?                       #DeclStandard
    | ESTO ID COLON expression COLON type SEMICOLON?                    #DeclExplicitType
    | ESTO ID COLON newInstance SEMICOLON?                              #DeclNewInstance
    | ESTO ID COLON booleanLiteral SEMICOLON?                           #DeclBoolean
    ;

arrayDeclaration
    : SERIES ID LEFT_CLASP expression RIGHT_CLASP COLON type (arrayLiteral)? SEMICOLON?
    ;

arrayLiteral
    : LEFT_BRACE expression (COMMA expression)* RIGHT_BRACE
    ;


block
    : LEFT_BRACE instruction* RIGHT_BRACE
    ;

ifStatement
    : SI LEFT_PAREN booleanExpression RIGHT_PAREN block
      (elseIfClause)*
      (elseClause)?
      FINIS SEMICOLON?
    ;

elseIfClause
    : ALITER LEFT_PAREN booleanExpression RIGHT_PAREN block
    ;

elseClause
    : ALITER block
    ;

whileStatement
    : DUM LEFT_PAREN booleanExpression RIGHT_PAREN block FINIS SEMICOLON?
    ;

doWhileStatement
    : FACERE block DUM LEFT_PAREN booleanExpression RIGHT_PAREN SEMICOLON?
    ;

forStatement
    : PER LEFT_PAREN forInit SEMICOLON booleanExpression SEMICOLON forUpdate? RIGHT_PAREN block
    ;

forInit
    : ESTO ID COLON type (expression)?              #ForInitDeclaration
    | assignment                                    #ForInitAssignment
    |                                               #ForInitEmpty
    ;

forUpdate
    : assignment                                    #ForUpdateAssignment
    | expression                                    #ForUpdateExpression
    ;

jumpStatement
    : PERGE SEMICOLON?                              #JumpContinue
    | INTERRUMPE SEMICOLON?                         #JumpBreak
    ;

// Object instantiation
newInstance
    : NOVUS ID LEFT_PAREN argumentList? RIGHT_PAREN
    ;

// Read statement
readStatement
    : LEERE SEMICOLON?                              #ReadSimple
    | lvalue LEERE SEMICOLON?                       #ReadLvalue
    ;

// Print statement
printStatement
    : IMPREMERE printItem (IMPREMERE printItem)* SEMICOLON?
    ;

printItem
    : STRING                                        #PrintString
    | expression                                    #PrintExpression
    ;

// Lvalues
lvalue
    : ID (lvalueSuffix)*
    ;

lvalueSuffix
    : DOT ID                                         #FieldAccess
    | LEFT_CLASP expression RIGHT_CLASP              #IndexAccess
    ;

// Assignment
assignment
    : lvalue ASSIGN expression SEMICOLON?
    ;

// Types
type
    : NUMERUS                                        #TypeNumerus
    | TEXTUM                                         #TypeTextum
    | DECIMALIS                                      #TypeDecimalis
    | LITTERA                                        #TypeLittera
    | BOOL                                           #TypeBool
    | ID                                             #TypeID
    ;

// Arguments
argumentList
    : expression (COMMA expression)*
    ;

// Expressions
expression
    : NULL                                           #ExprNull
    | booleanExpression                              #ExprBoolean
    | numericExpression                              #ExprNumeric
    | stringExpression                               #ExprString
    | arrayLiteral                                   #ExprArrayLiteral
    | newInstance                                    #ExprNewInstance
    ;

// Boolean expressions
booleanExpression
    : booleanOrExpression
    ;

booleanOrExpression
    : booleanAndExpression (OR booleanAndExpression)*
    ;

booleanAndExpression
    : comparisonExpression (AND comparisonExpression)*
    ;

comparisonExpression
    : NOT comparisonExpression                                                   #CompNot
    | comparisonOperand (relationalOp comparisonOperand)?                       #CompCompare
    | LEFT_PAREN booleanExpression RIGHT_PAREN                                  #CompParen
    ;

comparisonOperand
    : numericExpression                                                         #CompNumeric
    | booleanLiteral                                                            #CompBoolean
    | stringExpression                                                          #CompString
    | NULL                                                                      #CompNull
    ;

relationalOp
    : IDENTIC
    | DIFF
    | MINOR
    | MAJOR
    | MINORTO
    | MAJORTO
    ;

booleanLiteral
    : VERUM
    | FALSUS
    ;

// Numeric expressions
numericExpression
    : additiveExpression
    ;

additiveExpression
    : multiplicativeExpression ((PLUS | MINUS) multiplicativeExpression)*
    ;

multiplicativeExpression
    : unaryExpression ((MULT | SPLIT) unaryExpression)*
    ;

unaryExpression
    : (PLUS | MINUS | ADD | SUB) unaryExpression       #UnaryPrefix
    | primaryNumeric                                   #UnaryPrimary
    ;

primaryNumeric
    : numericLiteral                                   #PrimaryNumLiteral
    | ID (attributeAccess)* (ADD | SUB)?               #PrimaryNumId
    | LEFT_PAREN numericExpression RIGHT_PAREN          #PrimaryNumParen
    ;

numericLiteral
    : INTEGER
    | DECIMAL
    ;

// String expressions
stringExpression
    : stringAdditiveExpression
    ;

stringAdditiveExpression
    : stringAdditiveItem (PLUS stringAdditiveItem)*
    ;

stringAdditiveItem
    : stringPrimary                                   #StringItemPrimary
    | numericExpression                               #StringItemNumeric
    | booleanLiteral                                  #StringItemBoolean
    ;

stringPrimary
    : STRING                                          #StringPrimString
    | CHAR                                            #StringPrimChar
    | ID (attributeAccess)*                           #StringPrimId
    ;

// Attribute access
attributeAccess
    : DOT ID                                          #AttrField
    | LEFT_CLASP expression RIGHT_CLASP                #AttrIndex
    | LEFT_PAREN argumentList? RIGHT_PAREN             #AttrCall
    ;
```

## 5. **Tabla de Compatibilidad de Tipos**

| Tipo      | Tipo      | Conversión |
| --------- | --------- | ---------- |
| NUMERUS   | NUMERUS   | NUMERUS    |
| DECIMALIS | DECIMALIS | DECIMALIS  |
| NUMERUS   | DECIMALIS | DECIMALIS  |
| TEXTUM    | ANYTHING  | TEXTUM     |

## 6. **Lenguaje Y**

```
jflex
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

```

```
bnf

parser grammar YParser;

options { tokenVocab = YLexer; }

program
    : NEWLINE* (ESTRUCTURAS_HEADER globalDeclaration*)? FUNCIONES_HEADER NEWLINE* functionDeclaration* EOF
    ;

globalDeclaration
    : ESTRUCTURA ID COLON structBlock                                     #GlobalStructure
    ;

structBlock
    : NEWLINE INDENT (structField NEWLINE*)? DEDENT
    ;

structField
    : type ID (LEFT_BRACKET INTEGER RIGHT_BRACKET)?                      #FieldSimpleOrArray
    ;

mainInstructions
    : FUNCIONES_HEADER functionDeclaration*
    ;

functionDeclaration
    : DEFINIR ID LEFT_PAREN parameterList? RIGHT_PAREN (ARROW type)? COLON block
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : type ID                                                       #ParamSimple
    | LEFT_BRACKET RIGHT_BRACKET type ID                            #ParamArray
    | LEFT_BRACE RIGHT_BRACE type ID                                #ParamStruct
    ;

block
    : NEWLINE? INDENT (instruction NEWLINE*)+ DEDENT
    ;

instruction
    : assignment                                                      #InstructionAssignment
    | printStatement                                                  #InstructionPrint
    | ifStatement                                                     #InstructionIf
    | chooseStatement                                                 #InstructionChoose
    | whileStatement                                                  #InstructionWhile
    | doWhileStatement                                                #InstructionDoWhile
    | forStatement                                                    #InstructionFor
    | jumpStatement                                                   #InstructionJump
    | variableDeclaration                                             #InstructionDeclaration
    | arrayDeclaration                                                #InstructionArrayDeclaration
    | expression                                                      #InstructionExpression
    ;

variableDeclaration
    : type ID (ASSIGN expression)?
    ;

arrayDeclaration
    : type ID LEFT_BRACKET expression RIGHT_BRACKET (ASSIGN expression)?
    | type ID (LEFT_BRACKET expression RIGHT_BRACKET)+
    ;

arrayLiteral
    : LEFT_BRACE (expression (COMMA expression)*)? RIGHT_BRACE
    ;

assignment
    : lvalue ASSIGN expression                                        #AssigmentNormal
    | lvalue (INC | DEC)                                              #AssigmentIncDec
    ;

printStatement
    : IMPRIMIR LEFT_PAREN expression (PLUS expression)* RIGHT_PAREN
    ;

ifStatement
    : SI LEFT_PAREN expression RIGHT_PAREN ENTONCES block
      (SINO LEFT_PAREN expression RIGHT_PAREN ENTONCES block)*
      (CONTRARIO block)?
    ;

chooseStatement
    : ELEGIR LEFT_PAREN expression RIGHT_PAREN COLON NEWLINE? INDENT (CASO expression COLON block)+ (SIEMPRE COLON block)? DEDENT
    ;

whileStatement
    : MIENTRAS LEFT_PAREN expression RIGHT_PAREN HACER block
    ;

doWhileStatement
    : HACER COLON block MIENTRAS LEFT_PAREN expression RIGHT_PAREN
    ;

forStatement
    : PARA LEFT_PAREN (variableDeclaration | assignment)? SEMICOLON expression? SEMICOLON (assignment)? RIGHT_PAREN COLON block
    ;

jumpStatement
    : ROMPER                                                         #JumpBreak
    | CONTINUAR                                                      #JumpContinue
    | RETORNAR expression?                                           #JumpReturn
    ;

lvalue
    : ID
    | lvalue DOT ID
    | lvalue LEFT_BRACKET expression RIGHT_BRACKET
    ;

type
    : ENTERO
    | FLOTANTE
    | CARACTER
    | CADENA
    | BOOL_TYPE
    | ID // For structures
    ;

expression
    : primary                                                       #ExprPrimary
    | (NOT | MINUS) expression                                      #ExprUnary
    | expression (MULT | DIV) expression                            #ExprMultiplicative
    | expression (PLUS | MINUS) expression                          #ExprAdditive
    | expression (LESS | GREATER | EQUAL | NOTEQUAL) expression     #ExprRelational
    | expression (AND | OR) expression                              #ExprLogical
    | arrayLiteral                                                  #ExprArrayLiteral
    ;


primary
    : literal                                                       #PrimaryLiteral
    | lvalue                                                        #PrimaryLvalue
    | ID LEFT_PAREN argumentList? RIGHT_PAREN                       #PrimaryFunctionCall
    | LEER LEFT_PAREN RIGHT_PAREN                                   #PrimaryRead
    | LEFT_PAREN expression RIGHT_PAREN                             #PrimaryParen
    ;

literal
    : INTEGER
    | FLOAT
    | CHAR
    | STRING
    | VERDADERO
    | FALSO
    ;

argumentList
    : expression (COMMA expression)*
    ;

```

## 7. **Lenguaje Zetariano**

```jflex

lexer grammar ZetarianoLexer;

CLASS        : 'class' ;
PUBLIC       : 'public' ;
PRIVATE      : 'private' ;
PROTECTED    : 'protected' ;
EXTENDS      : 'extends' ;
OVERRIDE     : '@Override' ;
THIS         : 'this' ;
SUPER        : 'super' ;
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
FLOAT        : 'float' ;
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

DOUBLE_LITERAL : [0-9]+ '.' [0-9]+ ;
INT_LITERAL    : [0-9]+ ;
CHAR_LITERAL   : '\'' ( '\\' [btnfr"'\\] | ~['\\\r\n] ) '\'' ;
STRING_LITERAL : '"' ( '\\' [btnfr"'\\] | ~["\\\r\n] )* '"' ;

IDENTIFIER     : [a-zA-Z_] [a-zA-Z0-9_]* ;

WS             : [ \t\r\n]+ -> skip ;
LINE_COMMENT   : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT  : '/*' .*? '*/' -> skip ;
```

```
bnf

parser grammar ZetarianoParser;

options { tokenVocab = ZetarianoLexer; }

compilationUnit
    : classDeclaration* EOF
    ;

classDeclaration
    : accessModifier? CLASS IDENTIFIER (EXTENDS IDENTIFIER)? LBRACE classBodyMember* RBRACE
    ;

classBodyMember
    : fieldDeclaration                                                    #FieldDeclarationClassBodyMember
    | constructorDeclaration                                              #ConstructorDeclarationClassBodyMember
    | methodDeclaration                                                   #MethodDeclarationClassBodyMember
    ;

fieldDeclaration
    : accessModifier? type IDENTIFIER (ASSIGN expression)? SEMI
    ;

constructorDeclaration
    : accessModifier? IDENTIFIER LPAREN parameterList? RPAREN LBRACE (explicitConstructorInvocation SEMI)? statement* RBRACE
    ;

explicitConstructorInvocation
    : THIS LPAREN expressionList? RPAREN                                #ThisConstructorCall
    | SUPER LPAREN expressionList? RPAREN                               #SuperConstructorCall
    ;

methodDeclaration
    : OVERRIDE? accessModifier? typeOrVoid IDENTIFIER LPAREN parameterList? RPAREN block
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : type IDENTIFIER
    ;

typeOrVoid
    : VOID
    | type
    ;

type
    : primitiveType (LBRACK RBRACK)*
    | IDENTIFIER (LBRACK RBRACK)*
    ;

primitiveType
    : INT
    | FLOAT
    | CHAR
    | BOOLEAN
    | STRING
    ;

block
    : LBRACE statement* RBRACE
    ;

statement
    : block                                                             #StatementBlock
    | localVariableDeclaration SEMI                                     #LocalVariableDeclarationStatement
    | assignmentStatement SEMI                                          #StatementAssignment
    | expressionStatement SEMI                                          #StatementExpression
    | ifStatement                                                       #StatementIf
    | whileStatement                                                    #StatementWhile
    | doWhileStatement SEMI                                             #StatementDoWhile
    | forStatement                                                      #StatementFor
    | switchStatement                                                   #StatementSwitch
    | returnStatement SEMI                                              #StatementReturn
    | breakStatement SEMI                                               #StatementBreak
    | continueStatement SEMI                                            #StatementContinue
    | SEMI                                                              #StatementEmpty
    ;

localVariableDeclaration
    : type IDENTIFIER (ASSIGN expression)?
    ;

assignmentStatement
    : target assignmentOperator expression
    ;

assignmentOperator
    : ASSIGN                                                        #AssignmentOperatorAssign
    | ADD_ASSIGN                                                    #AssignmentOperatorAddAssign
    | SUB_ASSIGN                                                    #AssignmentOperatorSubAssign
    | MUL_ASSIGN                                                    #AssignmentOperatorMulAssign
    | DIV_ASSIGN                                                    #AssignmentOperatorDivAssign
    | MOD_ASSIGN                                                    #AssignmentOperatorModAssign
    ;

target
    : (THIS | SUPER | IDENTIFIER) (LBRACK expression RBRACK)* (DOT IDENTIFIER (LBRACK expression RBRACK)*)*
    ;

expressionStatement
    : expression
    ;

ifStatement
    : IF LPAREN expression RPAREN statement (ELSE statement)?
    ;

whileStatement
    : WHILE LPAREN expression RPAREN statement
    ;

doWhileStatement
    : DO statement WHILE LPAREN expression RPAREN
    ;

forStatement
    : FOR LPAREN forInit? SEMI expression? SEMI forUpdate? RPAREN statement
    ;

forInit
    : localVariableDeclaration
    | assignmentStatement (COMMA assignmentStatement)*
    | expression (COMMA expression)*
    ;

forUpdate
    : assignmentStatement (COMMA assignmentStatement)*
    | expression (COMMA expression)*
    ;

switchStatement
    : SWITCH LPAREN expression RPAREN LBRACE switchBlockStatementGroup* RBRACE
    ;

switchBlockStatementGroup
    : switchLabel+ statement*
    ;

switchLabel
    : CASE expression COLON
    | DEFAULT COLON
    ;

returnStatement
    : RETURN expression?
    ;

breakStatement
    : BREAK
    ;

continueStatement
    : CONTINUE
    ;

expression
    : ternaryExpression
    ;

ternaryExpression
    : logicalOrExpression (QUESTION expression COLON ternaryExpression)?
    ;

logicalOrExpression
    : logicalAndExpression (OR logicalAndExpression)*
    ;

logicalAndExpression
    : equalityExpression (AND equalityExpression)*
    ;

equalityExpression
    : relationalExpression ((EQUAL | NOTEQUAL) relationalExpression)*
    ;

relationalExpression
    : additiveExpression ((LT | LE | GT | GE) additiveExpression)*
    ;

additiveExpression
    : multiplicativeExpression ((PLUS | MINUS) multiplicativeExpression)*
    ;

multiplicativeExpression
    : unaryExpression ((STAR | SLASH | PERCENT) unaryExpression)*
    ;

unaryExpression
    : PLUS unaryExpression                                          #UnaryPlus
    | MINUS unaryExpression                                         #UnaryMinus
    | NOT unaryExpression                                           #UnaryNot
    | INC target                                                    #UnaryPreIncrement
    | DEC target                                                    #UnaryPreDecrement
    | postfixExpression                                             #UnaryPostfix
    ;

postfixExpression
    : primary (INC | DEC)?
    ;

primary
    : primaryCore primarySuffix*
    ;

primaryCore
    : LPAREN expression RPAREN                          #ParenthesizedExpression
    | literal                                           #LiteralPrimary
    | allocationExpression                              #AllocationExpressionPrimary
    | IDENTIFIER LPAREN expressionList? RPAREN          #DirectMethodCall
    | IDENTIFIER                                        #VariableOrField
    | THIS                                              #ThisPrimary
    | SUPER                                             #SuperPrimary
    ;

primarySuffix
    : DOT IDENTIFIER LPAREN expressionList? RPAREN                              #MethodCallSuffix
    | DOT IDENTIFIER                                                            #FieldAccessSuffix
    | LBRACK expression RBRACK                                                  #ArrayAccessSuffix
    ;

allocationExpression
    : NEW primitiveType arrayDimensions                                         #AllocationExpressionArray
    | NEW IDENTIFIER (arrayDimensions | LPAREN expressionList? RPAREN)          #AllocationExpressionObject
    ;

arrayDimensions
    : (LBRACK expression RBRACK)+ (LBRACK RBRACK)*
    ;

expressionList
    : expression (COMMA expression)*
    ;

literal
    : DOUBLE_LITERAL
    | INT_LITERAL
    | CHAR_LITERAL
    | STRING_LITERAL
    | TRUE
    | FALSE
    | NULL
    ;

accessModifier
    : PUBLIC
    | PRIVATE
    | PROTECTED
    ;
```
