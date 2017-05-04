import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;


public class decoder extends FourWayHeap{
	static Node root=null;
	static Node next=null;
	static Heap h = new Heap();
	
	
	public static void main(String args[]) throws IOException{
		decode(args);
	}
	
	
	private static void decode(String args[]) throws IOException {
		try{
			long startTime = System.currentTimeMillis();
			String encodedFile = args[0];
			String inputFile =args[1];
			FileInputStream in = new FileInputStream(inputFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			int count=0;  
			while ((line = br.readLine()) != null && !line.isEmpty()) {
				String inputSplit[] = line.split(" ");
				String value =inputSplit[0];
				String code = inputSplit[1];
				count++;
				buildTree(value, code);
			}
			
			br.close();
			if(count==0)
				throw new  IOException("Code File is empty");
			in.close();
			generateOutput(encodedFile);
			long elapsedTime = (new Date()).getTime() - startTime;
		    //System.out.println("Time for decoding using Fourway heap (millisec):  " + elapsedTime );
		}catch (Exception e){
			e.printStackTrace();
		}
		
        			
	}
	
	
	private static void buildTree(String value, String code) {
		Node curr;
		if(root==null){
			root = h.new Node(); //created the root
		}
		curr=root;
		for(int i =0; i< code.length();i++){
			if(code.charAt(i)=='0'){
				if(curr.left!=null){
					curr=curr.left;
				}
				else{
					Node next = h.new Node();
					curr.left = next;
					curr=next;
				}
					
			}
			else{
				if(curr.right!=null){
					curr=curr.right;
				}
				else{
					Node next = h.new Node();
					curr.right = next;
					curr=next;
				}
					
			}
		}
		curr.data = value;
		curr.left=null;
		curr.right=null;	
	}


	private static void generateOutput(String encodedFilePath) throws IOException {
		
		File decodedFile = new File("decoded.txt");
		@SuppressWarnings("resource")
		BufferedWriter output = new BufferedWriter(new FileWriter(decodedFile));
		File encodedFile = new File(encodedFilePath);
		String inputFile =encodedFile.getAbsolutePath();
		long fileSize = new File(inputFile).length();
		byte[] data = new byte[(int) fileSize];
		@SuppressWarnings("resource")
		FileInputStream in = new FileInputStream(inputFile);
		boolean[] bits = new boolean[8] ;
		int count=0;
		next=root;
		Node rootTemp=root;
		while ((count=in.read(data)) != -1) {/*
			bits = new boolean[data.length * 8];
		    for (int i = 0; i < data.length * 8; i++) {
			      if ((data[i / 8] & (1 << (7 - (i % 8)))) > 0)
			        bits[i] = true;
			}*/
			if(count==0)
				throw new IOException ("Encoded file is empty");
			for(int i = 0; i < data.length; i++){
				for(int j=0; j<8;j++){
					if((data[i] & (1 << (7 - (j)))) > 0)
						bits[j]=true;
					else
						bits[j]=false;
				}
				for(boolean opBits:bits){
		        	
		    		if(!opBits){
		    			rootTemp=rootTemp.left;
		    			//System.out.print(0);
		    		}
		    		if(opBits){
		    			rootTemp=rootTemp.right;
		    			//System.out.print(1);
		    		}
		    		if(rootTemp.left == null && rootTemp.right == null){
		        		output.write(rootTemp.data+"\n");
		        		rootTemp=root;
		        	}
		        }
			}
			/*
			for(byte b: data){
				buffer=new StringBuilder(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));		
				printBytes(buffer.toString(), output);
				
			}*/
		}
        /*for(boolean opBits:bits){
        	
    		if(!opBits){
    			rootTemp=rootTemp.left;
    			//System.out.print(0);
    		}
    		if(opBits){
    			rootTemp=rootTemp.right;
    			//System.out.print(1);
    		}
    		if(rootTemp.left == null && rootTemp.right == null){
        		output.write(rootTemp.data+"\n");
        		rootTemp=root;
        	}
        }*/
		
		output.close();
		in.close();
	}
	
	
	private static String printBytes(String string, BufferedWriter output) throws IOException {
		Node rootTemp=next;
		for(int i=0; i< string.length();i++){
			char opBits = string.charAt(i);
			
			if(opBits =='0'){
				rootTemp=rootTemp.left;
				next=rootTemp;
				//System.out.print(0);
			}
			if(opBits=='1'){
				rootTemp=rootTemp.right;
				next=rootTemp;
				//System.out.print(1);
			}
			if(rootTemp.left == null && rootTemp.right == null){
	    		output.write(rootTemp.data+"\n");
	    		rootTemp=root;
	    		next=root;
	    	}
		}
	
		return "";
	}
}