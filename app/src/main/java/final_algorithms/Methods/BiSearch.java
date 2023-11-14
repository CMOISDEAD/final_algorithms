package final_algorithms.Methods;

import final_algorithms.Graphs.Edge;
import final_algorithms.Graphs.EdgeAux;
import final_algorithms.Graphs.Graph;
import final_algorithms.Graphs.PathResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class BiSearch {
  public PathResult desopoPape(Graph graph, int source, int destination) {
    int V = graph.V;
    int[] distance = new int[V];
    int[] parent = new int[V];
    Arrays.fill(distance, Integer.MAX_VALUE);
    Arrays.fill(parent, -1);
    distance[source] = 0;

    // Variable para verificar si existe un nodo en la cola
    boolean[] inQueue = new boolean[V];

    // Cola para el algotimo de D'Esopo-Pape
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(source);
    inQueue[source] = true;

    while (!queue.isEmpty()) {
      int u = queue.poll();
      inQueue[u] = false;

      for (EdgeAux edge : graph.adjacencyList[u]) {
        int v = edge.destination;
        int weight = edge.weight;

        if (distance[u] != Integer.MAX_VALUE &&
            distance[u] + weight < distance[v]) {
          distance[v] = distance[u] + weight;
          parent[v] = u;

          // Si v no está en la cola, se agrega
          if (!inQueue[v]) {
            queue.offer(v);
            inQueue[v] = true;
          }
        }
      }
    }

    // Reconstruir la ruta
    List<Integer> path = new ArrayList<>();
    int current = destination;
    while (current != -1) {
      path.add(current);
      current = parent[current];
    }
    Collections.reverse(path);

    return new PathResult(path, distance[destination]);
  }

  public void execute(String path, int start, int end) {
    Graph graph = readGraphFromFile(path);

    PathResult shortestDistance = desopoPape(graph, start, end);

    if (shortestDistance.distance == Integer.MAX_VALUE) {
      System.out.println("No hay un camino desde el nodo " + start +
          " al nodo " + end);
    } else {
      System.out.println("La distancia más corta desde el nodo " + start +
          " al nodo " + end +
          " es: " + shortestDistance.distance);
      System.out.println("El camino más corto es: " + shortestDistance.path);
    }
  }

  public Graph readGraphFromFile(String filePath) {
    int maxNode = 0;
    List<Edge> edges = new ArrayList<>();

    try {
      File file = new File(filePath);
      Scanner scanner = new Scanner(file);

      while (scanner.hasNext()) {
        int source = scanner.nextInt();
        int destination = scanner.nextInt();
        int weight = scanner.nextInt();

        edges.add(new Edge(source, destination, weight));
        maxNode = Math.max(maxNode, Math.max(source, destination));
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    Graph graph = new Graph(maxNode + 1);
    for (Edge edge : edges) {
      graph.addEdge(edge.source, edge.destination, edge.weight);
    }

    return graph;
  }
}
