// First, we declare this grammar as just a lexer and give it a name
grammar MinC;

@header {
    // This section is pasted into the top of the generated
    // file.  We use it to declare a package here.  Other packages
    // can also be imported as required.
    package cs536.syntax;

    import static cs536.ast.AbstractSyntaxTree.*;
    import cs536.ast.SourceLocation;
}

@members {
    private SourceLocation loc(Token tok) {
        return new SourceLocation(tok);
    }

    private List<Expression> makeList(Expression arg) {
        List<Expression> l = new ArrayList<Expression>();
        l.add(arg);
        return l;
    }

    private List<Statement> makeList(Statement arg) {
        List<Statement> l = new ArrayList<Statement>();
        l.add(arg);
        return l;
    }


    private List<FormalArg> makeList(FormalArg arg) {
        List<FormalArg> l = new ArrayList<FormalArg>();
        l.add(arg);
        return l;
    }


    // This overrides the function in the parser class BaseRecognizer
    // (provided by Antlr). It's called by Antlr to print the syntax
    // errors. We will hijack it so that we know there was a problem.
    public void emitErrorMessage(String msg)
    {
        super.emitErrorMessage(msg);
        encounteredError = true;
    }

    private boolean encounteredError = false;

    public boolean isSuccessful() {
        return !encounteredError;
    }
}


@lexer::header {
    // This section is pasted into the top of the generated
    // file.  We use it to declare a package here.  Other packages
    // can also be imported as required.
    package cs536.syntax;
}

@lexer::members {
    // Emits a token with a string representation of 'str'
    private void emitRepresentation(int type, String str) {
        emit(new MinCToken(str, type, input, state, getCharIndex()-1));
    }

    // Emits a token with a string representation of '{prefix}{lexeme}' where
    // prefix is specified and lexeme is the current lexeme
    private void emitPrefixed(int type, String prefix) {
        emitRepresentation(type, prefix + getCurrentLexeme());
    }

    // Emit an integer literal
    private void emitIntLit(long val) {
        emit(new IntLitToken(val, INT_LIT, input, state, getCharIndex()-1));
    }

    // Emit a floating point literal
    private void emitFloatLit(double val) {
        emit(new FloatLitToken(val, FLOAT_LIT, input, state, getCharIndex()-1));
    }


    // Returns the lexeme that is currently being matched
    private String getCurrentLexeme() {
        return input.substring(state.tokenStartCharIndex, getCharIndex() - 1);
        //MinCToken t = new MinCToken(input, state, getCharIndex()-1);
        //return t.getText();
    }


    // Emits a token for an illegal escape sequence. This one is a little obnoxious.
    private void emitIllegalEscape() {
        // Remove any escaped \s so that we can just look at the remaining
        // ones.  That is, remove \\ from the string. The regex for that is
        // \\\\ since \ is special, which means we need to type \\\\\\\\ for
        // the actual string.
        String s = getCurrentLexeme().replaceAll("\\\\\\\\", "");
        int pos = -1;
        while ((pos = s.indexOf('\\', pos+1)) != -1) {
            char c = s.charAt(pos+1);
            if (c != 'n' && c != 't' && c != '\"' && c != '\\') {
                emitRepresentation(STRING_WITH_ILLEGAL_ESCAPE, "Illegal escape sequence: \\" + c);
                return;
            }
        }
        assert false;
    }
}

//////////////////////////////////////////////////////////////////////////

////////   Parser

//////////////////////////////////////////////////////////////////////////

program returns [List<GlobalDeclaration> list]
    :             { list = new ArrayList<GlobalDeclaration>(); }
        (d=decl   { list.add(d); }
        )*
    ;


groundType returns [TypeNode node]
    : l=INT      { node = new IntTypeNode(loc(l));     }
    | l=BOOL     { node = new BooleanTypeNode(loc(l)); }
    | l=DOUBLE   { node = new DoubleTypeNode(loc(l));  }
    | l=STRING   { node = new StringTypeNode(loc(l));  }
    ;

type returns [TypeNode node]
    : t=groundType                        { node = t; }
        ( STAR                            { node = new PointerTypeNode(t.getLocation(), node);  }
        | LBRACKET size=INT_LIT RBRACKET  { node = new ArrayTypeNode(t.getLocation(), node,
                                                                 ((IntLitToken)size).getValue()); }
        )*
    ;

funcType returns [TypeNode node]
    : ty=type  { node = ty;   }
    | VOID     { node = null; }  // 'void' return type is represented as null; see AST file
    ;

localVarDecl returns [VarDeclStmt node]
    : ty=type name=IDENT SEMI                  { node = new VarDeclStmt(loc(name), ty,
                                                                        name.getText(), null); }
    | ty=type name=IDENT ASSIGN init=exp SEMI  { node = new VarDeclStmt(loc(name), ty,
                                                                        name.getText(), init); }
    ;

globalVarDecl returns [GlobalVarDecl node]
    : ty=type name=IDENT SEMI                  { node = new GlobalVarDecl(loc(name), ty,
                                                                          name.getText(), null); }
    | ty=type name=IDENT ASSIGN init=exp SEMI  { node = new GlobalVarDecl(loc(name), ty,
                                                                          name.getText(), init); }
    ;


stmt returns [Statement node]
    : d=localVarDecl           { node = d; }
    | r=RETURN SEMI            { node = new ReturnStmt(loc(r), null);           }  // no expression => null
    | r=RETURN e=exp SEMI      { node = new ReturnStmt(loc(r), e);              }
    | e=exp SEMI               { node = new ExpressionStmt(e.getLocation(), e); }
    | w=WRITE e=exp SEMI       { node = new WriteStmt(loc(w), e);               }
    | r=READ name=IDENT SEMI   { node = new ReadStmt(loc(r), name.getText());   }
    | i=IF LPAREN cond=exp RPAREN thenBody=stmtOrBody
        (options {greedy=true;} 
         : ELSE elseBody=stmtOrBody  { node = new IfStmt(loc(i), cond, thenBody, elseBody); }
        |                            { node = new IfStmt(loc(i), cond, thenBody, new ArrayList<Statement>()); }
        )
    | w=WHILE LPAREN cond=exp RPAREN body=stmtOrBody { node = new WhileStmt(loc(w), cond, body); }
    ;

stmtOrBody returns [List<Statement> list]
    : s=stmt                      { list = makeList(s); }
    | LBRACE l=stmtList RBRACE    { list = l; }
    ;

stmtList returns [List<Statement> list]
    :               { list = new ArrayList<Statement>(); }
        (s=stmt     { list.add(s); }
        )*
    ;


formalList returns [List<FormalArg> list]
    : /* nothing */       { list = new ArrayList<FormalArg>(); }
    | f=formal            { list = makeList(f); }
        (COMMA f=formal   { list.add(f); }
        )*
    ;

formal returns [FormalArg node]
    : ty=type name=IDENT        { node = new FormalArg(loc(name), ty, name.getText()); }
    ;

funcDecl returns [FuncDecl node]
    : ret=funcType name=IDENT LPAREN args=formalList RPAREN LBRACE body=stmtList RBRACE
        { node = new FuncDecl(loc(name), ret, name.getText(), args, body); }
    ;


decl returns [GlobalDeclaration node]
    : v=globalVarDecl    { node = v; }
    | f=funcDecl         { node = f; }
    ;


actualsList returns [List<Expression> list]
    : /* nothing */    { list = new ArrayList<Expression>(); }
    | e=exp            { list = makeList(e); }
      (COMMA e=exp     { list.add(e); }
      )*
    ;

literalExp returns [LiteralExp node]
    : l=NULL                { node = new NullLit(loc(l));                }
    | l=TRUE                { node = new BooleanLit(loc(l), true);       }
    | l=FALSE               { node = new BooleanLit(loc(l), false);      }
    | l=STRING_LIT          { node = new StringLit(loc(l), l.getText()); }
    | l=INT_LIT             { node = new IntLit(loc(l),    ((IntLitToken)l).getValue());   }
    | l=FLOAT_LIT           { node = new DoubleLit(loc(l), ((FloatLitToken)l).getValue()); }
    ;

// Array access and function calls (++IDENT LPAREN actualsList RPAREN++)
// Postfix increment and decrement
// Prefix increment and decrement
// Unary minus, logical not
// Pointer dereference (++STAR exp++)
// Address-of
// Multiplication, division, modulus
// Addition and subtraction
// Relational less/greater (not associative)
// Relational (not) equal to
// Logical AND
// Logical OR
// Assignment (right associative)

atomExp returns [Expression node]
    : lit=literalExp             { node = lit; }
    | id=IDENT                   { node = new VarRefExp(loc(id), id.getText()); }
    | LPAREN e=exp RPAREN        { node = e; }
    ;

arrayFunctionExp returns [Expression node]
    : e=atomExp                            { node = e; }
        ( LBRACKET idx=exp RBRACKET        { node = new ArrayAccessOp(e.getLocation(), node, idx); }
        )*
    | name=IDENT LPAREN args=actualsList RPAREN  { node = new FunctionCallExp(loc(name), name.getText(), args); }
    ;

postfixExp returns [Expression node]
    : e=arrayFunctionExp       { node = e; }
        (INCR                  { node = new IncrementOp(node.getLocation(), node, false); }
        | DECR                 { node = new DecrementOp(node.getLocation(), node, false); }
        )*
    ;

prefixExp returns [Expression node]
    : i=INCR e=prefixExp      { node = new IncrementOp(loc(i), e, true); }
    | d=DECR e=prefixExp      { node = new DecrementOp(loc(d), e, true); }
    | e=postfixExp             { node = e; }
    ;

minusNotExp returns [Expression node]
    : m=MINUS e=minusNotExp    { node = new NegationOp(loc(m), e); }
    | n=LNOT e=minusNotExp     { node = new NotOp(loc(n), e);      }
    | e=prefixExp              { node = e; }
    ;

derefExp returns [Expression node]
    : s=STAR e=derefExp       { node = new DerefOp(loc(s), e); }
    | e=minusNotExp           { node = e; }
    ;

addrOfExp returns [Expression node]
    : a=ADDROF e=addrOfExp    { node = new AddrOfOp(loc(a), e); }
    | e=derefExp              { node = e; }
    ;

multExp returns [Expression node]
    : e=addrOfExp               { node = e; }
        ( STAR r=addrOfExp      { node = new MultiplyOp(node.getLocation(), node, r); }
        | DIV r=addrOfExp       { node = new DivideOp(node.getLocation(), node, r); }
        | MOD r=addrOfExp       { node = new ModOp(node.getLocation(), node, r); }
        )*
    ;

addExp returns [Expression node]
    : e=multExp                 { node = e; }
        ( p=PLUS r=multExp      { node = new AddOp(node.getLocation(), node, r); }
        | m=MINUS r=multExp     { node = new SubtractOp(node.getLocation(), node, r); }
        )*
    ;

inequalityExp returns [Expression node]
    : e=addExp              { node = e; }
        ( LT r=addExp       { node = new LessThanOp(node.getLocation(), node, r); }
        | LTE r=addExp      { node = new LessOrEqualOp(node.getLocation(), node, r); }
        | GT r=addExp       { node = new GreaterThanOp(node.getLocation(), node, r); }
        | GTE r=addExp      { node = new GreaterOrEqualOp(node.getLocation(), node, r); }
        )?
    ;

equalityExp returns [Expression node]
    : e=inequalityExp               { node = e; }
        ( EQUAL r=inequalityExp     { node = new EqualsOp(node.getLocation(), node, r); }
        | NEQUAL r=inequalityExp    { node = new NotEqualsOp(node.getLocation(), node, r); }
        )*
    ;

andExp returns [Expression node]
    : e=equalityExp             { node = e; }
        ( LAND r=equalityExp    { node = new AndOp(e.getLocation(), node, r); }
        )*
    ;

orExp returns [Expression node]
    : e=andExp                  { node = e; }
        ( LOR r=andExp          { node = new OrOp(e.getLocation(), node, r); }
        )*
    ;

assignExp returns [Expression node]
    : e=orExp                 { node = e; }
        (ASSIGN r=assignExp   { node = new AssignOp(node.getLocation(), node, r); }
        )?
    ;


exp returns [Expression node]
    : e=assignExp   { node = e; }
    ;



//////////////////////////////////////////////////////////////////////////

////////   Lexer

//////////////////////////////////////////////////////////////////////////

fragment LOWER: 'a'..'z';
fragment UPPER: 'A'..'Z';
fragment ALPHA: UPPER | LOWER;
fragment DIGIT: '0'..'9';


WHITESPACE
    : (' ' | '\r'? '\n' | '\t') { skip(); }
    ;

LINE_COMMENT
    : ('//' ~'\n'*)   {skip();}
    ;

fragment BLOCK_COMMENT_CONTENTS : '*'+ ~('*' | '/') | ~'*';
BLOCK_COMMENT
    : ('/*' BLOCK_COMMENT_CONTENTS* '*'* '*/')  {skip();}
    ;

UNTERM_BLOCK_COMMENT
    : ('/*' BLOCK_COMMENT_CONTENTS* '*'*)  { emitRepresentation(UNTERM_BLOCK_COMMENT, "Unterminated block comment"); }
    ;



/////////////////////////////////////
// Keywords and operators

BOOL:   'bool'     { emitRepresentation(BOOL, "BOOL"); };
DOUBLE: 'double'   { emitRepresentation(DOUBLE, "DOUBLE"); };
ELSE:   'else'     { emitRepresentation(ELSE, "ELSE"); };
FALSE:  'false'    { emitRepresentation(FALSE, "FALSE"); };
IF:     'if'       { emitRepresentation(IF, "IF"); };
INT:    'int'      { emitRepresentation(INT, "INT"); };
NULL:   'null'     { emitRepresentation(NULL, "NULL"); };
READ:   'read'     { emitRepresentation(READ, "READ"); };
RETURN: 'return'   { emitRepresentation(RETURN, "RETURN"); };
STRING: 'string'   { emitRepresentation(STRING, "STRING"); };
TRUE:   'true'     { emitRepresentation(TRUE, "TRUE"); };
VOID:   'void'     { emitRepresentation(VOID, "VOID"); };
WHILE:  'while'    { emitRepresentation(WHILE, "WHILE"); };
WRITE:  'write'    { emitRepresentation(WRITE, "WRITE"); };

LPAREN:   '('      { emitRepresentation(LPAREN, "LPAREN"); };
RPAREN:   ')'      { emitRepresentation(RPAREN, "RPAREN"); };
LBRACE:   '{'      { emitRepresentation(LBRACE, "LBRACE"); };
RBRACE:   '}'      { emitRepresentation(RBRACE, "RBRACE"); };
LBRACKET: '['      { emitRepresentation(LBRACKET, "LBRACKET"); };
RBRACKET: ']'      { emitRepresentation(RBRACKET, "RBRACKET"); };
SEMI:     ';'      { emitRepresentation(SEMI, "SEMI"); };
COMMA:    ','      { emitRepresentation(COMMA, "COMMA"); };
LAND:     '&&'     { emitRepresentation(LAND, "LAND"); };
LOR:      '||'     { emitRepresentation(LOR, "LOR"); };
LNOT:     '!'      { emitRepresentation(LNOT, "LNOT"); };
ASSIGN:   '='      { emitRepresentation(ASSIGN, "ASSIGN"); };
ADDROF:   '&'      { emitRepresentation(ADDROF, "ADDROF"); };
LT:       '<'      { emitRepresentation(LT, "LT"); };
LTE:      '<='     { emitRepresentation(LTE, "LTE"); };
GT:       '>'      { emitRepresentation(GT, "GT"); };
GTE:      '>='     { emitRepresentation(GTE, "GTE"); };
EQUAL:    '=='     { emitRepresentation(EQUAL, "EQUAL"); };
NEQUAL:   '!='     { emitRepresentation(NEQUAL, "NEQUAL"); };
INCR:     '++'     { emitRepresentation(INCR, "INCR"); };
DECR:     '--'     { emitRepresentation(DECR, "DECR"); };
PLUS:     '+'      { emitRepresentation(PLUS, "PLUS"); };
MINUS:    '-'      { emitRepresentation(MINUS, "MINUS"); };
STAR:     '*'      { emitRepresentation(STAR, "STAR"); };
DIV:      '/'      { emitRepresentation(DIV, "DIV"); };
MOD:      '%'      { emitRepresentation(MOD, "MOD"); };


/////////////////////////////////
// Identifiers

fragment IDENT_START:    ALPHA | ('_' | '$')+ (ALPHA | DIGIT);
fragment IDENT_CONTINUE: (ALPHA | DIGIT | '_' | '$')*;

IDENT
    : IDENT_START IDENT_CONTINUE  { emitPrefixed(IDENT, "IDENT: "); }
    ;


// "If a lexeme that would otherwise be an identifer contains only dollar
// signs and underscores or begins with a numeric character..."
BAD_IDENT
    : (
        ('_' | '$')+ 
      | (DIGIT+ (ALPHA | '_' | '$') IDENT_CONTINUE)
      )
    { emitPrefixed(BAD_IDENT, "Invalid identifier: "); };


////////////////////////////////////
// Strings

fragment VALID_ESCAPE_CHAR: '\\' | 'n' | 't' | '"';

// Anything is good in a string except for \ and ".
// 32: space
// 33: !
// 34: "
// 35: #
// 133: [
// 134: \
// 135: ]
// 126: ~
fragment STRING_CHAR: ' ' | '!' | '#'..'[' | ']'..'~' | '\\' VALID_ESCAPE_CHAR;


STRING_LIT
    : '"' STRING_CHAR* '"'  { emitPrefixed(STRING_LIT, "STRING_LIT: "); }
    ;

// Illegal escapes
fragment ILLEGAL_ESCAPE: '\\' ~(VALID_ESCAPE_CHAR);
fragment ILLEGAL_ESCAPE_STRING_CHAR: STRING_CHAR | ILLEGAL_ESCAPE;

STRING_WITH_ILLEGAL_ESCAPE
    : '"' STRING_CHAR*  ILLEGAL_ESCAPE  ILLEGAL_ESCAPE_STRING_CHAR* '"'
      { emitIllegalEscape(); }
    ;

// Strings with tabs can have illegal escapes
fragment TAB_STRING_CHAR: ILLEGAL_ESCAPE_STRING_CHAR | '\t';

STRING_WITH_TAB
    : '"' ILLEGAL_ESCAPE_STRING_CHAR* '\t' TAB_STRING_CHAR* '"'
      { emitRepresentation(STRING_WITH_TAB, "Illegal string character"); }
    ;

// Unterminated strings can have both illegal escape sequences and tabs
UNTERM_STRING
    : '"' TAB_STRING_CHAR* 
      { emitPrefixed(UNTERM_STRING, "Unterminated string: "); }
    ;



/////////////////////////////
// Integers

fragment INTEGER: ('0' | '1'..'9' DIGIT*);

INT_LIT
    : INTEGER
        {
            try {
                long val = Long.parseLong(getCurrentLexeme());
                long max = - (long) Integer.MIN_VALUE;
                if (val > max) {
                    emitPrefixed(INT_LIT, "Integer out of range: ");
                }
                else {
                    emitIntLit(val);
                }
            }
            catch (NumberFormatException n) {
                emitPrefixed(INT_LIT, "Integer out of range: ");
            }
        }
    ;

LEADING_ZERO_INT
    : '0' DIGIT+  { emitPrefixed(LEADING_ZERO_INT, "Integer has leading 0: "); }
    ;


/////////////////////////////
// Floats

FLOAT_LIT
    : INTEGER '.' DIGIT+ ('e' '-'? INTEGER)?
      {
          try {
              double val = Double.parseDouble(getCurrentLexeme());
              if (Double.isInfinite(val)) {
                  emitPrefixed(FLOAT_LIT, "Floating point out of range: ");
              }
              else {
                  emitFloatLit(val);
              }
          }
          catch (NumberFormatException n) {
              emitPrefixed(FLOAT_LIT, "Floating point out of range: ");
          }
      }
    ;

LEADING_ZERO_FLOAT
    : ( '0' DIGIT+ '.' DIGIT+ ('e' '-'? INTEGER)?
      | INTEGER    '.' DIGIT+ 'e' '-'? '0' DIGIT+
      | '0' DIGIT+ '.' DIGIT+ 'e' '-'? '0' DIGIT+
      )
      { emitPrefixed(FLOAT_LIT, "Floating point has leading 0: "); }
    ;
