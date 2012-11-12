package cs536.codegen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs536.ast.AstVisitor;
import cs536.ast.SourceLocation;
import cs536.ast.AbstractSyntaxTree;
import cs536.staticSemantics.symbolTable.LocalSymbol;
import static cs536.ast.AbstractSyntaxTree.*;


// Uses Object since there's not much real need for better
public class OffsetCalculator extends AstVisitor<Object>
{
    public Object visitGlobalVarDecl(GlobalVarDecl globalVar,
                                   Object typeResult, 
                                   Object exprResult)
    {
	return null;
    }


    public Object visitAst(AbstractSyntaxTree ast,
                         List<Object> declarationResults)
    {
        return null;
    }


    private int currentOffset = -1000;

    public void preVisit(FuncDecl funcDecl)
    {
	currentOffset = 0;
    }

    public Object visitFuncDecl(FuncDecl funcDecl, 
                              Object typeResult,
                              List<Object> formalResults, 
                              List<Object> statementResults)
    {
        assert currentOffset >= 0;
        // add 4 for the saved return value
        currentOffset += 4;

        // Now start computing offsets of formals
        List<FormalArg> formals = funcDecl.getFormalArguments();
        for(FormalArg formal : formals) {
            LocalSymbol sym = formal.getLocalSymbol();
            sym.setOffset(currentOffset);
            currentOffset += sym.getType().getSize();
            assert currentOffset % 4 == 0;
        }
        
        currentOffset = -1000;
        return null;
    }

    public Object visitFormalArg(FormalArg formalArg, Object typeResult)
    {
	// done; we handle these later, up in visitFuncDecl.
	return null;
    }

    public Object visitVarDeclStmt(VarDeclStmt localVar,
                                   Object typeResult, 
                                   Object initResult)
    {
        assert currentOffset >= 0;
        LocalSymbol sym = localVar.getLocalSymbol();
        sym.setOffset(currentOffset);
        currentOffset += sym.getType().getSize();
        assert currentOffset %4 == 0;
	return null;
    }


    public Object visitReturnStmt(ReturnStmt retStmt, Object valResult)
    {
	return null;
    }


    public Object visitIfStmt(IfStmt ifStmt,
                            Object conditionResult,
                            List<Object> thenResults,
                            List<Object> elseResults)
    {
	return null;
    }


    public Object visitWhileStmt(WhileStmt whileStmt,
                               Object conditionResult,
                               List<Object> bodyResults)
    {
	return null;
    }


    public Object visitExpressionStmt(ExpressionStmt exprStmt,
                                    Object expResult)
    {
	return null;
    }


    public Object visitWriteStmt(WriteStmt writeStmt, Object valResult)
    {
	return null;
    }


    public Object visitReadStmt(ReadStmt readStmt)
    {
	return null;
    }


    public Object visitFunctionCallExp(FunctionCallExp fcallExp,
                                     List<Object> argResults)
    {
	return null;
    }


    public Object visitDerefOp(DerefOp derefOp, Object expResult)
    {
        return null;
    }


    public Object visitNegationOp(NegationOp negationOp, Object expResult)
    {
        return null;
    }

    public Object visitIncrementOp(IncrementOp incrOp, Object expResult)
    {
        return null;
    }

    public Object visitDecrementOp(DecrementOp decrOp, Object expResult)
    {
        return null;
    }

    public Object visitNotOp(NotOp logNotOp, Object expResult)
    {
        return null;
    }

    public Object visitAddrOfOp(AddrOfOp addrOfOp, Object expResult)
    {
        return null;
    }

    public Object visitAndOp(AndOp logAndOp, Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitOrOp(OrOp logOrOp, Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitAssignOp(AssignOp assignOp, Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitEqualsOp(EqualsOp equalsOp, Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitNotEqualsOp(NotEqualsOp notEqualsOp,
                                    Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitLessThanOp(LessThanOp lessThanOp, Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitLessOrEqualOp(LessOrEqualOp lessOrEqualOp,
                                      Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitGreaterThanOp(GreaterThanOp greaterThanOp,
                                      Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitGreaterOrEqualOp(GreaterOrEqualOp greaterOrEqualOp,
                                         Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitAddOp(AddOp addOp, Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitSubtractOp(SubtractOp subtractOp, Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitMultiplyOp(MultiplyOp multiplyOp, Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitDivideOp(DivideOp divideOp, Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitModOp(ModOp modOp, Object lhsResult, Object rhsResult)
    {
        return null;
    }

    public Object visitArrayAccessOp(ArrayAccessOp arrayAccess, Object array, Object index)
    {
        return null;
    }

    public Object visitNullLit(NullLit nullLit)
    {
        return null;
    }

    public Object visitBooleanLit(BooleanLit booleanLit)
    {
        return null;
    }

    public Object visitStringLit(StringLit stringLit)
    {
        return null;
    }

    public Object visitIntLit(IntLit intLit)
    {
        return null;
    }

    public Object visitDoubleLit(DoubleLit doubleLit)
    {
        return null;
    }

    public Object visitVarRefExp(VarRefExp varRefExp)
    {
        return null;
    }

    public Object visitBooleanTypeNode(BooleanTypeNode boolType)
    {
        return null;
    }

    public Object visitIntTypeNode(IntTypeNode intType)
    {
        return null;
    }

    public Object visitDoubleTypeNode(DoubleTypeNode doubleType)
    {
        return null;
    }

    public Object visitStringTypeNode(StringTypeNode stringType)
    {
        return null;
    }

    public Object visitPointerTypeNode(PointerTypeNode pointerType, Object innerResult)
    {
        return null;
    }

    public Object visitArrayTypeNode(ArrayTypeNode pointerType, Object innerResult)
    {
        return null;
    }
}
