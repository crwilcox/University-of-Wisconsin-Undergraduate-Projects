package cs536.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cs536.ast.AbstractSyntaxTree.*;

public abstract class AstVisitor<T>
{
  /*
    These are pre-implemented helpers to handle casting from Object to
    more useful types.  These should never need to be overridden.
   */
  public final Object visit(AbstractSyntaxTree ast, List decls)
  {
    return visitAst(ast, (List<T>)decls);
  }
    
  public final Object visit(GlobalVarDecl globalVar,
      Object typeResult, Object exprResult)
  {
    return visitGlobalVarDecl(globalVar, (T)typeResult, (T)exprResult);
  }

  public final Object visit(FuncDecl funcDecl, Object typeResult,
      List formalResults, List statementResults)
  {
    return visitFuncDecl(funcDecl, (T)typeResult, (List<T>)formalResults,
        (List<T>)statementResults);
  }

  public final Object visit(FormalArg arg, Object typeResult)
  {
    return visitFormalArg(arg, (T)typeResult);
  }

  public final Object visit(VarDeclStmt localVar, Object typeResult,
      Object initResult)
  {
    return visitVarDeclStmt(localVar, (T)typeResult, (T)initResult);
  }

  public final Object visit(ReturnStmt retStmt, Object resultVal)
  {
    return visitReturnStmt(retStmt, (T)resultVal);
  }

  public final Object visit(IfStmt ifStmt, Object conditionResult,
      List thenResults, List elseResults)
  {
    return visitIfStmt(ifStmt, (T)conditionResult,
        (List<T>)thenResults, (List<T>)elseResults);
  }

  public final Object visit(WhileStmt whileStmt, Object conditionResult,
      List bodyResults)
  {
    return visitWhileStmt(whileStmt, (T)conditionResult, (List<T>)bodyResults);
  }

  public final Object visit(ExpressionStmt exprStmt, Object expResult)
  {
    return visitExpressionStmt(exprStmt, (T)expResult);
  }

  public final Object visit(WriteStmt writeStmt, Object valResult)
  {
    return visitWriteStmt(writeStmt, (T)valResult);
  }

  public final Object visit(ReadStmt readStmt)
  {
    return visitReadStmt(readStmt);
  }

  public final Object visit(FunctionCallExp fcallExp, List argResults)
  {
    return visitFunctionCallExp(fcallExp, (List<T>)argResults);
  }

  public final Object visit(DerefOp derefOp, Object expResult)
  {
    return visitDerefOp(derefOp, (T)expResult);
  }

  public final Object visit(NegationOp negationOp, Object expResult)
  {
    return visitNegationOp(negationOp, (T)expResult);
  }

  public final Object visit(IncrementOp incrOp, Object expResult)
  {
    return visitIncrementOp(incrOp, (T)expResult);
  }

  public final Object visit(DecrementOp decrOp, Object expResult)
  {
    return visitDecrementOp(decrOp, (T)expResult);
  }

  public final Object visit(NotOp logNotOp, Object expResult)
  {
    return visitNotOp(logNotOp, (T)expResult);
  }

  public final Object visit(AddrOfOp addrOfOp, Object expResult)
  {
    return visitAddrOfOp(addrOfOp, (T)expResult);
  }

  public final Object visit(ArrayAccessOp arrayAccessOp, Object lhsResult, Object rhsResult)
  {
    return visitArrayAccessOp(arrayAccessOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(AndOp logAndOp, Object lhsResult, Object rhsResult)
  {
    return visitAndOp(logAndOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(OrOp logOrOp, Object lhsResult, Object rhsResult)
  {
    return visitOrOp(logOrOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(AssignOp assignOp, Object lhsResult, Object rhsResult)
  {
    return visitAssignOp(assignOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(LessThanOp lessThanOp, Object lhsResult, Object rhsResult)
  {
    return visitLessThanOp(lessThanOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(LessOrEqualOp lessOrEqualOp, Object lhsResult, Object rhsResult)
  {
    return visitLessOrEqualOp(lessOrEqualOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(GreaterThanOp greaterThanOp, Object lhsResult, Object rhsResult)
  {
    return visitGreaterThanOp(greaterThanOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(GreaterOrEqualOp greaterOrEqualOp, Object lhsResult, Object rhsResult)
  {
    return visitGreaterOrEqualOp(greaterOrEqualOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(EqualsOp equalsOp, Object lhsResult, Object rhsResult)
  {
    return visitEqualsOp(equalsOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(NotEqualsOp notEqualsOp, Object lhsResult, Object rhsResult)
  {
    return visitNotEqualsOp(notEqualsOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(AddOp addOp, Object lhsResult, Object rhsResult)
  {
    return visitAddOp(addOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(SubtractOp subtractOp, Object lhsResult, Object rhsResult)
  {
    return visitSubtractOp(subtractOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(MultiplyOp multiplyOp, Object lhsResult, Object rhsResult)
  {
    return visitMultiplyOp(multiplyOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(DivideOp divideOp, Object lhsResult, Object rhsResult)
  {
    return visitDivideOp(divideOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(ModOp modOp, Object lhsResult, Object rhsResult)
  {
    return visitModOp(modOp, (T)lhsResult, (T)rhsResult);
  }

  public final Object visit(NullLit nullLit)
  {
    return visitNullLit(nullLit);
  }

  public final Object visit(BooleanLit boolLit)
  {
    return visitBooleanLit(boolLit);
  }

  public final Object visit(StringLit stringLit)
  {
    return visitStringLit(stringLit);
  }

  public final Object visit(IntLit intLit)
  {
    return visitIntLit(intLit);
  }

  public final Object visit(DoubleLit doubleLit)
  {
    return visitDoubleLit(doubleLit);
  }

  public final Object visit(VarRefExp varRefExp)
  {
    return visitVarRefExp(varRefExp);
  }

  public final Object visit(BooleanTypeNode boolType)
  {
    return visitBooleanTypeNode(boolType);
  }

  public final Object visit(IntTypeNode intType)
  {
    return visitIntTypeNode(intType);
  }

  public final Object visit(DoubleTypeNode doubleType)
  {
    return visitDoubleTypeNode(doubleType);
  }

  public final Object visit(StringTypeNode stringType)
  {
    return visitStringTypeNode(stringType);
  }

  public final Object visit(PointerTypeNode pointerType, Object innerResult)
  {
    return visitPointerTypeNode(pointerType, (T)innerResult);
  }

  public final Object visit(ArrayTypeNode arrayType, Object innerResult)
  {
    return visitArrayTypeNode(arrayType, (T)innerResult);
  }

  /*
    These are the actual visitation methods that should be implemented
    by a visitor.  They have the inputs casted to the proper types.
   */

  public abstract T visitAst(AbstractSyntaxTree ast, List<T> declResults);
  public abstract T visitGlobalVarDecl(GlobalVarDecl globalVar,
      T typeResult, T exprResult);
  public abstract T visitFuncDecl(FuncDecl funcDecl, T typeResult,
      List<T> formalResults, List<T> statementResults);
  public abstract T visitFormalArg(FormalArg formalArg, T typeResult);

  public abstract T visitVarDeclStmt(VarDeclStmt localVar,
      T typeResult, T initResult);
  public abstract T visitReturnStmt(ReturnStmt retStmt, T valResult);
  public abstract T visitIfStmt(IfStmt ifStmt, T conditionResult,
      List<T> thenResults, List<T> elseResults);
  public abstract T visitWhileStmt(WhileStmt whileStmt, T conditionResult,
      List<T> bodyResults);
  public abstract T visitExpressionStmt(ExpressionStmt exprStmt, T expResult);
  public abstract T visitWriteStmt(WriteStmt writeStmt, T valResult);
  public abstract T visitReadStmt(ReadStmt readStmt);

  public abstract T visitFunctionCallExp(FunctionCallExp fcallExp,
      List<T> argResults);

  public abstract T visitDerefOp(DerefOp derefOp, T expResult);
  public abstract T visitNegationOp(NegationOp negationOp, T expResult);
  public abstract T visitIncrementOp(IncrementOp incrOp, T expResult);
  public abstract T visitDecrementOp(DecrementOp decrOp, T expResult);
  public abstract T visitNotOp(NotOp logNotOp, T expResult);
  public abstract T visitAddrOfOp(AddrOfOp addrOfOp, T expResult);

  public abstract T visitArrayAccessOp(ArrayAccessOp arrayAccessOp, T lhsResult, T rhsResult);
  public abstract T visitAndOp(AndOp logAndOp, T lhsResult, T rhsResult);
  public abstract T visitOrOp(OrOp logOrOp, T lhsResult, T rhsResult);
  public abstract T visitAssignOp(AssignOp assignOp, T lhsResult, T rhsResult);
  public abstract T visitLessThanOp(LessThanOp lessThanOp, T lhsResult, T rhsResult);
  public abstract T visitLessOrEqualOp(LessOrEqualOp lessOrEqualOp,
      T lhsResult, T rhsResult);
  public abstract T visitGreaterThanOp(GreaterThanOp greaterThanOp,
      T lhsResult, T rhsResult);
  public abstract T visitGreaterOrEqualOp(GreaterOrEqualOp greaterOrEqualOp,
      T lhsResult, T rhsResult);
  public abstract T visitEqualsOp(EqualsOp equalsOp, T lhsResult, T rhsResult);
  public abstract T visitNotEqualsOp(NotEqualsOp notEqualsOp,
      T lhsResult, T rhsResult);
  public abstract T visitAddOp(AddOp addOp, T lhsResult, T rhsResult);
  public abstract T visitSubtractOp(SubtractOp subtractOp, T lhsResult, T rhsResult);
  public abstract T visitMultiplyOp(MultiplyOp multiplyOp, T lhsResult, T rhsResult);
  public abstract T visitDivideOp(DivideOp divideOp, T lhsResult, T rhsResult);
  public abstract T visitModOp(ModOp modOp, T lhsResult, T rhsResult);

  public abstract T visitNullLit(NullLit nullLit);
  public abstract T visitBooleanLit(BooleanLit booleanLit);
  public abstract T visitStringLit(StringLit stringLit);
  public abstract T visitIntLit(IntLit intLit);
  public abstract T visitDoubleLit(DoubleLit doubleLit);
  public abstract T visitVarRefExp(VarRefExp varRefExp);

  public abstract T visitBooleanTypeNode(BooleanTypeNode boolType);
  public abstract T visitIntTypeNode(IntTypeNode intType);
  public abstract T visitDoubleTypeNode(DoubleTypeNode doubleType);
  public abstract T visitStringTypeNode(StringTypeNode stringType);
  public abstract T visitPointerTypeNode(PointerTypeNode pointerType, T innerResult);
  public abstract T visitArrayTypeNode(ArrayTypeNode arrayType, T innerResult);

  /*
    These are overridable hooks that are run before the children of
    the argument node are visited.  This can be useful to prepare
    information that is to be propagated down the AST.
   */
  public void preVisit(AbstractSyntaxTree ast) {}
  public void preVisit(GlobalVarDecl globalVar) {}
  public void preVisit(FuncDecl funcDecl) {}
  public void preVisit(FormalArg arg) {}

  public void preVisit(VarDeclStmt localVar) {}
  public void preVisit(ReturnStmt retStmt) {}
  public void preVisit(IfStmt ifStmt) {}
  public void preVisit(WhileStmt whileStmt) {}
  public void preVisit(ExpressionStmt exprStmt) {}
  public void preVisit(WriteStmt writeStmt) {}
  public void preVisit(ReadStmt readStmt) {}

  public void preVisit(FunctionCallExp fcallExp) {}

  public void preVisit(DerefOp derefOp) {}
  public void preVisit(NegationOp negationOp) {}
  public void preVisit(IncrementOp incrOp) {}
  public void preVisit(DecrementOp decrOp) {}
  public void preVisit(NotOp logNotOp) {}
  public void preVisit(AddrOfOp addrOfOp) {}

  public void preVisit(ArrayAccessOp arrayAccessOp) {}
  public void preVisit(AndOp logAndOp) {}
  public void preVisit(OrOp logOrOp) {}
  public void preVisit(AssignOp assignOp) {}
  public void preVisit(LessThanOp lessThanOp) {}
  public void preVisit(LessOrEqualOp lessOrEqualOp) {}
  public void preVisit(GreaterThanOp greaterThanOp) {}
  public void preVisit(GreaterOrEqualOp greaterOrEqualOp) {}
  public void preVisit(EqualsOp equalsOp) {}
  public void preVisit(NotEqualsOp notEqualsOp) {}
  public void preVisit(AddOp addOp) {}
  public void preVisit(SubtractOp subtractOp) {}
  public void preVisit(MultiplyOp multiplyOp) {}
  public void preVisit(DivideOp divideOp) {}
  public void preVisit(ModOp modOp) {}

  public void preVisit(NullLit nullLit) {}
  public void preVisit(BooleanLit boolLit) {}
  public void preVisit(StringLit stringLit) {}
  public void preVisit(IntLit intLit) {}
  public void preVisit(DoubleLit doubleLit) {}

  public void preVisit(VarRefExp varRefExp) {}

  public void preVisit(BooleanTypeNode boolType) {}
  public void preVisit(IntTypeNode intType) {}
  public void preVisit(DoubleTypeNode doubleType) {}
  public void preVisit(StringTypeNode stringType) {}
  public void preVisit(PointerTypeNode pointerType) {}
  public void preVisit(ArrayTypeNode arrayType) {}
}