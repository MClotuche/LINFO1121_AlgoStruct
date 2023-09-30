import java.util.NoSuchElementException;

/*Opérations:
Stack(): crée une stack vide
void push(Item item)
Item pop()
boolean isEmpty()
int size()
*/
public class LinkedStack<Item> {
    private int n;
    private Node first;

    private class Node{
        Item item;
        Node next;
    }
    //Constructeur
    public LinkedStack(){
        first = null;
        n = 0;
    }

    //Méthodes

    public void push(Item item){
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public Item pop(){
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Node oldfirst = first;
        first = oldfirst.next;
        n--;
        return oldfirst.item;
    }

    public boolean isEmpty(){
        return first==null;
    }
    public int size(){
        return n;
    }

}

