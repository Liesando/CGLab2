package com.azzgil.cglab2.graphics;

import com.azzgil.cglab2.graphics.lighting.PointLight;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.vecmath.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

public class Context3D {
    private Matrix4d world;
    private Matrix4d view;
    private Matrix4d projection;
    private Drawer drawer;
    private Matrix4d pvw; // P*V*W
    private Tuple2d screenCenter;
    private List<PointLight> lights;

    public Context3D(double canvasWidth, double canvasHeight, Pane root) {
        drawer = new Drawer(canvasWidth, canvasHeight, root);
        screenCenter = new Point2d(canvasWidth / 2, canvasHeight / 2);

        // default matrices
        world = Math3DHelper.identityMatrix();
        view = new Matrix4dBuilder()
                .identity()
                .translate(0, 0, -20)
                .build();
        projection = Math3DHelper.projectionMatrix(canvasWidth, canvasHeight, 1, 100);

        computePVW();
        setupLights(15, 0.4);
    }

    private void computePVW() {
        pvw = new Matrix4d();
        pvw.mul(view, world);
        pvw.mul(projection, pvw);
    }

    public void setupLights(double range, double power) {
        double offset = 10.0;
        double y = 5.0;

        PointLight light1 = new PointLight(new Point4d(0, y, 0, 1), range, power, Color.WHITE);
        PointLight light2 = new PointLight(new Point4d(0, y, offset, 1), range, power, Color.RED);
        PointLight light3 = new PointLight(new Point4d(offset, y, 0, 1), range, power, Color.BLUE);
        PointLight light4 = new PointLight(new Point4d(offset, y, offset, 1), range, power, Color.GREEN);

        lights = new ArrayList<>();
        lights.add(light1);
        lights.add(light2);
        lights.add(light3);
        lights.add(light4);
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
                             Color color, boolean drawNormals) {
        double z = zStart;
        while (z < zEnd) {
            double x = xStart;
            while (x <= xEnd) {

                // get the point
                Point4d point = new Point4d(x, f.applyAsDouble(x, z), z, 1);

                // get the normal
                boolean reverseCross = false;
                double nextZ;
                double nextX;

                if (z + zStep > zEnd) {
                    reverseCross = true;
                }
                nextZ = Math.min(z + zStep, zEnd);
                Vector4d nextZPlane = new Vector4d(x, f.applyAsDouble(x, nextZ), nextZ, 1);

                if (x + xStep > xEnd) {
                    nextX = x - xStep;
                    reverseCross = !reverseCross;
                } else {
                    nextX = x + xStep;
                }
                Vector4d nextXPlane = new Vector4d(nextX, f.applyAsDouble(nextX, z), z, 1);
                Vector4d nextZXPlane = new Vector4d(nextX, f.applyAsDouble(nextX, nextZ), nextZ, 1);

                // construct triangle
                Tuple2d p1 = transformPoint(point);
                Tuple2d p2 = transformPoint(nextZPlane);
                Tuple2d p3 = transformPoint(nextXPlane);
                Tuple2d p4 = transformPoint(nextZXPlane);
                List<Tuple2d> firstTriangle = new ArrayList<>();
                firstTriangle.add(p1);
                firstTriangle.add(p2);
                firstTriangle.add(p3);
                List<Tuple2d> secondTriangle = new ArrayList<>();
                secondTriangle.add(p2);
                secondTriangle.add(p3);
                secondTriangle.add(p4);

                Vector4d toZPlane = CommonHelper.makeVectorNormalized(point, nextZPlane);
                Vector4d toXPlane = CommonHelper.makeVectorNormalized(point, nextXPlane);

                Vector4d firstCross, secondCross;
                if (!reverseCross) {
                    firstCross = CommonHelper.crossNormalized(toZPlane, toXPlane);
                    toZPlane = CommonHelper.makeVectorNormalized(nextZXPlane, nextZPlane);
                    toXPlane = CommonHelper.makeVectorNormalized(nextZXPlane, nextXPlane);
                    secondCross = CommonHelper.crossNormalized(toXPlane, toZPlane);
                } else {
                    firstCross = CommonHelper.crossNormalized(toXPlane, toZPlane);
                    toZPlane = CommonHelper.makeVectorNormalized(nextZXPlane, nextZPlane);
                    toXPlane = CommonHelper.makeVectorNormalized(nextZXPlane, nextXPlane);
                    secondCross = CommonHelper.crossNormalized(toZPlane, toXPlane);
                }

                // draw 1st filled triangle
                Color lightColor = Color.BLACK;
                Vector4d center = getTriangleCenter(point, nextXPlane, nextZPlane);
                for (PointLight pl : lights) {
                    lightColor = CommonHelper.add(lightColor,
                            pl.getColorForPoint(center, firstCross));
                }
                Color resultColor = CommonHelper.mul(color, lightColor);
//                resultColor = CommonHelper.mul(resultColor, CommonHelper.dot(firstCross, toLight));
                drawer.fillPolygon(firstTriangle, resultColor);

                // draw 2d filled triangle
                lightColor = Color.BLACK;
                center = getTriangleCenter(nextXPlane, nextZPlane, nextZXPlane);
                for (PointLight pl : lights) {
                    lightColor = CommonHelper.add(lightColor,
                            pl.getColorForPoint(center, secondCross));
                }
                resultColor = CommonHelper.mul(color, lightColor);
                drawer.fillPolygon(secondTriangle, resultColor);

                // draw normal
                if (drawNormals) {
                    firstCross.add(point);
                    firstCross.w = 1;
                    drawLine(point, firstCross, Color.YELLOW);
                }

                x += xStep;
            }
            z += zStep;
        }
    }

    private Vector4d getTriangleCenter(Tuple4d v1, Tuple4d v2, Tuple4d v3) {
        Vector4d result = new Vector4d(v1);
        result.add(v2);
        result.add(v3);
        result.scale(1.0 / 3);
        return result;
    }
}
