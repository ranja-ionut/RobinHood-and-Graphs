import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P3 {
	static class Task {
		public static final String INPUT_FILE = "p3.in";
		public static final String OUTPUT_FILE = "p3.out";
		public static final int NMAX = 100005;
		public static final int SOURCE = 1;
		
		int n, m, energy, dest, time = 0;
		
		double max_energy = 0;
		
		double[] dist;
		boolean[] visited;
		int[] parent;
		
		public class Edge implements Comparable<Edge> {
			public int node;
			public double cost;

			Edge(int _node, double _cost) {
				node = _node;
				cost = _cost;
			}

			@Override
			public int compareTo(Edge e) {
				if (cost < e.cost) {
					return 1;
				}
				
				if (cost > e.cost) {
					return -1;
				}
				
				return 0;
			}
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<Edge>[] adj = new ArrayList[NMAX];
		LinkedList<Integer> path = new LinkedList<>();
		PriorityQueue<Edge> queue = new PriorityQueue<>();

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				
				n = sc.nextInt();
				m = sc.nextInt();
				energy = sc.nextInt();
				
				dest = n;
				
				max_energy = (double) energy;
				
				parent = new int[n + 1];
				dist = new double[n + 1];
				visited = new boolean[n + 1];
				
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				
				for (int i = 1; i <= m; i++) {
					int x, y;
					double w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Edge(y, (100.0 - w) / 100.0));
				}
				
				/*
					Citirea se va face de M ori, M fiind 2 * N,
					deci complexitatea pentru citire va fi O ( N ).
				*/
				
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput() {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				
				pw.println(dist[n]);
				
				/*
					Scrierea e mult mai mica fata de citire, in cel mai rau caz ar fi
					toate nodurile, deci un O ( N ).
				*/
				for (int v : path) {
					pw.print(v + " ");
				}
				
				pw.println();
				
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		/*
			Algoritmul DIJKSTRA optimizat cu PRIORITY QUEUE si folosit
			pentru a obtine energia maxima (un fel de `drum maxim`).
			
			Din ce am gasit din documentatiile ORACLE si alte surse, se pare ca
			complexitatea cozii cu prioritati este O (N + logN), O ( N ) pentru
			a sterge un element specific si O (log N) pentru a adauga in coada.
			Tinand cont ca stergerea unui element, in acest caz, implica preluarea
			primului element din coada, complexitatea reala este O (log N).
			
			Din explicatiile de pe OCW si din faptul ca acest PRIORITY QUEUE se
			comporta ca un MAX_HEAP, complexitatea total obtinuta este
			O(|V|log|V| + |E|) = O (N log N + M) = O (N log N + 2*N) = O (N log N).
		*/
		
		private void dijkstra() {
			for (int i = 0; i <= n; i++) {
				dist[i] = 0;
				parent[i] = -1;
			}
			
			// Se porneste de la energia maxima. Si se calculeaza cea finala in fiecare nod.
			dist[SOURCE] = max_energy;
			
			queue.add(new Edge(SOURCE, 0));
			
			while (!queue.isEmpty()) {
				int u = queue.remove().node;
				
				if (!visited[u]) {
					visited[u] = true;
					
					for (Edge e : adj[u]) {
						if (!visited[e.node]) {
							// Comparatia modificata pentru a obtine rezultatul corect.
							if (dist[e.node] < dist[u] * e.cost) {
								dist[e.node] = dist[u] * e.cost;
								
								parent[e.node] = u;
								
								queue.add(new Edge(e.node, dist[e.node]));
							}
						}
					}
				}
			}
		}

		private void compute_solution() {
			dijkstra();
			
			int v = n;
			
			// Construieste calea aleasa.
			while (v != -1) {
				path.addFirst(v);
				
				v = parent[v];
			}
		}

		public void solve() {
			readInput();
			compute_solution();
			writeOutput();
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
