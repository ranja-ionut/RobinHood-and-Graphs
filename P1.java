import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class P1 {
	static class Task {
		public static final String INPUT_FILE = "p1.in";
		public static final String OUTPUT_FILE = "p1.out";
		public static final int NMAX = 100005;
		public static final int SOURCE = 1;
		
		int n, m, k, min = 0;
		HashSet<Integer> lords;
		boolean[] blocked;
		int[] perm;
		
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] adj = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				
				n = sc.nextInt();
				m = sc.nextInt();
				k = sc.nextInt();
				
				lords = new HashSet<>();
				perm = new int[n + 1];
				
				for (int i = 1; i <= k; i++) {
					lords.add(sc.nextInt());
				}
				
				for (int i = 1; i <= n - 1; i++) {
					perm[i] = sc.nextInt();
				}
				
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				
				for (int i = 1; i <= m; i++) {
					int u = sc.nextInt();
					int v = sc.nextInt();
					
					adj[u].add(v);
					adj[v].add(u);
				}
				
				/*
					Citirea fiind facuta pentru N, M si K elemente, iar 
					toate fiind N sau N * 2, vom aproxima complexitatea
					la O ( N ).
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
				
				pw.println(min);
				
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		/*
			Algoritm BFS pentru explorarea nodurilor.
			
			Complexitatea pentru cel mai rau caz va fi O (|E| + |V|),
			adica O (M + N).
		*/
		private boolean bfs() {
			LinkedList<Integer> queue = new LinkedList<>();
			boolean[] visited = new boolean[n + 1];
			
			queue.add(SOURCE);
			
			int u;
			
			while (!queue.isEmpty()) {
				u = queue.remove();
				
				// Daca nodul curent nu este blocat atunci exploreaza-i vecinii
				if (!blocked[u]) {
					visited[u] = true;
					
					for (int v : adj[u]) {
						/*
							Daca nodul adiacent cu nodul u nu este vizitat si nu este nici
							blocat, atunci se va adauga in coada pentru explorarea BFS si
							va fi marcat ca fiind vizitat.
						*/
						if (!visited[v] && !blocked[v]) {
							visited[v] = true;
							queue.add(v);
						}
					}
				}
			}
			
			// Daca am ajuns din sursa la un lord, atunci nu am gasit solutia.
			for (int lord : lords) {
				if (visited[lord]) {
					return false;
				}
			}
			
			// Nu am vizitat niciun lord, deci am gasit o posibila solutie.
			return true;
		}
		
		/*
			Functia de cautare binara in vectorul de permutari.
			
			Complexitatea pentru cel mai rau caz va fi O (log N).
		*/
		private void search_min(int start, int end) {
			if (start > end) {
				return;
			}
			
			int mid = (start + end) / 2;
			
			blocked = new boolean[n + 1];
			
			// Se blocheaza toate nodurile de la cel selectat spre stanga lui.
			for (int i = 1; i <= mid; i++) {
				blocked[perm[i]] = true;
			}
			
			// Se verifica daca am gasit o posibila solutie.
			if (bfs()) {
				min = mid;
				
				// Se cauta o solutie mai buna prin blocarea a mai putin noduri.
				search_min(start, mid - 1);
			} else {
				// Nu am gasit o solutie, se incearca blocarea a mai multe noduri.
				search_min(mid + 1, end);
			}
		}

		private void compute_solution() {
			search_min(1, n - 1);
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
