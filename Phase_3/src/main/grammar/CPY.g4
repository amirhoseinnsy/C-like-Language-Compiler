grammar MiniC;

compilationUnit
    : translationUnit? EOF ;

translationUnit
    : externalDeclaration+ ;

externalDeclaration
    : functionDefinition | declaration | Semi ; // stray ;

functionDefinition
    : declarationSpecifiers? declarator declarationList? compoundStatement ;

declarationList
    : declaration+ ;

expression
  : Identifier | Constant | StringLiteral+
  | LeftParen expression RightParen
  | LeftParen typeName RightParen LeftBrace initializerList Comma? RightBrace
  | expression LeftBracket expression RightBracket                                // Array indexing
  | expression LeftParen argumentExpressionList? RightParen                       // Function call
  | expression PlusPlus                                                           // Postfix increment
  | expression MinusMinus                                                         // Postfix decrement
  | (PlusPlus  | MinusMinus | Sizeof)* (                                          // Prefix operators (zero or more)
         Identifier
       | Constant
       | StringLiteral+
       | LeftParen expression RightParen
       | LeftParen typeName RightParen LeftBrace initializerList Comma? RightBrace
       | unaryOperator castExpression
       | Sizeof LeftParen typeName RightParen
    )
  | LeftParen typeName RightParen castExpression                                  // Cast expression
  | expression (Star | Div | Mod) expression                                      // Multiplicative
  | expression (Plus | Minus) expression                                          // Additive
  | expression (LeftShift | RightShift) expression                                // Shift
  | expression (Less | Greater | LessEqual | GreaterEqual) expression             // Relational
  | expression (Equal | NotEqual) expression                                      // Equality
  | expression And expression                                                     // Bitwise AND
  | expression Xor expression                                                     // Bitwise XOR
  | expression Or expression                                                      // Bitwise OR
  | expression AndAnd expression                                                  // Logical AND
  | expression OrOr expression                                                    // Logical OR
  | expression Question expression Colon expression                               // Conditional operator
  | expression assignmentOperator expression                                      // Assignment
  | expression (Comma expression)+ ;                                              // Comma operator

argumentExpressionList
  : expression (Comma expression)* ;

unaryOperator
  : And | Star | Plus | Minus | Tilde | Not ;

castExpression
  : LeftParen typeName RightParen castExpression | expression | DigitSequence ;

assignmentOperator
  : Assign | StarAssign | DivAssign | ModAssign | PlusAssign | MinusAssign | LeftShiftAssign | RightShiftAssign | AndAssign | XorAssign | OrAssign ;

declaration
    : declarationSpecifiers initDeclaratorList? Semi ;

declarationSpecifiers
    : declarationSpecifier+ ;

declarationSpecifier
    : Typedef | typeSpecifier | Const ;

initDeclaratorList
    : initDeclarator (Comma initDeclarator)* ;

initDeclarator
    : declarator (Assign initializer)? ;

typeSpecifier
    : Void | Char | Short | Int | Long | Float | Double | Signed | Unsigned | Bool | Identifier ;

specifierQualifierList
    : (typeSpecifier | Const) specifierQualifierList? ;

declarator
    : pointer? directDeclarator ;

directDeclarator
    : Identifier
    | LeftParen declarator RightParen
    | directDeclarator LeftBracket expression? RightBracket
    | directDeclarator LeftParen  (parameterList | identifierList?) RightParen ;

pointer
    : ((Star) (Const+)?)+ ;

parameterList
    : parameterDeclaration (Comma parameterDeclaration)* ;

parameterDeclaration
    : declarationSpecifiers (declarator | abstractDeclarator?) ;

identifierList
    : Identifier (Comma Identifier)* ;

typeName
    : specifierQualifierList abstractDeclarator? ;

abstractDeclarator
    : pointer | pointer? directAbstractDeclarator ;

directAbstractDeclarator
    : LeftBracket expression? RightBracket
    | LeftParen  (abstractDeclarator | parameterList?) RightParen
    | directAbstractDeclarator LeftBracket expression? RightBracket
    | directAbstractDeclarator LeftParen parameterList? RightParen ;

initializer
    : expression | LeftBrace initializerList Comma? RightBrace ;

initializerList
    : designation? initializer (Comma designation? initializer)* ;

designation
    : designator+ Assign ;

designator
    : LeftBracket expression RightBracket | Dot Identifier ;

statement
    : compoundStatement | expressionStatement | selectionStatement | iterationStatement | jumpStatement ;

compoundStatement
    : LeftBrace (blockItem+)? RightBrace ;

blockItem
    : statement | declaration ;

expressionStatement
    : expression? Semi ;

selectionStatement
    : If LeftParen expression RightParen statement (Else statement)? ;

iterationStatement
    : While LeftParen expression RightParen statement
    | Do statement While LeftParen expression RightParen Semi
    | For LeftParen forCondition RightParen statement ;

forCondition
    : (forDeclaration | expression?) Semi forExpression? Semi forExpression? ;

forDeclaration
    : declarationSpecifiers initDeclaratorList? ;

forExpression
    : expression (Comma expression)* ;

jumpStatement
    : ( Continue | Break | Return expression? ) Semi ;

Break                 : 'break'                 ;
Char                  : 'char'                  ;
Const                 : 'const'                 ;
Continue              : 'continue'              ;
Do                    : 'do'                    ;
Double                : 'double'                ;
Else                  : 'else'                  ;
Float                 : 'float'                 ;
For                   : 'for'                   ;
If                    : 'if'                    ;
Int                   : 'int'                   ;
Long                  : 'long'                  ;
Return                : 'return'                ;
Short                 : 'short'                 ;
Signed                : 'signed'                ;
Sizeof                : 'sizeof'                ;
Switch                : 'switch'                ;
Typedef               : 'typedef'               ;
Unsigned              : 'unsigned'              ;
Void                  : 'void'                  ;
While                 : 'while'                 ;
Bool                  : 'bool'                  ;
LeftParen             : '('                     ;
RightParen            : ')'                     ;
LeftBracket           : '['                     ;
RightBracket          : ']'                     ;
LeftBrace             : '{'                     ;
RightBrace            : '}'                     ;
Less                  : '<'                     ;
LessEqual             : '<='                    ;
Greater               : '>'                     ;
GreaterEqual          : '>='                    ;
LeftShift             : '<<'                    ;
RightShift            : '>>'                    ;
Plus                  : '+'                     ;
PlusPlus              : '++'                    ;
Minus                 : '-'                     ;
MinusMinus            : '--'                    ;
Star                  : '*'                     ;
Div                   : '/'                     ;
Mod                   : '%'                     ;
And                   : '&'                     ;
Or                    : '|'                     ;
AndAnd                : '&&'                    ;
OrOr                  : '||'                    ;
Xor                   : '^'                     ;
Not                   : '!'                     ;
Tilde                 : '~'                     ;
Question              : '?'                     ;
Colon                 : ':'                     ;
Semi                  : ';'                     ;
Comma                 : ','                     ;
Assign                : '='                     ;
StarAssign            : '*='                    ;
DivAssign             : '/='                    ;
ModAssign             : '%='                    ;
PlusAssign            : '+='                    ;
MinusAssign           : '-='                    ;
LeftShiftAssign       : '<<='                   ;
RightShiftAssign      : '>>='                   ;
AndAssign             : '&='                    ;
XorAssign             : '^='                    ;
OrAssign              : '|='                    ;
Equal                 : '=='                    ;
NotEqual              : '!='                    ;
Arrow                 : '->'                    ;
Dot                   : '.'                     ;

Identifier
    : IdentifierNondigit (IdentifierNondigit | Digit)* ;

fragment IdentifierNondigit
    : Nondigit | UniversalCharacterName ;

fragment Nondigit
    : [a-zA-Z_] ;

fragment Digit
    : [0-9] ;

fragment UniversalCharacterName
    : '\\u' HexQuad | '\\U' HexQuad HexQuad ;

fragment HexQuad
    : HexadecimalDigit HexadecimalDigit HexadecimalDigit HexadecimalDigit ;

Constant
    : IntegerConstant | FloatingConstant | CharacterConstant ;

fragment IntegerConstant
    : DecimalConstant IntegerSuffix?
    | OctalConstant IntegerSuffix?
    | HexadecimalConstant IntegerSuffix?
    | BinaryConstant ;

fragment BinaryConstant
    : '0' [bB] [0-1]+ ;

fragment DecimalConstant
    : NonzeroDigit Digit* ;

fragment OctalConstant
    : '0' OctalDigit* ;

fragment HexadecimalConstant
    : HexadecimalPrefix HexadecimalDigit+ ;

fragment HexadecimalPrefix
    : '0' [xX] ;

fragment NonzeroDigit
    : [1-9] ;

fragment OctalDigit
    : [0-7] ;

fragment HexadecimalDigit
    : [0-9a-fA-F] ;

fragment IntegerSuffix
    : UnsignedSuffix LongSuffix? | UnsignedSuffix LongLongSuffix | LongSuffix UnsignedSuffix? | LongLongSuffix UnsignedSuffix? ;

fragment UnsignedSuffix
    : [uU] ;

fragment LongSuffix
    : [lL] ;

fragment LongLongSuffix
    : 'll' | 'LL' ;

fragment FloatingConstant
    : DecimalFloatingConstant | HexadecimalFloatingConstant ;

fragment DecimalFloatingConstant
    : FractionalConstant ExponentPart? FloatingSuffix? | DigitSequence ExponentPart FloatingSuffix? ;

fragment HexadecimalFloatingConstant
    : HexadecimalPrefix (HexadecimalFractionalConstant | HexadecimalDigitSequence) BinaryExponentPart FloatingSuffix? ;

fragment FractionalConstant
    : DigitSequence? Dot DigitSequence | DigitSequence Dot ;

fragment ExponentPart
    : [eE] Sign? DigitSequence ;

fragment Sign
    : [+-] ;

DigitSequence
    : Digit+ ;

fragment HexadecimalFractionalConstant
    : HexadecimalDigitSequence? Dot HexadecimalDigitSequence | HexadecimalDigitSequence Dot ;

fragment BinaryExponentPart
    : [pP] Sign? DigitSequence ;

fragment HexadecimalDigitSequence
    : HexadecimalDigit+ ;

fragment FloatingSuffix
    : [flFL] ;

fragment CharacterConstant
    : '\'' CCharSequence '\'' | 'L\'' CCharSequence '\''| 'u\'' CCharSequence '\'' | 'U\'' CCharSequence '\''
    ;

fragment CCharSequence
    : CChar+ ;

fragment CChar
    : ~['\\\r\n] | EscapeSequence ;

fragment EscapeSequence
    : SimpleEscapeSequence | OctalEscapeSequence | HexadecimalEscapeSequence | UniversalCharacterName ;

fragment SimpleEscapeSequence
    : '\\' ['"?abfnrtv\\] ;

fragment OctalEscapeSequence
    : '\\' OctalDigit OctalDigit? OctalDigit? ;

fragment HexadecimalEscapeSequence
    : '\\x' HexadecimalDigit+ ;

StringLiteral
    : EncodingPrefix? '"' SCharSequence? '"' ;

fragment EncodingPrefix
    : 'u8' | 'u' | 'U' | 'L' ;

fragment SCharSequence
    : SChar+ ;

fragment SChar
    : ~["\\\r\n] | EscapeSequence | '\\\n' | '\\\r\n' ;

MultiLineMacro
    : '#' (~[\n]*? '\\' '\r'? '\n')+ ~ [\n]+ -> channel(HIDDEN) ;

Directive
    : '#' ~[\n]* -> channel(HIDDEN) ;

Whitespace
    : [ \t]+ -> channel(HIDDEN) ;

Newline
    : ('\r' '\n'? | '\n') -> channel(HIDDEN) ;

BlockComment
    : '/*' .*? '*/' -> channel(HIDDEN) ;

LineComment
    : '//' ~[\r\n]* -> channel(HIDDEN) ;