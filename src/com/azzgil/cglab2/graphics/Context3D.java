package com.azzgil.cglab2.graphics;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.vecmath.*;
import java.util.function.ToDoubleBiFunction;

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

    public Tuple2d transformPoint(Tuple4d point) {
        Tuple4d out = new Point4d();
        pvw.transform(point, out);
        if (out.w == 0 || has(out, Double.POSITIVE_INFINITY) || has(out, Double.NEGATIVE_INFINITY)) {
            return null;
        }
        out.scale(1 / out.w);

        Tuple2d result = new Point2d(out.x, out.y);
        revertPoint(result);
        return result;
    }

    private boolean has(Tuple4d v, double value) {
        return v.x == value || v.y == value || v.z == value || v.w == value;
    }

    private void revertPoint(Tuple2d point) {
        point.y = screenCenter.y - point.y;
        point.x = screenCenter.x + point.x;
    }

    public void clear() {
        drawer.clear();
    }

    public void drawPoint(Tuple2d point, int r, Color color) {
        if (point == null) {
            return;
        }
        int x = (int) point.x;
        int y = (int) point.y;

        if (x < 0 || x > drawer.getCanvasWidth()
                || y < 0 || y > drawer.getCanvasHeight()) {
            // out of canvas
            return;
        }

        for (int i = x - r; i <= x + r; i++) {
            for (int j = y - r; j <= y + r; j++) {
                drawer.setPixel(i, j, color);
            }
        }
    }

    public void renderPoint(Point4d point, Color color) {
        drawPoint(transformPoint(point), 0, color);
    }

    public void translateCamera(double x, double y, double z) {
        view = new Matrix4dBuilder()
                .take(view)
                .translate(x, y, z)
                .build();
        computePVW();
    }

    public void drawLine(Tuple4d start, Tuple4d end, Color color) {
        Tuple2d tStart = transformPoint(start);
        Tuple2d tEnd = transformPoint(end);
        drawer.drawLine(tStart.x, tStart.y, tEnd.x, tEnd.y, color);
    }

    public void drawFunction(ToDoubleBiFunction<Double, Double> f,
                             double zStart, double zEnd, double zStep,
                             double xStart, double xEnd, double xStep,
                             Color color) {
        double z = zStart;
        while (z <= zEnd) {
            double x = xStart;
            while (x <= xEnd) {
                Point4d point = new Point4d(x, f.applyAsDouble(x, z), z, 1);
                renderPoint(point, color);

                double nextZ = z + zStep <= zEnd ? z + zStep : z - zStep;
                Vector4d nextZPlane = new Vector4d(x, f.applyAsDouble(x, nextZ), nextZ, 1);

                drawLine(point, nextZPlane, Color.BLUE);

                double nextX = x + xStep <= xEnd ? x + xStep : x - xStep;
                Vector4d nextXPlane = new Vector4d(nextX, f.applyAsDouble(nextX, z), z, 1);

                drawLine(point, nextXPlane, Color.DEEPPINK);

                nextZPlane = CommonHelper.makeVectorNormalized(point, nextZPlane);
                nextXPlane = CommonHelper.makeVectorNormalized(point, nextXPlane);
//                nextZPlane.sub(point);
//                nextXPlane.sub(point);
//                nextZPlane.normalize();
//                nextXPlane.normalize();

//                nextXPlane = CommonHelper.sub(nextXPlane, point);
//                nextZPlane = CommonHelper.sub(nextZPlane, point);
                Vector4d cross = CommonHelper.crossNormalized(nextXPlane, nextZPlane);
                cross.add(point);
                cross.w = 1;

                drawLine(point, cross, Color.YELLOW);

                x += xStep;
            }
            z += zStep;
        }
    }
}
