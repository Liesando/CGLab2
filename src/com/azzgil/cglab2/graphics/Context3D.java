package com.azzgil.cglab2.graphics;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.vecmath.*;

public class Context3D {
    private Matrix4d world;
    private Matrix4d view;
    private Matrix4d projection;
    private Drawer drawer;
    private Matrix4d pvw; // P*V*W
    private Tuple2d screenCenter;

    public Context3D(double canvasWidth, double canvasHeight, Pane root) {
        drawer = new Drawer(canvasWidth, canvasHeight, root);
        screenCenter = new Point2d(canvasWidth / 2, canvasHeight / 2);

        // default matrices
        world = Math3DHelper.identityMatrix();
        view = new Matrix4dBuilder()
                .identity()
                .translate(0, 0, -1)
                .build();
        projection = Math3DHelper.projectionMatrix(5);

        computePVW();
    }

    private void computePVW() {
        pvw = new Matrix4d();
        pvw.mul(view, world);
        pvw.mul(projection, pvw);
    }

    public Tuple2d transformPoint(Point4d point) {
        Tuple4d out = new Point4d();
        pvw.transform(point, out);
        out.scale(1 / out.w);

        Tuple2d result = new Point2d(out.x, out.y);
        revertPoint(result);
        return result;
    }

    private void revertPoint(Tuple2d point) {
        point.y = screenCenter.y - point.y;
        point.x = screenCenter.x + point.x;
    }

    public void clear() {
        drawer.clear();
    }

    public void drawPoint(Tuple2d point, int r, Color color) {
        int x = (int) point.x;
        int y = (int) point.y;
        for (int i = x - r; i <= x + r; i++) {
            for (int j = y - r; j <= y + r; j++) {
                drawer.setPixel(i, j, color);
            }
        }
    }

    public void translateCamera(double x, double y, double z) {
        view = new Matrix4dBuilder()
                .take(view)
                .translate(x, y, z)
                .build();
        computePVW();
    }

    public void drawLine(Point4d start, Point4d end, Color color) {
        Tuple2d tStart = transformPoint(start);
        Tuple2d tEnd = transformPoint(end);
        drawer.drawLine(tStart.x, tStart.y, tEnd.x, tEnd.y, color);
    }
}
