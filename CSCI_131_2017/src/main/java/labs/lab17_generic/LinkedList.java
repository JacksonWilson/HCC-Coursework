package labs.lab17_generic;

public class LinkedList<T> {
    private Node<T> head;
    private int size;

    public LinkedList() {
        this(null);
    }

    public LinkedList(T data) {
        head = new Node<>(data);
        size = 1;
    }

    public void addToEnd(T data) {
        Node<T> next = head;

        while (next.getNext() != null) {
            next = next.getNext();
        }

        next.setNext(new Node<>(data));

        size++;
    }

    public void addToBeginning(T data) {
        head = new Node<T>(data, head);
        size++;
    }

    public void addAtPosition(int index, T data) {
        if (index == 0) {
            addToBeginning(data);
        } else if (index > 0 && index <= size) {
            Node<T> next = head;
            for (int i = 0; i < index - 1; i++) {
                next = next.getNext();
            }
    
            next.setNext(new Node<>(data, next.getNext()));
            size++;
        }
    }

    public T retreiveElementAtPosition(int index) {
        if (index < 0 || index >= size)
            return null;

        Node<T> next = head;
        for (int i = 0; i < index; i++) {
            next = next.getNext();
        }

        return next.getData();
    }

    public void removeAtPosition(int index) {
        if (index < 0 || index >= size)
            return;

        if (index == 0)
            head = head.getNext();
        else {
            Node<T> next = head;
            for (int i = 0; i < index - 1; i++) {
                next = next.getNext();
            }
    
            next.setNext(next.getNext().getNext());
        }
        size--;
    }

    public Node<T> getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        String representation = "Size: " + size + " List: " + head.getData();

        Node<T> next = head.getNext();

        while (next != null) {
            representation += " --> " + next.getData();
            next = next.getNext();
        }

        return representation;
    }
}