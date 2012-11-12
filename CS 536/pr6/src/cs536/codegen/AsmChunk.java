package cs536.codegen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AsmChunk
{
    private List<Instruction> asmStmts;
    
    public AsmChunk()
    {
        asmStmts = new ArrayList<Instruction>();
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Iterator<Instruction> it = asmStmts.iterator();
        while(it.hasNext()) {
            Instruction s = it.next();
            sb.append(s + "\n");
        }

        return sb.toString();
    }

    public void append(Instruction instr)
    {
        asmStmts.add(instr);
    }

    public void append(Instruction instr, String comment)
    {
        asmStmts.add(new CommentedInstruction(instr, comment));
    }

    public void appendChunk(AsmChunk other)
    {
        asmStmts.addAll(other.asmStmts);
    }

    public void appendChunks(List<AsmChunk> others)
    {
        Iterator<AsmChunk> it = others.iterator();
        while(it.hasNext()) {
            AsmChunk other = it.next();
            if(other != null)
                appendChunk(other);
        }
    }
}
