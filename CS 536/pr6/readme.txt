There's a bit of helpful stuff you should be aware of:

* Take a look in the packages 'cs536.codegen' and 'cs536.codegen.mips'.
  In there is:

  * LabelGenerator, which will continually feed you new labels. When
    you create one, you give it a prefix which will appear in the
    labels (e.g. ".Lelse_2"). If you think it'd help you follow your
    code, you can create multiple of them for different purposes
    (e.g. one for labels associated with an else condition, one for
    the tops of loops, etc.)

  * I suggest writing your visitor so the type is an
    'MipsChunk'. Basically this is a thin wrapper around a list of
    'Instruction's. There are two main kinds of instructions in the
    mips subpackage: NonMemoryInstruction and
    MemoryAccessInstruction. There are also "instruction" classes for
    labels, assembler directives (like .text), and wrapping any of the
    above with a comment.

    So if you go this route, what will happen is this. When a node's
    children get translated and your visit function is called, you'll
    get an AsmChunk back from them. You munge those together along
    with any new stuff you need and produce the translation of the
    node in question, then return the result.

    When you're putting them together, you'll have two main tasks:
    pull in the code you got from the child nodes, then add new
    stuff. The former can be done with the 'appendChunk' and
    'appendChunks' methods in AsmChunk. The latter can be done by
    creating a new instruction of the appropriate type (e.g. a new
    NonMemoryInstruction) and appending it to the chunk with 'append'.

    You don't have to use these, but I like this sort of support.

  * To make that last task a little nicer, I've given you a helper
    class called 'Mips' (in 'cs536.codegen.mips'). Spread across
    'Mips', 'AutogenMipsWithPseudo', and 'AutogenMips' are functions
    for generating all MIPS instructions (and all the
    pseudoinstructions MARS supports -- see below), almost all
    assembler directives, etc.

    There are also two enum classes, 'Regs' and 'FpRegs', which define
    'Regs.t0', 'Regs.t1', 'FpRegs.f0', etc. for all general purpose
    and floating point MIPS registers.

    So instead of saying 'System.out.println("lw $t0, 0($sp)")' or
    something (or likely worse since you'd have to be able to set the
    offset from a variable), you'd say 'mips.lw(Regs.t0, 0, Regs.sp)'.

    This gives you a little more checking from the Java compiler
    (e.g. it will make sure you don't give 'add' a floating point
    register, which would otherwise not be caught until you tried to
    run the output in SPIM). But it's especially nice if you work in
    an IDE, because then you can use its autocompletion & context help
    features as a reminder as to what the different instructions do.

    ** IMPORTANT **

    The functions in those classes are based on a list of instructions
    from the MARS simulator. (They give you source (as does SPIM) and
    I could get the list of instructions and it wasn't *too* painful.)
    In fact, the Javadoc comments are swiped directly from them.
    However, note that we'll be doing final grading using SPIM, which
    I don't guarantee will support exactly the same
    pseudo-instructions. I wouldn't expect any trouble, but you never
    know. Make sure to test on SPIM.

    (BTW, pulling from MARS is where the parameter names came from for
    the pseudo-instruction functions. I spent a couple hours renaming
    parameters for the non-pseudo-instructions so they're nice and say
    things like '$dest' or '$src' or '$left', and there were way too
    many pseudo-instructions. So they are named by example.)

  * Final point on this particular topic is that the MipsChunk class
    also contains functions for generating pushes and pops. (Just for
    integer registers. You'll probably find it useful to add
    corresponding functions for the floating point registers if you do
    that extension.)

    So some instructions you'll get with something like:
      chunk.append( mips.add(Regs.t0, Regs.t1, Regs.t2) );
    while a push/pop doesn't go through the 'mips' object:
      chunk.pushReg( Regs.t0 );
    The asymmetry isn't great.

* At 'cs536.codegen.mips.CodeGenerator' is a skeleton code
  generator. It's got the types filled in as if you'll take my
  suggestion and use MipsChunk. The 'visitAst()' method has code in
  it, but it's just to show how you use the classes mentioned above.

* My offset calculator visitor is at
  'cs536.codegen.OffsetCalculator'. I think I didn't put a call to it
  into the driver class for this project, so you'll have to do that. I
  did retrofit it into the Project 4 driver and changed the unparser
  so it prints out offsets on variable declarations, in case you want
  a reference of what they should be.

  If you are using your code you should be able to take that visitor
  and plop it right in with relatively little modification. (And if
  not, it took very little time to write.)

* When generating code for functions, you'll need to know the total
  size of local variabes and parameters. If only the FunctionSymbol
  stored that information. (It doesn't, right now.) I wonder if you
  might be able to store it at some point... say, when you're
  basically adding up sizes of variables for some other purpose. Hmm.
