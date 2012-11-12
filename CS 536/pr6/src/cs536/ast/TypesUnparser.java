package cs536.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cs536.ast.AbstractSyntaxTree.*;
import cs536.staticSemantics.types.Type;

public class TypesUnparser extends Unparser
{
    public String visitExpressionStmt(ExpressionStmt exprStmt,
				      String expResult)
    {
	Type t = exprStmt.getExpression().getType();
	if (t == null) {
	    return expResult + "; /* XXXXX */\n";
	}
	else {
	    return expResult + "; /* " + t + " */\n";
	}
    }

}
