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
    : ESTO ID COLON (type (expression)? | expression) SEMICOLON?
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
    : booleanExpression                              #ExprBoolean
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