package cs536.codegen;

/** This is assembly directives. Almost the same as NonMemoryInstructions,
 * but no commas between arguments.
 *  
 */
public class AsmDirective extends Instruction
{
    protected final String opcode;
    protected final String op1;
    protected final String op2;
    protected final String op3;

    public AsmDirective(String opcode, String operand1, String operand2, String operand3)
    {
        this.opcode = opcode;
        op1 = operand1;
        op2 = operand2;
        op3 = operand3;
    }

    public AsmDirective(String opcode, String operand1, String operand2)
    {
        this.opcode = opcode;
        op1 = operand1;
        op2 = operand2;
        op3 = null;
    }

    public AsmDirective(String opcode, String operand1)
    {
        this.opcode = opcode;
        op1 = operand1;
        op2 = null;
        op3 = null;
    }

    public AsmDirective(String opcode)
    {
        this.opcode = opcode;
        op1 = null;
        op2 = null;
        op3 = null;
    }


    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(indent + pad(opcode));

        if (op1 != null) {
            sb.append(" " + op1);
        }
        if (op2 != null) {
            sb.append("  " + op2);
        }
        if (op3 != null) {
            sb.append("  " + op3);
        }

        return sb.toString();
    }
}
