package cs536.staticSemantics.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs536.ast.AstVisitor;
import cs536.ast.SourceLocation;
import cs536.ast.AbstractSyntaxTree;
import cs536.staticSemantics.symbolTable.FunctionSymbol;
import static cs536.ast.AbstractSyntaxTree.*;

public class TypeChecker extends AstVisitor<Type>
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

    private boolean isNumeric(Type type)
    {
	if (type == IntType.make() || type == DoubleType.make()) {
	    return true;
	}
	else {
	    return false;
	}
    }

    // Given the types of the left and right side of an equality
    // expression (or initialization), determine whether the arguments
    // are legal, report an error and return ErrorType if not, and
    // return the left side if so.
    private Type helperAssign(SourceLocation loc, Type left, Type right)
    {
	if (left == ErrorType.make() || right == ErrorType.make()) {
	    return ErrorType.make();
	}

	// Applying an equality (==, !=) or assignment (=) operator to
	// operands of two different types, except that it is possible
	// to compare an int and double, as well as assign an int to a
	// double (but not vice versa).
	if (left == right ||
	    (left == DoubleType.make() && right == IntType.make())) {
	    // fine
	    return left;
	}
        // It should also be possible to assign null to a pointer type
        else if (left instanceof PointerType && right == NullType.make()) {
            // fine
            return left;
        }
	else {
	    error(loc, "Type mismatch");
	    return ErrorType.make();
	}
    }


    private Type helperArithmetic(SourceLocation loc, Type left, Type right)
    {
	if (left == ErrorType.make() || right == ErrorType.make()) {
	    return ErrorType.make();
	}

	// Applying either an inequality (<, <=, >, >=) operator or a
	// binary arithmetic (+,-,*,/) operator other than mod to a
	// non-numeric operand. (In particular, it is not possible to
	// perform pointer arithmetic.)

	if (!isNumeric(left) || !isNumeric(right)) {
	    error(loc, "Operator can only be applied to numeric types");
	    return ErrorType.make();
	}

	// It's legal. Now, is it an int or double?
	if (left == DoubleType.make() || right == DoubleType.make()) {
	    return DoubleType.make();
	}
	else {
	    return IntType.make();
	}
    }


    private Type helperEquality(SourceLocation loc, Type left, Type right)
    {
	if (left == ErrorType.make() || right == ErrorType.make()) {
	    return ErrorType.make();
	}

	// Applying an equality (==, !=) or assignment (=) operator to
	// operands of two different types, except that it is possible
	// to compare an int and double, as well as assign an int to a
	// double (but not vice versa).
	if (left == right ||
	    (isNumeric(left) && isNumeric(right))) {
	    // fine
	    return BoolType.make();
	}
        // It should also be possible to assign null to a pointer type
        else if (left instanceof PointerType && right == NullType.make()) {
            // fine
            return BoolType.make();
        }
        else if (left == NullType.make() && right instanceof PointerType) {
            // fine
            return BoolType.make();
        }
	else {
	    error(null, "Type mismatch");
	    return ErrorType.make();
	}
    }


    private Type helperInequality(SourceLocation loc, Type left, Type right)
    {
	if (left == ErrorType.make() || right == ErrorType.make()) {
	    return ErrorType.make();
	}

	// Applying either an inequality (<, <=, >, >=) operator or a
	// binary arithmetic (+,-,*,/) operator other than mod to a
	// non-numeric operand. (In particular, it is not possible to
	// perform pointer arithmetic.)

	if (!isNumeric(left) || !isNumeric(right)) {
	    error(loc, "Operator can only be applied to numeric types");
	    return ErrorType.make();
	}

	// It's legal. Now, is it an int or double?
	return BoolType.make();
    }

    // Given a requiredType && actualType, checks to make sure they
    // are the same, and give an error if not.
    private void helperRequireType(SourceLocation loc,
				   String messageIfError,
				   Type requiredType,
				   Type actualType)
    {
	if (actualType == ErrorType.make()) {
	    // fine. Here for uniformity
	    return;
	}

        // We also use this function for calls. We want to allow passing
        // 'null' as a parameter, so we'll do this:
        if (requiredType instanceof PointerType && actualType == NullType.make()) {
            return;
        }

	if (requiredType != actualType) {
	    error(loc, messageIfError);
	}
    }


    // Given a requiredType && actualType, checks to make sure they
    // are the same, and give an error if not.
    private Type helperUnaryMinus(SourceLocation loc,
				  Type operandType)
    {
	if (operandType == ErrorType.make()) {
	    return ErrorType.make();
	}

	if (!isNumeric(operandType)) {
	    error(loc, "Attempting to negate something that isn't a number");
	    return ErrorType.make();
	}

	return operandType;
    }


    private Type helperIncrDecr(SourceLocation loc,
				Type operandType)
    {
	if (operandType == ErrorType.make()) {
	    return ErrorType.make();
	}

	// Applying the increment or decrement operators to an operand
	// of type other than a numeric or pointer type
	if (!isNumeric(operandType) && !(operandType instanceof PointerType)) {
	    error(loc, "Integer arithmetic operator applied to non-numeric, non-pointer type");
	    return ErrorType.make();
	}

	return operandType;
    }



    private Type helperLogicalNot(SourceLocation loc,
				  Type operandType)
    {
	if (operandType == ErrorType.make()) {
	    return ErrorType.make();
	}

	// Applying the increment or decrement operators to an operand
	// of type other than a numeric or pointer type
	if (operandType != BoolType.make()) {
	    error(loc, "Logical operator applied to non-bool operand");
	    return ErrorType.make();
	}

	return operandType;
    }


    private Type helperLogicalBinary(SourceLocation loc,
				     Type left,
				     Type right)
    {
	if (left == ErrorType.make() || right == ErrorType.make()) {
	    return ErrorType.make();
	}

	// Applying the increment or decrement operators to an operand
	// of type other than a numeric or pointer type
	if (left != BoolType.make() || right != BoolType.make()) {
	    error(loc, "Logical operator applied to non-bool operand");
	    return ErrorType.make();
	}

	return BoolType.make();
    }

    

    public Type visitGlobalVarDecl(GlobalVarDecl globalVar,
                                   Type typeResult, 
                                   Type exprResult)
    {
        if (globalVar.hasInitializer()) {
            Type declType = globalVar.getTypeNode().asSemanticType();
            helperAssign(globalVar.getLocation(), declType, exprResult);
        }
	return null;
    }


    public Type visitAst(AbstractSyntaxTree ast,
                         List<Type> declarationResults)
    {
	// done
        return null;
    }


    private FuncDecl currentFunction;
    public void preVisit(FuncDecl funcDecl)
    {
	currentFunction = funcDecl;
    }

    public Type visitFuncDecl(FuncDecl funcDecl, 
                              Type typeResult,
                              List<Type> formalResults, 
                              List<Type> statementResults)
    {
	currentFunction = null;
        return null;
    }

    public Type visitFormalArg(FormalArg formalArg, Type typeResult)
    {
	// done
        Type t = formalArg.getTypeNode().asSemanticType();
        currentFunction.paramSize += t.getSize();

	return null;
    }

    public Type visitVarDeclStmt(VarDeclStmt localVar,
                                 Type typeResult, 
                                 Type initResult)
    {
        if (localVar.hasInitializer()) {
            Type declType = localVar.getTypeNode().asSemanticType();
            helperAssign(localVar.getLocation(), declType, initResult);
        }

       Type t = localVar.getTypeNode().asSemanticType();
       currentFunction.localSize += t.getSize();

	return null;
    }


    public Type visitReturnStmt(ReturnStmt retStmt, Type valResult)
    {
	FunctionSymbol symbol = currentFunction.getFunctionSymbol();
	Type returnType = symbol.getType().getReturnType();

	if (returnType == null) {
	    // The current function returns void. What did we get?
	    if (valResult == null) {
		// Fine
	    }
	    else {
		error(retStmt.getLocation(), "Returning a value from a void function");
	    }
	}
	else {
	    // The current function returns non-void. What did we get?
	    if (valResult == null) {
		error(retStmt.getLocation(), "Missing return value");
	    }
	    else if (valResult != returnType) {
		error(retStmt.getLocation(), "Bad return type");
	    }
	    else {
		// Fine
	    }
	}

	return null;
    }


    public Type visitIfStmt(IfStmt ifStmt,
                            Type conditionResult,
                            List<Type> thenResults,
                            List<Type> elseResults)
    {
	helperRequireType(ifStmt.getLocation(), "Non-boolean expression used as a condition",
			  BoolType.make(), conditionResult);
	return null;
    }


    public Type visitWhileStmt(WhileStmt whileStmt,
                               Type conditionResult,
                               List<Type> bodyResults)
    {
	helperRequireType(whileStmt.getLocation(), "Non-boolean expression used as a condition",
			  BoolType.make(), conditionResult);
	return null;
    }


    public Type visitExpressionStmt(ExpressionStmt exprStmt,
                                    Type expResult)
    {
	// done
	return null;
    }


    public Type visitWriteStmt(WriteStmt writeStmt, Type valResult)
    {
	// You didn't have to do this one
	if (valResult == null) {
	    error(writeStmt.getLocation(), "Argument to void should be non-void");
	}
	return null;
    }


    public Type visitReadStmt(ReadStmt readStmt)
    {
	// Nothing to do.
	return null;
    }


    public Type visitFunctionCallExp(FunctionCallExp fcallExp,
                                     List<Type> argResults)
    {
	FunctionType functionType = fcallExp.getFunctionSymbol().getType();
	List<Type> formalTypes = functionType.getArgumentTypes();

	if (argResults.size() == formalTypes.size()) {
	    // Need to iterate over both actuals and formals, and make
	    // sure that each is the same. We could use argResults,
	    // but we need the actual nodes so we can call
	    // .getLocation() on them. Instead, we'll iterate over the
	    // nodes for the actuals.
	    Iterator<Type> fit = formalTypes.iterator();
	    Iterator<Expression> ait = fcallExp.getActualIterator();
	    while (fit.hasNext()) {
		assert ait.hasNext();
		Type formalType = fit.next();
		Expression actual = ait.next();
		Type actualType = actual.getType();

		helperRequireType(actual.getLocation(),
				  "Type of actual does not match type of formal",
				  formalType, actualType);
	    }
	    assert !ait.hasNext();
	}
	else {
	    error(fcallExp.getLocation(),
		  fcallExp.getName() + " expects " + formalTypes.size() 
		  + " arguments; given " + argResults.size());
	}

	// Function calls always produce their return type (even if
	// the arguments of the call are the wrong number or do not
	// type check).
	fcallExp.setType(functionType.getReturnType());
	return functionType.getReturnType();
    }


    public Type visitDerefOp(DerefOp derefOp, Type expResult)
    {
	// If dereferencing, better have a pointer.
	try {
	    PointerType ptype = (PointerType) expResult;
	    derefOp.setType(ptype.getInnerType());
	    return ptype.getInnerType();
	}
	catch (ClassCastException e) {
	    error(derefOp.getLocation(),
		  "Dereference operator applied to a non-pointer type");
	    derefOp.setType(ErrorType.make());
	    return ErrorType.make();
	}
    }


    public Type visitNegationOp(NegationOp negationOp, Type expResult)
    {
	Type t = helperUnaryMinus(negationOp.getLocation(), expResult);
	negationOp.setType(t);
	return t;
    }

    public Type visitIncrementOp(IncrementOp incrOp, Type expResult)
    {
	Type t = helperIncrDecr(incrOp.getLocation(), expResult);
	incrOp.setType(t);
	return t;
    }


    public Type visitDecrementOp(DecrementOp decrOp, Type expResult)
    {
	Type t = helperIncrDecr(decrOp.getLocation(), expResult);
	decrOp.setType(t);
	return t;
    }


    public Type visitNotOp(NotOp logNotOp, Type expResult)
    {
	Type t = helperLogicalNot(logNotOp.getLocation(), expResult);
	logNotOp.setType(t);
	return t;
    }


    public Type visitAddrOfOp(AddrOfOp addrOfOp, Type expResult)
    {
	// Anything but error type
	if (expResult == ErrorType.make()) {
	    addrOfOp.setType(ErrorType.make());
	    return ErrorType.make();
	}
	else {
	    Type t = PointerType.make(expResult);
	    addrOfOp.setType(t);
	    return t;
	}
    }

    public Type visitArrayAccessOp(ArrayAccessOp arrayAccessOp,
                                   Type lhsResult,
                                   Type rhsResult)
    {
	if (lhsResult == ErrorType.make() || rhsResult == ErrorType.make()) {
	    arrayAccessOp.setType(ErrorType.make());
	    return ErrorType.make();
	}

	if (!(lhsResult instanceof ArrayType)) {
	    error(arrayAccessOp.getLocation(), "Using [] on non-array");
	    arrayAccessOp.setType(ErrorType.make());
	    return ErrorType.make();
	}

	// You don't have to do this one
	if (rhsResult != IntType.make()) {
	    error(arrayAccessOp.getLocation(), "Index of [] must be integer");
	    arrayAccessOp.setType(ErrorType.make());
	    return ErrorType.make();
	}

	// If a's type is t[], then the type of a[i] t
	ArrayType arrayType = (ArrayType) lhsResult;
	Type inner = arrayType.getInnerType();
	arrayAccessOp.setType(inner);
	return inner;
    }


    public Type visitAndOp(AndOp logAndOp,
                           Type lhsResult,
                           Type rhsResult)
    {
	Type t = helperLogicalBinary(logAndOp.getLocation(),
                                     lhsResult, rhsResult);
	logAndOp.setType(t);
	return t;
    }

    public Type visitOrOp(OrOp logOrOp, Type lhsResult, Type rhsResult)
    {
	Type t = helperLogicalBinary(logOrOp.getLocation(),
                                     lhsResult, rhsResult);
	logOrOp.setType(t);
	return t;
    }


    public Type visitAssignOp(AssignOp assignOp,
                              Type lhsResult,
                              Type rhsResult)
    {
	Type t = helperAssign(assignOp.getLocation(), lhsResult, rhsResult);
	assignOp.setType(t);
	return t;
    }

    public Type visitLessThanOp(LessThanOp lessThanOp,
                                Type lhsResult,
                                Type rhsResult)
    {
	Type t = helperInequality(lessThanOp.getLocation(), lhsResult, rhsResult);
	lessThanOp.setType(t);
	return t;
    }

    public Type visitLessOrEqualOp(LessOrEqualOp lessOrEqualOp,
                                   Type lhsResult,
                                   Type rhsResult)
    {
	Type t = helperInequality(lessOrEqualOp.getLocation(), lhsResult, rhsResult);
	lessOrEqualOp.setType(t);
	return t;
    }


    public Type visitGreaterThanOp(GreaterThanOp greaterThanOp,
                                   Type lhsResult, 
                                   Type rhsResult)
    {
	Type t = helperInequality(greaterThanOp.getLocation(), lhsResult, rhsResult);
	greaterThanOp.setType(t);
	return t;
    }


    public Type visitGreaterOrEqualOp(GreaterOrEqualOp greaterOrEqualOp,
                                      Type lhsResult, 
                                      Type rhsResult)
    {
	Type t = helperInequality(greaterOrEqualOp.getLocation(), lhsResult, rhsResult);
	greaterOrEqualOp.setType(t);
	return t;
    }


    public Type visitEqualsOp(EqualsOp equalsOp, 
                              Type lhsResult, 
                              Type rhsResult)
    {
	Type t = helperEquality(equalsOp.getLocation(), lhsResult, rhsResult);
	equalsOp.setType(t);
	return t;
    }


    public Type visitNotEqualsOp(NotEqualsOp notEqualsOp,
                                 Type lhsResult,
                                 Type rhsResult)
    {
	Type t = helperEquality(notEqualsOp.getLocation(), lhsResult, rhsResult);
	notEqualsOp.setType(t);
	return t;
    }


    public Type visitAddOp(AddOp addOp, Type lhsResult, Type rhsResult)
    {
	Type t = helperArithmetic(addOp.getLocation(), lhsResult, rhsResult);
	addOp.setType(t);
	return t;
    }


    public Type visitSubtractOp(SubtractOp subtractOp, 
                                Type lhsResult, 
                                Type rhsResult)
    {
	Type t = helperArithmetic(subtractOp.getLocation(), lhsResult, rhsResult);
	subtractOp.setType(t);
	return t;
    }


    public Type visitMultiplyOp(MultiplyOp multiplyOp, 
                                Type lhsResult,
                                Type rhsResult)
    {
	Type t = helperArithmetic(multiplyOp.getLocation(), lhsResult, rhsResult);
	multiplyOp.setType(t);
	return t;
    }


    public Type visitDivideOp(DivideOp divideOp, 
                              Type lhsResult, 
                              Type rhsResult)
    {
	Type t = helperArithmetic(divideOp.getLocation(), lhsResult, rhsResult);
	divideOp.setType(t);
	return t;
    }


    public Type visitModOp(ModOp modOp, Type left, Type right)
    {
	if (left == ErrorType.make() || right == ErrorType.make()) {
            modOp.setType(ErrorType.make());
	    return ErrorType.make();
	}

        SourceLocation loc = modOp.getLocation();

	// Applying either an inequality (<, <=, >, >=) operator or a
	// binary arithmetic (+,-,*,/) operator other than mod to a
	// non-numeric operand. (In particular, it is not possible to
	// perform pointer arithmetic.)

	if (!isNumeric(left) || !isNumeric(right)) {
	    error(loc, "Operator can only be applied to numeric types");
            modOp.setType(ErrorType.make());
	    return ErrorType.make();
	}

	// I didn't really give you a specific error here. This is the
	// closest. At this point, we know both operands are either
	// int or double. Both operands of mod must be int.
	if (left == DoubleType.make() || right == DoubleType.make()) {
	    error(loc, "Operator can only be applied to numeric types");
            modOp.setType(ErrorType.make());
	    return ErrorType.make();
	}

        modOp.setType(IntType.make());
	return IntType.make();
    }



    public Type visitNullLit(NullLit nullLit) {
	Type t = NullType.make();
	nullLit.setType(t);
	return t;
    }

    public Type visitBooleanLit(BooleanLit booleanLit) {
	Type t = BoolType.make();
	booleanLit.setType(t);
	return t;
    }

    public Type visitStringLit(StringLit stringLit){
	Type t = StringType.make();
	stringLit.setType(t);
	return t;
    }

    public Type visitIntLit(IntLit intLit) {
	Type t = IntType.make();
	intLit.setType(t);
	return t;
    }

    public Type visitDoubleLit(DoubleLit doubleLit) {
	Type t = DoubleType.make();
	doubleLit.setType(t);
	return t;
    }

    public Type visitVarRefExp(VarRefExp varRefExp) {
	Type t = varRefExp.getVariableSymbol().getType();
	varRefExp.setType(t);
	return t;
    }


    public Type visitBooleanTypeNode(BooleanTypeNode boolType) {
	return null;
    }

    public Type visitIntTypeNode(IntTypeNode intType) {
	return null;
    }

    public Type visitDoubleTypeNode(DoubleTypeNode doubleType) {
	return null;
    }

    public Type visitStringTypeNode(StringTypeNode stringType) {
	return null;
    }

    public Type visitPointerTypeNode(PointerTypeNode pointerType,
                                     Type innerResult)
    {
	return null;
    }

    public Type visitArrayTypeNode(ArrayTypeNode arrayType, Type innerResult)
    {
	return null;
    }
}
