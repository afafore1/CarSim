package carsim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Obstacle implements Renderable{
    private final Color OBSTACLE_COLOR = Color.BLACK;
    LineSegment lineSegment;

    public Obstacle(double x1, double y1, double x2, double y2) {
        lineSegment = new LineSegment(x1, y1, x2, y2);
    }
    
    public Obstacle(Vector2D v1, Vector2D v2) {
        lineSegment = new LineSegment(v1, v2);
    }

    @Override
    public void update() {}

    @Override
    public void render(Graphics g, Transformation camTransform) {
        g.setColor(OBSTACLE_COLOR);
        Point p1 = camTransform.transform(lineSegment.v1).toPoint();
        Point p2 = camTransform.transform(lineSegment.v2).toPoint();
        g.drawLine(p1.x, -p1.y, p2.x, -p2.y);
    }
}
