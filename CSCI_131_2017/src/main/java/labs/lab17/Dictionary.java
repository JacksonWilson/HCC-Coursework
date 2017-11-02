package labs.lab17;

public class Dictionary {
    private static final int GROW_CONSTANT = 10;
    private int[] keys;
    private String[] values;
    private int size;

    public Dictionary() {
        this.keys = new int[GROW_CONSTANT];
        this.values = new String[GROW_CONSTANT];
        size = 0;
    }

    public void put(int key, String value) {
        for (int i = 0; i < size; i++) {
            if (keys[i] == key)
                return;
        }

        if (keys.length == size)
            grow();

        keys[size] = key;
        values[size] = value;
        size++;
    }

    public String retrieveEntry(int key) {
        for (int i = 0; i < size; i++) {
            if (keys[i] == key)
                return values[i];
        }
        return "Not found";
    }

    public void deleteEntry(int key) {
        int i = 0;
        for (; i < size; i++) {
            if (keys[i] == key) {
                for (; i < size - 1; i++) {
                    keys[i] = keys[i + 1];
                    values[i] = values[i + 1];
                }
            }
        }
        size--;
    }

    public boolean contains(String value) {
        for (int i = 0; i < size; i++) {
            if (values[i].equals(value))
                return true;
        }
        return false;
    }

    private void grow() {
        int[] newKeys = new int[keys.length + GROW_CONSTANT];
        String[] newValues = new String[values.length + GROW_CONSTANT];

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