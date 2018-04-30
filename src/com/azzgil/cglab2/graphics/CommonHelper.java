package com.azzgil.cglab2.graphics;

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
        Vector4d result = new Vector4d(start.x - end.x, start.y - end.y, start.z - end.z, 1);
        result.normalize();
        return result;
    }

    public static Vector4d crossNormalized(Tuple4d one, Tuple4d second) {
        Vector3d _cross = new Vector3d();
        _cross.cross(CommonHelper.vector3d(one), CommonHelper.vector3d(second));
        _cross.normalize();
        return CommonHelper.vector4d(_cross);
    }

//    public static Vector4d sub(Tuple4d one, Tuple4d second) {
//        return new Vector4d(one.x - second.x, one.y - second.y, one.z - second.z, 1);
//    }
}
