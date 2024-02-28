public class Polynomial {
    private Node head;
    private Node tail;

    public Polynomial() {
        head = null;
        tail = null;
    }

    public void addTerm(Term term) {
        if (head == null) {
            head = new Node(term);
            tail = head;
        } else {
            tail.setNext(new Node(term));
            tail = tail.getNext();
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Node getHead() {
        return head;
    }

    // Other methods for polynomial manipulation can be added here
}