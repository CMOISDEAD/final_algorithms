package final_algorithms.Graphs;

public class NodeAux {
  private int node;
  private int distance;
  private int heuristic;

  public NodeAux(int node, int distance, int heuristic) {
    this.node = node;
    this.distance = distance;
    this.heuristic = heuristic;
  }

  public int getNode() {
    return node;
  }

  public int getDistance() {
    return distance;
  }

  public int getHeuristic() {
    return heuristic;
  }
}
