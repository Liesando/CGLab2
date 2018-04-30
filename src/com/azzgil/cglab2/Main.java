package com.azzgil.cglab2;

import com.azzgil.cglab2.graphics.Context3D;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.vecmath.Point4d;

public class Main extends Application {

    private Context3D context3D;
    private double zStart;
    private double zEnd;
    private double zStep;
    private double xStart;
    private double xEnd;
    private double xStep;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/com/azzgil/cglab2/main.fxml"));
        AnchorPane root = loader.load();
        MainController mainController = loader.getController();
        mainController.setMainApp(this);

        context3D = new Context3D(300, 300, root);

        clear();
//        redraw();

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void clear() {
        context3D.clear();
    }

    public Context3D getContext3D() {
        return context3D;
    }

    public void redraw() {
        clear();
        double delta = 50.0;
        Point4d center = new Point4d(0, 0, 0, 1);
        Point4d oX = new Point4d(delta, 0, 0, 1);
        Point4d oY = new Point4d(0, delta, 0, 1);
        Point4d oZ = new Point4d(0, 0, delta, 1);

        context3D.drawLine(center, oX, Color.RED);
        context3D.drawLine(center, oY, Color.BLUE);
        context3D.drawLine(center, oZ, Color.GREEN);

        context3D.drawFunction((x,z) -> Math.sin(x) * 3.0 + z + 1,
                zStart, zEnd, zStep, xStart, xEnd, xStep, Color.WHITE);

//        context3D.drawPoint(context3D.transformPoint(center), 3, Color.WHITE);
//        context3D.drawPoint(context3D.transformPoint(x), 3, Color.RED);
//        context3D.drawPoint(context3D.transformPoint(y), 3, Color.BLUE);
//        context3D.drawPoint(context3D.transformPoint(z), 3, Color.GREEN);
    }

    public void setzStart(double zStart) {
        this.zStart = zStart;
    }

    public void setzEnd(double zEnd) {
        this.zEnd = zEnd;
    }

    public void setzStep(double zStep) {
        this.zStep = zStep;
    }

    public void setxStart(double xStart) {
        this.xStart = xStart;
    }

    public void setxEnd(double xEnd) {
        this.xEnd = xEnd;
    }

    public void setxStep(double xStep) {
        this.xStep = xStep;
    }
}
