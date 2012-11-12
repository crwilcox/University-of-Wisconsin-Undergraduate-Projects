package cs536.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

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
         BooleanType
	 IntType
	 DoubleType
	 StringType
	 PointerType           [inner type, e.g. "int" for "int*"]
         ArrayType             [inner type; size]

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

        public TypeNode getType() {
            return type;
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
        public TypeNode getReturnType() {
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
    }

    
    /**
       Function declarations contain a list of these as formal parameters.
    */
    public static class FormalArg extends AstNode
    {
        private final TypeNode argumentType;
        private final String name;

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

        public TypeNode getType() {
            return argumentType;
        }

        public String getName() {
            return name;
        }
    }

    
    // Statements

    /**
       Like globals, the initializer expression may be null if the
       declaration has no initializer. (cf. GlobalVarDecl)
    */
    public static class VarDeclStmt extends Statement
    {
        private final TypeNode type;
        private final String name;
        private final Expression initializer;

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

        public TypeNode getType() {
            return type;
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
    }

    
    public static class ReadStmt extends Statement
    {
        private final String destination;

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
    }

    ////////////////////////////////////////////////////////////////
    // Expressions
    
    public static class FunctionCallExp extends Expression
    {
        private final String funcName;
        private final List<Expression> arguments;

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
    }



    ////////////////////////////////////////////////////////
    // Types

    public abstract static class TypeNode extends AstNode
    {
        public TypeNode(SourceLocation loc) {
            super(loc);
        }
    }

    public static class BooleanType extends TypeNode
    {
        public BooleanType(SourceLocation loc) {
            super(loc);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }
    }

    public static class IntType extends TypeNode
    {
        public IntType(SourceLocation loc) {
            super(loc);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }
    }

    public static class DoubleType extends TypeNode
    {
        public DoubleType(SourceLocation loc) {
            super(loc);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }
    }

    public static class StringType extends TypeNode
    {
        public StringType(SourceLocation loc) {
            super(loc);
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            return v.visit(this);
        }
    }

    public static class PointerType extends TypeNode
    {
        private final TypeNode innerType;

        public PointerType(SourceLocation loc, TypeNode type) {
            super(loc);

            innerType = type;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object innerResult = innerType.accept(v);
            return v.visit(this, innerResult);
        }

        public TypeNode getInnerType() {
            return innerType;
        }
    }


    public static class ArrayType extends TypeNode
    {
        private final TypeNode innerType;
        private final long size;

        public ArrayType(SourceLocation loc, TypeNode inner, long size) {
            super(loc);

            innerType = inner;
            this.size = size;
        }

        public Object accept(AstVisitor v) {
            v.preVisit(this);
            Object innerResult = innerType.accept(v);
            return v.visit(this, innerResult);
        }

        public TypeNode getInnerType() {
            return innerType;
        }

        public long getSize() {
            return size;
        }
    }
}
