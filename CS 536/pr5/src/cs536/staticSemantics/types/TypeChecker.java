package cs536.staticSemantics.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs536.ast.AstVisitor;
import cs536.ast.AbstractSyntaxTree;
import static cs536.ast.AbstractSyntaxTree.*;
import cs536.ast.SourceLocation;
import cs536.staticSemantics.types.*;

public class TypeChecker extends AstVisitor<Type>
{
	private boolean m_Passed = true;

    public boolean passed() {
        return m_Passed;
    }

	
	private Type checkArithmeticOperation(SourceLocation location, Type lhsResult, Type rhsResult)
	{
		if(lhsResult == DoubleType.make())
		{
			if(rhsResult == DoubleType.make() || rhsResult == IntType.make())
			{
				return lhsResult;
			}
			else
			{
				System.out.println(location + ":Operator can only be applied to numeric types");
				m_Passed = false;
				return DoubleType.make();
			}
		}
		else if(lhsResult == IntType.make())
		{
			if(rhsResult == DoubleType.make())
			{
				return rhsResult;
			}	
			else if(rhsResult == IntType.make())
			{
				return lhsResult;
			}
			else
			{
				System.out.println(location + ":Operator can only be applied to numeric types");
				m_Passed = false;
				return IntType.make();
			}
		}
		else
		{		
			System.out.println(location + ":Operator can only be applied to numeric types");
			m_Passed = false;
			if(rhsResult == DoubleType.make())
			{
				return rhsResult;
			}
			else
			{
				return IntType.make();
			}
		}
    }

	
	private Type checkBinaryArithmeticOperation(SourceLocation location, Type lhsResult, Type rhsResult)
	{
		Boolean passed = true;
		if(lhsResult != DoubleType.make() && lhsResult != IntType.make())
		{
			passed = false;
		}

		if(rhsResult != DoubleType.make() && rhsResult != IntType.make())
		{
			passed = false;
		}
		
		if(!passed)
		{		
			System.out.println(location + ":Operator can only be applied to numeric types");
			m_Passed = false;
		}

		return BoolType.make();
	}
	

    public Type visitGlobalVarDecl(GlobalVarDecl globalVar,
                                   Type typeResult, 
                                   Type initResult)
    {
		if(globalVar.hasInitializer())
		{	
			if(typeResult == initResult) {
			}
			else if(typeResult == DoubleType.make() && initResult == IntType.make()) {
			}
			else if(typeResult.isPointer() && initResult == NullType.make())
			{
			}
			else{
				System.out.println(globalVar.getLocation() + ":Type mismatch");
				m_Passed = false;
			}
		}
		return typeResult;//localVar.getType();
    }


    public Type visitAst(AbstractSyntaxTree ast,
                         List<Type> declarationResults)
    {
        return null;
    }


    public Type visitFuncDecl(FuncDecl funcDecl, 
                              Type typeResult,
                              List<Type> formalResults, 
                              List<Type> statementResults)
    {

		Type functionType;
		if(funcDecl.getReturnTypeNode() == null)
			functionType = NullType.make();
		else
        	functionType = funcDecl.getReturnTypeNode().asSemanticType();

		
		Iterator<Statement> statements = funcDecl.getBodyIterator();
		while(statements.hasNext()){
			Statement s = statements.next();
			if(s instanceof ReturnStmt){
				ReturnStmt rs = (ReturnStmt) s;
				Type returnType = rs.getExpression() == null ? NullType.make() : rs.getExpression().getType();
				if(returnType != functionType){
					if(functionType == NullType.make()){
						//Returning a value from a void function
						System.out.println(rs.getLocation() + ":Returning a value from a void function");
					}
					else if(returnType == NullType.make()){
						//Missing return value
						System.out.println(rs.getLocation() + ":Missing return value");
					}
					else{
						System.out.println(rs.getLocation() + ":Bad return type");
					}
					m_Passed = false;
				
				}
				//if they are not equal it is one of the errors
			}
			//check each statement for the kind of statment.
			//if it is a returnstmt, we should check it
		}
			/*
			Expression statement = it_statements.Next();
			if(statement == ReturnType.make()){
				if((ReturnType statement.getType()) == null){
					System.out.println(":Missing return value");
				}
				else if(typeResult == null){
					System.out.println(":Returning a value from a void function");
				}
				else{
				}
				m_Passed = false;
					
			}
		}
*/
	
		return functionType;
    }

    public Type visitFormalArg(FormalArg formalArg, Type typeResult)
    {
		//formalArg.setType(typeResult);
		return typeResult;//formalArg.getType();
    }

    public Type visitVarDeclStmt(VarDeclStmt localVar,
                                 Type typeResult, 
                                 Type initResult)
    {
		//localVar.setType(typeResult);
		if(localVar.hasInitializer())
		{	
			if(typeResult == initResult) {
			}
			else if(typeResult == DoubleType.make() && initResult == IntType.make()) {
			}
			else if(typeResult.isPointer() && initResult == NullType.make())
			{
			}
			else{
				System.out.println(localVar.getLocation() + ":Type mismatch");
				m_Passed = false;
			}
		}
		return typeResult;//localVar.getType();
    }


    public Type visitReturnStmt(ReturnStmt retStmt, Type valResult)
    {
		if(valResult == null)
			return NullType.make();
		else
		
		{
			retStmt.getExpression().setType(valResult);
			return valResult;
    	}
	}


    public Type visitIfStmt(IfStmt ifStmt,
                            Type conditionResult,
                            List<Type> thenResults,
                            List<Type> elseResults)
    {
		if(conditionResult != BoolType.make()){
			System.out.println(ifStmt.getLocation() + ":Non-boolean expression used as a condition");
			m_Passed = false;
		}
		
		ifStmt.getCondition().setType(BoolType.make());
		return ifStmt.getCondition().getType();
    }


    public Type visitWhileStmt(WhileStmt whileStmt,
                               Type conditionResult,
                               List<Type> bodyResults)
    {
		if(conditionResult != BoolType.make()){
			System.out.println(whileStmt.getLocation() + ":Non-boolean expression used as a condition");
			m_Passed = false;
		}
		
		whileStmt.getCondition().setType(BoolType.make());
		return whileStmt.getCondition().getType();
    }


    public Type visitExpressionStmt(ExpressionStmt exprStmt,
                                    Type expResult)
	{
		exprStmt.getExpression().setType(expResult);
		return exprStmt.getExpression().getType();
    }


    public Type visitWriteStmt(WriteStmt writeStmt, Type valResult)
    {
		//This is complete.  We DONT even need to check if the input is not null
		return null;
    }


    public Type visitReadStmt(ReadStmt readStmt)
    {
		//This is complete.  We DONT even need to check if the input is not null
		return null;
    }


    public Type visitFunctionCallExp(FunctionCallExp fcallExp,
                                     List<Type> argResults)
    {
		FunctionType funcType = fcallExp.getFunctionSymbol().getType();
		List<Type> argTypes = funcType.getArgumentTypes();

		if(argTypes.size() != argResults.size())
		{
			System.out.println(fcallExp.getLocation() + ":" + fcallExp.getName()  +  " expects " + argTypes.size() + " arguments; given " + argResults.size());
			m_Passed = false;
		}
		else
		{
			Iterator<Type> it_argResults = argResults.iterator();
			Iterator<Expression> it_actuals = fcallExp.getActualIterator();
			Iterator<Type> it_argTypes = argTypes.iterator();
			while(it_argResults.hasNext() && it_argTypes.hasNext() && it_actuals.hasNext())
			{
				Type argRes = it_argResults.next();
				Expression argExp = it_actuals.next();
				Type argType = it_argTypes.next();
				//if(argRes.getType() != argType)
				if(argRes != argType)
				{
					//System.out.println(fcallExp.getLocation() + ":Type of actual does not match type of formal" );
					System.out.println(argExp.getLocation() + ":Type of actual does not match type of formal" );
					m_Passed = false;
					//System.out.println("Function " + fcallExp.getName() + " variable " + argRes + "   " + argType);
				}
			}
		}
		


		// The name of the function to call is accessible through
		// fcallExp
		// TODO
		//System.out.println("ReturnType:" + fcallExp.getFunctionSymbol().getType().getReturnType().toString());
		//System.out.println("Type:" + fcallExp.getFunctionSymbol().getType().toString());
		fcallExp.setType(fcallExp.getFunctionSymbol().getType().getReturnType());
		//fcallExp.setType(fcallExp.getFunctionSymbol().getType());
		return fcallExp.getType();
    }


    public Type visitDerefOp(DerefOp derefOp, Type expResult)
    {
		if(!expResult.isPointer())
		{
			System.out.println(derefOp.getLocation() + ":Dereference operator applied to a non-pointer type");
			m_Passed = false;
		}
		else
		{
		//			System.out.println("deref pointer " + expResult.toString());
			derefOp.setType(((PointerType)expResult).getInnerType());
		}
		return derefOp.getType();
    }


    public Type visitNegationOp(NegationOp negationOp, Type expResult)
    {
	
		if(expResult != IntType.make() && expResult != DoubleType.make())
		{
			System.out.println(negationOp.getLocation() + ":Attempting to negate something that isn't a number");
			m_Passed = false;
		}
		
		negationOp.setType(expResult);
		return negationOp.getType();
    }

    public Type visitIncrementOp(IncrementOp incrOp, Type expResult)
	{
		if ( expResult != IntType.make() && expResult != DoubleType.make() && !expResult.isPointer())
		{
			System.out.println(incrOp.getLocation() + ":Integer arithmetic operator applied to non-numeric, non-pointer type");
			m_Passed = false;
		}
		else if(!incrOp.getOperand().isL() ){
			System.out.println(incrOp.getLocation() + ":Illegal modification of r-value");
			m_Passed = false;
		}
		
		incrOp.setType(expResult);
		return incrOp.getType();
    }


    public Type visitDecrementOp(DecrementOp decrOp, Type expResult)
    {
		if ( expResult != IntType.make() && expResult != DoubleType.make() && !expResult.isPointer())
		{
			System.out.println(decrOp.getLocation() + ":Integer arithmetic operator applied to non-numeric, non-pointer type");
			m_Passed = false;
		}
		else if(!decrOp.getOperand().isL() ){
			System.out.println(decrOp.getLocation() + ":Illegal modification of r-value");
			m_Passed = false;
		}
		
		decrOp.setType(expResult);
		return decrOp.getType();
    }

    public Type visitNotOp(NotOp logNotOp, Type expResult)
    {
		if(BoolType.make() != expResult){
			System.out.println(logNotOp.getLocation() + ":Logical operator applied to non-bool operand");
			m_Passed = false;
		}
		logNotOp.setType(BoolType.make());
		return logNotOp.getType();
    }


    public Type visitAddrOfOp(AddrOfOp addrOfOp, Type expResult)
    {

		if(!addrOfOp.getOperand().isL() ){
			System.out.println(addrOfOp.getLocation() + ":Cannot take the address of an r-value");
			m_Passed = false;
		}
		return PointerType.make(expResult);
    }

    public Type visitArrayAccessOp(ArrayAccessOp arrayAccessOp,
                                   Type lhsResult,
                                   Type rhsResult)
    {
		if(lhsResult.isArray())
		{
			return ((ArrayType)lhsResult).getInnerType();
		}
		else
		{
			System.out.println(arrayAccessOp.getLocation() + ":Using [] on non-array");
			m_Passed = false;
			return lhsResult;
		}
    }


    public Type visitAndOp(AndOp logAndOp,
                           Type lhsResult,
                           Type rhsResult)
    {
		BoolType bool = BoolType.make();
		if(lhsResult != bool  || rhsResult != bool){
			System.out.println(logAndOp.getLocation() + ":Logical operator applied to non-bool operand");
			m_Passed = false;
		}
		
		logAndOp.setType(bool);
		return logAndOp.getType();
	}

    public Type visitOrOp(OrOp logOrOp, Type lhsResult, Type rhsResult)
    {
		BoolType bool = BoolType.make();
		if(lhsResult != bool  || rhsResult != bool){
			System.out.println(logOrOp.getLocation() + ":Logical operator applied to non-bool operand");
			m_Passed = false;
		}
		
		logOrOp.setType(bool);
		return logOrOp.getType();
    }


    public Type visitAssignOp(AssignOp assignOp,
                              Type lhsResult,
                              Type rhsResult)
    {
		if(!assignOp.getLhsOperand().isL() ){
			System.out.println(assignOp.getLocation() + ":Illegal modification of r-value");
			m_Passed = false;
		}
		//System.out.println("assign = " + lhsResult + " " + rhsResult);
		else if(lhsResult == rhsResult) {
			assignOp.setType(lhsResult);
		}
		else if(lhsResult == DoubleType.make() && rhsResult == IntType.make()) {
			assignOp.setType(DoubleType.make()); 
		}
		else if(lhsResult.isPointer() && rhsResult == NullType.make())
		{
		}
		else{
			System.out.println(assignOp.getLocation() + ":Type mismatch");
			m_Passed = false;
			assignOp.setType(lhsResult);
		
		}

		return assignOp.getType();
    }

    public Type visitLessThanOp(LessThanOp lessThanOp,
                                Type lhsResult,
                                Type rhsResult)
    {
		return checkBinaryArithmeticOperation(lessThanOp.getLocation(), lhsResult, rhsResult);
    }

    public Type visitLessOrEqualOp(LessOrEqualOp lessOrEqualOp,
                                   Type lhsResult,
                                   Type rhsResult)
    {
		return checkBinaryArithmeticOperation(lessOrEqualOp.getLocation(), lhsResult, rhsResult);
    }


    public Type visitGreaterThanOp(GreaterThanOp greaterThanOp,
                                   Type lhsResult, 
                                   Type rhsResult)
    {
		greaterThanOp.setType( checkBinaryArithmeticOperation(greaterThanOp.getLocation(), lhsResult, rhsResult));
	//	System.out.println("GreaterThan = " + greaterThanOp.getType());
		return greaterThanOp.getType();
    }


    public Type visitGreaterOrEqualOp(GreaterOrEqualOp greaterOrEqualOp,
                                      Type lhsResult, 
                                      Type rhsResult)
    {
		return checkBinaryArithmeticOperation(greaterOrEqualOp.getLocation(), lhsResult, rhsResult);
    }


    public Type visitEqualsOp(EqualsOp equalsOp, 
                              Type lhsResult, 
                              Type rhsResult)
    {
		if	(
				(lhsResult == rhsResult)
				|| (lhsResult == DoubleType.make() && rhsResult == IntType.make()) 
			   	|| (lhsResult == IntType.make() && rhsResult == DoubleType.make())
			)	{;}
		else{
			System.out.println(equalsOp.getLhsOperand().getLocation() + ":Type mismatch");
			m_Passed = false;
		}
		equalsOp.setType(BoolType.make());
		return equalsOp.getType();

    }


    public Type visitNotEqualsOp(NotEqualsOp notEqualsOp,
                                 Type lhsResult,
                                 Type rhsResult)
    {
		if	(
				(lhsResult == rhsResult)
				|| (lhsResult == DoubleType.make() && rhsResult == IntType.make()) 
			   	|| (lhsResult == IntType.make() && rhsResult == DoubleType.make())
			)	{;}
		else{
			System.out.println(notEqualsOp.getLhsOperand().getLocation() + ":Type mismatch");
			m_Passed = false;
		}

		return BoolType.make();
    }


    public Type visitAddOp(AddOp addOp, Type lhsResult, Type rhsResult)
    {	
		return checkArithmeticOperation(addOp.getLocation(), lhsResult, rhsResult);
    }


    public Type visitSubtractOp(SubtractOp subtractOp, 
                                Type lhsResult, 
                                Type rhsResult)
    {	
		return checkArithmeticOperation(subtractOp.getLocation(), lhsResult, rhsResult);
    }


    public Type visitMultiplyOp(MultiplyOp multiplyOp, 
                                Type lhsResult,
                                Type rhsResult)
	{	
		return checkArithmeticOperation(multiplyOp.getLocation(), lhsResult, rhsResult);
	}


    public Type visitDivideOp(DivideOp divideOp, 
                              Type lhsResult, 
                              Type rhsResult)
    {
		return checkArithmeticOperation(divideOp.getLocation(), lhsResult, rhsResult);
	}

    public Type visitModOp(ModOp modOp, Type lhsResult, Type rhsResult)
    {
		if(lhsResult != IntType.make() || rhsResult != lhsResult)
		{
			System.out.println(modOp.getLocation() + ":Operator can only be applied to numeric types");
			m_Passed = false;
		}
		return IntType.make();
    }



    public Type visitNullLit(NullLit nullLit) {
		return NullType.make();
    }

    public Type visitBooleanLit(BooleanLit booleanLit) {
		return BoolType.make();
    }

    public Type visitStringLit(StringLit stringLit){
		return StringType.make();
    }

    public Type visitIntLit(IntLit intLit) {
		return IntType.make();
    }

    public Type visitDoubleLit(DoubleLit doubleLit) {
		return DoubleType.make();
    }

    public Type visitVarRefExp(VarRefExp varRefExp) {
		Type type = varRefExp.getVariableSymbol().getType();
		return type;
    }


    public Type visitBooleanTypeNode(BooleanTypeNode boolType) {
		return BoolType.make();
    }

    public Type visitIntTypeNode(IntTypeNode intType) {
		return IntType.make();
    }

    public Type visitDoubleTypeNode(DoubleTypeNode doubleType) {
		return DoubleType.make();
    }

    public Type visitStringTypeNode(StringTypeNode stringType) {
		return StringType.make();
    }

    public Type visitPointerTypeNode(PointerTypeNode pointerType,
                                     Type innerResult)
    {
		return PointerType.make(innerResult);
    }

    public Type visitArrayTypeNode(ArrayTypeNode arrayType, Type innerResult)
    {
		return ArrayType.make(innerResult, arrayType.getSize());
    }
}
