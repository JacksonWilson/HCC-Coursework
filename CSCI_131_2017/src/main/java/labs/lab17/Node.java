package labs.lab17;

public class Node {
    private int data;
    private Node next;

    public Node() {
        data = 0;
        next = null;
    }

    public Node(int d) {
        data = d;
        next = null;
    }
    
    public Node(int d, Node n) {
        data = d;
        next = n;
    }

    public int getData() {
        return data;
    }

    public void setData(int d) {
        data = d;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node n) {
        next = n;
    }
}