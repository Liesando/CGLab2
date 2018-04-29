package com.azzgil.cglab2;

import com.azzgil.cglab2.graphics.Context3D;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.vecmath.Point4d;
import javax.vecmath.Tuple2d;
import java.awt.Rectangle;

public class Main extends Application {

    private Rectangle clippingRect;
    private Context3D context3D;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/com/azzgil/cglab2/main.fxml"));
        AnchorPane root = loader.load();
        MainController mainController = loader.getController();
        mainController.setMainApp(this);

        context3D = new Context3D(300, 300, root);
        clippingRect = new Rectangle(75, 75, 100, 100);

        clear();
        redraw();

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
        double delta = 10.0;
        Point4d center = new Point4d(0, 0, 0, 1);
        Point4d x = new Point4d(delta, 0, 0, 1);
        Point4d y = new Point4d(0, delta, 0, 1);
        Point4d z = new Point4d(0, 0, delta, 1);

        context3D.drawLine(center, x, Color.RED);
        context3D.drawLine(center, y, Color.BLUE);
        context3D.drawLine(center, z, Color.GREEN);

//        context3D.drawPoint(context3D.transformPoint(center), 3, Color.WHITE);
//        context3D.drawPoint(context3D.transformPoint(x), 3, Color.RED);
//        context3D.drawPoint(context3D.transformPoint(y), 3, Color.BLUE);
//        context3D.drawPoint(context3D.transformPoint(z), 3, Color.GREEN);
    }
}
