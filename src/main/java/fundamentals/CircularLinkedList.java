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
        n = 0;
        nOp++;
        last = null;
    }

    public boolean isEmpty() {
        return (last == null);
    }

    public int size() {
         return n;
    }

    private long nOp() {
        return nOp;
    }


    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item) {
        Node NewNode = new Node();
        NewNode.item = item;

        //1 element
        if (isEmpty()){
            NewNode.next = NewNode;
        }
        else {
            NewNode.next = last.next;
            last.next = NewNode;
        }
        last = NewNode;
        n++;
        nOp++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index)
    {
        if (isEmpty() || index < 0 || index >= n) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }

        if (index == 0) {
            Item item = last.next.item;
            if (n == 1) {
                last = null; // Remove the last element if it's the only one
            } else {
                last.next = last.next.next; // Remove the first element
            }
            n--;
            nOp++;
            return item;
        } else {
            Node current = last.next;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            Item item = current.next.item;
            current.next = current.next.next; // Remove the element at the specified index
            n--;
            nOp++;
            return item;
        }
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
            nOpInit = nOp;
            if (!isEmpty()) current = last.next;
        }


        private boolean failFastCheck() {
            if (nOp != nOpInit) {
                throw new ConcurrentModificationException();
            }
            return true;
        }

        @Override
        public boolean hasNext() {
            return current != null;
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

}