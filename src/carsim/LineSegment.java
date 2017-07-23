package carsim;

public class LineSegment {
    Vector2D v1, v2;
    
    public LineSegment(double x1, double y1, double x2, double y2) {
        v1 = new Vector2D(x1, y1);
        v2 = new Vector2D(x2, y2);
    }
    
    public LineSegment(Vector2D v1, Vector2D v2) {
        
    }
}
