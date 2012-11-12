// $ANTLR 3.4 /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g 2011-12-16 13:33:20

    // This section is pasted into the top of the generated
    // file.  We use it to declare a package here.  Other packages
    // can also be imported as required.
    package cs536.syntax;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class MinCLexer extends Lexer {
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


    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public MinCLexer() {} 
    public MinCLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public MinCLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g"; }

    // $ANTLR start "LOWER"
    public final void mLOWER() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:345:15: ( 'a' .. 'z' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
            {
            if ( (input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LOWER"

    // $ANTLR start "UPPER"
    public final void mUPPER() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:346:15: ( 'A' .. 'Z' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UPPER"

    // $ANTLR start "ALPHA"
    public final void mALPHA() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:347:15: ( UPPER | LOWER )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ALPHA"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:348:15: ( '0' .. '9' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:352:5: ( ( ' ' | ( '\\r' )? '\\n' | '\\t' ) )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:352:7: ( ' ' | ( '\\r' )? '\\n' | '\\t' )
            {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:352:7: ( ' ' | ( '\\r' )? '\\n' | '\\t' )
            int alt2=3;
            switch ( input.LA(1) ) {
            case ' ':
                {
                alt2=1;
                }
                break;
            case '\n':
            case '\r':
                {
                alt2=2;
                }
                break;
            case '\t':
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }

            switch (alt2) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:352:8: ' '
                    {
                    match(' '); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:352:14: ( '\\r' )? '\\n'
                    {
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:352:14: ( '\\r' )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0=='\r') ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:352:14: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }


                    match('\n'); 

                    }
                    break;
                case 3 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:352:27: '\\t'
                    {
                    match('\t'); 

                    }
                    break;

            }


             skip(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHITESPACE"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:356:5: ( ( '//' (~ '\\n' )* ) )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:356:7: ( '//' (~ '\\n' )* )
            {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:356:7: ( '//' (~ '\\n' )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:356:8: '//' (~ '\\n' )*
            {
            match("//"); 



            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:356:13: (~ '\\n' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '\u0000' && LA3_0 <= '\t')||(LA3_0 >= '\u000B' && LA3_0 <= '\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }


            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "BLOCK_COMMENT_CONTENTS"
    public final void mBLOCK_COMMENT_CONTENTS() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:359:33: ( ( '*' )+ ~ ( '*' | '/' ) |~ '*' )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='*') ) {
                alt5=1;
            }
            else if ( ((LA5_0 >= '\u0000' && LA5_0 <= ')')||(LA5_0 >= '+' && LA5_0 <= '\uFFFF')) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }
            switch (alt5) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:359:35: ( '*' )+ ~ ( '*' | '/' )
                    {
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:359:35: ( '*' )+
                    int cnt4=0;
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0=='*') ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:359:35: '*'
                    	    {
                    	    match('*'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt4 >= 1 ) break loop4;
                                EarlyExitException eee =
                                    new EarlyExitException(4, input);
                                throw eee;
                        }
                        cnt4++;
                    } while (true);


                    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= ')')||(input.LA(1) >= '+' && input.LA(1) <= '.')||(input.LA(1) >= '0' && input.LA(1) <= '\uFFFF') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:359:55: ~ '*'
                    {
                    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= ')')||(input.LA(1) >= '+' && input.LA(1) <= '\uFFFF') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BLOCK_COMMENT_CONTENTS"

    // $ANTLR start "BLOCK_COMMENT"
    public final void mBLOCK_COMMENT() throws RecognitionException {
        try {
            int _type = BLOCK_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:361:5: ( ( '/*' ( BLOCK_COMMENT_CONTENTS )* ( '*' )* '*/' ) )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:361:7: ( '/*' ( BLOCK_COMMENT_CONTENTS )* ( '*' )* '*/' )
            {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:361:7: ( '/*' ( BLOCK_COMMENT_CONTENTS )* ( '*' )* '*/' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:361:8: '/*' ( BLOCK_COMMENT_CONTENTS )* ( '*' )* '*/'
            {
            match("/*"); 



            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:361:13: ( BLOCK_COMMENT_CONTENTS )*
            loop6:
            do {
                int alt6=2;
                alt6 = dfa6.predict(input);
                switch (alt6) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:361:13: BLOCK_COMMENT_CONTENTS
            	    {
            	    mBLOCK_COMMENT_CONTENTS(); 


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:361:37: ( '*' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='*') ) {
                    int LA7_1 = input.LA(2);

                    if ( (LA7_1=='*') ) {
                        alt7=1;
                    }


                }


                switch (alt7) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:361:37: '*'
            	    {
            	    match('*'); 

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            match("*/"); 



            }


            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BLOCK_COMMENT"

    // $ANTLR start "UNTERM_BLOCK_COMMENT"
    public final void mUNTERM_BLOCK_COMMENT() throws RecognitionException {
        try {
            int _type = UNTERM_BLOCK_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:365:5: ( ( '/*' ( BLOCK_COMMENT_CONTENTS )* ( '*' )* ) )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:365:7: ( '/*' ( BLOCK_COMMENT_CONTENTS )* ( '*' )* )
            {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:365:7: ( '/*' ( BLOCK_COMMENT_CONTENTS )* ( '*' )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:365:8: '/*' ( BLOCK_COMMENT_CONTENTS )* ( '*' )*
            {
            match("/*"); 



            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:365:13: ( BLOCK_COMMENT_CONTENTS )*
            loop8:
            do {
                int alt8=2;
                alt8 = dfa8.predict(input);
                switch (alt8) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:365:13: BLOCK_COMMENT_CONTENTS
            	    {
            	    mBLOCK_COMMENT_CONTENTS(); 


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:365:37: ( '*' )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='*') ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:365:37: '*'
            	    {
            	    match('*'); 

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            }


             emitRepresentation(UNTERM_BLOCK_COMMENT, "Unterminated block comment"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UNTERM_BLOCK_COMMENT"

    // $ANTLR start "BOOL"
    public final void mBOOL() throws RecognitionException {
        try {
            int _type = BOOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:373:5: ( 'bool' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:373:9: 'bool'
            {
            match("bool"); 



             emitRepresentation(BOOL, "BOOL"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BOOL"

    // $ANTLR start "DOUBLE"
    public final void mDOUBLE() throws RecognitionException {
        try {
            int _type = DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:374:7: ( 'double' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:374:9: 'double'
            {
            match("double"); 



             emitRepresentation(DOUBLE, "DOUBLE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOUBLE"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:375:5: ( 'else' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:375:9: 'else'
            {
            match("else"); 



             emitRepresentation(ELSE, "ELSE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:376:6: ( 'false' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:376:9: 'false'
            {
            match("false"); 



             emitRepresentation(FALSE, "FALSE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FALSE"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:377:3: ( 'if' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:377:9: 'if'
            {
            match("if"); 



             emitRepresentation(IF, "IF"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:378:4: ( 'int' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:378:9: 'int'
            {
            match("int"); 



             emitRepresentation(INT, "INT"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "NULL"
    public final void mNULL() throws RecognitionException {
        try {
            int _type = NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:379:5: ( 'null' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:379:9: 'null'
            {
            match("null"); 



             emitRepresentation(NULL, "NULL"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NULL"

    // $ANTLR start "READ"
    public final void mREAD() throws RecognitionException {
        try {
            int _type = READ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:380:5: ( 'read' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:380:9: 'read'
            {
            match("read"); 



             emitRepresentation(READ, "READ"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "READ"

    // $ANTLR start "RETURN"
    public final void mRETURN() throws RecognitionException {
        try {
            int _type = RETURN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:381:7: ( 'return' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:381:9: 'return'
            {
            match("return"); 



             emitRepresentation(RETURN, "RETURN"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RETURN"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:382:7: ( 'string' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:382:9: 'string'
            {
            match("string"); 



             emitRepresentation(STRING, "STRING"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:383:5: ( 'true' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:383:9: 'true'
            {
            match("true"); 



             emitRepresentation(TRUE, "TRUE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "VOID"
    public final void mVOID() throws RecognitionException {
        try {
            int _type = VOID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:384:5: ( 'void' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:384:9: 'void'
            {
            match("void"); 



             emitRepresentation(VOID, "VOID"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VOID"

    // $ANTLR start "WHILE"
    public final void mWHILE() throws RecognitionException {
        try {
            int _type = WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:385:6: ( 'while' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:385:9: 'while'
            {
            match("while"); 



             emitRepresentation(WHILE, "WHILE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHILE"

    // $ANTLR start "WRITE"
    public final void mWRITE() throws RecognitionException {
        try {
            int _type = WRITE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:386:6: ( 'write' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:386:9: 'write'
            {
            match("write"); 



             emitRepresentation(WRITE, "WRITE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WRITE"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:388:7: ( '(' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:388:11: '('
            {
            match('('); 

             emitRepresentation(LPAREN, "LPAREN"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:389:7: ( ')' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:389:11: ')'
            {
            match(')'); 

             emitRepresentation(RPAREN, "RPAREN"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "LBRACE"
    public final void mLBRACE() throws RecognitionException {
        try {
            int _type = LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:390:7: ( '{' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:390:11: '{'
            {
            match('{'); 

             emitRepresentation(LBRACE, "LBRACE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LBRACE"

    // $ANTLR start "RBRACE"
    public final void mRBRACE() throws RecognitionException {
        try {
            int _type = RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:391:7: ( '}' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:391:11: '}'
            {
            match('}'); 

             emitRepresentation(RBRACE, "RBRACE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RBRACE"

    // $ANTLR start "LBRACKET"
    public final void mLBRACKET() throws RecognitionException {
        try {
            int _type = LBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:392:9: ( '[' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:392:11: '['
            {
            match('['); 

             emitRepresentation(LBRACKET, "LBRACKET"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LBRACKET"

    // $ANTLR start "RBRACKET"
    public final void mRBRACKET() throws RecognitionException {
        try {
            int _type = RBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:393:9: ( ']' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:393:11: ']'
            {
            match(']'); 

             emitRepresentation(RBRACKET, "RBRACKET"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RBRACKET"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:394:5: ( ';' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:394:11: ';'
            {
            match(';'); 

             emitRepresentation(SEMI, "SEMI"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SEMI"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:395:6: ( ',' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:395:11: ','
            {
            match(','); 

             emitRepresentation(COMMA, "COMMA"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "LAND"
    public final void mLAND() throws RecognitionException {
        try {
            int _type = LAND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:396:5: ( '&&' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:396:11: '&&'
            {
            match("&&"); 



             emitRepresentation(LAND, "LAND"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LAND"

    // $ANTLR start "LOR"
    public final void mLOR() throws RecognitionException {
        try {
            int _type = LOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:397:4: ( '||' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:397:11: '||'
            {
            match("||"); 



             emitRepresentation(LOR, "LOR"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LOR"

    // $ANTLR start "LNOT"
    public final void mLNOT() throws RecognitionException {
        try {
            int _type = LNOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:398:5: ( '!' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:398:11: '!'
            {
            match('!'); 

             emitRepresentation(LNOT, "LNOT"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LNOT"

    // $ANTLR start "ASSIGN"
    public final void mASSIGN() throws RecognitionException {
        try {
            int _type = ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:399:7: ( '=' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:399:11: '='
            {
            match('='); 

             emitRepresentation(ASSIGN, "ASSIGN"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ASSIGN"

    // $ANTLR start "ADDROF"
    public final void mADDROF() throws RecognitionException {
        try {
            int _type = ADDROF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:400:7: ( '&' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:400:11: '&'
            {
            match('&'); 

             emitRepresentation(ADDROF, "ADDROF"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ADDROF"

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:401:3: ( '<' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:401:11: '<'
            {
            match('<'); 

             emitRepresentation(LT, "LT"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LT"

    // $ANTLR start "LTE"
    public final void mLTE() throws RecognitionException {
        try {
            int _type = LTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:402:4: ( '<=' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:402:11: '<='
            {
            match("<="); 



             emitRepresentation(LTE, "LTE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LTE"

    // $ANTLR start "GT"
    public final void mGT() throws RecognitionException {
        try {
            int _type = GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:403:3: ( '>' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:403:11: '>'
            {
            match('>'); 

             emitRepresentation(GT, "GT"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GT"

    // $ANTLR start "GTE"
    public final void mGTE() throws RecognitionException {
        try {
            int _type = GTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:404:4: ( '>=' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:404:11: '>='
            {
            match(">="); 



             emitRepresentation(GTE, "GTE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GTE"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:405:6: ( '==' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:405:11: '=='
            {
            match("=="); 



             emitRepresentation(EQUAL, "EQUAL"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EQUAL"

    // $ANTLR start "NEQUAL"
    public final void mNEQUAL() throws RecognitionException {
        try {
            int _type = NEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:406:7: ( '!=' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:406:11: '!='
            {
            match("!="); 



             emitRepresentation(NEQUAL, "NEQUAL"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEQUAL"

    // $ANTLR start "INCR"
    public final void mINCR() throws RecognitionException {
        try {
            int _type = INCR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:407:5: ( '++' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:407:11: '++'
            {
            match("++"); 



             emitRepresentation(INCR, "INCR"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INCR"

    // $ANTLR start "DECR"
    public final void mDECR() throws RecognitionException {
        try {
            int _type = DECR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:408:5: ( '--' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:408:11: '--'
            {
            match("--"); 



             emitRepresentation(DECR, "DECR"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DECR"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:409:5: ( '+' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:409:11: '+'
            {
            match('+'); 

             emitRepresentation(PLUS, "PLUS"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:410:6: ( '-' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:410:11: '-'
            {
            match('-'); 

             emitRepresentation(MINUS, "MINUS"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "STAR"
    public final void mSTAR() throws RecognitionException {
        try {
            int _type = STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:411:5: ( '*' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:411:11: '*'
            {
            match('*'); 

             emitRepresentation(STAR, "STAR"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STAR"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:412:4: ( '/' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:412:11: '/'
            {
            match('/'); 

             emitRepresentation(DIV, "DIV"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "MOD"
    public final void mMOD() throws RecognitionException {
        try {
            int _type = MOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:413:4: ( '%' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:413:11: '%'
            {
            match('%'); 

             emitRepresentation(MOD, "MOD"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MOD"

    // $ANTLR start "IDENT_START"
    public final void mIDENT_START() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:419:21: ( ALPHA | ( '_' | '$' )+ ( ALPHA | DIGIT ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( ((LA11_0 >= 'A' && LA11_0 <= 'Z')||(LA11_0 >= 'a' && LA11_0 <= 'z')) ) {
                alt11=1;
            }
            else if ( (LA11_0=='$'||LA11_0=='_') ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;

            }
            switch (alt11) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:419:26: ALPHA
                    {
                    mALPHA(); 


                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:419:34: ( '_' | '$' )+ ( ALPHA | DIGIT )
                    {
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:419:34: ( '_' | '$' )+
                    int cnt10=0;
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0=='$'||LA10_0=='_') ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
                    	    {
                    	    if ( input.LA(1)=='$'||input.LA(1)=='_' ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt10 >= 1 ) break loop10;
                                EarlyExitException eee =
                                    new EarlyExitException(10, input);
                                throw eee;
                        }
                        cnt10++;
                    } while (true);


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IDENT_START"

    // $ANTLR start "IDENT_CONTINUE"
    public final void mIDENT_CONTINUE() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:420:24: ( ( ALPHA | DIGIT | '_' | '$' )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:420:26: ( ALPHA | DIGIT | '_' | '$' )*
            {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:420:26: ( ALPHA | DIGIT | '_' | '$' )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0=='$'||(LA12_0 >= '0' && LA12_0 <= '9')||(LA12_0 >= 'A' && LA12_0 <= 'Z')||LA12_0=='_'||(LA12_0 >= 'a' && LA12_0 <= 'z')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IDENT_CONTINUE"

    // $ANTLR start "IDENT"
    public final void mIDENT() throws RecognitionException {
        try {
            int _type = IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:423:5: ( IDENT_START IDENT_CONTINUE )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:423:7: IDENT_START IDENT_CONTINUE
            {
            mIDENT_START(); 


            mIDENT_CONTINUE(); 


             emitPrefixed(IDENT, "IDENT: "); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IDENT"

    // $ANTLR start "BAD_IDENT"
    public final void mBAD_IDENT() throws RecognitionException {
        try {
            int _type = BAD_IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:430:5: ( ( ( '_' | '$' )+ | ( ( DIGIT )+ ( ALPHA | '_' | '$' ) IDENT_CONTINUE ) ) )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:430:7: ( ( '_' | '$' )+ | ( ( DIGIT )+ ( ALPHA | '_' | '$' ) IDENT_CONTINUE ) )
            {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:430:7: ( ( '_' | '$' )+ | ( ( DIGIT )+ ( ALPHA | '_' | '$' ) IDENT_CONTINUE ) )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='$'||LA15_0=='_') ) {
                alt15=1;
            }
            else if ( ((LA15_0 >= '0' && LA15_0 <= '9')) ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }
            switch (alt15) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:431:9: ( '_' | '$' )+
                    {
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:431:9: ( '_' | '$' )+
                    int cnt13=0;
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0=='$'||LA13_0=='_') ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
                    	    {
                    	    if ( input.LA(1)=='$'||input.LA(1)=='_' ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt13 >= 1 ) break loop13;
                                EarlyExitException eee =
                                    new EarlyExitException(13, input);
                                throw eee;
                        }
                        cnt13++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:432:9: ( ( DIGIT )+ ( ALPHA | '_' | '$' ) IDENT_CONTINUE )
                    {
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:432:9: ( ( DIGIT )+ ( ALPHA | '_' | '$' ) IDENT_CONTINUE )
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:432:10: ( DIGIT )+ ( ALPHA | '_' | '$' ) IDENT_CONTINUE
                    {
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:432:10: ( DIGIT )+
                    int cnt14=0;
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( ((LA14_0 >= '0' && LA14_0 <= '9')) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt14 >= 1 ) break loop14;
                                EarlyExitException eee =
                                    new EarlyExitException(14, input);
                                throw eee;
                        }
                        cnt14++;
                    } while (true);


                    if ( input.LA(1)=='$'||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    mIDENT_CONTINUE(); 


                    }


                    }
                    break;

            }


             emitPrefixed(BAD_IDENT, "Invalid identifier: "); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BAD_IDENT"

    // $ANTLR start "VALID_ESCAPE_CHAR"
    public final void mVALID_ESCAPE_CHAR() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:440:27: ( '\\\\' | 'n' | 't' | '\"' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
            {
            if ( input.LA(1)=='\"'||input.LA(1)=='\\'||input.LA(1)=='n'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VALID_ESCAPE_CHAR"

    // $ANTLR start "STRING_CHAR"
    public final void mSTRING_CHAR() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:451:21: ( ' ' | '!' | '#' .. '[' | ']' .. '~' | '\\\\' VALID_ESCAPE_CHAR )
            int alt16=5;
            switch ( input.LA(1) ) {
            case ' ':
                {
                alt16=1;
                }
                break;
            case '!':
                {
                alt16=2;
                }
                break;
            case '#':
            case '$':
            case '%':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
                {
                alt16=3;
                }
                break;
            case ']':
            case '^':
            case '_':
            case '`':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            case '{':
            case '|':
            case '}':
            case '~':
                {
                alt16=4;
                }
                break;
            case '\\':
                {
                alt16=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }

            switch (alt16) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:451:23: ' '
                    {
                    match(' '); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:451:29: '!'
                    {
                    match('!'); 

                    }
                    break;
                case 3 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:451:35: '#' .. '['
                    {
                    matchRange('#','['); 

                    }
                    break;
                case 4 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:451:46: ']' .. '~'
                    {
                    matchRange(']','~'); 

                    }
                    break;
                case 5 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:451:57: '\\\\' VALID_ESCAPE_CHAR
                    {
                    match('\\'); 

                    mVALID_ESCAPE_CHAR(); 


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING_CHAR"

    // $ANTLR start "STRING_LIT"
    public final void mSTRING_LIT() throws RecognitionException {
        try {
            int _type = STRING_LIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:455:5: ( '\"' ( STRING_CHAR )* '\"' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:455:7: '\"' ( STRING_CHAR )* '\"'
            {
            match('\"'); 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:455:11: ( STRING_CHAR )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0 >= ' ' && LA17_0 <= '!')||(LA17_0 >= '#' && LA17_0 <= '~')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:455:11: STRING_CHAR
            	    {
            	    mSTRING_CHAR(); 


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            match('\"'); 

             emitPrefixed(STRING_LIT, "STRING_LIT: "); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING_LIT"

    // $ANTLR start "ILLEGAL_ESCAPE"
    public final void mILLEGAL_ESCAPE() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:459:24: ( '\\\\' ~ ( VALID_ESCAPE_CHAR ) )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:459:26: '\\\\' ~ ( VALID_ESCAPE_CHAR )
            {
            match('\\'); 

            if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= 'm')||(input.LA(1) >= 'o' && input.LA(1) <= 's')||(input.LA(1) >= 'u' && input.LA(1) <= '\uFFFF') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ILLEGAL_ESCAPE"

    // $ANTLR start "ILLEGAL_ESCAPE_STRING_CHAR"
    public final void mILLEGAL_ESCAPE_STRING_CHAR() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:460:36: ( STRING_CHAR | ILLEGAL_ESCAPE )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0 >= ' ' && LA18_0 <= '!')||(LA18_0 >= '#' && LA18_0 <= '[')||(LA18_0 >= ']' && LA18_0 <= '~')) ) {
                alt18=1;
            }
            else if ( (LA18_0=='\\') ) {
                int LA18_2 = input.LA(2);

                if ( ((LA18_2 >= '\u0000' && LA18_2 <= '!')||(LA18_2 >= '#' && LA18_2 <= '[')||(LA18_2 >= ']' && LA18_2 <= 'm')||(LA18_2 >= 'o' && LA18_2 <= 's')||(LA18_2 >= 'u' && LA18_2 <= '\uFFFF')) ) {
                    alt18=2;
                }
                else if ( (LA18_2=='\"'||LA18_2=='\\'||LA18_2=='n'||LA18_2=='t') ) {
                    alt18=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 2, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;

            }
            switch (alt18) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:460:38: STRING_CHAR
                    {
                    mSTRING_CHAR(); 


                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:460:52: ILLEGAL_ESCAPE
                    {
                    mILLEGAL_ESCAPE(); 


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ILLEGAL_ESCAPE_STRING_CHAR"

    // $ANTLR start "STRING_WITH_ILLEGAL_ESCAPE"
    public final void mSTRING_WITH_ILLEGAL_ESCAPE() throws RecognitionException {
        try {
            int _type = STRING_WITH_ILLEGAL_ESCAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:463:5: ( '\"' ( STRING_CHAR )* ILLEGAL_ESCAPE ( ILLEGAL_ESCAPE_STRING_CHAR )* '\"' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:463:7: '\"' ( STRING_CHAR )* ILLEGAL_ESCAPE ( ILLEGAL_ESCAPE_STRING_CHAR )* '\"'
            {
            match('\"'); 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:463:11: ( STRING_CHAR )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0=='\\') ) {
                    int LA19_1 = input.LA(2);

                    if ( (LA19_1=='\"'||LA19_1=='\\'||LA19_1=='n'||LA19_1=='t') ) {
                        alt19=1;
                    }


                }
                else if ( ((LA19_0 >= ' ' && LA19_0 <= '!')||(LA19_0 >= '#' && LA19_0 <= '[')||(LA19_0 >= ']' && LA19_0 <= '~')) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:463:11: STRING_CHAR
            	    {
            	    mSTRING_CHAR(); 


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);


            mILLEGAL_ESCAPE(); 


            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:463:41: ( ILLEGAL_ESCAPE_STRING_CHAR )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0 >= ' ' && LA20_0 <= '!')||(LA20_0 >= '#' && LA20_0 <= '~')) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:463:41: ILLEGAL_ESCAPE_STRING_CHAR
            	    {
            	    mILLEGAL_ESCAPE_STRING_CHAR(); 


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            match('\"'); 

             emitIllegalEscape(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING_WITH_ILLEGAL_ESCAPE"

    // $ANTLR start "TAB_STRING_CHAR"
    public final void mTAB_STRING_CHAR() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:468:25: ( ILLEGAL_ESCAPE_STRING_CHAR | '\\t' )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( ((LA21_0 >= ' ' && LA21_0 <= '!')||(LA21_0 >= '#' && LA21_0 <= '~')) ) {
                alt21=1;
            }
            else if ( (LA21_0=='\t') ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;

            }
            switch (alt21) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:468:27: ILLEGAL_ESCAPE_STRING_CHAR
                    {
                    mILLEGAL_ESCAPE_STRING_CHAR(); 


                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:468:56: '\\t'
                    {
                    match('\t'); 

                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TAB_STRING_CHAR"

    // $ANTLR start "STRING_WITH_TAB"
    public final void mSTRING_WITH_TAB() throws RecognitionException {
        try {
            int _type = STRING_WITH_TAB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:471:5: ( '\"' ( ILLEGAL_ESCAPE_STRING_CHAR )* '\\t' ( TAB_STRING_CHAR )* '\"' )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:471:7: '\"' ( ILLEGAL_ESCAPE_STRING_CHAR )* '\\t' ( TAB_STRING_CHAR )* '\"'
            {
            match('\"'); 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:471:11: ( ILLEGAL_ESCAPE_STRING_CHAR )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0 >= ' ' && LA22_0 <= '!')||(LA22_0 >= '#' && LA22_0 <= '~')) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:471:11: ILLEGAL_ESCAPE_STRING_CHAR
            	    {
            	    mILLEGAL_ESCAPE_STRING_CHAR(); 


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            match('\t'); 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:471:44: ( TAB_STRING_CHAR )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0=='\t'||(LA23_0 >= ' ' && LA23_0 <= '!')||(LA23_0 >= '#' && LA23_0 <= '~')) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:471:44: TAB_STRING_CHAR
            	    {
            	    mTAB_STRING_CHAR(); 


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            match('\"'); 

             emitRepresentation(STRING_WITH_TAB, "Illegal string character"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING_WITH_TAB"

    // $ANTLR start "UNTERM_STRING"
    public final void mUNTERM_STRING() throws RecognitionException {
        try {
            int _type = UNTERM_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:477:5: ( '\"' ( TAB_STRING_CHAR )* )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:477:7: '\"' ( TAB_STRING_CHAR )*
            {
            match('\"'); 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:477:11: ( TAB_STRING_CHAR )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0=='\t'||(LA24_0 >= ' ' && LA24_0 <= '!')||(LA24_0 >= '#' && LA24_0 <= '~')) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:477:11: TAB_STRING_CHAR
            	    {
            	    mTAB_STRING_CHAR(); 


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


             emitPrefixed(UNTERM_STRING, "Unterminated string: "); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UNTERM_STRING"

    // $ANTLR start "INTEGER"
    public final void mINTEGER() throws RecognitionException {
        try {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:486:17: ( ( '0' | '1' .. '9' ( DIGIT )* ) )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:486:19: ( '0' | '1' .. '9' ( DIGIT )* )
            {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:486:19: ( '0' | '1' .. '9' ( DIGIT )* )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0=='0') ) {
                alt26=1;
            }
            else if ( ((LA26_0 >= '1' && LA26_0 <= '9')) ) {
                alt26=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;

            }
            switch (alt26) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:486:20: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:486:26: '1' .. '9' ( DIGIT )*
                    {
                    matchRange('1','9'); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:486:35: ( DIGIT )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( ((LA25_0 >= '0' && LA25_0 <= '9')) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);


                    }
                    break;

            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INTEGER"

    // $ANTLR start "INT_LIT"
    public final void mINT_LIT() throws RecognitionException {
        try {
            int _type = INT_LIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:489:5: ( INTEGER )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:489:7: INTEGER
            {
            mINTEGER(); 



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

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT_LIT"

    // $ANTLR start "LEADING_ZERO_INT"
    public final void mLEADING_ZERO_INT() throws RecognitionException {
        try {
            int _type = LEADING_ZERO_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:508:5: ( '0' ( DIGIT )+ )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:508:7: '0' ( DIGIT )+
            {
            match('0'); 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:508:11: ( DIGIT )+
            int cnt27=0;
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( ((LA27_0 >= '0' && LA27_0 <= '9')) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt27 >= 1 ) break loop27;
                        EarlyExitException eee =
                            new EarlyExitException(27, input);
                        throw eee;
                }
                cnt27++;
            } while (true);


             emitPrefixed(LEADING_ZERO_INT, "Integer has leading 0: "); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LEADING_ZERO_INT"

    // $ANTLR start "FLOAT_LIT"
    public final void mFLOAT_LIT() throws RecognitionException {
        try {
            int _type = FLOAT_LIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:516:5: ( INTEGER '.' ( DIGIT )+ ( 'e' ( '-' )? INTEGER )? )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:516:7: INTEGER '.' ( DIGIT )+ ( 'e' ( '-' )? INTEGER )?
            {
            mINTEGER(); 


            match('.'); 

            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:516:19: ( DIGIT )+
            int cnt28=0;
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( ((LA28_0 >= '0' && LA28_0 <= '9')) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt28 >= 1 ) break loop28;
                        EarlyExitException eee =
                            new EarlyExitException(28, input);
                        throw eee;
                }
                cnt28++;
            } while (true);


            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:516:26: ( 'e' ( '-' )? INTEGER )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0=='e') ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:516:27: 'e' ( '-' )? INTEGER
                    {
                    match('e'); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:516:31: ( '-' )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0=='-') ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:516:31: '-'
                            {
                            match('-'); 

                            }
                            break;

                    }


                    mINTEGER(); 


                    }
                    break;

            }



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

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FLOAT_LIT"

    // $ANTLR start "LEADING_ZERO_FLOAT"
    public final void mLEADING_ZERO_FLOAT() throws RecognitionException {
        try {
            int _type = LEADING_ZERO_FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:534:5: ( ( '0' ( DIGIT )+ '.' ( DIGIT )+ ( 'e' ( '-' )? INTEGER )? | INTEGER '.' ( DIGIT )+ 'e' ( '-' )? '0' ( DIGIT )+ | '0' ( DIGIT )+ '.' ( DIGIT )+ 'e' ( '-' )? '0' ( DIGIT )+ ) )
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:534:7: ( '0' ( DIGIT )+ '.' ( DIGIT )+ ( 'e' ( '-' )? INTEGER )? | INTEGER '.' ( DIGIT )+ 'e' ( '-' )? '0' ( DIGIT )+ | '0' ( DIGIT )+ '.' ( DIGIT )+ 'e' ( '-' )? '0' ( DIGIT )+ )
            {
            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:534:7: ( '0' ( DIGIT )+ '.' ( DIGIT )+ ( 'e' ( '-' )? INTEGER )? | INTEGER '.' ( DIGIT )+ 'e' ( '-' )? '0' ( DIGIT )+ | '0' ( DIGIT )+ '.' ( DIGIT )+ 'e' ( '-' )? '0' ( DIGIT )+ )
            int alt42=3;
            alt42 = dfa42.predict(input);
            switch (alt42) {
                case 1 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:534:9: '0' ( DIGIT )+ '.' ( DIGIT )+ ( 'e' ( '-' )? INTEGER )?
                    {
                    match('0'); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:534:13: ( DIGIT )+
                    int cnt31=0;
                    loop31:
                    do {
                        int alt31=2;
                        int LA31_0 = input.LA(1);

                        if ( ((LA31_0 >= '0' && LA31_0 <= '9')) ) {
                            alt31=1;
                        }


                        switch (alt31) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt31 >= 1 ) break loop31;
                                EarlyExitException eee =
                                    new EarlyExitException(31, input);
                                throw eee;
                        }
                        cnt31++;
                    } while (true);


                    match('.'); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:534:24: ( DIGIT )+
                    int cnt32=0;
                    loop32:
                    do {
                        int alt32=2;
                        int LA32_0 = input.LA(1);

                        if ( ((LA32_0 >= '0' && LA32_0 <= '9')) ) {
                            alt32=1;
                        }


                        switch (alt32) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt32 >= 1 ) break loop32;
                                EarlyExitException eee =
                                    new EarlyExitException(32, input);
                                throw eee;
                        }
                        cnt32++;
                    } while (true);


                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:534:31: ( 'e' ( '-' )? INTEGER )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0=='e') ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:534:32: 'e' ( '-' )? INTEGER
                            {
                            match('e'); 

                            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:534:36: ( '-' )?
                            int alt33=2;
                            int LA33_0 = input.LA(1);

                            if ( (LA33_0=='-') ) {
                                alt33=1;
                            }
                            switch (alt33) {
                                case 1 :
                                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:534:36: '-'
                                    {
                                    match('-'); 

                                    }
                                    break;

                            }


                            mINTEGER(); 


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:535:9: INTEGER '.' ( DIGIT )+ 'e' ( '-' )? '0' ( DIGIT )+
                    {
                    mINTEGER(); 


                    match('.'); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:535:24: ( DIGIT )+
                    int cnt35=0;
                    loop35:
                    do {
                        int alt35=2;
                        int LA35_0 = input.LA(1);

                        if ( ((LA35_0 >= '0' && LA35_0 <= '9')) ) {
                            alt35=1;
                        }


                        switch (alt35) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt35 >= 1 ) break loop35;
                                EarlyExitException eee =
                                    new EarlyExitException(35, input);
                                throw eee;
                        }
                        cnt35++;
                    } while (true);


                    match('e'); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:535:35: ( '-' )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0=='-') ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:535:35: '-'
                            {
                            match('-'); 

                            }
                            break;

                    }


                    match('0'); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:535:44: ( DIGIT )+
                    int cnt37=0;
                    loop37:
                    do {
                        int alt37=2;
                        int LA37_0 = input.LA(1);

                        if ( ((LA37_0 >= '0' && LA37_0 <= '9')) ) {
                            alt37=1;
                        }


                        switch (alt37) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt37 >= 1 ) break loop37;
                                EarlyExitException eee =
                                    new EarlyExitException(37, input);
                                throw eee;
                        }
                        cnt37++;
                    } while (true);


                    }
                    break;
                case 3 :
                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:536:9: '0' ( DIGIT )+ '.' ( DIGIT )+ 'e' ( '-' )? '0' ( DIGIT )+
                    {
                    match('0'); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:536:13: ( DIGIT )+
                    int cnt38=0;
                    loop38:
                    do {
                        int alt38=2;
                        int LA38_0 = input.LA(1);

                        if ( ((LA38_0 >= '0' && LA38_0 <= '9')) ) {
                            alt38=1;
                        }


                        switch (alt38) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt38 >= 1 ) break loop38;
                                EarlyExitException eee =
                                    new EarlyExitException(38, input);
                                throw eee;
                        }
                        cnt38++;
                    } while (true);


                    match('.'); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:536:24: ( DIGIT )+
                    int cnt39=0;
                    loop39:
                    do {
                        int alt39=2;
                        int LA39_0 = input.LA(1);

                        if ( ((LA39_0 >= '0' && LA39_0 <= '9')) ) {
                            alt39=1;
                        }


                        switch (alt39) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt39 >= 1 ) break loop39;
                                EarlyExitException eee =
                                    new EarlyExitException(39, input);
                                throw eee;
                        }
                        cnt39++;
                    } while (true);


                    match('e'); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:536:35: ( '-' )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0=='-') ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:536:35: '-'
                            {
                            match('-'); 

                            }
                            break;

                    }


                    match('0'); 

                    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:536:44: ( DIGIT )+
                    int cnt41=0;
                    loop41:
                    do {
                        int alt41=2;
                        int LA41_0 = input.LA(1);

                        if ( ((LA41_0 >= '0' && LA41_0 <= '9')) ) {
                            alt41=1;
                        }


                        switch (alt41) {
                    	case 1 :
                    	    // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt41 >= 1 ) break loop41;
                                EarlyExitException eee =
                                    new EarlyExitException(41, input);
                                throw eee;
                        }
                        cnt41++;
                    } while (true);


                    }
                    break;

            }


             emitPrefixed(FLOAT_LIT, "Floating point has leading 0: "); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LEADING_ZERO_FLOAT"

    public void mTokens() throws RecognitionException {
        // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:8: ( WHITESPACE | LINE_COMMENT | BLOCK_COMMENT | UNTERM_BLOCK_COMMENT | BOOL | DOUBLE | ELSE | FALSE | IF | INT | NULL | READ | RETURN | STRING | TRUE | VOID | WHILE | WRITE | LPAREN | RPAREN | LBRACE | RBRACE | LBRACKET | RBRACKET | SEMI | COMMA | LAND | LOR | LNOT | ASSIGN | ADDROF | LT | LTE | GT | GTE | EQUAL | NEQUAL | INCR | DECR | PLUS | MINUS | STAR | DIV | MOD | IDENT | BAD_IDENT | STRING_LIT | STRING_WITH_ILLEGAL_ESCAPE | STRING_WITH_TAB | UNTERM_STRING | INT_LIT | LEADING_ZERO_INT | FLOAT_LIT | LEADING_ZERO_FLOAT )
        int alt43=54;
        alt43 = dfa43.predict(input);
        switch (alt43) {
            case 1 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:10: WHITESPACE
                {
                mWHITESPACE(); 


                }
                break;
            case 2 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:21: LINE_COMMENT
                {
                mLINE_COMMENT(); 


                }
                break;
            case 3 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:34: BLOCK_COMMENT
                {
                mBLOCK_COMMENT(); 


                }
                break;
            case 4 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:48: UNTERM_BLOCK_COMMENT
                {
                mUNTERM_BLOCK_COMMENT(); 


                }
                break;
            case 5 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:69: BOOL
                {
                mBOOL(); 


                }
                break;
            case 6 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:74: DOUBLE
                {
                mDOUBLE(); 


                }
                break;
            case 7 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:81: ELSE
                {
                mELSE(); 


                }
                break;
            case 8 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:86: FALSE
                {
                mFALSE(); 


                }
                break;
            case 9 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:92: IF
                {
                mIF(); 


                }
                break;
            case 10 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:95: INT
                {
                mINT(); 


                }
                break;
            case 11 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:99: NULL
                {
                mNULL(); 


                }
                break;
            case 12 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:104: READ
                {
                mREAD(); 


                }
                break;
            case 13 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:109: RETURN
                {
                mRETURN(); 


                }
                break;
            case 14 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:116: STRING
                {
                mSTRING(); 


                }
                break;
            case 15 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:123: TRUE
                {
                mTRUE(); 


                }
                break;
            case 16 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:128: VOID
                {
                mVOID(); 


                }
                break;
            case 17 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:133: WHILE
                {
                mWHILE(); 


                }
                break;
            case 18 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:139: WRITE
                {
                mWRITE(); 


                }
                break;
            case 19 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:145: LPAREN
                {
                mLPAREN(); 


                }
                break;
            case 20 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:152: RPAREN
                {
                mRPAREN(); 


                }
                break;
            case 21 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:159: LBRACE
                {
                mLBRACE(); 


                }
                break;
            case 22 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:166: RBRACE
                {
                mRBRACE(); 


                }
                break;
            case 23 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:173: LBRACKET
                {
                mLBRACKET(); 


                }
                break;
            case 24 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:182: RBRACKET
                {
                mRBRACKET(); 


                }
                break;
            case 25 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:191: SEMI
                {
                mSEMI(); 


                }
                break;
            case 26 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:196: COMMA
                {
                mCOMMA(); 


                }
                break;
            case 27 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:202: LAND
                {
                mLAND(); 


                }
                break;
            case 28 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:207: LOR
                {
                mLOR(); 


                }
                break;
            case 29 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:211: LNOT
                {
                mLNOT(); 


                }
                break;
            case 30 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:216: ASSIGN
                {
                mASSIGN(); 


                }
                break;
            case 31 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:223: ADDROF
                {
                mADDROF(); 


                }
                break;
            case 32 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:230: LT
                {
                mLT(); 


                }
                break;
            case 33 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:233: LTE
                {
                mLTE(); 


                }
                break;
            case 34 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:237: GT
                {
                mGT(); 


                }
                break;
            case 35 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:240: GTE
                {
                mGTE(); 


                }
                break;
            case 36 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:244: EQUAL
                {
                mEQUAL(); 


                }
                break;
            case 37 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:250: NEQUAL
                {
                mNEQUAL(); 


                }
                break;
            case 38 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:257: INCR
                {
                mINCR(); 


                }
                break;
            case 39 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:262: DECR
                {
                mDECR(); 


                }
                break;
            case 40 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:267: PLUS
                {
                mPLUS(); 


                }
                break;
            case 41 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:272: MINUS
                {
                mMINUS(); 


                }
                break;
            case 42 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:278: STAR
                {
                mSTAR(); 


                }
                break;
            case 43 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:283: DIV
                {
                mDIV(); 


                }
                break;
            case 44 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:287: MOD
                {
                mMOD(); 


                }
                break;
            case 45 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:291: IDENT
                {
                mIDENT(); 


                }
                break;
            case 46 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:297: BAD_IDENT
                {
                mBAD_IDENT(); 


                }
                break;
            case 47 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:307: STRING_LIT
                {
                mSTRING_LIT(); 


                }
                break;
            case 48 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:318: STRING_WITH_ILLEGAL_ESCAPE
                {
                mSTRING_WITH_ILLEGAL_ESCAPE(); 


                }
                break;
            case 49 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:345: STRING_WITH_TAB
                {
                mSTRING_WITH_TAB(); 


                }
                break;
            case 50 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:361: UNTERM_STRING
                {
                mUNTERM_STRING(); 


                }
                break;
            case 51 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:375: INT_LIT
                {
                mINT_LIT(); 


                }
                break;
            case 52 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:383: LEADING_ZERO_INT
                {
                mLEADING_ZERO_INT(); 


                }
                break;
            case 53 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:400: FLOAT_LIT
                {
                mFLOAT_LIT(); 


                }
                break;
            case 54 :
                // /afs/cs.wisc.edu/u/w/i/wilcox/public/cs536/pr6/src/cs536/syntax/MinC.g:1:410: LEADING_ZERO_FLOAT
                {
                mLEADING_ZERO_FLOAT(); 


                }
                break;

        }

    }


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA42 dfa42 = new DFA42(this);
    protected DFA43 dfa43 = new DFA43(this);
    static final String DFA6_eotS =
        "\4\uffff";
    static final String DFA6_eofS =
        "\4\uffff";
    static final String DFA6_minS =
        "\2\0\2\uffff";
    static final String DFA6_maxS =
        "\2\uffff\2\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA6_specialS =
        "\1\1\1\0\2\uffff}>";
    static final String[] DFA6_transitionS = {
            "\52\2\1\1\uffd5\2",
            "\52\2\1\1\4\2\1\3\uffd0\2",
            "",
            ""
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
            return "()* loopback of 361:13: ( BLOCK_COMMENT_CONTENTS )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA6_1 = input.LA(1);

                        s = -1;
                        if ( (LA6_1=='/') ) {s = 3;}

                        else if ( (LA6_1=='*') ) {s = 1;}

                        else if ( ((LA6_1 >= '\u0000' && LA6_1 <= ')')||(LA6_1 >= '+' && LA6_1 <= '.')||(LA6_1 >= '0' && LA6_1 <= '\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;

                    case 1 : 
                        int LA6_0 = input.LA(1);

                        s = -1;
                        if ( (LA6_0=='*') ) {s = 1;}

                        else if ( ((LA6_0 >= '\u0000' && LA6_0 <= ')')||(LA6_0 >= '+' && LA6_0 <= '\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 6, _s, input);
            error(nvae);
            throw nvae;
        }

    }
    static final String DFA8_eotS =
        "\2\2\2\uffff";
    static final String DFA8_eofS =
        "\4\uffff";
    static final String DFA8_minS =
        "\2\0\2\uffff";
    static final String DFA8_maxS =
        "\2\uffff\2\uffff";
    static final String DFA8_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA8_specialS =
        "\1\0\1\1\2\uffff}>";
    static final String[] DFA8_transitionS = {
            "\52\3\1\1\uffd5\3",
            "\52\3\1\1\4\3\1\uffff\uffd0\3",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "()* loopback of 365:13: ( BLOCK_COMMENT_CONTENTS )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_0 = input.LA(1);

                        s = -1;
                        if ( (LA8_0=='*') ) {s = 1;}

                        else if ( ((LA8_0 >= '\u0000' && LA8_0 <= ')')||(LA8_0 >= '+' && LA8_0 <= '\uFFFF')) ) {s = 3;}

                        else s = 2;

                        if ( s>=0 ) return s;
                        break;

                    case 1 : 
                        int LA8_1 = input.LA(1);

                        s = -1;
                        if ( (LA8_1=='*') ) {s = 1;}

                        else if ( ((LA8_1 >= '\u0000' && LA8_1 <= ')')||(LA8_1 >= '+' && LA8_1 <= '.')||(LA8_1 >= '0' && LA8_1 <= '\uFFFF')) ) {s = 3;}

                        else s = 2;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 8, _s, input);
            error(nvae);
            throw nvae;
        }

    }
    static final String DFA42_eotS =
        "\5\uffff\1\7\3\uffff\1\7\1\uffff";
    static final String DFA42_eofS =
        "\13\uffff";
    static final String DFA42_minS =
        "\1\60\1\56\1\uffff\1\56\2\60\1\55\1\uffff\2\60\1\uffff";
    static final String DFA42_maxS =
        "\2\71\1\uffff\2\71\1\145\1\71\1\uffff\2\71\1\uffff";
    static final String DFA42_acceptS =
        "\2\uffff\1\2\4\uffff\1\1\2\uffff\1\3";
    static final String DFA42_specialS =
        "\13\uffff}>";
    static final String[] DFA42_transitionS = {
            "\1\1\11\2",
            "\1\2\1\uffff\12\3",
            "",
            "\1\4\1\uffff\12\3",
            "\12\5",
            "\12\5\53\uffff\1\6",
            "\1\10\2\uffff\1\11\11\7",
            "",
            "\1\11\11\7",
            "\12\12",
            ""
    };

    static final short[] DFA42_eot = DFA.unpackEncodedString(DFA42_eotS);
    static final short[] DFA42_eof = DFA.unpackEncodedString(DFA42_eofS);
    static final char[] DFA42_min = DFA.unpackEncodedStringToUnsignedChars(DFA42_minS);
    static final char[] DFA42_max = DFA.unpackEncodedStringToUnsignedChars(DFA42_maxS);
    static final short[] DFA42_accept = DFA.unpackEncodedString(DFA42_acceptS);
    static final short[] DFA42_special = DFA.unpackEncodedString(DFA42_specialS);
    static final short[][] DFA42_transition;

    static {
        int numStates = DFA42_transitionS.length;
        DFA42_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA42_transition[i] = DFA.unpackEncodedString(DFA42_transitionS[i]);
        }
    }

    class DFA42 extends DFA {

        public DFA42(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 42;
            this.eot = DFA42_eot;
            this.eof = DFA42_eof;
            this.min = DFA42_min;
            this.max = DFA42_max;
            this.accept = DFA42_accept;
            this.special = DFA42_special;
            this.transition = DFA42_transition;
        }
        public String getDescription() {
            return "534:7: ( '0' ( DIGIT )+ '.' ( DIGIT )+ ( 'e' ( '-' )? INTEGER )? | INTEGER '.' ( DIGIT )+ 'e' ( '-' )? '0' ( DIGIT )+ | '0' ( DIGIT )+ '.' ( DIGIT )+ 'e' ( '-' )? '0' ( DIGIT )+ )";
        }
    }
    static final String DFA43_eotS =
        "\2\uffff\1\47\13\40\10\uffff\1\66\1\uffff\1\70\1\72\1\74\1\76\1"+
        "\100\1\102\3\uffff\1\103\1\105\1\116\1\105\1\uffff\1\122\1\uffff"+
        "\4\40\1\127\10\40\17\uffff\1\141\2\uffff\4\116\2\uffff\1\116\1\uffff"+
        "\1\105\2\122\1\uffff\4\40\1\uffff\1\163\10\40\2\uffff\1\175\6\116"+
        "\1\uffff\1\116\2\uffff\1\122\1\u0086\1\40\1\u0088\1\40\1\uffff\1"+
        "\u008a\1\u008b\2\40\1\u008e\1\u008f\2\40\2\uffff\4\116\2\uffff\2"+
        "\116\1\uffff\1\40\1\uffff\1\u0097\2\uffff\2\40\2\uffff\1\u009a\1"+
        "\u009b\1\uffff\1\175\2\116\1\u009c\1\uffff\1\u009d\1\u009e\5\uffff";
    static final String DFA43_eofS =
        "\u009f\uffff";
    static final String DFA43_minS =
        "\1\11\1\uffff\1\52\2\157\1\154\1\141\1\146\1\165\1\145\1\164\1\162"+
        "\1\157\1\150\10\uffff\1\46\1\uffff\4\75\1\53\1\55\3\uffff\2\44\1"+
        "\11\1\44\1\uffff\1\0\1\uffff\1\157\1\165\1\163\1\154\1\44\1\164"+
        "\1\154\1\141\1\162\1\165\3\151\17\uffff\1\44\1\uffff\1\60\4\11\1"+
        "\0\1\uffff\1\11\1\uffff\1\44\2\0\1\uffff\1\154\1\142\1\145\1\163"+
        "\1\uffff\1\44\1\154\1\144\1\165\1\151\1\145\1\144\1\154\1\164\2"+
        "\uffff\1\60\6\11\1\0\1\11\2\uffff\1\0\1\44\1\154\1\44\1\145\1\uffff"+
        "\2\44\1\162\1\156\2\44\2\145\1\55\1\uffff\4\11\1\0\1\uffff\2\11"+
        "\1\uffff\1\145\1\uffff\1\44\2\uffff\1\156\1\147\2\uffff\2\44\2\60"+
        "\2\11\1\44\1\uffff\2\44\5\uffff";
    static final String DFA43_maxS =
        "\1\175\1\uffff\1\57\2\157\1\154\1\141\1\156\1\165\1\145\1\164\1"+
        "\162\1\157\1\162\10\uffff\1\46\1\uffff\4\75\1\53\1\55\3\uffff\2"+
        "\172\1\176\1\172\1\uffff\1\uffff\1\uffff\1\157\1\165\1\163\1\154"+
        "\1\172\1\164\1\154\1\164\1\162\1\165\3\151\17\uffff\1\172\1\uffff"+
        "\1\71\4\176\1\uffff\1\uffff\1\176\1\uffff\1\172\2\uffff\1\uffff"+
        "\1\154\1\142\1\145\1\163\1\uffff\1\172\1\154\1\144\1\165\1\151\1"+
        "\145\1\144\1\154\1\164\2\uffff\1\145\6\176\1\uffff\1\176\2\uffff"+
        "\1\uffff\1\172\1\154\1\172\1\145\1\uffff\2\172\1\162\1\156\2\172"+
        "\2\145\1\71\1\uffff\4\176\1\uffff\1\uffff\2\176\1\uffff\1\145\1"+
        "\uffff\1\172\2\uffff\1\156\1\147\2\uffff\2\172\2\71\2\176\1\172"+
        "\1\uffff\2\172\5\uffff";
    static final String DFA43_acceptS =
        "\1\uffff\1\1\14\uffff\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1"+
        "\uffff\1\34\6\uffff\1\52\1\54\1\55\4\uffff\1\2\1\uffff\1\53\15\uffff"+
        "\1\33\1\37\1\45\1\35\1\44\1\36\1\41\1\40\1\43\1\42\1\46\1\50\1\47"+
        "\1\51\1\56\1\uffff\1\63\6\uffff\1\57\1\uffff\1\62\3\uffff\1\4\4"+
        "\uffff\1\11\11\uffff\1\64\1\66\11\uffff\1\61\1\3\5\uffff\1\12\11"+
        "\uffff\1\65\5\uffff\1\60\2\uffff\1\5\1\uffff\1\7\1\uffff\1\13\1"+
        "\14\2\uffff\1\17\1\20\7\uffff\1\10\2\uffff\1\21\1\22\1\6\1\15\1"+
        "\16";
    static final String DFA43_specialS =
        "\46\uffff\1\4\44\uffff\1\3\4\uffff\1\6\1\1\30\uffff\1\5\3\uffff"+
        "\1\0\23\uffff\1\2\34\uffff}>";
    static final String[] DFA43_transitionS = {
            "\2\1\2\uffff\1\1\22\uffff\1\1\1\30\1\43\1\uffff\1\41\1\37\1"+
            "\26\1\uffff\1\16\1\17\1\36\1\34\1\25\1\35\1\uffff\1\2\1\42\11"+
            "\44\1\uffff\1\24\1\32\1\31\1\33\2\uffff\32\40\1\22\1\uffff\1"+
            "\23\1\uffff\1\41\1\uffff\1\40\1\3\1\40\1\4\1\5\1\6\2\40\1\7"+
            "\4\40\1\10\3\40\1\11\1\12\1\13\1\40\1\14\1\15\3\40\1\20\1\27"+
            "\1\21",
            "",
            "\1\46\4\uffff\1\45",
            "\1\50",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54\7\uffff\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63\11\uffff\1\64",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\65",
            "",
            "\1\67",
            "\1\71",
            "\1\73",
            "\1\75",
            "\1\77",
            "\1\101",
            "",
            "",
            "",
            "\1\41\13\uffff\12\40\7\uffff\32\40\4\uffff\1\41\1\uffff\32"+
            "\40",
            "\1\103\11\uffff\1\106\1\uffff\12\104\7\uffff\32\103\4\uffff"+
            "\1\103\1\uffff\32\103",
            "\1\115\26\uffff\1\107\1\110\1\114\71\111\1\113\42\112",
            "\1\103\11\uffff\1\106\1\uffff\12\117\7\uffff\32\103\4\uffff"+
            "\1\103\1\uffff\32\103",
            "",
            "\52\121\1\120\uffd5\121",
            "",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\130",
            "\1\131",
            "\1\132\22\uffff\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\103\11\uffff\1\142\1\uffff\12\104\7\uffff\32\103\4\uffff"+
            "\1\103\1\uffff\32\103",
            "",
            "\12\143",
            "\1\115\26\uffff\1\107\1\110\1\114\71\111\1\113\42\112",
            "\1\115\26\uffff\1\107\1\110\1\114\71\111\1\113\42\112",
            "\1\115\26\uffff\1\107\1\110\1\114\71\111\1\113\42\112",
            "\1\115\26\uffff\1\107\1\110\1\114\71\111\1\113\42\112",
            "\42\144\1\145\71\144\1\145\21\144\1\145\5\144\1\145\uff8b\144",
            "",
            "\1\153\26\uffff\1\146\1\147\1\154\71\150\1\152\42\151",
            "",
            "\1\103\11\uffff\1\106\1\uffff\12\117\7\uffff\32\103\4\uffff"+
            "\1\103\1\uffff\32\103",
            "\52\156\1\120\4\156\1\155\uffd0\156",
            "\52\121\1\120\uffd5\121",
            "",
            "\1\157",
            "\1\160",
            "\1\161",
            "\1\162",
            "",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "\1\170",
            "\1\171",
            "\1\172",
            "\1\173",
            "",
            "",
            "\12\143\53\uffff\1\174",
            "\1\115\26\uffff\1\176\1\177\1\u0083\71\u0080\1\u0082\42\u0081",
            "\1\115\26\uffff\1\107\1\110\1\114\71\111\1\113\42\112",
            "\1\153\26\uffff\1\146\1\147\1\154\71\150\1\152\42\151",
            "\1\153\26\uffff\1\146\1\147\1\154\71\150\1\152\42\151",
            "\1\153\26\uffff\1\146\1\147\1\154\71\150\1\152\42\151",
            "\1\153\26\uffff\1\146\1\147\1\154\71\150\1\152\42\151",
            "\42\u0084\1\u0085\71\u0084\1\u0085\21\u0084\1\u0085\5\u0084"+
            "\1\u0085\uff8b\u0084",
            "\1\153\26\uffff\1\146\1\147\1\154\71\150\1\152\42\151",
            "",
            "",
            "\52\121\1\120\uffd5\121",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\u0087",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\u0089",
            "",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\u008c",
            "\1\u008d",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\u0090",
            "\1\u0091",
            "\1\u0092\2\uffff\1\u0093\11\175",
            "",
            "\1\115\26\uffff\1\176\1\177\1\u0083\71\u0080\1\u0082\42\u0081",
            "\1\115\26\uffff\1\176\1\177\1\u0083\71\u0080\1\u0082\42\u0081",
            "\1\115\26\uffff\1\176\1\177\1\u0083\71\u0080\1\u0082\42\u0081",
            "\1\115\26\uffff\1\176\1\177\1\u0083\71\u0080\1\u0082\42\u0081",
            "\42\u0094\1\u0095\71\u0094\1\u0095\21\u0094\1\u0095\5\u0094"+
            "\1\u0095\uff8b\u0094",
            "",
            "\1\153\26\uffff\1\146\1\147\1\154\71\150\1\152\42\151",
            "\1\153\26\uffff\1\146\1\147\1\154\71\150\1\152\42\151",
            "",
            "\1\u0096",
            "",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "",
            "",
            "\1\u0098",
            "\1\u0099",
            "",
            "",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\u0093\11\175",
            "\12\142",
            "\1\115\26\uffff\1\176\1\177\1\u0083\71\u0080\1\u0082\42\u0081",
            "\1\115\26\uffff\1\176\1\177\1\u0083\71\u0080\1\u0082\42\u0081",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA43_eot = DFA.unpackEncodedString(DFA43_eotS);
    static final short[] DFA43_eof = DFA.unpackEncodedString(DFA43_eofS);
    static final char[] DFA43_min = DFA.unpackEncodedStringToUnsignedChars(DFA43_minS);
    static final char[] DFA43_max = DFA.unpackEncodedStringToUnsignedChars(DFA43_maxS);
    static final short[] DFA43_accept = DFA.unpackEncodedString(DFA43_acceptS);
    static final short[] DFA43_special = DFA.unpackEncodedString(DFA43_specialS);
    static final short[][] DFA43_transition;

    static {
        int numStates = DFA43_transitionS.length;
        DFA43_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA43_transition[i] = DFA.unpackEncodedString(DFA43_transitionS[i]);
        }
    }

    class DFA43 extends DFA {

        public DFA43(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 43;
            this.eot = DFA43_eot;
            this.eof = DFA43_eof;
            this.min = DFA43_min;
            this.max = DFA43_max;
            this.accept = DFA43_accept;
            this.special = DFA43_special;
            this.transition = DFA43_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( WHITESPACE | LINE_COMMENT | BLOCK_COMMENT | UNTERM_BLOCK_COMMENT | BOOL | DOUBLE | ELSE | FALSE | IF | INT | NULL | READ | RETURN | STRING | TRUE | VOID | WHILE | WRITE | LPAREN | RPAREN | LBRACE | RBRACE | LBRACKET | RBRACKET | SEMI | COMMA | LAND | LOR | LNOT | ASSIGN | ADDROF | LT | LTE | GT | GTE | EQUAL | NEQUAL | INCR | DECR | PLUS | MINUS | STAR | DIV | MOD | IDENT | BAD_IDENT | STRING_LIT | STRING_WITH_ILLEGAL_ESCAPE | STRING_WITH_TAB | UNTERM_STRING | INT_LIT | LEADING_ZERO_INT | FLOAT_LIT | LEADING_ZERO_FLOAT );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA43_110 = input.LA(1);

                        s = -1;
                        if ( (LA43_110=='*') ) {s = 80;}

                        else if ( ((LA43_110 >= '\u0000' && LA43_110 <= ')')||(LA43_110 >= '+' && LA43_110 <= '\uFFFF')) ) {s = 81;}

                        else s = 82;

                        if ( s>=0 ) return s;
                        break;

                    case 1 : 
                        int LA43_81 = input.LA(1);

                        s = -1;
                        if ( (LA43_81=='*') ) {s = 80;}

                        else if ( ((LA43_81 >= '\u0000' && LA43_81 <= ')')||(LA43_81 >= '+' && LA43_81 <= '\uFFFF')) ) {s = 81;}

                        else s = 82;

                        if ( s>=0 ) return s;
                        break;

                    case 2 : 
                        int LA43_130 = input.LA(1);

                        s = -1;
                        if ( ((LA43_130 >= '\u0000' && LA43_130 <= '!')||(LA43_130 >= '#' && LA43_130 <= '[')||(LA43_130 >= ']' && LA43_130 <= 'm')||(LA43_130 >= 'o' && LA43_130 <= 's')||(LA43_130 >= 'u' && LA43_130 <= '\uFFFF')) ) {s = 148;}

                        else if ( (LA43_130=='\"'||LA43_130=='\\'||LA43_130=='n'||LA43_130=='t') ) {s = 149;}

                        if ( s>=0 ) return s;
                        break;

                    case 3 : 
                        int LA43_75 = input.LA(1);

                        s = -1;
                        if ( ((LA43_75 >= '\u0000' && LA43_75 <= '!')||(LA43_75 >= '#' && LA43_75 <= '[')||(LA43_75 >= ']' && LA43_75 <= 'm')||(LA43_75 >= 'o' && LA43_75 <= 's')||(LA43_75 >= 'u' && LA43_75 <= '\uFFFF')) ) {s = 100;}

                        else if ( (LA43_75=='\"'||LA43_75=='\\'||LA43_75=='n'||LA43_75=='t') ) {s = 101;}

                        if ( s>=0 ) return s;
                        break;

                    case 4 : 
                        int LA43_38 = input.LA(1);

                        s = -1;
                        if ( (LA43_38=='*') ) {s = 80;}

                        else if ( ((LA43_38 >= '\u0000' && LA43_38 <= ')')||(LA43_38 >= '+' && LA43_38 <= '\uFFFF')) ) {s = 81;}

                        else s = 82;

                        if ( s>=0 ) return s;
                        break;

                    case 5 : 
                        int LA43_106 = input.LA(1);

                        s = -1;
                        if ( ((LA43_106 >= '\u0000' && LA43_106 <= '!')||(LA43_106 >= '#' && LA43_106 <= '[')||(LA43_106 >= ']' && LA43_106 <= 'm')||(LA43_106 >= 'o' && LA43_106 <= 's')||(LA43_106 >= 'u' && LA43_106 <= '\uFFFF')) ) {s = 132;}

                        else if ( (LA43_106=='\"'||LA43_106=='\\'||LA43_106=='n'||LA43_106=='t') ) {s = 133;}

                        if ( s>=0 ) return s;
                        break;

                    case 6 : 
                        int LA43_80 = input.LA(1);

                        s = -1;
                        if ( (LA43_80=='/') ) {s = 109;}

                        else if ( ((LA43_80 >= '\u0000' && LA43_80 <= ')')||(LA43_80 >= '+' && LA43_80 <= '.')||(LA43_80 >= '0' && LA43_80 <= '\uFFFF')) ) {s = 110;}

                        else if ( (LA43_80=='*') ) {s = 80;}

                        else s = 82;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 43, _s, input);
            error(nvae);
            throw nvae;
        }

    }
 

}