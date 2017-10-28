package Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

public class Graph {

	private int size;
	public boolean digraph;
	public HashMap<String, Integer> weights;
	public HashMap<Integer, List<Integer>> vertex;

	public Graph(int size, boolean digraph) {
		this.size = size;
		this.digraph = digraph;
		vertex = new HashMap<>();
		weights = new HashMap<>();
	}

	public int size() {
		return size;
	}

	public void insertEdge(String i, String j, String w) {
		int a = Integer.parseInt(i), b = Integer.parseInt(j), c = Integer.parseInt(w);
		if (a < size && b < size) {
			insert_Edge(a, b, c);
			if (!digraph)
				insert_Edge(b, a, c);
		} else
			System.out.println("error");
	}

	public int getWeight(int i, int j) {
		return weights.get((i + "" + j));
	}

	public boolean existEdge(int i, int j) {
		return weights.containsKey(i + "" + j);
	}

	public List<Integer> getAdjs(int i) {
		return vertex.get(i);
	}

	private void insert_Edge(int i, int j, int w) {
		weights.put(i + "" + j, w);
		if (!vertex.containsKey(i))
			vertex.put(i, new ArrayList<>());
		vertex.get(i).add(j);
	}

	public void printGraph() {
		for (Object v : vertex.keySet().toArray())
			System.out.println(v + " -> " + vertex.get(v));
	}

	public int[][] toMatrix() {
		int[][] m = new int[size()][size()];
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++)
				if (i != j)
					m[i][j] = weights.containsKey(i + "" + j) ? getWeight(i, j) : 99999;
		}
		return m;
	}

	public static Graph toGraph(int[][] m) {
		Graph g = new Graph(m.length, true);
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++)
				if (i != j)
					g.insertEdge(String.valueOf(i), String.valueOf(j), String.valueOf(m[i][j]));
		}
		return g;
	}

	public boolean existLoop() {
		// getAdjs((int) v).forEach(j -> set.addAll(getAdjs(j)));

		for (Object i : vertex.keySet().toArray()) {
			HashSet<Integer> common = new HashSet<>();
			List<Integer> adjs = getAdjs((int) i);
			for (Integer j : adjs)
				if (vertex.containsKey(j))
					common.addAll(getAdjs(j));			
			for (Integer j : adjs)
				if (common.contains(j))
					return true;
		}
		return false;
	}

	public int minimum(int i, int j) {
		int ij = minimumCostAndPath(i, j);
		if (digraph)
			return Math.min(ij, minimumCostAndPath(j, i));
		return ij;
	}

	private int minimumCostAndPath(int i, int j) {
		List<List<Integer>> paths = new ArrayList<List<Integer>>();
		DFS(i, j, new boolean[size()], new ArrayDeque<>(), paths);

		StringBuilder pathFinal = new StringBuilder();
		int minimum = Dijkstra.d(this, i)[j];

		for (List<Integer> path : paths) {
			StringBuilder pathTmp = new StringBuilder();
			int tmp = 0;
			for (int k = 0, l = 1; l < path.size(); k = l, l++) {
				tmp += getWeight(path.get(k), path.get(l));
				if (l % 2 != 0)
					pathTmp.append((path.get(k)) + " " + (path.get(l)) + " ");
				else if (l == path.size() - 1)
					pathTmp.append((path.get(l)));
			}
			if (tmp == minimum) {
				minimum = tmp;
				if (pathTmp.length() < pathFinal.length() || pathFinal.length() == 0)
					pathFinal = pathTmp;
			}
		}
		System.out.println("Path-> " + pathFinal);
		System.out.println("Cost: " + minimum);
		return minimum;
	}

	public List<List<Integer>> getAllPathsBetweenTwoNodes(int i, int j) {
		List<List<Integer>> paths = new ArrayList<List<Integer>>();
		DFS(i, j, new boolean[size()], new ArrayDeque<>(), paths);
		return paths;
	}

	private void DFS(int i, int j, boolean[] visited, Deque<Integer> path, List<List<Integer>> result) {
		visited[i] = path.add(i);
		if (i == j)
			result.add(new ArrayList<Integer>(path));
		else if (vertex.containsKey(i)) {
			for (Integer adj : vertex.get(i))
				if (!visited[adj])
					DFS(adj, j, visited, path, result);
		}
		path.removeLast();
		visited[i] = false;
	}

	public int TSP(int start) {
		int finalCost = Integer.MAX_VALUE;
		StringJoiner finalPath = new StringJoiner("->"), path;

		for (int i = 0; i < size * 9999; i++) {
			int cost = 0, current = start, previous = current;

			List<Integer> list = new ArrayList<>();
			vertex.keySet().forEach(x -> list.add(x));
			path = new StringJoiner("->").add(list.remove(list.indexOf(start)) + "");

			do {
				if (list.contains(current = new Random().nextInt(size()))) {
					cost += getWeight(previous, list.remove(list.indexOf(current)));
					path.add(String.valueOf(previous = current));
				}
			} while (!list.isEmpty() && cost < finalCost);
			if ((cost += getWeight(current, start)) < finalCost) {
				finalCost = cost;
				finalPath = path;
			}
		}
		System.out.println("Path: " + finalPath.add(start + "") + "\nCost: " + finalCost);
		return finalCost;
	}
}
