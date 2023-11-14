package ui.components;

import final_algorithms.Graphs.Graph;
import final_algorithms.Methods.BellmanFord;
import final_algorithms.Methods.Dijkstra;
import final_algorithms.Methods.FloydWarshall;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame {
  private JTextField graphSize;
  private Chart leftPanel;

  public Window() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setSize(500, 400);

    setLayout(new BorderLayout());

    List<Bar> bars = new ArrayList<>();
    bars.add(new Bar("Dijkstra", 100, Color.blue));
    bars.add(new Bar("BellmanFord", 100, Color.red));
    bars.add(new Bar("FloydWarshall", 100, Color.green));

    leftPanel = new Chart(bars);

    JPanel rigthPanel = new JPanel();
    rigthPanel.setLayout(new BoxLayout(rigthPanel, BoxLayout.Y_AXIS));

    JLabel graphSizeLabel = new JLabel("Random Graph Size:");
    rigthPanel.add(graphSizeLabel);

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

    graphSize = new JTextField();
    graphSize.setPreferredSize(new Dimension(100, 50));
    inputPanel.add(graphSize);

    JButton generateGraph = new JButton("Execute");
    generateGraph.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        generateBars();
      }
    });
    inputPanel.add(generateGraph);

    rigthPanel.add(inputPanel);

    add(leftPanel, BorderLayout.CENTER);
    add(rigthPanel, BorderLayout.EAST);
  }

  private void generateBars() {
    String sizeString = graphSize.getText();

    if (!sizeString.isEmpty()) {
      int size = Integer.parseInt(sizeString);
      Graph graph = new Graph(size);

      System.out.println("Generating random graph...");
      graph.saveRandomGraph("graph.txt", size, size);
      System.out.println("Random graph generated!");

      Dijkstra dijkstra = new Dijkstra();
      long dijkstraTime = dijkstra.execute("graph.txt", 0, size - 1) * 2;

      BellmanFord bellmanFord = new BellmanFord();
      long bellmanFordTime = bellmanFord.execute("graph.txt", 0, size - 1) * 2;

      FloydWarshall floydWarshall = new FloydWarshall();
      long floydWarshallTime = floydWarshall.execute("graph.txt", 0, size - 1) * 2;

      List<Bar> bars = new ArrayList<>();
      bars.add(new Bar("Dijkstra", dijkstraTime, Color.blue));
      bars.add(new Bar("BellmanFord", bellmanFordTime, Color.red));
      bars.add(new Bar("FloydWarshall", floydWarshallTime, Color.green));

      leftPanel.setBars(bars);
    } else {
      JOptionPane.showMessageDialog(this, "Please enter a number", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }
}
