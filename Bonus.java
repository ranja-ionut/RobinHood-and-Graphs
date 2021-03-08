import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Bonus {
	static class Task {
		public static final String INPUT_FILE = "bonus.in";
		public static final String OUTPUT_FILE = "bonus.out";

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput() {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void compute_solution() {
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
