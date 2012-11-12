package cs536.codegen.mips;

import cs536.codegen.Instruction;
import cs536.codegen.mips.NonMemoryInstruction;

public class AutogenMips
{
    /** No operation */ 
    public Instruction nop () { return new NonMemoryInstruction("nop"); }
    /** Addition with overflow : set $dest to ($left plus $right) */ 
    public Instruction add (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("add", "$"+dest, "$"+left, "$"+right); }
    /** Subtraction with overflow : set $dest to ($left minus $right) */ 
    public Instruction sub (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("sub", "$"+dest, "$"+left, "$"+right); }
    /** Addition immediate with overflow : set $dest to ($left plus signed 16-bit immediate) */ 
    public Instruction addi (Regs dest, Regs left, int signed) { return new NonMemoryInstruction("addi", "$"+dest, "$"+left, ""+signed); }
    /** Addition unsigned without overflow : set $dest to ($left plus $right), no overflow */ 
    public Instruction addu (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("addu", "$"+dest, "$"+left, "$"+right); }
    /** Subtraction unsigned without overflow : set $dest to ($left minus $right), no overflow */ 
    public Instruction subu (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("subu", "$"+dest, "$"+left, "$"+right); }
    /** Addition immediate unsigned without overflow : set $dest to ($left plus signed 16-bit immediate), no overflow */ 
    public Instruction addiu (Regs dest, Regs left, int signed) { return new NonMemoryInstruction("addiu", "$"+dest, "$"+left, ""+signed); }
    /** Multiplication : Set hi to high-order 32 bits, lo to low-order 32 bits of the product of $dest and $left (use mfhi to access hi, mflo to access lo) */ 
    public Instruction mult (Regs dest, Regs left) { return new NonMemoryInstruction("mult", "$"+dest, "$"+left); }
    /** Multiplication unsigned : Set HI to high-order 32 bits, LO to low-order 32 bits of the product of unsigned $dest and $left (use mfhi to access HI, mflo to access LO) */ 
    public Instruction multu (Regs dest, Regs left) { return new NonMemoryInstruction("multu", "$"+dest, "$"+left); }
    /** Multiplication without overflow  : Set HI to high-order 32 bits, LO and $dest to low-order 32 bits of the product of $dest and $left (use mfhi to access HI, mflo to access LO) */ 
    public Instruction mul (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("mul", "$"+dest, "$"+left, "$"+right); }
    /** Multiply add : Multiply $dest by $left then increment HI by high-order 32 bits of product, increment LO by low-order 32 bits of product (use mfhi to access HI, mflo to access LO) */ 
    public Instruction madd (Regs dest, Regs left) { return new NonMemoryInstruction("madd", "$"+dest, "$"+left); }
    /** Multiply add unsigned : Multiply $dest by $left then increment HI by high-order 32 bits of product, increment LO by low-order 32 bits of product, unsigned (use mfhi to access HI, mflo to access LO) */ 
    public Instruction maddu (Regs dest, Regs left) { return new NonMemoryInstruction("maddu", "$"+dest, "$"+left); }
    /** Multiply subtract : Multiply $dest by $left then decrement HI by high-order 32 bits of product, decrement LO by low-order 32 bits of product (use mfhi to access HI, mflo to access LO) */ 
    public Instruction msub (Regs dest, Regs left) { return new NonMemoryInstruction("msub", "$"+dest, "$"+left); }
    /** Multiply subtract unsigned : Multiply $dest by $left then decrement HI by high-order 32 bits of product, decement LO by low-order 32 bits of product, unsigned (use mfhi to access HI, mflo to access LO) */ 
    public Instruction msubu (Regs dest, Regs left) { return new NonMemoryInstruction("msubu", "$"+dest, "$"+left); }
    /** Division with overflow : Divide $dest by $left then set LO to quotient and HI to remainder (use mfhi to access HI, mflo to access LO) */ 
    public Instruction div (Regs dest, Regs left) { return new NonMemoryInstruction("div", "$"+dest, "$"+left); }
    /** Division unsigned without overflow : Divide unsigned $dest by $left then set LO to quotient and HI to remainder (use mfhi to access HI, mflo to access LO) */ 
    public Instruction divu (Regs dest, Regs left) { return new NonMemoryInstruction("divu", "$"+dest, "$"+left); }
    /** Move from HI register : Set $dest to contents of HI (see multiply and divide operations) */ 
    public Instruction mfhi (Regs dest) { return new NonMemoryInstruction("mfhi", "$"+dest); }
    /** Move from LO register : Set $dest to contents of LO (see multiply and divide operations) */ 
    public Instruction mflo (Regs dest) { return new NonMemoryInstruction("mflo", "$"+dest); }
    /** Move to HI registerr : Set HI to contents of $dest (see multiply and divide operations) */ 
    public Instruction mthi (Regs dest) { return new NonMemoryInstruction("mthi", "$"+dest); }
    /** Move to LO register : Set LO to contents of $dest (see multiply and divide operations) */ 
    public Instruction mtlo (Regs dest) { return new NonMemoryInstruction("mtlo", "$"+dest); }
    /** Bitwise AND : Set $dest to bitwise AND of $left and $right */ 
    public Instruction and (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("and", "$"+dest, "$"+left, "$"+right); }
    /** Bitwise OR : Set $dest to bitwise OR of $left and $right */ 
    public Instruction or (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("or", "$"+dest, "$"+left, "$"+right); }
    /** Bitwise AND immediate : Set $dest to bitwise AND of $left and zero-extended 16-bit immediate */ 
    public Instruction andi (Regs dest, Regs left, int unsigned) { return new NonMemoryInstruction("andi", "$"+dest, "$"+left, ""+unsigned); }
    /** Bitwise OR immediate : Set $dest to bitwise OR of $left and zero-extended 16-bit immediate */ 
    public Instruction ori (Regs dest, Regs left, int unsigned) { return new NonMemoryInstruction("ori", "$"+dest, "$"+left, ""+unsigned); }
    /** Bitwise NOR : Set $dest to bitwise NOR of $left and $right */ 
    public Instruction nor (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("nor", "$"+dest, "$"+left, "$"+right); }
    /** Bitwise XOR (exclusive OR) : Set $dest to bitwise XOR of $left and $right */ 
    public Instruction xor (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("xor", "$"+dest, "$"+left, "$"+right); }
    /** Bitwise XOR immediate : Set $dest to bitwise XOR of $left and zero-extended 16-bit immediate */ 
    public Instruction xori (Regs dest, Regs left, int unsigned) { return new NonMemoryInstruction("xori", "$"+dest, "$"+left, ""+unsigned); }
    /** Shift left logical : Set $dest to result of shifting $left left by number of bits specified by immediate */ 
    public Instruction sll (Regs dest, Regs left, int numbits) { return new NonMemoryInstruction("sll", "$"+dest, "$"+left, ""+numbits); }
    /** Shift left logical variable : Set $dest to result of shifting $left left by number of bits specified by value in low-order 5 bits of $right */ 
    public Instruction sllv (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("sllv", "$"+dest, "$"+left, "$"+right); }
    /** Shift right logical : Set $dest to result of shifting $left right by number of bits specified by immediate */ 
    public Instruction srl (Regs dest, Regs left, int numbits) { return new NonMemoryInstruction("srl", "$"+dest, "$"+left, ""+numbits); }
    /** Shift right arithmetic : Set $dest to result of sign-extended shifting $left right by number of bits specified by immediate */ 
    public Instruction sra (Regs dest, Regs left, int numbits) { return new NonMemoryInstruction("sra", "$"+dest, "$"+left, ""+numbits); }
    /** Shift right arithmetic variable : Set $dest to result of sign-extended shifting $left right by number of bits specified by value in low-order 5 bits of $right */ 
    public Instruction srav (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("srav", "$"+dest, "$"+left, "$"+right); }
    /** Shift right logical variable : Set $dest to result of shifting $left right by number of bits specified by value in low-order 5 bits of $right */ 
    public Instruction srlv (Regs dest, Regs left, Regs right) { return new NonMemoryInstruction("srlv", "$"+dest, "$"+left, "$"+right); }
    /** Load upper immediate : Set high-order 16 bits of $dest to 16-bit immediate and low-order 16 bits to 0 */ 
    public Instruction lui (Regs dest, int unsigned) { return new NonMemoryInstruction("lui", "$"+dest, ""+unsigned); }
    /** Load word : Set $dest to contents of effective memory word address */ 
    public Instruction lw (Regs dest, int offset, Regs base) { return new MemoryAccessInstruction("lw", "$"+dest, offset, "$"+base); }
    /** Load linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors. */ 
    public Instruction ll (Regs dest, int offset, Regs base) { return new MemoryAccessInstruction("ll", "$"+dest, offset, "$"+base); }
    /** Load word left : Load from 1 to 4 bytes left-justified into $dest, starting with effective memory byte address and continuing through the low-order byte of its word */ 
    public Instruction lwl (Regs dest, int offset, Regs base) { return new MemoryAccessInstruction("lwl", "$"+dest, offset, "$"+base); }
    /** Load word right : Load from 1 to 4 bytes right-justified into $dest, starting with effective memory byte address and continuing through the high-order byte of its word */ 
    public Instruction lwr (Regs dest, int offset, Regs base) { return new MemoryAccessInstruction("lwr", "$"+dest, offset, "$"+base); }
    /** Store word : Store contents of $src into effective memory word address */ 
    public Instruction sw (Regs src, int offset, Regs base) { return new MemoryAccessInstruction("sw", "$"+src, offset, "$"+base); }
    /** Store conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Stores $src value into effective address, then sets $src to 1 for success.  Always succeeds because MARS does not simulate multiple processors. */ 
    public Instruction sc (Regs src, int offset, Regs base) { return new MemoryAccessInstruction("sc", "$"+src, offset, "$"+base); }
    /** Store word left : Store high-order 1 to 4 bytes of $src into memory, starting with effective byte address and continuing through the low-order byte of its word */ 
    public Instruction swl (Regs src, int offset, Regs base) { return new MemoryAccessInstruction("swl", "$"+src, offset, "$"+base); }
    /** Store word right : Store low-order 1 to 4 bytes of $src into memory, starting with high-order byte of word containing effective byte address and continuing through that byte address */ 
    public Instruction swr (Regs src, int offset, Regs base) { return new MemoryAccessInstruction("swr", "$"+src, offset, "$"+base); }
    /** Load byte : Set $dest to sign-extended 8-bit value from effective memory byte address */ 
    public Instruction lb (Regs dest, int offset, Regs base) { return new MemoryAccessInstruction("lb", "$"+dest, offset, "$"+base); }
    /** Load halfword : Set $dest to sign-extended 16-bit value from effective memory halfword address */ 
    public Instruction lh (Regs dest, int offset, Regs base) { return new MemoryAccessInstruction("lh", "$"+dest, offset, "$"+base); }
    /** Load halfword unsigned : Set $dest to zero-extended 16-bit value from effective memory halfword address */ 
    public Instruction lhu (Regs dest, int offset, Regs base) { return new MemoryAccessInstruction("lhu", "$"+dest, offset, "$"+base); }
    /** Load byte unsigned : Set $dest to zero-extended 8-bit value from effective memory byte address */ 
    public Instruction lbu (Regs dest, int offset, Regs base) { return new MemoryAccessInstruction("lbu", "$"+dest, offset, "$"+base); }
    /** Store byte : Store the low-order 8 bits of $src into the effective memory byte address */ 
    public Instruction sb (Regs src, int offset, Regs base) { return new MemoryAccessInstruction("sb", "$"+src, offset, "$"+base); }
    /** Store halfword : Store the low-order 16 bits of $src into the effective memory halfword address */ 
    public Instruction sh (Regs src, int offset, Regs base) { return new MemoryAccessInstruction("sh", "$"+src, offset, "$"+base); }
    /** Branch if equal : Branch to statement at <label>'s address if $left and $t2 are equal */ 
    public Instruction beq (Regs left, Regs t2, String label) { return new NonMemoryInstruction("beq", "$"+left, "$"+t2, ""+label); }
    /** Branch if not equal : Branch to statement at <label>'s address if $left and $t2 are not equal */ 
    public Instruction bne (Regs left, Regs t2, String label) { return new NonMemoryInstruction("bne", "$"+left, "$"+t2, ""+label); }
    /** Branch if greater than or equal to zero : Branch to statement at <label>'s address if $src is greater than or equal to zero */ 
    public Instruction bgez (Regs src, String label) { return new NonMemoryInstruction("bgez", "$"+src, ""+label); }
    /** Branch if greater then or equal to zero and link : If $src is greater than or equal to zero, then set $ra to the Program Counter and branch to statement at <label>'s address */ 
    public Instruction bgezal (Regs src, String label) { return new NonMemoryInstruction("bgezal", "$"+src, ""+label); }
    /** Branch if greater than zero : Branch to statement at <label>'s address if $src is greater than zero */ 
    public Instruction bgtz (Regs src, String label) { return new NonMemoryInstruction("bgtz", "$"+src, ""+label); }
    /** Branch if less than or equal to zero : Branch to statement at <label>'s address if $src is less than or equal to zero */ 
    public Instruction blez (Regs src, String label) { return new NonMemoryInstruction("blez", "$"+src, ""+label); }
    /** Branch if less than zero : Branch to statement at <label>'s address if $src is less than zero */ 
    public Instruction bltz (Regs src, String label) { return new NonMemoryInstruction("bltz", "$"+src, ""+label); }
    /** Branch if less than zero and link : If $src is less than or equal to zero, then set $ra to the Program Counter and branch to statement at <label>'s address */ 
    public Instruction bltzal (Regs src, String label) { return new NonMemoryInstruction("bltzal", "$"+src, ""+label); }
    /** Set less than : If $t2 is less than $t3, then set $dest to 1 else set $dest to 0 */ 
    public Instruction slt (Regs dest, Regs t2, Regs t3) { return new NonMemoryInstruction("slt", "$"+dest, "$"+t2, "$"+t3); }
    /** Set less than unsigned : If $t2 is less than $t3 using unsigned comparision, then set $dest to 1 else set $dest to 0 */ 
    public Instruction sltu (Regs dest, Regs t2, Regs t3) { return new NonMemoryInstruction("sltu", "$"+dest, "$"+t2, "$"+t3); }
    /** Set less than immediate : If $t2 is less than sign-extended 16-bit immediate, then set $dest to 1 else set $dest to 0 */ 
    public Instruction slti (Regs dest, Regs t2, int signed) { return new NonMemoryInstruction("slti", "$"+dest, "$"+t2, ""+signed); }
    /** Set less than immediate unsigned : If $t2 is less than  sign-extended 16-bit immediate using unsigned comparison, then set $dest to 1 else set $dest to 0 */ 
    public Instruction sltiu (Regs dest, Regs t2, int signed) { return new NonMemoryInstruction("sltiu", "$"+dest, "$"+t2, ""+signed); }
    /** Move conditional not zero : Set $dest to $t2 if $t3 is not zero */ 
    public Instruction movn (Regs dest, Regs t2, Regs t3) { return new NonMemoryInstruction("movn", "$"+dest, "$"+t2, "$"+t3); }
    /** Move conditional zero : Set $dest to $t2 if $t3 is zero */ 
    public Instruction movz (Regs dest, Regs t2, Regs t3) { return new NonMemoryInstruction("movz", "$"+dest, "$"+t2, "$"+t3); }
    /** Move if FP condition flag 0 false : Set $dest to $t2 if FPU (Coprocessor 1) condition flag 0 is false (zero) */ 
    public Instruction movf (Regs dest, Regs t2) { return new NonMemoryInstruction("movf", "$"+dest, "$"+t2); }
    /** Move if FP condition flag 0 true : Set $dest to $t2 if FPU (Coprocessor 1) condition flag 0 is true (one) */ 
    public Instruction movt (Regs dest, Regs t2) { return new NonMemoryInstruction("movt", "$"+dest, "$"+t2); }
    /** Issue a system call : Execute the system call specified by value in $v0 */ 
    public Instruction syscall () { return new NonMemoryInstruction("syscall"); }
    /** Jump unconditionally : Jump to statement at <label> address */ 
    public Instruction j (String label) { return new NonMemoryInstruction("j", ""+label); }
    /** Jump register unconditionally : Jump to statement whose address is in $src */ 
    public Instruction jr (Regs src) { return new NonMemoryInstruction("jr", "$"+src); }
    /** Jump and link : Set $ra to Program Counter (return address) then jump to statement at <label> address */ 
    public Instruction jal (String label) { return new NonMemoryInstruction("jal", ""+label); }
    /** Jump and link register : Set $dest to Program Counter (return address) then jump to statement whose address is in $src */ 
    public Instruction jalr (Regs dest, Regs src) { return new NonMemoryInstruction("jalr", "$"+dest, "$"+src); }
    /** Jump and link register : Set $ra to Program Counter (return address) then jump to statement whose address is in $src */ 
    public Instruction jalr (Regs src) { return new NonMemoryInstruction("jalr", "$"+src); }
    /** Count number of leading ones : Set $dest to the count of leading one bits in $src starting at most significant bit position */ 
    public Instruction clo (Regs dest, Regs src) { return new NonMemoryInstruction("clo", "$"+dest, "$"+src); }
    /** Count number of leading zeroes : Set $dest to the count of leading zero bits in $src starting at most significant bit positio */ 
    public Instruction clz (Regs dest, Regs src) { return new NonMemoryInstruction("clz", "$"+dest, "$"+src); }
    /** Floating point addition single precision : Set $fpdest to single-precision floating point value of $fpleft plus $fpright */ 
    public Instruction add_s (FpRegs dest, FpRegs left, FpRegs right) { return new NonMemoryInstruction("add.s", "$"+dest, "$"+left, "$"+right); }
    /** Floating point subtraction single precision : Set $fpdest to single-precision floating point value of $fpleft  minus $fpright */ 
    public Instruction sub_s (FpRegs dest, FpRegs left, FpRegs right) { return new NonMemoryInstruction("sub.s", "$"+dest, "$"+left, "$"+right); }
    /** Floating point multiplication single precision : Set $fpdest to single-precision floating point value of $fpleft times $fpright */ 
    public Instruction mul_s (FpRegs dest, FpRegs left, FpRegs right) { return new NonMemoryInstruction("mul.s", "$"+dest, "$"+left, "$"+right); }
    /** Floating point division single precision : Set $fpdest to single-precision floating point value of $fpleft divided by $fpright */ 
    public Instruction div_s (FpRegs dest, FpRegs left, FpRegs right) { return new NonMemoryInstruction("div.s", "$"+dest, "$"+left, "$"+right); }
    /** Square root single precision : Set $fpdest to single-precision floating point square root of $fpsrc */ 
    public Instruction sqrt_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("sqrt.s", "$"+dest, "$"+src); }
    /** Floor single precision to word : Set $fpdest to 32-bit integer floor of single-precision float in $fpsrc */ 
    public Instruction floor_w_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("floor.w.s", "$"+dest, "$"+src); }
    /** Ceiling single precision to word : Set $fpdest to 32-bit integer ceiling of single-precision float in $fpsrc */ 
    public Instruction ceil_w_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("ceil.w.s", "$"+dest, "$"+src); }
    /** Round single precision to word : Set $fpdest to 32-bit integer round of single-precision float in $fpsrc */ 
    public Instruction round_w_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("round.w.s", "$"+dest, "$"+src); }
    /** Truncate single precision to word : Set $fpdest to 32-bit integer truncation of single-precision float in $fpsrc */ 
    public Instruction trunc_w_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("trunc.w.s", "$"+dest, "$"+src); }
    /** Floating point addition double precision : Set $fpdest to double-precision floating point value of $fpleft plus $fpright */ 
    public Instruction add_d (FpRegs dest, FpRegs left, FpRegs right) { return new NonMemoryInstruction("add.d", "$"+dest, "$"+left, "$"+right); }
    /** Floating point subtraction double precision : Set $fpdest to double-precision floating point value of $fpleft minus $fpright */ 
    public Instruction sub_d (FpRegs dest, FpRegs left, FpRegs right) { return new NonMemoryInstruction("sub.d", "$"+dest, "$"+left, "$"+right); }
    /** Floating point multiplication double precision : Set $fpdest to double-precision floating point value of $fpleft times $fpright */ 
    public Instruction mul_d (FpRegs dest, FpRegs left, FpRegs right) { return new NonMemoryInstruction("mul.d", "$"+dest, "$"+left, "$"+right); }
    /** Floating point division double precision : Set $fpdest to double-precision floating point value of $fpleft divided by $fpright */ 
    public Instruction div_d (FpRegs dest, FpRegs left, FpRegs right) { return new NonMemoryInstruction("div.d", "$"+dest, "$"+left, "$"+right); }
    /** Square root double precision : Set $fpdest to double-precision floating point square root of $fpsrc */ 
    public Instruction sqrt_d (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("sqrt.d", "$"+dest, "$"+src); }
    /** Ceiling double precision to word : Set $fpdest to 32-bit integer ceiling of double-precision float in $fpsrc */ 
    public Instruction ceil_w_d (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("ceil.w.d", "$"+dest, "$"+src); }
    /** Round double precision to word : Set $fpdest to 32-bit integer round of double-precision float in $fpsrc */ 
    public Instruction round_w_d (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("round.w.d", "$"+dest, "$"+src); }
    /** Truncate double precision to word : Set $fpdest to 32-bit integer truncation of double-precision float in $fpsrc */ 
    public Instruction trunc_w_d (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("trunc.w.d", "$"+dest, "$"+src); }
    /** Branch if FP condition flag 0 true (BC1T, not BCLT) : If Coprocessor 1 condition flag 0 is true (one) then branch to statement at <label>'s address */ 
    public Instruction bc1t (String label) { return new NonMemoryInstruction("bc1t", ""+label); }
    /** Branch if FP condition flag 0 false (BC1F, not BCLF) : If Coprocessor 1 condition flag 0 is false (zero) then branch to statement at <label>'s address */ 
    public Instruction bc1f (String label) { return new NonMemoryInstruction("bc1f", ""+label); }
    /** Compare equal single precision : If $fpleft is equal to $fpright, set Coprocessor 1 condition flag 0 true else set it false */ 
    public Instruction c_eq_s (FpRegs left, FpRegs right) { return new NonMemoryInstruction("c.eq.s", "$"+left, "$"+right); }
    /** Compare less or equal single precision : If $fpleft is less than or equal to $fpright, set Coprocessor 1 condition flag 0 true else set it false */ 
    public Instruction c_le_s (FpRegs left, FpRegs right) { return new NonMemoryInstruction("c.le.s", "$"+left, "$"+right); }
    /** Compare less than single precision : If $fpleft is less than $fpright, set Coprocessor 1 condition flag 0 true else set it false */ 
    public Instruction c_lt_s (FpRegs left, FpRegs right) { return new NonMemoryInstruction("c.lt.s", "$"+left, "$"+right); }
    /** Compare equal double precision : If $fpleft is equal to $fpright (double-precision), set Coprocessor 1 condition flag 0 true else set it false */ 
    public Instruction c_eq_d (FpRegs left, FpRegs right) { return new NonMemoryInstruction("c.eq.d", "$"+left, "$"+right); }
    /** Compare less or equal double precision : If $fpleft is less than or equal to $fpright (double-precision), set Coprocessor 1 condition flag 0 true else set it false */ 
    public Instruction c_le_d (FpRegs left, FpRegs right) { return new NonMemoryInstruction("c.le.d", "$"+left, "$"+right); }
    /** Compare less than double precision : If $fpleft is less than $fpright (double-precision), set Coprocessor 1 condition flag 0 true else set it false */ 
    public Instruction c_lt_d (FpRegs left, FpRegs right) { return new NonMemoryInstruction("c.lt.d", "$"+left, "$"+right); }
    /** Floating point absolute value single precision : Set $fpdest to absolute value of $fpsrc, single precision */ 
    public Instruction abs_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("abs.s", "$"+dest, "$"+src); }
    /** Floating point absolute value double precision : Set $fpdest to absolute value of $fpsrc, double precision */ 
    public Instruction abs_d (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("abs.d", "$"+dest, "$"+src); }
    /** Convert from single precision to double precision : Set $fpdest to double precision equivalent of single precision value in $fpsrc */ 
    public Instruction cvt_d_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("cvt.d.s", "$"+dest, "$"+src); }
    /** Convert from word to double precision : Set $fpdest to double precision equivalent of 32-bit integer value in $fpsrc */ 
    public Instruction cvt_d_w (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("cvt.d.w", "$"+dest, "$"+src); }
    /** Convert from double precision to single precision : Set $fpdest to single precision equivalent of double precision value in $fpsrc */ 
    public Instruction cvt_s_d (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("cvt.s.d", "$"+dest, "$"+src); }
    /** Convert from word to single precision : Set $fpdest to single precision equivalent of 32-bit integer value in $fpsrc */ 
    public Instruction cvt_s_w (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("cvt.s.w", "$"+dest, "$"+src); }
    /** Convert from double precision to word : Set $fpdest to 32-bit integer equivalent of double precision value in $fpsrc */ 
    public Instruction cvt_w_d (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("cvt.w.d", "$"+dest, "$"+src); }
    /** Convert from single precision to word : Set $fpdest to 32-bit integer equivalent of single precision value in $fpsrc */ 
    public Instruction cvt_w_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("cvt.w.s", "$"+dest, "$"+src); }
    /** Move floating point double precision : Set double precision $fpdest to double precision value in $fpsrc */ 
    public Instruction mov_d (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("mov.d", "$"+dest, "$"+src); }
    /** Move floating point double precision : If condition flag 0 false, set double precision $fpdest to double precision value in $fpsrc */ 
    public Instruction movf_d (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("movf.d", "$"+dest, "$"+src); }
    /** Move floating point double precision : If condition flag 0 true, set double precision $fpdest to double precision value in $fpsrc */ 
    public Instruction movt_d (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("movt.d", "$"+dest, "$"+src); }
    /** Move floating point double precision : If $intreg is not zero, set double precision $fpdest to double precision value in $fpsrc */ 
    public Instruction movn_d (FpRegs dest, FpRegs src, Regs intreg) { return new NonMemoryInstruction("movn.d", "$"+dest, "$"+src, "$"+intreg); }
    /** Move floating point double precision : If $intreg is zero, set double precision $fpdest to double precision value in $fpsrc */ 
    public Instruction movz_d (FpRegs dest, FpRegs src, Regs intreg) { return new NonMemoryInstruction("movz.d", "$"+dest, "$"+src, "$"+intreg); }
    /** Move floating point single precision : Set single precision $fpdest to single precision value in $fpsrc */ 
    public Instruction mov_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("mov.s", "$"+dest, "$"+src); }
    /** Move floating point single precision : If condition flag 0 is false, set single precision $fpdest to single precision value in $fpsrc */ 
    public Instruction movf_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("movf.s", "$"+dest, "$"+src); }
    /** Move floating point single precision : If condition flag 0 is true, set single precision $fpdest to single precision value in $fpsrce */ 
    public Instruction movt_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("movt.s", "$"+dest, "$"+src); }
    /** Move floating point single precision : If $intreg is not zero, set single precision $fpdest to single precision value in $fpsrc */ 
    public Instruction movn_s (FpRegs dest, FpRegs src, Regs intreg) { return new NonMemoryInstruction("movn.s", "$"+dest, "$"+src, "$"+intreg); }
    /** Move floating point single precision : If $intreg is zero, set single precision $fpdest to single precision value in $fpsrc */ 
    public Instruction movz_s (FpRegs dest, FpRegs src, Regs intreg) { return new NonMemoryInstruction("movz.s", "$"+dest, "$"+src, "$"+intreg); }
    /** Move from Coprocessor 1 (FPU) : Set $intdest to value in Coprocessor 1 register $fpsrc */ 
    public Instruction mfc1 (Regs intdest, FpRegs src) { return new NonMemoryInstruction("mfc1", "$"+intdest, "$"+src); }
    /** Move to Coprocessor 1 (FPU) : Set Coprocessor 1 register $fpdest to value in $intsrc */ 
    public Instruction mtc1 (Regs intsrc, FpRegs dest) { return new NonMemoryInstruction("mtc1", "$"+intsrc, "$"+dest); }
    /** Floating point negate double precision : Set double precision $fpdest to negation of double precision value in $fpsrc */ 
    public Instruction neg_d (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("neg.d", "$"+dest, "$"+src); }
    /** Floating point negate single precision : Set single precision $fpdest to negation of single precision value in $fpsrc */ 
    public Instruction neg_s (FpRegs dest, FpRegs src) { return new NonMemoryInstruction("neg.s", "$"+dest, "$"+src); }
    /** Load word into Coprocessor 1 (FPU) : Set $fpdest to 32-bit value from effective memory word address */ 
    public Instruction lwc1 (FpRegs dest, int offset, Regs base) { return new MemoryAccessInstruction("lwc1", "$"+dest, offset, "$"+base); }
    /** Load double word Coprocessor 1 (FPU)) : Set $fpdest to 64-bit value from effective memory doubleword address */ 
    public Instruction ldc1 (FpRegs dest, int offset, Regs base) { return new MemoryAccessInstruction("ldc1", "$"+dest, offset, "$"+base); }
    /** Store word from Coprocesor 1 (FPU) : Store 32 bit value in $fpsrc to effective memory word address */ 
    public Instruction swc1 (FpRegs src, int offset, Regs base) { return new MemoryAccessInstruction("swc1", "$"+src, offset, "$"+base); }
    /** Store double word from Coprocessor 1 (FPU)) : Store 64 bit value in $fpsrc to effective memory doubleword address */ 
    public Instruction sdc1 (FpRegs src, int offset, Regs base) { return new MemoryAccessInstruction("sdc1", "$"+src, offset, "$"+base); }
    /** Trap if equal : Trap if $left is equal to $right */ 
    public Instruction teq (Regs left, Regs right) { return new NonMemoryInstruction("teq", "$"+left, "$"+right); }
    /** Trap if equal to immediate : Trap if $src is equal to sign-extended 16 bit immediate */ 
    public Instruction teqi (Regs src, int signed) { return new NonMemoryInstruction("teqi", "$"+src, ""+signed); }
    /** Trap if not equal : Trap if $left is not equal to $right */ 
    public Instruction tne (Regs left, Regs right) { return new NonMemoryInstruction("tne", "$"+left, "$"+right); }
    /** Trap if not equal to immediate : Trap if $src is not equal to sign-extended 16 bit immediate */ 
    public Instruction tnei (Regs src, int signed) { return new NonMemoryInstruction("tnei", "$"+src, ""+signed); }
    /** Trap if greater or equal : Trap if $left is greater than or equal to $right */ 
    public Instruction tge (Regs left, Regs right) { return new NonMemoryInstruction("tge", "$"+left, "$"+right); }
    /** Trap if greater or equal unsigned : Trap if $left is greater than or equal to $right using unsigned comparision */ 
    public Instruction tgeu (Regs left, Regs right) { return new NonMemoryInstruction("tgeu", "$"+left, "$"+right); }
    /** Trap if greater than or equal to immediate : Trap if $src greater than or equal to sign-extended 16 bit immediate */ 
    public Instruction tgei (Regs src, int signed) { return new NonMemoryInstruction("tgei", "$"+src, ""+signed); }
    /** Trap if greater or equal to immediate unsigned : Trap if $src greater than or equal to sign-extended 16 bit immediate, unsigned comparison */ 
    public Instruction tgeiu (Regs src, int signed) { return new NonMemoryInstruction("tgeiu", "$"+src, ""+signed); }
    /** Trap if less than: Trap if $left less than $right */ 
    public Instruction tlt (Regs left, Regs right) { return new NonMemoryInstruction("tlt", "$"+left, "$"+right); }
    /** Trap if less than unsigned : Trap if $left less than $right, unsigned comparison */ 
    public Instruction tltu (Regs left, Regs right) { return new NonMemoryInstruction("tltu", "$"+left, "$"+right); }
    /** Trap if less than immediate : Trap if $src less than sign-extended 16-bit immediate */ 
    public Instruction tlti (Regs src, int signed) { return new NonMemoryInstruction("tlti", "$"+src, ""+signed); }
    /** Trap if less than immediate unsigned : Trap if $src less than sign-extended 16-bit immediate, unsigned comparison */ 
    public Instruction tltiu (Regs src, int signed) { return new NonMemoryInstruction("tltiu", "$"+src, ""+signed); }
    /** Exception return : Set Program Counter to Coprocessor 0 EPC register value, set Coprocessor Status register bit 1 (exception level) to zero */ 
    public Instruction eret () { return new NonMemoryInstruction("eret"); }
}
