grammar SimpleLang;

@header{
    import main.ast.nodes.*;
    import main.ast.nodes.expr.*;
    import main.ast.nodes.Stmt.*;
}


program returns[Program programRet]
    : (tu=translationUnit { $programRet = new Program(); $programRet.setTu($tu.tuRet); })? EOF ;

translationUnit returns[TranslationUnit tuRet]
    : { $tuRet = new TranslationUnit(); } (ed=externalDeclaration { $tuRet.addED($ed.EDRet); })+;

externalDeclaration returns [ExternalDeclaration EDRet]
    : { $EDRet = new ExternalDeclaration(); }
     (fd=functionDefinition { $EDRet.setFD($fd.fdRet); }
    | d=declaration { $EDRet.setDeclaration($d.DRet); }) | Semi ;

functionDefinition returns[FunctionDefinition fdRet]
    : { $fdRet = new FunctionDefinition(); } (dss=declarationSpecifiers { $fdRet.setDeclarationSpecifiers($dss.dssRet); })?
    dr=declarator { $fdRet.setDeclarator($dr.drRet); } (dl=declarationList { $fdRet.setDeclarationList($dl.dlRet); })?
    cs=compoundStatement { $fdRet.setCompoundStatement($cs.CSRet); };

declarationList returns[DeclarationList dlRet]
    : {$dlRet = new DeclarationList(); } (dn=declaration { $dlRet.sddDeclaration($dn.DRet); })+ ;

expression returns [Expr exprRet]
  : id=Identifier
  {
    IdExpr id = new IdExpr($id.text);
    $exprRet = id;
    $exprRet.setLine($id.line);
  }
  | cons=Constant
  {
    $exprRet = new ConstantExpr($cons.text);
    $exprRet.setLine($cons.line);
  }
  | (st=StringLiteral {$exprRet = new StringLiteralExpr($st.text); $exprRet.setLine($st.line); })+
  | LeftParen e=expression { $exprRet = $e.exprRet; } RightParen
  | { CompoundLiteralExpr cle = new CompoundLiteralExpr(); }
    LeftParen t=typeName { cle.setTn($t.tpnm); }
    RightParen LeftBrace
    itls=initializerList { cle.setInitlst($itls.il); }
    Comma? RightBrace
    {
      $exprRet = cle;
    }
  | e1=expression LeftBracket e2=expression RightBracket
    {
        BracketExpr e = new BracketExpr($e1.exprRet, $e2.exprRet);
        $exprRet = e;
    }
  |
    e=expression { ArgumentExpressionList exprlist = new ArgumentExpressionList(); exprlist.addExpr($e.exprRet); } LeftParen
   (el=argumentExpressionList
   {
       ArrayList<Expr> exprlist_ = $el.exprlistRet.getExprlist();
       Expr x = exprlist.getExprlist().get(0);
       exprlist.setExprlist(exprlist_);
       exprlist.addExpr(x);
   })? RightParen
   {
       $exprRet = exprlist;
   }
  | e=expression  p=(PlusPlus | MinusMinus)
  {
    UnaryExpr expr = new UnaryExpr();
    UnaryOperator unary = new UnaryOperator($p.text);
    expr.setOperand($e.exprRet);
    expr.setOperator(unary);
    $exprRet = expr;
    $exprRet.setLine($p.line);
  }                                                        // Postfix decrement
  | { UnaryExpr expr = new UnaryExpr(); UnaryExpr expr1 = expr;} (op=(PlusPlus  | MinusMinus | Sizeof)
    {
        UnaryOperator unary = new UnaryOperator($op.text);
        expr1.setOperator(unary);
        UnaryExpr expr2 = new UnaryExpr();
        expr1.setOperand(expr2);
        expr1 = expr2;
    }
    )* (
         id=Identifier
         {
             IdExpr id = new IdExpr($id.text);
             $exprRet = id;
             $exprRet.setLine($id.line);
         }
       | cons=Constant
       {
            $exprRet = new ConstantExpr($cons.text);
            $exprRet.setLine($cons.line);
       }
       | (StringLiteral {$exprRet = new StringLiteralExpr($st.text); $exprRet.setLine($st.line); })+
       | LeftParen e=expression { $exprRet = $e.exprRet; } RightParen
       | { CompoundLiteralExpr cle = new CompoundLiteralExpr(); }
         LeftParen t=typeName { cle.setTn($t.tpnm); }
         RightParen LeftBrace
         itls=initializerList { cle.setInitlst($itls.il); }
         Comma? RightBrace
         {
           $exprRet = cle;
         }
       | uo=unaryOperator ce=castExpression
       {
            UnaryExpr uexpr = new UnaryExpr();
            uexpr.setOperand($ce.ceRet);
            uexpr.setOperator($uo.uoRet);
            $exprRet = uexpr;
       }
       | Sizeof LeftParen tn=typeName { $exprRet = $tn.tpnm; } RightParen
    )
    {
        expr1.setOperand($exprRet);
        $exprRet = expr;
    }
  | LeftParen tn=typeName RightParen ce=castExpression
   {
        CastExpression ce = new CastExpression();
        ce.setType($tn.tpnm);
        ce.setNestedCE($ce.ceRet);
        $exprRet = ce;
   }
  | e1=expression n=(Star | Div | Mod) e2=expression
   {
        BinaryExpr be = new BinaryExpr($n.text, $e1.exprRet, $e2.exprRet);
        $exprRet = be;
        $exprRet.setLine($n.line);
   }
  | e1=expression n=(Plus | Minus) e2=expression
   {
       BinaryExpr be = new BinaryExpr($n.text, $e1.exprRet, $e2.exprRet);
       $exprRet = be;
       $exprRet.setLine($n.line);
   }
  | e1=expression n=(LeftShift | RightShift) e2=expression
   {
       BinaryExpr be = new BinaryExpr($n.text, $e1.exprRet, $e2.exprRet);
       $exprRet = be;
       $exprRet.setLine($n.line);
   }
  | e1=expression n=(Less | Greater | LessEqual | GreaterEqual) e2=expression
   {
       BinaryExpr be = new BinaryExpr($n.text, $e1.exprRet, $e2.exprRet);
       $exprRet = be;
       $exprRet.setLine($n.line);
   }
  | e1=expression n=(Equal | NotEqual) e2=expression
   {
       BinaryExpr be = new BinaryExpr($n.text, $e1.exprRet, $e2.exprRet);
       $exprRet = be;
       $exprRet.setLine($n.line);
   }
  | e1=expression n=(And | Xor | Or | AndAnd | OrOr) e2=expression
   {
       BinaryExpr be = new BinaryExpr($n.text, $e1.exprRet, $e2.exprRet);
       $exprRet = be;
       $exprRet.setLine($n.line);
   }
  | e1=expression q=Question e2=expression Colon e3=expression
   {
        IfExpr ie = new IfExpr($e1.exprRet, $e2.exprRet, $e3.exprRet);
        $exprRet = ie;
        $exprRet.setLine($q.line);
   }
  | e1=expression ao=assignmentOperator e2=expression
   {
       BinaryExpr be = new BinaryExpr($ao.apRet.getSymbol(), $e1.exprRet, $e2.exprRet);
       $exprRet = be;
       $exprRet.setLine($ao.apRet.getLine());
   }
  | e=expression { ArgumentExpressionList el = new ArgumentExpressionList(); el.addExpr($e.exprRet); }
    (Comma e=expression {el.addExpr($e.exprRet); })+ { $exprRet = el; };                                              // Comma operator

argumentExpressionList returns[ArgumentExpressionList exprlistRet]
  : { $exprlistRet = new ArgumentExpressionList(); } e=expression { $exprlistRet.addExpr($e.exprRet); }
    (Comma e=expression { $exprlistRet.addExpr($e.exprRet); })* ;

unaryOperator returns[UnaryOperator uoRet]
  : uo=(And | Star | Plus | Minus | Tilde | Not)
  {
    $uoRet = new UnaryOperator($uo.text);
    $uoRet.setLine($uo.line);
  };

castExpression returns[CastExpression ceRet]
  : { $ceRet = new CastExpression(); }
  ( LeftParen tn=typeName RightParen ce=castExpression
  {
    $ceRet.setType($tn.tpnm);
    $ceRet.setNestedCE($ce.ceRet);
  }
  | e=expression { $ceRet.setE($e.exprRet); } | ds=DigitSequence { $ceRet.setLine($ds.line); }) ;

assignmentOperator returns [AssignmentOperator apRet]
  : a=Assign { $apRet = new AssignmentOperator("="); $apRet.setLine($a.line); }
  | a=StarAssign { $apRet = new AssignmentOperator("*="); $apRet.setLine($a.line); }
  | a=DivAssign { $apRet = new AssignmentOperator("/="); $apRet.setLine($a.line); }
  | a=ModAssign { $apRet = new AssignmentOperator("%="); $apRet.setLine($a.line); }
  | a=PlusAssign { $apRet = new AssignmentOperator("+="); $apRet.setLine($a.line); }
  | a=MinusAssign { $apRet = new AssignmentOperator("-="); $apRet.setLine($a.line); }
  | a=LeftShiftAssign { $apRet = new AssignmentOperator("<<="); $apRet.setLine($a.line); }
  | a=RightShiftAssign { $apRet = new AssignmentOperator(">>="); $apRet.setLine($a.line); }
  | a=AndAssign { $apRet = new AssignmentOperator("&="); $apRet.setLine($a.line); }
  | a=XorAssign { $apRet = new AssignmentOperator("^="); $apRet.setLine($a.line); }
  | a=OrAssign { $apRet = new AssignmentOperator("|="); $apRet.setLine($a.line); }
  ;

declaration returns[Declaration DRet]
    : { $DRet = new Declaration(); } dss=declarationSpecifiers { $DRet.setDss($dss.dssRet); }
      (idl=initDeclaratorList { $DRet.setIdl($idl.idlRet); })? Semi {
          $DRet.setLine($dss.start.getLine());
      };

declarationSpecifiers returns [DeclarationSpecifiers dssRet]
    : { $dssRet = new DeclarationSpecifiers(); } (ds=declarationSpecifier { $dssRet.addDs($ds.ds); })+ ;

declarationSpecifier returns [DeclarationSpecifier ds]
    : { $ds = new DeclarationSpecifier(); } (Typedef | type=typeSpecifier {$ds.setType($type.type); }
      | c=Const { $ds.setConst(true); $ds.setLine($c.line); } );

initDeclaratorList returns [InitDeclaratorList idlRet]
    : { $idlRet = new InitDeclaratorList(); } id=initDeclarator { $idlRet.addIds($id.idRet); }
      (comma=Comma id=initDeclarator
      {
        $idlRet.addIds($id.idRet);
        $idlRet.addCommaLine($comma.line);
      })* ;

initDeclarator returns [InitDeclarator idRet]
    : { $idRet = new InitDeclarator(); } dr=declarator { $idRet.setDr($dr.drRet); }
      (a=Assign init=initializer { $idRet.setInit($init.initRet); $idRet.setLine($a.line); })? ;

typeSpecifier returns [Type type]
    : t=(Void | Char | Short | Int | Long | Float | Double | Signed | Unsigned | Bool)
      { PremetiveType pt = new PremetiveType($t.text); $type=pt; $type.setLine($t.line); }
    | id=Identifier
      { CustomType ct = new CustomType($id.text); $type=ct; $type.setLine($id.line); }
    ;

specifierQualifierList returns [SpecifierQualifierList typeRet]
    : { $typeRet = new SpecifierQualifierList(); } (t=typeSpecifier {$typeRet.setType($t.type); } | c=Const { $typeRet.setLine($c.line); })
      (sq=specifierQualifierList { $typeRet.setSql($sq.typeRet); })?;

declarator returns[Declarator drRet]
    : { $drRet = new Declarator(); }
      (ptr=pointer { $drRet.setPtr($ptr.ptr); })?
      dtdr=directDeclarator { $drRet.setDd($dtdr.dd); } ;

directDeclarator returns [DirectDeclarator dd]
    : id=Identifier {
                      $dd = new DirectDeclarator();
                      $dd.setName($id.text);
                      $dd.setLine($id.line);
                    }
    | LeftParen dr=declarator RightParen {
        $dd = new DirectDeclarator();
        $dd.setDr($dr.drRet);
      }
    | ddr=directDeclarator { $dd = new DirectDeclarator(); $dd.setDd($ddr.dd); } LeftBracket (e=expression {$dd.setE($e.exprRet); })? RightBracket
    | ddr=directDeclarator { $dd = new DirectDeclarator(); $dd.setDd($ddr.dd); } LeftParen  (pl=parameterList { $dd.setPl($pl.plRet); }|
      (il=identifierList { $dd.setIl($il.il); })?) RightParen ;

pointer returns [Pointer ptr]
    : { $ptr = new Pointer(); } ((s=Star { $ptr.setLevel();  $ptr.setLine($s.line); }) (c=Const+)?)+ ;

parameterList returns [ParameterList plRet]
    : pd=parameterDeclaration { $plRet = new ParameterList(); $plRet.addPl($pd.pdRet); }
      (Comma pd=parameterDeclaration { $plRet.addPl($pd.pdRet); })* ;

parameterDeclaration returns [ParameterDeclaration pdRet]
    : dss=declarationSpecifiers { $pdRet = new ParameterDeclaration(); $pdRet.setDss($dss.dssRet); }
      (dr=declarator { $pdRet.setDr($dr.drRet); }| (ad=abstractDeclarator { $pdRet.setAd($ad.adRet); })?) ;

identifierList returns [IdentifierList il]
    : id=Identifier { $il = new IdentifierList($id.text); $il.setLine($id.line); } (Comma id2=Identifier {$il.addIdentifier($id2.text); })* ;

typeName returns[TypeName tpnm]
    : sq=specifierQualifierList { $tpnm = new TypeName(); $tpnm.setSql($sq.typeRet); }
      (ad=abstractDeclarator { $tpnm.setAd($ad.adRet); })? ;

abstractDeclarator returns [AbstractDeclarator adRet]
    : { $adRet = new AbstractDeclarator(); } (ptr=pointer { $adRet.setPointer($ptr.ptr); }
      | (ptr=pointer { $adRet.setPointer($ptr.ptr); })?
       dad=directAbstractDeclarator { $adRet.setDad($dad.dadRet); });

directAbstractDeclarator returns [DirectAbstractDeclarator dadRet]
    : { $dadRet = new DirectAbstractDeclarator(); } (LeftBracket (e=expression { $dadRet.setE($e.exprRet); })? RightBracket
    | LeftParen  (ad=abstractDeclarator { $dadRet.setAd($ad.adRet); } | (pl=parameterList { $dadRet.setPl($pl.plRet); })?) RightParen)
    | dad=directAbstractDeclarator { $dadRet = new DirectAbstractDeclarator(); $dadRet.setDad($dad.dadRet);  }
      LeftBracket (e=expression { $dadRet.setE($e.exprRet); })? RightBracket
    | dad=directAbstractDeclarator { $dadRet = new DirectAbstractDeclarator(); $dadRet.setDad($dad.dadRet);  }
      LeftParen (pl=parameterList { $dadRet.setPl($pl.plRet); })? RightParen ;

initializer returns [Initializer initRet]
    : { $initRet = new Initializer(); } (e=expression { $initRet.setE($e.exprRet); }|
      LeftBrace il=initializerList { $initRet.setIl($il.il); } Comma? RightBrace) ;

initializerList returns[InitializerList il]
    : { $il = new InitializerList(); } (des=designation { $il.addDes($des.desRet); })? init=initializer { $il.addInit($init.initRet); }
      (Comma (des=designation { $il.addDes($des.desRet); })? init=initializer { $il.addInit($init.initRet); })* ;

designation returns [Designation desRet]
    : { $desRet = new Designation(); } (dsr=designator { $desRet.addDsr($dsr.dsrRet); })+ a=Assign  { $desRet.setLine($a.line); };

designator returns [Designator dsrRet]
    : { $dsrRet = new Designator(); } (LeftBracket e=expression { $dsrRet.setE($e.exprRet); } RightBracket
    | Dot id=Identifier { $dsrRet.setIdName($id.text); $dsrRet.setLine($id.line); }) ;

statement returns [Stmt stmtRet]
    : (cs=compoundStatement { $stmtRet = $cs.CSRet; }
    | es=expressionStatement { $stmtRet = $es.ESRet; }
    | ss=selectionStatement { $stmtRet = $ss.SSRet; }
    | is=iterationStatement {$stmtRet = $is.ISRet; }
    | js=jumpStatement { $stmtRet = $js.JSRet; });

compoundStatement returns[CompoundStatement CSRet]
    : { $CSRet = new CompoundStatement(); } LeftBrace ((bi=blockItem { $CSRet.addBi($bi.BIRet); })+)? RightBrace ;

blockItem returns [BlockItem BIRet]
    : { $BIRet = new BlockItem(); } (stmt=statement { $BIRet.setStmt($stmt.stmtRet); }
    | dec=declaration { $BIRet.setDeclaration($dec.DRet); }) ;

expressionStatement returns [ExpressionStatement ESRet]
    : { $ESRet = new ExpressionStatement(); } (e=expression { $ESRet.setE($e.exprRet); })? Semi ;

selectionStatement returns [SelectionStatement SSRet]
    : { $SSRet = new SelectionStatement(); }
      f=If { $SSRet.setLine($f.line); }
      LeftParen e=expression { $SSRet.setE($e.exprRet); } RightParen
      s1=statement { $SSRet.setS1($s1.stmtRet); }
      (
        ef=Else s2=selectionStatement
        {
          $SSRet.setS2($s2.SSRet);
        }
      |
        es=Else s=statement
        {
          SelectionStatement elseStmt = new SelectionStatement();
          elseStmt.setS1($s.stmtRet);
          elseStmt.setLine($es.line);
          $SSRet.setS2(elseStmt);
        }
      )?
;


iterationStatement returns [IterationStatement ISRet]
    : { $ISRet = new IterationStatement(); }
    (w=While LeftParen e=expression { $ISRet.setE($e.exprRet); $ISRet.setLine($w.line); } RightParen
    s=statement { $ISRet.setStmt($s.stmtRet); }
    | w=Do s=statement { $ISRet.setStmt($s.stmtRet); }
    While LeftParen e=expression { $ISRet.setE($e.exprRet); $ISRet.setLine($w.line);} RightParen Semi
    | f=For LeftParen fc=forCondition { $ISRet.setFc($fc.FCRet); $ISRet.setLine($f.line); }
    RightParen s=statement { $ISRet.setStmt($s.stmtRet); })
    ;

forCondition returns [ForCondition FCRet]
    : { $FCRet = new ForCondition(); } (fd=forDeclaration { $FCRet.setFD($fd.FDRet); }
    | (e=expression { $FCRet.setE($e.exprRet); })?) Semi
    (fe=forExpression  { $FCRet.setFE1($fe.FERet); })? Semi
    (fe=forExpression { $FCRet.setFE2($fe.FERet); })? ;

forDeclaration returns [ForDeclaration FDRet]
    : { $FDRet = new ForDeclaration(); }
    dss=declarationSpecifiers { $FDRet.setDss($dss.dssRet); }
    (idl=initDeclaratorList { $FDRet.setIdl($idl.idlRet); })? ;

forExpression returns [ForExpression FERet]
    : { $FERet = new ForExpression(); } e=expression { $FERet.addExpr($e.exprRet); }
    (Comma e=expression { $FERet.addExpr($e.exprRet); })* ;

jumpStatement returns [JumpStatement JSRet]
    : { $JSRet = new JumpStatement(); } (state=(Continue|Break ) { $JSRet.setState($state.text); $JSRet.setLine($state.line); } Semi
    | state=Return (e=expression { $JSRet.setE($e.exprRet); })? { $JSRet.setState($state.text); $JSRet.setLine($state.line); } Semi);

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