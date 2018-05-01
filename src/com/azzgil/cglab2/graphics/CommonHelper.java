package com.azzgil.cglab2.graphics;

import javafx.scene.paint.Color;

import javax.vecmath.Tuple3d;
import javax.vecmath.Tuple4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

public class CommonHelper {

    public static double clamp(double value, double left, double right) {
        if (value < left) return left;
        if (value > right) return right;
        return value;
    }

    public static Vector3d vector3d(Tuple4d v) {
        return new Vector3d(v.x, v.y, v.z);
    }

    public static Vector4d vector4d(Tuple3d v) {
        return new Vector4d(v.x, v.y, v.z, 1);
    }

    public static Vector4d makeVectorNormalized(Tuple4d start, Tuple4d end) {
        Vector3d t = new Vector3d(end.x - start.x, end.y - start.y, end.z - start.z);
        t.normalize();
        Vector4d result = new Vector4d(t.x, t.y, t.z, 1);
        return result;
    }

    public static Vector4d crossNormalized(Tuple4d one, Tuple4d second) {
        Vector3d _cross = new Vector3d();
        _cross.cross(CommonHelper.vector3d(one), CommonHelper.vector3d(second));
        _cross.normalize();
        return CommonHelper.vector4d(_cross);
    }

    public static double dot(Vector4d v1, Vector4d v2) {
        return vector3d(v1).dot(vector3d(v2));
    }

    public static Color mul(Color c1, Color c2) {
        return Color.rgb((int) (c1.getRed() * c2.getRed() * 255),
                (int) (c1.getGreen() * c2.getGreen() * 255),
                (int) (c1.getBlue() * c2.getBlue() * 255));
    }

    public static Color mul(Color c1, double factor) {
        factor = Math.max(0, factor);
        return Color.rgb((int) (c1.getRed() * factor * 255),
                (int) (c1.getGreen() * factor * 255),
                (int) (c1.getBlue() * factor * 255));
    }
}
