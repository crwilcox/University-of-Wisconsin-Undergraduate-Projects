package cs536.codegen.mips;

import cs536.codegen.Instruction;
import cs536.codegen.mips.NonMemoryInstruction;

public class AutogenMipsWithPseudo extends AutogenMips
{
    /** Bitwise NOT (bit inversion) */ 
    public Instruction not (Regs t1, Regs t2) { return new NonMemoryInstruction("not", "$"+t1, "$"+t2); }
    /** ADDition : set $t1 to ($t2 plus 16-bit immediate) */ 
    public Instruction add (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("add", "$"+t1, "$"+t2, ""+signed); }
    /** ADDition Unsigned : set $t1 to ($t2 plus 32-bit immediate), no overflow */ 
    public Instruction addu (Regs t1, Regs t2, int large_unsigned) { return new NonMemoryInstruction("addu", "$"+t1, "$"+t2, ""+large_unsigned); }
    /** ADDition Immediate : set $t1 to ($t2 plus 32-bit immediate) */ 
    public Instruction addi (Regs t1, Regs t2, int large_unsigned) { return new NonMemoryInstruction("addi", "$"+t1, "$"+t2, ""+large_unsigned); }
    /** ADDition Immediate Unsigned: set $t1 to ($t2 plus 32-bit immediate), no overflow */ 
    public Instruction addiu (Regs t1, Regs t2, int large_unsigned) { return new NonMemoryInstruction("addiu", "$"+t1, "$"+t2, ""+large_unsigned); }
    /** SUBtraction : set $t1 to ($t2 minus 16-bit immediate) */ 
    public Instruction sub (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("sub", "$"+t1, "$"+t2, ""+signed); }
    /** SUBtraction Unsigned : set $t1 to ($t2 minus 32-bit immediate), no overflow */ 
    public Instruction subu (Regs t1, Regs t2, int large_unsigned) { return new NonMemoryInstruction("subu", "$"+t1, "$"+t2, ""+large_unsigned); }
    /** SUBtraction Immediate : set $t1 to ($t2 minus 16-bit immediate) */ 
    public Instruction subi (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("subi", "$"+t1, "$"+t2, ""+signed); }
    /** SUBtraction Immediate Unsigned : set $t1 to ($t2 minus 32-bit immediate), no overflow */ 
    public Instruction subiu (Regs t1, Regs t2, int large_unsigned) { return new NonMemoryInstruction("subiu", "$"+t1, "$"+t2, ""+large_unsigned); }
    /** AND : set $t1 to ($t2 bitwise-AND 16-bit unsigned immediate) */ 
    public Instruction and (Regs t1, Regs t2, int unsigned) { return new NonMemoryInstruction("and", "$"+t1, "$"+t2, ""+unsigned); }
    /** OR : set $t1 to ($t2 bitwise-OR 16-bit unsigned immediate) */ 
    public Instruction or (Regs t1, Regs t2, int unsigned) { return new NonMemoryInstruction("or", "$"+t1, "$"+t2, ""+unsigned); }
    /** XOR : set $t1 to ($t2 bitwise-exclusive-OR 16-bit unsigned immediate) */ 
    public Instruction xor (Regs t1, Regs t2, int unsigned) { return new NonMemoryInstruction("xor", "$"+t1, "$"+t2, ""+unsigned); }
    /** AND : set $t1 to ($t1 bitwise-AND 16-bit unsigned immediate) */ 
    public Instruction and (Regs t1, int unsigned) { return new NonMemoryInstruction("and", "$"+t1, ""+unsigned); }
    /** OR : set $t1 to ($t1 bitwise-OR 16-bit unsigned immediate) */ 
    public Instruction or (Regs t1, int unsigned) { return new NonMemoryInstruction("or", "$"+t1, ""+unsigned); }
    /** XOR : set $t1 to ($t1 bitwise-exclusive-OR 16-bit unsigned immediate) */ 
    public Instruction xor (Regs t1, int unsigned) { return new NonMemoryInstruction("xor", "$"+t1, ""+unsigned); }
    /** AND Immediate : set $t1 to ($t1 bitwise-AND 16-bit unsigned immediate) */ 
    public Instruction andi (Regs t1, int unsigned) { return new NonMemoryInstruction("andi", "$"+t1, ""+unsigned); }
    /** OR Immediate : set $t1 to ($t1 bitwise-OR 16-bit unsigned immediate) */ 
    public Instruction ori (Regs t1, int unsigned) { return new NonMemoryInstruction("ori", "$"+t1, ""+unsigned); }
    /** XOR Immediate : set $t1 to ($t1 bitwise-exclusive-OR 16-bit unsigned immediate) */ 
    public Instruction xori (Regs t1, int unsigned) { return new NonMemoryInstruction("xori", "$"+t1, ""+unsigned); }
    /** Set EQual : if $t2 equal to $t3 then set $t1 to 1 else 0 */ 
    public Instruction seq (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("seq", "$"+t1, "$"+t2, "$"+t3); }
    /** Set EQual : if $t2 equal to 16-bit immediate then set $t1 to 1 else 0 */ 
    public Instruction seq (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("seq", "$"+t1, "$"+t2, ""+signed); }
    /** Set Not Equal : if $t2 not equal to $t3 then set $t1 to 1 else 0 */ 
    public Instruction sne (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("sne", "$"+t1, "$"+t2, "$"+t3); }
    /** Set Not Equal : if $t2 not equal to 16-bit immediate then set $t1 to 1 else 0 */ 
    public Instruction sne (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("sne", "$"+t1, "$"+t2, ""+signed); }
    /** Set Greater or Equal : if $t2 greater or equal to $t3 then set $t1 to 1 else 0 */ 
    public Instruction sge (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("sge", "$"+t1, "$"+t2, "$"+t3); }
    /** Set Greater or Equal : if $t2 greater or equal to 16-bit immediate then set $t1 to 1 else 0 */ 
    public Instruction sge (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("sge", "$"+t1, "$"+t2, ""+signed); }
    /** Set Greater or Equal Unsigned : if $t2 greater or equal to $t3 (unsigned compare) then set $t1 to 1 else 0 */ 
    public Instruction sgeu (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("sgeu", "$"+t1, "$"+t2, "$"+t3); }
    /** Set Greater or Equal Unsigned : if $t2 greater or equal to 16-bit immediate (unsigned compare) then set $t1 to 1 else 0 */ 
    public Instruction sgeu (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("sgeu", "$"+t1, "$"+t2, ""+signed); }
    /** Set Greater Than : if $t2 greater than $t3 then set $t1 to 1 else 0 */ 
    public Instruction sgt (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("sgt", "$"+t1, "$"+t2, "$"+t3); }
    /** Set Greater Than : if $t2 greater than 16-bit immediate then set $t1 to 1 else 0 */ 
    public Instruction sgt (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("sgt", "$"+t1, "$"+t2, ""+signed); }
    /** Set Greater Than Unsigned : if $t2 greater than $t3 (unsigned compare) then set $t1 to 1 else 0 */ 
    public Instruction sgtu (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("sgtu", "$"+t1, "$"+t2, "$"+t3); }
    /** Set Greater Than Unsigned : if $t2 greater than 16-bit immediate (unsigned compare) then set $t1 to 1 else 0 */ 
    public Instruction sgtu (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("sgtu", "$"+t1, "$"+t2, ""+signed); }
    /** Set Less or Equal : if $t2 less or equal to $t3 then set $t1 to 1 else 0 */ 
    public Instruction sle (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("sle", "$"+t1, "$"+t2, "$"+t3); }
    /** Set Less or Equal : if $t2 less or equal to 16-bit immediate then set $t1 to 1 else 0 */ 
    public Instruction sle (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("sle", "$"+t1, "$"+t2, ""+signed); }
    /** Set Less or Equal Unsigned: if $t2 less or equal to $t3 (unsigned compare) then set $t1 to 1 else 0 */ 
    public Instruction sleu (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("sleu", "$"+t1, "$"+t2, "$"+t3); }
    /** Set Less or Equal Unsigned: if $t2 less or equal to 16-bit immediate (unsigned compare) then set $t1 to 1 else 0 */ 
    public Instruction sleu (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("sleu", "$"+t1, "$"+t2, ""+signed); }
    /** MOVE : Set $t1 to contents of $t2 */ 
    public Instruction move (Regs t1, Regs t2) { return new NonMemoryInstruction("move", "$"+t1, "$"+t2); }
    /** ABSolute value : Set $t1 to absolute value of $t2 (algorithm from Hacker's Delight)  */ 
    public Instruction abs (Regs t1, Regs t2) { return new NonMemoryInstruction("abs", "$"+t1, "$"+t2); }
    /** NEGate : Set $t1 to negation of $t2 */ 
    public Instruction neg (Regs t1, Regs t2) { return new NonMemoryInstruction("neg", "$"+t1, "$"+t2); }
    /** NEGate Unsigned : Set $t1 to negation of $t2, no overflow */ 
    public Instruction negu (Regs t1, Regs t2) { return new NonMemoryInstruction("negu", "$"+t1, "$"+t2); }
    /** Branch : Branch to statement at label unconditionally */ 
    public Instruction b (String label) { return new NonMemoryInstruction("b", ""+label); }
    /** Branch if EQual Zero : Branch to statement at label if $t1 is equal to zero */ 
    public Instruction beqz (Regs t1, String label) { return new NonMemoryInstruction("beqz", "$"+t1, ""+label); }
    /** Branch if Not Equal Zero : Branch to statement at label if $t1 is not equal to zero */ 
    public Instruction bnez (Regs t1, String label) { return new NonMemoryInstruction("bnez", "$"+t1, ""+label); }
    /** Branch if EQual : Branch to statement at label if $t1 is equal to 16-bit immediate */ 
    public Instruction beq (Regs t1, int signed, String label) { return new NonMemoryInstruction("beq", "$"+t1, ""+signed, ""+label); }
    /** Branch if Not Equal : Branch to statement at label if $t1 is not equal to 16-bit immediate */ 
    public Instruction bne (Regs t1, int signed, String label) { return new NonMemoryInstruction("bne", "$"+t1, ""+signed, ""+label); }
    /** Branch if Greater or Equal : Branch to statement at label if $t1 is greater or equal to $t2 */ 
    public Instruction bge (Regs t1, Regs t2, String label) { return new NonMemoryInstruction("bge", "$"+t1, "$"+t2, ""+label); }
    /** Branch if Greater or Equal : Branch to statement at label if $t1 is greater or equal to 16-bit immediate      */ 
    public Instruction bge (Regs t1, int signed, String label) { return new NonMemoryInstruction("bge", "$"+t1, ""+signed, ""+label); }
    /** Branch if Greater or Equal Unsigned : Branch to statement at label if $t1 is greater or equal to $t2 (unsigned compare) */ 
    public Instruction bgeu (Regs t1, Regs t2, String label) { return new NonMemoryInstruction("bgeu", "$"+t1, "$"+t2, ""+label); }
    /** Branch if Greater or Equal Unsigned : Branch to statement at label if $t1 is greater or equal to 16-bit immediate (unsigned compare) */ 
    public Instruction bgeu (Regs t1, int signed, String label) { return new NonMemoryInstruction("bgeu", "$"+t1, ""+signed, ""+label); }
    /** Branch if Greater Than : Branch to statement at label if $t1 is greater than $t2 */ 
    public Instruction bgt (Regs t1, Regs t2, String label) { return new NonMemoryInstruction("bgt", "$"+t1, "$"+t2, ""+label); }
    /** Branch if Greater Than : Branch to statement at label if $t1 is greater than 16-bit immediate      */ 
    public Instruction bgt (Regs t1, int signed, String label) { return new NonMemoryInstruction("bgt", "$"+t1, ""+signed, ""+label); }
    /** Branch if Greater Than Unsigned: Branch to statement at label if $t1 is greater than $t2 (unsigned compare) */ 
    public Instruction bgtu (Regs t1, Regs t2, String label) { return new NonMemoryInstruction("bgtu", "$"+t1, "$"+t2, ""+label); }
    /** Branch if Greater Than Unsigned: Branch to statement at label if $t1 is greater than 16-bit immediate (unsigned compare) */ 
    public Instruction bgtu (Regs t1, int signed, String label) { return new NonMemoryInstruction("bgtu", "$"+t1, ""+signed, ""+label); }
    /** Branch if Less or Equal : Branch to statement at label if $t1 is less than or equal to $t2 */ 
    public Instruction ble (Regs t1, Regs t2, String label) { return new NonMemoryInstruction("ble", "$"+t1, "$"+t2, ""+label); }
    /** Branch if Less or Equal : Branch to statement at label if $t1 is less than or equal to 16-bit immediate */ 
    public Instruction ble (Regs t1, int signed, String label) { return new NonMemoryInstruction("ble", "$"+t1, ""+signed, ""+label); }
    /** Branch if Less or Equal Unsigned : Branch to statement at label if $t1 is less than or equal to $t2 (unsigned compare) */ 
    public Instruction bleu (Regs t1, Regs t2, String label) { return new NonMemoryInstruction("bleu", "$"+t1, "$"+t2, ""+label); }
    /** Branch if Less or Equal Unsigned : Branch to statement at label if $t1 is less than or equal to 16-bit immediate (unsigned compare) */ 
    public Instruction bleu (Regs t1, int signed, String label) { return new NonMemoryInstruction("bleu", "$"+t1, ""+signed, ""+label); }
    /** Branch if Less Than : Branch to statement at label if $t1 is less than $t2 */ 
    public Instruction blt (Regs t1, Regs t2, String label) { return new NonMemoryInstruction("blt", "$"+t1, "$"+t2, ""+label); }
    /** Branch if Less Than : Branch to statement at label if $t1 is less than 16-bit immediate */ 
    public Instruction blt (Regs t1, int signed, String label) { return new NonMemoryInstruction("blt", "$"+t1, ""+signed, ""+label); }
    /** Branch if Less Than Unsigned : Branch to statement at label if $t1 is less than $t2 */ 
    public Instruction bltu (Regs t1, Regs t2, String label) { return new NonMemoryInstruction("bltu", "$"+t1, "$"+t2, ""+label); }
    /** Branch if Less Than Unsigned : Branch to statement at label if $t1 is less than 16-bit immediate      */ 
    public Instruction bltu (Regs t1, int signed, String label) { return new NonMemoryInstruction("bltu", "$"+t1, ""+signed, ""+label); }
    /** ROtate Left : Set $t1 to ($t2 rotated left by number of bit positions specified in $t3) */ 
    public Instruction rol (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("rol", "$"+t1, "$"+t2, "$"+t3); }
    /** ROtate Left : Set $t1 to ($t2 rotated left by number of bit positions specified in 5-bit immediate) */ 
    public Instruction rol (Regs t1, Regs t2, int numbits) { return new NonMemoryInstruction("rol", "$"+t1, "$"+t2, ""+numbits); }
    /** ROtate Right : Set $t1 to ($t2 rotated right by number of bit positions specified in $t3) */ 
    public Instruction ror (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("ror", "$"+t1, "$"+t2, "$"+t3); }
    /** ROtate Right : Set $t1 to ($t2 rotated right by number of bit positions specified in 5-bit immediate) */ 
    public Instruction ror (Regs t1, Regs t2, int numbits) { return new NonMemoryInstruction("ror", "$"+t1, "$"+t2, ""+numbits); }
    /** Move From Coprocessor 1 Double : Set $t1 to contents of $fpf2, set next higher register from $t1 to contents of next higher register from $fpf2 */ 
    public Instruction mfc1_d (Regs t1, FpRegs f2) { return new NonMemoryInstruction("mfc1.d", "$"+t1, "$"+f2); }
    /** Move To Coprocessor 1 Double : Set $fpf2 to contents of $t1, set next higher register from $fpf2 to contents of next higher register from $t1 */ 
    public Instruction mtc1_d (Regs t1, FpRegs f2) { return new NonMemoryInstruction("mtc1.d", "$"+t1, "$"+f2); }
    /** MULtiplication : Set HI to high-order 32 bits, LO and $t1 to low-order 32 bits of the product of $t2 and 16-bit signed immediate (use mfhi to access HI, mflo to access LO) */ 
    public Instruction mul (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("mul", "$"+t1, "$"+t2, ""+signed); }
    /** MULtiplication Unsigned : Set HI to high-order 32 bits, LO and $t1 to low-order 32 bits of ($t2 multiplied by $t3, unsigned multiplication) */ 
    public Instruction mulu (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("mulu", "$"+t1, "$"+t2, "$"+t3); }
    /** MULtiplication Unsigned :  Set HI to high-order 32 bits, LO and $t1 to low-order 32 bits of ($t2 multiplied by 16-bit immediate, unsigned multiplication) */ 
    public Instruction mulu (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("mulu", "$"+t1, "$"+t2, ""+signed); }
    /** MULtiplication with Overflow : Set $t1 to low-order 32 bits of the product of $t2 and $t3 */ 
    public Instruction mulo (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("mulo", "$"+t1, "$"+t2, "$"+t3); }
    /** MULtiplication with Overflow : Set $t1 to low-order 32 bits of the product of $t2 and signed 16-bit immediate */ 
    public Instruction mulo (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("mulo", "$"+t1, "$"+t2, ""+signed); }
    /** MULtiplication with Overflow Unsigned : Set $t1 to low-order 32 bits of the product of $t2 and $t3 */ 
    public Instruction mulou (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("mulou", "$"+t1, "$"+t2, "$"+t3); }
    /** MULtiplication with Overflow Unsigned : Set $t1 to low-order 32 bits of the product of $t2 and signed 16-bit immediate */ 
    public Instruction mulou (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("mulou", "$"+t1, "$"+t2, ""+signed); }
    /** DIVision : Set $t1 to ($t2 divided by $t3, integer division) */ 
    public Instruction div (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("div", "$"+t1, "$"+t2, "$"+t3); }
    /** DIVision : Set $t1 to ($t2 divided by 16-bit immediate, integer division) */ 
    public Instruction div (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("div", "$"+t1, "$"+t2, ""+signed); }
    /** DIVision Unsigned :  Set $t1 to ($t2 divided by $t3, unsigned integer division) */ 
    public Instruction divu (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("divu", "$"+t1, "$"+t2, "$"+t3); }
    /** DIVision Unsigned :  Set $t1 to ($t2 divided by 16-bit immediate, unsigned integer division) */ 
    public Instruction divu (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("divu", "$"+t1, "$"+t2, ""+signed); }
    /** REMainder : Set $t1 to (remainder of $t2 divided by $t3) */ 
    public Instruction rem (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("rem", "$"+t1, "$"+t2, "$"+t3); }
    /** REMainder : Set $t1 to (remainder of $t2 divided by 16-bit immediate) */ 
    public Instruction rem (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("rem", "$"+t1, "$"+t2, ""+signed); }
    /** REMainder : Set $t1 to (remainder of $t2 divided by $t3, unsigned division) */ 
    public Instruction remu (Regs t1, Regs t2, Regs t3) { return new NonMemoryInstruction("remu", "$"+t1, "$"+t2, "$"+t3); }
    /** REMainder : Set $t1 to (remainder of $t2 divided by 16-bit immediate, unsigned division) */ 
    public Instruction remu (Regs t1, Regs t2, int signed) { return new NonMemoryInstruction("remu", "$"+t1, "$"+t2, ""+signed); }
    /** Load Immediate : Set $t1 to 16-bit immediate (sign-extended) */ 
    public Instruction li (Regs t1, int signed) { return new NonMemoryInstruction("li", "$"+t1, ""+signed); }
    /** Load Address : Set $t1 to contents of $t2 */ 
    public Instruction la (Regs t1, Regs t2) { return new MemoryAccessInstruction("la", "$"+t1, "$"+t2); }
    /** Load Address : Set $t1 to 16-bit immediate (sign-extended)  */ 
    public Instruction la (Regs t1, int signed) { return new NonMemoryInstruction("la", "$"+t1, ""+signed); }
    /** Load Address : Set $t1 to sum (of $t2 and 16-bit immediate) */ 
    public Instruction la (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("la", "$"+t1, unsigned, "$"+t2); }
    /** Load Address : Set $t1 to label's address */ 
    public Instruction la (Regs t1, String label) { return new NonMemoryInstruction("la", "$"+t1, ""+label); }
    /** Load Address : Set $t1 to sum (of $t2 and label's address) */ 
    public Instruction la (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("la", "$"+t1, label, "$"+t2); }
    /** Load Address : Set $t1 to sum (of label's address and 32-bit immediate) */ 
    public Instruction la (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("la", "$"+t1, ""+label, ""+large_unsigned); }
    /** Load Address : Set $t1 to sum (of label's address, 32-bit immediate, and $t2) */ 
    public Instruction la (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("la", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Load Word : Set $t1 to contents of effective memory word address */ 
    public Instruction lw (Regs t1, Regs t2) { return new MemoryAccessInstruction("lw", "$"+t1, "$"+t2); }
    /** Load Word : Set $t1 to contents of effective memory word address */ 
    public Instruction lw (Regs t1, int signed) { return new NonMemoryInstruction("lw", "$"+t1, ""+signed); }
    /** Load Word : Set $t1 to contents of effective memory word address */ 
    public Instruction lw (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("lw", "$"+t1, unsigned, "$"+t2); }
    /** Load Word : Set $t1 to contents of memory word at label's address */ 
    public Instruction lw (Regs t1, String label) { return new NonMemoryInstruction("lw", "$"+t1, ""+label); }
    /** Load Word : Set $t1 to contents of effective memory word address */ 
    public Instruction lw (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("lw", "$"+t1, label, "$"+t2); }
    /** Load Word : Set $t1 to contents of effective memory word address  */ 
    public Instruction lw (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("lw", "$"+t1, ""+label, ""+large_unsigned); }
    /** Load Word : Set $t1 to contents of effective memory word address */ 
    public Instruction lw (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("lw", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Store Word : Store $t1 contents into effective memory word address */ 
    public Instruction sw (Regs t1, Regs t2) { return new MemoryAccessInstruction("sw", "$"+t1, "$"+t2); }
    /** Store Word : Store $t1 contents into effective memory word address */ 
    public Instruction sw (Regs t1, int signed) { return new NonMemoryInstruction("sw", "$"+t1, ""+signed); }
    /** Store Word : Store $t1 contents into effective memory word address */ 
    public Instruction sw (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("sw", "$"+t1, unsigned, "$"+t2); }
    /** Store Word : Store $t1 contents into memory word at label's address */ 
    public Instruction sw (Regs t1, String label) { return new NonMemoryInstruction("sw", "$"+t1, ""+label); }
    /** Store Word : Store $t1 contents into effective memory word address */ 
    public Instruction sw (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("sw", "$"+t1, label, "$"+t2); }
    /** Store Word : Store $t1 contents into effective memory word address */ 
    public Instruction sw (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("sw", "$"+t1, ""+label, ""+large_unsigned); }
    /** Store Word : Store $t1 contents into effective memory word address */ 
    public Instruction sw (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("sw", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address */ 
    public Instruction lh (Regs t1, Regs t2) { return new MemoryAccessInstruction("lh", "$"+t1, "$"+t2); }
    /** Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address */ 
    public Instruction lh (Regs t1, int signed) { return new NonMemoryInstruction("lh", "$"+t1, ""+signed); }
    /** Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address */ 
    public Instruction lh (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("lh", "$"+t1, unsigned, "$"+t2); }
    /** Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address */ 
    public Instruction lh (Regs t1, String label) { return new NonMemoryInstruction("lh", "$"+t1, ""+label); }
    /** Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address */ 
    public Instruction lh (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("lh", "$"+t1, label, "$"+t2); }
    /** Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address */ 
    public Instruction lh (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("lh", "$"+t1, ""+label, ""+large_unsigned); }
    /** Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address */ 
    public Instruction lh (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("lh", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address */ 
    public Instruction sh (Regs t1, Regs t2) { return new MemoryAccessInstruction("sh", "$"+t1, "$"+t2); }
    /** Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address */ 
    public Instruction sh (Regs t1, int signed) { return new NonMemoryInstruction("sh", "$"+t1, ""+signed); }
    /** Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address */ 
    public Instruction sh (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("sh", "$"+t1, unsigned, "$"+t2); }
    /** Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address */ 
    public Instruction sh (Regs t1, String label) { return new NonMemoryInstruction("sh", "$"+t1, ""+label); }
    /** Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address */ 
    public Instruction sh (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("sh", "$"+t1, label, "$"+t2); }
    /** Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address */ 
    public Instruction sh (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("sh", "$"+t1, ""+label, ""+large_unsigned); }
    /** Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address */ 
    public Instruction sh (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("sh", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address */ 
    public Instruction lb (Regs t1, Regs t2) { return new MemoryAccessInstruction("lb", "$"+t1, "$"+t2); }
    /** Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address */ 
    public Instruction lb (Regs t1, int signed) { return new NonMemoryInstruction("lb", "$"+t1, ""+signed); }
    /** Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address */ 
    public Instruction lb (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("lb", "$"+t1, unsigned, "$"+t2); }
    /** Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address */ 
    public Instruction lb (Regs t1, String label) { return new NonMemoryInstruction("lb", "$"+t1, ""+label); }
    /** Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address */ 
    public Instruction lb (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("lb", "$"+t1, label, "$"+t2); }
    /** Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address */ 
    public Instruction lb (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("lb", "$"+t1, ""+label, ""+large_unsigned); }
    /** Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address */ 
    public Instruction lb (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("lb", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address */ 
    public Instruction sb (Regs t1, Regs t2) { return new MemoryAccessInstruction("sb", "$"+t1, "$"+t2); }
    /** Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address */ 
    public Instruction sb (Regs t1, int signed) { return new NonMemoryInstruction("sb", "$"+t1, ""+signed); }
    /** Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address */ 
    public Instruction sb (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("sb", "$"+t1, unsigned, "$"+t2); }
    /** Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address */ 
    public Instruction sb (Regs t1, String label) { return new NonMemoryInstruction("sb", "$"+t1, ""+label); }
    /** Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address */ 
    public Instruction sb (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("sb", "$"+t1, label, "$"+t2); }
    /** Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address */ 
    public Instruction sb (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("sb", "$"+t1, ""+label, ""+large_unsigned); }
    /** Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address */ 
    public Instruction sb (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("sb", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address */ 
    public Instruction lhu (Regs t1, Regs t2) { return new MemoryAccessInstruction("lhu", "$"+t1, "$"+t2); }
    /** Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address */ 
    public Instruction lhu (Regs t1, int signed) { return new NonMemoryInstruction("lhu", "$"+t1, ""+signed); }
    /** Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address */ 
    public Instruction lhu (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("lhu", "$"+t1, unsigned, "$"+t2); }
    /** Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address */ 
    public Instruction lhu (Regs t1, String label) { return new NonMemoryInstruction("lhu", "$"+t1, ""+label); }
    /** Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address */ 
    public Instruction lhu (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("lhu", "$"+t1, label, "$"+t2); }
    /** Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address */ 
    public Instruction lhu (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("lhu", "$"+t1, ""+label, ""+large_unsigned); }
    /** Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address */ 
    public Instruction lhu (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("lhu", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address */ 
    public Instruction lbu (Regs t1, Regs t2) { return new MemoryAccessInstruction("lbu", "$"+t1, "$"+t2); }
    /** Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address */ 
    public Instruction lbu (Regs t1, int signed) { return new NonMemoryInstruction("lbu", "$"+t1, ""+signed); }
    /** Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address */ 
    public Instruction lbu (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("lbu", "$"+t1, unsigned, "$"+t2); }
    /** Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address */ 
    public Instruction lbu (Regs t1, String label) { return new NonMemoryInstruction("lbu", "$"+t1, ""+label); }
    /** Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address */ 
    public Instruction lbu (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("lbu", "$"+t1, label, "$"+t2); }
    /** Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address */ 
    public Instruction lbu (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("lbu", "$"+t1, ""+label, ""+large_unsigned); }
    /** Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address */ 
    public Instruction lbu (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("lbu", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction lwl (Regs t1, Regs t2) { return new MemoryAccessInstruction("lwl", "$"+t1, "$"+t2); }
    /** Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction lwl (Regs t1, int signed) { return new NonMemoryInstruction("lwl", "$"+t1, ""+signed); }
    /** Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction lwl (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("lwl", "$"+t1, unsigned, "$"+t2); }
    /** Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction lwl (Regs t1, String label) { return new NonMemoryInstruction("lwl", "$"+t1, ""+label); }
    /** Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction lwl (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("lwl", "$"+t1, label, "$"+t2); }
    /** Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction lwl (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("lwl", "$"+t1, ""+label, ""+large_unsigned); }
    /** Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction lwl (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("lwl", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction swl (Regs t1, Regs t2) { return new MemoryAccessInstruction("swl", "$"+t1, "$"+t2); }
    /** Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction swl (Regs t1, int signed) { return new NonMemoryInstruction("swl", "$"+t1, ""+signed); }
    /** Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction swl (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("swl", "$"+t1, unsigned, "$"+t2); }
    /** Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction swl (Regs t1, String label) { return new NonMemoryInstruction("swl", "$"+t1, ""+label); }
    /** Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction swl (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("swl", "$"+t1, label, "$"+t2); }
    /** Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction swl (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("swl", "$"+t1, ""+label, ""+large_unsigned); }
    /** Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction swl (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("swl", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word */ 
    public Instruction lwr (Regs t1, Regs t2) { return new MemoryAccessInstruction("lwr", "$"+t1, "$"+t2); }
    /** Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word */ 
    public Instruction lwr (Regs t1, int signed) { return new NonMemoryInstruction("lwr", "$"+t1, ""+signed); }
    /** Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word */ 
    public Instruction lwr (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("lwr", "$"+t1, unsigned, "$"+t2); }
    /** Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word */ 
    public Instruction lwr (Regs t1, String label) { return new NonMemoryInstruction("lwr", "$"+t1, ""+label); }
    /** Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word */ 
    public Instruction lwr (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("lwr", "$"+t1, label, "$"+t2); }
    /** Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word */ 
    public Instruction lwr (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("lwr", "$"+t1, ""+label, ""+large_unsigned); }
    /** Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word */ 
    public Instruction lwr (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("lwr", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address */ 
    public Instruction swr (Regs t1, Regs t2) { return new MemoryAccessInstruction("swr", "$"+t1, "$"+t2); }
    /** Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address */ 
    public Instruction swr (Regs t1, int signed) { return new NonMemoryInstruction("swr", "$"+t1, ""+signed); }
    /** Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address */ 
    public Instruction swr (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("swr", "$"+t1, unsigned, "$"+t2); }
    /** Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address */ 
    public Instruction swr (Regs t1, String label) { return new NonMemoryInstruction("swr", "$"+t1, ""+label); }
    /** Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address */ 
    public Instruction swr (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("swr", "$"+t1, label, "$"+t2); }
    /** Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address */ 
    public Instruction swr (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("swr", "$"+t1, ""+label, ""+large_unsigned); }
    /** Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address */ 
    public Instruction swr (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("swr", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors. */ 
    public Instruction ll (Regs t1, Regs t2) { return new MemoryAccessInstruction("ll", "$"+t1, "$"+t2); }
    /** Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors. */ 
    public Instruction ll (Regs t1, int signed) { return new NonMemoryInstruction("ll", "$"+t1, ""+signed); }
    /** Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors. */ 
    public Instruction ll (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("ll", "$"+t1, unsigned, "$"+t2); }
    /** Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors. */ 
    public Instruction ll (Regs t1, String label) { return new NonMemoryInstruction("ll", "$"+t1, ""+label); }
    /** Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors. */ 
    public Instruction ll (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("ll", "$"+t1, label, "$"+t2); }
    /** Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors. */ 
    public Instruction ll (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("ll", "$"+t1, ""+label, ""+large_unsigned); }
    /** Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors. */ 
    public Instruction ll (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("ll", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors. */ 
    public Instruction sc (Regs t1, Regs t2) { return new MemoryAccessInstruction("sc", "$"+t1, "$"+t2); }
    /** Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors. */ 
    public Instruction sc (Regs t1, int signed) { return new NonMemoryInstruction("sc", "$"+t1, ""+signed); }
    /** Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors. */ 
    public Instruction sc (Regs t1, int unsigned, Regs t2) { return new MemoryAccessInstruction("sc", "$"+t1, unsigned, "$"+t2); }
    /** Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors. */ 
    public Instruction sc (Regs t1, String label) { return new NonMemoryInstruction("sc", "$"+t1, ""+label); }
    /** Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors. */ 
    public Instruction sc (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("sc", "$"+t1, label, "$"+t2); }
    /** Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors. */ 
    public Instruction sc (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("sc", "$"+t1, ""+label, ""+large_unsigned); }
    /** Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors. */ 
    public Instruction sc (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("sc", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address */ 
    public Instruction ulw (Regs t1, int signed, Regs t2) { return new MemoryAccessInstruction("ulw", "$"+t1, signed, "$"+t2); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address */ 
    public Instruction ulh (Regs t1, int signed, Regs t2) { return new MemoryAccessInstruction("ulh", "$"+t1, signed, "$"+t2); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address */ 
    public Instruction ulhu (Regs t1, int signed, Regs t2) { return new MemoryAccessInstruction("ulhu", "$"+t1, signed, "$"+t2); }
    /** Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory byte address */ 
    public Instruction ld (Regs t1, int signed, Regs t2) { return new MemoryAccessInstruction("ld", "$"+t1, signed, "$"+t2); }
    /** Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address */ 
    public Instruction usw (Regs t1, int signed, Regs t2) { return new MemoryAccessInstruction("usw", "$"+t1, signed, "$"+t2); }
    /** Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address */ 
    public Instruction ush (Regs t1, int signed, Regs t2) { return new MemoryAccessInstruction("ush", "$"+t1, signed, "$"+t2); }
    /** Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory byte address */ 
    public Instruction sd (Regs t1, int signed, Regs t2) { return new MemoryAccessInstruction("sd", "$"+t1, signed, "$"+t2); }
    /** Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address */ 
    public Instruction ulw (Regs t1, String label) { return new NonMemoryInstruction("ulw", "$"+t1, ""+label); }
    /** Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address */ 
    public Instruction ulw (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("ulw", "$"+t1, ""+label, ""+large_unsigned); }
    /** Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address */ 
    public Instruction ulw (Regs t1, Regs t2) { return new MemoryAccessInstruction("ulw", "$"+t1, "$"+t2); }
    /** Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address */ 
    public Instruction ulw (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("ulw", "$"+t1, label, "$"+t2); }
    /** Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address */ 
    public Instruction ulw (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("ulw", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address */ 
    public Instruction ulh (Regs t1, String label) { return new NonMemoryInstruction("ulh", "$"+t1, ""+label); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address */ 
    public Instruction ulh (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("ulh", "$"+t1, ""+label, ""+large_unsigned); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address */ 
    public Instruction ulh (Regs t1, Regs t2) { return new MemoryAccessInstruction("ulh", "$"+t1, "$"+t2); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address */ 
    public Instruction ulh (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("ulh", "$"+t1, label, "$"+t2); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address */ 
    public Instruction ulh (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("ulh", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address */ 
    public Instruction ulhu (Regs t1, String label) { return new NonMemoryInstruction("ulhu", "$"+t1, ""+label); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address */ 
    public Instruction ulhu (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("ulhu", "$"+t1, ""+label, ""+large_unsigned); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address */ 
    public Instruction ulhu (Regs t1, Regs t2) { return new MemoryAccessInstruction("ulhu", "$"+t1, "$"+t2); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address */ 
    public Instruction ulhu (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("ulhu", "$"+t1, label, "$"+t2); }
    /** Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address */ 
    public Instruction ulhu (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("ulhu", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction ld (Regs t1, int large_unsigned) { return new NonMemoryInstruction("ld", "$"+t1, ""+large_unsigned); }
    /** Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction ld (Regs t1, String label) { return new NonMemoryInstruction("ld", "$"+t1, ""+label); }
    /** Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction ld (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("ld", "$"+t1, ""+label, ""+large_unsigned); }
    /** Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction ld (Regs t1, Regs t2) { return new MemoryAccessInstruction("ld", "$"+t1, "$"+t2); }
    /** Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction ld (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("ld", "$"+t1, label, "$"+t2); }
    /** Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction ld (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("ld", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address */ 
    public Instruction usw (Regs t1, int large_unsigned) { return new NonMemoryInstruction("usw", "$"+t1, ""+large_unsigned); }
    /** Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address */ 
    public Instruction usw (Regs t1, String label) { return new NonMemoryInstruction("usw", "$"+t1, ""+label); }
    /** Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address */ 
    public Instruction usw (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("usw", "$"+t1, ""+label, ""+large_unsigned); }
    /** Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address */ 
    public Instruction usw (Regs t1, Regs t2) { return new MemoryAccessInstruction("usw", "$"+t1, "$"+t2); }
    /** Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address */ 
    public Instruction usw (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("usw", "$"+t1, label, "$"+t2); }
    /** Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address */ 
    public Instruction usw (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("usw", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address */ 
    public Instruction ush (Regs t1, int large_unsigned) { return new NonMemoryInstruction("ush", "$"+t1, ""+large_unsigned); }
    /** Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address */ 
    public Instruction ush (Regs t1, String label) { return new NonMemoryInstruction("ush", "$"+t1, ""+label); }
    /** Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address */ 
    public Instruction ush (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("ush", "$"+t1, ""+label, ""+large_unsigned); }
    /** Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address */ 
    public Instruction ush (Regs t1, Regs t2) { return new MemoryAccessInstruction("ush", "$"+t1, "$"+t2); }
    /** Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address */ 
    public Instruction ush (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("ush", "$"+t1, label, "$"+t2); }
    /** Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address */ 
    public Instruction ush (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("ush", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction sd (Regs t1, int large_unsigned) { return new NonMemoryInstruction("sd", "$"+t1, ""+large_unsigned); }
    /** Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction sd (Regs t1, String label) { return new NonMemoryInstruction("sd", "$"+t1, ""+label); }
    /** Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction sd (Regs t1, String label, int large_unsigned) { return new NonMemoryInstruction("sd", "$"+t1, ""+label, ""+large_unsigned); }
    /** Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction sd (Regs t1, Regs t2) { return new MemoryAccessInstruction("sd", "$"+t1, "$"+t2); }
    /** Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction sd (Regs t1, String label, Regs t2) { return new MemoryAccessInstruction("sd", "$"+t1, label, "$"+t2); }
    /** Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address */ 
    public Instruction sd (Regs t1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("sd", "$"+t1, label, large_unsigned, "$"+t2); }
    /** Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address */ 
    public Instruction lwc1 (FpRegs f1, Regs t2) { return new MemoryAccessInstruction("lwc1", "$"+f1, "$"+t2); }
    /** Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address */ 
    public Instruction lwc1 (FpRegs f1, int signed) { return new NonMemoryInstruction("lwc1", "$"+f1, ""+signed); }
    /** Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address */ 
    public Instruction lwc1 (FpRegs f1, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("lwc1", "$"+f1, large_unsigned, "$"+t2); }
    /** Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address */ 
    public Instruction lwc1 (FpRegs f1, String label) { return new NonMemoryInstruction("lwc1", "$"+f1, ""+label); }
    /** Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address */ 
    public Instruction lwc1 (FpRegs f1, String label, Regs t2) { return new MemoryAccessInstruction("lwc1", "$"+f1, label, "$"+t2); }
    /** Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address */ 
    public Instruction lwc1 (FpRegs f1, String label, int large_unsigned) { return new NonMemoryInstruction("lwc1", "$"+f1, ""+label, ""+large_unsigned); }
    /** Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address */ 
    public Instruction lwc1 (FpRegs f1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("lwc1", "$"+f1, label, large_unsigned, "$"+t2); }
    /** Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction ldc1 (FpRegs f2, Regs t2) { return new MemoryAccessInstruction("ldc1", "$"+f2, "$"+t2); }
    /** Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction ldc1 (FpRegs f2, int signed) { return new NonMemoryInstruction("ldc1", "$"+f2, ""+signed); }
    /** Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction ldc1 (FpRegs f2, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("ldc1", "$"+f2, large_unsigned, "$"+t2); }
    /** Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction ldc1 (FpRegs f2, String label) { return new NonMemoryInstruction("ldc1", "$"+f2, ""+label); }
    /** Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction ldc1 (FpRegs f2, String label, Regs t2) { return new MemoryAccessInstruction("ldc1", "$"+f2, label, "$"+t2); }
    /** Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction ldc1 (FpRegs f2, String label, int large_unsigned) { return new NonMemoryInstruction("ldc1", "$"+f2, ""+label, ""+large_unsigned); }
    /** Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction ldc1 (FpRegs f2, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("ldc1", "$"+f2, label, large_unsigned, "$"+t2); }
    /** Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction swc1 (FpRegs f1, Regs t2) { return new MemoryAccessInstruction("swc1", "$"+f1, "$"+t2); }
    /** Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction swc1 (FpRegs f1, int signed) { return new NonMemoryInstruction("swc1", "$"+f1, ""+signed); }
    /** Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction swc1 (FpRegs f1, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("swc1", "$"+f1, large_unsigned, "$"+t2); }
    /** Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction swc1 (FpRegs f1, String label) { return new NonMemoryInstruction("swc1", "$"+f1, ""+label); }
    /** Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction swc1 (FpRegs f1, String label, Regs t2) { return new MemoryAccessInstruction("swc1", "$"+f1, label, "$"+t2); }
    /** Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction swc1 (FpRegs f1, String label, int large_unsigned) { return new NonMemoryInstruction("swc1", "$"+f1, ""+label, ""+large_unsigned); }
    /** Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction swc1 (FpRegs f1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("swc1", "$"+f1, label, large_unsigned, "$"+t2); }
    /** Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction sdc1 (FpRegs f2, Regs t2) { return new MemoryAccessInstruction("sdc1", "$"+f2, "$"+t2); }
    /** Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction sdc1 (FpRegs f2, int signed) { return new NonMemoryInstruction("sdc1", "$"+f2, ""+signed); }
    /** Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction sdc1 (FpRegs f2, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("sdc1", "$"+f2, large_unsigned, "$"+t2); }
    /** Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction sdc1 (FpRegs f2, String label) { return new NonMemoryInstruction("sdc1", "$"+f2, ""+label); }
    /** Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction sdc1 (FpRegs f2, String label, Regs t2) { return new MemoryAccessInstruction("sdc1", "$"+f2, label, "$"+t2); }
    /** Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction sdc1 (FpRegs f2, String label, int large_unsigned) { return new NonMemoryInstruction("sdc1", "$"+f2, ""+label, ""+large_unsigned); }
    /** Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction sdc1 (FpRegs f2, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("sdc1", "$"+f2, label, large_unsigned, "$"+t2); }
    /** Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address */ 
    public Instruction l_s (FpRegs f1, Regs t2) { return new MemoryAccessInstruction("l.s", "$"+f1, "$"+t2); }
    /** Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address */ 
    public Instruction l_s (FpRegs f1, int signed) { return new NonMemoryInstruction("l.s", "$"+f1, ""+signed); }
    /** Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address */ 
    public Instruction l_s (FpRegs f1, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("l.s", "$"+f1, large_unsigned, "$"+t2); }
    /** Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address */ 
    public Instruction l_s (FpRegs f1, String label) { return new NonMemoryInstruction("l.s", "$"+f1, ""+label); }
    /** Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address */ 
    public Instruction l_s (FpRegs f1, String label, Regs t2) { return new MemoryAccessInstruction("l.s", "$"+f1, label, "$"+t2); }
    /** Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address */ 
    public Instruction l_s (FpRegs f1, String label, int large_unsigned) { return new NonMemoryInstruction("l.s", "$"+f1, ""+label, ""+large_unsigned); }
    /** Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address */ 
    public Instruction l_s (FpRegs f1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("l.s", "$"+f1, label, large_unsigned, "$"+t2); }
    /** Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction s_s (FpRegs f1, Regs t2) { return new MemoryAccessInstruction("s.s", "$"+f1, "$"+t2); }
    /** Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction s_s (FpRegs f1, int signed) { return new NonMemoryInstruction("s.s", "$"+f1, ""+signed); }
    /** Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction s_s (FpRegs f1, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("s.s", "$"+f1, large_unsigned, "$"+t2); }
    /** Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction s_s (FpRegs f1, String label) { return new NonMemoryInstruction("s.s", "$"+f1, ""+label); }
    /** Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction s_s (FpRegs f1, String label, Regs t2) { return new MemoryAccessInstruction("s.s", "$"+f1, label, "$"+t2); }
    /** Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction s_s (FpRegs f1, String label, int large_unsigned) { return new NonMemoryInstruction("s.s", "$"+f1, ""+label, ""+large_unsigned); }
    /** Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address */ 
    public Instruction s_s (FpRegs f1, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("s.s", "$"+f1, label, large_unsigned, "$"+t2); }
    /** Load <dbl> into floating point register $fpf2 */ 
    public Instruction li_d (FpRegs f2, double dbl) { return new NonMemoryInstruction("li.d", "$"+f2, ""+dbl); }
    /** Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction l_d (FpRegs f2, Regs t2) { return new MemoryAccessInstruction("l.d", "$"+f2, "$"+t2); }
    /** Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction l_d (FpRegs f2, int signed) { return new NonMemoryInstruction("l.d", "$"+f2, ""+signed); }
    /** Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction l_d (FpRegs f2, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("l.d", "$"+f2, large_unsigned, "$"+t2); }
    /** Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction l_d (FpRegs f2, String label) { return new NonMemoryInstruction("l.d", "$"+f2, ""+label); }
    /** Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction l_d (FpRegs f2, String label, Regs t2) { return new MemoryAccessInstruction("l.d", "$"+f2, label, "$"+t2); }
    /** Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction l_d (FpRegs f2, String label, int large_unsigned) { return new NonMemoryInstruction("l.d", "$"+f2, ""+label, ""+large_unsigned); }
    /** Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address */ 
    public Instruction l_d (FpRegs f2, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("l.d", "$"+f2, label, large_unsigned, "$"+t2); }
    /** Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction s_d (FpRegs f2, Regs t2) { return new MemoryAccessInstruction("s.d", "$"+f2, "$"+t2); }
    /** Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction s_d (FpRegs f2, int signed) { return new NonMemoryInstruction("s.d", "$"+f2, ""+signed); }
    /** Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction s_d (FpRegs f2, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("s.d", "$"+f2, large_unsigned, "$"+t2); }
    /** Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction s_d (FpRegs f2, String label) { return new NonMemoryInstruction("s.d", "$"+f2, ""+label); }
    /** Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction s_d (FpRegs f2, String label, Regs t2) { return new MemoryAccessInstruction("s.d", "$"+f2, label, "$"+t2); }
    /** Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction s_d (FpRegs f2, String label, int large_unsigned) { return new NonMemoryInstruction("s.d", "$"+f2, ""+label, ""+large_unsigned); }
    /** Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address */ 
    public Instruction s_d (FpRegs f2, String label, int large_unsigned, Regs t2) { return new MemoryAccessInstruction("s.d", "$"+f2, label, large_unsigned, "$"+t2); }
}
