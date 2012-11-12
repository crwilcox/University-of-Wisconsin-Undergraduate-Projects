package cs536.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cs536.ast.AbstractSyntaxTree.*;

public class Unparser extends AstVisitor<String>
{
    // This string is prepended to each line when we indent
    private final String indent = "  ";


    /**
     * This helper function takes a list of strings, and returns a new
     * string with them all joined together with the 'sep' separator.
     */
    protected String join(List<String> lst, String sep)
    {
	StringBuilder sb = new StringBuilder();
	boolean first = true; // I wish there were a non-sucky way to do this
	for (String str : lst) {
	    if (!first) {
		sb.append(sep + str);
	    }
	    else {
		sb.append(str);
		first = false;
	    }
	}
	return sb.toString();
    }


    /**
     * This helper function takes a list of strings. Each string may
     * have multiple lines. For every line in the input -- whether it
     * be a line in one of the input strings or a different input
     * string, this indents that line by 'indent' (above), then joins
     * everything together.
     */
    protected String indentAndJoin(List<String> lst)
    {
	StringBuilder sb = new StringBuilder();
	for (String str : lst) {
	    // This string may have multiple lines, so we need to split
	    // them up. Note that "abc\n".split("\n") => ["abc"], not
	    // ["abc", ""].
	    String[] lines = str.split("\n");
	    for (String line : lines) {
		sb.append(indent + line + "\n");
	    }
	}
	return sb.toString();
    }


  /*
    These are the actual visitation methods that should be implemented
    by a visitor.  They have the inputs casted to the proper types.
   */

    public String visitGlobalVarDecl(GlobalVarDecl globalVar,
				     String typeResult, 
				     String exprResult)
    {
	// exprResult is null if there is no initializer. The
	// identifier is only accessible through 'globalVar'
        String name = globalVar.getName();
	
	if (exprResult != null) {
	    return typeResult + " " + name + " = " + exprResult
                + "; /*" + globalVar.getGlobalSymbol().getType().getSize() + "*/\n";
	}
	else {
	    return typeResult + " " + name
                + "; /*" + globalVar.getGlobalSymbol().getType().getSize() + "*/\n";
	}
    }


    public String visitAst(AbstractSyntaxTree ast,
                           List<String> declarationResults)
    {
        return join(declarationResults, "");
    }


    public String visitFuncDecl(FuncDecl funcDecl, 
				String typeResult,
				List<String> formalResults, 
				List<String> statementResults)
    {
        if (typeResult == null) {
            typeResult = "void";
        }
        
	return
	    typeResult + " " + funcDecl.getName()
	    + "(" + join(formalResults, ", ") + ") {\n"
	    + indentAndJoin(statementResults)
	    + "}\n";
    }

    public String visitFormalArg(FormalArg formalArg, String typeResult)
    {
	// The name of the argument is available through 'formalArg'.
	return typeResult + " " + formalArg.getName();
    }

    public String visitVarDeclStmt(VarDeclStmt localVar,
				   String typeResult, 
				   String initResult)
    {
	// 'initResult' is null if there is no initializer. The name
	// of the variable is available through 'localVar'
        String name = localVar.getName();
        
	if (initResult != null) {
	    return typeResult + " " + name + " = " + initResult + ";\n";
	}
	else {
	    return typeResult + " " + name + ";\n";
	}
    }


    public String visitReturnStmt(ReturnStmt retStmt, String valResult)
    {
	// 'valResult' is null if there is no return expression
	if (valResult != null) {
	    return "return " + valResult + ";\n";
	}
	else {
	    return "return;\n";
	}
    }


    public String visitIfStmt(IfStmt ifStmt,
			      String conditionResult,
			      List<String> thenResults,
			      List<String> elseResults)
    {
	return
	    "if(" + conditionResult + ") {\n"
	    + indentAndJoin(thenResults) +
	    "}\n" +
	    "else {\n"
	    + indentAndJoin(elseResults) +
	    "}\n";
    }


    public String visitWhileStmt(WhileStmt whileStmt,
				 String conditionResult,
				 List<String> bodyResults)
    {
	return
	    "while(" + conditionResult + ") {\n"
	    + indentAndJoin(bodyResults) +
	    "}\n";
    }


    public String visitExpressionStmt(ExpressionStmt exprStmt,
				      String expResult)
    {
	return expResult + ";\n";
    }


    public String visitWriteStmt(WriteStmt writeStmt, String valResult)
    {
	return "write " + valResult + ";\n";
    }


    public String visitReadStmt(ReadStmt readStmt)
    {
	// the name of the variable to read into is accessible through
	// readStmt.
	return "read " + readStmt.getDestinationName() + ";\n";
    }


    public String visitFunctionCallExp(FunctionCallExp fcallExp,
				       List<String> argResults)
    {
	// The name of the function to call is accessible through
	// fcallExp
	return fcallExp.getName() + "(" + join(argResults, ", ") + ")";
    }


    public String visitDerefOp(DerefOp derefOp, String expResult)
    {
	return "(*" + expResult + ")";
    }


    public String visitNegationOp(NegationOp negationOp, String expResult)
    {
	return "(-" + expResult + ")";
    }

    public String visitIncrementOp(IncrementOp incrOp, String expResult)
    {
	// Determine whether prefix or postfix from 'incrOp'
	if (incrOp.isPrefix()) {
	    return "(++" + expResult + ")";
	}
	else {
	    return "(" + expResult + "++)";
	}
    }


    public String visitDecrementOp(DecrementOp decrOp, String expResult)
    {
	// Determine whether prefix or postfix from 'incrOp'
	if (decrOp.isPrefix()) {
	    return "(--" + expResult + ")";
	}
	else {
	    return "(" + expResult + "--)";
	}
    }


    public String visitNotOp(NotOp logNotOp, String expResult)
    {
	return "(!" + expResult + ")";
    }


    public String visitAddrOfOp(AddrOfOp addrOfOp, String expResult)
    {
	// Note to self: tell them to parenthesize the expression, not
	// the operands
	return "(&" + expResult + ")";
    }

    public String visitArrayAccessOp(ArrayAccessOp arrayAccessOp,
				     String lhsResult,
				     String rhsResult)
    {
	return "(" + lhsResult + "[" + rhsResult + "])";
    }


    public String visitAndOp(AndOp logAndOp,
			     String lhsResult,
			     String rhsResult)
    {
	return "(" + lhsResult + " && " + rhsResult + ")";
    }

    public String visitOrOp(OrOp logOrOp, String lhsResult, String rhsResult)
    {
	return "(" + lhsResult + " || " + rhsResult + ")";
    }


    public String visitAssignOp(AssignOp assignOp,
				String lhsResult,
				String rhsResult)
    {
	return "(" + lhsResult + " = " + rhsResult + ")";
    }

    public String visitLessThanOp(LessThanOp lessThanOp,
				  String lhsResult,
				  String rhsResult)
    {
	return "(" + lhsResult + " < " + rhsResult + ")";
    }

    public String visitLessOrEqualOp(LessOrEqualOp lessOrEqualOp,
				     String lhsResult,
				     String rhsResult)
    {
	return "(" + lhsResult + " <= " + rhsResult + ")";
    }


    public String visitGreaterThanOp(GreaterThanOp greaterThanOp,
				     String lhsResult, 
				     String rhsResult)
    {
	return "(" + lhsResult + " > " + rhsResult + ")";
    }


    public String visitGreaterOrEqualOp(GreaterOrEqualOp greaterOrEqualOp,
					String lhsResult, 
					String rhsResult)
    {
	return "(" + lhsResult + " >= " + rhsResult + ")";
    }


    public String visitEqualsOp(EqualsOp equalsOp, 
				String lhsResult, 
				String rhsResult)
    {
	return "(" + lhsResult + " == " + rhsResult + ")";
    }


    public String visitNotEqualsOp(NotEqualsOp notEqualsOp,
				   String lhsResult,
				   String rhsResult)
    {
	return "(" + lhsResult + " != " + rhsResult + ")";
    }


    public String visitAddOp(AddOp addOp, String lhsResult, String rhsResult)
    {
	return "(" + lhsResult + " + " + rhsResult + ")";
    }


    public String visitSubtractOp(SubtractOp subtractOp, 
				  String lhsResult, 
				  String rhsResult)
    {
	return "(" + lhsResult + " - " + rhsResult + ")";
    }


    public String visitMultiplyOp(MultiplyOp multiplyOp, 
				  String lhsResult,
				  String rhsResult)
    {
	return "(" + lhsResult + " * " + rhsResult + ")";
    }


    public String visitDivideOp(DivideOp divideOp, 
				String lhsResult, 
				String rhsResult)
    {
	return "(" + lhsResult + " / " + rhsResult + ")";
    }


    public String visitModOp(ModOp modOp, String lhsResult, String rhsResult)
    {
	return "(" + lhsResult + " % " + rhsResult + ")";
    }



    public String visitNullLit(NullLit nullLit) {
	return "null";
    }

    public String visitBooleanLit(BooleanLit booleanLit) {
	return "" + booleanLit.getValue();
    }

    public String visitStringLit(StringLit stringLit){
	return "" + stringLit.getValue();
    }

    public String visitIntLit(IntLit intLit) {
	return "" + intLit.getValue();
    }

    public String visitDoubleLit(DoubleLit doubleLit) {
	return "" + doubleLit.getValue();
    }

    public String visitVarRefExp(VarRefExp varRefExp) {
	return varRefExp.getName();
    }


    public String visitBooleanTypeNode(BooleanTypeNode boolType) {
	return "bool";
    }

    public String visitIntTypeNode(IntTypeNode intType) {
	return "int";
    }

    public String visitDoubleTypeNode(DoubleTypeNode doubleType) {
	return "double";
    }

    public String visitStringTypeNode(StringTypeNode stringType) {
	return "string";
    }

    public String visitPointerTypeNode(PointerTypeNode pointerType,
				   String innerResult)
    {
	return innerResult + "*";
    }

    public String visitArrayTypeNode(ArrayTypeNode arrayType, String innerResult)
    {
	// size can be determined from 'arrayType'
	return innerResult + "[" + arrayType.getSize() + "]";
    }
}
