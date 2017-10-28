package Graph;

import java.util.*;

public class Dijkstra {

	public static void dijkstra(Graph g, int inicio) {

		Object[] d = new Object[g.size()], pi = new Object[g.size()];
		ArrayList<Integer> q = inicializarQ(d, inicio);

		algorithm(g, d, pi, q);
		System.out.println("Padres de cada nodo");
		print(pi);
		System.out.println("\nDistancias minimas a cada nodo");
		print(d);
	}

	public static Object[] pi(Graph g, int inicio) {
		Object[] d = new Object[g.size()], pi = new Object[g.size()];
		ArrayList<Integer> q = inicializarQ(d, inicio);
		algorithm(g, d, pi, q);
		return pi;
	}

	public static Integer[] d(Graph g, int inicio) {
		Integer[] d = new Integer[g.size()];
		Object[] pi = new Object[g.size()];
		ArrayList<Integer> q = inicializarQ(d, inicio);
		algorithm(g, d, pi, q);
		return d;
	}

	private static void algorithm(Graph g, Object[] d, Object[] pi, ArrayList<Integer> q) {
		while (!q.isEmpty()) {
			int evaluando = minimoValor(d, q);
			List<Integer> conexiones = g.getAdjs(evaluando);
			if (conexiones != null)
				for (Integer conexion : conexiones) {
					int aux = (int) d[evaluando] + peso(g, evaluando, conexion);
					if ((int) d[conexion] > aux) {
						d[conexion] = aux;
						pi[conexion] = evaluando;
					}
				}
		}
	}

	static int minimoValor(Object[] d, ArrayList<Integer> q) {
		int valor = 0, min = 99999;
		for (int i = 0; i < d.length; i++)
			if ((int) d[i] < min && q.indexOf(i) != -1)
				min = (int) d[valor = i];
		q.remove(q.indexOf(valor));
		return valor;
	}

	private static int peso(Graph g, int i, int j) {
		if (!g.weights.containsKey(i + "" + j))
			return 99999;
		return g.getWeight(i, j);
	}

	static ArrayList<Integer> inicializarQ(Object[] d, int inicio) {
		ArrayList<Integer> q = new ArrayList<>();
		for (int i = 0; i < d.length; i++) {
			d[i] = i != inicio ? 99999 : 0;
			q.add(i);
		}
		return q;
	}

	private static void print(Object[] a) {
		for (Object i : a)
			System.out.print(i + " ");
	}
}