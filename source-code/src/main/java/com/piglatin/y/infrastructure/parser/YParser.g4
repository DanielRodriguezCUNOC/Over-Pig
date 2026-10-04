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
