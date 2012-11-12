// First, we declare this grammar as just a lexer and give it a name
lexer grammar MinC;

@header {
    // This section is pasted into the top of the generated
    // file.  We use it to declare a package here.  Other packages
    // can also be imported as required.
    package cs536.syntax;
}

@members {
    // In this section you can declare additional members,
    // including constructors.  These additional constructors
    // demonstrate how to provide additional information to
    // the lexer at instantiation time.
    private String inputFile;

    public MinC(CharStream input, String filename) {
        this(input, new RecognizerSharedState(), filename);
    }

    public MinC(CharStream input, RecognizerSharedState state, String filename) {
        super(input, state);
        inputFile = filename;
    }
}


// fragment rules are like helper functions; they recognize some small
// part of the grammar but do *not* cause ANTLR to emit tokens.  They
// are meant to be referenced in more complex rules.
fragment LOWER: 'a'..'z';
fragment UPPER: 'A'..'Z';
fragment ALPHA: UPPER | LOWER;
fragment DIGIT : '0'..'9' ; 

// This is a rule to discard spaces.  The skip(); function is special
// and tells the lexer to discard the lexemes that match this rule.
SPACE :  (' ' | '\t' | '\n' | '\r') { skip(); };
BLOCK_COMMENT : '/*' (options { greedy = false; } : .)* '*/'{ skip(); };

COMMENT : ('//' ~('\n'|'\r')* '\r'? '\n') { skip(); }; 

//cant do ~('*/') this doesn't work
UNTERMINATED_BLOCK_COMMENT: ( '/*' (~('*') ~('/'))* ~('<EOF>')) {
  this.state.type = MinC.UNTERMINATED_BLOCK_COMMENT; 
  emit(new MinCToken(input, state, getCharIndex()-1));
};

END: '<EOF>' { skip();};

// **************************KEYWORDS**************************
// The string representation of a keyword token is name of the 
// keyword in all caps (e.g. BOOL).
// bool
BOOL : 'bool'{ this.state.type = MinC.BOOL; emit(new MinCToken(input, state, getCharIndex()-1)); };

// double
DOUBLE : 'double'{ this.state.type = MinC.DOUBLE; emit(new MinCToken(input, state, getCharIndex()-1)); };

// else
ELSE : 'else'{ this.state.type = MinC.ELSE; emit(new MinCToken(input, state, getCharIndex()-1)); };

// false
FALSE : 'false'{ this.state.type = MinC.FALSE; emit(new MinCToken(input, state, getCharIndex()-1)); };

// if
IF : 'if'{ this.state.type = MinC.IF; emit(new MinCToken(input, state, getCharIndex()-1)); };

// int
INT : 'int'{ this.state.type = MinC.INT; emit(new MinCToken(input, state, getCharIndex()-1)); };

// null
NULL : 'null'{ this.state.type = MinC.NULL; emit(new MinCToken(input, state, getCharIndex()-1)); };

// read
READ : 'read'{ this.state.type = MinC.READ; emit(new MinCToken(input, state, getCharIndex()-1)); };

// return
RETURN : 'return'{ this.state.type = MinC.RETURN; emit(new MinCToken(input, state, getCharIndex()-1)); };

// string
STRING : 'string'{ this.state.type = MinC.STRING; emit(new MinCToken(input, state, getCharIndex()-1)); };

// true
TRUE : 'true'{ this.state.type = MinC.TRUE; emit(new MinCToken(input, state, getCharIndex()-1)); };

// void
VOID : 'void'{ this.state.type = MinC.VOID; emit(new MinCToken(input, state, getCharIndex()-1)); };

// while
WHILE : 'while'{ this.state.type = MinC.WHILE; emit(new MinCToken(input, state, getCharIndex()-1)); };

// write
WRITE : 'write'{ this.state.type = MinC.WRITE; emit(new MinCToken(input, state, getCharIndex()-1)); };

//**************************OPERATORS**************************
// ( (string representation: LPAREN)
LPAREN : '('{ this.state.type = MinC.LPAREN; emit(new MinCToken(input, state, getCharIndex()-1)); };

// ) (string representation: RPAREN)
RPAREN : ')'{ this.state.type = MinC.RPAREN; emit(new MinCToken(input, state, getCharIndex()-1)); };

// { (string representation: LBRACE)
LBRACE : '{'{ this.state.type = MinC.LBRACE; emit(new MinCToken(input, state, getCharIndex()-1)); };

// } (string representation: RBRACE)
RBRACE : '}'{ this.state.type = MinC.RBRACE; emit(new MinCToken(input, state, getCharIndex()-1)); };

// [ (string representation: LBRACKET)
LBRACKET : '['{ this.state.type = MinC.LBRACKET; emit(new MinCToken(input, state, getCharIndex()-1)); };

// ] (string representation: RBRACKET)
RBRACKET : ']'{ this.state.type = MinC.RBRACKET; emit(new MinCToken(input, state, getCharIndex()-1)); };

// ; (string representation: SEMI)
SEMI : ';'{ this.state.type = MinC.SEMI; emit(new MinCToken(input, state, getCharIndex()-1)); };

// , (string representation: COMMA)
COMMA : ','{ this.state.type = MinC.COMMA; emit(new MinCToken(input, state, getCharIndex()-1)); };

// && (string representation: LAND for logical and)
LAND : '&&'{ this.state.type = MinC.LAND; emit(new MinCToken(input, state, getCharIndex()-1)); };

// || (string representation: LOR for logical or)
LOR : '||'{ this.state.type = MinC.LOR; emit(new MinCToken(input, state, getCharIndex()-1)); };

// ! (string representation: LNOT for logical not)
LNOT : '!'{ this.state.type = MinC.LNOT; emit(new MinCToken(input, state, getCharIndex()-1)); };

// = (string representation: ASSIGN)
ASSIGN : '='{ this.state.type = MinC.ASSIGN; emit(new MinCToken(input, state, getCharIndex()-1)); };

// & (string representation: ADDROF)
ADDROF : '&'{ this.state.type = MinC.ADDROF; emit(new MinCToken(input, state, getCharIndex()-1)); };

// < (string representation: LT)
LT : '<'{ this.state.type = MinC.LT; emit(new MinCToken(input, state, getCharIndex()-1)); };

// <= (string representation: LTE)
LTE : '<='{ this.state.type = MinC.LTE; emit(new MinCToken(input, state, getCharIndex()-1)); };

// > (string representation: GT)
GT : '>'{ this.state.type = MinC.GT; emit(new MinCToken(input, state, getCharIndex()-1)); };

// >= (string representation: GTE)
GTE : '>='{ this.state.type = MinC.GTE; emit(new MinCToken(input, state, getCharIndex()-1)); };

// == (string representation: EQUAL)
EQUAL : '=='{ this.state.type = MinC.EQUAL; emit(new MinCToken(input, state, getCharIndex()-1)); };

// != (string representation: NEQUAL)
NEQUAL : '!='{ this.state.type = MinC.NEQUAL; emit(new MinCToken(input, state, getCharIndex()-1)); };

// ++ (string representation: INCR)
INCR : '++'{ this.state.type = MinC.INCR; emit(new MinCToken(input, state, getCharIndex()-1)); };

// -- (string representation: DECR)
DECR : '--'{ this.state.type = MinC.DECR; emit(new MinCToken(input, state, getCharIndex()-1)); };

// + (string representation: PLUS)
PLUS : '+'{ this.state.type = MinC.PLUS; emit(new MinCToken(input, state, getCharIndex()-1)); };

// - (string representation: MINUS)
MINUS : '-'{ this.state.type = MinC.MINUS; emit(new MinCToken(input, state, getCharIndex()-1)); };

// * (string representation: STAR, used for both multiplication and dereference)
STAR : '*'{ this.state.type = MinC.STAR; emit(new MinCToken(input, state, getCharIndex()-1)); };

// / (string representation: DIV)
DIV : '/'{ this.state.type = MinC.DIV; emit(new MinCToken(input, state, getCharIndex()-1)); };

// % (string representation: MOD)
MOD : '%'{ this.state.type = MinC.MOD; emit(new MinCToken(input, state, getCharIndex()-1)); };

//*************************MORE COMPLICATED*************************
//Identifiers may begin with an underscore, dollar sign, or alphabetic character. The remainder
// of the identifier may contain any alphanumeric character, dollar signs, and underscores. If an 
//identifier begins with an underscore or dollar sign, it must contain at least one alphanumeric character.
//if this doesn't happen, print invalid ident
IDENT : (
          (('_' | '$')+  (ALPHA | DIGIT) (ALPHA | DIGIT| '_' | '$')*) 
          |  
          (ALPHA (ALPHA | DIGIT | '_' | '$')*)
         ){ 
  this.state.type = MinC.IDENT; 
  emit(new MinCToken(input, state, getCharIndex()-1)); 
};

//invalid if it start with a number, or starts with a $ or _ and has no other characters
//as many digits as you want, followed by alpha or _ or $ then followed by as many of whatever else you want
//other option is just _ and $ characters
INVALID_IDENT: ((DIGIT+ (ALPHA| '_' | '$') (DIGIT|ALPHA| '_' | '$')*) | ('_' | '$')+){
  this.state.type = MinC.INVALID_IDENT; 
  emit(new MinCToken(input, state, getCharIndex()-1)); 
};

//MIN C STRING LIT TOKENS
fragment ILLEGAL_STRING_CHARACTER:;
fragment ESCAPE_SEQUENCES : '\\n' | '\\t' | '\\r' | '\\\\' | '\\"'; 

STRING_LIT: ('"' ( ESCAPE_SEQUENCES | ~('\\' | '"' | '\r' | '\n' | '\r\n') )* '"'){
  if(this.getText().contains("\t")) this.state.type = MinC.ILLEGAL_STRING_CHARACTER;
  else this.state.type = MinC.STRING_LIT; 
  emit(new MinCStringLitToken(input, state, getCharIndex()-1));
};

//any character, or a slash followed by a bad symbol (not one of the good ones)

ILLEGAL_ESCAPE: ('"' ( ESCAPE_SEQUENCES | ~('\\' | '"' | '\r' | '\n' | '\r\n') | ( '\\' ~('t' | 'n' | 'r' | '\\' | '"')))* '"'){
  this.state.type = MinC.ILLEGAL_ESCAPE; 
  emit(new MinCStringLitToken(input, state, getCharIndex()-1));
};

//UNTERMINATED_STRING:{}
UNTERMINATED_STRING: ('"' ( ESCAPE_SEQUENCES | ~('\\' | '"' | '\n' | '\r' | '\r\n') )* ('\n' | '\r' | '\r\n' )){
  this.state.type = MinC.UNTERMINATED_STRING; 
  emit(new MinCStringLitToken(input, state, getCharIndex()-1));
};


//MIN C INT LIT TOKENS
// Integer Literals
INT_LIT : ('0' | ('1'..'9' DIGIT*)) {
  //RANGE_INT_LIT: THIS IS CHECKED IN MinCToken.java
  this.state.type = MinC.INT_LIT; 
  emit(new MinCIntLitToken(input, state, getCharIndex()-1));
} ;

//MAKES SURE WE DON'T START AT 0...
LEADING_INT_LIT: ('0' DIGIT+){
  this.state.type = MinC.LEADING_INT_LIT; 
  emit(new MinCIntLitToken(input, state, getCharIndex()-1));
};

//MIN C FLOAT LIT TOKENS
//front half is either 0, or has no leading 0s
//second half just needs digits
//exponent cannot have an extra 0
FLOAT_LIT: (
              ('1'..'9' DIGIT* | '0') '.' (DIGIT+) (('e' '-'? ('1'..'9' DIGIT* | '0'))?)
           ){
  this.state.type = MinC.FLOAT_LIT; 
	emit(new MinCFloatLitToken(input, state, getCharIndex()-1));
};

//either the first one happens (extra 0 out front)
// or extra 0 on the exponent
LEADING_FLOAT_LIT: (
                      (('0' DIGIT+) '.' (DIGIT+) ('e' '-'? ('1'..'9' DIGIT* | '0'))?) 
                   | 
                      (('1'..'9' DIGIT* | '0') '.' (DIGIT+) ('e' '-'? ('0' DIGIT+))?)
                   )
{
  this.state.type = MinC.LEADING_FLOAT_LIT; 
  emit(new MinCFloatLitToken(input, state, getCharIndex()-1));
};
