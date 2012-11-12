package cs536.codegen.mips;

import cs536.codegen.Instruction;
import cs536.codegen.Label;
import cs536.codegen.AsmDirective;

public class Mips extends AutogenMipsWithPseudo
{
    /** Do NOT pass a multi-line string! */
    public Instruction comment(String comment)
    {
	return new AsmDirective("# " + comment);
    }

    
    public Instruction newline()
    {
	return new AsmDirective("");
    }

    public Instruction label(String label)
    {
	return new Label(label);
    }

    /// ASSEMBLER DIRECTIVES
    public Instruction _globl(String label)          { return new AsmDirective(".globl", label);             }
    public Instruction _text()                       { return new AsmDirective(".text");                     }
    public Instruction _ktext()                      { return new AsmDirective(".ktext");                    }
    public Instruction _data()                       { return new AsmDirective(".data");                     }
    public Instruction _kdata()                      { return new AsmDirective(".kdata");                    }

    public Instruction _align(int power_of_two_size) { return new AsmDirective(".align", ""+power_of_two_size); }

    public Instruction _ascii(String str)            { return new AsmDirective(".ascii", str);               }
    public Instruction _asciiz(String str)           { return new AsmDirective(".asciiz", str);              }
    public Instruction _byte(byte b)                 { return new AsmDirective(".byte", ""+b);               }
    public Instruction _half(short hw)               { return new AsmDirective(".half", ""+hw);              }
    public Instruction _word(int w)                  { return new AsmDirective(".word", ""+w);               }
    public Instruction _float(float f)               { return new AsmDirective(".float", ""+f);              }
    public Instruction _double(double d)             { return new AsmDirective(".double", ""+d);             }
    public Instruction _space(int size)              { return new AsmDirective(".space", ""+size);           }

}

