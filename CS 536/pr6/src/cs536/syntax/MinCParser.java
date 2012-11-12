// $ANTLR 3.4 /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g 2011-12-16 13:33:19

    // This section is pasted into the top of the generated
    // file.  We use it to declare a package here.  Other packages
    // can also be imported as required.
    package cs536.syntax;

    import static cs536.ast.AbstractSyntaxTree.*;
    import cs536.ast.SourceLocation;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class MinCParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ADDROF", "ALPHA", "ASSIGN", "BAD_IDENT", "BLOCK_COMMENT", "BLOCK_COMMENT_CONTENTS", "BOOL", "COMMA", "DECR", "DIGIT", "DIV", "DOUBLE", "ELSE", "EQUAL", "FALSE", "FLOAT_LIT", "GT", "GTE", "IDENT", "IDENT_CONTINUE", "IDENT_START", "IF", "ILLEGAL_ESCAPE", "ILLEGAL_ESCAPE_STRING_CHAR", "INCR", "INT", "INTEGER", "INT_LIT", "LAND", "LBRACE", "LBRACKET", "LEADING_ZERO_FLOAT", "LEADING_ZERO_INT", "LINE_COMMENT", "LNOT", "LOR", "LOWER", "LPAREN", "LT", "LTE", "MINUS", "MOD", "NEQUAL", "NULL", "PLUS", "RBRACE", "RBRACKET", "READ", "RETURN", "RPAREN", "SEMI", "STAR", "STRING", "STRING_CHAR", "STRING_LIT", "STRING_WITH_ILLEGAL_ESCAPE", "STRING_WITH_TAB", "TAB_STRING_CHAR", "TRUE", "UNTERM_BLOCK_COMMENT", "UNTERM_STRING", "UPPER", "VALID_ESCAPE_CHAR", "VOID", "WHILE", "WHITESPACE", "WRITE"
    };

    public static final int EOF=-1;
    public static final int ADDROF=4;
    public static final int ALPHA=5;
    public static final int ASSIGN=6;
    public static final int BAD_IDENT=7;
    public static final int BLOCK_COMMENT=8;
    public static final int BLOCK_COMMENT_CONTENTS=9;
    public static final int BOOL=10;
    public static final int COMMA=11;
    public static final int DECR=12;
    public static final int DIGIT=13;
    public static final int DIV=14;
    public static final int DOUBLE=15;
    public static final int ELSE=16;
    public static final int EQUAL=17;
    public static final int FALSE=18;
    public static final int FLOAT_LIT=19;
    public static final int GT=20;
    public static final int GTE=21;
    public static final int IDENT=22;
    public static final int IDENT_CONTINUE=23;
    public static final int IDENT_START=24;
    public static final int IF=25;
    public static final int ILLEGAL_ESCAPE=26;
    public static final int ILLEGAL_ESCAPE_STRING_CHAR=27;
    public static final int INCR=28;
    public static final int INT=29;
    public static final int INTEGER=30;
    public static final int INT_LIT=31;
    public static final int LAND=32;
    public static final int LBRACE=33;
    public static final int LBRACKET=34;
    public static final int LEADING_ZERO_FLOAT=35;
    public static final int LEADING_ZERO_INT=36;
    public static final int LINE_COMMENT=37;
    public static final int LNOT=38;
    public static final int LOR=39;
    public static final int LOWER=40;
    public static final int LPAREN=41;
    public static final int LT=42;
    public static final int LTE=43;
    public static final int MINUS=44;
    public static final int MOD=45;
    public static final int NEQUAL=46;
    public static final int NULL=47;
    public static final int PLUS=48;
    public static final int RBRACE=49;
    public static final int RBRACKET=50;
    public static final int READ=51;
    public static final int RETURN=52;
    public static final int RPAREN=53;
    public static final int SEMI=54;
    public static final int STAR=55;
    public static final int STRING=56;
    public static final int STRING_CHAR=57;
    public static final int STRING_LIT=58;
    public static final int STRING_WITH_ILLEGAL_ESCAPE=59;
    public static final int STRING_WITH_TAB=60;
    public static final int TAB_STRING_CHAR=61;
    public static final int TRUE=62;
    public static final int UNTERM_BLOCK_COMMENT=63;
    public static final int UNTERM_STRING=64;
    public static final int UPPER=65;
    public static final int VALID_ESCAPE_CHAR=66;
    public static final int VOID=67;
    public static final int WHILE=68;
    public static final int WHITESPACE=69;
    public static final int WRITE=70;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public MinCParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public MinCParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return MinCParser.tokenNames; }
    public String getGrammarFileName() { return "/afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g"; }


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



    // $ANTLR start "program"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:119:1: program returns [List<GlobalDeclaration> list] : (d= decl )* ;
    public final List<GlobalDeclaration> program() throws RecognitionException {
        List<GlobalDeclaration> list = null;


        GlobalDeclaration d =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:120:5: ( (d= decl )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:120:19: (d= decl )*
            {
             list = new ArrayList<GlobalDeclaration>(); 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:121:9: (d= decl )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==BOOL||LA1_0==DOUBLE||LA1_0==INT||LA1_0==STRING||LA1_0==VOID) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:121:10: d= decl
            	    {
            	    pushFollow(FOLLOW_decl_in_program81);
            	    d=decl();

            	    state._fsp--;


            	     list.add(d); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return list;
    }
    // $ANTLR end "program"



    // $ANTLR start "groundType"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:126:1: groundType returns [TypeNode node] : (l= INT |l= BOOL |l= DOUBLE |l= STRING );
    public final TypeNode groundType() throws RecognitionException {
        TypeNode node = null;


        Token l=null;

        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:127:5: (l= INT |l= BOOL |l= DOUBLE |l= STRING )
            int alt2=4;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt2=1;
                }
                break;
            case BOOL:
                {
                alt2=2;
                }
                break;
            case DOUBLE:
                {
                alt2=3;
                }
                break;
            case STRING:
                {
                alt2=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }

            switch (alt2) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:127:7: l= INT
                    {
                    l=(Token)match(input,INT,FOLLOW_INT_in_groundType120); 

                     node = new IntTypeNode(loc(l));     

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:128:7: l= BOOL
                    {
                    l=(Token)match(input,BOOL,FOLLOW_BOOL_in_groundType137); 

                     node = new BooleanTypeNode(loc(l)); 

                    }
                    break;
                case 3 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:129:7: l= DOUBLE
                    {
                    l=(Token)match(input,DOUBLE,FOLLOW_DOUBLE_in_groundType153); 

                     node = new DoubleTypeNode(loc(l));  

                    }
                    break;
                case 4 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:130:7: l= STRING
                    {
                    l=(Token)match(input,STRING,FOLLOW_STRING_in_groundType167); 

                     node = new StringTypeNode(loc(l));  

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "groundType"



    // $ANTLR start "type"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:133:1: type returns [TypeNode node] : t= groundType ( STAR | LBRACKET size= INT_LIT RBRACKET )* ;
    public final TypeNode type() throws RecognitionException {
        TypeNode node = null;


        Token size=null;
        TypeNode t =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:134:5: (t= groundType ( STAR | LBRACKET size= INT_LIT RBRACKET )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:134:7: t= groundType ( STAR | LBRACKET size= INT_LIT RBRACKET )*
            {
            pushFollow(FOLLOW_groundType_in_type194);
            t=groundType();

            state._fsp--;


             node = t; 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:135:9: ( STAR | LBRACKET size= INT_LIT RBRACKET )*
            loop3:
            do {
                int alt3=3;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==STAR) ) {
                    alt3=1;
                }
                else if ( (LA3_0==LBRACKET) ) {
                    alt3=2;
                }


                switch (alt3) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:135:11: STAR
            	    {
            	    match(input,STAR,FOLLOW_STAR_in_type231); 

            	     node = new PointerTypeNode(t.getLocation(), node);  

            	    }
            	    break;
            	case 2 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:136:11: LBRACKET size= INT_LIT RBRACKET
            	    {
            	    match(input,LBRACKET,FOLLOW_LBRACKET_in_type272); 

            	    size=(Token)match(input,INT_LIT,FOLLOW_INT_LIT_in_type276); 

            	    match(input,RBRACKET,FOLLOW_RBRACKET_in_type278); 

            	     node = new ArrayTypeNode(t.getLocation(), node,
            	                                                                         ((IntLitToken)size).getValue()); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "type"



    // $ANTLR start "funcType"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:141:1: funcType returns [TypeNode node] : (ty= type | VOID );
    public final TypeNode funcType() throws RecognitionException {
        TypeNode node = null;


        TypeNode ty =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:142:5: (ty= type | VOID )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==BOOL||LA4_0==DOUBLE||LA4_0==INT||LA4_0==STRING) ) {
                alt4=1;
            }
            else if ( (LA4_0==VOID) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:142:7: ty= type
                    {
                    pushFollow(FOLLOW_type_in_funcType315);
                    ty=type();

                    state._fsp--;


                     node = ty;   

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:143:7: VOID
                    {
                    match(input,VOID,FOLLOW_VOID_in_funcType326); 

                     node = null; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "funcType"



    // $ANTLR start "localVarDecl"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:146:1: localVarDecl returns [VarDeclStmt node] : (ty= type name= IDENT SEMI |ty= type name= IDENT a= ASSIGN init= exp SEMI );
    public final VarDeclStmt localVarDecl() throws RecognitionException {
        VarDeclStmt node = null;


        Token name=null;
        Token a=null;
        TypeNode ty =null;

        Expression init =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:147:5: (ty= type name= IDENT SEMI |ty= type name= IDENT a= ASSIGN init= exp SEMI )
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:147:7: ty= type name= IDENT SEMI
                    {
                    pushFollow(FOLLOW_type_in_localVarDecl357);
                    ty=type();

                    state._fsp--;


                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_localVarDecl361); 

                    match(input,SEMI,FOLLOW_SEMI_in_localVarDecl363); 

                     node = new VarDeclStmt(loc(name), ty,
                                                                                            name.getText(), null); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:149:7: ty= type name= IDENT a= ASSIGN init= exp SEMI
                    {
                    pushFollow(FOLLOW_type_in_localVarDecl392);
                    ty=type();

                    state._fsp--;


                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_localVarDecl396); 

                    a=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_localVarDecl400); 

                    pushFollow(FOLLOW_exp_in_localVarDecl404);
                    init=exp();

                    state._fsp--;


                    match(input,SEMI,FOLLOW_SEMI_in_localVarDecl406); 

                     node = new VarDeclStmt(loc(a), ty,
                                                                                            name.getText(), init); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "localVarDecl"



    // $ANTLR start "globalVarDecl"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:153:1: globalVarDecl returns [GlobalVarDecl node] : (ty= type name= IDENT SEMI |ty= type name= IDENT a= ASSIGN init= exp SEMI );
    public final GlobalVarDecl globalVarDecl() throws RecognitionException {
        GlobalVarDecl node = null;


        Token name=null;
        Token a=null;
        TypeNode ty =null;

        Expression init =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:154:5: (ty= type name= IDENT SEMI |ty= type name= IDENT a= ASSIGN init= exp SEMI )
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:154:7: ty= type name= IDENT SEMI
                    {
                    pushFollow(FOLLOW_type_in_globalVarDecl432);
                    ty=type();

                    state._fsp--;


                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_globalVarDecl436); 

                    match(input,SEMI,FOLLOW_SEMI_in_globalVarDecl438); 

                     node = new GlobalVarDecl(loc(name), ty,
                                                                                              name.getText(), null); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:156:7: ty= type name= IDENT a= ASSIGN init= exp SEMI
                    {
                    pushFollow(FOLLOW_type_in_globalVarDecl467);
                    ty=type();

                    state._fsp--;


                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_globalVarDecl471); 

                    a=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_globalVarDecl475); 

                    pushFollow(FOLLOW_exp_in_globalVarDecl479);
                    init=exp();

                    state._fsp--;


                    match(input,SEMI,FOLLOW_SEMI_in_globalVarDecl481); 

                     node = new GlobalVarDecl(loc(a), ty,
                                                                                              name.getText(), init); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "globalVarDecl"



    // $ANTLR start "stmt"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:161:1: stmt returns [Statement node] : (d= localVarDecl |r= RETURN SEMI |r= RETURN e= exp SEMI |e= exp SEMI |w= WRITE e= exp SEMI |r= READ name= IDENT SEMI |i= IF LPAREN cond= exp RPAREN thenBody= stmtOrBody ( options {greedy=true; } : ELSE elseBody= stmtOrBody |) |w= WHILE LPAREN cond= exp RPAREN body= stmtOrBody );
    public final Statement stmt() throws RecognitionException {
        Statement node = null;


        Token r=null;
        Token w=null;
        Token name=null;
        Token i=null;
        VarDeclStmt d =null;

        Expression e =null;

        Expression cond =null;

        List<Statement> thenBody =null;

        List<Statement> elseBody =null;

        List<Statement> body =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:162:5: (d= localVarDecl |r= RETURN SEMI |r= RETURN e= exp SEMI |e= exp SEMI |w= WRITE e= exp SEMI |r= READ name= IDENT SEMI |i= IF LPAREN cond= exp RPAREN thenBody= stmtOrBody ( options {greedy=true; } : ELSE elseBody= stmtOrBody |) |w= WHILE LPAREN cond= exp RPAREN body= stmtOrBody )
            int alt8=8;
            switch ( input.LA(1) ) {
            case BOOL:
            case DOUBLE:
            case INT:
            case STRING:
                {
                alt8=1;
                }
                break;
            case RETURN:
                {
                int LA8_2 = input.LA(2);

                if ( (LA8_2==SEMI) ) {
                    alt8=2;
                }
                else if ( (LA8_2==ADDROF||LA8_2==DECR||(LA8_2 >= FALSE && LA8_2 <= FLOAT_LIT)||LA8_2==IDENT||LA8_2==INCR||LA8_2==INT_LIT||LA8_2==LNOT||LA8_2==LPAREN||LA8_2==MINUS||LA8_2==NULL||LA8_2==STAR||LA8_2==STRING_LIT||LA8_2==TRUE) ) {
                    alt8=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 2, input);

                    throw nvae;

                }
                }
                break;
            case ADDROF:
            case DECR:
            case FALSE:
            case FLOAT_LIT:
            case IDENT:
            case INCR:
            case INT_LIT:
            case LNOT:
            case LPAREN:
            case MINUS:
            case NULL:
            case STAR:
            case STRING_LIT:
            case TRUE:
                {
                alt8=4;
                }
                break;
            case WRITE:
                {
                alt8=5;
                }
                break;
            case READ:
                {
                alt8=6;
                }
                break;
            case IF:
                {
                alt8=7;
                }
                break;
            case WHILE:
                {
                alt8=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }

            switch (alt8) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:162:7: d= localVarDecl
                    {
                    pushFollow(FOLLOW_localVarDecl_in_stmt508);
                    d=localVarDecl();

                    state._fsp--;


                     node = d; 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:163:7: r= RETURN SEMI
                    {
                    r=(Token)match(input,RETURN,FOLLOW_RETURN_in_stmt530); 

                    match(input,SEMI,FOLLOW_SEMI_in_stmt532); 

                     node = new ReturnStmt(loc(r), null);           

                    }
                    break;
                case 3 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:164:7: r= RETURN e= exp SEMI
                    {
                    r=(Token)match(input,RETURN,FOLLOW_RETURN_in_stmt557); 

                    pushFollow(FOLLOW_exp_in_stmt561);
                    e=exp();

                    state._fsp--;


                    match(input,SEMI,FOLLOW_SEMI_in_stmt563); 

                     node = new ReturnStmt(loc(r), e);              

                    }
                    break;
                case 4 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:165:7: e= exp SEMI
                    {
                    pushFollow(FOLLOW_exp_in_stmt580);
                    e=exp();

                    state._fsp--;


                    match(input,SEMI,FOLLOW_SEMI_in_stmt582); 

                     node = new ExpressionStmt(e.getLocation(), e); 

                    }
                    break;
                case 5 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:166:7: w= WRITE e= exp SEMI
                    {
                    w=(Token)match(input,WRITE,FOLLOW_WRITE_in_stmt608); 

                    pushFollow(FOLLOW_exp_in_stmt612);
                    e=exp();

                    state._fsp--;


                    match(input,SEMI,FOLLOW_SEMI_in_stmt614); 

                     node = new WriteStmt(loc(w), e);               

                    }
                    break;
                case 6 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:167:7: r= READ name= IDENT SEMI
                    {
                    r=(Token)match(input,READ,FOLLOW_READ_in_stmt632); 

                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_stmt636); 

                    match(input,SEMI,FOLLOW_SEMI_in_stmt638); 

                     node = new ReadStmt(loc(r), name.getText());   

                    }
                    break;
                case 7 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:168:7: i= IF LPAREN cond= exp RPAREN thenBody= stmtOrBody ( options {greedy=true; } : ELSE elseBody= stmtOrBody |)
                    {
                    i=(Token)match(input,IF,FOLLOW_IF_in_stmt652); 

                    match(input,LPAREN,FOLLOW_LPAREN_in_stmt654); 

                    pushFollow(FOLLOW_exp_in_stmt658);
                    cond=exp();

                    state._fsp--;


                    match(input,RPAREN,FOLLOW_RPAREN_in_stmt660); 

                    pushFollow(FOLLOW_stmtOrBody_in_stmt664);
                    thenBody=stmtOrBody();

                    state._fsp--;


                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:169:9: ( options {greedy=true; } : ELSE elseBody= stmtOrBody |)
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==ELSE) ) {
                        alt7=1;
                    }
                    else if ( (LA7_0==ADDROF||LA7_0==BOOL||LA7_0==DECR||LA7_0==DOUBLE||(LA7_0 >= FALSE && LA7_0 <= FLOAT_LIT)||LA7_0==IDENT||LA7_0==IF||(LA7_0 >= INCR && LA7_0 <= INT)||LA7_0==INT_LIT||LA7_0==LNOT||LA7_0==LPAREN||LA7_0==MINUS||LA7_0==NULL||LA7_0==RBRACE||(LA7_0 >= READ && LA7_0 <= RETURN)||(LA7_0 >= STAR && LA7_0 <= STRING)||LA7_0==STRING_LIT||LA7_0==TRUE||LA7_0==WHILE||LA7_0==WRITE) ) {
                        alt7=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 0, input);

                        throw nvae;

                    }
                    switch (alt7) {
                        case 1 :
                            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:170:12: ELSE elseBody= stmtOrBody
                            {
                            match(input,ELSE,FOLLOW_ELSE_in_stmt694); 

                            pushFollow(FOLLOW_stmtOrBody_in_stmt698);
                            elseBody=stmtOrBody();

                            state._fsp--;


                             node = new IfStmt(loc(i), cond, thenBody, elseBody); 

                            }
                            break;
                        case 2 :
                            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:171:38: 
                            {
                             node = new IfStmt(loc(i), cond, thenBody, new ArrayList<Statement>()); 

                            }
                            break;

                    }


                    }
                    break;
                case 8 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:173:7: w= WHILE LPAREN cond= exp RPAREN body= stmtOrBody
                    {
                    w=(Token)match(input,WHILE,FOLLOW_WHILE_in_stmt760); 

                    match(input,LPAREN,FOLLOW_LPAREN_in_stmt762); 

                    pushFollow(FOLLOW_exp_in_stmt766);
                    cond=exp();

                    state._fsp--;


                    match(input,RPAREN,FOLLOW_RPAREN_in_stmt768); 

                    pushFollow(FOLLOW_stmtOrBody_in_stmt772);
                    body=stmtOrBody();

                    state._fsp--;


                     node = new WhileStmt(loc(w), cond, body); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "stmt"



    // $ANTLR start "stmtOrBody"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:176:1: stmtOrBody returns [List<Statement> list] : (s= stmt | LBRACE l= stmtList RBRACE );
    public final List<Statement> stmtOrBody() throws RecognitionException {
        List<Statement> list = null;


        Statement s =null;

        List<Statement> l =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:177:5: (s= stmt | LBRACE l= stmtList RBRACE )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==ADDROF||LA9_0==BOOL||LA9_0==DECR||LA9_0==DOUBLE||(LA9_0 >= FALSE && LA9_0 <= FLOAT_LIT)||LA9_0==IDENT||LA9_0==IF||(LA9_0 >= INCR && LA9_0 <= INT)||LA9_0==INT_LIT||LA9_0==LNOT||LA9_0==LPAREN||LA9_0==MINUS||LA9_0==NULL||(LA9_0 >= READ && LA9_0 <= RETURN)||(LA9_0 >= STAR && LA9_0 <= STRING)||LA9_0==STRING_LIT||LA9_0==TRUE||LA9_0==WHILE||LA9_0==WRITE) ) {
                alt9=1;
            }
            else if ( (LA9_0==LBRACE) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;

            }
            switch (alt9) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:177:7: s= stmt
                    {
                    pushFollow(FOLLOW_stmt_in_stmtOrBody797);
                    s=stmt();

                    state._fsp--;


                     list = makeList(s); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:178:7: LBRACE l= stmtList RBRACE
                    {
                    match(input,LBRACE,FOLLOW_LBRACE_in_stmtOrBody828); 

                    pushFollow(FOLLOW_stmtList_in_stmtOrBody832);
                    l=stmtList();

                    state._fsp--;


                    match(input,RBRACE,FOLLOW_RBRACE_in_stmtOrBody834); 

                     list = l; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return list;
    }
    // $ANTLR end "stmtOrBody"



    // $ANTLR start "stmtList"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:181:1: stmtList returns [List<Statement> list] : (s= stmt )* ;
    public final List<Statement> stmtList() throws RecognitionException {
        List<Statement> list = null;


        Statement s =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:182:5: ( (s= stmt )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:182:21: (s= stmt )*
            {
             list = new ArrayList<Statement>(); 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:183:9: (s= stmt )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==ADDROF||LA10_0==BOOL||LA10_0==DECR||LA10_0==DOUBLE||(LA10_0 >= FALSE && LA10_0 <= FLOAT_LIT)||LA10_0==IDENT||LA10_0==IF||(LA10_0 >= INCR && LA10_0 <= INT)||LA10_0==INT_LIT||LA10_0==LNOT||LA10_0==LPAREN||LA10_0==MINUS||LA10_0==NULL||(LA10_0 >= READ && LA10_0 <= RETURN)||(LA10_0 >= STAR && LA10_0 <= STRING)||LA10_0==STRING_LIT||LA10_0==TRUE||LA10_0==WHILE||LA10_0==WRITE) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:183:10: s= stmt
            	    {
            	    pushFollow(FOLLOW_stmt_in_stmtList887);
            	    s=stmt();

            	    state._fsp--;


            	     list.add(s); 

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return list;
    }
    // $ANTLR end "stmtList"



    // $ANTLR start "formalList"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:188:1: formalList returns [List<FormalArg> list] : (|f= formal ( COMMA f= formal )* );
    public final List<FormalArg> formalList() throws RecognitionException {
        List<FormalArg> list = null;


        FormalArg f =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:189:5: (|f= formal ( COMMA f= formal )* )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==RPAREN) ) {
                alt12=1;
            }
            else if ( (LA12_0==BOOL||LA12_0==DOUBLE||LA12_0==INT||LA12_0==STRING) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;

            }
            switch (alt12) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:189:27: 
                    {
                     list = new ArrayList<FormalArg>(); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:190:7: f= formal ( COMMA f= formal )*
                    {
                    pushFollow(FOLLOW_formal_in_formalList944);
                    f=formal();

                    state._fsp--;


                     list = makeList(f); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:191:9: ( COMMA f= formal )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0==COMMA) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:191:10: COMMA f= formal
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_formalList968); 

                    	    pushFollow(FOLLOW_formal_in_formalList972);
                    	    f=formal();

                    	    state._fsp--;


                    	     list.add(f); 

                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return list;
    }
    // $ANTLR end "formalList"



    // $ANTLR start "formal"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:195:1: formal returns [FormalArg node] : ty= type name= IDENT ;
    public final FormalArg formal() throws RecognitionException {
        FormalArg node = null;


        Token name=null;
        TypeNode ty =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:196:5: (ty= type name= IDENT )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:196:7: ty= type name= IDENT
            {
            pushFollow(FOLLOW_type_in_formal1010);
            ty=type();

            state._fsp--;


            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_formal1014); 

             node = new FormalArg(loc(name), ty, name.getText()); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "formal"



    // $ANTLR start "funcDecl"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:199:1: funcDecl returns [FuncDecl node] : ret= funcType name= IDENT LPAREN args= formalList RPAREN LBRACE body= stmtList RBRACE ;
    public final FuncDecl funcDecl() throws RecognitionException {
        FuncDecl node = null;


        Token name=null;
        TypeNode ret =null;

        List<FormalArg> args =null;

        List<Statement> body =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:200:5: (ret= funcType name= IDENT LPAREN args= formalList RPAREN LBRACE body= stmtList RBRACE )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:200:7: ret= funcType name= IDENT LPAREN args= formalList RPAREN LBRACE body= stmtList RBRACE
            {
            pushFollow(FOLLOW_funcType_in_funcDecl1046);
            ret=funcType();

            state._fsp--;


            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_funcDecl1050); 

            match(input,LPAREN,FOLLOW_LPAREN_in_funcDecl1052); 

            pushFollow(FOLLOW_formalList_in_funcDecl1056);
            args=formalList();

            state._fsp--;


            match(input,RPAREN,FOLLOW_RPAREN_in_funcDecl1058); 

            match(input,LBRACE,FOLLOW_LBRACE_in_funcDecl1060); 

            pushFollow(FOLLOW_stmtList_in_funcDecl1064);
            body=stmtList();

            state._fsp--;


            match(input,RBRACE,FOLLOW_RBRACE_in_funcDecl1066); 

             node = new FuncDecl(loc(name), ret, name.getText(), args, body); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "funcDecl"



    // $ANTLR start "decl"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:205:1: decl returns [GlobalDeclaration node] : (v= globalVarDecl |f= funcDecl );
    public final GlobalDeclaration decl() throws RecognitionException {
        GlobalDeclaration node = null;


        GlobalVarDecl v =null;

        FuncDecl f =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:206:5: (v= globalVarDecl |f= funcDecl )
            int alt13=2;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:206:7: v= globalVarDecl
                    {
                    pushFollow(FOLLOW_globalVarDecl_in_decl1100);
                    v=globalVarDecl();

                    state._fsp--;


                     node = v; 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:207:7: f= funcDecl
                    {
                    pushFollow(FOLLOW_funcDecl_in_decl1115);
                    f=funcDecl();

                    state._fsp--;


                     node = f; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "decl"



    // $ANTLR start "actualsList"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:211:1: actualsList returns [List<Expression> list] : (|e= exp ( COMMA e= exp )* );
    public final List<Expression> actualsList() throws RecognitionException {
        List<Expression> list = null;


        Expression e =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:212:5: (|e= exp ( COMMA e= exp )* )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==RPAREN) ) {
                alt15=1;
            }
            else if ( (LA15_0==ADDROF||LA15_0==DECR||(LA15_0 >= FALSE && LA15_0 <= FLOAT_LIT)||LA15_0==IDENT||LA15_0==INCR||LA15_0==INT_LIT||LA15_0==LNOT||LA15_0==LPAREN||LA15_0==MINUS||LA15_0==NULL||LA15_0==STAR||LA15_0==STRING_LIT||LA15_0==TRUE) ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }
            switch (alt15) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:212:24: 
                    {
                     list = new ArrayList<Expression>(); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:213:7: e= exp ( COMMA e= exp )*
                    {
                    pushFollow(FOLLOW_exp_in_actualsList1162);
                    e=exp();

                    state._fsp--;


                     list = makeList(e); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:214:7: ( COMMA e= exp )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==COMMA) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:214:8: COMMA e= exp
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_actualsList1184); 

                    	    pushFollow(FOLLOW_exp_in_actualsList1188);
                    	    e=exp();

                    	    state._fsp--;


                    	     list.add(e); 

                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return list;
    }
    // $ANTLR end "actualsList"



    // $ANTLR start "literalExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:218:1: literalExp returns [LiteralExp node] : (l= NULL |l= TRUE |l= FALSE |l= STRING_LIT |l= INT_LIT |l= FLOAT_LIT );
    public final LiteralExp literalExp() throws RecognitionException {
        LiteralExp node = null;


        Token l=null;

        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:219:5: (l= NULL |l= TRUE |l= FALSE |l= STRING_LIT |l= INT_LIT |l= FLOAT_LIT )
            int alt16=6;
            switch ( input.LA(1) ) {
            case NULL:
                {
                alt16=1;
                }
                break;
            case TRUE:
                {
                alt16=2;
                }
                break;
            case FALSE:
                {
                alt16=3;
                }
                break;
            case STRING_LIT:
                {
                alt16=4;
                }
                break;
            case INT_LIT:
                {
                alt16=5;
                }
                break;
            case FLOAT_LIT:
                {
                alt16=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }

            switch (alt16) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:219:7: l= NULL
                    {
                    l=(Token)match(input,NULL,FOLLOW_NULL_in_literalExp1226); 

                     node = new NullLit(loc(l));                

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:220:7: l= TRUE
                    {
                    l=(Token)match(input,TRUE,FOLLOW_TRUE_in_literalExp1253); 

                     node = new BooleanLit(loc(l), true);       

                    }
                    break;
                case 3 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:221:7: l= FALSE
                    {
                    l=(Token)match(input,FALSE,FOLLOW_FALSE_in_literalExp1280); 

                     node = new BooleanLit(loc(l), false);      

                    }
                    break;
                case 4 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:222:7: l= STRING_LIT
                    {
                    l=(Token)match(input,STRING_LIT,FOLLOW_STRING_LIT_in_literalExp1306); 

                     node = new StringLit(loc(l), l.getText()); 

                    }
                    break;
                case 5 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:223:7: l= INT_LIT
                    {
                    l=(Token)match(input,INT_LIT,FOLLOW_INT_LIT_in_literalExp1327); 

                     node = new IntLit(loc(l),    ((IntLitToken)l).getValue());   

                    }
                    break;
                case 6 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:224:7: l= FLOAT_LIT
                    {
                    l=(Token)match(input,FLOAT_LIT,FOLLOW_FLOAT_LIT_in_literalExp1351); 

                     node = new DoubleLit(loc(l), ((FloatLitToken)l).getValue()); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "literalExp"



    // $ANTLR start "atomExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:241:1: atomExp returns [Expression node] : (lit= literalExp |id= IDENT | LPAREN e= exp RPAREN );
    public final Expression atomExp() throws RecognitionException {
        Expression node = null;


        Token id=null;
        LiteralExp lit =null;

        Expression e =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:242:5: (lit= literalExp |id= IDENT | LPAREN e= exp RPAREN )
            int alt17=3;
            switch ( input.LA(1) ) {
            case FALSE:
            case FLOAT_LIT:
            case INT_LIT:
            case NULL:
            case STRING_LIT:
            case TRUE:
                {
                alt17=1;
                }
                break;
            case IDENT:
                {
                alt17=2;
                }
                break;
            case LPAREN:
                {
                alt17=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;

            }

            switch (alt17) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:242:7: lit= literalExp
                    {
                    pushFollow(FOLLOW_literalExp_in_atomExp1400);
                    lit=literalExp();

                    state._fsp--;


                     node = lit; 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:243:7: id= IDENT
                    {
                    id=(Token)match(input,IDENT,FOLLOW_IDENT_in_atomExp1424); 

                     node = new VarRefExp(loc(id), id.getText()); 

                    }
                    break;
                case 3 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:244:7: LPAREN e= exp RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_atomExp1452); 

                    pushFollow(FOLLOW_exp_in_atomExp1456);
                    e=exp();

                    state._fsp--;


                    match(input,RPAREN,FOLLOW_RPAREN_in_atomExp1458); 

                     node = e; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "atomExp"



    // $ANTLR start "arrayFunctionExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:247:1: arrayFunctionExp returns [Expression node] : (e= atomExp (b= LBRACKET idx= exp RBRACKET )* |name= IDENT LPAREN args= actualsList RPAREN );
    public final Expression arrayFunctionExp() throws RecognitionException {
        Expression node = null;


        Token b=null;
        Token name=null;
        Expression e =null;

        Expression idx =null;

        List<Expression> args =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:248:5: (e= atomExp (b= LBRACKET idx= exp RBRACKET )* |name= IDENT LPAREN args= actualsList RPAREN )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( ((LA19_0 >= FALSE && LA19_0 <= FLOAT_LIT)||LA19_0==INT_LIT||LA19_0==LPAREN||LA19_0==NULL||LA19_0==STRING_LIT||LA19_0==TRUE) ) {
                alt19=1;
            }
            else if ( (LA19_0==IDENT) ) {
                int LA19_2 = input.LA(2);

                if ( (LA19_2==LPAREN) ) {
                    alt19=2;
                }
                else if ( (LA19_2==ASSIGN||(LA19_2 >= COMMA && LA19_2 <= DECR)||LA19_2==DIV||LA19_2==EQUAL||(LA19_2 >= GT && LA19_2 <= GTE)||LA19_2==INCR||LA19_2==LAND||LA19_2==LBRACKET||LA19_2==LOR||(LA19_2 >= LT && LA19_2 <= NEQUAL)||LA19_2==PLUS||LA19_2==RBRACKET||(LA19_2 >= RPAREN && LA19_2 <= STAR)) ) {
                    alt19=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 19, 2, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;

            }
            switch (alt19) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:248:7: e= atomExp (b= LBRACKET idx= exp RBRACKET )*
                    {
                    pushFollow(FOLLOW_atomExp_in_arrayFunctionExp1490);
                    e=atomExp();

                    state._fsp--;


                     node = e; 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:249:9: (b= LBRACKET idx= exp RBRACKET )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==LBRACKET) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:249:11: b= LBRACKET idx= exp RBRACKET
                    	    {
                    	    b=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_arrayFunctionExp1533); 

                    	    pushFollow(FOLLOW_exp_in_arrayFunctionExp1537);
                    	    idx=exp();

                    	    state._fsp--;


                    	    match(input,RBRACKET,FOLLOW_RBRACKET_in_arrayFunctionExp1539); 

                    	     node = new ArrayAccessOp(loc(b), node, idx); 

                    	    }
                    	    break;

                    	default :
                    	    break loop18;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:251:7: name= IDENT LPAREN args= actualsList RPAREN
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_arrayFunctionExp1567); 

                    match(input,LPAREN,FOLLOW_LPAREN_in_arrayFunctionExp1569); 

                    pushFollow(FOLLOW_actualsList_in_arrayFunctionExp1573);
                    args=actualsList();

                    state._fsp--;


                    match(input,RPAREN,FOLLOW_RPAREN_in_arrayFunctionExp1575); 

                     node = new FunctionCallExp(loc(name), name.getText(), args); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "arrayFunctionExp"



    // $ANTLR start "postfixExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:254:1: postfixExp returns [Expression node] : e= arrayFunctionExp (i= INCR |d= DECR )* ;
    public final Expression postfixExp() throws RecognitionException {
        Expression node = null;


        Token i=null;
        Token d=null;
        Expression e =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:255:5: (e= arrayFunctionExp (i= INCR |d= DECR )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:255:7: e= arrayFunctionExp (i= INCR |d= DECR )*
            {
            pushFollow(FOLLOW_arrayFunctionExp_in_postfixExp1601);
            e=arrayFunctionExp();

            state._fsp--;


             node = e; 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:256:9: (i= INCR |d= DECR )*
            loop20:
            do {
                int alt20=3;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==INCR) ) {
                    alt20=1;
                }
                else if ( (LA20_0==DECR) ) {
                    alt20=2;
                }


                switch (alt20) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:256:10: i= INCR
            	    {
            	    i=(Token)match(input,INCR,FOLLOW_INCR_in_postfixExp1622); 

            	     node = new IncrementOp(loc(i), node, false); 

            	    }
            	    break;
            	case 2 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:257:11: d= DECR
            	    {
            	    d=(Token)match(input,DECR,FOLLOW_DECR_in_postfixExp1653); 

            	     node = new DecrementOp(loc(d), node, false); 

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "postfixExp"



    // $ANTLR start "prefixExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:261:1: prefixExp returns [Expression node] : (i= INCR e= prefixExp |d= DECR e= prefixExp |e= postfixExp );
    public final Expression prefixExp() throws RecognitionException {
        Expression node = null;


        Token i=null;
        Token d=null;
        Expression e =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:262:5: (i= INCR e= prefixExp |d= DECR e= prefixExp |e= postfixExp )
            int alt21=3;
            switch ( input.LA(1) ) {
            case INCR:
                {
                alt21=1;
                }
                break;
            case DECR:
                {
                alt21=2;
                }
                break;
            case FALSE:
            case FLOAT_LIT:
            case IDENT:
            case INT_LIT:
            case LPAREN:
            case NULL:
            case STRING_LIT:
            case TRUE:
                {
                alt21=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;

            }

            switch (alt21) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:262:7: i= INCR e= prefixExp
                    {
                    i=(Token)match(input,INCR,FOLLOW_INCR_in_prefixExp1703); 

                    pushFollow(FOLLOW_prefixExp_in_prefixExp1707);
                    e=prefixExp();

                    state._fsp--;


                     node = new IncrementOp(loc(i), e, true); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:263:7: d= DECR e= prefixExp
                    {
                    d=(Token)match(input,DECR,FOLLOW_DECR_in_prefixExp1724); 

                    pushFollow(FOLLOW_prefixExp_in_prefixExp1728);
                    e=prefixExp();

                    state._fsp--;


                     node = new DecrementOp(loc(d), e, true); 

                    }
                    break;
                case 3 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:264:7: e= postfixExp
                    {
                    pushFollow(FOLLOW_postfixExp_in_prefixExp1745);
                    e=postfixExp();

                    state._fsp--;


                     node = e; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "prefixExp"



    // $ANTLR start "minusNotExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:267:1: minusNotExp returns [Expression node] : (m= MINUS e= minusNotExp |n= LNOT e= minusNotExp |e= prefixExp );
    public final Expression minusNotExp() throws RecognitionException {
        Expression node = null;


        Token m=null;
        Token n=null;
        Expression e =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:268:5: (m= MINUS e= minusNotExp |n= LNOT e= minusNotExp |e= prefixExp )
            int alt22=3;
            switch ( input.LA(1) ) {
            case MINUS:
                {
                alt22=1;
                }
                break;
            case LNOT:
                {
                alt22=2;
                }
                break;
            case DECR:
            case FALSE:
            case FLOAT_LIT:
            case IDENT:
            case INCR:
            case INT_LIT:
            case LPAREN:
            case NULL:
            case STRING_LIT:
            case TRUE:
                {
                alt22=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;

            }

            switch (alt22) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:268:7: m= MINUS e= minusNotExp
                    {
                    m=(Token)match(input,MINUS,FOLLOW_MINUS_in_minusNotExp1782); 

                    pushFollow(FOLLOW_minusNotExp_in_minusNotExp1786);
                    e=minusNotExp();

                    state._fsp--;


                     node = new NegationOp(loc(m), e); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:269:7: n= LNOT e= minusNotExp
                    {
                    n=(Token)match(input,LNOT,FOLLOW_LNOT_in_minusNotExp1801); 

                    pushFollow(FOLLOW_minusNotExp_in_minusNotExp1805);
                    e=minusNotExp();

                    state._fsp--;


                     node = new NotOp(loc(n), e);      

                    }
                    break;
                case 3 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:270:7: e= prefixExp
                    {
                    pushFollow(FOLLOW_prefixExp_in_minusNotExp1821);
                    e=prefixExp();

                    state._fsp--;


                     node = e; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "minusNotExp"



    // $ANTLR start "derefExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:273:1: derefExp returns [Expression node] : (s= STAR e= derefExp |e= minusNotExp );
    public final Expression derefExp() throws RecognitionException {
        Expression node = null;


        Token s=null;
        Expression e =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:274:5: (s= STAR e= derefExp |e= minusNotExp )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==STAR) ) {
                alt23=1;
            }
            else if ( (LA23_0==DECR||(LA23_0 >= FALSE && LA23_0 <= FLOAT_LIT)||LA23_0==IDENT||LA23_0==INCR||LA23_0==INT_LIT||LA23_0==LNOT||LA23_0==LPAREN||LA23_0==MINUS||LA23_0==NULL||LA23_0==STRING_LIT||LA23_0==TRUE) ) {
                alt23=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;

            }
            switch (alt23) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:274:7: s= STAR e= derefExp
                    {
                    s=(Token)match(input,STAR,FOLLOW_STAR_in_derefExp1859); 

                    pushFollow(FOLLOW_derefExp_in_derefExp1863);
                    e=derefExp();

                    state._fsp--;


                     node = new DerefOp(loc(s), e); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:275:7: e= minusNotExp
                    {
                    pushFollow(FOLLOW_minusNotExp_in_derefExp1881);
                    e=minusNotExp();

                    state._fsp--;


                     node = e; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "derefExp"



    // $ANTLR start "addrOfExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:278:1: addrOfExp returns [Expression node] : (a= ADDROF e= addrOfExp |e= derefExp );
    public final Expression addrOfExp() throws RecognitionException {
        Expression node = null;


        Token a=null;
        Expression e =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:279:5: (a= ADDROF e= addrOfExp |e= derefExp )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==ADDROF) ) {
                alt24=1;
            }
            else if ( (LA24_0==DECR||(LA24_0 >= FALSE && LA24_0 <= FLOAT_LIT)||LA24_0==IDENT||LA24_0==INCR||LA24_0==INT_LIT||LA24_0==LNOT||LA24_0==LPAREN||LA24_0==MINUS||LA24_0==NULL||LA24_0==STAR||LA24_0==STRING_LIT||LA24_0==TRUE) ) {
                alt24=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;

            }
            switch (alt24) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:279:7: a= ADDROF e= addrOfExp
                    {
                    a=(Token)match(input,ADDROF,FOLLOW_ADDROF_in_addrOfExp1916); 

                    pushFollow(FOLLOW_addrOfExp_in_addrOfExp1920);
                    e=addrOfExp();

                    state._fsp--;


                     node = new AddrOfOp(loc(a), e); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:280:7: e= derefExp
                    {
                    pushFollow(FOLLOW_derefExp_in_addrOfExp1935);
                    e=derefExp();

                    state._fsp--;


                     node = e; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "addrOfExp"



    // $ANTLR start "multExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:283:1: multExp returns [Expression node] : e= addrOfExp (s= STAR r= addrOfExp |d= DIV r= addrOfExp |m= MOD r= addrOfExp )* ;
    public final Expression multExp() throws RecognitionException {
        Expression node = null;


        Token s=null;
        Token d=null;
        Token m=null;
        Expression e =null;

        Expression r =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:284:5: (e= addrOfExp (s= STAR r= addrOfExp |d= DIV r= addrOfExp |m= MOD r= addrOfExp )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:284:7: e= addrOfExp (s= STAR r= addrOfExp |d= DIV r= addrOfExp |m= MOD r= addrOfExp )*
            {
            pushFollow(FOLLOW_addrOfExp_in_multExp1973);
            e=addrOfExp();

            state._fsp--;


             node = e; 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:285:9: (s= STAR r= addrOfExp |d= DIV r= addrOfExp |m= MOD r= addrOfExp )*
            loop25:
            do {
                int alt25=4;
                switch ( input.LA(1) ) {
                case STAR:
                    {
                    alt25=1;
                    }
                    break;
                case DIV:
                    {
                    alt25=2;
                    }
                    break;
                case MOD:
                    {
                    alt25=3;
                    }
                    break;

                }

                switch (alt25) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:285:11: s= STAR r= addrOfExp
            	    {
            	    s=(Token)match(input,STAR,FOLLOW_STAR_in_multExp2003); 

            	    pushFollow(FOLLOW_addrOfExp_in_multExp2007);
            	    r=addrOfExp();

            	    state._fsp--;


            	     node = new MultiplyOp(loc(s), node, r); 

            	    }
            	    break;
            	case 2 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:286:11: d= DIV r= addrOfExp
            	    {
            	    d=(Token)match(input,DIV,FOLLOW_DIV_in_multExp2026); 

            	    pushFollow(FOLLOW_addrOfExp_in_multExp2030);
            	    r=addrOfExp();

            	    state._fsp--;


            	     node = new DivideOp(loc(d), node, r); 

            	    }
            	    break;
            	case 3 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:287:11: m= MOD r= addrOfExp
            	    {
            	    m=(Token)match(input,MOD,FOLLOW_MOD_in_multExp2050); 

            	    pushFollow(FOLLOW_addrOfExp_in_multExp2054);
            	    r=addrOfExp();

            	    state._fsp--;


            	     node = new ModOp(loc(m), node, r); 

            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "multExp"



    // $ANTLR start "addExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:291:1: addExp returns [Expression node] : e= multExp (p= PLUS r= multExp |m= MINUS r= multExp )* ;
    public final Expression addExp() throws RecognitionException {
        Expression node = null;


        Token p=null;
        Token m=null;
        Expression e =null;

        Expression r =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:292:5: (e= multExp (p= PLUS r= multExp |m= MINUS r= multExp )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:292:7: e= multExp (p= PLUS r= multExp |m= MINUS r= multExp )*
            {
            pushFollow(FOLLOW_multExp_in_addExp2094);
            e=multExp();

            state._fsp--;


             node = e; 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:293:9: (p= PLUS r= multExp |m= MINUS r= multExp )*
            loop26:
            do {
                int alt26=3;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==PLUS) ) {
                    alt26=1;
                }
                else if ( (LA26_0==MINUS) ) {
                    alt26=2;
                }


                switch (alt26) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:293:11: p= PLUS r= multExp
            	    {
            	    p=(Token)match(input,PLUS,FOLLOW_PLUS_in_addExp2126); 

            	    pushFollow(FOLLOW_multExp_in_addExp2130);
            	    r=multExp();

            	    state._fsp--;


            	     node = new AddOp(loc(p), node, r); 

            	    }
            	    break;
            	case 2 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:294:11: m= MINUS r= multExp
            	    {
            	    m=(Token)match(input,MINUS,FOLLOW_MINUS_in_addExp2151); 

            	    pushFollow(FOLLOW_multExp_in_addExp2155);
            	    r=multExp();

            	    state._fsp--;


            	     node = new SubtractOp(loc(m), node, r); 

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "addExp"



    // $ANTLR start "inequalityExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:298:1: inequalityExp returns [Expression node] : e= addExp (a= LT r= addExp |b= LTE r= addExp |c= GT r= addExp |d= GTE r= addExp )? ;
    public final Expression inequalityExp() throws RecognitionException {
        Expression node = null;


        Token a=null;
        Token b=null;
        Token c=null;
        Token d=null;
        Expression e =null;

        Expression r =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:299:5: (e= addExp (a= LT r= addExp |b= LTE r= addExp |c= GT r= addExp |d= GTE r= addExp )? )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:299:7: e= addExp (a= LT r= addExp |b= LTE r= addExp |c= GT r= addExp |d= GTE r= addExp )?
            {
            pushFollow(FOLLOW_addExp_in_inequalityExp2195);
            e=addExp();

            state._fsp--;


             node = e; 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:300:9: (a= LT r= addExp |b= LTE r= addExp |c= GT r= addExp |d= GTE r= addExp )?
            int alt27=5;
            switch ( input.LA(1) ) {
                case LT:
                    {
                    alt27=1;
                    }
                    break;
                case LTE:
                    {
                    alt27=2;
                    }
                    break;
                case GT:
                    {
                    alt27=3;
                    }
                    break;
                case GTE:
                    {
                    alt27=4;
                    }
                    break;
            }

            switch (alt27) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:300:11: a= LT r= addExp
                    {
                    a=(Token)match(input,LT,FOLLOW_LT_in_inequalityExp2224); 

                    pushFollow(FOLLOW_addExp_in_inequalityExp2228);
                    r=addExp();

                    state._fsp--;


                     node = new LessThanOp(loc(a), node, r); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:301:11: b= LTE r= addExp
                    {
                    b=(Token)match(input,LTE,FOLLOW_LTE_in_inequalityExp2248); 

                    pushFollow(FOLLOW_addExp_in_inequalityExp2252);
                    r=addExp();

                    state._fsp--;


                     node = new LessOrEqualOp(loc(b), node, r); 

                    }
                    break;
                case 3 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:302:11: c= GT r= addExp
                    {
                    c=(Token)match(input,GT,FOLLOW_GT_in_inequalityExp2271); 

                    pushFollow(FOLLOW_addExp_in_inequalityExp2275);
                    r=addExp();

                    state._fsp--;


                     node = new GreaterThanOp(loc(c), node, r); 

                    }
                    break;
                case 4 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:303:11: d= GTE r= addExp
                    {
                    d=(Token)match(input,GTE,FOLLOW_GTE_in_inequalityExp2295); 

                    pushFollow(FOLLOW_addExp_in_inequalityExp2299);
                    r=addExp();

                    state._fsp--;


                     node = new GreaterOrEqualOp(loc(d), node, r); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "inequalityExp"



    // $ANTLR start "equalityExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:307:1: equalityExp returns [Expression node] : e= inequalityExp (q= EQUAL r= inequalityExp |n= NEQUAL r= inequalityExp )* ;
    public final Expression equalityExp() throws RecognitionException {
        Expression node = null;


        Token q=null;
        Token n=null;
        Expression e =null;

        Expression r =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:308:5: (e= inequalityExp (q= EQUAL r= inequalityExp |n= NEQUAL r= inequalityExp )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:308:7: e= inequalityExp (q= EQUAL r= inequalityExp |n= NEQUAL r= inequalityExp )*
            {
            pushFollow(FOLLOW_inequalityExp_in_equalityExp2338);
            e=inequalityExp();

            state._fsp--;


             node = e; 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:309:9: (q= EQUAL r= inequalityExp |n= NEQUAL r= inequalityExp )*
            loop28:
            do {
                int alt28=3;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==EQUAL) ) {
                    alt28=1;
                }
                else if ( (LA28_0==NEQUAL) ) {
                    alt28=2;
                }


                switch (alt28) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:309:11: q= EQUAL r= inequalityExp
            	    {
            	    q=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_equalityExp2368); 

            	    pushFollow(FOLLOW_inequalityExp_in_equalityExp2372);
            	    r=inequalityExp();

            	    state._fsp--;


            	     node = new EqualsOp(loc(q), node, r); 

            	    }
            	    break;
            	case 2 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:310:11: n= NEQUAL r= inequalityExp
            	    {
            	    n=(Token)match(input,NEQUAL,FOLLOW_NEQUAL_in_equalityExp2390); 

            	    pushFollow(FOLLOW_inequalityExp_in_equalityExp2394);
            	    r=inequalityExp();

            	    state._fsp--;


            	     node = new NotEqualsOp(loc(n), node, r); 

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "equalityExp"



    // $ANTLR start "andExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:314:1: andExp returns [Expression node] : e= equalityExp (o= LAND r= equalityExp )* ;
    public final Expression andExp() throws RecognitionException {
        Expression node = null;


        Token o=null;
        Expression e =null;

        Expression r =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:315:5: (e= equalityExp (o= LAND r= equalityExp )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:315:7: e= equalityExp (o= LAND r= equalityExp )*
            {
            pushFollow(FOLLOW_equalityExp_in_andExp2431);
            e=equalityExp();

            state._fsp--;


             node = e; 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:316:9: (o= LAND r= equalityExp )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==LAND) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:316:11: o= LAND r= equalityExp
            	    {
            	    o=(Token)match(input,LAND,FOLLOW_LAND_in_andExp2459); 

            	    pushFollow(FOLLOW_equalityExp_in_andExp2463);
            	    r=equalityExp();

            	    state._fsp--;


            	     node = new AndOp(loc(o), node, r); 

            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "andExp"



    // $ANTLR start "orExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:320:1: orExp returns [Expression node] : e= andExp (o= LOR r= andExp )* ;
    public final Expression orExp() throws RecognitionException {
        Expression node = null;


        Token o=null;
        Expression e =null;

        Expression r =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:321:5: (e= andExp (o= LOR r= andExp )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:321:7: e= andExp (o= LOR r= andExp )*
            {
            pushFollow(FOLLOW_andExp_in_orExp2500);
            e=andExp();

            state._fsp--;


             node = e; 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:322:9: (o= LOR r= andExp )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==LOR) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:322:11: o= LOR r= andExp
            	    {
            	    o=(Token)match(input,LOR,FOLLOW_LOR_in_orExp2533); 

            	    pushFollow(FOLLOW_andExp_in_orExp2537);
            	    r=andExp();

            	    state._fsp--;


            	     node = new OrOp(loc(o), node, r); 

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "orExp"



    // $ANTLR start "assignExp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:326:1: assignExp returns [Expression node] : e= orExp (a= ASSIGN r= assignExp )? ;
    public final Expression assignExp() throws RecognitionException {
        Expression node = null;


        Token a=null;
        Expression e =null;

        Expression r =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:327:5: (e= orExp (a= ASSIGN r= assignExp )? )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:327:7: e= orExp (a= ASSIGN r= assignExp )?
            {
            pushFollow(FOLLOW_orExp_in_assignExp2580);
            e=orExp();

            state._fsp--;


             node = e; 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:328:9: (a= ASSIGN r= assignExp )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==ASSIGN) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:328:10: a= ASSIGN r= assignExp
                    {
                    a=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_assignExp2611); 

                    pushFollow(FOLLOW_assignExp_in_assignExp2615);
                    r=assignExp();

                    state._fsp--;


                     node = new AssignOp(loc(a), node, r); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "assignExp"



    // $ANTLR start "exp"
    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:333:1: exp returns [Expression node] : e= assignExp ;
    public final Expression exp() throws RecognitionException {
        Expression node = null;


        Expression e =null;


        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:334:5: (e= assignExp )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:334:7: e= assignExp
            {
            pushFollow(FOLLOW_assignExp_in_exp2652);
            e=assignExp();

            state._fsp--;


             node = e; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return node;
    }
    // $ANTLR end "exp"

    // Delegated rules


    protected DFA5 dfa5 = new DFA5(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA5_eotS =
        "\14\uffff";
    static final String DFA5_eofS =
        "\14\uffff";
    static final String DFA5_minS =
        "\1\12\5\26\1\37\1\6\1\62\2\uffff\1\26";
    static final String DFA5_maxS =
        "\1\70\5\67\1\37\1\66\1\62\2\uffff\1\67";
    static final String DFA5_acceptS =
        "\11\uffff\1\1\1\2\1\uffff";
    static final String DFA5_specialS =
        "\14\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\2\4\uffff\1\3\15\uffff\1\1\32\uffff\1\4",
            "\1\7\13\uffff\1\6\24\uffff\1\5",
            "\1\7\13\uffff\1\6\24\uffff\1\5",
            "\1\7\13\uffff\1\6\24\uffff\1\5",
            "\1\7\13\uffff\1\6\24\uffff\1\5",
            "\1\7\13\uffff\1\6\24\uffff\1\5",
            "\1\10",
            "\1\12\57\uffff\1\11",
            "\1\13",
            "",
            "",
            "\1\7\13\uffff\1\6\24\uffff\1\5"
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "146:1: localVarDecl returns [VarDeclStmt node] : (ty= type name= IDENT SEMI |ty= type name= IDENT a= ASSIGN init= exp SEMI );";
        }
    }
    static final String DFA6_eotS =
        "\14\uffff";
    static final String DFA6_eofS =
        "\14\uffff";
    static final String DFA6_minS =
        "\1\12\5\26\1\37\1\6\1\62\2\uffff\1\26";
    static final String DFA6_maxS =
        "\1\70\5\67\1\37\1\66\1\62\2\uffff\1\67";
    static final String DFA6_acceptS =
        "\11\uffff\1\1\1\2\1\uffff";
    static final String DFA6_specialS =
        "\14\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\2\4\uffff\1\3\15\uffff\1\1\32\uffff\1\4",
            "\1\7\13\uffff\1\6\24\uffff\1\5",
            "\1\7\13\uffff\1\6\24\uffff\1\5",
            "\1\7\13\uffff\1\6\24\uffff\1\5",
            "\1\7\13\uffff\1\6\24\uffff\1\5",
            "\1\7\13\uffff\1\6\24\uffff\1\5",
            "\1\10",
            "\1\12\57\uffff\1\11",
            "\1\13",
            "",
            "",
            "\1\7\13\uffff\1\6\24\uffff\1\5"
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
        int numStates = DFA6_transitionS.length;
        DFA6_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
        }
    }

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA6_eot;
            this.eof = DFA6_eof;
            this.min = DFA6_min;
            this.max = DFA6_max;
            this.accept = DFA6_accept;
            this.special = DFA6_special;
            this.transition = DFA6_transition;
        }
        public String getDescription() {
            return "153:1: globalVarDecl returns [GlobalVarDecl node] : (ty= type name= IDENT SEMI |ty= type name= IDENT a= ASSIGN init= exp SEMI );";
        }
    }
    static final String DFA13_eotS =
        "\14\uffff";
    static final String DFA13_eofS =
        "\14\uffff";
    static final String DFA13_minS =
        "\1\12\4\26\1\uffff\1\26\1\37\1\6\1\62\1\uffff\1\26";
    static final String DFA13_maxS =
        "\1\103\4\67\1\uffff\1\67\1\37\1\66\1\62\1\uffff\1\67";
    static final String DFA13_acceptS =
        "\5\uffff\1\2\4\uffff\1\1\1\uffff";
    static final String DFA13_specialS =
        "\14\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\2\4\uffff\1\3\15\uffff\1\1\32\uffff\1\4\12\uffff\1\5",
            "\1\10\13\uffff\1\7\24\uffff\1\6",
            "\1\10\13\uffff\1\7\24\uffff\1\6",
            "\1\10\13\uffff\1\7\24\uffff\1\6",
            "\1\10\13\uffff\1\7\24\uffff\1\6",
            "",
            "\1\10\13\uffff\1\7\24\uffff\1\6",
            "\1\11",
            "\1\12\42\uffff\1\5\14\uffff\1\12",
            "\1\13",
            "",
            "\1\10\13\uffff\1\7\24\uffff\1\6"
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "205:1: decl returns [GlobalDeclaration node] : (v= globalVarDecl |f= funcDecl );";
        }
    }
 

    public static final BitSet FOLLOW_decl_in_program81 = new BitSet(new long[]{0x0100000020008402L,0x0000000000000008L});
    public static final BitSet FOLLOW_INT_in_groundType120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_groundType137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_in_groundType153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_groundType167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_groundType_in_type194 = new BitSet(new long[]{0x0080000400000002L});
    public static final BitSet FOLLOW_STAR_in_type231 = new BitSet(new long[]{0x0080000400000002L});
    public static final BitSet FOLLOW_LBRACKET_in_type272 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_INT_LIT_in_type276 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_type278 = new BitSet(new long[]{0x0080000400000002L});
    public static final BitSet FOLLOW_type_in_funcType315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VOID_in_funcType326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_localVarDecl357 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_IDENT_in_localVarDecl361 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SEMI_in_localVarDecl363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_localVarDecl392 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_IDENT_in_localVarDecl396 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ASSIGN_in_localVarDecl400 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_exp_in_localVarDecl404 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SEMI_in_localVarDecl406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_globalVarDecl432 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_IDENT_in_globalVarDecl436 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SEMI_in_globalVarDecl438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_globalVarDecl467 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_IDENT_in_globalVarDecl471 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ASSIGN_in_globalVarDecl475 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_exp_in_globalVarDecl479 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SEMI_in_globalVarDecl481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localVarDecl_in_stmt508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RETURN_in_stmt530 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SEMI_in_stmt532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RETURN_in_stmt557 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_exp_in_stmt561 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SEMI_in_stmt563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exp_in_stmt580 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SEMI_in_stmt582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WRITE_in_stmt608 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_exp_in_stmt612 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SEMI_in_stmt614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_READ_in_stmt632 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_IDENT_in_stmt636 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SEMI_in_stmt638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_stmt652 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_LPAREN_in_stmt654 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_exp_in_stmt658 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_RPAREN_in_stmt660 = new BitSet(new long[]{0x45989242B24C9410L,0x0000000000000050L});
    public static final BitSet FOLLOW_stmtOrBody_in_stmt664 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ELSE_in_stmt694 = new BitSet(new long[]{0x45989242B24C9410L,0x0000000000000050L});
    public static final BitSet FOLLOW_stmtOrBody_in_stmt698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_stmt760 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_LPAREN_in_stmt762 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_exp_in_stmt766 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_RPAREN_in_stmt768 = new BitSet(new long[]{0x45989242B24C9410L,0x0000000000000050L});
    public static final BitSet FOLLOW_stmtOrBody_in_stmt772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stmt_in_stmtOrBody797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_stmtOrBody828 = new BitSet(new long[]{0x459A9240B24C9410L,0x0000000000000050L});
    public static final BitSet FOLLOW_stmtList_in_stmtOrBody832 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_stmtOrBody834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stmt_in_stmtList887 = new BitSet(new long[]{0x45989240B24C9412L,0x0000000000000050L});
    public static final BitSet FOLLOW_formal_in_formalList944 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COMMA_in_formalList968 = new BitSet(new long[]{0x0100000020008400L});
    public static final BitSet FOLLOW_formal_in_formalList972 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_type_in_formal1010 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_IDENT_in_formal1014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_funcType_in_funcDecl1046 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_IDENT_in_funcDecl1050 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_LPAREN_in_funcDecl1052 = new BitSet(new long[]{0x0120000020008400L});
    public static final BitSet FOLLOW_formalList_in_funcDecl1056 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_RPAREN_in_funcDecl1058 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LBRACE_in_funcDecl1060 = new BitSet(new long[]{0x459A9240B24C9410L,0x0000000000000050L});
    public static final BitSet FOLLOW_stmtList_in_funcDecl1064 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_funcDecl1066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_globalVarDecl_in_decl1100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_funcDecl_in_decl1115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exp_in_actualsList1162 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COMMA_in_actualsList1184 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_exp_in_actualsList1188 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_NULL_in_literalExp1226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_literalExp1253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_literalExp1280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LIT_in_literalExp1306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_LIT_in_literalExp1327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_LIT_in_literalExp1351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literalExp_in_atomExp1400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_atomExp1424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_atomExp1452 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_exp_in_atomExp1456 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_RPAREN_in_atomExp1458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomExp_in_arrayFunctionExp1490 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_LBRACKET_in_arrayFunctionExp1533 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_exp_in_arrayFunctionExp1537 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_arrayFunctionExp1539 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_IDENT_in_arrayFunctionExp1567 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_LPAREN_in_arrayFunctionExp1569 = new BitSet(new long[]{0x44A09240904C1010L});
    public static final BitSet FOLLOW_actualsList_in_arrayFunctionExp1573 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_RPAREN_in_arrayFunctionExp1575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayFunctionExp_in_postfixExp1601 = new BitSet(new long[]{0x0000000010001002L});
    public static final BitSet FOLLOW_INCR_in_postfixExp1622 = new BitSet(new long[]{0x0000000010001002L});
    public static final BitSet FOLLOW_DECR_in_postfixExp1653 = new BitSet(new long[]{0x0000000010001002L});
    public static final BitSet FOLLOW_INCR_in_prefixExp1703 = new BitSet(new long[]{0x44008200904C1000L});
    public static final BitSet FOLLOW_prefixExp_in_prefixExp1707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECR_in_prefixExp1724 = new BitSet(new long[]{0x44008200904C1000L});
    public static final BitSet FOLLOW_prefixExp_in_prefixExp1728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExp_in_prefixExp1745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_minusNotExp1782 = new BitSet(new long[]{0x44009240904C1000L});
    public static final BitSet FOLLOW_minusNotExp_in_minusNotExp1786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LNOT_in_minusNotExp1801 = new BitSet(new long[]{0x44009240904C1000L});
    public static final BitSet FOLLOW_minusNotExp_in_minusNotExp1805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_prefixExp_in_minusNotExp1821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_derefExp1859 = new BitSet(new long[]{0x44809240904C1000L});
    public static final BitSet FOLLOW_derefExp_in_derefExp1863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minusNotExp_in_derefExp1881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ADDROF_in_addrOfExp1916 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_addrOfExp_in_addrOfExp1920 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_derefExp_in_addrOfExp1935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_addrOfExp_in_multExp1973 = new BitSet(new long[]{0x0080200000004002L});
    public static final BitSet FOLLOW_STAR_in_multExp2003 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_addrOfExp_in_multExp2007 = new BitSet(new long[]{0x0080200000004002L});
    public static final BitSet FOLLOW_DIV_in_multExp2026 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_addrOfExp_in_multExp2030 = new BitSet(new long[]{0x0080200000004002L});
    public static final BitSet FOLLOW_MOD_in_multExp2050 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_addrOfExp_in_multExp2054 = new BitSet(new long[]{0x0080200000004002L});
    public static final BitSet FOLLOW_multExp_in_addExp2094 = new BitSet(new long[]{0x0001100000000002L});
    public static final BitSet FOLLOW_PLUS_in_addExp2126 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_multExp_in_addExp2130 = new BitSet(new long[]{0x0001100000000002L});
    public static final BitSet FOLLOW_MINUS_in_addExp2151 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_multExp_in_addExp2155 = new BitSet(new long[]{0x0001100000000002L});
    public static final BitSet FOLLOW_addExp_in_inequalityExp2195 = new BitSet(new long[]{0x00000C0000300002L});
    public static final BitSet FOLLOW_LT_in_inequalityExp2224 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_addExp_in_inequalityExp2228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LTE_in_inequalityExp2248 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_addExp_in_inequalityExp2252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GT_in_inequalityExp2271 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_addExp_in_inequalityExp2275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GTE_in_inequalityExp2295 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_addExp_in_inequalityExp2299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inequalityExp_in_equalityExp2338 = new BitSet(new long[]{0x0000400000020002L});
    public static final BitSet FOLLOW_EQUAL_in_equalityExp2368 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_inequalityExp_in_equalityExp2372 = new BitSet(new long[]{0x0000400000020002L});
    public static final BitSet FOLLOW_NEQUAL_in_equalityExp2390 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_inequalityExp_in_equalityExp2394 = new BitSet(new long[]{0x0000400000020002L});
    public static final BitSet FOLLOW_equalityExp_in_andExp2431 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_LAND_in_andExp2459 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_equalityExp_in_andExp2463 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_andExp_in_orExp2500 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_LOR_in_orExp2533 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_andExp_in_orExp2537 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_orExp_in_assignExp2580 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_ASSIGN_in_assignExp2611 = new BitSet(new long[]{0x44809240904C1010L});
    public static final BitSet FOLLOW_assignExp_in_assignExp2615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignExp_in_exp2652 = new BitSet(new long[]{0x0000000000000002L});

}