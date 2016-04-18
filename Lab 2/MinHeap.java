import java.util.PriorityQueue;
import java.util.*;
import java.io.*;

public class MinHeap {

	private static PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
	private static PriorityQueue<Integer> secondaryMinHeap = new PriorityQueue<Integer>();

	private static Scanner sc = new Scanner(System.in);

	private static String fileName;
	private static int k;

	private static String WELCOME_MESSAGE = "Enter the file name: ";
	
	//Function to show the welcome message to the user
	public static void showUserPrompt() {
		System.out.println(WELCOME_MESSAGE);
	}

	//Function to accept the file name
	public static void acceptFileName() {
		fileName = sc.next();
	}
	
	//Function to assign the value of k
	public static void acceptk(BufferedReader br, String temp) throws IOException {
		k = Integer.parseInt(temp);
	}
	
	//Function to decode the input entered by the user
	public static void decodeCommand(BufferedReader br) throws IOException {
		String line; int counter = 0;
		while(((line = br.readLine()) != null)) {
			counter++;
			String[] inputParts = line.split(",");
			if(counter == 1) {
				acceptk(br, inputParts[0].substring(1));
			}
			for(int i=1; i<inputParts.length; i++) {
				if(i == inputParts.length - 1) {
					inputParts[i] = inputParts[i].substring(1, 2);
				}
				else {
					inputParts[i] = inputParts[i].substring(1);
				}
				calculate(inputParts[i]);
			}
		} 
	}
	
	public static void calculate(String input) {
		if(input.equals("+")) {
			int kthElement = peek();
			if(kthElement == (minHeap.peek() -1)){
				System.out.println("null");
			}
			else {
				printKthElement();
			}
		}
		else if(input.equals("-")) {
			int kthElement = delete();
			if(kthElement == (minHeap.peek() -1))	{
				System.out.println("null");
			}
			else {
				System.out.println(kthElement);
			}
		}
		else if(input.contains("]")== true) {
			System.exit(0);
		}
		else {
			insert(Integer.parseInt(input));
		}

	}

	//Function to display the kth element
	public static int peek() {
		if((minHeap.size() + secondaryMinHeap.size())>= k) {
			return secondaryMinHeap.peek();	
		}
		else {
			return (minHeap.peek() - 1);
		}
	}

	//Function to delete the kth element
	public static int delete() {
		if((minHeap.size() + secondaryMinHeap.size())>= k) {
			return secondaryMinHeap.poll();	
		}
		else {
			return (minHeap.peek() - 1);
		}
	}

	//Function to insert a new element
	public static void insert(Integer temp) {
		minHeap.add(temp);
		
		PriorityQueue<Integer> tempPQ = new PriorityQueue<Integer>();
		
		if(minHeap.size() == k) {
			while(minHeap.size() > 1) {
				tempPQ.add(minHeap.poll());
			}
			secondaryMinHeap.add(minHeap.poll());
			minHeap = tempPQ;
		}
	}

	/*public static void printHeap() {
		System.out.println("First Heap");
		while(minHeap.size() > 0) {
			System.out.print(minHeap.poll() + " ");
		}

		System.out.println();
		System.out.println("Second Heap");
		while(secondaryMinHeap.size() > 0) {
			System.out.print(secondaryMinHeap.poll() + " ");
		}
		System.exit(0);
	}*/

	//Function to print the kth element
	public static void printKthElement() {
		System.out.println(secondaryMinHeap.peek());
	}

	//Function to check if the first min heap is empty
	public static boolean isEmpty() {
		return minHeap.size() == 0;
	}

	public static void main(String args[]) throws IOException {
		showUserPrompt();
		acceptFileName();			
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			decodeCommand(br);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}