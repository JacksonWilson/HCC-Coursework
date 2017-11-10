package labs.lab17_generic;

public class Dictionary<K, V> {
    private static final int GROW_CONSTANT = 10;
    private K[] keys;
    private V[] values;
    private int size;

    @SuppressWarnings("unchecked")
    public Dictionary() {
        this.keys = (K[])(new Object[GROW_CONSTANT]);
        this.values = (V[])(new Object[GROW_CONSTANT]);
        size = 0;
    }

    public Dictionary(K[] keys, V[] values) {
        this();
        if (keys.length != values.length)
            throw new IllegalArgumentException("Unbalanced key-value pairs");
        
        for (int i = 0; i < keys.length; i++)
            put(keys[i], values[i]);
    }

    public void put(K key, V value) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key))
                return;
        }

        if (keys.length == size)
            grow();

        keys[size] = key;
        values[size] = value;
        size++;
    }

    public V retrieveEntry(K key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key))
                return values[i];
        }
        return null;
    }

    public void deleteEntry(K key) {
        int i = 0;
        for (; i < size; i++) {
            if (keys[i].equals(key)) {
                for (; i < size - 1; i++) {
                    keys[i] = keys[i + 1];
                }
            }
        }
        size--;
    }

    public boolean contains(V value) {
        for (int i = 0; i < size; i++) {
            if (values[i].equals(value))
                return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void grow() {
        K[] newKeys = (K[])(new Object[keys.length + GROW_CONSTANT]);
        V[] newValues = (V[])(new Object[values.length + GROW_CONSTANT]);

        for (int i = 0; i < keys.length; i++) {
            newKeys[i] = keys[i];
            newValues[i] = values[i];
        }

        keys = newKeys;
        values = newValues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Size: " + size + " Pairs: ");
        for (int i = 0; i < size; i++) {
            sb.append("(");
            sb.append(keys[i]);
            sb.append(", ");
            sb.append(values[i]);
            sb.append(") ");
        }
        return sb.toString();
    }
}