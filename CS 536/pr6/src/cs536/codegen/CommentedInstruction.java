package cs536.codegen;

/** This class adds a comment at the end of any instruction
 *  
 */
public class CommentedInstruction extends Instruction
{
    private static int CommentColumn = 40;

    // Pads the string on the right out to 'maxOpLen + 1' characters. E.g. "add" becomes "add    "
    protected String pad(String s, int len) {
        return String.format("%1$-" + len + "s", s);  
    }

    private final Instruction inner;
    private final String comment;

    public CommentedInstruction(Instruction inner_instruction, String comment)
    {
	inner = inner_instruction;
	this.comment = comment;
    }


    public String toString()
    {
	String s = inner.toString();
	s = pad(s, CommentColumn);
	s = s + "# " + comment;
	return s;
    }
}
