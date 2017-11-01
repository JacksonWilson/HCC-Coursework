package labs.lab17_generic;

public class Node<T> {
    private T data;
    private Node<T> next;

    public Node() {
        this(null, null);
    }

    public Node(T d) {
        this(d, null);
    }
    
    public Node(T d, Node<T> n) {
        data = d;
        next = n;
    }

    public T getData() {
        return data;
    }

    public void setData(T d) {
        data = d;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> n) {
        next = n;
    }
}