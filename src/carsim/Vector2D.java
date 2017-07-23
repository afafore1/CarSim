package carsim;

import java.awt.Point;

public class Vector2D {
    double x, y;
    
    public Vector2D() {
        x = y = 0;
    }
    
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D normalize() {
        double magnitude = magnitude();
        x /= magnitude;
        y /= magnitude;
        return this;
    }
    
    public Vector2D add(Vector2D other) {
        x += other.x;
        y += other.y;
        return this;
    }
    
    public Vector2D subtract(Vector2D other) {
        x -= other.x;
        y -= other.y;
        return this;
    }
    
    public Vector2D multiply(double alpha) {
        x *= alpha;
        y *= alpha;
        return this;
    }
    
    public Vector2D negated() {
        return new Vector2D(-x, -y);
    }
    
    public Vector2D clone() {
        return new Vector2D(x, y);
    }
    
    public Vector2D rotate(double theta) {
        double temp = x;
        x = x * Math.cos(theta) - y * Math.sin(theta);
        y = temp * Math.sin(theta) + y * Math.cos(theta);
        return this;
    }
    
    public Point toPoint() {
        return new Point((int) x, (int) y);
    }
    
    public Vector2D moveTo(Vector2D other) {
        x = other.x;
        y = other.y;
        return this;
    }
    
    public Vector2D transpose() { // Technically rotate 90 degrees to the right.
        double temp = x;
        x = y;
        y = -temp;
        return this;
    }
    
    public Vector2D transform(Transformation t) {
        double tempX = x, tempY = y;
        return moveTo(t.origin).add(Vector2D.multiply(tempX, t.xVector)).add(
                        Vector2D.multiply(tempY, t.yVector));
    }
    
    static Vector2D cross(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x * v2.y, v1.y * v2.x);
    }
    
    static double dot(Vector2D v1, Vector2D v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }
    
    static Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }
    
    static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }
    
    static Vector2D multiply(double a, Vector2D v) {
        return new Vector2D(a * v.x, a * v.y);
    }
    
    static Vector2D lerp(Vector2D v1, Vector2D v2, double alpha) {
        return new Vector2D(lerp(v1.x, v2.x, alpha), lerp(v1.y, v2.y, alpha));
    }
    
    static Vector2D rotate(Vector2D v, double theta) {
        return new Vector2D(v.x * Math.cos(theta) - v.y * Math.sin(theta),
                v.x * Math.sin(theta) + v.y * Math.cos(theta));
    }
    
    static Vector2D normalized(Vector2D v) {
        double magnitude = v.magnitude();
        return Vector2D.multiply(1 / magnitude, v);
    }
    
    static double distance(Vector2D v1, Vector2D v2) {
        return Math.sqrt((v1.x - v2.x) * (v1.x - v2.x)
                + (v1.y - v2.y) * (v1.y - v2.y));
    }
    
    private static double lerp(double n1, double n2, double a) {
        return (1 - a) * n1 + a * n2;
    }
    
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
