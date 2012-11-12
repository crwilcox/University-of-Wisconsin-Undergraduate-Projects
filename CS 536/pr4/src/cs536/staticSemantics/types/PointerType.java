package cs536.staticSemantics.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointerType extends Type
{
    // Singleton stuff
    
    // Map from inner types to pointer type instantiations
    private static Map<Type, PointerType> instances = new HashMap<Type, PointerType>();

    public synchronized static PointerType make(Type innerType)
    {
        // We can't create void* pointers.
        assert innerType != null;
        
        PointerType t = instances.get(innerType);

        // null is not a valid type, so we don't have to use containsKey
        // to disambiguate
        if(t != null) {
            // We've already created a pointer type with the given inner
            // type. Return it.
            return t;
        }
        else {
            // We've not made a pointer out of this before! Make such a
            // pointer type, then add it to 'instances'. Then return it.
            t = new PointerType(innerType);
            instances.put(innerType, t);
            return t;
        }
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    

    //////////////////////////////////////////
    // Instance stuff
    
    private Type innerType;

    /** Call PointerType.make() instead */
    private PointerType(Type innerType) {
        this.innerType = innerType;
    }


    public int getSize() {
		return 4;
    }

    public Type getInnerType() {
        return innerType;
    }

    public String toString() {
        return innerType.toString() + "*";
    }
}
