package com.azzgil.cglab2;

import com.azzgil.cglab2.graphics.Drawer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.vecmath.Point2d;
import java.awt.Rectangle;
import java.util.List;

public class Main extends Application {

    private Rectangle clippingRect;
    private Drawer drawer;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/com/azzgil/cglab1/main.fxml"));
        AnchorPane root = loader.load();
        MainController mainController = loader.getController();
        mainController.setMainApp(this);

        drawer = new Drawer(300, 300, root);
        clippingRect = new Rectangle(75, 75, 100, 100);

        clear();

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void clear() {
        drawer.clear();
    }

    public void strokePolygon(List<Point2d> polygonPoints, Color color) {
        drawer.strokePolygon(polygonPoints, color);
    }
}
