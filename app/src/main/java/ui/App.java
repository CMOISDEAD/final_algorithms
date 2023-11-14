package ui;

import javax.swing.SwingUtilities;
import ui.components.Window;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      Window ventana = new Window();
      ventana.setVisible(true);
    });
  }
}
