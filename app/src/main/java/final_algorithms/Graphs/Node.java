package final_algorithms.Graphs;

public class Node {
  private final int node;
  private final int distance;
  private final int weight;

  public Node(int node, int distance, int weight) {
    this.node = node;
    this.distance = distance;
    this.weight = weight;
  }

  public int getNode() {
    return node;
  }

  public int getDistance() {
    return distance;
  }

  public int getWeight() {
    return weight;
  }
}
