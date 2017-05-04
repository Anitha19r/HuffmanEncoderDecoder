public class PairingHeap extends Heap {
	
	private Node root; 
	private int size;
    private Node [ ] treeArray = new Node[ 5 ];
    /* Constructor */
    public PairingHeap( )
    {
        root = null;
        size=0;
      }
    /* Check if heap is empty */
    public boolean isEmpty() 
    {
        return root == null;
    }
    /* Make heap logically empty */ 
    public void makeEmpty( )
    {
        root = null;
    }
    public int getSize() {
        return size;
    }

    public void insert(Node newNode)
    {
        //Node newNode = new Node(d, x );
        if (root == null)
            root = newNode;
        else
            root = compareAndLink(root, newNode);
        size++;
        //return newNode;
    }
    private Node compareAndLink(Node first, Node second)
    {
        if (second == null)
            return first;
 
        if (second.freq < first.freq)
        {
            /* Attach first as leftmost child of second */
            second.prev = first.prev;
            first.prev = second;
            first.nextSibling = second.leftChild;
            if (first.nextSibling != null)
                first.nextSibling.prev = first;
            second.leftChild = first;
            return second;
        }
        else
        {
            /* Attach second as leftmost child of first */
            second.prev = first;
            first.nextSibling = second.nextSibling;
            if (first.nextSibling != null)
                first.nextSibling.prev = first;
            second.nextSibling = first.leftChild;
            if (second.nextSibling != null)
                second.nextSibling.prev = second;
            first.leftChild = second;
            return first;
        }
    }
    private Node combineSiblings(Node firstSibling)
    {
        if( firstSibling.nextSibling == null )
            return firstSibling;
        /* Store the subtrees in an array */
        int numSiblings = 0;
        for ( ; firstSibling != null; numSiblings++)
        {
            treeArray = doubleIfFull( treeArray, numSiblings );
            treeArray[ numSiblings ] = firstSibling;
            /* break links */
            firstSibling.prev.nextSibling = null;  
            firstSibling = firstSibling.nextSibling;
        }
        treeArray = doubleIfFull( treeArray, numSiblings );
        treeArray[ numSiblings ] = null;
        /* Combine subtrees two at a time, going left to right */
        int i = 0;
        for ( ; i + 1 < numSiblings; i += 2)
            treeArray[ i ] = compareAndLink(treeArray[i], treeArray[i + 1]);
        int j = i - 2;
        /* j has the result of last compareAndLink */
        /* If an odd number of trees, get the last one */
        if (j == numSiblings - 3)
            treeArray[ j ] = compareAndLink( treeArray[ j ], treeArray[ j + 2 ] );
        /* Now go right to left, merging last tree with */
        /* next to last. The result becomes the new last */
        for ( ; j >= 2; j -= 2)
            treeArray[j - 2] = compareAndLink(treeArray[j-2], treeArray[j]);
        return treeArray[0];
    }
    private Node[] doubleIfFull(Node [ ] array, int index)
    {
        if (index == array.length)
        {
            Node [ ] oldArray = array;
            array = new Node[index * 2];
            for( int i = 0; i < index; i++ )
                array[i] = oldArray[i];
        }
        return array;
    }
    /* Delete min freq */
    public Node delete( int ind)
    {
        if (isEmpty( ) )
           // return -1;
        	return null;
        //int x = root.freq;
        Node n =root;
        if (root.leftChild == null)
            root = null;
        else
            root = combineSiblings( root.leftChild );
        size--;
        return n;
    }
}
