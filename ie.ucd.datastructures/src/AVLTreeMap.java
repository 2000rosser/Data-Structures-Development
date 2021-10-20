

import java.util.Arrays;
import java.util.Comparator;


/**
 * An implementation of a sorted map using an AVL tree.
 */


public class AVLTreeMap<K, V> extends TreeMap<K, V> {
    //static Logger log = Logger.getLogger(AVLTreeMap.class.getName());

    /**
     * Constructs an empty map using the natural ordering of keys.
     */
    public AVLTreeMap() {
        super();
    }

    /**
     * Constructs an empty map using the given comparator to order keys.
     *
     * @param comp comparator defining the order of keys in the map
     */
    public AVLTreeMap(Comparator<K> comp) {
        super(comp);
    }

    public static void main(String[] args) {
        main_default(args);
    }
    public static void main_default(String[] args) {

//        AVLTreeMap avl = new AVLTreeMap<>();

        //Integer [] arr = new Integer[] {5, 3, 10, 2, 4, 7, 11, 1, null, null, null, 6, 9, null, 12, null, null, null, null, null, null, null, null, 8, null};
        //Integer[] arr = new Integer[] {21, 4, 35, 15, 1, 26, 23, 12, 2, 24, 33, 5};
        //Integer [] arr = new Integer[] {5,3,10,2,4,7,11,1,6,9,12,8};
//        Integer[] arr = new Integer[]{14,7,17,4,11,53,13};
        //Integer[] arr = new Integer[]{14, 17, 11, 7, 53, 4, 8, 13, 12};

//        List<Integer> intList = Arrays.asList(arr);
        //Collections.shuffle(intList);
//        intList.forEach(x -> avl.put(x, x));
//
//
//        System.out.println(avl.toBinaryTreeString());

        //avl.remove(5);
//        avl.put(12,12);
        //avl.put(8,8);
        //avl.remove(53);
        //System.out.println(avl.toBinaryTreeString());
        AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
        Integer[] arr = new Integer[] {5,3,10,2,4,7,11,1,6,9,12,8};

        for(Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        System.out.println(map.toBinaryTreeString());
        System.out.println(map.height(map.root()));
        map.remove(5);
        System.out.println(map.toBinaryTreeString());
        System.out.println(map.height(map.root()));

    }

    /**
     * Recomputes the height of the given position based on its children's heights.
     */
    protected int height(Position<Entry<K, V>> p) {
        return tree.getAux(p);
    }


    protected void recomputeHeight(Position<Entry<K, V>> p) {
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));

    }

    /**
     * Returns whether a position has balance factor between -1 and 1 inclusive.
     */
    protected boolean isBalanced(Position<Entry<K, V>> p) {
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }

    /**
     * Returns a child of p with height no smaller than that of the other child.
     */
    protected Position<Entry<K, V>> tallerChild(Position<Entry<K, V>> p) {
        if (height(left(p)) > height(right(p)))
        {
            return left(p);//winner
        }

        if (height(left(p)) < height(right(p)))
        {
            return right(p);//winner
        }
        // equal height children; break tie while matching parent's orientation
        if (isRoot(p))
        {
            return left(p);
        }

        if (p == left(parent(p)))
        {
            return left(p);
        }

        else
        {
            return right(p);
        }
    }

    /**
     * Utility used to rebalance after an insert or removal operation. This
     * traverses the path upward from p, performing a trinode restructuring when
     * imbalance is found, continuing until balance is restored.
     */
    protected void rebalance(Position<Entry<K, V>> p) {
        int oldHeight;
        int newHeight;

        do
        {
            oldHeight = height(p);

            if (!isBalanced(p))
            {
                p = tree.restructure(tallerChild(tallerChild(p)));
                recomputeHeight(left(p));
                recomputeHeight(right(p));
            }

            recomputeHeight(p);
            newHeight = height(p);
            p = parent(p);
        } while (oldHeight != newHeight && p != null);
    }

    /**
     * Overrides the TreeMap rebalancing hook that is called after an insertion.
     */
    @Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        rebalance(p);
    }

    /**
     * Overrides the TreeMap rebalancing hook that is called after a deletion.
     */
    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        if (!isRoot(p))
        {
            rebalance(parent(p));
        }
    }

    /**
     * Ensure that current tree structure is valid AVL (for debug use only).
     */
    private boolean sanityCheck() {
        for (Position<Entry<K, V>> p : tree.positions()) {
            if (isInternal(p)) {
                if (p.getElement() == null)
                    System.out.println("VIOLATION: Internal node has null entry");
                else if (height(p) != 1 + Math.max(height(left(p)), height(right(p)))) {
                    System.out.println("VIOLATION: AVL unbalanced node with key " + p.getElement().getKey());
                    dump();
                    return false;
                }
            }
        }
        return true;
    }


    public String toBinaryTreeString() {
        BinaryTreePrinter< Entry<K, V> > btp = new BinaryTreePrinter<>( (BalanceableBinaryTree<K, V>) this.tree);
        return btp.print();
//        return null;
    }



}