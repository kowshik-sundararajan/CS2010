import java.util.ArrayList;

/******************************************************************************
 *  Compilation:  javac Graph.java        
 *  Execution:    java Graph input.txt
 *  Dependencies: Bag.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *
 *  A graph, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *
 *  % java Graph tinyG.txt
 *  13 vertices, 13 edges 
 *  0: 6 2 1 5 
 *  1: 0 
 *  2: 0 
 *  3: 5 4 
 *  4: 5 6 3 
 *  5: 3 4 0 
 *  6: 0 4 
 *  7: 8 
 *  8: 7 
 *  9: 11 10 12 
 *  10: 9 
 *  11: 9 12 
 *  12: 11 9 
 *
 *  % java Graph mediumG.txt
 *  250 vertices, 1273 edges 
 *  0: 225 222 211 209 204 202 191 176 163 160 149 114 97 80 68 59 58 49 44 24 15 
 *  1: 220 203 200 194 189 164 150 130 107 72 
 *  2: 141 110 108 86 79 51 42 18 14 
 *  ...
 *  
 ******************************************************************************/


/**
 *  The <tt>AdjListGraph</tt> class represents an undirected graph of vertices
 *  named 0 through <em>V</em> - 1.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all of the vertices adjacent to a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which 
 *  is a vertex-indexed array of {@link Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the vertices adjacent to a given vertex, which takes
 *  time proportional to the number of such vertices.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class AdjListGraph {
	private static final String NEWLINE = System.getProperty("line.separator");

	private final int V;
	private int E;
	private Bag<double[]>[] adj;
	private ArrayList<Integer> blockedCountries;


	/**
	 * Initializes an empty graph with <tt>V</tt> vertices and 0 edges.
	 * param V the number of vertices
	 *
	 * @param  V number of vertices
	 * @throws IllegalArgumentException if <tt>V</tt> < 0
	 */
	public AdjListGraph(int V) {
		if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
		this.V = V;
		this.E = 0;
		adj = (Bag<double[]>[]) new Bag[V];
		for (int v = 0; v <V; v++) {
			adj[v] = new Bag<double[]>();
		}
		blockedCountries = new ArrayList<Integer>();
	}

	/**
	 * Initializes a new graph that is a deep copy of <tt>G</tt>.
	 *
	 * @param  G the graph to copy
	 */
	public AdjListGraph(AdjListGraph G) {
		this(G.V());
		this.E = G.E();
		for (int v = 0; v < G.V(); v++) {
			// reverse so that adjacency list is in same order as original
			Stack<double[]> reverse = new Stack<double[]>();
			for (double[] temp : G.adj[v]) {
				reverse.push(temp);
			}
			for (double[] temp : reverse) {
				adj[v].add(temp);
			}
		}
	}

	/**
	 * Returns the number of vertices in this graph.
	 *
	 * @return the number of vertices in this graph
	 */
	public int V() {
		return V;
	}

	/**
	 * Returns the number of edges in this graph.
	 *
	 * @return the number of edges in this graph
	 */
	public int E() {
		return E;
	}

	/**
	 * Returns the size of this graph.
	 *
	 * @return the size of this graph.
	 */
	public int sizeOfGraph() {
		int temp = E*2*4 + E*2*8;
		return temp;
	}

	public int edgeMemory() {
		int temp = E*4;
		return temp;
	}

	// throw an IndexOutOfBoundsException unless 0 <= v < V
	private void validateVertex(int v) {
		if (v < 0 || v >= V)
			throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
	}

	/**
	 * Adds the undirected edge v-w to this graph.
	 *
	 * @param  v one vertex in the edge
	 * @param  w the other vertex in the edge
	 * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
	 */
	public void addEdge(int v, int w, double distance) {
		validateVertex(v);
		validateVertex(w);
		E++;
		double[] temp = new double[2];
		temp[0] = w;
		temp[1] = distance;
		adj[v].add(temp);
	}


	/**
	 * Returns the vertices adjacent to vertex <tt>v</tt>.
	 *
	 * @param  v the vertex
	 * @return the vertices adjacent to vertex <tt>v</tt>, as an iterable
	 * @throws IndexOutOfBoundsException unless 0 <= v < V
	 */
	public Iterable<double[]> adj(int v) {
		validateVertex(v);
		return adj[v];
	}

	/**
	 * Returns the degree of vertex <tt>v</tt>.
	 *
	 * @param  v the vertex
	 * @return the degree of vertex <tt>v</tt>
	 * @throws IndexOutOfBoundsException unless 0 <= v < V
	 */
	public int degree(int v) {
		validateVertex(v);
		return adj[v].size();
	}
}
