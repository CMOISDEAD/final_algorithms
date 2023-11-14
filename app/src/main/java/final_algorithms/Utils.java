package final_algorithms;

import final_algorithms.Graphs.Node;
import java.io.File;
import java.io.FileNotFoundException;
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
}
