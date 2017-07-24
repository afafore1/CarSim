package carsim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Sensor implements Renderable {

    private final Color SENSOR_COLOR = Color.GRAY;

    Vector2D position = new Vector2D(); // Position local to car.
    double angle = 0, maxDistance = 0;

    ArrayList<Obstacle> obstacleList;

    private Transformation parent;
    private LineSegment segment = new LineSegment();

    public Sensor(Transformation parent) {
        this.parent = parent;
    }

    public Sensor(Vector2D position, double angle,
            double maxDistance, Transformation parent) {
        this.position = position.clone();
        this.angle = angle;
        this.parent = parent;
        this.maxDistance = maxDistance;
    }

    public Sensor(double x, double y, double angle,
            double maxDistance, Transformation parent) {
        this.position = new Vector2D(x, y);
        this.angle = angle;
        this.parent = parent;
        this.maxDistance = maxDistance;
    }

    public double getDistance() {
        return segment.getLength();
    }

    @Override
    public void update() {
        segment.v1.moveTo(position);
        segment.v2.moveTo(segment.v1)
                .add(new Vector2D(0, maxDistance).rotate(angle));

        if (parent != null) {
            segment.v1.transform(parent);
            segment.v2.transform(parent);
        }

        if (obstacleList != null) {
            for (Obstacle obstacle : obstacleList) {
                Vector2D collision = new LineSegment(segment.v1, segment.v2)
                        .intersect(obstacle.lineSegment);
                if (collision != null) {
                    segment.v2.moveTo(collision);
                }
            }
        }
    }

    @Override
    public void render(Graphics g, Transformation camTransform) {
        g.setColor(SENSOR_COLOR);
        Point origin = camTransform.transform(segment.v1).toPoint();
        Point end = camTransform.transform(segment.v2).toPoint();
        g.drawLine(origin.x, -origin.y, end.x, -end.y);
    }
}
