import java.util.Arrays;
import java.util.NoSuchElementException;
 
/** Class D-ary Heap **/
class FourWayHeap  extends Heap
{
	int heapStart=3;
	
    /** The number of children each node has **/
    private int d;
    private int heapSize;
    private Node[] heap;
    int n=3;
    int capacity=8;
    int numChild=4;
 
    /** Constructor **/    
    public FourWayHeap()
    {
    	heapSize = n;
        d = numChild;
        heap = new Node[capacity + 1];
        Arrays.fill(heap, null);
    }
    private void increaseCapacity() {
        capacity *= 2;
        heap = Arrays.copyOf(heap, capacity);
    }
 
    /** Function to check if heap is empty **/
    public boolean isEmpty( )
    {
        //return heapSize == 0;
    	return heapSize < n;
    }
 
    /** Check if heap is full **/
    public boolean isFull( )
    {
        return heapSize == heap.length;
    }
 
    /** Clear heap */
    public void clear( )
    {
        heapSize = 0;
    }
 
    /** Function to  get index parent of i **/
    private int parent(int i) 
    {
        return ((i-n - 1)/d)+n;
    }
    /** Function to insert element */
    public void insert(Node x)
    {
        if (isFull( ) )
        	increaseCapacity();
        /** Percolate up **/
        heap[heapSize++] = x;
        heapifyUp(heapSize - 1);
    }
 
    /** Function to find least element **/
    public Node findMin( )
    {
        if (isEmpty() )
            throw new NoSuchElementException("Underflow Exception");           
        return heap[0];
    }
 
    /** Function to delete element at an index **/
    public Node delete(int ind)
    {
        if (isEmpty() )
            throw new NoSuchElementException("Underflow Exception");
        Node keyItem = heap[ind];
        heap[ind] = heap[heapSize - 1];
        heapSize--;
        heapifyDown(ind);
        return keyItem;
    }
 
    /** Function heapifyUp  **/
    private void heapifyUp(int childInd)
    {
        Node tmp = heap[childInd];    
        while (childInd > n && tmp.freq < heap[parent(childInd)].freq)
        {
            heap[childInd] = heap[ parent(childInd) ];
            childInd = parent(childInd);
        }                   
        heap[childInd] = tmp;
        
    }
 
    /** Function heapifyDown **/
    
    
    public void heapifyDown(int ix) {
		int minChild = findMin((ix-n)*d+1+n,(ix-n)*d+d+n);
		Node tmp = heap[ ix ];     // save root in temp variable
        while ((minChild < heapSize) && (heap[minChild].freq < tmp.freq)){
			heap[ix] = heap[minChild];
			ix = minChild;
			minChild = findMin((minChild-n)*d+1+n, (minChild-n)*d+d+n);
		}
		heap[ix] = tmp;
    }
	
    // find maximum of the children
	public int findMin(int from, int to) {
		int minChild = from;
		for (int i=from+1; (i<=to && i<heapSize); i++){
			if ((heap[minChild].freq) > (heap[i].freq))
				minChild = i;
		}
		return minChild;
	}
    /** Function to print heap **/
    public void printHeap()
    {
        System.out.print("\nHeap = ");
        for (int i = 3; i < heapSize; i++)
            System.out.print(heap[i].data+" "+heap[i].freq +" ");
        System.out.println();
    }     
  
	public int getSize() {
		return heapSize;
	}
}