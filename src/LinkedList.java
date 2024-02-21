public class LinkedList {

    protected Node head;
    protected Node tail;

    public LinkedList() {
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Node getHead() {
        return head;
    }

    public void insertFirst(Node newNode) {
        if (isEmpty()) {
            tail = newNode;
        }
        newNode.setNext(head);
        head = newNode;
    }

    public void insertLast(Node newNode) {
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
    }

    public void insertMiddle(Node newNode, Node previous) {
        newNode.setNext(previous.getNext());
        previous.setNext(newNode);
    }

    /**
     * @param value The value to be searched.
     * @return The node that has the data value. If no node exists, returns null.
     */
    public Node search(int value) {
        Node tmp = head;
        while (tmp != null) {
            if (value == tmp.getData()) {
                return tmp;
            }
            tmp = tmp.getNext();
        }
        return null;
    }

    public Node getNodeI(int i) {
        Node tmp = head;
        int index = 0;
        while (tmp != null) {
            if (index == i) {
                return tmp;
            }
            index++;
            tmp = tmp.getNext();
        }
        return null;
    }

    public int numberOfElements() {
        Node tmp = head;
        int count = 0;
        while (tmp != null) {
            count++;
            tmp = tmp.getNext();
        }
        return count;
    }

    public void deleteFirst() {
        head = head.getNext();
        if (isEmpty()) {
            tail = null;
        }
    }

    public Node getPrevious(Node node) {
        Node tmp = head;
        Node previous = null;
        while (tmp != node) {
            previous = tmp;
            tmp = tmp.getNext();
        }
        return previous;
    }

    public void deleteValue(int value) {
        Node tmp = head;
        Node previous = null;
        while (tmp != null) {
            if (tmp.getData() == value) {
                if (previous != null) {
                    previous.setNext(tmp.next);
                    if (tmp.next == null) {
                        tail = previous;
                    }
                } else {
                    head = tmp.next;
                    if (head == null) {
                        tail = null;
                    }
                }
                break;
            }
            previous = tmp;
            tmp = tmp.getNext();
        }
    }

    public void deleteLast() {
        tail = getPrevious(tail);
        if (tail != null) {
            tail.setNext(null);
        } else {
            head = null;
        }
    }

    public void deleteMiddle(Node node) {
        Node previous;
        previous = getPrevious(node);
        previous.setNext(node.getNext());
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        Node tmp = head;
        while (tmp != null) {
            result.append(tmp).append(" ");
            tmp = tmp.getNext();
        }
        return result.toString();
    }

    /*
     * 1. Write a function that will find the smallest number in a singly linked
     * list.
     * int smallest ()
     */
    int smallest() {
        Node temp = head;
        int smallest = temp.data;
        while (temp != null && temp.next != null) {
            if (temp.data > temp.next.data) {
                smallest = temp.next.data; // bu kısımda hata yaptın tam tersini yazdın
            }
            temp = temp.next;
        }
        return smallest; // smallest yerine 0 returnledin
    }

    /*
     * 2. Write a function to delete the second node from a singly linked list.
     * void deleteSecond()
     */
    void deleteSecond() {
        if (head == null || head.next == null) {
            System.out.println("cant delete second");
        } else {
            head.next = head.next.next;
        } // else koymadan yaptığında null pointer exception verdi o yüzden else koymayı
          // unutma
    }

    /*
     * 3. Write a function that will delete the odd indexed elements from a singly
     * linked list. The function will also return the deleted nodes as a new
     * linked list.
     * LinkedList oddIndexedElements()
     */
    LinkedList oddIndexedElements() {
        LinkedList odd = new LinkedList();
        Node temp = head;
        Node prev = null;
        int count = 1;
        while (temp != null) {

            if (count % 2 == 1) {

                if (odd.head == null) {
                    odd.head = temp;
                } else {
                    odd.tail.next = temp;
                }
                odd.tail = temp;

                ///////////
                ///////////
                if (temp == head) {
                    head = head.next;
                } else {
                    prev.next = temp.next;
                }
            }
            prev = temp;
            temp = temp.next;
            count++;
        }
        return odd;
    }


    /*
     * Write a function that will add a new node before the last node of a
     * singly linked list.
     * void insertBeforeLast (Node newNode)
     */
    void insertBeforeKthNode(int k, Node newNode) {
        Node temp = head;
        int count = 0;
        while (temp != null) {
            if (count + 1 > k) {
                if (count == 0) {
                    newNode.next = head;
                    head = newNode;
                } else {
                    System.out.println("it cant");
                }
                break;
            }

            else if (count + 1 == k) {
                newNode.next = temp.next;
                temp.next = newNode;
            }
            temp = temp.next;
            count++;
        }
    }

    /*
     * write a function which sorts the linkedlists and deletes the repeating
     * numbers
     * ornegin :
     * 111 3 00 2222 4
     */
    // void sortAndDeleteRepeatings(){
    // Node temp=head;
    // LinkedList noRepeat = new LinkedList();
    // while(temp!=null){
    // for(int i=0;i<count;i++){
    // }
    // }
    // }

    /*
     * boolean evenOddSorted()
     * if odd indexed sorted in increasing
     * if even indexed sorted in decreasing order
     * 1 /2 3 /4 5 6
     */
    boolean evenOddSorted() {
        Node temp = head;
        int count = 1;
        while (temp.next.next != null) {
            if (count % 2 == 1 && temp.data >= temp.next.next.data && temp.next.next != null) {
                return false;
            }
            if (count % 2 == 0 && temp.data <= temp.next.next.data && temp.next.next != null) {
                return false;
            }
            temp = temp.next;
            count++;
        }
        return true;
    }

    /*
     9. Given node X, write a function to move that node n position forward.
    Assume that there are at least n nodes after node X.
     void move(Node X, int n)
    */
    //1 2 3 4 5 6 7 8
    void move(Node x, int n){
        Node temp=head;
        if(x==head){
            head=head.next;
        }

        while(temp.next!=x){
            temp=temp.next;
        }

        temp.next=temp.next.next;
        for(int i=0;i<n;i++){
            temp=temp.next;
        }

        if(temp.next.next==null){
            System.out.println("no");
        }

        x.next=temp.next.next;
        temp.next=x;

        if(x.next==null){
            tail=x;
        }
    }

    /*
    Given an integer N, write a function which returns the prime factors
    of N as singly linked list.
    LinkedList primeFactors( int N)
    */ LinkedList primeFactors(int N) {
      LinkedList factors = new LinkedList();
      int i=2;
      while(i<N){
        if(N%i==0){
            factors.insertLast(new Node(i));
            N=N/i;
        }
        else{
            i++;
        }
    }
       return factors;

}

    /*
     Write a function that will delete all nodes whose contents are divisible
     by N. The function will also return the deleted nodes as a new linked
     list.
     LinkedList removeDivisibleByN(int N)
     */
    LinkedList removeDivisibleByN(int n) {
        Node temp = head;
        Node prev = temp;
        while (temp != null) {
            if (temp.data % n == 0) {
                if (temp == head) {
                    head = head.next;
                }
                else {
                prev.next=temp.next;
                }
            }
            prev=temp;
            temp=temp.next;
        }
        return this;
    }


    /*Write a function that will delete even indexed elements from a singly
    linked list.
    void deleteEvenIndexed()
    */
    void deleteOddIndexed(){
        Node temp=head;
        Node prev=null;
        int count = 1;
        while(temp!=null){
            if(count%2==1){
                if(temp==head){
                    head=head.next;
                }
                else{
                    prev.next=temp.next;
                }
            }
            prev=temp;
            temp=temp.next;
            count++;
        }
    }

    /*
    Write a function that will add a new node before the last node of a
    singly linked list.
    void insertBeforeLast (Node newNode)*/
    void insertBeforeLast (Node newNode){
        Node prev=null;
        Node temp=head;
        if(temp==null){
            head=newNode;
            tail=newNode;
            tail.next=null;
        }
        while(temp!=null){
            if(head.next==null){
                newNode.next=head;
                head=newNode;
            }
            else{
                if(temp.next==tail)
                prev.next=newNode;
                newNode.next=temp;   //burada else deyip tailden önce insert etmemiz gerektiğini unutma
            }
            prev=temp;               //kritik noktalardan birisi de bu
            temp=temp.next;
        }

    }

    /*Given a sorted linked list, write a function to add a new integer without
    destroying the sortedness property.
    void AddToSortedList(int x)*/
    void AddToSortedList(int x){
        Node temp=head;
        Node prev=null;
        Node newNode = new Node(x);
        while(temp!=null){
            if(x<=temp.data){
                if(prev==null){
                    newNode.next=temp;
                    head=newNode;
                }
                prev.next=newNode;
                newNode.next=temp;
                break;
            }
            prev=temp;
            temp=temp.next;
        }
    }

    /*Write a function to delete k’th node from a singly linked list.
    void deleteKth(int k)*/
    void deleteKth(int k){
        int count =1;
        Node temp = head;
        Node prev=null;
        while(temp!=null){
            if(count==k){
                prev.next=temp.next;
            }
            prev=temp;
            temp=temp.next;
            count++;
        }
    }

    /*
    Write a function that determines if a singly link list is palindrome, that
    is, it is equal its reverse.
    boolean palindrom()
    a a a a a a a
    1 2 3 4 5 6 7
     */
    boolean palindrom() {
        int count = 0;
        Node temp1 = head;
        while (temp1 != null) {
            temp1 = temp1.next;
            count++;
        }
        temp1 = head;
        for (int i = 1; i <= count / 2; i++) {
            Node temp2 = head;
            for (int j = 1; j < count - i + 1; j++) {
                temp2 = temp2.next;
            }
            if (temp1.data != temp2.data) {
                return false;
            }
            temp1 = temp1.next;
        }
        return true;
    }


    /*
     Given two sorted linked lists L1 and L2, write a function to compute
     L1 ! L2.
     LinkedList intersection ( LinkedList l1 , LinkedList l2)
     */
    LinkedList intersection(LinkedList a , LinkedList b){
        LinkedList intersection = new LinkedList();
        Node temp1=a.head;
        Node temp2=b.head;
        int countA=1;
        int countB=1;
        while(temp1!=null){
            countA++;
            temp1=temp1.next;
        }
        while(temp2!=null){
            countA++;
            temp2=temp2.next;
        }

        temp1=a.head;
        temp2=b.head;

        for(int i=1;i<countA;i++){
            temp2=head;
            for(int j=1;j<countB;j++){
                if(temp1.data== temp2.data){
                    intersection.insertLast(temp1);
                }
                temp2=temp2.next;
            }
            temp1=temp1.next;
        }
        return intersection;
    }


    public static void main(String[] args) {
        LinkedList muzo2 = new LinkedList();
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);
        Node e = new Node(5);
        Node f = new Node(6);
        muzo2.insertLast(a);
        muzo2.insertLast(b);
        muzo2.insertLast(c);
        muzo2.insertLast(d);
        muzo2.insertLast(e);
        muzo2.insertLast(f);

        LinkedList muzo = new LinkedList();
        muzo.insertLast(new Node(1));
        muzo.insertLast(new Node(2));
        muzo.insertLast(new Node(3));
        muzo.insertLast(new Node(2));
        muzo.insertLast(new Node(1));

        // muzo.deleteSecond();
        // muzo.oddIndexedElements();
        // muzo.insertBeforeKthNode(0, new Node(10));
        // System.out.println(muzo.smallest());
        //System.out.println(muzo.evenOddSorted());
        //muzo2.move(b,4);
        //System.out.println(muzo2.toString());
        //LinkedList muzo3 = new LinkedList();
        //LinkedList factors = muzo3.primeFactors(10);
        //muzo.removeDivisibleByN(6);
        //muzo.deleteOddIndexed();
        //muzo.insertBeforeLast(new Node(9));
        //muzo.AddToSortedList(7);
        //muzo.deleteKth(4);

        //boolean ab = muzo.palindrom();
        System.out.println("--");
        //System.out.println(ab);
        muzo.intersection(muzo,muzo2);
        System.out.println(muzo.toString());


    }

}


