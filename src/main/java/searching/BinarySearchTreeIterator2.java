package searching;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * In this exercise, we are interested in implementing an iterator (BSTIterator) for a Binary Search Tree (BST).
 * The iterator must traverse the tree in-order. This means that for each node, the left sub-tree is visited, then the node and finally the right sub-tree.
 *
 *  For example, consider the following tree
 *
 *                              12
 *                              |
 *                 8 ------------------------ 18
 *                  |                          |
 *           3 ------------ 11       14 -------------- 20
 *                          |        |
 *                     9 ---         --- 15
 *
 *
 * The iterator visits the nodes in this order: 3, 8, 9, 11, 12, 14, 15, 18, 20
 * We ask you to complete the BSTIterator class, which must implement the Iterator interface.
 *
 * The BSTNode are generic over their key (the integers in the example above) and implement the
 * BinaryNode and KeyNode interface available in the utils package.
 *
 * Hint: You have two strategies to implement this iterator Fail-Fast and Fail-Safe
 * https://www.geeksforgeeks.org/fail-fast-fail-safe-iterators-java/
 *
 * The Fail-Safe will collect all the keys in a collection and return an iterator on this collection.
 * The Fail-Fast will lazily return the elements and throw an exception if the BST is modified while iterating on it.
 *
 * The advantage of Fail-Fast is that the constructor and iteration is Lazy.
 * The total cost of iterating will be Theta(n) but the initialization can be O(h).
 *
 * It is a good exercise to implement both version.
 *
 */
public class BinarySearchTreeIterator2<Key extends Comparable<Key>> implements Iterable<Key> {

    private BSTNode<Key> root;

    public BinarySearchTreeIterator2() { };

    /**
     * Returns the size of the tree
     */
    public int size() {
        return this.size(root);
    }

    /**
     * Returns the size the subtree rooted at node
     *
     * @param node the root of the subtree
     */
    private int size(BSTNode<Key> node) {
        if (node == null) {
            return 0;
        }
        return node.getSize();
    }

    /**
     * Adds a value in the tree
     *
     * @param key the value to add
     */
    public void put(Key key) {
        this.root = put(this.root, key);
    }

    /**
     * Adds a value in a subtree of the tree
     *
     * @param node the root of the subtree
     * @param key the value to add
     */
    private BSTNode<Key> put(BSTNode<Key> node, Key key) {
        if (node == null) {
            return new BSTNode<>(key, 1);
        }
        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) {
            node.setLeft(put(node.getLeft(), key));
        } else if (cmp > 0) {
            node.setRight(put(node.getRight(), key));
        }
        node.setSize(1 + size(node.getLeft()) + size(node.getRight()));
        return node;
    }

    @Override
    public Iterator<Key> iterator() {
        return new BSTIterator2();
    }

    private class BSTIterator2 implements Iterator<Key> {
        //Fail-fast implementation
        private final int refSize;

        //Idée: création d'une Stack selon l'ordre préfixe
        private Stack<BSTNode<Key>> myStack;


        public BSTIterator2(){
            Stack<BSTNode<Key>> stack = new Stack<BSTNode<Key>>();
            BSTNode<Key> cur = root;
            while (cur != null){
                //Déplacement vers la gauche en ajoutant les noeuds rencontrés dans la stack
                stack.push(cur);
                cur = cur.left;
            }
            this.refSize = size();
            this.myStack = stack;
        }


        @Override
        public boolean hasNext() {
            if (size() != refSize)throw new ConcurrentModificationException();
            return (!myStack.isEmpty());
        }

        @Override
        public Key next() {
            if (size() != refSize)throw new ConcurrentModificationException();
            if (!hasNext())throw new NoSuchElementException();
            BSTNode<Key> node = this.myStack.pop();
            Key key = node.getKey();
            //Besoin d'ajouter dans la stack le noeud de droite & ses noeuds de gauche
            if (node.right !=null){
                node = node.right;
                while (node !=null){
                    this.myStack.push(node);
                    node = node.getLeft();
                }
            }
            return key;
        }
    }

    class BSTNode<K extends Comparable<K>> {

        private BSTNode<K> left;
        private BSTNode<K> right;
        private K key;
        private int size;

        public BSTNode(K key) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.size = 0;
        }

        public BSTNode(K key, int size) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.size = size;
        }

        public BSTNode<K> getLeft() {
            return this.left;
        }

        @SuppressWarnings("unchecked")
        public void setLeft(BSTNode<K> node) {
            this.left = node;
        }

        public BSTNode<K> getRight() {
            return this.right;
        }

        @SuppressWarnings("unchecked")
        public void setRight(BSTNode<K> node) {
            this.right = node;
        }

        public K getKey() {
            return this.key;
        }

        public void setKey(K newKey) {
            this.key = newKey;
        }

        public int getSize() {
            return this.size;
        }

        public void setSize(int newSize) {
            this.size = newSize;
        }

        /**
         * Adds a new value in the subtree rooted at this node
         */
        public void add(K key) {
            if (key.compareTo(this.key) > 0) {
                if (this.right == null) {
                    this.right = new BSTNode<>(key);
                } else {
                    this.right.add(key);
                }
            } else {
                if (this.left == null) {
                    this.left = new BSTNode<>(key);
                } else {
                    this.left.add(key);
                }
            }
        }
    }
}