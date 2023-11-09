package searching;

import java.util.ArrayList;


/**
 *  We study a BST representation using an arrayList internal representation.
 *  Rather than using a Linked-Node Data-Structure, the left/right children
 *  will be encoded with indices in array lists.
 *  More exactly, in this data-structure each node
 *  is represented by an index i (that correspond to the ith added element)
 *  The left  node of node i, if any, is located
 *        at index idxLeftNode.get(i) otherwise idxLeftNode.get(i) == NONE
 *  The right node of node i, if any is located
 *       at index idxRightNode.get(i) otherwise idxRightNode.get(i) == NONE
 *
 *  The tree below is the result of putting (key,value) pairs (12,A),(15,B),(5,C),(8,d),(1,E) (in this order)
 *
 *                12(A)
 *                / \
 *               /   \
 *             5(C)  15(B)
 *             / \
 *          1(E)  8(D)
 *
 *   The state of internal array list after those put operations is
 *    values:        A, B, C, D, E
 *    keys:        12, 15, 5, 8, 1
 *    idxLeftNode:  2, -1, 4,-1,-1
 *    idxRightNode: 1, -1, 3,-1,-1
 *
 *  You can implement the get method before the put method if you prefer since
 *  the two methods will be graded separately.
 *
 *  You cannot add of change the fields already declared, nor change
 *  the signatures of the methods in this
 *  class but feel free to add methods if needed.
 *
 */
public class ArrayBST<Key extends Comparable<Key>, Value> {

    // The next four array lists should always have exactly equal size after a put

    public ArrayList<Key> keys;
    public ArrayList<Value> values;

    public ArrayList<Integer> idxLeftNode; // idxLeftNode[i] = index of left node of i
    public ArrayList<Integer> idxRightNode; // idxRightNode[i] = index of right node of i

    final int NONE = -1;

    public ArrayBST() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        idxLeftNode = new ArrayList<>();
        idxRightNode = new ArrayList<>();
    }

    /**
     * Insert the entry in the BST, replace the value if the key is already present
     * in O(h) where h is the height of the tree
     * @param key a key that is present or not in the BST
     * @param val the value that must be attached to this key
     * @return true if the key was added, false if already present and the value has simply been erased
     */
    public boolean put(Key key, Value val) {
        if (key==null)return false;
         return putRec(key, val, 0);
    }
    private boolean putRec(Key key, Value val, int idx) {

        //1. Le BST est vide
        if (keys.size() == 0){
            keys.add(key);
            values.add(val);
            idxLeftNode.add(NONE);
            idxRightNode.add(NONE);
            return true;
        }
        int cmp = key.compareTo(keys.get(idx));
        if (cmp==0){ //key déjà présente dans l'arbre
            values.set(idx, val);
            return false;
        }
        if (cmp<0){ //key inférieure au node actuel
            int leftIdx = idxLeftNode.get(idx);
            if (leftIdx == NONE){
                //Maj du parent
                idxLeftNode.set(idx,keys.size());
                //Création du Node
                keys.add(key);
                values.add(val);
                idxLeftNode.add(NONE);
                idxRightNode.add(NONE);
                return true;
            }
            else return putRec(key,val,leftIdx);
        }
        else{ //key supérieure au node actuel
            int rightIdx = idxRightNode.get(idx);
            if (rightIdx == NONE){
                //Maj du parent
                idxRightNode.set(idx,keys.size());
                //Création du Node
                keys.add(key);
                values.add(val);
                idxLeftNode.add(NONE);
                idxRightNode.add(NONE);
                return true;
            }
            else return putRec(key,val,rightIdx);
        }
    }

    /**
     * Return the value attached to this key, null if the key is not present
     * in O(h) where h is the height of the tree
     * @param key
     * @return the value attached to this key, null if the key is not present
     */
    public Value get(Key key) {
        if (key==null)return null;
        return getRec(key,0);
    }
    private Value getRec(Key key, int idx){
        if (idx>=keys.size() || idx==NONE)return null;
        int cmp = key.compareTo(keys.get(idx));
        if (cmp<0)return getRec(key,idxLeftNode.get(idx));
        if (cmp>0)return getRec(key, idxRightNode.get(idx));
        return values.get(idx);
    }






}
