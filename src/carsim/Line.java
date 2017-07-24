package carsim;

public class Line {

    double a, b;
    boolean hasA;

    public Line(Vector2D p1, Vector2D p2) {
        construct(p1, p2);
    }

    public void construct(Vector2D p1, Vector2D p2) {
        if (p2.x == p1.x) {
            hasA = false;
            a = p1.x;
        } else {
            hasA = true;
            a = (p2.y - p1.y) / (p2.x - p1.x);
            b = p1.y - a * (p1.x);
        }
    }

    public Vector2D intersect(Line other) {
        if (!hasA && !other.hasA
                || a == 0 && other.a == 0) { // Parallel
            return null;
        }
        Vector2D result = new Vector2D();

        if (hasA && !other.hasA) {
            result.x = other.a;
            result.y = a * result.x + b;
        } else if (!hasA && other.hasA) {
            result.x = a;
            result.y = other.a * result.x + other.b;
        } else {
            result.x = (other.b - b) / (a - other.a);
            result.y = a * result.x + b;
        }
        return result;
    }
}
