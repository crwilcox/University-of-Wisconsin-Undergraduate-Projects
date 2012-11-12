// @author Christopher Wilcox
// wilcox@cae.wisc.edu

// First, we declare this grammar as just a lexer and give it a name
grammar MinC;

// NOTE: The @header and @members only applies to the parser. There is a
// separate section for the lexer, below.
@header {
    // This section is pasted into the top of the generated
    // file.  We use it to declare a package here.  Other packages
    // can also be imported as required.
    package cs536.syntax;

    import static cs536.ast.AbstractSyntaxTree.*;
    import cs536.ast.SourceLocation;
}

@members {
    // Utility function for turning a token into a source location. (These
    // will be used in future projects.) For now don't worry too much about
    // where you set your source locations; if you want to keep using your
    // code, make it "reasonable." In particular, for declarations, make it
    // the same as the thing being declared.
    private SourceLocation loc(Token tok) {
        return new SourceLocation(tok);
    }


    // Some utility functions for creating a list of one element.
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

///////////////////////////////////d///////////////////////////////////////

////////   Parser

//////////////////////////////////////////////////////////////////////////

program returns [List<GlobalDeclaration> list]
    :             { list = new ArrayList<GlobalDeclaration>(); }
        (d=decl   { list.add(d); }
        )*
    ;

groundType returns [TypeNode node]
    : l=INT      { node = new IntType(loc(l));     } |
      l=BOOL     { node = new BooleanType(loc(l)); } |
      l=DOUBLE   { node = new DoubleType(loc(l));  } |
      l=STRING   { node = new StringType(loc(l));  } 
    ;

type_pointer returns [TypeNode node]                 
    : t=groundType { node = t; }  
      ( STAR { node = new PointerType(node.getLocation(), node); } )*                          
    ;

type returns [TypeNode node]
    : t=type_pointer  { node = t; } (LBRACKET size=INT_LIT RBRACKET { node = new ArrayType(node.getLocation(), node, ((IntLitToken)size).getValue()); } )* 
    ;
     
funcType returns [TypeNode node]
    : t=type  { node = t;   } 
    | VOID     { node = null; }
    ;

stmtList returns [List<Statement> list]
    : s=stmt sl=stmtList  		{ sl.add(0, s); list = sl; } |
                               	{ list = new ArrayList<Statement>(); }
    ;
    
stmt returns [Statement node]
    : v=varDecl                       { node = new VarDeclStmt(v.getLocation(),v.getType(),v.getName(),v.getInitializer()); }
    | r=RETURN SEMI                   { node = new ReturnStmt(loc(r), null); }
    | r=RETURN e=exp SEMI             { node = new ReturnStmt(loc(r), e); }
    | e=exp SEMI                      { node = new ExpressionStmt(e.getLocation(), e); }
    | i=IF LPAREN cond=exp RPAREN thenBody=stmtOrBody
        (options {greedy=true;} 
         : ELSE elseBody=stmtOrBody    { node = new IfStmt(loc(i), cond, thenBody, elseBody); }
        |                              { node = new IfStmt(loc(i), cond, thenBody, new ArrayList<Statement>()); }
        )
    | w=WHILE LPAREN cond=exp RPAREN body=stmtOrBody { node = new WhileStmt(loc(w), cond, body); }
    | w=WRITE e=exp SEMI              	{ node = new WriteStmt(loc(w), e); }
    | r=READ i=IDENT SEMI            	{ node = new ReadStmt(loc(r), i.getText()); }
    ;

stmtOrBody returns [List<Statement> list]
    : s=stmt                      		{ list = new ArrayList<Statement>(); list.add(s); } | 
      LBRACE sl=stmtList RBRACE    		{ list = sl; }
    ;

funcDecl returns [FuncDecl node]
    : ret=funcType name=IDENT LPAREN param=formalList RPAREN LBRACE body=stmtList RBRACE
        { node = new FuncDecl(loc(name), ret, name.getText(), param, body); }
    ;

formalList returns [ArrayList<FormalArg> args]
    : list=nonEmptyFormalList   { args = list; } |
                                { args = new ArrayList<FormalArg>();}
    ;

nonEmptyFormalList returns [ArrayList<FormalArg> args]
    : next=formal list=formalTail {list.add(0, next);  args = list; }
    ;
    
formalTail returns [ArrayList<FormalArg> args]
    : COMMA next=formal list=formalTail { list.add(0, next);    args = list; } |
                                        { args = new ArrayList<FormalArg>(); }
    ;

formal returns [FormalArg arg]
    :  t=type name=IDENT { arg = new FormalArg(t.getLocation(), t, name.getText()); }
    ;

varDecl returns [GlobalVarDecl node]
    : ret=type name=IDENT SEMI
        { node = new GlobalVarDecl(loc(name), ret, name.getText(), null); } 
    | ret=type name=IDENT ASSIGN init=exp SEMI
        { node = new GlobalVarDecl(loc(name), ret, name.getText(), init); }
    ;

decl returns [GlobalDeclaration node]
    : f=funcDecl         { node = f; } |
      v=varDecl          { node = v; }
    ;

literalExp returns [LiteralExp node]
    : l=NULL                { node = new NullLit(loc(l)); }
    | l=TRUE                { node = new BooleanLit(loc(l), true); }
    | l=FALSE               { node = new BooleanLit(loc(l), false); }
    | l=STRING_LIT          { node = new StringLit(loc(l), l.getText()); }
    | l=INT_LIT             { node = new IntLit(loc(l), ((IntLitToken)l).getValue()); }
    | l=FLOAT_LIT           { node = new DoubleLit(loc(l), ((FloatLitToken)l).getValue()); }
    ;

varExp returns [VarRefExp node]
    : v=IDENT   { node = new VarRefExp(loc(v),v.getText()); }
    ;
    
exp returns [Expression node]
    :e=precedence_12     		{ node = e; } 
    ;

actualsList returns [List<Expression> actuals]
    : list=nonEmptyActualsList { actuals = list; }
    |                          { actuals = new ArrayList<Expression>(); }
    ;
 
nonEmptyActualsList returns [List<Expression> actuals]
    : e=exp tail=actualsTail { tail.add(0,e); actuals = tail; }
    ;
    
actualsTail returns [List<Expression> actuals]
    : COMMA e=exp tail=actualsTail { tail.add(0,e); actuals = tail;}
    |                              { actuals = new ArrayList<Expression>(); }
    ;

precedence_12 returns [Expression node] 
    : e=precedence_11             { node = e; }
        ( ASSIGN r=precedence_12  { node = new AssignOp(node.getLocation(), node, r); }
        )*
    ;

precedence_11 returns [Expression node] 
    : e=precedence_10          	{ node = e; }
        ( LOR r=precedence_10  	{ node = new OrOp(node.getLocation(), node, r); }
        )*
    ;

precedence_10 returns [Expression node] 
    : e=precedence_9             { node = e; }
        ( LAND r=precedence_9    { node = new AndOp(node.getLocation(), node, r); }
        )*
    ;

precedence_9 returns [Expression node] 
    : e=precedence_8             { node = e; }
        ( NEQUAL r=precedence_8  { node = new NotEqualsOp(node.getLocation(), node, r); }
		| EQUAL  r=precedence_8  { node = new EqualsOp(node.getLocation(), node, r); }
        )*
    ;

precedence_8 returns [Expression node] 
    : e=precedence_7         	{ node = e; }
    	( LT r=precedence_7  	{ node = new LessThanOp(node.getLocation(), node, r); }
		| GT r=precedence_7  	{ node = new GreaterThanOp(node.getLocation(), node, r); }
		| LTE r=precedence_7 	{ node = new LessOrEqualOp(node.getLocation(), node, r); }
		| GTE r=precedence_7 	{ node = new GreaterOrEqualOp(node.getLocation(), node, r); }
        )?
    ;
    
precedence_7 returns [Expression node] 
    : e=precedence_6           { node = e; }
        ( PLUS r=precedence_6  { node = new AddOp(node.getLocation(), node, r); }
        | MINUS r=precedence_6 { node = new SubtractOp(node.getLocation(), node, r); }
        )*
    ;
 
precedence_6 returns [Expression node] 
    : e=precedence_5           	{ node = e; }
        ( STAR r=precedence_5  	{ node = new MultiplyOp(node.getLocation(), node, r); }
	    | DIV r=precedence_5   	{ node = new DivideOp(node.getLocation(), node, r); }
	    | MOD r=precedence_5   	{ node = new ModOp(node.getLocation(), node, r); }
        )*
    ;
   
precedence_5 returns [Expression node]
    : m=ADDROF e=precedence_5  { node = new AddrOfOp(loc(m), e); }
    | i=precedence_4           { node = i; }
    ;
    
precedence_4 returns [Expression node]
    : m=STAR e=precedence_4  { node = new DerefOp(loc(m), e); }
    | i=precedence_3         { node = i; }
    ;
    
precedence_3 returns [Expression node]
    : i=precedence_2         	{ node = i; }
    | m=MINUS e=precedence_3 	{ node = new NegationOp(loc(m), e); }
    | m=LNOT e=precedence_3  	{ node = new NotOp(loc(m), e); }
    ;

precedence_2 returns [Expression node]
    : m=INCR i=precedence_2 	{ node = new IncrementOp(loc(m), i, true);  }
    | m=DECR i=precedence_2 	{ node = new DecrementOp(loc(m), i, true); }
    | i=precedence_1         	{ node = i; }
    ;

precedence_1 returns [Expression node]
    : e=precedence_0    { node = e; }
        ( INCR    		{ node = new IncrementOp(node.getLocation(), node, false); }
        | DECR    		{ node = new DecrementOp(node.getLocation(), node, false); }
        )*
    ;

precedence_0 returns [Expression node]
    : LPAREN e=exp RPAREN                   { node = e; }
    | v=IDENT   							{ node = new VarRefExp(loc(v),v.getText()); }
      ( LBRACKET val=exp RBRACKET 			{ node = new ArrayAccessOp(node.getLocation(), node, val); }
      | LPAREN a=actualsList RPAREN   		{ node = new FunctionCallExp(node.getLocation(), v.getText() ,a); }
      )*
	| l=literalExp                    		{ node = l; }
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
