package cs536.staticSemantics.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayType extends Type
{
    // Singleton stuff
    
    // Map from inner types to pointer type instantiations
    private static Map<Type, Map<Integer, ArrayType>> instances =
        new HashMap<Type, Map<Integer, ArrayType>>();

    public synchronized static ArrayType make(Type innerType, int size)
    {
        // We can't create void[] arrays, or negative or zero-sized arrays
        assert innerType != null;
        assert size >= 1;

        // We look up by inner type first...
        Map<Integer, ArrayType> arrayTypesOfThatInnerType = instances.get(innerType);

        if (arrayTypesOfThatInnerType == null) {
            // We haven't seen ANY arrays with the given inner type
            arrayTypesOfThatInnerType = new HashMap<Integer, ArrayType>();
            instances.put(innerType, arrayTypesOfThatInnerType);
		} 

        // ...then look up by array size.
        ArrayType t = arrayTypesOfThatInnerType.get(size);

        // null is not a valid type, so we don't have to use containsKey
        // to disambiguate
        if(t != null) {
            // We've already created an array type of the given inner type
            // and the given size. Just return it.
            return t;
        }
        else {
            // We've not made an array with the given size yet. Do so, and
            // add it to the map.
            t = new ArrayType(innerType, size);
            arrayTypesOfThatInnerType.put(size, t);
            return t;
        }
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    

    //////////////////////////////////////////
    // Instance stuff
    
    private Type innerType;
    private int size;

    /** Call ArrayType.make() instead */
    private ArrayType(Type innerType, int size) {
        this.innerType = innerType;
        this.size = size;
    }


    public int getSize() {
		return this.size*this.innerType.getSize();
    }

    public Type getInnerType() {
        return innerType;
    }

    public String toString() {
        return innerType.toString() + "*";
    }
}
