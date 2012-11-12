package cs536.codegen;

public class Instruction
{
    protected final static String indent = "        ";
    protected final static int maxOpLen = 6;

    // Pads the string on the right out to 'maxOpLen + 1' characters. E.g. "add" becomes "add    "
    protected String pad(String s) {
        return String.format("%1$-" + (maxOpLen + 1) + "s", s);  
    }
    
}
