package carsim;

public class Transformation {
    Vector2D xVector;
    Vector2D yVector;
    Vector2D origin;
    
    public Transformation() {reset();}
    
    public Transformation(double x, double y) {
        origin.x = x;
        origin.y = y;
        resetRotation();
    }
    
    public void rotate(double theta) {
        xVector.rotate(theta);
        yVector.rotate(theta);
    }
    
    public void shift(double x, double y) {
        origin.x += x;
        origin.y += y;
    }
    
    public void setOrigin(double x, double y) {
        origin.x = x;
        origin.y = y;
    }
    
    public void setScale(double scale) {
        xVector.normalize().multiply(scale);
        yVector.normalize().multiply(scale);
    }
    
    public void resetOrigin() {
        origin = new Vector2D();
    }
    
    public void resetRotation() {
        double scale = xVector.magnitude();
        xVector = new Vector2D(scale, 0);
        yVector = new Vector2D(0, scale);
    }
    
    public void resetScale() {
        xVector.normalize();
        yVector.normalize();
    }
    
    public void reset() {
        xVector = new Vector2D(1, 0);
        yVector = new Vector2D(0, 1);
        origin = new Vector2D();
    }

    public Vector2D transform(Vector2D v) {
        // result = origin + v.x * xVector + v.y * yVector.
        return Vector2D.add(origin,
                Vector2D.add(
                        Vector2D.multiply(v.x, xVector),
                        Vector2D.multiply(v.y, yVector)));
    }
    
}
