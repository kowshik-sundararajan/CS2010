import java.util.Iterator;
import java.util.NoSuchElementException;

public class AdjMatrixEfficient {
	private static final String NEWLINE = System.getProperty("line.separator");
	private int V;
	private int E;
	private boolean[][] adj;

	// empty graph with V vertices
	public AdjMatrixEfficient(int V) {
		if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
		this.V = V;
		this.E = 0;
		this.adj = new boolean[V][];

		for(int i = 1; i<V; i++) {
			this.adj[i] = new boolean[i];
		}
	}
	/**  
	 * Initializes a graph from an input stream.
	 * The format is the number of vertices <em>V</em>,
	 * followed by the number of edges <em>E</em>,
	 * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
	 *
	 * @param  in the input stream
	 * @throws IndexOutOfBoundsException if the endpoints of any edge are not in prescribed range
	 * @throws IllegalArgumentException if the number of vertices or edges is negative
	 */
	public AdjMatrixEfficient(In in) {
		this(in.readInt());
		int E = in.readInt();
		if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
		for (int i = 0; i < E; i++) {
			int v = in.readInt();
			int w = in.readInt();
			addEdge(Math.max(v, w), Math.min(v, w));
		}
	}

	// number of vertices and edges
	public int V() { return V; }
	public int E() { return E; }


	// add undirected edge v-w
	public void addEdge(int v, int w) {
		if (adj[v][w] == false) {
			E++;
		}
		adj[v][w] = true;
	}

	public void printAdjacent() {
		for(int i = 0; i<V; i++) {
			for(int j = 0; j<i; j++) {
				System.out.print(adj[i][j] + " ");
			}
			System.out.println();
		}
	}

	// does the graph contain the edge v-w?
	public boolean contains(int v, int w) {
		return adj[v][w];
	}

	// return list of neighbors of v
	public Iterable<Boolean> adj(int v) {
		return new AdjIterator(v);
	}

	/**
	 * Returns the size of this graph.
	 *
	 * @return the size of this graph.
	 */   
	public int sizeOfGraph() {
		int temp = (V() * V() - V())/2;
		return temp;
	}
	
	/**
	 * To print the graph
	 */
	public void printGraph() {
		for (int i = 0; i < V(); i++) {
			Iterable<Boolean> myIterator = adj(i);
			String neighbours = i + ":";
			int count = 0;
			while (myIterator.iterator().hasNext()) {	
				if (myIterator.iterator().next()) {
					neighbours += " " + count;
				}
				count++;
			}
			System.out.println(neighbours);
		}
	}

	// support iteration over graph vertices
	private class AdjIterator implements Iterator<Boolean>, Iterable<Boolean> {
		private int v;
		private int count;
		private boolean[] neighbours;

		AdjIterator(int v) {
			this.v = v;
			this.count = -1;
			neighbours = new boolean[V];


			if (v == 0) {
				for (int i = 1; i < V; i++) {
					if (adj[i][0]) {
						neighbours[i] = true;
					}
				}
			} else if (v == V-1) {
				for (int i = 0; i < v; i++) {
					if (adj[v][i]) {
						neighbours[i] = true;
					}
				}
			} else {
				for (int i = v + 1; i < V; i++) {
					if (adj[i][v]) {
						neighbours[i] = true;
					}
				}
				for (int i = 0; i < v; i++) {
					if (adj[v][i]) {
						neighbours[i] = true;
					}
				}
			}
		}

		public Iterator<Boolean> iterator() {
			return this;
		}

		public boolean hasNext() {
			return count < V-1;
		}

		public Boolean next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			count++;
			return neighbours[count];
		}

		public void remove()  {
			throw new UnsupportedOperationException();
		}
	}   

}

