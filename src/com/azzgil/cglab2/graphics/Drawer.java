package com.azzgil.cglab2.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.vecmath.Point2d;
import javax.vecmath.Tuple2d;
import javax.vecmath.Vector3d;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Кастомный класс-рисователь
 */
public class Drawer {
    private double canvasWidth;
    private double canvasHeight;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private PixelWriter pixelWriter;
    private Color backgroundColor;

    public Drawer(double width, double height, Pane root, Color backgroundColor) {
        canvasWidth = width;
        canvasHeight = height;
        this.backgroundColor = backgroundColor;

        canvas = new Canvas(width, height);
        graphicsContext = canvas.getGraphicsContext2D();
        pixelWriter = graphicsContext.getPixelWriter();

        root.getChildren().add(canvas);
    }

    public Drawer(double width, double height, Pane root) {
        this(width, height, root, Color.BLACK);
    }

    /**
     * Очищает область рисования цветом фона
     */
    public void clear() {
        graphicsContext.setFill(backgroundColor);
        graphicsContext.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    public void setPixel(int x, int y, Color c) {
        pixelWriter.setColor(x, y, c);
    }

    public void strokePolygon(List<? extends Tuple2d> points, Color color) {
        double[] xs = points.stream().mapToDouble(p -> p.getX()).toArray();
        double[] ys = points.stream().mapToDouble(p -> p.getY()).toArray();
        graphicsContext.setStroke(color);
        graphicsContext.strokePolygon(xs, ys, points.size());
    }

    private void fillPolygon(List<? extends Tuple2d> points, Color color) {
        double[] xs = points.stream().mapToDouble(p -> p.getX()).toArray();
        double[] ys = points.stream().mapToDouble(p -> p.getY()).toArray();
        graphicsContext.setFill(color);
        graphicsContext.fillPolygon(xs, ys, points.size());
    }

    public double getCanvasHeight() {
        return canvasHeight;
    }

    public double getCanvasWidth() {
        return canvasWidth;
    }

    public void drawLine(double x1, double y1, double x2, double y2, Color color) {
        graphicsContext.setStroke(color);
        graphicsContext.strokeLine(x1, y1, x2, y2);
    }
}
