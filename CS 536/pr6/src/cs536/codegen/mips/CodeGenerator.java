package cs536.codegen.mips;

import java.util.List;
import java.util.Iterator;

import cs536.ast.SourceLocation;
import cs536.ast.AstVisitor;
import static cs536.ast.AbstractSyntaxTree.*;
import cs536.ast.AbstractSyntaxTree;

import cs536.codegen.*;
import cs536.staticSemantics.types.*;
import cs536.staticSemantics.symbolTable.*;
import java.util.ArrayList;

public class CodeGenerator extends AstVisitor<MipsChunk>
{
    Mips mips = new Mips();
	int[] freeIntRegs = new int[8];
	int[] freeDoubleRegs = new int[14];
    
	LabelGenerator whileLabelGen = new LabelGenerator("while");
	LabelGenerator endWhileLabelGen = new LabelGenerator("endWhile");
	LabelGenerator elseLabelGen = new LabelGenerator("else");
	LabelGenerator endIfLabelGen = new LabelGenerator("endIf");
	LabelGenerator condLabelGen	= new LabelGenerator("cond");
	LabelGenerator doubleLabelGen	= new LabelGenerator("double");
        LabelGenerator readBoolLabelGen	= new LabelGenerator("read_bool");
        LabelGenerator stringLabelGen	= new LabelGenerator("string");

	FuncDecl main = null;

    public MipsChunk visitAst(AbstractSyntaxTree ast, List<MipsChunk> decls)
    {
        // This is just a demo of how to use the Mips and MipsChunk classes,
        // as well as the LabelGenerator.

        MipsChunk chunk = new MipsChunk();

		Iterator<MipsChunk> it = decls.iterator();
		while(it.hasNext())
		{
			MipsChunk next = it.next();
			if(next != null)
				chunk.appendChunk(next);
		}
		
		if(main == null)
			System.out.println("0:0:no main function");
		else if(main.getFormalArguments().size() != 0)
			System.out.println(main.getLocation() + ":main must take no arguments");
		else if(main.getReturnTypeNode() != null)
			System.out.println(main.getLocation() + ":main must return void");
		else
			return chunk;
		
		return new MipsChunk();
    }

    private List<GlobalVarDecl> globals = new ArrayList<GlobalVarDecl>();
    public MipsChunk visitGlobalVarDecl(GlobalVarDecl globalVar,
                                      MipsChunk typeResult, MipsChunk exprResult)
    {
        globals.add(globalVar);
		MipsChunk chunk = new MipsChunk();
		// Create the global var decl
		chunk.append(mips.newline());
		chunk.append(mips.comment("Global Var Decl: " + globalVar.getName()));
		chunk.append(mips._data());
		chunk.append(mips._align(2));
		chunk.append(mips.label("_" + globalVar.getName()));
		chunk.append(mips._space(globalVar.getTypeNode().asSemanticType().getSize()));
	
		if(typeResult != null)
			chunk.appendChunk(typeResult);


		// Check if there is an initialization
		if(exprResult != null)
                {
                    chunk.append(mips._text());
                    chunk.append(mips.label("init_" + globalVar.getName()));
                    chunk.appendChunk(exprResult);
                    if(globalVar.getTypeNode().asSemanticType() != DoubleType.make())
                    {
                    	chunk.append(mips.sw(globalVar.getInitializer().getRegs(), "_" + globalVar.getName()), "Global initializer");
                    	freeIntReg(globalVar.getInitializer().getRegs());
                    }
                    else
                    {
                    	chunk.append(mips.s_d(globalVar.getInitializer().getFpRegs(), "_" + globalVar.getName()), "Global initializer");
                    	freeDoubleReg(globalVar.getInitializer().getFpRegs());
                    }
                    
                    // Jump back to main function
                    chunk.append(mips.j("end_init_" + globalVar.getName()), "Return from init");
                    
                }

		return chunk;
    }

    private FuncDecl currentFunction;
    private int currLocalStackOffset;
    private int currParamStackOffset;
    public void preVisit(FuncDecl funcDecl)
    {
	currentFunction = funcDecl;
        currLocalStackOffset = 0;
        currParamStackOffset = currentFunction.localSize + 4 + currentFunction.paramSize;
    }

    public MipsChunk visitFuncDecl(FuncDecl funcDecl, MipsChunk typeResult,
                                 List<MipsChunk> formalResults, List<MipsChunk> statementResults)
    {
		MipsChunk chunk = new MipsChunk();

		// Function Declaration
		String name = funcDecl.getName();
		chunk.append(mips.newline());
		chunk.append(mips.comment("#################################################"));
		chunk.append(mips.comment(" Function Declaration: " + name));
		chunk.append(mips.comment("#################################################"));
		chunk.append(mips._text());
		if(name.equals("main"))
		{
			chunk.append(mips._globl(name));
			chunk.append(mips.label(name));
			main = funcDecl;

                        // Init all the global variables
                        chunk.append(mips.newline());
                       for(GlobalVarDecl g : globals)
                       {
                            if(g.hasInitializer())
                            {
                                chunk.append(mips.j("init_" + g.getName()));
                                chunk.append(mips.label("end_init_" + g.getName()));
                           }
                       }
		}
		else
		{
			chunk.append(mips.label("_" + name));
		}

		// Setup stack
		chunk.append(mips.comment("Setup Function Stack"));
		// (1) push return address
                chunk.pushReg(Regs.ra, "push return address");
		// (2) "push" space for locals
		chunk.append(mips.subu(Regs.sp, Regs.sp, funcDecl.localSize), "Push space for locals");
		// (3) push control link
		chunk.pushReg(Regs.fp, "push frame pinter");
		// (4) set the FP
		chunk.append(mips.addu(Regs.fp, Regs.sp, 4), "set the FP");

                // Push registers
                chunk.append(mips.newline());
                chunk.pushReg(Regs.t0);
                chunk.pushReg(Regs.t1);
                chunk.pushReg(Regs.t2);
                chunk.pushReg(Regs.t3);
                chunk.pushReg(Regs.t4);
                chunk.pushReg(Regs.t5);
                chunk.pushReg(Regs.t6);
                chunk.pushReg(Regs.t7);

		// Function statments 
		Iterator<MipsChunk> it = statementResults.iterator();
		while(it.hasNext())
		{
                    chunk.append(mips.comment("formal decl"));
                    MipsChunk next = it.next();
                    chunk.appendChunk(next);
		}
	
		// Function Exit
		chunk.append(mips.newline());
		chunk.append(mips.comment("Return From Function"));
                chunk.append(mips.label("return_" + name));

                // Pop registers
                chunk.popToReg(Regs.t7);
                chunk.popToReg(Regs.t6);
                chunk.popToReg(Regs.t5);
                chunk.popToReg(Regs.t4);
                chunk.popToReg(Regs.t3);
                chunk.popToReg(Regs.t2);
                chunk.popToReg(Regs.t1);
                chunk.popToReg(Regs.t0);

		chunk.popToReg(Regs.fp); // "pop and restore frame pointer"
                chunk.append(mips.add(Regs.sp, Regs.sp, funcDecl.localSize), "\"Pop\" local variables");
                chunk.popToReg(Regs.ra); // Pop the return address
                // We do caller parameter cleanup
		chunk.append(mips.jr(Regs.ra), "return");
		return chunk;
    }

    public MipsChunk visitFormalArg(FormalArg formalArg, MipsChunk typeResult)
    {
    	MipsChunk chunk = new MipsChunk();
	Type t = formalArg.getTypeNode().asSemanticType();
        currParamStackOffset -= t.getSize();

        formalArg.getLocalSymbol().setOffset(currParamStackOffset);
	return chunk;
    }

    public MipsChunk visitVarDeclStmt(VarDeclStmt localVar,
                                    MipsChunk typeResult, MipsChunk initResult)
    {
        MipsChunk chunk = new MipsChunk();
        chunk.append(mips.newline());
        chunk.append(mips.comment("Local Var Declaration: " + localVar.getName()));

        // Update Stack offset for locals
        localVar.getLocalSymbol().setOffset(currLocalStackOffset);
        Type t = localVar.getTypeNode().asSemanticType();
        currLocalStackOffset += t.getSize();

        if(typeResult != null)
        {
            chunk.appendChunk(typeResult);
        }

        if(initResult != null)
        {
            chunk.appendChunk(initResult);
            if (localVar.getInitializer().getType() == IntType.make() ||
                localVar.getInitializer().getType() == BoolType.make())
            {
                    freeIntReg(localVar.getInitializer().getRegs());
                    // Place the value on the stack
                    chunk.append(mips.sw(localVar.getInitializer().getRegs(), localVar.getLocalSymbol().getOffset(), Regs.fp), "Store init value");
            }
            else
            {
                freeDoubleReg(localVar.getInitializer().getFpRegs());
                // Place the value on the stack
                chunk.append(mips.s_d(localVar.getInitializer().getFpRegs(), localVar.getLocalSymbol().getOffset(), Regs.fp), "Store init value");
            }
        }
        return chunk;
    }

    public MipsChunk visitReturnStmt(ReturnStmt retStmt, MipsChunk valResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.append(mips.newline());
		chunk.append(mips.comment("Return Statement"));
		
		if(valResult != null)
			chunk.appendChunk(valResult);
		
		if(retStmt.hasExpression())
		{
			Expression exp = retStmt.getExpression();
			if (exp.getType() == IntType.make() || exp.getType() == BoolType.make())
			{
				Regs r = exp.getRegs();
				chunk.append(mips.move(Regs.v0, r), "Move return into v0");
			}
			else if (exp.getType() == DoubleType.make())
			{
				FpRegs r = exp.getFpRegs();
				chunk.append(mips.mov_d(FpRegs.f0, r), "Move return into f0");
			}
		}
		if (retStmt.hasExpression())
		{
			if (retStmt.getExpression().getType() == IntType.make() || retStmt.getExpression().getType() == BoolType.make())
				freeIntReg(retStmt.getExpression().getRegs());
		}

                // Jump to the function return
                chunk.append(mips.b("return_" + currentFunction.getName()));
		return chunk;
    }

    public MipsChunk visitIfStmt(IfStmt ifStmt, MipsChunk conditionResult,
                               List<MipsChunk> thenResults, List<MipsChunk> elseResults)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.append(mips.comment("If Statement"));
		/*
		Evaluate the condition, leaving the value on the stack.
		Pop the top-of-stack value into register $t0.
		Jump to a FalseLabel if $t0 is false
		(Code for the statement list for the true body)
		Jump to EndOfIf
		ElseLabel:
		(Code for the statement list of the false body)
		EndOfIf:
		*/
		String falseLabel = elseLabelGen.genLabel();
		String endOfIfLabel = endIfLabelGen.genLabel();
		//IF STATEMENT
		chunk.appendChunk(conditionResult);
		//Branch over if false...
		chunk.append(mips.beqz(ifStmt.getCondition().getRegs(), falseLabel));
		//TRUE
		for(MipsChunk mc : thenResults)
			chunk.appendChunk(mc);
		//branch over false
		chunk.append(mips.b(endOfIfLabel));
		//FALSE
		//ElseLabel:
		chunk.append(mips.label(falseLabel));
		for(MipsChunk mc : elseResults)
			chunk.appendChunk(mc);
		//EndOfIf:
		chunk.append(mips.label(endOfIfLabel));
		freeIntReg(ifStmt.getCondition().getRegs());
        return chunk;
    }

    public MipsChunk visitWhileStmt(WhileStmt whileStmt, MipsChunk conditionResult,
                                  List<MipsChunk> bodyResults)
    {
        MipsChunk chunk = new MipsChunk();
		chunk.append(mips.comment(whileStmt.toString()));
		
		String whileLabel = whileLabelGen.genLabel();
		String endWhileLabel = endWhileLabelGen.genLabel();

		/*
		While:
		//CONDITION CHUNK
		branch to endwhile if condition is false
		//WHILE CODE
		branch to While
		EndWhile:
		*/
		
		chunk.append(mips.label(whileLabel));
		chunk.appendChunk(conditionResult);
		chunk.append(mips.beqz(whileStmt.getCondition().getRegs(), endWhileLabel));
		for(MipsChunk mc : bodyResults)
			chunk.appendChunk(mc);
		chunk.append(mips.b(whileLabel));
		chunk.append(mips.label(endWhileLabel));

		freeIntReg(whileStmt.getCondition().getRegs());
        return chunk;
    }

    public MipsChunk visitExpressionStmt(ExpressionStmt exprStmt, MipsChunk expResult)
    {
		//if (exprStmt.getExpression().getType() != DoubleType.make())
		freeIntReg(exprStmt.getExpression().getRegs());
		freeDoubleReg(exprStmt.getExpression().getFpRegs());
			
        return expResult;
    }


    public MipsChunk visitWriteStmt(WriteStmt writeStmt, MipsChunk valResult)
    {
    	MipsChunk chunk = new MipsChunk();
        chunk.append(mips.newline());
        chunk.append(mips.comment("Write Statement"));

        if(valResult != null)
                chunk.appendChunk(valResult);

        /*
        print integer	1	$a0 = integer to print	 
		print float		2	$f12 = float to print	 
		print double	3	$f12 = double to print	 
		print string	4	$a0 = address of null-terminated string to print	
        */
        
        if(writeStmt.getExpression().getType() == StringType.make()){
	        Regs r = writeStmt.getExpression().getRegs();
        	chunk.append(mips.move(Regs.a0, r), "Copy expression result into special register");
	        chunk.append(mips.li(Regs.v0, 4), "Set Service number to print integer");
	        chunk.append(mips.syscall());
	        freeIntReg(r);
	        return chunk;
        }
        else if (writeStmt.getExpression().getType() == DoubleType.make()){
        	FpRegs r = writeStmt.getExpression().getFpRegs();
        	chunk.append(mips.mov_d(FpRegs.f12, r), "Copy expression result into special register");
	        chunk.append(mips.li(Regs.v0, 3), "Set Service number to print integer");
	        chunk.append(mips.syscall());
	        freeDoubleReg(r);
	        return chunk;
        }
        else {
	        Regs r = writeStmt.getExpression().getRegs();
	        chunk.append(mips.move(Regs.a0, r), "Copy expression result into special register");
	        chunk.append(mips.li(Regs.v0, 1), "Set Service number to print integer");
	        chunk.append(mips.syscall());
	        freeIntReg(r);
	        return chunk;
        }
    }

    public MipsChunk visitReadStmt(ReadStmt readStmt)
    {
        MipsChunk chunk = new MipsChunk();
		
		chunk.append(mips.newline());
		chunk.append(mips.comment("Read Statement"));
                Type t = readStmt.getDestinationSymbol().getType();
                if(t == IntType.make() )
                    chunk.append(mips.li(Regs.v0, 5), "this reads an integer");
                else if(t == DoubleType.make())
                    chunk.append(mips.li(Regs.v0, 7), "this reads an Double");
                else if(t == BoolType.make() || t == StringType.make())
                {
                    chunk.append(mips.li(Regs.a0, 105), "set allocate size");
                    chunk.append(mips.li(Regs.v0, 9), "Allocate the memory");
                    chunk.append(mips.syscall());
                    chunk.append(mips.move(Regs.a0, Regs.v0), "copy memory");
                    chunk.append(mips.li(Regs.a1, 105), "set read size");
                    chunk.append(mips.li(Regs.v0, 8), "this reads an string");
                }
                
		chunk.append(mips.syscall());
                Symbol s = readStmt.getDestinationSymbol();


                // If bool  check for string "true"

                if(t == BoolType.make())
                {
                    Regs r1 = nextFreeIntReg();
                    Regs r2 = nextFreeIntReg();
                    String label1 = readBoolLabelGen.genLabel();
                    String label2 = readBoolLabelGen.genLabel();

                    

                    chunk.append(mips.li(r1, 0), "Init to false");
                    chunk.append(mips.lw(r2, Regs.a0));

                    chunk.append(mips.bne(r2, 2609, label1), "Check if 1");
                    chunk.append(mips.li(r1, 1));
                    chunk.append(mips.label(label1));

                    chunk.append(mips.bne(r2, 1702195828, label2), "compare to true");
                    chunk.append(mips.li(r1, 1));
                    chunk.append(mips.label(label2));

                    if(s instanceof LocalSymbol)
                      chunk.append(mips.sw(r1, ((LocalSymbol)s).getOffset(), Regs.fp), "Store local var");
                    else if(s instanceof GlobalSymbol)
                      chunk.append(mips.sw(r1, "_" + s.getName()), "Store global Var");
                    freeIntReg(r1);
                }
                else if(t == StringType.make())
                {
                    if(s instanceof LocalSymbol)
                        chunk.append(mips.sw(Regs.a0, ((LocalSymbol)s).getOffset(), Regs.fp), "Store local var");
                    else if(s instanceof GlobalSymbol)
                        chunk.append(mips.sw(Regs.a0, "_" + s.getName()), "Store global Var");
                }
                else
                {
                  if(s instanceof LocalSymbol)
                    chunk.append(mips.sw(Regs.v0, ((LocalSymbol)s).getOffset(), Regs.fp), "Store local var");
                  else if(s instanceof GlobalSymbol)
                    chunk.append(mips.sw(Regs.v0, "_" + s.getName()), "Store global Var");
                }


		//chunk.pushToReg(Regs.a0);
		return chunk;
    }

    public MipsChunk visitFunctionCallExp(FunctionCallExp fcallExp,
                                        List<MipsChunk> argResults)
    {
        MipsChunk chunk = new MipsChunk();
		chunk.append(mips.newline());
		chunk.append(mips.comment("Function Call: " + fcallExp.getName()));
		
		// Push all the arguments
		Iterator<MipsChunk> it = argResults.iterator();
		Iterator<Expression> it_args = fcallExp.getActualIterator();
                int parametersSize = 0;
		while(it.hasNext() && it_args.hasNext())
		{
			MipsChunk next = it.next();
			Expression exp = it_args.next();
                        chunk.appendChunk(next);
                        chunk.pushReg(exp.getRegs(), "Place the Argument on the stack");
                        parametersSize += exp.getType().getSize();
                        freeIntReg(exp.getRegs());
		}

		// jump to the address of the function
		chunk.append(mips.jal("_" + fcallExp.getName()));

                // Fix the stack offset from parameters
                if(parametersSize != 0)
                    chunk.append(mips.add(Regs.sp, Regs.sp, parametersSize), "Fix Stack from parameters");

                // Copy the return value into a register
                Regs r = nextFreeIntReg();
		fcallExp.setRegs(r);
                chunk.append(mips.move(r, Regs.v0));
		return chunk;
    }

    public MipsChunk visitDerefOp(DerefOp derefOp, MipsChunk expResult)
    {
        MipsChunk chunk = new MipsChunk();
        Regs r = derefOp.getOperand().getRegs();
        Symbol s = ((VarRefExp)derefOp.getOperand()).getVariableSymbol();
       
        if(s instanceof LocalSymbol)
        {
            //chunk.append(mips.lw(r, ((LocalSymbol)s).getOffset(), Regs.fp));
            chunk.appendChunk(expResult);
            chunk.append(mips.lw(r, r));
        }
        derefOp.setRegs(r);
        return chunk;
    }

    public MipsChunk visitNegationOp(NegationOp negationOp, MipsChunk expResult)
    {
		MipsChunk chunk = new MipsChunk();

                chunk.appendChunk(expResult);

		//perform negation 
		if (negationOp.getType() == IntType.make())
		{
			Regs r = negationOp.getOperand().getRegs();
			chunk.append(mips.sub(r,Regs.zero, r), "Integer Negation");
			negationOp.setRegs(r);
		}
		else if (negationOp.getType() == DoubleType.make())
		{
			FpRegs r = negationOp.getOperand().getFpRegs();
			chunk.append(mips.neg_d(FpRegs.f0, FpRegs.f0), "Double Negation");
			negationOp.setFpRegs(r);
		}
		return chunk;	
    }


    public MipsChunk visitIncrementOp(IncrementOp incrOp, MipsChunk expResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(expResult);
		//perform increment
                
		if (incrOp.getType() == IntType.make())
		{
                    Regs r1 = incrOp.getOperand().getRegs();
                    Regs r2 = nextFreeIntReg();
                    chunk.append(mips.addi(r2, r1,1));
                    
                    Symbol s = ((VarRefExp)incrOp.getOperand()).getVariableSymbol();
                    if(s instanceof LocalSymbol)
                        chunk.append(mips.sw(r2,((LocalSymbol)s).getOffset(), Regs.fp));
                    else if(s instanceof GlobalSymbol)
                        chunk.append(mips.sw(r2, "_" + s.getName()));

                    if(incrOp.isPrefix())
                    {
                        incrOp.setRegs(r2);
                        freeIntReg(r1);
                    }
                    else
                    {
                        incrOp.setRegs(r1);
                        freeIntReg(r2);
                    }
		}
		return chunk;
    }

    public MipsChunk visitDecrementOp(DecrementOp decrOp, MipsChunk expResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(expResult);
		//perform increment
		if (decrOp.getType() == IntType.make())
		{
                    Regs r1 = decrOp.getOperand().getRegs();
                    Regs r2 = nextFreeIntReg();
                    chunk.append(mips.sub(r2, r1,1));

                    Symbol s = ((VarRefExp)decrOp.getOperand()).getVariableSymbol();
                    if(s instanceof LocalSymbol)
                        chunk.append(mips.sw(r2,((LocalSymbol)s).getOffset(), Regs.fp));
                    else if(s instanceof GlobalSymbol)
                        chunk.append(mips.sw(r2, "_" + s.getName()));

                    if(decrOp.isPrefix())
                    {
                        decrOp.setRegs(r2);
                        freeIntReg(r1);
                    }
                    else
                    {
                        decrOp.setRegs(r1);
                        freeIntReg(r2);
                    }
		}
		
		return chunk;
    }



    public MipsChunk visitNotOp(NotOp logNotOp, MipsChunk expResult)
    {
      	MipsChunk chunk = new MipsChunk();

       	chunk.appendChunk(expResult);
	   	Regs r1 = logNotOp.getOperand().getRegs();
		Regs rnew = nextFreeIntReg();
		
		String condLabel = condLabelGen.genLabel();
        chunk.append(mips.li(rnew,1));  //by default set to true
        
		chunk.append(mips.beqz(r1,condLabel)); //if equal to zero, rnew should be 1, else set to 0
        chunk.append(mips.li(rnew,0));
        
		chunk.append(mips.label(condLabel));

        logNotOp.setRegs(rnew);
		freeIntReg(r1);
       //should we free anything here? 
		return chunk;
    }

    public MipsChunk visitAddrOfOp(AddrOfOp addrOfOp, MipsChunk expResult)
    {
	MipsChunk chunk = new MipsChunk();
        Regs r = ((VarRefExp)addrOfOp.getOperand()).getRegs();
        Symbol s = ((VarRefExp)addrOfOp.getOperand()).getVariableSymbol();
        if(s instanceof LocalSymbol)
            chunk.append(mips.add(r, Regs.fp, ((LocalSymbol)s).getOffset()));
        addrOfOp.setRegs(r);
        return chunk;
    }

   public MipsChunk visitAndOp(AndOp logAndOp, MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(lhsResult);
		
		Regs r1 = logAndOp.getLhsOperand().getRegs();
		Regs r2 = logAndOp.getRhsOperand().getRegs();
		
		Regs rnew = nextFreeIntReg(); //stores whether the result is true or false
		String condLabel = condLabelGen.genLabel();
		
		chunk.append(mips.li(rnew,0));	//by default set to false
		chunk.append(mips.beqz(r1,condLabel)); //check the 1st condition - if false skip all the way
		chunk.append(mips.li(rnew,1));	//equal to zero if it gets here (ie the condition is true, check other cond)
                chunk.append(mips.newline());
                
chunk.appendChunk(rhsResult);

		chunk.append(mips.bnez(r2,condLabel)); //check the 2nd condition
		chunk.append(mips.li(rnew,0));	//not equal to zero if it gets here (ie the condition is true)		
		chunk.append(mips.label(condLabel));
		
		logAndOp.setRegs(rnew);
		freeIntReg(r1);
		freeIntReg(r2);
        return chunk;
    }

    public MipsChunk visitOrOp(OrOp logOrOp, MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(lhsResult);
		
		Regs r1 = logOrOp.getLhsOperand().getRegs();
		Regs r2 = logOrOp.getRhsOperand().getRegs();
		
		Regs rnew = nextFreeIntReg(); //stores whether the result is true or false
		String condLabel = condLabelGen.genLabel();
		
		chunk.append(mips.li(rnew,1));	//by default set to true
		chunk.append(mips.bnez(r1,condLabel)); //check the 1st condition

                chunk.appendChunk(rhsResult);

		chunk.append(mips.li(rnew,0));	//equal to zero if it gets here (ie the condition is false, check other cond)
		chunk.append(mips.beqz(r2,condLabel)); //check the 2nd condition
		chunk.append(mips.li(rnew,1));	//not equal to zero if it gets here (ie the condition is true)		
		chunk.append(mips.label(condLabel));
		
		logOrOp.setRegs(rnew);
		freeIntReg(r1);
		freeIntReg(r2);
        return chunk;
    }

	public MipsChunk visitAssignOp(AssignOp assignOp, MipsChunk lhsResult, MipsChunk rhsResult)
    {
        MipsChunk chunk = new MipsChunk();
        chunk.append(mips.newline());

       // chunk.appendChunk(lhsResult);

        Regs r = assignOp.getRhsOperand().getRegs();
        FpRegs fp = assignOp.getRhsOperand().getFpRegs();
        chunk.appendChunk(rhsResult);

        //take the value of the rhs
        if(assignOp.getLhsOperand() instanceof VarRefExp)
        {
            //assign into the location pointed to by the reg of lhs
            Symbol s = ((VarRefExp)assignOp.getLhsOperand()).getVariableSymbol();
            if(s instanceof LocalSymbol)
            {
            	if (assignOp.getType() != DoubleType.make())
            		chunk.append(mips.sw(r, ((LocalSymbol)s).getOffset(), Regs.fp), "Assign");
            	else
            		chunk.append(mips.s_d(fp, ((LocalSymbol)s).getOffset(), Regs.fp), "Assign double");
            }
            else if(s instanceof GlobalSymbol)
            {
            	if (assignOp.getType() != DoubleType.make())
            		chunk.append(mips.sw(r, "_" + s.getName()));
            	else
            		chunk.append(mips.s_d(fp, "_" + s.getName()));            		
            }
        }
        else if(assignOp.getLhsOperand() instanceof DerefOp)
        {
            // Aww my knee
            Symbol s = ((VarRefExp)((DerefOp)assignOp.getLhsOperand()).getOperand()).getVariableSymbol();
            if(s instanceof LocalSymbol)
                chunk.append(mips.lw(assignOp.getLhsOperand().getRegs(), ((LocalSymbol)s).getOffset(), Regs.fp));
            else if(s instanceof GlobalSymbol)
                chunk.append(mips.lw(assignOp.getLhsOperand().getRegs(), "_" + s.getName()));
            chunk.append(mips.sw(r, assignOp.getLhsOperand().getRegs()));
        }
        assignOp.setRegs(r);
        assignOp.setFpRegs(fp);
        freeIntReg(assignOp.getLhsOperand().getRegs());
        freeDoubleReg(assignOp.getLhsOperand().getFpRegs());
        return chunk;
    }

    public MipsChunk visitEqualsOp(EqualsOp equalsOp, MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
        chunk.append(mips.comment("Condition Statement: =="));
		chunk.appendChunk(lhsResult);
		chunk.appendChunk(rhsResult);

		

		if (equalsOp.getLhsOperand().getType() != DoubleType.make() &&
                        equalsOp.getRhsOperand().getType() != DoubleType.make())
		{
			Regs r1 = equalsOp.getLhsOperand().getRegs();
			Regs r2 = equalsOp.getRhsOperand().getRegs();

			String condLabel = condLabelGen.genLabel();
			//allocate new register to store result
			Regs rnew	= nextFreeIntReg();
			// evaluate as ints
			//set to false
			chunk.append(mips.li(rnew,1));
			//check for condition
			chunk.append(mips.beq(r1,r2,condLabel));
			//if it's false it doesn't branch, so set to false
			chunk.append(mips.li(rnew,0));
			//at label, continue...
			chunk.append(mips.label(condLabel));
			freeIntReg(r1);
			freeIntReg(r2);
			equalsOp.setRegs(rnew);		
		}
		else
		{
			FpRegs r1 = equalsOp.getLhsOperand().getFpRegs();
			FpRegs r2 = equalsOp.getRhsOperand().getFpRegs();
			Regs rnew	= nextFreeIntReg();
			String condLabel = condLabelGen.genLabel();
			chunk.append(mips.comment("Condition Statement: >"));
			// evaluate as doubles
			//set to false
			chunk.append(mips.li(rnew,1));
			//check for condition
			chunk.append(mips.c_eq_d(r2, r1));
			chunk.append(mips.bc1t(condLabel));
			//if it's false it doesn't branch, so set to false
			chunk.append(mips.li(rnew,0));
			//at label, continue...
			chunk.append(mips.label(condLabel));
			freeDoubleReg(r1);
			freeDoubleReg(r2);
			equalsOp.setRegs(rnew);	
		}	
        return chunk;
    }

	public MipsChunk visitNotEqualsOp(NotEqualsOp notEqualsOp,
                                    MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(lhsResult);
		chunk.appendChunk(rhsResult);

		chunk.append(mips.comment("Condition Statement: !="));

           if (notEqualsOp.getLhsOperand().getType() != DoubleType.make() &&
               notEqualsOp.getRhsOperand().getType() != DoubleType.make())
           {
       		Regs r1 = notEqualsOp.getLhsOperand().getRegs();
    		Regs r2 = notEqualsOp.getRhsOperand().getRegs();
    		
    		//allocate new register to store result
    		Regs rnew	= nextFreeIntReg();
    		String condLabel = condLabelGen.genLabel();
			// evaluate as ints
			//set to false
			chunk.append(mips.li(rnew,1));
			//check for condition
			chunk.append(mips.bne(r1,r2,condLabel));
			//if it's false it doesn't branch, so set to false
			chunk.append(mips.li(rnew,0));
			//at label, continue...
			chunk.append(mips.label(condLabel));
			freeIntReg(r1);
			freeIntReg(r2);
			notEqualsOp.setRegs(rnew);		
		}
   		else
   		{
   			FpRegs r1 = notEqualsOp.getLhsOperand().getFpRegs();
   			FpRegs r2 = notEqualsOp.getRhsOperand().getFpRegs();
   			Regs rnew	= nextFreeIntReg();
   			String condLabel = condLabelGen.genLabel();
   			chunk.append(mips.comment("Condition Statement: >"));
   			// evaluate as doubles
   			//set to false
   			chunk.append(mips.li(rnew,0));
   			//check for condition
   			chunk.append(mips.c_eq_d(r2, r1));
   			chunk.append(mips.bc1t(condLabel));
   			//if it's false it doesn't branch, so set to false
   			chunk.append(mips.li(rnew,1));
   			//at label, continue...
   			chunk.append(mips.label(condLabel));
   			freeDoubleReg(r1);
   			freeDoubleReg(r2);
   			notEqualsOp.setRegs(rnew);	
   		}	
        return chunk;
    }

    public MipsChunk visitLessThanOp(LessThanOp lessThanOp, MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(lhsResult);
		chunk.appendChunk(rhsResult);

		chunk.append(mips.comment("Condition Statement: <"));

		if (lessThanOp.getLhsOperand().getType() != DoubleType.make() && 
				lessThanOp.getRhsOperand().getType() != DoubleType.make())
		{
			Regs r1 = lessThanOp.getLhsOperand().getRegs();
			Regs r2 = lessThanOp.getRhsOperand().getRegs();
			
			//allocate new register to store result
			Regs rnew	= nextFreeIntReg();
			String condLabel = condLabelGen.genLabel();
			// evaluate as ints
			//set to false
			chunk.append(mips.li(rnew,1));
			//subtract to be able to compare <0
			chunk.append(mips.sub(r1,r1,r2));
			//check for condition
			chunk.append(mips.bltz(r1,condLabel));
			//if it's false it doesn't branch, so set to false
			chunk.append(mips.li(rnew,0));
			//at label, continue...
			chunk.append(mips.label(condLabel));
			freeIntReg(r1);
			freeIntReg(r2);
			lessThanOp.setRegs(rnew);	
		}
		else
		{
			FpRegs r1 = lessThanOp.getLhsOperand().getFpRegs();
			FpRegs r2 = lessThanOp.getRhsOperand().getFpRegs();
			Regs rnew	= nextFreeIntReg();
			String condLabel = condLabelGen.genLabel();
			chunk.append(mips.comment("Condition Statement: >"));
			// evaluate as doubles
			//set to false
			chunk.append(mips.li(rnew,1));
			//check for condition
			chunk.append(mips.c_lt_d(r1, r2));
			chunk.append(mips.bc1t(condLabel));
			//if it's false it doesn't branch, so set to false
			chunk.append(mips.li(rnew,0));
			//at label, continue...
			chunk.append(mips.label(condLabel));
			freeDoubleReg(r1);
			freeDoubleReg(r2);
			lessThanOp.setRegs(rnew);	
		}		
        return chunk;
    }

    public MipsChunk visitLessOrEqualOp(LessOrEqualOp lessOrEqualOp,
                                      MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(lhsResult);
		chunk.appendChunk(rhsResult);


		if (lessOrEqualOp.getLhsOperand().getType() != DoubleType.make() && 
				lessOrEqualOp.getRhsOperand().getType() != DoubleType.make())
		{
			Regs r1 = lessOrEqualOp.getLhsOperand().getRegs();
			Regs r2 = lessOrEqualOp.getRhsOperand().getRegs();
			
			//allocate new register to store result
			Regs rnew	= nextFreeIntReg();
			String condLabel = condLabelGen.genLabel();
			chunk.append(mips.comment("Condition Statement: <="));
			// evaluate as ints
			//set to false
			chunk.append(mips.li(rnew,1));
			//subtract to be able to compare <= 0
			chunk.append(mips.sub(r1,r1,r2));
			//check for condition
			chunk.append(mips.blez(r1,condLabel));
			//if it's false it doesn't branch, so set to false
			chunk.append(mips.li(rnew,0));
			//at label, continue...
			chunk.append(mips.label(condLabel));
			freeIntReg(r1);
			freeIntReg(r2);
			lessOrEqualOp.setRegs(rnew);
		}
		else
		{
			FpRegs r1 = lessOrEqualOp.getLhsOperand().getFpRegs();
			FpRegs r2 = lessOrEqualOp.getRhsOperand().getFpRegs();
			Regs rnew	= nextFreeIntReg();
			String condLabel = condLabelGen.genLabel();
			chunk.append(mips.comment("Condition Statement: >"));
			// evaluate as doubles
			//set to false
			chunk.append(mips.li(rnew,1));
			//check for condition
			chunk.append(mips.c_le_d(r1, r2));
			chunk.append(mips.bc1t(condLabel));
			//if it's false it doesn't branch, so set to false
			chunk.append(mips.li(rnew,0));
			//at label, continue...
			chunk.append(mips.label(condLabel));
			freeDoubleReg(r1);
			freeDoubleReg(r2);
			lessOrEqualOp.setRegs(rnew);	
		}			
        return chunk;
    }

    public MipsChunk visitGreaterThanOp(GreaterThanOp greaterThanOp,
                                      MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.append(mips.comment("Condition Statement: >"));
		chunk.appendChunk(lhsResult);
		chunk.appendChunk(rhsResult);

		if (greaterThanOp.getLhsOperand().getType() != DoubleType.make() && 
				greaterThanOp.getRhsOperand().getType() != DoubleType.make())
		{
			Regs r1 = greaterThanOp.getLhsOperand().getRegs();
			Regs r2 = greaterThanOp.getRhsOperand().getRegs();
			
			//allocate new register to store result
			Regs rnew	= nextFreeIntReg();
			String condLabel = condLabelGen.genLabel();

			// evaluate as ints
			//set to false
			chunk.append(mips.li(rnew,1));
			//subtract to be able to compare > 0
			chunk.append(mips.sub(r1,r1,r2));
			//check for condition
			chunk.append(mips.bgtz(r1,condLabel));
			//if it's false it doesn't branch, so set to false
			chunk.append(mips.li(rnew,0));
			//at label, continue...
			chunk.append(mips.label(condLabel));
			freeIntReg(r1);
			freeIntReg(r2);
			greaterThanOp.setRegs(rnew);	
		}
		else
		{
			FpRegs r1 = greaterThanOp.getLhsOperand().getFpRegs();
			FpRegs r2 = greaterThanOp.getRhsOperand().getFpRegs();
			Regs rnew	= nextFreeIntReg();
			String condLabel = condLabelGen.genLabel();
			chunk.append(mips.comment("Condition Statement: >"));
			// evaluate as doubles
			//set to false
			chunk.append(mips.li(rnew,1));
			//check for condition
			chunk.append(mips.c_lt_d(r2, r1));
			chunk.append(mips.bc1t(condLabel));
			//if it's false it doesn't branch, so set to false
			chunk.append(mips.li(rnew,0));
			//at label, continue...
			chunk.append(mips.label(condLabel));
			freeDoubleReg(r1);
			freeDoubleReg(r2);
			greaterThanOp.setRegs(rnew);	
		}		
        return chunk;
    }

    public MipsChunk visitGreaterOrEqualOp(GreaterOrEqualOp greaterOrEqualOp,
                                         MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(lhsResult);
		chunk.appendChunk(rhsResult);
		//allocate new register to store result

		if (greaterOrEqualOp.getLhsOperand().getType() != DoubleType.make() && 
				greaterOrEqualOp.getRhsOperand().getType() != DoubleType.make())
		{
			Regs r1 = greaterOrEqualOp.getLhsOperand().getRegs();
			Regs r2 = greaterOrEqualOp.getRhsOperand().getRegs();
			Regs rnew	= nextFreeIntReg();
			String condLabel = condLabelGen.genLabel();
			chunk.append(mips.comment("Condition Statement: >"));
			// evaluate as ints
			//set to false
			chunk.append(mips.li(rnew,1));
			//subtract to be able to compare > 0
			chunk.append(mips.sub(r1,r1,r2));
			//check for condition
			chunk.append(mips.bgez(r1,condLabel));
			//if it's false it doesn't branch, so set to false
			chunk.append(mips.li(rnew,0));
			//at label, continue...
			chunk.append(mips.label(condLabel));
			freeIntReg(r1);
			freeIntReg(r2);
			greaterOrEqualOp.setRegs(rnew);	
		}	
		else
		{
			FpRegs r1 = greaterOrEqualOp.getLhsOperand().getFpRegs();
			FpRegs r2 = greaterOrEqualOp.getRhsOperand().getFpRegs();
			Regs rnew	= nextFreeIntReg();
			String condLabel = condLabelGen.genLabel();
			chunk.append(mips.comment("Condition Statement: >"));
			// evaluate as doubles
			//set to false
			chunk.append(mips.li(rnew,1));
			//check for condition
			chunk.append(mips.c_le_d(r2, r1));
			chunk.append(mips.bc1t(condLabel));
			//if it's false it doesn't branch, so set to false
			chunk.append(mips.li(rnew,0));
			//at label, continue...
			chunk.append(mips.label(condLabel));
			freeDoubleReg(r1);
			freeDoubleReg(r2);
			greaterOrEqualOp.setRegs(rnew);	
		}	
        return chunk;
    }

	public MipsChunk visitAddOp(AddOp addOp, MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(lhsResult);
		chunk.appendChunk(rhsResult);
		//perform increment
		if (addOp.getType() == IntType.make())
		{
			Regs r1 = addOp.getLhsOperand().getRegs();
			Regs r2 = addOp.getRhsOperand().getRegs();
			chunk.append(mips.add(r1,r1,r2));
			freeIntReg(r2);
			addOp.setRegs(r1);	
		}
		else if (addOp.getType() == DoubleType.make())
		{
			FpRegs r1 = addOp.getLhsOperand().getFpRegs();
			FpRegs r2 = addOp.getRhsOperand().getFpRegs();
			chunk.append(mips.add_d(r1, r1, r2));
			freeDoubleReg(r2);
			addOp.setFpRegs(r1);		
		}	
        return chunk;
    }

    public MipsChunk visitSubtractOp(SubtractOp subtractOp, MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(lhsResult);
		chunk.appendChunk(rhsResult);
		//perform increment
		if (subtractOp.getType() == IntType.make())
		{
			Regs r1 = subtractOp.getLhsOperand().getRegs();
			Regs r2 = subtractOp.getRhsOperand().getRegs();
			chunk.append(mips.sub(r1,r1,r2));
			freeIntReg(r2);
			subtractOp.setRegs(r1);	
		}
		else if (subtractOp.getType() == DoubleType.make())
		{
			FpRegs r1 = subtractOp.getLhsOperand().getFpRegs();
			FpRegs r2 = subtractOp.getRhsOperand().getFpRegs();
			chunk.append(mips.sub_d(r1, r1, r2));
			freeDoubleReg(r2);
			subtractOp.setFpRegs(r1);		
		}		
        return chunk;
    }

    public MipsChunk visitMultiplyOp(MultiplyOp multiplyOp, MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(lhsResult);
		chunk.appendChunk(rhsResult);
		//perform increment
		if (multiplyOp.getType() == IntType.make())
		{
			Regs r1 = multiplyOp.getLhsOperand().getRegs();
			Regs r2 = multiplyOp.getRhsOperand().getRegs();
			chunk.append(mips.mult(r1,r2));
			chunk.append(mips.mflo(r1));
			freeIntReg(r2);
			multiplyOp.setRegs(r1);	
		}
		else if (multiplyOp.getType() == DoubleType.make())
		{
			FpRegs r1 = multiplyOp.getLhsOperand().getFpRegs();
			FpRegs r2 = multiplyOp.getRhsOperand().getFpRegs();
			chunk.append(mips.mul_d(r1, r1, r2));
			freeDoubleReg(r2);
			multiplyOp.setFpRegs(r1);		
		}	
        return chunk;
    }

    public MipsChunk visitDivideOp(DivideOp divideOp, MipsChunk lhsResult, MipsChunk rhsResult)
    {
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(lhsResult);
		chunk.appendChunk(rhsResult);
		//perform increment
		if (divideOp.getType() == IntType.make())
		{
			Regs r1 = divideOp.getLhsOperand().getRegs();
			Regs r2 = divideOp.getRhsOperand().getRegs();
			chunk.append(mips.div(r1,r2));
			chunk.append(mips.mflo(r1));
			freeIntReg(r2);
			divideOp.setRegs(r1);		
		}
		else if (divideOp.getType() == DoubleType.make())
		{
			FpRegs r1 = divideOp.getLhsOperand().getFpRegs();
			FpRegs r2 = divideOp.getRhsOperand().getFpRegs();
			chunk.append(mips.div_d(r1, r1, r2));
			freeDoubleReg(r2);
			divideOp.setFpRegs(r1);		
		}
        return chunk;
    }

    public MipsChunk visitModOp(ModOp modOp, MipsChunk lhsResult, MipsChunk rhsResult)
    {
		//div $rs, $rt R 0/26 Lo = $rs / $rt, Hi = $rs mod $rt	
		//mfhi $rd R 0/16 $rd = Hi Get value of Hi
		
		MipsChunk chunk = new MipsChunk();
		chunk.appendChunk(lhsResult);
		chunk.appendChunk(rhsResult);
		//perform increment
		Regs r1 = modOp.getLhsOperand().getRegs();
		Regs r2 = modOp.getRhsOperand().getRegs();
		if (modOp.getType() == IntType.make())
		{
			chunk.append(mips.div(r1,r2));
			chunk.append(mips.mfhi(r1));
		}
		freeIntReg(r2);
		modOp.setRegs(r1);		

		return chunk;	
    }

    public MipsChunk visitArrayAccessOp(ArrayAccessOp arrayAccess, MipsChunk array, MipsChunk index)
    {
        MipsChunk chunk = new MipsChunk();

        //TODO:

        return chunk;
    }

    public MipsChunk visitNullLit(NullLit nullLit)
    {
        MipsChunk chunk = new MipsChunk();
        Regs r = nextFreeIntReg();
        chunk.append(mips.li(r, 0), "init to null");
        nullLit.setRegs(r);
        return chunk;
    }

    public MipsChunk visitBooleanLit(BooleanLit booleanLit)
    {
		MipsChunk chunk = new MipsChunk();
		Regs r = nextFreeIntReg();
		if (booleanLit.getValue())
			chunk.append(mips.li(r,1));
		else
			chunk.append(mips.li(r,0));
		booleanLit.setRegs(r);
        return chunk;
    }

    public MipsChunk visitStringLit(StringLit stringLit)
    {
		MipsChunk chunk = new MipsChunk();
		
		//code in v0 is 8
		//read string 	8 	$a0 = address of input buffer $a1 = maximum number of characters to read 	See note below table
		//sbrk (allocate heap memory) 	9 	$a0 = number of bytes to allocate 	$v0 contains address of allocated memory
		String str = stringLit.getValue();
		
                //assign the string a label in memory
                //return the register
                String label = stringLabelGen.genLabel();
                chunk.append(mips._data());
                chunk.append(mips.label(label));
                chunk.append(mips._asciiz(stringLit.getValue()));
                chunk.append(mips._text());

                Regs r = nextFreeIntReg();
                chunk.append(mips.la(r, label));

		stringLit.setRegs(r);
		return chunk;	
    }

    public MipsChunk visitIntLit(IntLit intLit)
    {
		MipsChunk chunk = new MipsChunk();
		Regs r = nextFreeIntReg();
		chunk.append(mips.li(r,(int)intLit.getValue()), "Load IntLit: " + intLit.getValue());
		intLit.setRegs(r);
        return chunk;
    }

    public MipsChunk visitDoubleLit(DoubleLit doubleLit)
    {
		MipsChunk chunk = new MipsChunk();
		String doubleLabel = doubleLabelGen.genLabel();
		
		FpRegs r = nextFreeDoubleReg();
		//chunk.append(mips.label(doubleLabel));
		//chunk.append(mips._double(doubleLit.getValue()));
		chunk.append(mips.li_d(r,doubleLit.getValue()));
		doubleLit.setFpRegs(r);
		
		return chunk;	
    }

    public MipsChunk visitVarRefExp(VarRefExp varRefExp)
    {
		MipsChunk chunk = new MipsChunk();
		Regs r = null;
		FpRegs fp = null;
		//get the variable symbol
		Symbol sym = varRefExp.getVariableSymbol();
		if(sym instanceof LocalSymbol)
		{
			int offset = ((LocalSymbol)sym).getOffset();
			if (varRefExp.getType() != DoubleType.make())
			{
				r = nextFreeIntReg();
				chunk.append(mips.lw(r, offset, Regs.fp), "Load the local var off the stack: " + varRefExp.getName());
				varRefExp.setRegs(r);
			}
			else if (varRefExp.getType() == DoubleType.make())
			{
				fp = nextFreeDoubleReg();
				chunk.append(mips.l_d(fp, offset, Regs.fp), "Load the (double) local var off the stack: " + varRefExp.getName());
				varRefExp.setFpRegs(fp);				
			}
		}
                else if(sym instanceof GlobalSymbol)
                {
                    if (varRefExp.getType() != DoubleType.make())
                    {
                        r = nextFreeIntReg();
                        chunk.append(mips.lw(r, "_" + sym.getName()), "Global Var Ref");
                        varRefExp.setRegs(r);
                    }
        			else if (varRefExp.getType() == DoubleType.make())
        			{
        				fp = nextFreeDoubleReg();
        				chunk.append(mips.l_d(fp, "_" + sym.getName()), "Global Double Var Ref");
        				varRefExp.setFpRegs(fp);				
        			}
                }
		
		return chunk;
    }

    public MipsChunk visitBooleanTypeNode(BooleanTypeNode boolType)
    {
		MipsChunk chunk = new MipsChunk();
		//Regs r = nextFreeIntReg();
                
//		chunk.append(mips.li(Regs.t1, signed));
		
		return chunk;	
    }

    public MipsChunk visitIntTypeNode(IntTypeNode intType)
    {
		MipsChunk chunk = new MipsChunk();
		
		//TODO:
		
		return chunk;	
    }

    public MipsChunk visitDoubleTypeNode(DoubleTypeNode doubleType)
    {
		MipsChunk chunk = new MipsChunk();
		
		//TODO:
		
		return chunk;	
    }

    public MipsChunk visitStringTypeNode(StringTypeNode stringType)
    {
		MipsChunk chunk = new MipsChunk();
		
		//TODO:
		
		//TODO: look at this.  is it good?
		return chunk;	
    }

    public MipsChunk visitPointerTypeNode(PointerTypeNode pointerType, MipsChunk innerResult)
    {
		MipsChunk chunk = new MipsChunk();
		//Type t = pointerType.;
                //if(t instanceof LocalSymbol)
                //    chunk.append(mips.lw(Regs.t1, Regs.fp),"Pointer reference");
		//TODO: look at this.  is it good?

		return chunk;	
    }

    public MipsChunk visitArrayTypeNode(ArrayTypeNode pointerType, MipsChunk innerResult)
    {
		MipsChunk chunk = new MipsChunk();
		
		//TODO:
		
		return chunk;	
    }
private Regs nextFreeIntReg()
	{
		for (int i=0;i<freeIntRegs.length;i++)
		{
			if (freeIntRegs[i] == 0)
			{
				freeIntRegs[i] = 1;
				switch (i)
				{
					case 0:
						return Regs.t0;
					case 1:
						return Regs.t1;
					case 2:
						return Regs.t2;
					case 3:
						return Regs.t3;
					case 4:
						return Regs.t4;
					case 5:
						return Regs.t5;
					case 6:
						return Regs.t6;
					case 7:
						return Regs.t7;
				}
			}
		}
                boolean NO_FREE_REGS = true;
                assert(NO_FREE_REGS);
		return null;
	}
	private void markIntReg(Regs r)
	{		
		switch (r)
		{
			case t0:
				freeIntRegs[0] = 1;
				break;
			case t1:
				freeIntRegs[1] = 1;
				break;
			case t2:
				freeIntRegs[2] = 1;
				break;
			case t3:
				freeIntRegs[3] = 1;
				break;
			case t4:
				freeIntRegs[4] = 1;
				break;
			case t5:
				freeIntRegs[5] = 1;
				break;
			case t6:
				freeIntRegs[6] = 1;
				break;
			case t7:
				freeIntRegs[7] = 1;
				break;
		}
	}
	public void freeIntReg(Regs r)
	{
		if(r == null)
			return;
		
		switch (r)
		{
			case t0:
				freeIntRegs[0] = 0;
				break;
			case t1:
				freeIntRegs[1] = 0;
				break;
			case t2:
				freeIntRegs[2] = 0;
				break;
			case t3:
				freeIntRegs[3] = 0;
				break;
			case t4:
				freeIntRegs[4] = 0;
				break;
			case t5:
				freeIntRegs[5] = 0;
				break;
			case t6:
				freeIntRegs[6] = 0;
				break;
			case t7:
				freeIntRegs[7] = 0;
				break;
		}
	}
	private FpRegs nextFreeDoubleReg()
	{
		for (int i=0;i<freeDoubleRegs.length;i++)
		{
			if (freeDoubleRegs[i] == 0)
			{
				freeDoubleRegs[i] = 1;
				switch (i)
				{
					case 0:
						return FpRegs.f0;
					case 1:
						return FpRegs.f2;
					case 2:
						return FpRegs.f4;
					case 3:
						return FpRegs.f6;
					case 4:
						return FpRegs.f8;
					case 5:
						return FpRegs.f10;
					case 6:
						return FpRegs.f12;
					case 7:
						return FpRegs.f14;
					case 8:
						return FpRegs.f16;
					case 9:
						return FpRegs.f18;
					case 10:
						return FpRegs.f20;
					case 11:
						return FpRegs.f22;
					case 12:
						return FpRegs.f24;
					case 13:
						return FpRegs.f26;
					case 14:
						return FpRegs.f28;
				}
			}
		}
                boolean NO_FREE_RP_REGS = true;
                assert(NO_FREE_RP_REGS);
		return null;
	}
	private void freeDoubleReg(FpRegs r)
	{
		if(r == null)
			return;
		
		switch (r)
		{
			case f0:
				freeDoubleRegs[0] = 0;
				break;
			case f2:
				freeDoubleRegs[1] = 0;
				break;
			case f4:
				freeDoubleRegs[2] = 0;
				break;
			case f6:
				freeDoubleRegs[3] = 0;
				break;
			case f8:
				freeDoubleRegs[4] = 0;
				break;
			case f10:
				freeDoubleRegs[5] = 0;
				break;
			case f12:
				freeDoubleRegs[6] = 0;
				break;
			case f14:
				freeDoubleRegs[7] = 0;
				break;
			case f16:
				freeDoubleRegs[8] = 0;
				break;
			case f18:
				freeDoubleRegs[9] = 0;
				break;
			case f20:
				freeDoubleRegs[10] = 0;
				break;
			case f22:
				freeDoubleRegs[11] = 0;
				break;
			case f24:
				freeDoubleRegs[12] = 0;
				break;
			case f26:
				freeDoubleRegs[13] = 0;
				break;
			case f28:
				freeDoubleRegs[14] = 0;
				break;
		}
	}
	
	public FpRegs promoteIntToDouble(Expression e)
	{
		FpRegs fp = nextFreeDoubleReg();
		
		return fp;
	}
}
