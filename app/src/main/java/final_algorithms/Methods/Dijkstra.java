package final_algorithms.Methods;

import final_algorithms.Graphs.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Dijkstra {

  public class PathResult {
    private final List<Integer> path;
    private final int distance;

    public PathResult(List<Integer> path, int distance) {
      this.path = path;
      this.distance = distance;
    }

    public List<Integer> getPath() {
      return path;
    }

    public int getDistance() {
      return distance;
    }
  }

  public PathResult shortestPath(Map<Integer, List<Node>> graph, int startNode,
      int endNode) {
    Map<Integer, Integer> distances = new HashMap<>(); // Se crea el mapa que almacenará las distancias más
                                                       // cortas desde el inicio hasta los demás nodos
    Map<Integer, Integer> previuosNodes = new HashMap<>();

    // Se inicializa todas las distancias con un valor de MAX_VALUE (inifinito)
    for (Integer node : graph.keySet()) {
      distances.put(node, Integer.MAX_VALUE);
      previuosNodes.put(node, null);
    }

    distances.put(
        startNode,
        0); // La distancia del nodo de inicio a si mismo se inicia en 0
    Set<Integer> unvisitedNodes = new HashSet<>(
        graph.keySet()); // Se crea el conjunto de nodos no visitados, y se
                         // agregan todos los nodos del grafo

    while (!unvisitedNodes.isEmpty()) { // mientras queden nodos no visitandos
      Integer currentNode = getClosestNode(
          distances, unvisitedNodes); // Obtener el nodo no visitado con
      // distancia más corta
      if (currentNode == endNode) {
        break; // Salir del bucle si se alcanza el nodo destino
      }

      unvisitedNodes.remove(currentNode); // Remueve el nodo actual de los no
                                          // visitados (ya lo visitó)

      for (Node neighbor : graph.get(currentNode)) { // Se itera sobre los nodos
                                                     // vecinos del nodo actual
        // Calcular una distancia potencial desde el nodo de inicial al nodo
        // vecino mediante el nodo actual
        int potentialDistance = distances.get(currentNode) + neighbor.getDistance();
        // Si la distancia potencial es mejor que la distancia actual,
        // actualizar la distancia
        if (potentialDistance < distances.get(neighbor.getNode())) {
          distances.put(neighbor.getNode(), potentialDistance);
          previuosNodes.put(neighbor.getNode(), currentNode);
        }
      }
    }

    List<Integer> path = new ArrayList<>();
    Integer current = endNode;
    while (current != null) {
      path.add(current);
      current = previuosNodes.get(current);
    }
    Collections.reverse(path);

    int distanceToDestination = distances.get(endNode);

    return new PathResult(path, distanceToDestination);
  }

  private Integer getClosestNode(Map<Integer, Integer> distances,
      Set<Integer> unvisitedNodes) {
    Integer closestNode = null;
    int closestDistance = Integer.MAX_VALUE;

    for (Entry<Integer, Integer> entry : distances.entrySet()) {
      Integer node = entry.getKey(); // el nodo actual
      Integer distance = entry.getValue(); // la distacia del nodo actual

      // Verificamos si el nodo actual es no visitado y si su distancia es más
      // corta que la distancia más corta actual
      if (unvisitedNodes.contains(node) && distance < closestDistance) {
        // Si es así, actualizamos "closestNode" con el nodo actual y
        // "closestDistance" con la distancia actual
        closestNode = node;
        closestDistance = distance;
      }
    }

    return closestNode;
  }

  private Map<Integer, List<Node>> readGraphFromFile(String filePath) {
    Map<Integer, List<Node>> graph = new HashMap<>();

    try {
      File file = new File(filePath);
      Scanner scanner = new Scanner(file);

      while (scanner.hasNext()) {
        int source = scanner.nextInt();
        int destination = scanner.nextInt();
        int weight = scanner.nextInt();

        graph.computeIfAbsent(source, k -> new ArrayList<>())
            .add(new Node(destination, weight, 0));
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return graph;
  }

  public long execute(String path, int startNode, int endNode) {
    Map<Integer, List<Node>> graph = readGraphFromFile(path);

    long startTime = System.currentTimeMillis();
    PathResult result = shortestPath(graph, startNode, endNode);
    long endTime = System.currentTimeMillis();

    long duration = (endTime - startTime);

    // if (result.getDistance() == Integer.MAX_VALUE) {
    // System.out.println("No se encontró un camino desde " + startNode + " a
    // " +
    // endNode);
    // } else {
    // System.out.println("El camino desde " + startNode + " a " + endNode +
    // " es " + result.getPath());
    // System.out.println("La distancia desde " + startNode + " a " + endNode
    // +
    // " es " + result.getDistance());
    // }

    System.out.println("Dijkstra: " + duration + " ms");
    return duration;
  }
}
