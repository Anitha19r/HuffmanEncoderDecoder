

public class Heap {
	
	public class Node implements Comparable<Node>{
		String data;
		int freq;
		Node left;
		Node right;
		Node leftChild;
	    Node nextSibling;
	    Node prev;
		Node(){};
		Node(String data,int freq){
			this.data = data;
			this.freq = freq;
		}
		@Override
		public int compareTo(Node other) {
		    return Integer.compare(freq, other.freq);
		}
	}
	int heapStart=0;
	
	public void insert(Node x)
    {
    
    }
	 public Node delete(int ind)
	 {
		return null;
	 }     
	 

}
