package ui.components;

import java.awt.Color;

public class Bar {
  private String name;
  private long height;
  private Color color;

  public Bar(String name, long height, Color color) {
    this.name = name;
    this.height = height;
    this.color = color;
  }

  public String getName() {
    return name;
  }

  public long getHeight() {
    return height;
  }

  public Color getColor() {
    return color;
  }
}
