import java.util.Arrays;


public class BinaryHeap extends Heap{
	int heapStart=0;
    private Node[] heap;
    private int capacity;
    private int heapSize;

    BinaryHeap() {
        capacity = 8;
        heap = new Node[capacity];
        heapSize = 0;
    }

    private void increaseCapacity() {
        capacity *= 2;
        heap = Arrays.copyOf(heap, capacity);
    }

    public int getSize() {
        return heapSize;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public Node top() {
        return heapSize > 0 ? heap[0] : null;
    }

    public Node delete(int ind) {
        if (heapSize == 0) {
            return null;
        }
        heapSize--;
        Node res = heap[0];
        Node te = heap[heapSize];
        int curr = 0, son = 1;
        while (son < heapSize) {
            if (son + 1 < heapSize
                    && ((Comparable<Node>) heap[son + 1])
                            .compareTo(heap[son]) < 0) {
                son++;
            }
            if (((Comparable<Node>) te).compareTo(heap[son]) <= 0) {
                break;
            }
            heap[curr] = heap[son];
            curr = son;
            son = 2 * curr + 1;
        }
        heap[curr] = te;
        return res;
    }

    public void insert(Node e) {
        if (heapSize == capacity) { // auto scaling
            increaseCapacity();
        }
        int curr = heapSize;
        int parent;
        heap[heapSize] = e;
        heapSize++;
        while (curr > 0) {
            parent = (curr - 1) / 2;
            if (((Comparable<Node>) heap[parent]).compareTo(e) <= 0) {
                break;
            }
            heap[curr] = heap[parent];
            curr = parent;
        }
        heap[curr] = e;
    }
    public void printHeap()
    {
        System.out.print("\nHeap = ");
        for (int i = 0; i < heapSize; i++)
            System.out.print(heap[i].data+" "+heap[i].freq +" ");
        System.out.println();
    }     
 
}