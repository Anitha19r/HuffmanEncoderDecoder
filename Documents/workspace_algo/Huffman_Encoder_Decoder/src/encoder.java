import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;


	public class encoder extends Heap{
		static HashMap<String, String> codeTable = new HashMap<>();
	

		//@SuppressWarnings("resource")
		@SuppressWarnings("resource")
		public static void main(String args[]) throws FileNotFoundException {
			String fileName = args[0];
		    String line = null;
		
		    try {
		    	long startTime = System.currentTimeMillis();
		        FileReader fileReader = new FileReader(fileName);
		        BufferedReader bufferedReader = new BufferedReader(fileReader);
		        HashMap<String, Integer> freq_map = new HashMap<>();
		        int count=0;
		        while((line = bufferedReader.readLine()) != null ) {
		        	count++;
		        	if(!line.isEmpty()){
			            if(freq_map.containsKey(line))
			            	freq_map.put(line, freq_map.get(line)+1);
			            else
			            	freq_map.put(line,1);
		        	}
		        }   
		        if(count==0)
		        	throw new IOException ("Input file is empty");
		        huffman(freq_map,fileName);
		        bufferedReader.close();         
		        long elapsedTime = (new Date()).getTime() - startTime;
			    //System.out.println("Time for encoding using Fourway heap (millisec):  " + elapsedTime );
		    }
		    catch(FileNotFoundException ex) {
		        System.out.println("Unable to open file '" + fileName + "'");      
		        ex.printStackTrace();
		    }
		    catch(IOException ex) {
		        System.out.println("Error reading file '" + fileName + "'");                 
		        ex.printStackTrace();
		    }
		}
		
		private static void huffman(HashMap<String, Integer> freq_map, String fileName) throws IOException {
			Node root1 = constructTree(freq_map);
			BufferedWriter output = null;
			try {
				if(root1==null){
					throw new IOException("Input File empty");
				}
				File codeTableFile = new File("code_table.txt");
				File encodedFile = new File("encoded.bin");
				output = new BufferedWriter(new FileWriter(codeTableFile));
				String repr="";
				buildCodes(root1, repr,codeTableFile,output);
				output.close();
				FileReader fileReader = new FileReader(fileName);
		        BufferedReader bufferedReader = new BufferedReader(fileReader);
		        BufferedOutputStream buffOpStream = new BufferedOutputStream(new FileOutputStream(encodedFile.getAbsolutePath()));
				encode(bufferedReader, buffOpStream);
				fileReader.close();
				bufferedReader.close();
				output.close();
				buffOpStream.close();
				fileReader.close();
				bufferedReader.close();
			} catch ( IOException e ) {
	            e.printStackTrace();
	            System.exit(1);
	        } finally {
	          if ( output != null ) {
	            output.close();
	          }
	        }
		}
		/*Construct Huffman tree*/
		private static Node constructTree(HashMap<String, Integer> freq_map) {
			
			long startTime = System.currentTimeMillis();
			Node root1	 = constructHeap(freq_map);
		    long elapsedTime = (new Date()).getTime() - startTime;   
		    //System.out.println("Time using Fourway heap to build tree (millisec):  " + elapsedTime );
		    return root1;
		}
		//Construct heap by traversing through the hashmap
		private static Node constructHeap(HashMap<String, Integer> freq_map) {
			FourWayHeap heap = new FourWayHeap();
			//Change this line for Binary and Pairing heap
			//BinaryHeap heap = new BinaryHeap();
			//PairingHeap heap= new PairingHeap();
			
		    for(String c: freq_map.keySet()){
		    	Node n = heap.new Node(c,freq_map.get(c));
		    	heap.insert(n);
		    }
		    while (heap.getSize() > (heap.heapStart+1)) { 
	    		Node t1 = heap.delete(heap.heapStart); // Remove the smallest weight tree
	    		Node t2 = heap.delete(heap.heapStart); // Remove the next smallest weight 
	    		Node root = heap.new Node("internal",t1.freq+t2.freq);
	    		root.left=t1;
	    		root.right=t2;	
	    	    heap.insert(root); // Combine two trees
	    	}
		    return heap.delete(heap.heapStart); 
		}
		
		
		//Generate code Table
		private static void buildCodes(Node root1, String repr,File codeTableFile,BufferedWriter output) throws IOException {
			String s1 , s2;
			if (root1.left== null && root1.right== null)
		    {	
				String text=root1.data+" ";
	            output.write(text+repr);
	            output.write("\n");
	            codeTable.put(String.valueOf(root1.data),repr);
		        
		    }
			if(root1.left!= null){
				s1 = repr;
			    s1 += "0";
			    buildCodes(root1.left, s1,codeTableFile,output); // recursive call to the left
			}
			if(root1.right!= null){
			    s2 = repr;
			    s2 += "1";
			    buildCodes(root1.right, s2,codeTableFile,output);
			}	
		}

		
		
		private static void encode(BufferedReader bufferedReader, BufferedOutputStream buffOpStream) throws IOException {
			
			String line;
			int outputByte = 0;
			boolean[] bitArray = new boolean[8];
			String buffer= "";
	        while((line = bufferedReader.readLine()) != null ) {
	        	String code = codeTable.get(line);
	        	if(code!= null){ 
	        		buffer = buffer+code; 
	        		while (buffer.length() >= 8) {
	    				for (int i = 0; i < 8; ++i) {
	    					if (buffer.charAt(i) == '1')
	    						bitArray[i] = true;
	    					else
	    						bitArray[i] = false; 
	    				}
	    				buffer = buffer.substring(8);
	    				outputByte = computeBytes(bitArray);
	    				buffOpStream.write(outputByte);	
	    			} 
	        		
	        	}
	        }
	        if (!buffer.isEmpty()){
		        for(int i = 0; i < buffer.length(); ++i) {
	                if(i > 8-buffer.length()) {
	                	bitArray[i] = false;
	                }
	                else {
	                    if(buffer.charAt(i) == '1')
	                    	bitArray[i] = true;
	                    else
	                        bitArray[i] = false;
	                }
		        }
	        
		        outputByte = computeBytes(bitArray);
		        buffOpStream.write(outputByte);
		        System.out.println(outputByte);
	        }
	        buffOpStream.close();
	        
		}
		
		public static int computeBytes(boolean[] bitArray) {
			if (bitArray == null || bitArray.length != 8) {
				throw new IllegalArgumentException();
			}
			int data = 0;
			for (int i = 0; i < 8; i++) {
				if (bitArray[i])
					data += (1 << (7 - i));
			}
			return data;
		}
}
