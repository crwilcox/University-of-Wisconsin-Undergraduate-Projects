package cs536.codegen;

/** This class represents just a label.
 *  
 */
public class Label extends Instruction
{
    private final String label;

    public Label(String label)
    {
	this.label = label;
    }


    public String toString()
    {
	return label + ":";
    }
}
