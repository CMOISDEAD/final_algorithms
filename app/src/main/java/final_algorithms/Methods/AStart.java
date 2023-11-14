package final_algorithms.Methods;

import final_algorithms.Graphs.NodeAux;
import final_algorithms.Graphs.PathResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

// WARN: java error: memory heap
public class AStart {
  public PathResult aStar(int[][] graph, int source, int destination) {
    int V = graph.length;
    int[] distance = new int[V];
    Arrays.fill(distance, Integer.MAX_VALUE);

    // PriorityQueue ordenado por el costo total (costo acumulado + heurística)
    PriorityQueue<NodeAux> minHeap = new PriorityQueue<>((a, b) -> a.getDistance() + a.getHeuristic() -
        (b.getDistance() + b.getHeuristic()));
    distance[source] = 0;
    minHeap.add(new NodeAux(
        source, 0,
        heuristic(source,
            destination))); // Agregar el nodo de origen con su heurística

    int[] parent = new int[V];
    Arrays.fill(parent, -1);

    while (!minHeap.isEmpty()) {
      NodeAux node = minHeap.poll();
      int u = node.getNode();

      if (u == destination) {
        return reconstructPath(parent, source, destination, distance[u]);
      }

      for (int v = 0; v < V; v++) {
        if (graph[u][v] != 0) { // Si hay una arista válida
          int newCost = distance[u] + graph[u][v];

          if (newCost < distance[v]) {
            distance[v] = newCost;
            parent[v] = u;
            int totalCost = newCost + heuristic(v, destination);
            minHeap.add(new NodeAux(v, newCost, totalCost));
          }
        }
      }
    }

    return new PathResult(Collections.emptyList(), -1);
    // Si no puede llegar al nodo destino, se devuelve -1
  }

  public int heuristic(int from, int to) {
    // La función heurística es utilizada, pero no se debe sobreestimar el costo
    // real
    return Math.abs(from - to);
  }

  public PathResult reconstructPath(int[] parent, int source, int destination,
      int distance) {
    List<Integer> path = new ArrayList<>();
    int current = destination;

    while (current != -1) {
      path.add(current);
      current = parent[current];
    }

    Collections.reverse(path);

    return new PathResult(path, distance);
  }

  public void execute(String path, int source, int destination) {
    int[][] graph = readGraphFromFile(path);

    PathResult result = aStar(graph, source, destination);
    if (result.distance == -1) {
      System.out.println("No se encontró un camino");
    } else {
      System.out.println("El camino más corto desde " + source + " a " +
          destination + " es: " + result.path);
      System.out.println("La distancia es: " + result.distance);
    }
  }

  public int[][] readGraphFromFile(String path) {
    int maxNode = 0;

    try {
      File file = new File(path);
      Scanner scanner = new Scanner(file);

      while (scanner.hasNext()) {
        int source = scanner.nextInt();
        int destination = scanner.nextInt();
        int weight = scanner.nextInt();

        maxNode = Math.max(maxNode, Math.max(source, destination));
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    int[][] graph = new int[maxNode + 1][maxNode + 1];
    initializeGraph(graph);

    try {
      File file = new File(path);
      Scanner scanner = new Scanner(file);

      while (scanner.hasNext()) {
        int source = scanner.nextInt();
        int destination = scanner.nextInt();
        int weight = scanner.nextInt();

        graph[source][destination] = weight;
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return graph;
  }

  public void initializeGraph(int[][] graph) {
    for (int i = 0; i < graph.length; i++) {
      for (int j = 0; j < graph[i].length; j++) {
        graph[i][j] = Integer.MAX_VALUE;
      }
    }
  }
}
