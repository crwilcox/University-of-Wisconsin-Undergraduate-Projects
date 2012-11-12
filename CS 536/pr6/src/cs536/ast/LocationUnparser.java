package cs536.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cs536.ast.AbstractSyntaxTree.*;

public class LocationUnparser extends Unparser
{

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


    public String visitFormalArg(FormalArg formalArg, String typeResult)
    {
	// The name of the argument is available through 'formalArg'.
	return typeResult + " " + formalArg.getName()
            + " /*" + formalArg.getLocalSymbol().getType().getSize() + ", offset " + formalArg.getLocalSymbol().getOffset() + "*/";
    }

    public String visitVarDeclStmt(VarDeclStmt localVar,
				   String typeResult, 
				   String initResult)
    {
	// 'initResult' is null if there is no initializer. The name
	// of the variable is available through 'localVar'
        String name = localVar.getName();
        
	if (initResult != null) {
	    return typeResult + " " + name + " = " + initResult
                 + "; /*" + localVar.getLocalSymbol().getType().getSize() + ", offset " + localVar.getLocalSymbol().getOffset() + "*/\n";
	}
	else {
	    return typeResult + " " + name
                 + "; /*" + localVar.getLocalSymbol().getType().getSize() + ", offset " + localVar.getLocalSymbol().getOffset() + "*/\n";
	}
    }


    public String visitReadStmt(ReadStmt readStmt)
    {
	// the name of the variable to read into is accessible through
	// readStmt.
	return
            "read "+ readStmt.getDestinationName()
            + "/*" + readStmt.getDestinationSymbol().getAnnotationString() + "*/"
            + ";\n";
    }


    public String visitFunctionCallExp(FunctionCallExp fcallExp,
				       List<String> argResults)
    {
	// The name of the function to call is accessible through
	// fcallExp
	return
            fcallExp.getName()
            + "/*" + fcallExp.getFunctionSymbol().getDeclarationLocation() + "*/"
            + "(" + join(argResults, ", ") + ")";
    }

    public String visitVarRefExp(VarRefExp varRefExp) {
	return varRefExp.getName()
            + "/*" + varRefExp.getVariableSymbol().getAnnotationString() + "*/";
    }
}
