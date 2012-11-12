package cs536.staticSemantics.symbolTable;

public class RedeclarationException extends Exception
{
    public RedeclarationException(Symbol duplicate, Symbol existing)
    {
    }
}
