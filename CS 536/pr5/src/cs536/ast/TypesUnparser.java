package cs536.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cs536.ast.AbstractSyntaxTree.*;
import cs536.staticSemantics.types.*;

public class TypesUnparser extends Unparser
{
	public String visitAst(AbstractSyntaxTree ast,
		           List<String> declarationResults)
	{
		return join(declarationResults, "");
	}

	public String visitExpressionStmt(ExpressionStmt exprStmt, String expResult)
	{

		Type type = exprStmt.getExpression().getType();		
		String stringType = (type == null) ? "TODO: TYPE HERE" : type.toString();
		return expResult + "; /* " + stringType + " */\n";
	}

	public String visitGlobalVarDecl(GlobalVarDecl globalVar,
		     String typeResult,
		     String exprResult)
	{
		// exprResult is null if there is no initializer. The
		// identifier is only accessible through 'globalVar'
		String name = globalVar.getName();

		if (exprResult != null) {
			//return typeResult + " " + name + " = " + exprResult + "; /* " + typeResult + " */\n";
			return typeResult + " " + name + " = " + exprResult + ";\n";
		}
		else {
			return typeResult + " " + name + ";\n";
		}
	}


	public String visitVarDeclStmt(VarDeclStmt localVar,
		   String typeResult,
		   String initResult)
	{
		// 'initResult' is null if there is no initializer. The name
		// of the variable is available through 'localVar'
		String name = localVar.getName();

		if (initResult != null) {
			//return typeResult + " " + name + " = " + initResult + "; /* " + typeResult + " */" + "\n";
			return typeResult + " " + name + " = " + initResult + ";\n";
		}
		else {
			return typeResult + " " + name + ";\n";
		}
	}

	public String visitIfStmt(IfStmt ifStmt,
		  String conditionResult,
		  List<String> thenResults,
		  List<String> elseResults)
	{
	return
		//"if(" + conditionResult + ") { /* " + ifStmt.getCondition().getType()  + " */\n"
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
			//"while(" + conditionResult + ") { /* " + whileStmt.getCondition().getType() + " */\n"
			"while(" + conditionResult + ") {\n"
			+ indentAndJoin(bodyResults) +
			"}\n";
	}




}
