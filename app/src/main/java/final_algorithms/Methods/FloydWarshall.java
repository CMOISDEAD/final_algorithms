package final_algorithms.Methods;

import final_algorithms.Graphs.PathResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FloydWarshall {
  public long execute(String path, int start, int end) {
    int[][] graph = readGraphFromFile(path);

    long startTime = System.currentTimeMillis();
    PathResult result = floydWarshall(graph, start, end);
    long endTime = System.currentTimeMillis();

    long duration = endTime - startTime;

    // if (result.distance == Integer.MAX_VALUE) {
    // System.out.println("No se encontró un camino desde " + start + " a " +
    // end);
    // } else {
    // System.out.println("La distancia mínima desde " + start + " a " + end +
    // " es " + result.distance);
    // System.out.println("El camino más corto es: " + result.path);
    // }

    System.out.println("FloydWarshall: " + (duration) + "ms");
    return duration;
  }

  public int[][] readGraphFromFile(String filePath) {
    int maxNode = 0;

    try {
      File file = new File(filePath);
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
      File file = new File(filePath);
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

  public PathResult floydWarshall(int[][] graph, int startNode, int endNode) {
    int V = graph.length; // número de nodos
    int[][] dist = new int[V][V];
    int[][] next = new int[V][V];
    // int[][] dist = new int[V][V]; // matriz para almacenar distancias minimas
    // entre los pares de nodos

    /*
     * // Iniciar la matriz de distancias con el grafo original
     * for (int i = 0; i < V; i++) {
     * for (int j = 0; j < V; j++) {
     * dist[i][j] = graph[i][j];
     * }
     * }
     */

    for (int i = 0; i < V; i++) {
      for (int j = 0; j < V; j++) {
        dist[i][j] = graph[i][j];
        if (i != j && graph[i][j] != Integer.MAX_VALUE) {
          next[i][j] = j;
        } else {
          next[i][j] = -1;
        }
      }
    }

    for (int k = 0; k < V; k++) {
      for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
          if (dist[i][k] != Integer.MAX_VALUE &&
              dist[k][j] != Integer.MAX_VALUE) {
            if (dist[i][k] + dist[k][j] < dist[i][j]) {
              dist[i][j] = dist[i][k] + dist[k][j];
              next[i][j] = next[i][k];
            }
          }
        }
      }
    }

    if (dist[startNode][endNode] == Integer.MAX_VALUE) {
      return new PathResult(new ArrayList<>(), Integer.MAX_VALUE);
    } else {
      List<Integer> path = reconstructPath(next, startNode, endNode);
      int distance = dist[startNode][endNode];
      return new PathResult(path, distance);
    }
  }

  private List<Integer> reconstructPath(int[][] next, int start, int end) {
    List<Integer> path = new ArrayList<>();
    if (next[start][end] == -1) {
      path.add(start);
      path.add(end);
    } else {
      path.add(start);
      while (start != end) {
        start = next[start][end];
        path.add(start);
      }
    }
    return path;
  }
}
