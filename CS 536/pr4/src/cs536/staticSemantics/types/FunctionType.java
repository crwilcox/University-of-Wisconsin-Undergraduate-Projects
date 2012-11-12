package cs536.staticSemantics.types;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
   Tracks the return and argument types of a function.  The return
   type *may* be null if the function is void.
*/
public class FunctionType extends Type
{
    private Type returnType;
    private List<Type> argumentTypes;

    private FunctionType(Type retType, List<Type> argTypes)
    {
        returnType = retType;
        argumentTypes = argTypes;
    }

    // We won't be using this one to compute stack offsets, so its
    // return value doesn't matter.
    public int getSize()
    {
        return -1;
    }

    public Type getReturnType()
    {
        return returnType;
    }

    public List<Type> getArgumentTypes()
    {
        return argumentTypes;
    }

    /////////////////////////////////////
    // Singleton stuff

    // Map from inner types to function types. I pull the following
    // hack. Instead of making a pair class for holding the argument and
    // return types, I just put the return type at the end of the argument
    // type list.
    private static Map<List<Type>, FunctionType> instances = new HashMap<List<Type>, FunctionType>();
    
    public synchronized static FunctionType make(Type returnType, List<Type> argTypes)
    {
        // See above comment (for 'instances')
        List<Type> allInnerTypes = new ArrayList<Type>(argTypes);
        allInnerTypes.add(returnType);
        
        FunctionType t = instances.get(allInnerTypes);
        
        // null is not a valid type, so we don't have to use containsKey
        // to disambiguate
        if(t != null) {
            // We've run into a type for this function before. Return it.
            return t;
        }
        else {
            // Hey, new function! Make its type!
            t = new FunctionType(returnType, argTypes);
            instances.put(allInnerTypes, t);
            return t;
        }
    }

}
