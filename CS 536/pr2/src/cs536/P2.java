package cs536;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Token;
import cs536.syntax.MinC;

public class P2 {
	public static void main(String argv[]) throws FileNotFoundException {
		//TODO: REMOVE LATER. FOR ECLIPSE EASE
		//System.setIn(new FileInputStream(argv[0]));

		try {

			// Create the lexer using an ANTLR-wrapped stream
			ANTLRInputStream input = new ANTLRInputStream(System.in);
			MinC lexer = new MinC(input);

			
			// Print out each token.
			Token t;
			do {
				t = lexer.nextToken();

				if (t.getType() == Token.EOF)
					break;
				
				System.out.println(t.getLine() + ":"
						+ (t.getCharPositionInLine() + 1) + ":" + t.toString()
				);
				
			} while (true);


		} catch (IOException ex) {
			System.err.println("IO EXCEPTION");
			System.exit(1);
		}
	}
}
