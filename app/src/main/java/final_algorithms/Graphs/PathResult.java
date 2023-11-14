package final_algorithms.Graphs;

import java.util.List;

public class PathResult {
  public final List<Integer> path;
  public final int distance;

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
