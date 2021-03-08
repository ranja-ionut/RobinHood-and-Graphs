import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class P2 {
	static class Task {
		public static final String INPUT_FILE = "p2.in";
		public static final String OUTPUT_FILE = "p2.out";
		public static final int NMAX = 100005;
		public static final int CMAX = Integer.MAX_VALUE / 2;
		
		int n, m, source, dest;
		int[] dist;
		boolean[] visited;

		public class Edge {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<Edge>[] adj = new ArrayList[NMAX];
		
		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				
				n = sc.nextInt();
				m = sc.nextInt();
				source = sc.nextInt();
				dest = sc.nextInt();
				
				dist = new int[n + 1];
				visited = new boolean[n + 1];
				
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Edge(y, w));
				}
				
				/*
					Avem o citire de M triplete, M fiind 2 * N,
					deci complexitatea citirii va fi O ( N ).
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
				pw.println(dist[dest]);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		/*
			Algoritmul bellmanFord optimizat care nu face verificarea pentru 
			cicluri negative, cerinta specificand ca exista solutie pentru fiecare
			test, deci verificarea nemaifiind necesara.
			
			Cu tot cu aceste optimizari, in cel mai rau caz, cum ar fi testul 15,
			complexitatea va fi cea specifica algoritmului bellmanFord, adica
			O (|E| * |V|) = O (M * N) = O (N ^ 2)
		*/
		private boolean bellmanFord() {
			for (int v = 1; v <= n; v++) {
				dist[v] = CMAX;
			}
			
			LinkedList<Integer> queue = new LinkedList<>();
			
			dist[source] = 0;
			queue.add(source);
			
			// Optimizarea folosind o coada pentru relaxari
			while (!queue.isEmpty()) {
				int u = queue.remove();
				
				visited[u] = false;
				
				if (dist[u] != CMAX) {
					for (Edge e : adj[u]) {
						if (dist[e.node] > dist[u] + e.cost) {
							dist[e.node] = dist[u] + e.cost;
							
							if (!visited[e.node]) {
								visited[e.node] = true;
								queue.add(e.node);
								
								// Optimizarea folosind comportamentul de MINHEAP sau PRIORITYQUEUE
								if (dist[queue.getLast()] < dist[queue.getFirst()]) {
									int w = queue.removeLast();
									queue.addFirst(w);
								}
							}
						}
					}
				}
			}
			
			return true;
		}

		private void compute_solution() {
			bellmanFord();
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
