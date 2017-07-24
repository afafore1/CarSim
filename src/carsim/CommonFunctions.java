package carsim;

public class CommonFunctions {
    public static double clamp(double number, double min, double max) {
        return Math.max(min, Math.min(max, number));
    }
}
