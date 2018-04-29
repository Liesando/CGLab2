package com.azzgil.cglab2;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javax.vecmath.Point2d;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private TextField renderPolygonTf;
    @FXML
    private TextField clippingPolygonTf;
    private Main mainApp;
    private List<Point2d> renderPolygonPoints = new ArrayList<>();
    private List<Point2d> clippingPolygonPoints = new ArrayList<>();

    @FXML
    private void onClearBtnClick() {
        if(mainApp != null) {
            mainApp.clear();
            renderPolygonPoints.clear();
            clippingPolygonPoints.clear();
        }
    }

    @FXML
    private void onDrawBtnClick() {
        if (mainApp != null && validateData()) {
            mainApp.clear();
            mainApp.strokePolygon(renderPolygonPoints, Color.BLUE);
            mainApp.strokePolygon(clippingPolygonPoints, Color.RED);
        }
    }

    private boolean validateData() {
        try {
            renderPolygonPoints.clear();
            clippingPolygonPoints.clear();
            parsePoints(renderPolygonTf, renderPolygonPoints);
            parsePoints(clippingPolygonTf, clippingPolygonPoints);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void parsePoints(TextField tf, List<Point2d> collector) throws Exception {
        if (tf.getText().trim().length() == 0) {
            throw new NumberFormatException();
        }

        String[] points = tf.getText().trim().split(" ");
        for (String point : points) {
            String[] pointXY = point.split(";");
            if (pointXY.length != 2) {
                throw new Exception();
            }

            Point2d newPoint = new Point2d(Double.parseDouble(pointXY[0]), Double.parseDouble(pointXY[1]));
            collector.add(newPoint);
        }
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
