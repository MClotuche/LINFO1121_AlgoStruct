package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author Pierre Schaus
 *
 * We are interested in the implementation of a circular simply linked list,
 * i.e. a list for which the last position of the list refers, as the next position,
 * to the first position of the list.
 *
 * The addition of a new element (enqueue method) is done at the end of the list and
 * the removal (remove method) is done at a particular index of the list.
 *
 * A (single) reference to the end of the list (last) is necessary to perform all operations on this queue.
 *
 * You are therefore asked to implement this circular simply linked list by completing the class see (TODO's)
 * Most important methods are:
 *
 * - the enqueue to add an element;
 * - the remove method [The exception IndexOutOfBoundsException is thrown when the index value is not between 0 and size()-1];
 * - the iterator (ListIterator) used to browse the list in FIFO.
 *
 * @param <Item>
 */
public class CircularLinkedList<Item> implements Iterable<Item> {

    private long nOp = 0; // count the number of operations
    private int n;          // size of the stack
    private Node  last;   // trailer of the list

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    public CircularLinkedList() {
        //Correction: pour éviter des cas spéciaux, besoin de faire un noeud dummy
        last = new Node();
        last.next = last;
        n = 1;
    }

    public boolean isEmpty() {
        return (n == 1);
    }

    public int size() {
         return n-1;
    }

    private long nOp() {
        return nOp;
    }


    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item) {
        nOp++;
        Node NewNode = new Node();
        NewNode.item = item;

        NewNode.next = last.next;
        last.next = NewNode;

        last = NewNode;
        n++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index)
    {
        nOp++;
        if (index < 0 || index >= size()) { //attention, pas n car défini différement avc dummy
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        //Pas besoin de gérer les différents cas grâce au dummy!
        Node current = last.next;
        for (int i = 0; i < index; i++) { //grâce au dummy toujours présent
            current = current.next;
        }
        Item item = current.next.item;
        current.next = current.next.next; // Remove the element at the specified index
        if (index == n-1) last = current;
        n--;
        return item;


    }


    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Implementation of an iterator that iterates through the items in FIFO order.
     * The iterator should implement a fail-fast strategy, that is ConcurrentModificationException
     * is thrown whenever the list is modified while iterating on it.
     * This can be achieved by counting the number of operations (nOp) in the list and
     * updating it everytime a method modifying the list is called.
     * Whenever it gets the next value (i.e. using next() method), and if it finds that the
     * nOp has been modified after this iterator has been created, it throws ConcurrentModificationException.
     */
    private class ListIterator implements Iterator<Item> {

        private Node current;
        private final long nOpInit;

        public ListIterator(){
            nOpInit = nOp();
            current = last.next.next; //le dummy!
        }


        private boolean failFastCheck() {
            if (nOp != nOpInit) {
                throw new ConcurrentModificationException();
            }
            return true;
        }

        @Override
        public boolean hasNext() {
            return current != last.next;
        }

        @Override
        public Item next() {
            failFastCheck();

            if (!hasNext()){
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    public static void main(String[] args) {
        CircularLinkedList MyList = new CircularLinkedList();
        boolean a = MyList.isEmpty();
        MyList.enqueue(0);
        MyList.enqueue(1);
        MyList.enqueue(2);
        MyList.enqueue(3);
        MyList.enqueue(4);
        MyList.enqueue(5);
        int s = MyList.size();
        MyList.remove(0);
        MyList.remove(4);
        MyList.remove(3);
        int s2 = MyList.size();
        boolean c = MyList.isEmpty();
        Iterator I = MyList.iterator();
        I.hasNext();
        I.next();
        I.next();
        MyList.enqueue(6);
        //I.next();


    }

}