package com.azzgil.cglab2.graphics;

import javafx.scene.layout.Pane;

import javax.vecmath.*;

public class Context3D {
    private Matrix4d world;
    private Matrix4d view;
    private Matrix4d projection;
    private Drawer drawer;
    private Matrix4d wvp; // actually P*V*W
    private Tuple2d screenCenter;

    public Context3D(double canvasWidth, double canvasHeight, Pane root) {
        drawer = new Drawer(canvasWidth, canvasHeight, root);
        screenCenter = new Point2d(canvasWidth / 2, canvasHeight / 2);

        // default matrices
        world = Math3DHelper.identityMatrix();
        view = new Matrix4dBuilder()
                .identity()
                .translate(1, 0, -1)
                .build();
        projection = Math3DHelper.projectionMatrix(1);

        computeWVP();
    }

    private void computeWVP() {
        wvp = new Matrix4d();
        wvp.mul(view, world);
        wvp.mul(projection, wvp);
    }

    public Tuple2d transformPoint(Point4d point) {
        Tuple4d out = new Point4d();
        wvp.transform(point, out);

        Tuple2d result = new Point2d(out.x, out.y);
        revertPoint(result);
        return result;
    }

    public void revertPoint(Tuple2d point) {
        point.y = screenCenter.y - point.y;
        point.x = screenCenter.x + point.x;
    }
}
