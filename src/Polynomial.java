public class Polynomial {
    private Node head;
    private Node tail;

    public Polynomial() {
        head = null;
        tail = null;
    }

    public void addTerm(Term term) {
        Node newNode = new Node(term);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Node getHead() {
        return head;
    }

    // Other methods for polynomial manipulation can be added here
}