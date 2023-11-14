package final_algorithms.Graphs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Graph {
  public int V; // Número de nodos
  public List<EdgeAux>[] adjacencyList; // Lista de adyacencia
  public List<Edge> edges; // Lista de aristas

  public Graph(int V) {
    this.V = V;
    adjacencyList = new ArrayList[V];
    edges = new ArrayList<>();
    for (int i = 0; i < V; i++) {
      adjacencyList[i] = new ArrayList<>();
    }
  }

  // Agregar una arista ponderada al grafo
  public void addEdge(int source, int destination, int weight) {
    EdgeAux edgeAux = new EdgeAux(destination, weight);
    adjacencyList[source].add(edgeAux);
  }

  // Agregar una arista al grafo
  public void addEdgeAux(int source, int destination, int weight) {
    Edge edge = new Edge(source, destination, weight);
    edges.add(edge);
  }

  public void generateRandomGraph(int nodes, int edges) {
    Random random = new Random();
    Set<String> addedEdges = new HashSet<>();

    for (int i = 0; i < edges; i++) {
      int source = random.nextInt(nodes);
      int destination = random.nextInt(nodes);

      // Asegurarse de que source y destination sean diferentes
      while (source == destination ||
          addedEdges.contains(source + "-" + destination)) {
        destination = random.nextInt(nodes);
      }

      int weight = random.nextInt(
          100); // Puedes ajustar el rango de pesos según tus necesidades

      addEdge(source, destination, weight);
      addEdge(destination, source,
          weight); // Añadir la arista en ambas direcciones

      // Agregar la arista al conjunto de aristas agregadas
      addedEdges.add(source + "-" + destination);
      addedEdges.add(destination + "-" + source);
    }
  }

  public void saveGraphToFile(String path) {
    try (FileWriter writer = new FileWriter(path)) {
      for (int i = 0; i < V; i++) {
        for (EdgeAux edge : adjacencyList[i]) {
          writer.write(i + " " + edge.destination + " " + edge.weight + "\n");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void saveRandomGraph(String path, int nodes, int edges) {
    this.generateRandomGraph(nodes, edges);
    this.saveGraphToFile(path);
  }

  public void printGraph() {
    System.out.println("Printing graph:");
    for (Edge edge : edges) {
      System.out.println(edge.source + " - > " + edge.destination + " " +
          edge.weight);
    }
  }
}
