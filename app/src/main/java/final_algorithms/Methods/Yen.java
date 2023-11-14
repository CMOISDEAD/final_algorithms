package final_algorithms.Methods;

import final_algorithms.Graphs.Edge;
import final_algorithms.Graphs.EdgeAux;
import final_algorithms.Graphs.Graph;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Yen {

  public List<List<Integer>> yenKShortestPaths(Graph graph, int source,
      int destination, int k) {
    List<List<Integer>> result = new ArrayList<>();

    for (int i = 0; i < k; i++) {
      List<Integer> shortestPath = dijkstra(graph, source, destination);

      if (shortestPath != null) {
        result.add(shortestPath);

        removeEdgesFromGraph(graph, shortestPath);

        if (i < k - 1) {
          graph = resetGraph(graph, result.get(i));
        }
      } else {
        break; // No hay más caminos
      }
    }

    return result;
  }

  private List<Integer> dijkstra(Graph graph, int source, int destination) {
    int V = graph.V;
    int[] distance = new int[V];
    int[] previous = new int[V];
    Arrays.fill(distance, Integer.MAX_VALUE);
    Arrays.fill(previous, -1);
    distance[source] = 0;

    PriorityQueue<Integer> minHeap = new PriorityQueue<>(V, Comparator.comparingInt(a -> distance[a]));
    minHeap.add(source);

    while (!minHeap.isEmpty()) {
      int u = minHeap.poll();
      if (u == destination) {
        return reconstructPath(previous, destination);
      }

      for (EdgeAux edge : graph.adjacencyList[u]) {
        int v = edge.destination;
        int weight = edge.weight;
        if (distance[u] + weight < distance[v]) {
          distance[v] = distance[u] + weight;
          previous[v] = u;
          minHeap.add(v);
        }
      }
    }
    return null;
  }

  private List<Integer> reconstructPath(int[] previous, int destination) {
    List<Integer> path = new ArrayList<>();
    int current = destination;
    while (current != -1) {
      path.add(current);
      current = previous[current];
    }
    Collections.reverse(path);
    return path;
  }

  private void removeEdgesFromGraph(Graph graph, List<Integer> path) {
    for (int i = 0; i < path.size() - 1; i++) {
      int u = path.get(i);
      int v = path.get(i + 1);
      graph.adjacencyList[u].removeIf(edge -> edge.destination == v);
    }
  }

  private Graph resetGraph(Graph graph, List<Integer> path) {
    Graph newGraph = new Graph(graph.V);

    for (int i = 0; i < graph.V; i++) {
      for (EdgeAux edge : graph.adjacencyList[i]) {
        int destination = edge.destination;
        int weight = edge.weight;
        newGraph.addEdge(i, destination, weight);
      }
    }

    for (int i = 0; i < path.size() - 1; i++) {
      int u = path.get(i);
      int v = path.get(i + 1);
      int weight = getEdgeWeight(graph, u, v);
      newGraph.addEdge(u, v, weight);
    }

    return newGraph;
  }

  private int getEdgeWeight(Graph graph, int source, int destination) {
    for (EdgeAux edge : graph.adjacencyList[source]) {
      if (edge.destination == destination) {
        return edge.weight;
      }
    }
    return Integer.MAX_VALUE;
  }

  public void execute(String path, int source, int destination, int k) {
    // Leer el grafo desde el archivo
    Graph graph = readGraphFromFile(path);

    // Encontrar los k caminos más cortos
    List<List<Integer>> kshortestPaths = yenKShortestPaths(graph, source, destination, k);

    // Imprimir los caminos más cortos
    for (int i = 0; i < kshortestPaths.size(); i++) {
      List<Integer> shortestpath = kshortestPaths.get(i);
      System.out.println("Camino " + (i + 1) + ": " + shortestpath);
    }
  }

  // Función para leer el grafo desde un archivo
  public Graph readGraphFromFile(String path) {
    int maxNode = 0;
    List<Edge> edges = new ArrayList<>();

    try {
      File file = new File(path);
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
