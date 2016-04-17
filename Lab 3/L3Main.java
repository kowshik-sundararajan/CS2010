import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/** Lab 3 - CS2010 
 * "Space analysis of graphs"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: Kowshik Sundararajan	
 * YOUR MATRICULATION #: A0132791E
 * 
 * COMMENTS:
 * You are not allowed to change any existing codes for reading and writing. You
 * may add your own printing code to print your results of each part.
 * 
 * 
 * Assigned: Mar 2 (Wednesday), 2016
 * Due: Mar 18 (Friday by 11.59pm), 2016
 */


public class L3Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter name of the file");
		File name = new File(sc.next());

		In in1 = new In(name);
		In in2 = new In(name);
		In in3 = new In(name);
		
		AdjListGraph adjListGraph = new AdjListGraph(in1);
		AdjMatrixGraph adjMatGraph = new AdjMatrixGraph(in2);
		AdjMatrixEfficient adjMatEff = new AdjMatrixEfficient(in3);

		int numVertices = adjListGraph.V();
		int numEdges = adjListGraph.E();
	
		//Operations on adjacency list graph
		int listEdgeMemory = adjListGraph.E() * 4;
		int listTotalMemory = adjListGraph.sizeOfGraph();
		double listEfficiency = (double)listEdgeMemory/listTotalMemory * 100;

		//Operations on adjacency matrix graph
		int matEdgeMemory = adjMatGraph.E() * 1;
		int matTotalMemory = adjMatGraph.sizeOfGraph();
		double matEfficiency = (double)matEdgeMemory/matTotalMemory * 100;

		//Operations on efficient adjacency matrix graph
		int effMatEdgeMemory = adjMatEff.E() * 1;
		int effMatTotalMemory = adjMatEff.sizeOfGraph();
		double effMatEfficiency = (double)effMatEdgeMemory/effMatTotalMemory * 100;
		
		System.out.format("1. Number of vertices = %d \n", numVertices);
		System.out.format("2. Number of edges = %d \n", numEdges);
		System.out.format("3. Output of the graph using adjacency list: \n");
		System.out.println(numVertices + " vertices, " + numEdges + " edges");
		printAdjacencyList(adjListGraph);
		System.out.format("4. Adjacency list\n (a) Memory needed to record edges = %d\n" , listEdgeMemory);
        System.out.format(" (b) Total amount of memory used  = %d\n", listTotalMemory);
        System.out.format(" (c) Efficiency  = %f\n", listEfficiency);
		System.out.format("5. Output of the graph using matrix: \n");
		System.out.println(numVertices + " vertices, " + numEdges + " edges");
		printAdjacencyMatrix(adjMatGraph);
		System.out.format("6. Adjacency matrix\n (a) Memory needed to record edges = %d\n", matEdgeMemory);
        System.out.format(" (b) Total amount of memory used  = %d\n", matTotalMemory);
        System.out.format(" (c) Efficiency  = %f\n", matEfficiency);
        System.out.format("7. Additional task: Output of the graph using efficient matrix: \n");
		System.out.println(numVertices + " vertices, " + numEdges + " edges");
		adjMatEff.printGraph();
        System.out.format("8. Additional task: Efficient Adjacency matrix\n (a) Memory needed to record edges = %d\n" , effMatEdgeMemory);
        System.out.format(" (b) Total amount of memory used  = %d\n" , effMatTotalMemory);
        System.out.format(" (c) Efficiency  = %f\n" , effMatEfficiency);
	}

	/**
	 * Function to print the adjacency list graph
	 * @param adjListGraph the graph to be printed
	 */
	public static void printAdjacencyList(AdjListGraph adjListGraph) {
		for(int v = 0; v < adjListGraph.V(); v++) {
			Iterable<Integer> adj;
			adj = adjListGraph.adj(v);

			System.out.print(v + ": ");
			for(int elements : adj) {
				System.out.print(elements + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Function to print the adjacency matrix graph
	 * @param adjMatGraph the graph to be printed
	 */
	public static void printAdjacencyMatrix(AdjMatrixGraph adjMatGraph) {
		for(int v = 0; v < adjMatGraph.V(); v++) {
			Iterable<Integer> adj;
			adj = adjMatGraph.adj(v);

			System.out.print(v + ": ");
			for(int elements : adj) {
				System.out.print(elements + " ");
			}
			System.out.println();
		}
	}
}


