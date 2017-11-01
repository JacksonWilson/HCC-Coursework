package labs.lab17;

public class LinkedList {
    private Node head;
    private int size;

    public LinkedList () {
        head = new Node();
        size = 1;
    }

    public LinkedList (int data) {
        head = new Node(data);
        size = 1;
    }

    public void addToEnd(int data) {
        Node next = head;

        while(next.getNext() != null) {
            next = next.getNext();
        }

        next.setNext(new Node(data));

        size++;
    }

    public void addToBeginning(int data) {
        head = new Node(data, head);
        size++;
    }

    public void addAtPosition(int index, int data) {
        if (index == 0) {
            addToBeginning(data);
        } else if (index > 0 && index <= size) {
            Node next = head;
            
            for (int i = 0; i < index - 1; i++) {
                next = next.getNext();
            }

            next.setNext(new Node(data, next.getNext()));
            size++;
        }
    }

    public int retreiveElementAtPosition(int index) {
        if (index < 0 || index >= size)
            return 0;

        Node next = head;
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
            Node next = head;
            for (int i = 0; i < index - 1; i++) {
                next = next.getNext();
            }
    
            next.setNext(next.getNext().getNext());
        }
        size--;
    }

    @Override
    public String toString () {
        String representation = "Size: " + size + " List: " + head.getData();

        Node next = head.getNext();

        while(next != null) {
            representation += " --> " + next.getData();
            next = next.getNext();
        }

        return representation;
    }

    public Node getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }
}