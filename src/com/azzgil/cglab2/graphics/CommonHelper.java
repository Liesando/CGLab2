package com.azzgil.cglab2.graphics;

public class CommonHelper {

    public static double clamp(double value, double left, double right) {
        if (value < left) return left;
        if (value > right) return right;
        return value;
    }
}
