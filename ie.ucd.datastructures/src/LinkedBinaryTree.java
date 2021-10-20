public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {



    protected static class Node<E> implements Position<E> {
        private E element; //the element (i.e. data) stored at this node
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild){
            element = e;
            parent = above;
            left = leftChild;
            right = rightChild;
        }

        public E getElement() throws IllegalStateException {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }

        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(element);
            return sb.toString();
        }
    }

    /** Factory function to create a new node storing element e. */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    // LinkedBinaryTree instance variables
    /** The root of the binary tree */
    protected Node<E> root = null;     // root of the tree

    /** The number of nodes in the binary tree */
    private int size = 0;              // number of nodes in the tree

    // constructor
    /** Construts an empty binary tree. */
    public LinkedBinaryTree() { }      // constructs an empty binary tree

    // nonpublic utility
    /**
     * Verifies that a Position belongs to the appropriate class, and is
     * not one that has been previously removed. Note that our current
     * implementation does not actually verify that the position belongs
     * to this particular list instance.
     *
     * @param p   a Position (that should belong to this tree)
     * @return    the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node))
            throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;       // safe cast
        if (node.getParent() == node)     // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    // accessor methods (not already implemented in AbstractBinaryTree)
    /**
     * Returns the number of nodes in the tree.
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p    A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getParent( );
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getLeft( );
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getRight( );
    }

    // update methods supported by this class
    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty( )) throw new IllegalStateException("Tree not empty");
        root = createNode(e, null, null, null);//root node doesn't have a parent or left or right
        size = 1;
        return root;
    }


    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if(parent.getLeft() != null) {
            throw new IllegalArgumentException("P already has a left child");
        }
        Node<E> n = createNode(e, parent, null, null);
        parent.setLeft(n);
        ++size;
        return n;
    }


    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if(parent.getRight() != null) {
            throw new IllegalArgumentException("P already has a right child");
        }
        Node<E> n = createNode(e, parent, null, null);
        parent.setRight(n);
        ++size;
        return n;
    }


    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = node.getElement( );
        node.setElement(e);
        return temp;
    }


    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if(isInternal(p)){
            throw new IllegalArgumentException("P must be a leaf");
        }
        size += t1.size() + t2.size();
        if(!t1.isEmpty()){
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }

        if(!t2.isEmpty()){
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }

    }


    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if(numChildren(p) == 2) {
            throw new IllegalArgumentException("You can't remove a node which has 2 children");
        }

        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if(child != null) {//the child could still be a null pointer
            child.setParent(node.getParent());//child's grandparent becomes its parent
        }
        if(node == root) {//if we're removing the root
            root = child;//child becomes root
        }
        else {//if it's not the root
            Node<E> parent = node.getParent();//we get the parent of the node
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        size--;
        E temp = node.getElement();//since the method returns the element that is removed we need to store it
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return temp;
    }

    public void insert(E e){
        //recursively add from root
        if(e != null) {
            root = addRecursive(root, e);
            ++size;
        }
    }


    private final DefaultComparator<E> comparator = new DefaultComparator<>();

    public int compareTo(E first, E second){
        return comparator.compare(first, second);
    }

    //recursively add Nodes to binary tree in proper position
    private Node<E> addRecursive(Node<E> p, E e) {
        //Node<E> node = validate(p);
        if (p != null) {
            if (e.equals(p.getElement())) {
                return p;
            } else if (compareTo(e, p.getElement()) < 0) {
                if (p.getLeft() == null) {
                    //p = (Node<E>) addLeft(p, e);
                    p.setLeft(createNode(e, p, null, null));
                } else {
                    p.setLeft(addRecursive(p.getLeft(), e));
                }
            } else if (compareTo(e, p.getElement()) > 0) {
                if (p.getRight() == null) {
                    //p = (Node<E>) addRight(p, e);
                    p.setRight(createNode(e, p, null, null));
                } else {
                    p.setRight(addRecursive(p.getRight(), e));
                }
            } else {
                throw new IllegalArgumentException("For some reason this is not valid.");
            }
            return p;
        } else {
            return createNode(e, p, null, null);
        }
    }

    public void createLevelOrder(E[] arr)
    {
        root = createLevelOrderHelper(arr, root, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i)
    {
        if (i < arr.length) {
            Node<E> n = createNode(arr[i], p, null, null);
            n.left = createLevelOrderHelper(arr, n.left, 2 * i + 1);
            n.right = createLevelOrderHelper(arr, n.right, 2 * i + 2);
            size++;
            return n;
        }

        return p;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Position<E> p : positions()) {
            sb.append(p.getElement()).append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }





    public static void main(String [] args) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();
        int[] arr = {12, 25, 31, 58, 36, 42, 90, 62, 75};
        for(int i : arr) {
            bt.insert(i);
        }
        System.out.println("bt:"+ bt.size() +""+ bt );
    }
}