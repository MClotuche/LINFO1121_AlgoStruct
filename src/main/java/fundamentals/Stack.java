package fundamentals;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Author: Pierre Schaus
 *
 * You have to implement the interface using
 * - a simple linkedList as internal structure
 * - a growing array as internal structure
 */
public interface Stack<E> {

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty();

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException;

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException;

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item);

}

/**
 * Implement the Stack interface above using a simple linked list.
 * You should have at least one constructor withtout argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class LinkedStack<E> implements Stack<E> {

    private Node<E> top;        // the node on the top of the stack
    private int size;        // size of the stack

    // helper linked list class
    private class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }

    public LinkedStack(){
        size= 0;
        top = null;
    }

    @Override
    public boolean empty() {
        return (top == null);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (empty()){ throw new EmptyStackException();}
        else{
            return top.item;
        }
    }

    @Override
    public E pop() throws EmptyStackException {
        if (empty()){ throw new EmptyStackException(); }
        E item = top.item;
        top = top.next;
        size--;
        return item;
    }

    @Override
    public void push(E item) {
        Node newNode = new Node(item,top);
        top = newNode;
        size++;
    }


}


/**
 * Implement the Stack interface above using an array as internal representation
 * The capacity of the array should double when the number of elements exceed its length.
 * You should have at least one constructor withtout argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class ArrayStack<E> implements Stack<E> {

    private E[] array;        // array storing the elements on the stack
    private int size;        // size of the stack
    private int firstindex;

    public ArrayStack() {
        array = (E[]) new Object[10];
        size = 0;
        firstindex = 0;
    }
    private void resize(int newsize){
        array = Arrays.copyOf(array,newsize);
    }

    @Override
    public boolean empty() {
        if (size==0) return true;
         return false;
    }

    @Override
    public E peek() throws EmptyStackException {
        if (empty()) throw new EmptyStackException();
         return array[firstindex-1];
    }

    @Override
    public E pop() throws EmptyStackException {
        if (empty()) throw new EmptyStackException();
        E old = array[firstindex-1];
        if (firstindex>0) firstindex--;
        size--;
        if (size >0 && size<= array.length/4){
            array = Arrays.copyOf(array, array.length/2);
        }
        return old;
    }

    @Override
    public void push(E item) {
        if (size == array.length){resize(2* array.length);}
        array[firstindex++] = item;
        size++;
    }

    public static void main(String[] args) {
        ArrayStack<Integer> S = new ArrayStack<Integer>();
        S.push(1);
    }
}

