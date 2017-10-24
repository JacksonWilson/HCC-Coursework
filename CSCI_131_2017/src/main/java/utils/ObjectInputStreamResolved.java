package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.HashMap;
import java.util.Map;

public class ObjectInputStreamResolved extends ObjectInputStream {
    private Map<String, Class<?>> classDefinitions;

    public ObjectInputStreamResolved(InputStream in) throws IOException {
        this(in, new HashMap<>());
    }

    public ObjectInputStreamResolved(InputStream in, Map<String, Class<?>> classDefinitions) throws IOException {
        super(in);
        this.classDefinitions = classDefinitions;
    }

    public Class<?> putClassDefinition(String oldClassName, Class<?> newClassDefinition) {
        return classDefinitions.put(oldClassName, newClassDefinition);
    }

    public Class<?> removeClassDefinition(String oldClassName) {
        return classDefinitions.remove(oldClassName);
    }

    public boolean removeClassDefinition(String oldClassName, Class<?> newClassDefinition) {
        return classDefinitions.remove(oldClassName, newClassDefinition);
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();

        if (classDefinitions.containsKey(resultClassDescriptor.getName()))
            resultClassDescriptor = ObjectStreamClass.lookup(classDefinitions.get(resultClassDescriptor.getName()));

        return resultClassDescriptor;
    }
}