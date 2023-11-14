package final_algorithms.Methods;

import final_algorithms.Graphs.Edge;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Prim {

  class Graph {
    private int V; // Número de nodos en el grafo
    private List<List<Edge>> adjacencyList; // Lista de adyacencia

    public Graph(int V) {
      this.V = V;
      adjacencyList = new ArrayList<>(V);
      for (int i = 0; i < V; i++) {
        adjacencyList.add(new ArrayList<>());
      }
    }

    // Agregar una arista al grafo
    public void addEdge(int u, int v, int weight) {
      Edge edge = new Edge(v, weight);
      Edge reverseEdge = new Edge(u, weight); // Para grafos no dirigidos
      adjacencyList.get(u).add(edge);
      adjacencyList.get(v).add(reverseEdge);
    }

    // Algoritmo de Prim
    public void primMST() {
      boolean[] inMST = new boolean[V]; // Para llevar un registro de los nodos en el MST
      int[] parent = new int[V]; // Para almacenar el padre de cada nodo en el MST
      int[] key = new int[V]; // Para almacenar la clave (peso mínimo) de cada nodo

      Arrays.fill(key, Integer.MAX_VALUE);
      key[0] = 0; // Elegimos un nodo inicial (puede ser cualquiera)

      for (int i = 0; i < V - 1; i++) {
        int u = minKey(key, inMST);
        inMST[u] = true;

        for (Edge edge : adjacencyList.get(u)) {
          int v = edge.destination;
          int weight = edge.weight;
          if (!inMST[v] && weight < key[v]) {
            parent[v] = u;
            key[v] = weight;
          }
        }
      }

      print(parent);
    }

    // Función para encontrar el nodo con la clave mínima
    private int minKey(int[] key, boolean[] inMST) {
      int min = Integer.MAX_VALUE;
      int minIndex = -1;

      for (int v = 0; v < V; v++) {
        if (!inMST[v] && key[v] < min) {
          min = key[v];
          minIndex = v;
        }
      }

      return minIndex;
    }

    // Función para imprimir el MST
    private void print(int[] parent) {
      System.out.println("Aristas del Árbol de Expansión Mínima (MST):");
      for (int i = 1; i < V; i++) {
        System.out.println(parent[i] + " - " + i);
      }
    }

    // Clase para representar las aristas del grafo
    class Edge {
      int destination;
      int weight;

      public Edge(int destination, int weight) {
        this.destination = destination;
        this.weight = weight;
      }
    }
  }

  public void execute(String path) {
    // Crear un objeto Graph leyendo desde el archivo
    Graph graph = readGraphFromFile(path);

    // Encontrar el MST e imprimirlo
    graph.primMST();
  }

  // Función para leer el grafo desde un archivo
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
