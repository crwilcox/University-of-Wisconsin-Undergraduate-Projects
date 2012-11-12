package cs536.codegen.mips;

import cs536.codegen.Instruction;

/** This class represents a single MIPS instruction. It is used for
 * almost all instructions; anything that doesn't do a memory access.
 *  
 */
public class NonMemoryInstruction extends Instruction
{
    protected final String opcode;
    protected final String op1;
    protected final String op2;
    protected final String op3;
    protected final String op4;

    public NonMemoryInstruction(String opcode, String operand1, String operand2, String operand3, String operand4)
    {
        this.opcode = opcode;
        op1 = operand1;
        op2 = operand2;
        op3 = operand3;
        op4 = operand4;
    }

    public NonMemoryInstruction(String opcode, String operand1, String operand2, String operand3)
    {
        this.opcode = opcode;
        op1 = operand1;
        op2 = operand2;
        op3 = operand3;
        op4 = null;
    }

    public NonMemoryInstruction(String opcode, String operand1, String operand2)
    {
        this.opcode = opcode;
        op1 = operand1;
        op2 = operand2;
        op3 = null;
        op4 = null;
    }

    public NonMemoryInstruction(String opcode, String operand1)
    {
        this.opcode = opcode;
        op1 = operand1;
        op2 = null;
        op3 = null;
        op4 = null;
    }

    public NonMemoryInstruction(String opcode)
    {
        this.opcode = opcode;
        op1 = null;
        op2 = null;
        op3 = null;
        op4 = null;
    }


    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(indent + pad(opcode));

        if (op1 != null) {
            sb.append(" " + op1);
        }
        if (op2 != null) {
            sb.append(", " + op2);
        }
        if (op3 != null) {
            sb.append(", " + op3);
        }
        if (op4 != null) {
            sb.append(", " + op4);
        }

        return sb.toString();
    }
}
