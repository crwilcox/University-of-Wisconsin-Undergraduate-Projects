package cs536.codegen.mips;

/** Constants for integer general purpose registers */
public enum Regs
{

    // Integer (32-bit word) registers

    zero,   // register which always has the value 0
	at, // "assembler temporary", reserved by assembler (for pseudoinstructions)
	v0, // return values (or use the stack)
	v1,
	a0, // arguments traditionally (I suggest using stack)
	a1,
	a2,
	a3,
	t0, // temporary (caller-saved) registers
	t1, // (but you don't have to deal with saved registers
	t2, // other than the special cases of $fp and $ra)
	t3,
	t4,
	t5,
	t6,
	t7,
	s0, // safe (callee-saved) registers
	s1,
	s2,
	s3,
	s4,
	s5,
	s6,
	s7,
	t8, // here because this is actually $r24
	t9, // $r25
	k0, // reserved for kernel (trap) use
	k1,
	gp, // "global pointer" (you can ignore)
	sp, // stack pointer
	fp, // frame pointer
	ra  // return address
	;
}
