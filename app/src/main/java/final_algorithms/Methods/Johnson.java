package final_algorithms.Methods;

import final_algorithms.Graphs.Edge;
import final_algorithms.Graphs.Graph;
import final_algorithms.Graphs.PathResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Johnson {
  // Esta función es para encontrar el camino más corto entre todos los pares de
  // nodos
  public PathResult findShortestPath(Graph graph, int start, int end) {
    int V = graph.V;

    // Agrega un nuevo nodo ficticio y aristas desde él a todos los demás nodos
    for (int i = 0; i < V; i++) {
      graph.addEdgeAux(V, i, 0);
    }

    // Se ejecuta el algoritmo de Bellman-Ford para encontrar los pesos mínimos
    int[] h = bellmanFord(graph, V);

    // Se reescriben los pesos de las aristas y se debe ejecutar Dijkstra desde
    // el nodo de inicio al nodo final
    PathResult shortestPath = dijkstra(graph, start, end, h);

    return shortestPath;
  }

  // Implementación del algoritmo de Bellman-Ford
  private int[] bellmanFord(Graph graph, int source) {
    int V = graph.V;
    int[] distance = new int[V + 1];
    Arrays.fill(distance, Integer.MAX_VALUE);

    distance[source] = 0;

    for (int i = 1; i < V; i++) {
      for (Edge edge : graph.edges) {
        int u = edge.source;
        int v = edge.destination;
        int weight = edge.weight;

        if (distance[u] != Integer.MAX_VALUE &&
            distance[u] + weight < distance[v]) {
          distance[v] = distance[u] + weight;
        }
      }
    }

    return distance;
  }

  // Implementación del algoritmo de Dijkstra
  private PathResult dijkstra(Graph graph, int start, int end, int[] h) {
    int V = graph.V;
    int[] distance = new int[V];
    Arrays.fill(distance, Integer.MAX_VALUE);
    int[] parent = new int[V];
    Arrays.fill(parent, -1);

    distance[start] = 0;

    PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> distance[a] - distance[b]);
    minHeap.add(start);

    while (!minHeap.isEmpty()) {
      int u = minHeap.poll();

      for (Edge edge : graph.edges) {
        if (edge.source == u) {
          int v = edge.destination;
          int weight = edge.weight;

          if (distance[u] != Integer.MAX_VALUE &&
              distance[u] + weight + h[u] - h[v] < distance[v]) {
            distance[v] = distance[u] + weight + h[u] - h[v];
            parent[v] = u;
            minHeap.add(v);
          }
        }
      }
    }

    List<Integer> path = new ArrayList<>();
    int current = end;
    while (current != -1) {
      path.add(current);
      current = parent[current];
    }
    Collections.reverse(path);

    return new PathResult(path, distance[end]);
  }

  public void execute(String path, int start, int end) {
    Graph graph = readGraphFromFile(path);

    PathResult shortestPath = findShortestPath(graph, start, end);

    if (shortestPath.distance == Integer.MAX_VALUE) {
      System.out.println("No hay un camino entre los nodos " + start + " y " +
          end);
    } else {
      System.out.println("La distancia más corta entre los nodos " + start +
          " y " + end + " es: " + shortestPath.distance);
      System.out.println("El camino más corto es: " + shortestPath.path);
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
      graph.addEdgeAux(edge.source, edge.destination, edge.weight);
    }
    return graph;
  }
}
