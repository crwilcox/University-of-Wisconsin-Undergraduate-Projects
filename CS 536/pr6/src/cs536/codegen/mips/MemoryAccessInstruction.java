package cs536.codegen.mips;

import cs536.codegen.Instruction;

class MemoryAccessInstruction extends Instruction
{
    protected final String instruction;

    public MemoryAccessInstruction(String opcode, String operand1, int operand2, String operand3)
    {
        // must be 'op dest, off(basereg)'
        instruction = String.format("%-7s %s, %s(%s)", opcode, operand1, operand2, operand3);
    }

    public MemoryAccessInstruction(String opcode, String operand1, String operand2)
    {
        // two possibilities. either 'op dest, (reg)' or 'op dest, label'.
        if (operand2.charAt(0) == '$') {
            instruction = String.format("%-7s %s, (%s)", opcode, operand1, operand2);
        }
        else {
            instruction = String.format("%-7s %s, %s", opcode, operand1, operand2);
        }
    }

    public MemoryAccessInstruction(String opcode, String operand1, int operand2)
    {
        // only choice: 'op  dest, addr'
        instruction = String.format("%-7s %s, %s", opcode, operand1, operand2);
    }

    public MemoryAccessInstruction(String opcode, String operand1, String operand2, String operand3)
    {
        // 'op dest, label(reg)'
        instruction = String.format("%-7s %s, %s(%s)", opcode, operand1, operand2, operand3);
    }

    public MemoryAccessInstruction(String opcode, String operand1, String operand2, int operand3)
    {
        // 'op dest, label + offset'
        instruction = String.format("%-7s %s, %s+%s", opcode, operand1, operand2, operand3);
    }

    public MemoryAccessInstruction(String opcode, String operand1, String operand2, int operand3, String operand4)
    {
        // 'op dest, label + offset'
        instruction = String.format("%-7s %s, %s+%s(%s)", opcode, operand1, operand2, operand3, operand4);
    }


    public String toString()
    {
        return indent + instruction;
    }
}
