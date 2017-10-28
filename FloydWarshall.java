package Graph;

public class FloydWarshall {

	public static void floydWarshall(Graph g) {
		int[][] pi = inicializarPi(g);
		algorithm(g, pi);

		System.out.println("Distancias mas cortas de cada nodo a los demás");
		imprimir(algorithm(g, pi));
		System.out.println("\nPadres de cada nodo");
		imprimir(pi);
	}

	public static int[][] pi(Graph g) {
		int[][] pi = inicializarPi(g);
		algorithm(g, pi);
		return pi;
	}

	public static int[][] d(Graph g) {
		return algorithm(g, inicializarPi(g));
	}

	private static int[][] algorithm(Graph g, int[][] pi) {

		int[][] d = g.toMatrix();
		for (int k = 0; k < g.size(); k++)
			for (int i = 0; i < g.size(); i++)
				for (int j = 0; j < g.size(); j++)
					if (i != j) 
						if (d[i][j] > d[i][k] + d[k][j]) {
							d[i][j] = d[i][k] + d[k][j];
							pi[i][j] = pi[k][j];						
					}

		return d;
	}

	private static int[][] inicializarPi(Graph g) {
		int[][] pi = new int[g.size()][g.size()];
		for (int i = 0; i < pi.length; i++)
			for (int j = 0; j < pi.length; j++)
				pi[i][j] = (i != j && peso(g, i, j) < 99999) ? i : -1;
		return pi;
	}

	private static int peso(Graph g, int i, int j) {
		if (!g.weights.containsKey(i + "" + j))
			return 99999;
		return g.getWeight(i, j);
	}

	public static void imprimir(int[][] a) {
		for (int[] is : a) {
			for (int i : is)
				System.out.print(i + " ");
			System.out.println();
		}
	}
}