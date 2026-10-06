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
    : accessModifier? IDENTIFIER LPAREN parameterList? RPAREN block
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
    : (THIS | IDENTIFIER) (LBRACK expression RBRACK)* (DOT IDENTIFIER (LBRACK expression RBRACK)*)*
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
    : INT_LITERAL
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