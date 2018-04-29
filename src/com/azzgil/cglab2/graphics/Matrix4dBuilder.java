package com.azzgil.cglab2.graphics;

import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

public class Matrix4dBuilder {
    private Matrix4d matrix;

    public Matrix4dBuilder take(Matrix4d matrix) {
        this.matrix = new Matrix4d(matrix);
        return this;
    }

    public Matrix4dBuilder identity() {
        matrix = Math3DHelper.identityMatrix();
        return this;
    }

    public Matrix4dBuilder rotate(double x, double y, double z) {
        Matrix4d xRotation = new Matrix4d();
        xRotation.rotX(x);
        Matrix4d yRotation = new Matrix4d();
        yRotation.rotY(y);
        Matrix4d zRotation = new Matrix4d();
        zRotation.rotZ(z);

        matrix.mul(xRotation, matrix);
        matrix.mul(yRotation, matrix);
        matrix.mul(zRotation, matrix);

        return this;
    }

    public Matrix4dBuilder translate(double x, double y, double z) {
        Matrix4d translation = new Matrix4d();
        translation.set(new Vector3d(-x, -y, -z));

        matrix.mul(translation, matrix);

        return this;
    }

    public Matrix4d build() {
        return matrix;
    }
}
