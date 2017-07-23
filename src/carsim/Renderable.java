package carsim;

import java.awt.Graphics;

public interface Renderable {
    public void update();
    public void render(Graphics g, Transformation t);
}
