package final_algorithms;

import final_algorithms.Graphs.Node;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Utils {

  public Map<Integer, List<Node>> readGraphFromFile(String filePath) {
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

  public void writeTime(String name, long time) {
    // Definimos el nombre del archivo
    String path = "tiempos.txt";

    try {
      // Creamos una instancia de FileWriter con el modo de agregar al final
      // (true)
      FileWriter fileWriter = new FileWriter(path, true);

      // Creamos una instancia de BufferedWriter para escribir en el archivo
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

      // Escribimos el nombre del algoritmo y el tiempo en el archivo
      bufferedWriter.write(name + ": " + time);
      // Agregamos un salto de línea para la siguiente entrada
      bufferedWriter.newLine();

      // Cerramos BufferedWriter
      bufferedWriter.close();

      System.out.println("Se ha escrito el tiempo en el archivo.");
    } catch (IOException e) {
      // Manejamos posibles excepciones de IO
      e.printStackTrace();
    }
  }
}
