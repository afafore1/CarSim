package carsim;

public class LineSegment {
    Vector2D v1, v2;
    private Line line;
    
    public LineSegment() {
        v1 = new Vector2D();
        v2 = new Vector2D();
    }
    
    public LineSegment(double x1, double y1, double x2, double y2) {
        v1 = new Vector2D(x1, y1);
        v2 = new Vector2D(x2, y2);
        line = new Line(v1, v2);
    }
    
    public LineSegment(Vector2D v1, Vector2D v2) {
        this.v1 = v1.clone();
        this.v2 = v2.clone();
        line = new Line(v1, v2);
    }
    
    public Vector2D intersect(LineSegment other) {
        line.construct(v1, v2);
        other.line.construct(other.v1, other.v2);
        Vector2D intersection = line.intersect(other.line);
        if (intersection != null) {
            if (Vector2D.dot(Vector2D.subtract(intersection, v1), 
                    Vector2D.subtract(intersection, v2)) <= 0
                    && Vector2D.dot(Vector2D.subtract(intersection, other.v1), 
                    Vector2D.subtract(intersection, other.v2)) <= 0) {
                // If intersection belongs inside both the segments.
                return intersection;
            }
        }
        return null;
    }
    
    public double getLength() {
        return Vector2D.distance(v1, v2);
    }
}
