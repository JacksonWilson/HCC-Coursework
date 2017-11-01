package labs.lab17;

import java.util.ArrayList;

public class Stack<T> {
    private ArrayList<T> list;
    private int size;

    public Stack() {
        list = new ArrayList<>();
        size = 0;
    }

    public void push(T data) {
        list.add(data);
        size++;
    }

    public T pop() {
        if (size > 0) {
            size--;
            return list.remove(size - 1);
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Size: " + size + " Data: ");
        for (T data : list) {
            sb.append(" [");
            sb.append(data);
            sb.append("] ");
        }
        return sb.toString();
    }
}