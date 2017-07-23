package carsim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Car implements Renderable{
    
    private Vector2D rearTire;
    private Vector2D frontTire;
    Vector2D size;
    
    double tireDistance;
    double acceleration = 0;
    double maxSpeed;
    double steeringTarget;
    double maxSteerAngle;
    double brakingDeceleration;
    double friction;
    boolean brake;
    
    double speed = 0;
    double steeringAngle = 0;
    double steeringSpeed; // Radians per frame.
    Transformation carTransform = new Transformation();
    
    private Vector2D[] corners = new Vector2D[4];
    private Color CAR_COLOR = Color.BLACK;
    private Color TIRE_COLOR = Color.BLUE;
    private Color STEER_COLOR = Color.PINK;
    private double STEERING_VECTOR_LENGTH = 20; // 20 pixels
    
    public Car(double maxSpeed, double maxSteerAngle, double steeringSpeed,
            Vector2D size, double tireDistance, double brakingDeceleration,
            double friction) {
        this.maxSpeed = maxSpeed;
        this.maxSteerAngle = maxSteerAngle;
        this.steeringSpeed = steeringSpeed;
        this.tireDistance = tireDistance;
        this.size = size.clone();
        this.brakingDeceleration = brakingDeceleration;
        this.friction = friction;

        frontTire = new Vector2D();
        rearTire = new Vector2D(0, -tireDistance);
        
        for (int i = 0; i < corners.length; i++) {
            corners[i] = new Vector2D();
        }
        recalculateVectors();
    }
    
    public void setLocation(Vector2D location) {
        frontTire.moveTo(location).
                add(Vector2D.multiply(tireDistance / 2, carTransform.yVector));
        rearTire.moveTo(location).
                add(Vector2D.multiply(-tireDistance / 2, carTransform.yVector));
        recalculateVectors();
    }
    
    public void rotate(double theta) {
        carTransform.yVector.rotate(theta);
        setLocation(carTransform.origin);
        // Since setLocation depends on the forward vector, that line 
        // effectively keep the car in the same position and rotate it.
    }
    
    public void setRotation(double theta) {
        carTransform.yVector.x = 1;
        carTransform.yVector.y = 0;
        carTransform.yVector.rotate(theta);
        setLocation(carTransform.origin); // Same idea.
    }
    
    public void update() {
        steeringAngle = linearApproach(steeringAngle, steeringTarget,
                steeringSpeed);
        
        speed = clamp(speed + acceleration, -maxSpeed, maxSpeed);
        speed = linearApproach(speed, 0, friction);
        if (brake) { // If braking, previous acceleration is ignored.
            speed = linearApproach(speed, 0, brakingDeceleration);
        }
        
        double carAngle = Math.atan2(carTransform.yVector.y, carTransform.yVector.x);
        Vector2D movement = Vector2D.rotate(new Vector2D(speed, 0),
                carAngle + steeringAngle);
        frontTire.add(movement);
        // Temporarily calculate this to make rear tire follow.
        carTransform.yVector.moveTo(frontTire).subtract(rearTire).normalize();
        rearTire.moveTo(frontTire)
                .subtract(Vector2D.multiply(tireDistance, carTransform.yVector));
        recalculateVectors();
    }
    
    public void render(Graphics g, Transformation camTransform) {
        // Java's y axis points downward but we use it as if it points up.
        // All y's will be negated.

        // Draw car.
        g.setColor(CAR_COLOR);
        for (int i = 0; i <= 3; i++) {
            Point corner1 = camTransform.transform(corners[i]).toPoint();
            Point corner2 = camTransform.transform(corners[(i + 1) % 4]).toPoint();
            g.drawLine(corner1.x, -corner1.y, corner2.x, -corner2.y);
        }

        // Draw tire line.
        g.setColor(TIRE_COLOR);
        Point frontPoint = camTransform.transform(frontTire).toPoint();
        Point rearPoint = camTransform.transform(rearTire).toPoint();
        g.drawLine(frontPoint.x, -frontPoint.y, rearPoint.x, -rearPoint.y);
        
        // Draw steering vector.
        g.setColor(STEER_COLOR);
        Point steer = camTransform.transform(
                Vector2D.add(frontTire,
                        Vector2D.multiply(STEERING_VECTOR_LENGTH,
                                Vector2D.rotate(carTransform.yVector, steeringAngle))))
                .toPoint();
        g.drawLine(frontPoint.x, -frontPoint.y, steer.x, -steer.y);
    }
    
    // Move number to target linearly. Speed must always be possitive.
    private double linearApproach(double number, double target, double speed) {
        double absDelta = Math.abs(target - number);
        if (absDelta <= speed) {
            return target;
        } else {
            // (target - number) / absDelta is -1 or 1 depending on the direction.
            // Laziness ftw!
            return number + speed * (target - number) / absDelta;
        }
    }
    
    private void recalculateVectors() {
        carTransform.yVector.moveTo(frontTire).subtract(rearTire).normalize();
        carTransform.xVector.moveTo(carTransform.yVector).transpose();
        carTransform.origin.moveTo(rearTire).add(Vector2D.multiply(tireDistance / 2,
                carTransform.yVector));
        
        Vector2D halfForward = Vector2D.multiply(size.y / 2, carTransform.yVector);
        Vector2D halfRight = Vector2D.multiply(size.x / 2, carTransform.xVector);
        //System.out.println(carTransform.yVector + ", " + carTransform.xVector + ", " + carTransform.origin);
        for (int i = 0; i < 4; i++) {
            corners[i].moveTo(carTransform.origin);
            // corner 0 and 1 are at the front.
            // Just some bit shifting tricks cuz I'm lazy.
            corners[i].add((i >> 1) == 0 ? halfForward : halfForward.negated());
            corners[i].add(((i & 1) ^ (i >> 1)) == 0
                    ? halfRight.negated() : halfRight);
            /* Result is
                0   1
                3   2
            */
        }
    }
    
    private double clamp(double number, double min, double max) {
        return Math.max(min, Math.min(max, number));
    }
}
