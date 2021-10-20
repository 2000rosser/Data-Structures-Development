
import java.util.Iterator;

/**
 * A basic singly linked list implementation.
 */
public class SinglyLinkedList<E> implements Cloneable, Iterable<E>, List<E> {
    public E removeLast() {
        if (head.getNext() == null)
        {
            E e = head.getElement();
            head = null;
            size--;
            return e;
        }

        else
        {
            Node<E> curr = head;
            Node<E> prev = null;

            while(curr.next != null)
            {
                prev = curr;
                curr = curr.getNext();
            }

            E e = curr.element;
            prev.next = null;
            size--;
            return e;
        }
    }
    //---------------- nested Node class ----------------

    /**
     * Node of a singly linked list, which stores a reference to its
     * element and to the subsequent node in the list (or null if this
     * is the last node).
     */
    private static class Node<E> {
        //Object which is held in the node
        private E element;

        //Pointer to next node
        private Node<E> next;


        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }


        /**	This method returns element from node
         * @return Element stored in Node
         */
        public E getElement() {
            return element;
        }

        /** This method adds element to Node
         * @param element to be set in Node
         */
        public void setElement(E element) {
            this.element = element;
        }

        /** This method gets Node which variable next is pointing to
         * @return Node which next is pointing to
         */
        public Node<E> getNext() {
            return next;
        }


        public void setNext(Node<E> n) {
            next = n;
        }
    } //----------- end of nested Node class -----------

    // instance variables of the SinglyLinkedList
    private Node<E> head = null; // head node of the list (or null if empty)

    private int size = 0; // number of nodes in the list

    private Node<E> tail;

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    // access methods

    /**
     * Returns the number of elements in the linked list.
     *
     * @return number of elements in the linked list
     */
    public int size() {
        return size;
    }

    /**
     * Tests whether the linked list is empty.
     *
     * @return true if the linked list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i){
        Node<E> currentNode = head;

        if (i < size && i >= 0) {
            for (int j = 0; j < size; j++) {
                if (j == i) {
                    return currentNode.getElement();
                } else {
                    currentNode = currentNode.getNext();
                }
            }

        } else {
            currentNode = null;
        }

        return currentNode.getElement();
    }

    @Override
    public E set(int i, E e) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public void add(int i, E e) {

        Node<E> currentNode = head;

        if (i >= 0 && i <= size || size == 0) {

            if (size == 0) {
                head = new Node<E>(e, null);
                size++;
            } else if (i == 0) {
                this.addFirst(e);
            }

            else {
                for (int j = 0; j < size; j++) {
                    if (j == i - 1) {
                        currentNode.setNext(new Node<E>(e, currentNode.next));
                        size++;
                    } else {
                        currentNode = currentNode.next;
                    }
                }
            }

        } else {
            throw new IllegalArgumentException("OUT OF BOUNDS:add()");
        }
    }



    @Override
    public E remove(int i) {
        if (head == null)
        {
            throw new RuntimeException("cannot delete");
        }

        if (i == 0)
        {
            E e = removeFirst();
            return e;
        }

        Node<E> curr = head;
        Node<E> prev = null;
        int k = 0;

        //find either end of list or correct position
        while (curr != null && k != i)
        {
            prev = curr;
            curr = curr.next;
            k++;
        }

        if (curr == null)
        {
            throw new RuntimeException("cannot delete");
        }

        E e = prev.getElement();
        prev.next = curr.next;
        size--;
        return e;
    }

    /**
     * Returns (but does not remove) the first element of the list
     *
     * @return element at the front of the list (or null if empty)
     */
    public E first() {
        if(head != null)
            return head.getElement(); else {
            return null;
        }
    }

    /**
     * Returns the last node of the list
     *
     * @return last node of the list (or null if empty)
     */
    public Node<E> getLast() {
        // TODO
        return null;
    }

    /**
     * Returns (but does not remove) the last element of the list.
     *
     * @return element at the end of the list (or null if empty)
     */
    public E last() {
        if(size > 0) {
            return get(size-1);
        } else {
            return null;
        }
    }

    // update methods

    /**
     * Adds an element to the front of the list.
     *
     * @param e the new element to add
     */
    public void addFirst(E e) {
        if (size != 0) {
            head = new Node<E>(e, head);
            size++;
        } else {
            head = new Node<E>(e, null);
            size++;
        }
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param e the new element to add
     */
    public void addLast(E e) {
        this.add(size, e);
    }

    /**
     * Removes and returns the first element of the list.
     *
     * @return the removed element (or null if empty)
     */
    public E removeFirst() {
        Node<E> result = head;
        head = head.next;
        size--;

        return result.getElement();
    }



    @SuppressWarnings({"unchecked"})
    public boolean equals(Object o) {
        // TODO
        return false;   // if we reach this, everything matched successfully
    }

    @SuppressWarnings({"unchecked"})
    public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
        // TODO
        return null;
    }


    /**
     * Produces a string representation of the contents of the list.
     * This exists for debugging purposes only.
     */
    public String toString() {
        Node<E> currentNode = head;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {

            if (i == 0) {
                sb.append("[" + currentNode.getElement().toString() + ", ");
            } else {
                sb.append(currentNode.getElement() + ", ");
            }
            currentNode = currentNode.next;

        }
        sb.delete(sb.lastIndexOf(", "), sb.lastIndexOf(", ") + 2);
        sb.append("]");

        return sb.toString();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node cur;
        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public E next() {
            E res = (E) cur.getElement();
            cur = cur.getNext();
            return res;
        }
    }

    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }



    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addFirst(5);
        ll.add(3, 2);
        System.out.println(ll);

        ll.addFirst(-100);
        ll.addFirst(+100);
        System.out.println(ll);

        ll.removeFirst();
        ll.removeLast();
        System.out.println(ll);

        ll.remove(2);
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.addFirst(9999);
        ll.addFirst(8888);
        ll.addFirst(7777);


        System.out.println(ll);
        System.out.println(ll.get(0));
        System.out.println(ll.get(1));
        System.out.println(ll.get(2));
        System.out.println(ll);
    }
}

