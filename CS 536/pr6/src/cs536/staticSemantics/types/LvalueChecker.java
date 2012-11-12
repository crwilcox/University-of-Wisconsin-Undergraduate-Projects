package cs536.staticSemantics.types;

import java.util.List;
import java.util.Iterator;

import cs536.ast.SourceLocation;
import cs536.ast.AstVisitor;
import static cs536.ast.AbstractSyntaxTree.*;
import cs536.ast.AbstractSyntaxTree;

public class LvalueChecker extends AstVisitor<Boolean>
{
    public boolean passed() {
        // After all, we didn't find any errors, did we? :-)
        return passed;
    }

    private boolean passed = true;

    
    private void error(SourceLocation loc, String error)
    {
	String stringLoc = "??:??";
	if (loc != null) {
	    stringLoc = loc.toString();
	}

	System.out.println(stringLoc + ":" + error);

	passed = false;
    }

    


    public Boolean visitAst(AbstractSyntaxTree ast, List<Boolean> decls)
    {
        return null;
    }
    
    public Boolean visitGlobalVarDecl(GlobalVarDecl globalVar,
                                      Boolean typeResult, Boolean exprResult)
    {
        return null;
    }

    public Boolean visitFuncDecl(FuncDecl funcDecl, Boolean typeResult,
                                 List<Boolean> formalResults, List<Boolean> statementResults)
    {
        return null;
    }

    public Boolean visitFormalArg(FormalArg formalArg, Boolean typeResult)
    {
        return null;
    }

    public Boolean visitVarDeclStmt(VarDeclStmt localVar,
                                    Boolean typeResult, Boolean initResult)
    {
        return null;
    }

    public Boolean visitReturnStmt(ReturnStmt retStmt, Boolean valResult)
    {
        return null;
    }

    public Boolean visitIfStmt(IfStmt ifStmt, Boolean conditionResult,
                               List<Boolean> thenResults, List<Boolean> elseResults)
    {
        return null;
    }

    public Boolean visitWhileStmt(WhileStmt whileStmt, Boolean conditionResult,
                                  List<Boolean> bodyResults)
    {
        return null;
    }

    public Boolean visitExpressionStmt(ExpressionStmt exprStmt, Boolean expResult)
    {
        return null;
    }

    public Boolean visitWriteStmt(WriteStmt writeStmt, Boolean valResult)
    {
        return null;
    }

    public Boolean visitReadStmt(ReadStmt readStmt)
    {
        return null;
    }

    public Boolean visitFunctionCallExp(FunctionCallExp fcallExp,
                                        List<Boolean> argResults)
    {
        return false;
    }

    public Boolean visitDerefOp(DerefOp derefOp, Boolean expResult)
    {
        return true;
    }

    public Boolean visitNegationOp(NegationOp negationOp, Boolean expResult)
    {
        return false;
    }

    public Boolean visitIncrementOp(IncrementOp incrOp, Boolean expResult)
    {
        if (!expResult) {
            error(incrOp.getLocation(), "Illegal modification of r-value");
        }
        return false;
    }

    public Boolean visitDecrementOp(DecrementOp decrOp, Boolean expResult)
    {
        if (!expResult) {
            error(decrOp.getLocation(), "Illegal modification of r-value");
        }
        return false;
    }

    public Boolean visitNotOp(NotOp logNotOp, Boolean expResult)
    {
        return false;
    }

    public Boolean visitAddrOfOp(AddrOfOp addrOfOp, Boolean expResult)
    {
        if (!expResult) {
            error(addrOfOp.getLocation(), "Cannot take the address of an r-value");
        }
        return false;
    }

    public Boolean visitAndOp(AndOp logAndOp, Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitOrOp(OrOp logOrOp, Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitAssignOp(AssignOp assignOp, Boolean lhsResult, Boolean rhsResult)
    {
        // The lhs operand must be an lvalue
        if (!lhsResult) {
            error(assignOp.getLocation(), "Illegal modification of r-value");
        }
        return false;
    }

    public Boolean visitEqualsOp(EqualsOp equalsOp, Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitNotEqualsOp(NotEqualsOp notEqualsOp,
                                    Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitLessThanOp(LessThanOp lessThanOp, Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitLessOrEqualOp(LessOrEqualOp lessOrEqualOp,
                                      Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitGreaterThanOp(GreaterThanOp greaterThanOp,
                                      Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitGreaterOrEqualOp(GreaterOrEqualOp greaterOrEqualOp,
                                         Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitAddOp(AddOp addOp, Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitSubtractOp(SubtractOp subtractOp, Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitMultiplyOp(MultiplyOp multiplyOp, Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitDivideOp(DivideOp divideOp, Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitModOp(ModOp modOp, Boolean lhsResult, Boolean rhsResult)
    {
        return false;
    }

    public Boolean visitArrayAccessOp(ArrayAccessOp arrayAccess, Boolean array, Boolean index)
    {
        return true;
    }

    public Boolean visitNullLit(NullLit nullLit)
    {
        return false;
    }

    public Boolean visitBooleanLit(BooleanLit booleanLit)
    {
        return false;
    }

    public Boolean visitStringLit(StringLit stringLit)
    {
        return false;
    }

    public Boolean visitIntLit(IntLit intLit)
    {
        return false;
    }

    public Boolean visitDoubleLit(DoubleLit doubleLit)
    {
        return false;
    }

    public Boolean visitVarRefExp(VarRefExp varRefExp)
    {
        return true;
    }

    public Boolean visitBooleanTypeNode(BooleanTypeNode boolType)
    {
        return null;
    }

    public Boolean visitIntTypeNode(IntTypeNode intType)
    {
        return null;
    }

    public Boolean visitDoubleTypeNode(DoubleTypeNode doubleType)
    {
        return null;
    }

    public Boolean visitStringTypeNode(StringTypeNode stringType)
    {
        return null;
    }

    public Boolean visitPointerTypeNode(PointerTypeNode pointerType, Boolean innerResult)
    {
        return null;
    }

    public Boolean visitArrayTypeNode(ArrayTypeNode pointerType, Boolean innerResult)
    {
        return null;
    }
}
