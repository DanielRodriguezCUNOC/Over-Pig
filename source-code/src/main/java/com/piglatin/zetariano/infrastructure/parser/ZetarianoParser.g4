parser grammar ZetarianoParser;

options { tokenVocab = ZetarianoLexer; }

program
    : classDefinition EOF
    ;

classDefinition
    : PUBLIC? CLASS ID LEFT_BRACE globalDeclarations RIGHT_BRACE
    ;

globalDeclarations
    : globalDeclaration*
    ;

globalDeclaration
    : fieldDeclaration                                              #GlobalField
    | methodDeclaration                                             #GlobalMethod
    | constructorDeclaration                                        #GlobalConstructor
    ;

fieldDeclaration
    : type (LEFT_BRACKET RIGHT_BRACKET)* ID (ASSIGN (expression | arrayInitializer))? SEMICOLON
    ;

methodDeclaration
    : PUBLIC? (type | VOID) ID LEFT_PAREN parameterList? RIGHT_PAREN block
    ;

constructorDeclaration
    : PUBLIC? ID LEFT_PAREN parameterList? RIGHT_PAREN block
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : type ID
    | type (LEFT_BRACKET RIGHT_BRACKET)* ID
    ;

block
    : LEFT_BRACE mainInstructions RIGHT_BRACE
    | instruction
    ;

mainInstructions
    : instruction*
    ;

instruction
    : assignment SEMICOLON                                          #InstructionAssignment
    | readStatement SEMICOLON                                       #InstructionRead
    | printStatement SEMICOLON                                      #InstructionPrint
    | ifStatement                                                   #InstructionIf
    | switchStatement                                               #InstructionSwitch
    | whileStatement                                                #InstructionWhile
    | doWhileStatement SEMICOLON                                    #InstructionDoWhile
    | forStatement                                                  #InstructionFor
    | jumpStatement SEMICOLON                                       #InstructionJump
    | variableDeclaration SEMICOLON                                 #InstructionDeclaration
    | arrayDeclaration SEMICOLON                                    #InstructionArrayDeclaration
    | expression SEMICOLON                                          #InstructionExpression
    ;

variableDeclaration
    : type ID (ASSIGN expression)?
    ;

arrayDeclaration
    : type (LEFT_BRACKET RIGHT_BRACKET)+ ID (ASSIGN (expression | arrayInitializer))?
    ;

arrayInitializer
    : LEFT_BRACE (expression | arrayInitializer) (COMMA (expression | arrayInitializer))* RIGHT_BRACE
    ;

assignment
    : lvalue (ASSIGN | ADD_ASSIGN | SUB_ASSIGN | MULT_ASSIGN) expression
    | lvalue (INC | DEC)
    ;

readStatement
    : READLN LEFT_PAREN RIGHT_PAREN
    ;

printStatement
    : (PRINT | PRINTLN) LEFT_PAREN expression? RIGHT_PAREN
    ;

ifStatement
    : IF LEFT_PAREN expression RIGHT_PAREN block (ELSE block)?
    ;

switchStatement
    : SWITCH LEFT_PAREN expression RIGHT_PAREN LEFT_BRACE (CASE expression COLON mainInstructions)* (DEFAULT COLON mainInstructions)? RIGHT_BRACE
    ;

whileStatement
    : WHILE LEFT_PAREN expression RIGHT_PAREN block
    ;

doWhileStatement
    : DO block WHILE LEFT_PAREN expression RIGHT_PAREN
    ;

forStatement
    : FOR LEFT_PAREN (variableDeclaration | assignment)? SEMICOLON expression? SEMICOLON (assignment | expression)? RIGHT_PAREN block
    ;

jumpStatement
    : BREAK                                                         #JumpBreak
    | CONTINUE                                                      #JumpContinue
    | RETURN expression?                                            #JumpReturn
    ;

lvalue
    : ID
    | lvalue DOT ID
    | lvalue LEFT_BRACKET expression RIGHT_BRACKET
    ;

type
    : INT
    | DOUBLE
    | CHAR_TYPE
    | BOOLEAN
    | STRING_TYPE
    | ID // For class types
    ;

expression
    : LEFT_PAREN expression RIGHT_PAREN                             #ExprParen
    | (PLUS | MINUS | NOT | INC | DEC) expression                   #ExprUnary
    | expression (MULT | DIV | MOD) expression                      #ExprMultiplicative
    | expression (PLUS | MINUS) expression                          #ExprAdditive
    | expression (LESS | GREATER | LESSEQUAL | GREATEREQUAL) expression #ExprRelational
    | expression (EQUAL | NOTEQUAL) expression                      #ExprEquality
    | expression AND expression                                     #ExprAnd
    | expression OR expression                                      #ExprOr
    | expression QUESTION expression COLON expression               #ExprTernary
    | primary                                                       #ExprPrimary
    ;

primary
    : literal                                                       #PrimaryLiteral
    | NEW ID LEFT_PAREN argumentList? RIGHT_PAREN                  #PrimaryNewObject
    | NEW type (LEFT_BRACKET expression RIGHT_BRACKET)+            #PrimaryNewArray
    | lvalue (LEFT_PAREN argumentList? RIGHT_PAREN)?               #PrimaryLvalueOrCall
    ;

literal
    : INTEGER
    | DECIMAL
    | CHAR
    | STRING
    | TRUE
    | FALSE
    | NULL
    ;

argumentList
    : expression (COMMA expression)*
    ;
