package ui.components;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class Chart extends JPanel {
  private List<Bar> bars;

  public Chart(List<Bar> bars) {
    this.bars = bars;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int barWidth = 50;
    int spacing = 100;
    int x = spacing;

    for (Bar bar : bars) {
      g2d.setColor(bar.getColor());
      long barHeight = bar.getHeight();
      int y = (int) (getHeight() - barHeight);

      g2d.fillRect(x, y, barWidth, (int) barHeight);

      g2d.setColor(Color.black);
      g2d.drawString(bar.getName(),
          x + barWidth / 2 -
              g.getFontMetrics().stringWidth(bar.getName()) / 2,
          getHeight() - 5);

      x += barWidth + spacing;
    }
  }

  public void setBars(List<Bar> bars) {
    this.bars = bars;
    repaint();
  }
}
