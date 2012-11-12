package cs536.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

import cs536.staticSemantics.symbolTable.SymbolTable;
import cs536.staticSemantics.symbolTable.Symbol;
import cs536.staticSemantics.symbolTable.VariableSymbol;
import cs536.staticSemantics.symbolTable.FunctionSymbol;
import cs536.staticSemantics.symbolTable.RedeclarationException;
import cs536.staticSemantics.symbolTable.EmptyException;

import cs536.staticSemantics.types.Type;
import cs536.staticSemantics.types.IntType;
import cs536.staticSemantics.types.BoolType;
import cs536.staticSemantics.types.StringType;
import cs536.staticSemantics.types.DoubleType;
import cs536.staticSemantics.types.ArrayType;
import cs536.staticSemantics.types.PointerType;
import cs536.staticSemantics.types.FunctionType;


/**
   This abstract-syntax tree represents a program as a list of global
   declarations. This file contains both the 'AbstractSyntaxTree' class,
   which is basically a wrapper around the root of the AST, and all of the
   node types such as 'IfStmt'.

   To get easy access to all of the individual classes, use
     import static cs536.ast.AbstractSyntaxTree.*;

   All of the node types are defined in this file for convenience. The
   interpretations should be reasonably obvious; ask if there is something
   that isn't clear.

   
   The following is the class heirarchy of AST nodes provided in this
   file. Also indicated is any children that the nodes have. (Inherited
   fields aren't shown explicitly.) All nodes that are a base class of
   another node (such as AstNode or Statement) are abstract. In addition, all
   nodes have a source location.

     AstNode
       TypeNode
         BooleanTypeNode
         IntTypeNode
         DoubleTypeNode
         StringTypeNode
         PointerTypeNode           [inner type, e.g. "int" for "int*"]
         ArrayTypeNode             [inner type; size]

       FormalArg               [name; type]

       GlobalDeclaration
         GlobalVarDecl         [name; type; initializer]
         FuncDecl              [name; return type; formal arguments; body]

       Statement
         VarDeclStmt           [name; type; initializer]
         ReturnStmt            [value]
         IfStmt                [condition; true body; false body]
         WhileStmt             [condition; body]
         ExpressionStmt        [expression]
         WriteStmt             [value]
         ReadStmt              [destination]

       Expression
         FunctionCallExp       [callee name; actual arguments]
         UnaryOp               [operand]
           DerefOp
           NegationOp
             PreOrPostOp         [isPrefix]
             IncrementOp
             DecrementOp
           NotOp
           AddrOfNot
         BinaryOp              [lhs operand; rhs operand]
           AndOp
           OrOp
           ArrayAccessOp
           AssignOp
           LessThanOp
           LessOrEqualOp
           GreaterThanOp
           GreaterOrEqualOp
           EqualsOp
           NotEqualsOp
           AddOp
           SubtractOp
           MultiplyOp
           DivideOp
           ModOp
         LiteralExp
           NullLit
           BooleanLit          [value]
           StringLit           [value]
           IntLit              [value]
           DoubleLit           [value]
         VarRefExp             [name]

 */
public class AbstractSyntaxTree
{
   /////////////////////////////////////////////////////
    // The AST itself
   
    private final List<GlobalDeclaration> decls;

    public AbstractSyntaxTree(List<GlobalDeclaration> l) {
        decls = copyUnmodifiable(l);
    }

    public <T> T accept(AstVisitor<T> v) {
        v.preVisit(this);
        
        List results = new ArrayList();
        Iterator<GlobalDeclaration> it = decls.iterator();
        while(it.hasNext()) {
            GlobalDeclaration d = it.next();
            results.add(d.accept(v));
        }

        return (T) v.visit(this, results);
    }

    
    // This is the big function for this project. 
    public boolean resolveNames() {
		// Build the Symbol Table
		SymbolTable table = new SymbolTable();
		// Call Resolvenames on all nodes
		Iterator<GlobalDeclaration> it = decls.iterator();
		boolean pass = true;
		while(it.hasNext())
		{
			GlobalDeclaration d = it.next();
			if(!d.resolveNames(table))
				pass = false;
		}
        // I'm returning true here; but really neither answer is terribly
        // "correct".
        return pass;
    }

    
    /** Returns an unmodifiable copy of the given list.

        Just a utility function.
     */
    public static <T> List<T> copyUnmodifiable(List<? extends T> list) {
        if (list == null) {
            return null;
        }
        else {
            ArrayList<T> copy = new ArrayList<T>(list);
            return Collections.unmodifiableList(copy);
        }
    }

    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    // Ast nodes
    ////////////////////////////////////////////////////////////////
    
    /**
       The base of all AST nodes; this stores location information
       and enforces the interface for the visitor.
    */
    public abstract static class AstNode
    {
        private final SourceLocation sloc;

        public AstNode(SourceLocation loc) {
            sloc = loc;
        }

        public SourceLocation getLocation() {
            return sloc;
        }

        // Each concrete node type needs to implement
        // the accept method to handle visitation
        public abstract Object accept(AstVisitor v);

        // And this functon, to handle name analysis
        // TODO Change to abstract when you think you're done
        public boolean resolveNames(SymbolTable table){
		return true;
		} 
    }

    public abstract static class Statement extends AstNode
    {
        public Statement(SourceLocation loc) {
            super(loc);
        }
    }

    public abstract static class Expression extends AstNode
    {
        public Expression(SourceLocation loc) {
            super(loc);
        }
    }

    // Global declarations are really just statements, but having
    // separate node types ensures that no invalid statement types
    // can be parsed as top-level entities.
    public abstract static class GlobalDeclaration extends AstNode
    {
        public GlobalDeclaration(SourceLocation loc) {
            super(loc);
        }
    }

    
    /**
       These are global variable declarations.  They track
       no data beyond what a local varaible declaration does,
       but splitting them up simplifies some semantic checking
       later.

       The initializer expression may be null if the declaration
       has no initializer.
    */
    public static class GlobalVarDecl extends GlobalDeclaration
    {
        private final TypeNode type;
        private final String name;
        private final Expression initializer;
		public int offset;

        public GlobalVarDecl(SourceLocation loc, TypeNode t,
                             String varName, Expression init)
        {
            super(loc);

            type = t;
            name = varName;
            initializer = init;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object typeResult = type.accept(v);
            Object exprResult = null;
            if(initializer != null) {
                exprResult = initializer.accept(v);
            }
            return v.visit(this, typeResult, exprResult);
        }

        public boolean hasInitializer() {
            return initializer != null;
        }

        /** Null if the initializer is omitted */
        public Expression getInitializer() {
            return initializer;
        }

        public String getName() {
            return name;
        }

        public TypeNode getTypeNode() {
            return type;
        }


        public boolean resolveNames(SymbolTable table) {
            // This is a declaration. Probably should declare something in
            // the symbol table.
			
			try {
				this.offset = table.declare(new VariableSymbol(name, type.asSemanticType(), this.getLocation(), true)); 
				return true;
			}
			catch(RedeclarationException e){
		 		Symbol symbol = table.lookup(name);							
				if(symbol != null)
					System.out.println(this.getLocation() + ":" + name + " was previously defined at " + symbol.getDeclarationLocation());
				return false;
			}
		 }
    }

    
    /**
       A top-level function declaration.  I suggest designing things so
       that a null return type indicates that the function is void.
       Keeping void out of the explicit type hierarchy (i.e. not
       creating a 'class VoidType extends TypeNode') makes it impossible
       to create types such as void*, which are not permitted in MinC.
       This will simplify type checking later.
    */
    public static class FuncDecl extends GlobalDeclaration
    {
        // a NULL return type indicates void functions
        private final TypeNode returnType;
        private final String funcName;
        private final List<FormalArg> formalArgs;
        private final List<Statement> statements;

        public FuncDecl(SourceLocation loc, TypeNode rtype, String name,
                        List<FormalArg> args, List<Statement> body)
        {
            super(loc);

            returnType = rtype;
            funcName = name;
            formalArgs = copyUnmodifiable(args);
            statements = copyUnmodifiable(body);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object typeResult = null;
            if(returnType != null)
                typeResult = returnType.accept(v);
            List formalResults = new ArrayList();
            List statementResults = new ArrayList();

            Iterator<FormalArg> argIter = formalArgs.iterator();
            Iterator<Statement> stmtIter = statements.iterator();

            while(argIter.hasNext()) {
                FormalArg arg = argIter.next();
                formalResults.add(arg.accept(v));
            }

            while(stmtIter.hasNext()) {
                Statement s = stmtIter.next();
                statementResults.add(s.accept(v));
            }

            return v.visit(this, typeResult, formalResults, statementResults);
        }

        /** Return the type of the function return value will be null if
         * the function is void
         */
        public TypeNode getReturnTypeNode() {
            return returnType;
        }

        public String getName() {
            return funcName;
        }

        public List<FormalArg> getFormalArguments() {
            return formalArgs;
        }

        public List<Statement> getBody() {
            return statements;
        }

        public Iterator<FormalArg> getFormalIterator() {
            return formalArgs.iterator();
        }

        public Iterator<Statement> getBodyIterator() {
            return statements.iterator();
        }

        
        public  boolean resolveNames(SymbolTable table) {
            // We are defining function foo().
            //  Step 1: Add foo() to the symbol table
            //  Step 2: Analyze the names in the body
            // We continue with step 2 even if foo() is previously declared.

            // Step 1: Add foo() to the symbol table. To know what type it
            // has, we need to know the types of the arguments and the type
            // of the return value. You'll have to get both. Then you can call:
            //  FunctionType typeOfThisFunction = FunctionType.make(returnSemanticType, argumentSemanticTypeList);
            // and use that type when declaring this function.
			
			//System.out.println("RESOLVE: FuncDecl");	
			
			//Converts FormalArgs to Types
			boolean pass = true;
			ArrayList<Type> argList = new ArrayList<Type>();
			for(FormalArg fa : this.getFormalArguments()) 
				argList.add(fa.getTypeNode().asSemanticType());
		
			try
			{
				String Name = this.getName();	
				SourceLocation location = this.getLocation();
				Type t  = null;
				TypeNode nt = this.getReturnTypeNode();
				if(nt != null)
					t = nt.asSemanticType();

				FunctionType ft = FunctionType.make(t,argList);
				table.declare(new FunctionSymbol(Name, ft, location), false);
			}
			catch(RedeclarationException e){
				Symbol symbol = table.lookup(this.getName());
				System.out.println(this.getLocation() + ":" + this.getName() + " was previously defined at " + symbol.getDeclarationLocation());
				pass = false;
			}

            // Step 2: analyze the names in the body. Don't forget to add a
            // new scope.
			table.startScope(true);
			
			int currOff = 0;		
			Iterator<FormalArg> argIter = formalArgs.iterator();
			while(argIter.hasNext())
			{
				FormalArg arg = argIter.next();
				arg.offset = currOff;
				currOff +=	arg.getTypeNode().asSemanticType().getSize();
				if(!arg.resolveNames(table))
					pass = false;
			}

			Iterator<Statement> stmtIter = statements.iterator();
			while(stmtIter.hasNext())
			{
				Statement s = stmtIter.next();
				if(!s.resolveNames(table))
					pass = false;
			}

			table.endScope();

			return pass;
        }
    }
   

    
    /**
       Function declarations contain a list of these as formal parameters.
    */
    public static class FormalArg extends AstNode
    {
        private final TypeNode argumentType;
        private final String name;
		public int offset;

        public FormalArg(SourceLocation loc, TypeNode t, String argName)
        {
            super(loc);

            argumentType = t;
            name = argName;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object typeResult = argumentType.accept(v);
            return v.visit(this, typeResult);
        }

        public TypeNode getTypeNode() {
            return argumentType;
        }

        public String getName() {
            return name;
        }


		public boolean  resolveNames(SymbolTable table)
		{
			
			try {
				table.declare(new VariableSymbol(name, argumentType.asSemanticType(), this.getLocation(), false), false); 
				return true;
			}
			catch(RedeclarationException e){
				Symbol symbol = table.lookup(name);
				if(symbol != null)
					System.out.println(this.getLocation() + ":" + name + " was previously defined at " + symbol.getDeclarationLocation());
				return false;
			}
		}
    }

    
    // Statements

    /**
       Like globals, the initializer expression may be null if the
       declaration has no initializer. (cf. Globalecl)
    */
    public static class VarDeclStmt extends Statement
    {
        private final TypeNode type;
        private final String name;
        private final Expression initializer;
		public int offset;

        public VarDeclStmt(SourceLocation loc, TypeNode t,
                           String varName, Expression init)
        {
            super(loc);

            type = t;
            name = varName;
            initializer = init;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object typeResult = type.accept(v);
            Object exprResult = null;
            if(initializer != null) {
                exprResult = initializer.accept(v);
            }
            return v.visit(this, typeResult, exprResult);
        }

        public boolean hasInitializer() {
            return initializer != null;
        }

        // Null if there is no initializer
        public Expression getInitializer() {
            return initializer;
        }

        public String getName() {
            return name;
        }

        public TypeNode getTypeNode() {
            return type;
        }


        public boolean resolveNames(SymbolTable table) {
            // This is a declaration. Probably should declare something in
            // the symbol table.
			boolean pass = true;
			if(initializer != null)
			{
				//System.out.println("HERE");
				if(!initializer.resolveNames(table))
					pass = false;
			}

			try {
				this.offset = table.declare(new VariableSymbol(name, type.asSemanticType(), this.getLocation(), false)); 
			}
			catch(RedeclarationException e){
				Symbol symbol = table.lookup(name);
				if(symbol != null)
					System.out.println(this.getLocation() + ":" + name + " was previously defined at " + symbol.getDeclarationLocation());
				pass = false;
			}

			return pass;
		}
    }

    
    /**
       I suggest setting the value expression to null if the return statement
       does not have an attached expression (is just 'return;').
    */
    public static class ReturnStmt extends Statement
    {
        // May be null
        private final Expression value;

        public ReturnStmt(SourceLocation loc, Expression val) {
            super(loc);

            value = val;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object valResult = null;
            if(value != null)
                {
                    valResult = value.accept(v);
                }

            return v.visit(this, valResult);
        }

        /** Null if no expression */
        public Expression getExpression() {
            return value;
        }

        public boolean hasExpression() {
            return value != null;
        
		}

		public boolean resolveNames(SymbolTable table)
		{
			//System.out.println("RESOLVE: return stmt");
			boolean pass = true;
			if(value != null)
			{
				if(!value.resolveNames(table))
					pass = false;
			}

			return pass;
		}
    }

    
    /**
       I suggest designing things so that the list of statements comprising
       the else branch of the if statement is an empty list if there is no
       else branch in the original source.
    */
    public static class IfStmt extends Statement
    {
        private final Expression condition;
        private final List<Statement> thenBranch;
        private final List<Statement> elseBranch;

        public IfStmt(SourceLocation loc, Expression cond,
                      List<Statement> trueBody, List<Statement> falseBody)
        {
            super(loc);

            condition = cond;
            thenBranch = copyUnmodifiable(trueBody);
            elseBranch = copyUnmodifiable(falseBody);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object conditionResult = condition.accept(v);
            List thenResults = new ArrayList();
            List elseResults = null;
            if(elseBranch != null)
                elseResults = new ArrayList();

            Iterator<Statement> it = thenBranch.iterator();

            while(it.hasNext()) {
                Statement s = it.next();
                thenResults.add(s.accept(v));
            }

            if(elseBranch != null) {
                it = elseBranch.iterator();

                while(it.hasNext())
                    {
                        Statement s = it.next();
                        elseResults.add(s.accept(v));
                    }
            }

            return v.visit(this, conditionResult, thenResults, elseResults);
        }

        public Expression getCondition() {
            return condition;
        }

        public List<Statement> getTrueBody() {
            return thenBranch;
        }

        public List<Statement> getFalseBody() {
            return elseBranch;
        }

        public Iterator<Statement> getTrueIterator() {
            return thenBranch.iterator();
        }

        public Iterator<Statement> getFalseIterator() {
            return elseBranch.iterator();
        }


        public boolean resolveNames(SymbolTable table) {
            // Call resolveNames on the condition, true branch, and false
            // branch successively. When we do each branch, they are each in
            // their own scope.
			boolean pass = true;

			if(!condition.resolveNames(table))
				pass = false;

			//iterate over the statements in if
            table.startScope();
			Iterator<Statement> it = thenBranch.iterator();

            while(it.hasNext()) {
                Statement s = it.next();
				if(!s.resolveNames(table))
					pass = false;
            }
			table.endScope();

			//if an else, iterate over the statements in it
            if(elseBranch != null) {
				table.startScope();
                it = elseBranch.iterator();
                while(it.hasNext())
                    {
                        Statement s = it.next();
						if(!s.resolveNames(table))
							pass = false;
                    }
				table.endScope();

			}
			return pass;
        }
    }

    
    public static class WhileStmt extends Statement
    {
        private final Expression condition;
        private final List<Statement> body;

        public WhileStmt(SourceLocation loc, Expression cond, List<Statement> stmts)
        {
            super(loc);

            condition = cond;
            body = copyUnmodifiable(stmts);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object conditionResult = condition.accept(v);
            List bodyResults = new ArrayList();

            Iterator<Statement> it = body.iterator();

            while(it.hasNext()) {
                Statement s = it.next();
                bodyResults.add(s.accept(v));
            }

            return v.visit(this, conditionResult, bodyResults);
        }

        public Expression getCondition() {
            return condition;
        }

        public List<Statement> getBody() {
            return body;
        }

        public Iterator<Statement> getBodyIterator() {
            return body.iterator();
        }


        public boolean resolveNames(SymbolTable table) {
            // Call resolveNames on the condition, true branch, and false
            // branch successively. When we do each branch, they are each in
            // their own scope.
			boolean pass = true;

			if(!condition.resolveNames(table))
				pass = false;

			//iterate over the statements in if
            table.startScope();
			Iterator<Statement> it = body.iterator();

            while(it.hasNext()) {
                Statement s = it.next();
				if(!s.resolveNames(table))
					pass = false;
            }
			table.endScope();

			return pass;
    	}
	}

    /** This is for statements that are just an expression. Note that
        something like C does *not* have an actual assignment *statement*: it
        has an expression statement, and the expression is an assignemnt
        expression. We follow this.
    */
    public static class ExpressionStmt extends Statement
    {
        private final Expression exp;

        public ExpressionStmt(SourceLocation loc, Expression e) {
            super(loc);

            exp = e;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object expResult = exp.accept(v);
            return v.visit(this, expResult);
        }

        public Expression getExpression() {
            return exp;
        }


        public boolean resolveNames(SymbolTable table) {
			boolean pass = true;

		 	if(!exp.resolveNames(table))
				pass = false;

			return pass;
        }
    }

    
    public static class WriteStmt extends Statement
    {
        private final Expression value;

        public WriteStmt(SourceLocation loc, Expression val) {
            super(loc);

            value = val;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object valResult = value.accept(v);
            return v.visit(this, valResult);
        }

        public Expression getExpression() {
            return value;
        }


        public boolean resolveNames(SymbolTable table) {
            boolean pass = true;
			
			if(!value.resolveNames(table))
				pass = false;

			return pass;
        }
    }

    
    public static class ReadStmt extends Statement
    {
        private final String destination;
		private VariableSymbol symbol;

        public ReadStmt(SourceLocation loc, String destVarName) {
            super(loc);

            destination = destVarName;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }

        public String getDestinationName() {
            return destination;
        }

		public VariableSymbol getVariableSymbol(){
			return symbol;
		}

        public boolean resolveNames(SymbolTable table) {
            boolean pass = true;
			
			// another one in here somewhere. Hmmmm.
			Symbol VarSymbol = table.lookup(destination);
			//System.out.println("RESOLVE: VarRefExp");			
			if(VarSymbol == null)
			{
				//throw new EmptyException(); 
				System.err.println(this.getLocation() + ":" + destination + " is undefined");
				pass = false;
			}
			else if(VarSymbol instanceof FunctionSymbol)
			{
				//throw new Exception("Attempt to use function other than by calling it");
				System.err.println("Not calling a function: row:col:Attempt to use function ident other than by calling it");
				pass = false;
			}
			else if(VarSymbol instanceof VariableSymbol)
				symbol = (VariableSymbol) VarSymbol;

			return pass;
        }

    }

    ////////////////////////////////////////////////////////////////
    // Expressions
    
    public static class FunctionCallExp extends Expression
    {
        private final String funcName;
        private final List<Expression> arguments;

        private FunctionSymbol funcSymbol;

        public FunctionCallExp(SourceLocation loc, String name,
                               List<Expression> actuals)
        {
            super(loc);

            funcName = name;
            arguments = copyUnmodifiable(actuals);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            List results = new ArrayList();
            Iterator<Expression> it = arguments.iterator();
            while(it.hasNext()) {
                Expression ex = it.next();
                results.add(ex.accept(v));
            }

            return v.visit(this, results);
        }

        public String getName() {
            return funcName;
        }

        public List<Expression> getActualArguments() {
            return arguments;
        }

        public Iterator<Expression> getActualIterator() {
            return arguments.iterator();
        }

        public FunctionSymbol getFunctionSymbol() {
            return funcSymbol;
        }


        public boolean resolveNames(SymbolTable table) {
            // To analyze a function call like foo(1, x), we analyze the
            // parameters as well as the name of the function itself.
    		boolean pass = true;
			
			Symbol symbol = table.lookup(funcName);
			if(symbol == null)
			{
				//throw new EmptyException(); 
				System.out.println(this.getLocation() + ":" + funcName  + " is undefined");
				pass = false;
			}
			if(symbol instanceof VariableSymbol)
			{
				//throw new Exception("Attempt to use function other than by calling it");
				System.out.println("Not calling a function: row:col:Attempt to use function ident other than by calling it");
				pass = false;
			}
			else if(symbol instanceof FunctionSymbol)
				this.funcSymbol = (FunctionSymbol) symbol;
		
			return pass;
		}
    }

    
    public abstract static class UnaryOp extends Expression
    {
        private final Expression ex;

        public UnaryOp(SourceLocation loc, Expression expr) {
            super(loc);
            ex = expr;
        }

        public Expression getOperand() {
            return ex;
        }


        public boolean resolveNames(SymbolTable table) {
			boolean pass = true;
			if(!ex.resolveNames(table))
				pass = false;

			return pass;
        }
    }

    
    public static class DerefOp extends UnaryOp
    {
        public DerefOp(SourceLocation loc, Expression ex) {
            super(loc, ex);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object operandResult = getOperand().accept(v);
            return v.visit(this, operandResult);
        }
    }

    
    public static class NegationOp extends UnaryOp
    {
        public NegationOp(SourceLocation loc, Expression ex) {
            super(loc, ex);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object operandResult = getOperand().accept(v);
            return v.visit(this, operandResult);
        }
    }

    
    public abstract static class PreOrPostOp extends UnaryOp
    {
        private final boolean isPrefix;

        public PreOrPostOp(SourceLocation loc, Expression ex, boolean prefix) {
            super(loc, ex);
            isPrefix = prefix;
        }

        public boolean isPrefix() {
            return isPrefix;
        }
    }

    
    public static class IncrementOp extends PreOrPostOp
    {
        public IncrementOp(SourceLocation loc, Expression ex, boolean prefix)
        {
            super(loc, ex, prefix);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object operandResult = getOperand().accept(v);
            return v.visit(this, operandResult);
        }
    }

    
    public static class DecrementOp extends PreOrPostOp
    {
        public DecrementOp(SourceLocation loc, Expression ex, boolean prefix) {
            super(loc, ex, prefix);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object operandResult = getOperand().accept(v);
            return v.visit(this, operandResult);
        }
    }

    public static class NotOp extends UnaryOp
    {
        public NotOp(SourceLocation loc, Expression ex) {
            super(loc, ex);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object operandResult = getOperand().accept(v);
            return v.visit(this, operandResult);
        }
    }
    

    public static class AddrOfOp extends UnaryOp
    {
        public AddrOfOp(SourceLocation loc, Expression ex) {
            super(loc, ex);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object operandResult = getOperand().accept(v);
            return v.visit(this, operandResult);
        }
    }

    public abstract static class BinaryOp extends Expression
    {
        private final Expression lhs;
        private final Expression rhs;

        public BinaryOp(SourceLocation loc, Expression lhsOp, Expression rhsOp)
        {
            super(loc);
            lhs = lhsOp;
            rhs = rhsOp;
        }

        public Expression getRhsOperand() {
            return rhs;
        }

        public Expression getLhsOperand() {
            return lhs;
        }


        public boolean resolveNames(SymbolTable table) {
			boolean pass = true;
			if(!lhs.resolveNames(table))
				pass = false;

			if(!rhs.resolveNames(table))
				pass = false;
		
			return pass;
        }
    }

    public static class AndOp extends BinaryOp
    {
        public AndOp(SourceLocation loc, Expression lhs, Expression rhs) {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class ArrayAccessOp extends BinaryOp
    {
        public ArrayAccessOp(SourceLocation loc, Expression lhs, Expression rhs) {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class OrOp extends BinaryOp
    {
        public OrOp(SourceLocation loc, Expression lhs, Expression rhs) {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class AssignOp extends BinaryOp
    {
        public AssignOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v)   {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class LessThanOp extends BinaryOp
    {
        public LessThanOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class LessOrEqualOp extends BinaryOp
    {
        public LessOrEqualOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class GreaterThanOp extends BinaryOp
    {
        public GreaterThanOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class GreaterOrEqualOp extends BinaryOp
    {
        public GreaterOrEqualOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class EqualsOp extends BinaryOp
    {
        public EqualsOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class NotEqualsOp extends BinaryOp
    {
        public NotEqualsOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class AddOp extends BinaryOp
    {
        public AddOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }
    

    public static class SubtractOp extends BinaryOp
    {
        public SubtractOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }
    

    public static class MultiplyOp extends BinaryOp
    {
        public MultiplyOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class DivideOp extends BinaryOp
    {
        public DivideOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }

    
    public static class ModOp extends BinaryOp
    {
        public ModOp(SourceLocation loc, Expression lhs, Expression rhs)
        {
            super(loc, lhs, rhs);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object lhsResult = getLhsOperand().accept(v);
            Object rhsResult = getRhsOperand().accept(v);

            return v.visit(this, lhsResult, rhsResult);
        }
    }
    

    public abstract static class LiteralExp extends Expression
    {
        public LiteralExp(SourceLocation loc)
        {
            super(loc);
        }
    }
    

    public static class NullLit extends LiteralExp
    {
        public NullLit(SourceLocation loc)
        {
            super(loc);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }
    }
    

    public static class BooleanLit extends LiteralExp
    {
        private final boolean value;

        public BooleanLit(SourceLocation loc, boolean val) {
            super(loc);

            value = val;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }

        public boolean getValue() {
            return value;
        }
    }

    
    public static class StringLit extends LiteralExp
    {
        private final String value;

        public StringLit(SourceLocation loc, String val) {
            super(loc);
            value = val;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }

        public String getValue() {
            return value;
        }
    }
    

    /**
       Take a long instead of an int since this could be a negated INT_MIN,
       which isn't representable by an int.
    */
    public static class IntLit extends LiteralExp
    {
        private final long value;

        public IntLit(SourceLocation loc, long val) {
            super(loc);
            value = val;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }

        public long getValue() {
            return value;
        }
    }

    
    public static class DoubleLit extends LiteralExp
    {
        private final double value;

        public DoubleLit(SourceLocation loc, double val) {
            super(loc);
            value = val;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }

        public double getValue() {
            return value;
        }
    }

    /** Use of a variable in an expression (e.g. x+1 is
              AddOp
            /       \
        VarRefExp  IntLit
    */
    public static class VarRefExp extends Expression
    {
        private final String name;
        private VariableSymbol symbol;

        public VarRefExp(SourceLocation loc, String s) {
            super(loc);
            name = s;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }

        public String getName() {
            return name;
        }

        public VariableSymbol getVariableSymbol() {
            return symbol;
        }


        public boolean resolveNames(SymbolTable table) {
            // I'll give you some help on this one, because I think the best
            // way to do it is a bit strange. Suppose we have an expression
            // like "x + 1"; this node covers the "x" part. There are two
            // possible errors we need to detect at this point. The first is
            // if 'x' is totally undeclared, and the second is if 'x' is a
            // function instead of a variable.

            // If we don't find 'x' in the symbol table at all, that
            // indicates the first problem. But if 'x' is in the symbol
            // table, what we get back is a 'Symbol' object. 'Symbol' has two
            // subclasses, 'VariableSymbol' and 'FunctionSymbol', and what we
            // get back has to be one or the other. If what we get back for
            // 'x' is actually a 'FunctionSymbol', then we've found the other
            // error. If what we get back is a 'VariableSymbol' then great;
            // that's what we need, and we should store it for later
            // reference.
       		boolean pass = true;


			Symbol VarSymbol = table.lookup(name);
			//System.out.println("RESOLVE: VarRefExp");			
			if(VarSymbol == null)
			{
				//throw new EmptyException(); 
				System.err.println(this.getLocation() + ":" + name + " is undefined");
				pass = false;
			}
			else if(VarSymbol instanceof FunctionSymbol)
			{
				//throw new Exception("Attempt to use function other than by calling it");
				System.err.println("Not calling a function: row:col:Attempt to use function ident other than by calling it");
				pass = false;
			}
			else if(VarSymbol instanceof VariableSymbol)
				symbol = (VariableSymbol) VarSymbol;
	   	
			return pass;
		}
    }



    ////////////////////////////////////////////////////////
    // Types

    public abstract static class TypeNode extends AstNode
    {
        public TypeNode(SourceLocation loc) {
            super(loc);
        }

        public abstract Type asSemanticType();

        // This should never be called on any type node
        public boolean resolveNames(SymbolTable table) {
            assert false;
			return false;
        }
    }

    public static class BooleanTypeNode extends TypeNode
    {
        public BooleanTypeNode(SourceLocation loc) {
            super(loc);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }

        public BoolType asSemanticType() {
            return BoolType.make();
        }
    }

    public static class IntTypeNode extends TypeNode
    {
        public IntTypeNode(SourceLocation loc) {
            super(loc);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }

        public IntType asSemanticType() {
            return IntType.make();
        }
    }

    public static class DoubleTypeNode extends TypeNode
    {
        public DoubleTypeNode(SourceLocation loc) {
            super(loc);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }

        public DoubleType asSemanticType() {
            return DoubleType.make();
        }
    }

    public static class StringTypeNode extends TypeNode
    {
        public StringTypeNode(SourceLocation loc) {
            super(loc);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }

        public StringType asSemanticType() {
            return StringType.make();
        }
    }

    public static class PointerTypeNode extends TypeNode
    {
        private final TypeNode innerType;

        public PointerTypeNode(SourceLocation loc, TypeNode type) {
            super(loc);

            innerType = type;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object innerResult = innerType.accept(v);
            return v.visit(this, innerResult);
        }

        public TypeNode getInnerTypeNode() {
            return innerType;
        }

        public PointerType asSemanticType() {
            Type inner = innerType.asSemanticType();
            return PointerType.make(inner);
        }
    }


    public static class ArrayTypeNode extends TypeNode
    {
        private final TypeNode innerType;
        private final int size;

        public ArrayTypeNode(SourceLocation loc, TypeNode inner, long size) {
            super(loc);
            assert size >= 0;
            assert size <= Integer.MAX_VALUE;

            innerType = inner;
            this.size = (int) size;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object innerResult = innerType.accept(v);
            return v.visit(this, innerResult);
        }

        public TypeNode getInnerTypeNode() {
            return innerType;
        }

        public int getSize() {
            return size;
        }


        public ArrayType asSemanticType() {
            Type inner = innerType.asSemanticType();
            return ArrayType.make(inner, size);
        }
    }
}


// Changed name of BlahType to BlahTypeNode
// Changed ArrayTypeNode.getSize to return long
// Assert instead of throw on SymbolTable empty
