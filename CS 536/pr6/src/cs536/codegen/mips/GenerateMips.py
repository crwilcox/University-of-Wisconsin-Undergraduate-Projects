
# these are from MARS, and in fact taken almost verbitim
instructions = [
    ("nop", "No operation"),
    ("add   $dest,$left,$right", "Addition with overflow : set $dest to ($left plus $right)"),
    ("sub   $dest,$left,$right", "Subtraction with overflow : set $dest to ($left minus $right)"),
    ("addi  $dest,$left,<signed>", "Addition immediate with overflow : set $dest to ($left plus signed 16-bit immediate)"),
    ("addu  $dest,$left,$right", "Addition unsigned without overflow : set $dest to ($left plus $right), no overflow"),
    ("subu  $dest,$left,$right", "Subtraction unsigned without overflow : set $dest to ($left minus $right), no overflow"),
    ("addiu $dest,$left,<signed>", "Addition immediate unsigned without overflow : set $dest to ($left plus signed 16-bit immediate), no overflow"),
    ("mult  $dest,$left", "Multiplication : Set hi to high-order 32 bits, lo to low-order 32 bits of the product of $dest and $left (use mfhi to access hi, mflo to access lo)"),
    ("multu $dest,$left", "Multiplication unsigned : Set HI to high-order 32 bits, LO to low-order 32 bits of the product of unsigned $dest and $left (use mfhi to access HI, mflo to access LO)"),
    ("mul   $dest,$left,$right", "Multiplication without overflow  : Set HI to high-order 32 bits, LO and $dest to low-order 32 bits of the product of $dest and $left (use mfhi to access HI, mflo to access LO)"),
    ("madd  $dest,$left", "Multiply add : Multiply $dest by $left then increment HI by high-order 32 bits of product, increment LO by low-order 32 bits of product (use mfhi to access HI, mflo to access LO)"),
    ("maddu $dest,$left", "Multiply add unsigned : Multiply $dest by $left then increment HI by high-order 32 bits of product, increment LO by low-order 32 bits of product, unsigned (use mfhi to access HI, mflo to access LO)"),
    ("msub  $dest,$left", "Multiply subtract : Multiply $dest by $left then decrement HI by high-order 32 bits of product, decrement LO by low-order 32 bits of product (use mfhi to access HI, mflo to access LO)"),
    ("msubu $dest,$left", "Multiply subtract unsigned : Multiply $dest by $left then decrement HI by high-order 32 bits of product, decement LO by low-order 32 bits of product, unsigned (use mfhi to access HI, mflo to access LO)"),
    ("div   $dest,$left", "Division with overflow : Divide $dest by $left then set LO to quotient and HI to remainder (use mfhi to access HI, mflo to access LO)"),
    ("divu  $dest,$left", "Division unsigned without overflow : Divide unsigned $dest by $left then set LO to quotient and HI to remainder (use mfhi to access HI, mflo to access LO)"),
    ("mfhi $dest",  "Move from HI register : Set $dest to contents of HI (see multiply and divide operations)"),
    ("mflo $dest",  "Move from LO register : Set $dest to contents of LO (see multiply and divide operations)"),
    ("mthi $dest",  "Move to HI registerr : Set HI to contents of $dest (see multiply and divide operations)"),
    ("mtlo $dest",  "Move to LO register : Set LO to contents of $dest (see multiply and divide operations)"),
    ("and  $dest,$left,$right", "Bitwise AND : Set $dest to bitwise AND of $left and $right"),
    ("or   $dest,$left,$right", "Bitwise OR : Set $dest to bitwise OR of $left and $right"),
    ("andi $dest,$left,<unsigned>", "Bitwise AND immediate : Set $dest to bitwise AND of $left and zero-extended 16-bit immediate"),
    ("ori  $dest,$left,<unsigned>", "Bitwise OR immediate : Set $dest to bitwise OR of $left and zero-extended 16-bit immediate"),
    ("nor  $dest,$left,$right", "Bitwise NOR : Set $dest to bitwise NOR of $left and $right"),
    ("xor  $dest,$left,$right", "Bitwise XOR (exclusive OR) : Set $dest to bitwise XOR of $left and $right"),
    ("xori $dest,$left,<unsigned>", "Bitwise XOR immediate : Set $dest to bitwise XOR of $left and zero-extended 16-bit immediate"),
    ("sll  $dest,$left,<numbits>", "Shift left logical : Set $dest to result of shifting $left left by number of bits specified by immediate"),
    ("sllv $dest,$left,$right", "Shift left logical variable : Set $dest to result of shifting $left left by number of bits specified by value in low-order 5 bits of $right"),
    ("srl  $dest,$left,<numbits>", "Shift right logical : Set $dest to result of shifting $left right by number of bits specified by immediate"),
    ("sra  $dest,$left,<numbits>", "Shift right arithmetic : Set $dest to result of sign-extended shifting $left right by number of bits specified by immediate"),
    ("srav $dest,$left,$right", "Shift right arithmetic variable : Set $dest to result of sign-extended shifting $left right by number of bits specified by value in low-order 5 bits of $right"),
    ("srlv $dest,$left,$right", "Shift right logical variable : Set $dest to result of shifting $left right by number of bits specified by value in low-order 5 bits of $right"),
    ("lui  $dest,<unsigned>", "Load upper immediate : Set high-order 16 bits of $dest to 16-bit immediate and low-order 16 bits to 0"),
    ("lw $dest,<offset>,($base)", "Load word : Set $dest to contents of effective memory word address"),
    ("ll $dest,<offset>,($base)", "Load linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors."),
    ("lwl $dest,<offset>,($base)", "Load word left : Load from 1 to 4 bytes left-justified into $dest, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("lwr $dest,<offset>,($base)", "Load word right : Load from 1 to 4 bytes right-justified into $dest, starting with effective memory byte address and continuing through the high-order byte of its word"),
    ("sw $src,<offset>,($base)", "Store word : Store contents of $src into effective memory word address"),
    ("sc $src,<offset>,($base)", "Store conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Stores $src value into effective address, then sets $src to 1 for success.  Always succeeds because MARS does not simulate multiple processors."),
    ("swl $src,<offset>,($base)", "Store word left : Store high-order 1 to 4 bytes of $src into memory, starting with effective byte address and continuing through the low-order byte of its word"),
    ("swr $src,<offset>,($base)", "Store word right : Store low-order 1 to 4 bytes of $src into memory, starting with high-order byte of word containing effective byte address and continuing through that byte address"),
    ("lb  $dest,<offset>,($base)", "Load byte : Set $dest to sign-extended 8-bit value from effective memory byte address"),
    ("lh  $dest,<offset>,($base)", "Load halfword : Set $dest to sign-extended 16-bit value from effective memory halfword address"),
    ("lhu $dest,<offset>,($base)", "Load halfword unsigned : Set $dest to zero-extended 16-bit value from effective memory halfword address"),
    ("lbu $dest,<offset>,($base)", "Load byte unsigned : Set $dest to zero-extended 8-bit value from effective memory byte address"),
    ("sb  $src,<offset>,($base)", "Store byte : Store the low-order 8 bits of $src into the effective memory byte address"),
    ("sh  $src,<offset>,($base)", "Store halfword : Store the low-order 16 bits of $src into the effective memory halfword address"),
    ("beq    $left,$t2,<label>", "Branch if equal : Branch to statement at <label>'s address if $left and $t2 are equal"),
    ("bne    $left,$t2,<label>", "Branch if not equal : Branch to statement at <label>'s address if $left and $t2 are not equal"),
    ("bgez   $src,<label>", "Branch if greater than or equal to zero : Branch to statement at <label>'s address if $src is greater than or equal to zero"),
    ("bgezal $src,<label>", "Branch if greater then or equal to zero and link : If $src is greater than or equal to zero, then set $ra to the Program Counter and branch to statement at <label>'s address"),
    ("bgtz   $src,<label>", "Branch if greater than zero : Branch to statement at <label>'s address if $src is greater than zero"),
    ("blez   $src,<label>", "Branch if less than or equal to zero : Branch to statement at <label>'s address if $src is less than or equal to zero"),
    ("bltz   $src,<label>", "Branch if less than zero : Branch to statement at <label>'s address if $src is less than zero"),
    ("bltzal $src,<label>", "Branch if less than zero and link : If $src is less than or equal to zero, then set $ra to the Program Counter and branch to statement at <label>'s address"),
    ("slt    $dest,$t2,$t3", "Set less than : If $t2 is less than $t3, then set $dest to 1 else set $dest to 0"),
    ("sltu   $dest,$t2,$t3", "Set less than unsigned : If $t2 is less than $t3 using unsigned comparision, then set $dest to 1 else set $dest to 0"),
    ("slti   $dest,$t2,<signed>", "Set less than immediate : If $t2 is less than sign-extended 16-bit immediate, then set $dest to 1 else set $dest to 0"),
    ("sltiu   $dest,$t2,<signed>", "Set less than immediate unsigned : If $t2 is less than  sign-extended 16-bit immediate using unsigned comparison, then set $dest to 1 else set $dest to 0"),
    ("movn $dest,$t2,$t3", "Move conditional not zero : Set $dest to $t2 if $t3 is not zero"),
    ("movz $dest,$t2,$t3", "Move conditional zero : Set $dest to $t2 if $t3 is zero"),
    ("movf $dest,$t2", "Move if FP condition flag 0 false : Set $dest to $t2 if FPU (Coprocessor 1) condition flag 0 is false (zero)"),
    #("movf $dest,$t2,1", "Move if specified FP condition flag false : Set $dest to $t2 if FPU (Coprocessor 1) condition flag specified by the immediate is false (zero)"),
    ("movt $dest,$t2", "Move if FP condition flag 0 true : Set $dest to $t2 if FPU (Coprocessor 1) condition flag 0 is true (one)"),
    #("movt $dest,$t2,1", "Move if specfied FP condition flag true : Set $dest to $t2 if FPU (Coprocessor 1) condition flag specified by the immediate is true (one)"),
    #("break <unsigned>",  "Break execution with code : Terminate program execution with specified exception code"),
    #("break",  "Break execution : Terminate program execution with exception"),
    ("syscall",  "Issue a system call : Execute the system call specified by value in $v0"),
    ("j  <label>",  "Jump unconditionally : Jump to statement at <label> address"),
    ("jr $src",  "Jump register unconditionally : Jump to statement whose address is in $src"),
    ("jal <label>", "Jump and link : Set $ra to Program Counter (return address) then jump to statement at <label> address"),
    ("jalr $dest,$src", "Jump and link register : Set $dest to Program Counter (return address) then jump to statement whose address is in $src"),
    ("jalr $src", "Jump and link register : Set $ra to Program Counter (return address) then jump to statement whose address is in $src"),
    ("clo $dest,$src",  "Count number of leading ones : Set $dest to the count of leading one bits in $src starting at most significant bit position"),
    ("clz $dest,$src",  "Count number of leading zeroes : Set $dest to the count of leading zero bits in $src starting at most significant bit positio"),
    ("add.s     $fpdest,$fpleft,$fpright", "Floating point addition single precision : Set $fpdest to single-precision floating point value of $fpleft plus $fpright",),
    ("sub.s     $fpdest,$fpleft,$fpright", "Floating point subtraction single precision : Set $fpdest to single-precision floating point value of $fpleft  minus $fpright"),
    ("mul.s     $fpdest,$fpleft,$fpright", "Floating point multiplication single precision : Set $fpdest to single-precision floating point value of $fpleft times $fpright"),
    ("div.s     $fpdest,$fpleft,$fpright", "Floating point division single precision : Set $fpdest to single-precision floating point value of $fpleft divided by $fpright"),
    ("sqrt.s    $fpdest,$fpsrc", "Square root single precision : Set $fpdest to single-precision floating point square root of $fpsrc"),
    ("floor.w.s $fpdest,$fpsrc", "Floor single precision to word : Set $fpdest to 32-bit integer floor of single-precision float in $fpsrc"),
    ("ceil.w.s  $fpdest,$fpsrc", "Ceiling single precision to word : Set $fpdest to 32-bit integer ceiling of single-precision float in $fpsrc"),
    ("round.w.s $fpdest,$fpsrc", "Round single precision to word : Set $fpdest to 32-bit integer round of single-precision float in $fpsrc"),
    ("trunc.w.s $fpdest,$fpsrc", "Truncate single precision to word : Set $fpdest to 32-bit integer truncation of single-precision float in $fpsrc"),
    ("add.d     $fpdest,$fpleft,$fpright", "Floating point addition double precision : Set $fpdest to double-precision floating point value of $fpleft plus $fpright"),
    ("sub.d     $fpdest,$fpleft,$fpright", "Floating point subtraction double precision : Set $fpdest to double-precision floating point value of $fpleft minus $fpright"),
    ("mul.d     $fpdest,$fpleft,$fpright", "Floating point multiplication double precision : Set $fpdest to double-precision floating point value of $fpleft times $fpright"),
    ("div.d     $fpdest,$fpleft,$fpright", "Floating point division double precision : Set $fpdest to double-precision floating point value of $fpleft divided by $fpright"),
    ("sqrt.d    $fpdest,$fpsrc", "Square root double precision : Set $fpdest to double-precision floating point square root of $fpsrc"),
    ("ceil.w.d  $fpdest,$fpsrc", "Ceiling double precision to word : Set $fpdest to 32-bit integer ceiling of double-precision float in $fpsrc"),
    ("round.w.d $fpdest,$fpsrc", "Round double precision to word : Set $fpdest to 32-bit integer round of double-precision float in $fpsrc"),
    ("trunc.w.d $fpdest,$fpsrc", "Truncate double precision to word : Set $fpdest to 32-bit integer truncation of double-precision float in $fpsrc"),
    ("bc1t <label>", "Branch if FP condition flag 0 true (BC1T, not BCLT) : If Coprocessor 1 condition flag 0 is true (one) then branch to statement at <label>'s address"),
    ("bc1f <label>", "Branch if FP condition flag 0 false (BC1F, not BCLF) : If Coprocessor 1 condition flag 0 is false (zero) then branch to statement at <label>'s address"),
    ("c.eq.s $fpleft,$fpright",   "Compare equal single precision : If $fpleft is equal to $fpright, set Coprocessor 1 condition flag 0 true else set it false"),
    ("c.le.s $fpleft,$fpright",   "Compare less or equal single precision : If $fpleft is less than or equal to $fpright, set Coprocessor 1 condition flag 0 true else set it false"),
    ("c.lt.s $fpleft,$fpright",   "Compare less than single precision : If $fpleft is less than $fpright, set Coprocessor 1 condition flag 0 true else set it false"),
    ("c.eq.d $fpleft,$fpright",   "Compare equal double precision : If $fpleft is equal to $fpright (double-precision), set Coprocessor 1 condition flag 0 true else set it false"),
    ("c.le.d $fpleft,$fpright",   "Compare less or equal double precision : If $fpleft is less than or equal to $fpright (double-precision), set Coprocessor 1 condition flag 0 true else set it false"),
    ("c.lt.d $fpleft,$fpright",   "Compare less than double precision : If $fpleft is less than $fpright (double-precision), set Coprocessor 1 condition flag 0 true else set it false"),
    ("abs.s   $fpdest,$fpsrc", "Floating point absolute value single precision : Set $fpdest to absolute value of $fpsrc, single precision"),
    ("abs.d   $fpdest,$fpsrc", "Floating point absolute value double precision : Set $fpdest to absolute value of $fpsrc, double precision"),
    ("cvt.d.s $fpdest,$fpsrc", "Convert from single precision to double precision : Set $fpdest to double precision equivalent of single precision value in $fpsrc"),
    ("cvt.d.w $fpdest,$fpsrc", "Convert from word to double precision : Set $fpdest to double precision equivalent of 32-bit integer value in $fpsrc"),
    ("cvt.s.d $fpdest,$fpsrc",        "Convert from double precision to single precision : Set $fpdest to single precision equivalent of double precision value in $fpsrc"),
    ("cvt.s.w $fpdest,$fpsrc", "Convert from word to single precision : Set $fpdest to single precision equivalent of 32-bit integer value in $fpsrc"),
    ("cvt.w.d $fpdest,$fpsrc",        "Convert from double precision to word : Set $fpdest to 32-bit integer equivalent of double precision value in $fpsrc"),
    ("cvt.w.s $fpdest,$fpsrc", "Convert from single precision to word : Set $fpdest to 32-bit integer equivalent of single precision value in $fpsrc"),
    ("mov.d  $fpdest,$fpsrc",  "Move floating point double precision : Set double precision $fpdest to double precision value in $fpsrc"),
    ("movf.d $fpdest,$fpsrc",  "Move floating point double precision : If condition flag 0 false, set double precision $fpdest to double precision value in $fpsrc"),
    #("movf.d $fpdest,$fpsrc,1",   "Move floating point double precision : If condition flag specified by immediate is false, set double precision $fpdest to double precision value in $fpsrc"),
    ("movt.d $fpdest,$fpsrc",     "Move floating point double precision : If condition flag 0 true, set double precision $fpdest to double precision value in $fpsrc"),
    #("movt.d $fpdest,$fpsrc,1",   "Move floating point double precision : If condition flag specified by immediate is true, set double precision $fpdest to double precision value in $fpsrce"),
    ("movn.d $fpdest,$fpsrc,$intreg", "Move floating point double precision : If $intreg is not zero, set double precision $fpdest to double precision value in $fpsrc"),
    ("movz.d $fpdest,$fpsrc,$intreg", "Move floating point double precision : If $intreg is zero, set double precision $fpdest to double precision value in $fpsrc"),
    ("mov.s  $fpdest,$fpsrc",   "Move floating point single precision : Set single precision $fpdest to single precision value in $fpsrc"),
    ("movf.s $fpdest,$fpsrc",   "Move floating point single precision : If condition flag 0 is false, set single precision $fpdest to single precision value in $fpsrc"),
    #("movf.s $fpdest,$fpsrc,1", "Move floating point single precision : If condition flag specified by immediate is false, set single precision $fpdest to single precision value in $fpsrce"),
    ("movt.s $fpdest,$fpsrc",   "Move floating point single precision : If condition flag 0 is true, set single precision $fpdest to single precision value in $fpsrce"),
    #("movt.s $fpdest,$fpsrc,1", "Move floating point single precision : If condition flag specified by immediate is true, set single precision $fpdest to single precision value in $fpsrce"),
    ("movn.s $fpdest,$fpsrc,$intreg", "Move floating point single precision : If $intreg is not zero, set single precision $fpdest to single precision value in $fpsrc"),
    ("movz.s $fpdest,$fpsrc,$intreg", "Move floating point single precision : If $intreg is zero, set single precision $fpdest to single precision value in $fpsrc"),
    ("mfc1  $intdest,$fpsrc", "Move from Coprocessor 1 (FPU) : Set $intdest to value in Coprocessor 1 register $fpsrc"),
    ("mtc1  $intsrc,$fpdest", "Move to Coprocessor 1 (FPU) : Set Coprocessor 1 register $fpdest to value in $intsrc"),
    ("neg.d $fpdest,$fpsrc", "Floating point negate double precision : Set double precision $fpdest to negation of double precision value in $fpsrc"),
    ("neg.s $fpdest,$fpsrc", "Floating point negate single precision : Set single precision $fpdest to negation of single precision value in $fpsrc"),
    ("lwc1 $fpdest,<offset>,($base)", "Load word into Coprocessor 1 (FPU) : Set $fpdest to 32-bit value from effective memory word address"),
    ("ldc1 $fpdest,<offset>,($base)", "Load double word Coprocessor 1 (FPU)) : Set $fpdest to 64-bit value from effective memory doubleword address"),
    ("swc1 $fpsrc,<offset>,($base)", "Store word from Coprocesor 1 (FPU) : Store 32 bit value in $fpsrc to effective memory word address"),
    ("sdc1 $fpsrc,<offset>,($base)", "Store double word from Coprocessor 1 (FPU)) : Store 64 bit value in $fpsrc to effective memory doubleword address"),
    ("teq  $left,$right", "Trap if equal : Trap if $left is equal to $right"),
    ("teqi $src,<signed>", "Trap if equal to immediate : Trap if $src is equal to sign-extended 16 bit immediate"),
    ("tne  $left,$right", "Trap if not equal : Trap if $left is not equal to $right"),
    ("tnei $src,<signed>", "Trap if not equal to immediate : Trap if $src is not equal to sign-extended 16 bit immediate"),
    ("tge  $left,$right", "Trap if greater or equal : Trap if $left is greater than or equal to $right"),
    ("tgeu $left,$right", "Trap if greater or equal unsigned : Trap if $left is greater than or equal to $right using unsigned comparision"),
    ("tgei $src,<signed>", "Trap if greater than or equal to immediate : Trap if $src greater than or equal to sign-extended 16 bit immediate"),
    ("tgeiu $src,<signed>", "Trap if greater or equal to immediate unsigned : Trap if $src greater than or equal to sign-extended 16 bit immediate, unsigned comparison"),
    ("tlt  $left,$right", "Trap if less than: Trap if $left less than $right"),
    ("tltu $left,$right", "Trap if less than unsigned : Trap if $left less than $right, unsigned comparison"),
    ("tlti $src,<signed>", "Trap if less than immediate : Trap if $src less than sign-extended 16-bit immediate"),
    ("tltiu $src,<signed>", "Trap if less than immediate unsigned : Trap if $src less than sign-extended 16-bit immediate, unsigned comparison"),
    ("eret",  "Exception return : Set Program Counter to Coprocessor 0 EPC register value, set Coprocessor Status register bit 1 (exception level) to zero"),
]

# these are from MARS, and in fact taken almost verbitim
pseudo_instructions = [
    ("not $t1,$t2", "Bitwise NOT (bit inversion)"),
    ("add $t1,$t2,<signed>", "ADDition : set $t1 to ($t2 plus 16-bit immediate)"),
    #("add $t1,$t2,<large_unsigned>", "ADDition : set $t1 to ($t2 plus 32-bit immediate)"),
    ("addu $t1,$t2,<large_unsigned>", "ADDition Unsigned : set $t1 to ($t2 plus 32-bit immediate), no overflow"),
    ("addi $t1,$t2,<large_unsigned>", "ADDition Immediate : set $t1 to ($t2 plus 32-bit immediate)"),
    ("addiu $t1,$t2,<large_unsigned>", "ADDition Immediate Unsigned: set $t1 to ($t2 plus 32-bit immediate), no overflow"),
    ("sub $t1,$t2,<signed>", "SUBtraction : set $t1 to ($t2 minus 16-bit immediate)"),
    #("sub $t1,$t2,<large_unsigned>", "SUBtraction : set $t1 to ($t2 minus 32-bit immediate)"),
    ("subu $t1,$t2,<large_unsigned>", "SUBtraction Unsigned : set $t1 to ($t2 minus 32-bit immediate), no overflow"),
    ("subi $t1,$t2,<signed>", "SUBtraction Immediate : set $t1 to ($t2 minus 16-bit immediate)"),
    #("subi $t1,$t2,<large_unsigned>", "SUBtraction Immediate : set $t1 to ($t2 minus 32-bit immediate)"),
    ("subiu $t1,$t2,<large_unsigned>", "SUBtraction Immediate Unsigned : set $t1 to ($t2 minus 32-bit immediate), no overflow"),

    #("andi $t1,$t2,<large_unsigned>", "AND Immediate : set $t1 to ($t2 bitwise-AND 32-bit immediate)"),
    #("ori $t1,$t2,<large_unsigned>", "OR Immediate : set $t1 to ($t2 bitwise-OR 32-bit immediate)"),
    #("xori $t1,$t2,<large_unsigned>", "XOR Immediate : set $t1 to ($t2 bitwise-exclusive-OR 32-bit immediate)"),
    ("and $t1,$t2,<unsigned>", "AND : set $t1 to ($t2 bitwise-AND 16-bit unsigned immediate)"),
    ("or $t1,$t2,<unsigned>", "OR : set $t1 to ($t2 bitwise-OR 16-bit unsigned immediate)"),
    ("xor $t1,$t2,<unsigned>", "XOR : set $t1 to ($t2 bitwise-exclusive-OR 16-bit unsigned immediate)"),
    ("and $t1,<unsigned>", "AND : set $t1 to ($t1 bitwise-AND 16-bit unsigned immediate)"),
    ("or $t1,<unsigned>", "OR : set $t1 to ($t1 bitwise-OR 16-bit unsigned immediate)"),
    ("xor $t1,<unsigned>", "XOR : set $t1 to ($t1 bitwise-exclusive-OR 16-bit unsigned immediate)"),
    ("andi $t1,<unsigned>", "AND Immediate : set $t1 to ($t1 bitwise-AND 16-bit unsigned immediate)"),
    ("ori $t1,<unsigned>", "OR Immediate : set $t1 to ($t1 bitwise-OR 16-bit unsigned immediate)"),
    ("xori $t1,<unsigned>", "XOR Immediate : set $t1 to ($t1 bitwise-exclusive-OR 16-bit unsigned immediate)"),
    #("andi $t1,<large_unsigned>", "AND Immediate : set $t1 to ($t1 bitwise-AND 32-bit immediate)"),
    #("ori $t1,<large_unsigned>", "OR Immediate : set $t1 to ($t1 bitwise-OR 32-bit immediate)"),
    #("xori $t1,<large_unsigned>", "XOR Immediate : set $t1 to ($t1 bitwise-exclusive-OR 32-bit immediate)"),

    ("seq $t1,$t2,$t3", "Set EQual : if $t2 equal to $t3 then set $t1 to 1 else 0"),
    ("seq $t1,$t2,<signed>", "Set EQual : if $t2 equal to 16-bit immediate then set $t1 to 1 else 0"),
    #("seq $t1,$t2,<large_unsigned>", "Set EQual : if $t2 equal to 32-bit immediate then set $t1 to 1 else 0"),

    ("sne $t1,$t2,$t3", "Set Not Equal : if $t2 not equal to $t3 then set $t1 to 1 else 0"),
    ("sne $t1,$t2,<signed>", "Set Not Equal : if $t2 not equal to 16-bit immediate then set $t1 to 1 else 0"),
    #("sne $t1,$t2,<large_unsigned>", "Set Not Equal : if $t2 not equal to 32-bit immediate then set $t1 to 1 else 0"),

    ("sge $t1,$t2,$t3", "Set Greater or Equal : if $t2 greater or equal to $t3 then set $t1 to 1 else 0"),
    ("sge $t1,$t2,<signed>", "Set Greater or Equal : if $t2 greater or equal to 16-bit immediate then set $t1 to 1 else 0"),
    #("sge $t1,$t2,<large_unsigned>", "Set Greater or Equal : if $t2 greater or equal to 32-bit immediate then set $t1 to 1 else 0"),

    ("sgeu $t1,$t2,$t3", "Set Greater or Equal Unsigned : if $t2 greater or equal to $t3 (unsigned compare) then set $t1 to 1 else 0"),
    ("sgeu $t1,$t2,<signed>", "Set Greater or Equal Unsigned : if $t2 greater or equal to 16-bit immediate (unsigned compare) then set $t1 to 1 else 0"),
    #("sgeu $t1,$t2,<large_unsigned>", "Set Greater or Equal Unsigned : if $t2 greater or equal to 32-bit immediate (unsigned compare) then set $t1 to 1 else 0"),

    ("sgt $t1,$t2,$t3", "Set Greater Than : if $t2 greater than $t3 then set $t1 to 1 else 0"),
    ("sgt $t1,$t2,<signed>", "Set Greater Than : if $t2 greater than 16-bit immediate then set $t1 to 1 else 0"),
    #("sgt $t1,$t2,<large_unsigned>", "Set Greater Than : if $t2 greater than 32-bit immediate then set $t1 to 1 else 0"),

    ("sgtu $t1,$t2,$t3", "Set Greater Than Unsigned : if $t2 greater than $t3 (unsigned compare) then set $t1 to 1 else 0"),
    ("sgtu $t1,$t2,<signed>", "Set Greater Than Unsigned : if $t2 greater than 16-bit immediate (unsigned compare) then set $t1 to 1 else 0"),
    #("sgtu $t1,$t2,<large_unsigned>", "Set Greater Than Unsigned : if $t2 greater than 32-bit immediate (unsigned compare) then set $t1 to 1 else 0"),

    ("sle $t1,$t2,$t3", "Set Less or Equal : if $t2 less or equal to $t3 then set $t1 to 1 else 0"),
    ("sle $t1,$t2,<signed>", "Set Less or Equal : if $t2 less or equal to 16-bit immediate then set $t1 to 1 else 0"),
    #("sle $t1,$t2,<large_unsigned>", "Set Less or Equal : if $t2 less or equal to 32-bit immediate then set $t1 to 1 else 0"),

    ("sleu $t1,$t2,$t3", "Set Less or Equal Unsigned: if $t2 less or equal to $t3 (unsigned compare) then set $t1 to 1 else 0"),
    ("sleu $t1,$t2,<signed>", "Set Less or Equal Unsigned: if $t2 less or equal to 16-bit immediate (unsigned compare) then set $t1 to 1 else 0"),
    #("sleu $t1,$t2,<large_unsigned>", "Set Less or Equal Unsigned: if $t2 less or equal to 32-bit immediate (unsigned compare) then set $t1 to 1 else 0"),


    ("move $t1,$t2", "MOVE : Set $t1 to contents of $t2"),
    ("abs $t1,$t2", "ABSolute value : Set $t1 to absolute value of $t2 (algorithm from Hacker's Delight) "),
    ("neg $t1,$t2", "NEGate : Set $t1 to negation of $t2"),
    ("negu $t1,$t2", "NEGate Unsigned : Set $t1 to negation of $t2, no overflow"),

    ("b <label>", "Branch : Branch to statement at label unconditionally"),
    ("beqz $t1,<label>", "Branch if EQual Zero : Branch to statement at label if $t1 is equal to zero"),
    ("bnez $t1,<label>", "Branch if Not Equal Zero : Branch to statement at label if $t1 is not equal to zero"),

    ("beq $t1,<signed>,<label>", "Branch if EQual : Branch to statement at label if $t1 is equal to 16-bit immediate"),
    #("beq $t1,<large_unsigned>,<label>", "Branch if EQual : Branch to statement at label if $t1 is equal to 32-bit immediate     "),
    ("bne $t1,<signed>,<label>", "Branch if Not Equal : Branch to statement at label if $t1 is not equal to 16-bit immediate"),
    #("bne $t1,<large_unsigned>,<label>", "Branch if Not Equal : Branch to statement at label if $t1 is not equal to 32-bit immediate     "),

    ("bge $t1,$t2,<label>", "Branch if Greater or Equal : Branch to statement at label if $t1 is greater or equal to $t2"),
    ("bge $t1,<signed>,<label>", "Branch if Greater or Equal : Branch to statement at label if $t1 is greater or equal to 16-bit immediate     "),
    #("bge $t1,<large_unsigned>,<label>", "Branch if Greater or Equal : Branch to statement at label if $t1 is greater or equal to 32-bit immediate     "),

    ("bgeu $t1,$t2,<label>", "Branch if Greater or Equal Unsigned : Branch to statement at label if $t1 is greater or equal to $t2 (unsigned compare)"),
    ("bgeu $t1,<signed>,<label>", "Branch if Greater or Equal Unsigned : Branch to statement at label if $t1 is greater or equal to 16-bit immediate (unsigned compare)"),
    #("bgeu $t1,<large_unsigned>,<label>", "Branch if Greater or Equal Unsigned : Branch to statement at label if $t1 is greater or equal to 32-bit immediate (unsigned compare)"),

    ("bgt $t1,$t2,<label>", "Branch if Greater Than : Branch to statement at label if $t1 is greater than $t2"),
    ("bgt $t1,<signed>,<label>", "Branch if Greater Than : Branch to statement at label if $t1 is greater than 16-bit immediate     "),
    #("bgt $t1,<large_unsigned>,<label>", "Branch if Greater Than : Branch to statement at label if $t1 is greater than 32-bit immediate"),

    ("bgtu $t1,$t2,<label>", "Branch if Greater Than Unsigned: Branch to statement at label if $t1 is greater than $t2 (unsigned compare)"),
    ("bgtu $t1,<signed>,<label>", "Branch if Greater Than Unsigned: Branch to statement at label if $t1 is greater than 16-bit immediate (unsigned compare)"),
    #("bgtu $t1,<large_unsigned>,<label>", "Branch if Greater Than Unsigned: Branch to statement at label if $t1 is greater than 16-bit immediate (unsigned compare)"),

    ("ble $t1,$t2,<label>", "Branch if Less or Equal : Branch to statement at label if $t1 is less than or equal to $t2"),
    ("ble $t1,<signed>,<label>", "Branch if Less or Equal : Branch to statement at label if $t1 is less than or equal to 16-bit immediate"),
    #("ble $t1,<large_unsigned>,<label>", "Branch if Less or Equal : Branch to statement at label if $t1 is less than or equal to 32-bit immediate     "),

    ("bleu $t1,$t2,<label>", "Branch if Less or Equal Unsigned : Branch to statement at label if $t1 is less than or equal to $t2 (unsigned compare)"),
    ("bleu $t1,<signed>,<label>", "Branch if Less or Equal Unsigned : Branch to statement at label if $t1 is less than or equal to 16-bit immediate (unsigned compare)"),
    #("bleu $t1,<large_unsigned>,<label>", "Branch if Less or Equal Unsigned : Branch to statement at label if $t1 is less than or equal to 32-bit immediate (unsigned compare)"),

    ("blt $t1,$t2,<label>", "Branch if Less Than : Branch to statement at label if $t1 is less than $t2"),
    ("blt $t1,<signed>,<label>", "Branch if Less Than : Branch to statement at label if $t1 is less than 16-bit immediate"),
    #("blt $t1,<large_unsigned>,<label>", "Branch if Less Than : Branch to statement at label if $t1 is less than 32-bit immediate"),

    ("bltu $t1,$t2,<label>", "Branch if Less Than Unsigned : Branch to statement at label if $t1 is less than $t2"),
    ("bltu $t1,<signed>,<label>", "Branch if Less Than Unsigned : Branch to statement at label if $t1 is less than 16-bit immediate     "),
    #("bltu $t1,<large_unsigned>,<label>", "Branch if Less Than Unsigned : Branch to statement at label if $t1 is less than 32-bit immediate"),

    ("rol $t1,$t2,$t3", "ROtate Left : Set $t1 to ($t2 rotated left by number of bit positions specified in $t3)"),
    ("rol $t1,$t2,<numbits>", "ROtate Left : Set $t1 to ($t2 rotated left by number of bit positions specified in 5-bit immediate)"),
    ("ror $t1,$t2,$t3", "ROtate Right : Set $t1 to ($t2 rotated right by number of bit positions specified in $t3)"),
    ("ror $t1,$t2,<numbits>", "ROtate Right : Set $t1 to ($t2 rotated right by number of bit positions specified in 5-bit immediate)"),

    ("mfc1.d $t1,$fpf2", "Move From Coprocessor 1 Double : Set $t1 to contents of $fpf2, set next higher register from $t1 to contents of next higher register from $fpf2"),
    ("mtc1.d $t1,$fpf2", "Move To Coprocessor 1 Double : Set $fpf2 to contents of $t1, set next higher register from $fpf2 to contents of next higher register from $t1"),

    ("mul $t1,$t2,<signed>", "MULtiplication : Set HI to high-order 32 bits, LO and $t1 to low-order 32 bits of the product of $t2 and 16-bit signed immediate (use mfhi to access HI, mflo to access LO)"),
    #("mul $t1,$t2,<large_unsigned>", "MULtiplication : Set HI to high-order 32 bits, LO and $t1 to low-order 32 bits of the product of $t2 and 32-bit immediate (use mfhi to access HI, mflo to access LO)"),
    ("mulu $t1,$t2,$t3", "MULtiplication Unsigned : Set HI to high-order 32 bits, LO and $t1 to low-order 32 bits of ($t2 multiplied by $t3, unsigned multiplication)"),
    ("mulu $t1,$t2,<signed>", "MULtiplication Unsigned :  Set HI to high-order 32 bits, LO and $t1 to low-order 32 bits of ($t2 multiplied by 16-bit immediate, unsigned multiplication)"),
    #("mulu $t1,$t2,<large_unsigned>", "MULtiplication Unsigned :  Set HI to high-order 32 bits, LO and $t1 to low-order 32 bits of ($t2 multiplied by 32-bit immediate, unsigned multiplication)"),
    ("mulo $t1,$t2,$t3", "MULtiplication with Overflow : Set $t1 to low-order 32 bits of the product of $t2 and $t3"),
    ("mulo $t1,$t2,<signed>", "MULtiplication with Overflow : Set $t1 to low-order 32 bits of the product of $t2 and signed 16-bit immediate"),
    #("mulo $t1,$t2,<large_unsigned>", "MULtiplication with Overflow : Set $t1 to low-order 32 bits of the product of $t2 and 32-bit immediate"),
    ("mulou $t1,$t2,$t3", "MULtiplication with Overflow Unsigned : Set $t1 to low-order 32 bits of the product of $t2 and $t3"),
    ("mulou $t1,$t2,<signed>", "MULtiplication with Overflow Unsigned : Set $t1 to low-order 32 bits of the product of $t2 and signed 16-bit immediate"),
    #("mulou $t1,$t2,<large_unsigned>", "MULtiplication with Overflow Unsigned : Set $t1 to low-order 32 bits of the product of $t2 and 32-bit immediate"),
    ("div $t1,$t2,$t3", "DIVision : Set $t1 to ($t2 divided by $t3, integer division)"),
    ("div $t1,$t2,<signed>", "DIVision : Set $t1 to ($t2 divided by 16-bit immediate, integer division)"),
    #("div $t1,$t2,<large_unsigned>", "DIVision : Set $t1 to ($t2 divided by 32-bit immediate, integer division)"),
    ("divu $t1,$t2,$t3", "DIVision Unsigned :  Set $t1 to ($t2 divided by $t3, unsigned integer division)"),
    ("divu $t1,$t2,<signed>", "DIVision Unsigned :  Set $t1 to ($t2 divided by 16-bit immediate, unsigned integer division)"),
    #("divu $t1,$t2,<large_unsigned>", "DIVision Unsigned :  Set $t1 to ($t2 divided by 32-bit immediate, unsigned integer division)"),
    ("rem $t1,$t2,$t3", "REMainder : Set $t1 to (remainder of $t2 divided by $t3)"),
    ("rem $t1,$t2,<signed>", "REMainder : Set $t1 to (remainder of $t2 divided by 16-bit immediate)"),
    #("rem $t1,$t2,<large_unsigned>", "REMainder : Set $t1 to (remainder of $t2 divided by 32-bit immediate)"),
    ("remu $t1,$t2,$t3", "REMainder : Set $t1 to (remainder of $t2 divided by $t3, unsigned division)"),
    ("remu $t1,$t2,<signed>", "REMainder : Set $t1 to (remainder of $t2 divided by 16-bit immediate, unsigned division)"),
    #("remu $t1,$t2,<large_unsigned>", "REMainder : Set $t1 to (remainder of $t2 divided by 32-bit immediate, unsigned division)"),


    #########################  load/store pseudo-ops start here  ##########################,
    #,
    #  Most of these simply provide a variety of convenient memory addressing modes for ),
    #  specifying load/store address.
    #

    ("li $t1,<signed>", "Load Immediate : Set $t1 to 16-bit immediate (sign-extended)"),
    #("li $t1,<unsigned>", "Load Immediate : Set $t1 to unsigned 16-bit immediate (zero-extended)"),
    #("li $t1,<large_unsigned>", "Load Immediate : Set $t1 to 32-bit immediate"),

    ("la $t1,($t2)", "Load Address : Set $t1 to contents of $t2"),
    ("la $t1,<signed>", "Load Address : Set $t1 to 16-bit immediate (sign-extended) "),
    #("la $t1,<unsigned>", "Load Address : Set $t1 to 16-bit immediate (zero-extended) "),
    #("la $t1,<large_unsigned>", "Load Address : Set $t1 to 32-bit immediate"),
    ("la $t1,<unsigned>,($t2)", "Load Address : Set $t1 to sum (of $t2 and 16-bit immediate)"),
    #("la $t1,<large_unsigned>,($t2)", "Load Address : Set $t1 to sum (of $t2 and 32-bit immediate)"),
    ("la $t1,<label>", "Load Address : Set $t1 to label's address"),
    ("la $t1,<label>,($t2)", "Load Address : Set $t1 to sum (of $t2 and label's address)"),
    ("la $t1,<label>,<large_unsigned>", "Load Address : Set $t1 to sum (of label's address and 32-bit immediate)"),
    ("la $t1,<label>,<large_unsigned>,($t2)", "Load Address : Set $t1 to sum (of label's address, 32-bit immediate, and $t2)"),

    ("lw $t1,($t2)", "Load Word : Set $t1 to contents of effective memory word address"),
    ("lw $t1,<signed>", "Load Word : Set $t1 to contents of effective memory word address"),
    #("lw $t1,<unsigned>", "Load Word : Set $t1 to contents of effective memory word address"),
    #("lw $t1,<large_unsigned>", "Load Word : Set $t1 to contents of effective memory word address"),
    ("lw $t1,<unsigned>,($t2)", "Load Word : Set $t1 to contents of effective memory word address"),
    #("lw $t1,<large_unsigned>,($t2)", "Load Word : Set $t1 to contents of effective memory word address"),
    ("lw $t1,<label>", "Load Word : Set $t1 to contents of memory word at label's address"),
    ("lw $t1,<label>,($t2)", "Load Word : Set $t1 to contents of effective memory word address"),
    ("lw $t1,<label>,<large_unsigned>", "Load Word : Set $t1 to contents of effective memory word address "),
    ("lw $t1,<label>,<large_unsigned>,($t2)", "Load Word : Set $t1 to contents of effective memory word address"),

    ("sw $t1,($t2)", "Store Word : Store $t1 contents into effective memory word address"),
    ("sw $t1,<signed>", "Store Word : Store $t1 contents into effective memory word address"),
    #("sw $t1,<unsigned>", "Store Word : Store $t1 contents into effective memory word address"),
    #("sw $t1,<large_unsigned>", "Store Word : Store $t1 contents into effective memory word address"),
    ("sw $t1,<unsigned>,($t2)", "Store Word : Store $t1 contents into effective memory word address"),
    #("sw $t1,<large_unsigned>,($t2)", "Store Word : Store $t1 contents into effective memory word address"),
    ("sw $t1,<label>", "Store Word : Store $t1 contents into memory word at label's address"),
    ("sw $t1,<label>,($t2)", "Store Word : Store $t1 contents into effective memory word address"),
    ("sw $t1,<label>,<large_unsigned>", "Store Word : Store $t1 contents into effective memory word address"),
    ("sw $t1,<label>,<large_unsigned>,($t2)", "Store Word : Store $t1 contents into effective memory word address"),

    ("lh $t1,($t2)", "Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address"),
    ("lh $t1,<signed>", "Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address"),
    #("lh $t1,<unsigned>", "Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address"),
    #("lh $t1,<large_unsigned>", "Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address"),
    ("lh $t1,<unsigned>,($t2)", "Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address"),
    #("lh $t1,<large_unsigned>,($t2)", "Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address"),
    ("lh $t1,<label>", "Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address"),
    ("lh $t1,<label>,($t2)", "Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address"),
    ("lh $t1,<label>,<large_unsigned>", "Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address"),
    ("lh $t1,<label>,<large_unsigned>,($t2)", "Load Halfword : Set $t1 to sign-extended 16-bit value from effective memory halfword address"),

    ("sh $t1,($t2)", "Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address"),
    ("sh $t1,<signed>", "Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address"),
    #("sh $t1,<unsigned>", "Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address"),
    #("sh $t1,<large_unsigned>", "Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address"),
    ("sh $t1,<unsigned>,($t2)", "Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address"),
    #("sh $t1,<large_unsigned>,($t2)", "Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address"),
    ("sh $t1,<label>", "Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address"),
    ("sh $t1,<label>,($t2)", "Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address"),
    ("sh $t1,<label>,<large_unsigned>", "Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address"),
    ("sh $t1,<label>,<large_unsigned>,($t2)", "Store Halfword : Store the low-order 16 bits of $t1 into the effective memory halfword address"),

    ("lb $t1,($t2)", "Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address"),
    ("lb $t1,<signed>", "Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address"),
    #("lb $t1,<unsigned>", "Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address"),
    #("lb $t1,<large_unsigned>", "Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address"),
    ("lb $t1,<unsigned>,($t2)", "Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address"),
    #("lb $t1,<large_unsigned>,($t2)", "Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address"),
    ("lb $t1,<label>", "Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address"),
    ("lb $t1,<label>,($t2)", "Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address"),
    ("lb $t1,<label>,<large_unsigned>", "Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address"),
    ("lb $t1,<label>,<large_unsigned>,($t2)", "Load Byte : Set $t1 to sign-extended 8-bit value from effective memory byte address"),

    ("sb $t1,($t2)", "Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address"),
    ("sb $t1,<signed>", "Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address"),
    #("sb $t1,<unsigned>", "Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address"),
    #("sb $t1,<large_unsigned>", "Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address"),
    ("sb $t1,<unsigned>,($t2)", "Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address"),
    #("sb $t1,<large_unsigned>,($t2)", "Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address"),
    ("sb $t1,<label>", "Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address"),
    ("sb $t1,<label>,($t2)", "Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address"),
    ("sb $t1,<label>,<large_unsigned>", "Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address"),
    ("sb $t1,<label>,<large_unsigned>,($t2)", "Store Byte : Store the low-order 8 bits of $t1 into the effective memory byte address"),

    ("lhu $t1,($t2)", "Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address"),
    ("lhu $t1,<signed>", "Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address"),
    #("lhu $t1,<unsigned>", "Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address"),
    #("lhu $t1,<large_unsigned>", "Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address"),
    ("lhu $t1,<unsigned>,($t2)", "Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address"),
    #("lhu $t1,<large_unsigned>,($t2)", "Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address"),
    ("lhu $t1,<label>", "Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address"),
    ("lhu $t1,<label>,($t2)", "Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address"),
    ("lhu $t1,<label>,<large_unsigned>", "Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address"),
    ("lhu $t1,<label>,<large_unsigned>,($t2)", "Load Halfword Unsigned : Set $t1 to zero-extended 16-bit value from effective memory halfword address"),

    ("lbu $t1,($t2)", "Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address"),
    ("lbu $t1,<signed>", "Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address"),
    #("lbu $t1,<unsigned>", "Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address"),
    #("lbu $t1,<large_unsigned>", "Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address"),
    ("lbu $t1,<unsigned>,($t2)", "Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address"),
    #("lbu $t1,<large_unsigned>,($t2)", "Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address"),
    ("lbu $t1,<label>", "Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address"),
    ("lbu $t1,<label>,($t2)", "Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address"),
    ("lbu $t1,<label>,<large_unsigned>", "Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address"),
    ("lbu $t1,<label>,<large_unsigned>,($t2)", "Load Byte Unsigned : Set $t1 to zero-extended 8-bit value from effective memory byte address"),

    ("lwl $t1,($t2)", "Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("lwl $t1,<signed>", "Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word"),
    #("lwl $t1,<unsigned>", "Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word"),
    #("lwl $t1,<large_unsigned>", "Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("lwl $t1,<unsigned>,($t2)", "Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word"),
    #("lwl $t1,<large_unsigned>,($t2)", "Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("lwl $t1,<label>", "Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("lwl $t1,<label>,($t2)", "Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("lwl $t1,<label>,<large_unsigned>", "Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("lwl $t1,<label>,<large_unsigned>,($t2)", "Load Word Left : Load from 1 to 4 bytes left-justified into $t1, starting with effective memory byte address and continuing through the low-order byte of its word"),

    ("swl $t1,($t2)", "Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("swl $t1,<signed>", "Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word"),
    #("swl $t1,<unsigned>", "Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word"),
    #("swl $t1,<large_unsigned>", "Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("swl $t1,<unsigned>,($t2)", "Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word"),
    #("swl $t1,<large_unsigned>,($t2)", "Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("swl $t1,<label>", "Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("swl $t1,<label>,($t2)", "Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("swl $t1,<label>,<large_unsigned>", "Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word"),
    ("swl $t1,<label>,<large_unsigned>,($t2)", "Store Word Left : Store high-order 1 to 4 bytes of $t1 into memory, starting with effective memory byte address and continuing through the low-order byte of its word"),

    ("lwr $t1,($t2)", "Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word"),
    ("lwr $t1,<signed>", "Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word"),
    #("lwr $t1,<unsigned>", "Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word"),
    #("lwr $t1,<large_unsigned>", "Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word"),
    ("lwr $t1,<unsigned>,($t2)", "Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word"),
    #("lwr $t1,<large_unsigned>,($t2)", "Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word"),
    ("lwr $t1,<label>", "Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word"),
    ("lwr $t1,<label>,($t2)", "Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word"),
    ("lwr $t1,<label>,<large_unsigned>", "Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word"),
    ("lwr $t1,<label>,<large_unsigned>,($t2)", "Load Word Right : Load from 1 to 4 bytes right-justified into $t1, starting with effective memory byte address and continuing through the high-order byte of its word"),

    ("swr $t1,($t2)", "Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address"),
    ("swr $t1,<signed>", "Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address"),
    #("swr $t1,<unsigned>", "Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address"),
    #("swr $t1,<large_unsigned>", "Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address"),
    ("swr $t1,<unsigned>,($t2)", "Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address"),
    #("swr $t1,<large_unsigned>,($t2)", "Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address"),
    ("swr $t1,<label>", "Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address"),
    ("swr $t1,<label>,($t2)", "Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address"),
    ("swr $t1,<label>,<large_unsigned>", "Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address"),
    ("swr $t1,<label>,<large_unsigned>,($t2)", "Store Word Right : Store low-order 1 to 4 bytes of $t1 into memory, starting with high-order byte of word containing effective memory byte address and continuing through that byte address"),

    ("ll $t1,($t2)", "Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors."),
    ("ll $t1,<signed>", "Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors."),
    #("ll $t1,<unsigned>", "Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors."),
    #("ll $t1,<large_unsigned>", "Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors."),
    ("ll $t1,<unsigned>,($t2)", "Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors."),
    #("ll $t1,<large_unsigned>,($t2)", "Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors."),
    ("ll $t1,<label>", "Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors."),
    ("ll $t1,<label>,($t2)", "Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors."),
    ("ll $t1,<label>,<large_unsigned>", "Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors."),
    ("ll $t1,<label>,<large_unsigned>,($t2)", "Load Linked : Paired with Store Conditional (sc) to perform atomic read-modify-write.  Treated as equivalent to Load Word (lw) because MARS does not simulate multiple processors."),

    ("sc $t1,($t2)", "Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors."),
    ("sc $t1,<signed>", "Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors."),
    #("sc $t1,<unsigned>", "Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors."),
    #("sc $t1,<large_unsigned>", "Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors."),
    ("sc $t1,<unsigned>,($t2)", "Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors."),
    #("sc $t1,<large_unsigned>,($t2)", "Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors."),
    ("sc $t1,<label>", "Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors."),
    ("sc $t1,<label>,($t2)", "Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors."),
    ("sc $t1,<label>,<large_unsigned>", "Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors."),
    ("sc $t1,<label>,<large_unsigned>,($t2)", "Store Conditional : Paired with Load Linked (ll) to perform atomic read-modify-write.  Treated as equivalent to Store Word (sw) because MARS does not simulate multiple processors."),

    ("ulw $t1,<signed>,($t2)", "Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address"),
    ("ulh $t1,<signed>,($t2)", "Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address"),
    ("ulhu $t1,<signed>,($t2)", "Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address"),
    ("ld $t1,<signed>,($t2)", "Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory byte address"),
    ("usw $t1,<signed>,($t2)", "Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address"),
    ("ush $t1,<signed>,($t2)", "Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address"),
    ("sd $t1,<signed>,($t2)", "Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory byte address"),

    #("ulw $t1,<large_unsigned>", "Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address"),
    ("ulw $t1,<label>", "Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address"),
    ("ulw $t1,<label>,<large_unsigned>", "Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address"),
    ("ulw $t1,($t2)", "Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address"),
    #("ulw $t1,<large_unsigned>,($t2)", "Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address"),
    ("ulw $t1,<label>,($t2)", "Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address"),
    ("ulw $t1,<label>,<large_unsigned>,($t2)", "Unaligned Load Word : Set $t1 to the 32 bits starting at effective memory byte address"),

    #("ulh $t1,<large_unsigned>", "Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address"),
    ("ulh $t1,<label>", "Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address"),
    ("ulh $t1,<label>,<large_unsigned>", "Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address"),
    ("ulh $t1,($t2)", "Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address"),
    #("ulh $t1,<large_unsigned>,($t2)", "Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address"),
    ("ulh $t1,<label>,($t2)", "Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address"),
    ("ulh $t1,<label>,<large_unsigned>,($t2)", "Unaligned Load Halfword : Set $t1 to the 16 bits, sign-extended, starting at effective memory byte address"),

    #("ulhu $t1,<large_unsigned>", "Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address"),
    ("ulhu $t1,<label>", "Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address"),
    ("ulhu $t1,<label>,<large_unsigned>", "Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address"),
    ("ulhu $t1,($t2)", "Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address"),
    #("ulhu $t1,<large_unsigned>,($t2)", "Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address"),
    ("ulhu $t1,<label>,($t2)", "Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address"),
    ("ulhu $t1,<label>,<large_unsigned>,($t2)", "Unaligned Load Halfword : Set $t1 to the 16 bits, zero-extended, starting at effective memory byte address"),

    ("ld $t1,<large_unsigned>", "Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address"),
    ("ld $t1,<label>", "Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address"),
    ("ld $t1,<label>,<large_unsigned>", "Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address"),
    ("ld $t1,($t2)", "Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address"),
    #("ld $t1,<large_unsigned>,($t2)", "Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address"),
    ("ld $t1,<label>,($t2)", "Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address"),
    ("ld $t1,<label>,<large_unsigned>,($t2)", "Load Doubleword : Set $t1 and the next register to the 64 bits starting at effective memory word address"),

    ("usw $t1,<large_unsigned>", "Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address"),
    ("usw $t1,<label>", "Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address"),
    ("usw $t1,<label>,<large_unsigned>", "Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address"),
    ("usw $t1,($t2)", "Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address"),
    #("usw $t1,<large_unsigned>,($t2)", "Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address"),
    ("usw $t1,<label>,($t2)", "Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address"),
    ("usw $t1,<label>,<large_unsigned>,($t2)", "Unaligned Store Word : Store $t1 contents into the 32 bits starting at effective memory byte address"),

    ("ush $t1,<large_unsigned>", "Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address"),
    ("ush $t1,<label>", "Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address"),
    ("ush $t1,<label>,<large_unsigned>", "Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address"),
    ("ush $t1,($t2)", "Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address"),
    #("ush $t1,<large_unsigned>,($t2)", "Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address"),
    ("ush $t1,<label>,($t2)", "Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address"),
    ("ush $t1,<label>,<large_unsigned>,($t2)", "Unaligned Store Halfword: Store low-order halfword $t1 contents into the 16 bits starting at effective memory byte address"),

    ("sd $t1,<large_unsigned>", "Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address"),
    ("sd $t1,<label>", "Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address"),
    ("sd $t1,<label>,<large_unsigned>", "Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address"),
    ("sd $t1,($t2)", "Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address"),
    #("sd $t1,<large_unsigned>,($t2)", "Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address"),
    ("sd $t1,<label>,($t2)", "Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address"),
    ("sd $t1,<label>,<large_unsigned>,($t2)", "Store Doubleword : Store contents of $t1 and the next register to the 64 bits starting at effective memory word address"),

    ("lwc1 $fpf1,($t2)", "Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address"),
    ("lwc1 $fpf1,<signed>", "Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address"),
    #("lwc1 $fpf1,<large_unsigned>", "Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address"),
    ("lwc1 $fpf1,<large_unsigned>,($t2)", "Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address"),
    ("lwc1 $fpf1,<label>", "Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address"),
    ("lwc1 $fpf1,<label>,($t2)", "Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address"),
    ("lwc1 $fpf1,<label>,<large_unsigned>", "Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address"),
    ("lwc1 $fpf1,<label>,<large_unsigned>,($t2)", "Load Word Coprocessor 1 : Set $fpf1 to 32-bit value from effective memory word address"),

    ("ldc1 $fpf2,($t2)", "Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("ldc1 $fpf2,<signed>", "Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    #("ldc1 $fpf2,<large_unsigned>", "Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("ldc1 $fpf2,<large_unsigned>,($t2)", "Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("ldc1 $fpf2,<label>", "Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("ldc1 $fpf2,<label>,($t2)", "Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("ldc1 $fpf2,<label>,<large_unsigned>", "Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("ldc1 $fpf2,<label>,<large_unsigned>,($t2)", "Load Doubleword Coprocessor 1 : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),

    ("swc1 $fpf1,($t2)", "Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address"),
    ("swc1 $fpf1,<signed>", "Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address"),
    #("swc1 $fpf1,<large_unsigned>", "Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address"),
    ("swc1 $fpf1,<large_unsigned>,($t2)", "Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address"),
    ("swc1 $fpf1,<label>", "Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address"),
    ("swc1 $fpf1,<label>,($t2)", "Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address"),
    ("swc1 $fpf1,<label>,<large_unsigned>", "Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address"),
    ("swc1 $fpf1,<label>,<large_unsigned>,($t2)", "Store Word Coprocessor 1 : Store 32-bit value from $fpf1 to effective memory word address"),

    ("sdc1 $fpf2,($t2)", "Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("sdc1 $fpf2,<signed>", "Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    #("sdc1 $fpf2,<large_unsigned>", "Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("sdc1 $fpf2,<large_unsigned>,($t2)", "Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("sdc1 $fpf2,<label>", "Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("sdc1 $fpf2,<label>,($t2)", "Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("sdc1 $fpf2,<label>,<large_unsigned>", "Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("sdc1 $fpf2,<label>,<large_unsigned>,($t2)", "Store Doubleword Coprocessor 1 : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),

    ("l.s $fpf1,($t2)", "Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address"),
    ("l.s $fpf1,<signed>", "Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address"),
    #("l.s $fpf1,<large_unsigned>", "Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address"),
    ("l.s $fpf1,<large_unsigned>,($t2)", "Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address"),
    ("l.s $fpf1,<label>", "Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address"),
    ("l.s $fpf1,<label>,($t2)", "Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address"),
    ("l.s $fpf1,<label>,<large_unsigned>", "Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address"),
    ("l.s $fpf1,<label>,<large_unsigned>,($t2)", "Load floating point Single precision : Set $fpf1 to 32-bit value at effective memory word address"),

    ("s.s $fpf1,($t2)", "Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address"),
    ("s.s $fpf1,<signed>", "Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address"),
    #("s.s $fpf1,<large_unsigned>", "Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address"),
    ("s.s $fpf1,<large_unsigned>,($t2)", "Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address"),
    ("s.s $fpf1,<label>", "Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address"),
    ("s.s $fpf1,<label>,($t2)", "Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address"),
    ("s.s $fpf1,<label>,<large_unsigned>", "Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address"),
    ("s.s $fpf1,<label>,<large_unsigned>,($t2)", "Store floating point Single precision : Store 32-bit value from $fpf1 to effective memory word address"),

    ("li.d $fpf2,<dbl>", "Load <dbl> into floating point register $fpf2"),
    ("l.d $fpf2,($t2)", "Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("l.d $fpf2,<signed>", "Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    #("l.d $fpf2,<large_unsigned>", "Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("l.d $fpf2,<large_unsigned>,($t2)", "Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("l.d $fpf2,<label>", "Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("l.d $fpf2,<label>,($t2)", "Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("l.d $fpf2,<label>,<large_unsigned>", "Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),
    ("l.d $fpf2,<label>,<large_unsigned>,($t2)", "Load floating point Double precision : Set $fpf2 and $fpf3 register pair to 64-bit value at effective memory doubleword address"),

    ("s.d $fpf2,($t2)", "Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("s.d $fpf2,<signed>", "Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    #("s.d $fpf2,<large_unsigned>", "Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("s.d $fpf2,<large_unsigned>,($t2)", "Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("s.d $fpf2,<label>", "Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("s.d $fpf2,<label>,($t2)", "Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("s.d $fpf2,<label>,<large_unsigned>", "Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address"),
    ("s.d $fpf2,<label>,<large_unsigned>,($t2)", "Store floating point Double precision : Store 64 bits from $fpf2 and $fpf3 register pair to effective memory doubleword address")
]


def parse_syntax(syntax):
    try:
        opcode, args = syntax.split()
    except ValueError:
        # something like "syscall" with no args
        return (syntax, [])

    args = args.split(",")
    return (opcode, args)

def friendly_arg(arg):
    # 4 possibilities. It can be an integer register, a floating point
    # register, an immediate, or a label.
    if arg.startswith("$fp"):
        return ("FpRegs", arg[3:])
    if arg.startswith("$"):
        return ("Regs", arg[1:])
    if arg.startswith("($"):
        assert arg.endswith(")")
        return ("Regs", arg[2:-1])
    if arg == "<signed>" or arg == "<unsigned>" or arg == "<offset>" or arg == "<numbits>" or arg == "<large_unsigned>":
        return ("int", arg[1:-1])
    if arg == "<label>":
        return ("String", arg[1:-1])
    if arg == "<dbl>": 
    	return ("double", arg[1:-1])
    print arg
    assert False


def java_fn_signature(syntax):
    (opcode, args) = syntax
    opcode = opcode.replace(".", "_")
    argstr = ", ".join( (type + " " + name) for (type, name) in map(friendly_arg, args))
    return "public Instruction %s (%s)" % (opcode, argstr)

def java_fn_body(syntax):
    (opcode, args) = syntax
    found_index = False
    for arg in args:
        if arg.startswith("($"):
            assert arg.endswith(")")
            found_index = True

    def make_arg(arg):
        (type, name) = arg
        if type == "FpRegs" or type == "Regs":
            return '"$"+%s' % name
        elif found_index:
            # then we don't want to stringize it
            return name
        else:
            return '""+%s' % name

    args_to_instruction_constructor = ", ".join(['"%s"' % opcode] + list(make_arg(arg) for arg in map(friendly_arg, args)))


    instruction_type = "NonMemoryInstruction"
    if found_index:
        instruction_type = "MemoryAccessInstruction"
    
    return "return new %s(%s);" % (instruction_type, args_to_instruction_constructor)


def java_fn_def(instruction, help):
    signature = java_fn_signature(instruction)
    body = java_fn_body(instruction)

    return "    /** %s */ \n    %s { %s }\n" % (help, signature, body)


with open("AutogenMips.java", "w") as f:
    f.write("""package cs536.codegen.mips;

import cs536.codegen.Instruction;
import cs536.codegen.mips.NonMemoryInstruction;

public class AutogenMips
{
""")


    for (syntax, help) in instructions:
        f.write(java_fn_def(parse_syntax(syntax), help))

    f.write("}\n")


with open("AutogenMipsWithPseudo.java", "w") as f:
    f.write("""package cs536.codegen.mips;

import cs536.codegen.Instruction;
import cs536.codegen.mips.NonMemoryInstruction;

public class AutogenMipsWithPseudo extends AutogenMips
{
""")


    for (syntax, help) in pseudo_instructions:
        f.write(java_fn_def(parse_syntax(syntax), help))

    f.write("}\n")



#"mfc0 $dest,$8",  "Move from Coprocessor 0 : Set $dest to the value stored in Coprocessor 0 register $8"
#"mtc0 $t1,$8",  "Move to Coprocessor 0 : Set Coprocessor 0 register $8 to value stored in $t1"
#"bc1t 1,<label>", "Branch if specified FP condition flag true (BC1T, not BCLT) : If Coprocessor 1 condition flag specified by immediate is true (one) then branch to statement at <label>'s address"
#"bc1f 1,<label>", "Branch if specified FP condition flag false (BC1F, not BCLF) : If Coprocessor 1 condition flag specified by immediate is false (zero) then branch to statement at <label>'s address"
#"c.eq.s 1,$fpleft,$fpright", "Compare equal single precision : If $fpleft is equal to $fpright, set Coprocessor 1 condition flag specied by immediate to true else set it to false"
#"c.le.s 1,$fpleft,$fpright", "Compare less or equal single precision : If $fpleft is less than or equal to $fpright, set Coprocessor 1 condition flag specified by immediate to true else set it to false"
#"c.lt.s 1,$fpleft,$fpright", "Compare less than single precision : If $fpleft is less than $fpright, set Coprocessor 1 condition flag specified by immediate to true else set it to false"
#"c.eq.d 1,$fpleft,$fpright", "Compare equal double precision : If $fpleft is equal to $fpright (double-precision), set Coprocessor 1 condition flag specified by immediate to true else set it to false"
#"c.le.d 1,$fpleft,$fpright", "Compare less or equal double precision : If $fpleft is less than or equal to $fpright (double-precision), set Coprocessor 1 condition flag specfied by immediate true else set it false"
#"c.lt.d 1,$fpleft,$fpright", "Compare less than double precision : If $fpleft is less than $fpright (double-precision), set Coprocessor 1 condition flag specified by immediate to true else set it to false"
