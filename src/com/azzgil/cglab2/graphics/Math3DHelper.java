package com.azzgil.cglab2.graphics;

import javax.vecmath.Matrix4d;

public class Math3DHelper {

    public static Matrix4d identityMatrix() {
        return new Matrix4d(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);
    }

    public static Matrix4d projectionMatrix(double width, double height, double zNear, double zFar) {
        return new Matrix4d(
//                focalDistance, 0, 0, 0,
//                0, focalDistance, 0, 0,
//                0, 0, 1, 0,
//                0, 0, 1, 0);
                width, 0, 0, 0,
                0, height, 0, 0,
                0, 0, zFar / (zFar - zNear), -zFar * zNear / (zFar - zNear),
                0, 0, 1, 0);
    }
}
