package final_algorithms.Methods;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BellmanFord {
  List<Edge> edges = new ArrayList<>();

  int[] distance;
  int[] predecessor;

  public class Edge {
    private final int source;
    private final int destination;
    private final int weight;

    public Edge(int source, int destination, int weight) {
      this.source = source;
      this.destination = destination;
      this.weight = weight;
    }
  }

  public long execute(String path, int source, int destination) {
    int vertices = readGraphFromFile(path);

    long startTime = System.currentTimeMillis();
    initializeArrays(vertices);
    bellmanFord(vertices, source, destination);
    printShortestPaths(source, destination);
    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;

    System.out.println("BellmanFord: " + (duration) + "ms");
    return duration;
  }

  public int readGraphFromFile(String filePath) {
    int maxVertex = 0;

    try {
      File file = new File(filePath);
      Scanner scanner = new Scanner(file);

      while (scanner.hasNext()) {
        int source = scanner.nextInt();
        int destination = scanner.nextInt();
        int weight = scanner.nextInt();

        edges.add(new Edge(source, destination, weight));

        maxVertex = Math.max(maxVertex, Math.max(source, destination));
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return maxVertex +
        1; // Devuelve la cantidad de vértices (el máximo encontrado + 1)
  }

  // Se inicia arreglos donde se encuentran las distancia y los predecesores
  public void initializeArrays(int vertices) {
    distance = new int[vertices]; // Almacena las distancias más cortas desde el
                                  // nodo fuente a otro nodos
    predecessor = new int[vertices]; // Este arreglo se utilizara para almacenar los
                                     // predecesores en el camino más corto desde el nodo
                                     // fuente a otros nodos (para la ruta)

    Arrays.fill(
        distance,
        Integer.MAX_VALUE); // Se llena distances con el valor de inifinito
    Arrays.fill(predecessor,
        -1); // Se llena el precessor con -1, indicando que no se conoce
             // ningún predecesor para ningún nodo.

    distance[0] = 0; // La distancia al nodo actual es 0
  }

  public void bellmanFord(int vertices, int source, int destination) {
    for (int i = 0; i < vertices - 1; i++) {

      if (i == destination) {
        break; // Se detiene la iteración si se ha encontrado el cmaino al nodo
               // de destino o se detecta un ciclo negativo
      }

      if (i == vertices - 1) {
        System.out.println(
            "No se encontró un camino al nodo de destino"); // Si llegamos al
                                                            // final de las
                                                            // interaciones sin
                                                            // enontrar el nodo
                                                            // de destino, no
                                                            // hay camino
        return;
      }

      for (Edge edge : edges) { // Recorrer todas las aristas en la lista de
                                // aristas del grafo
        int u = edge.source; // nodo origen de la arista
        int v = edge.destination; // nodo destino de la arista
        int weight = edge.weight; // peso de la ariesta

        if (distance[u] != Integer.MAX_VALUE &&
            distance[u] + weight < distance[v]) { // Se comprueba si se
                                                  // puede mejorar la
                                                  // distancia a través de
                                                  // la arista acutal
          distance[v] = distance[u] + weight; // Si se encuentra una distancia más corta,
                                              // actualizamos la distancia al nodo v.
          predecessor[v] = u; // Se actualiza el predecesor de v (para hacer la ruta)
        }
      }
    }

    // Se debe verificar si hay ciclos con peso negativo
    for (Edge edge : edges) {
      int u = edge.source;
      int v = edge.destination;
      int weight = edge.weight;
      if (distance[u] != Integer.MAX_VALUE &&
          distance[u] + weight < distance[v]) { // Comprobamos si hay una
                                                // distancia más corta a
                                                // través del ciclo de peso
                                                // negativo
        System.out.println("El grado contiene un ciclo de peso negativo");
        return;
      }
    }
  }

  public void printShortestPaths(int source, int destination) {
    // System.out.print("Distancia desde " + source + " a " + destination + " :
    // " + distance[destination] + ", Ruta: ");
    printPath(destination);
    System.out.println();
  }

  // Se imprime entonces el nodo fuente al nodo fuente al nodo objetivo
  public void printPath(int currentVertex) {
    if (currentVertex == -1) // Se verifica si el nodo actual es -1, lo que
                             // significa que no hay ruta
      return;
    printPath(
        predecessor[currentVertex]); // Se llama de forma recursiva la función
                                     // con el predesor del nodo actual
    System.out.print(currentVertex + " ");
  }
}
