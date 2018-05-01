package com.azzgil.cglab2;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class MainController {

    private Main mainApp;
    private double delta = 3f;
    @FXML
    private TextField zPlaneTf;
    @FXML
    private TextField xPlaneTf;
    @FXML
    private TextField lightsTf;
    @FXML
    private CheckBox drawNormalsCb;

    @FXML
    private void onLeftClick() {
        mainApp.getContext3D().translateCamera(-delta, 0, 0);
        mainApp.redraw();
    }

    @FXML
    private void onRightClick() {
        mainApp.getContext3D().translateCamera(delta, 0, 0);
        mainApp.redraw();
    }

    @FXML
    private void onUpClick() {
        mainApp.getContext3D().translateCamera(0, delta, 0);
        mainApp.redraw();
    }

    @FXML
    private void onDownClick() {
        mainApp.getContext3D().translateCamera(0, -delta, 0);
        mainApp.redraw();
    }

    @FXML
    private void onGoClick() {
        String[] parsed = zPlaneTf.getText().trim().split(" ");
        try {
            mainApp.setzStart(Double.parseDouble(parsed[0]));
            mainApp.setzEnd(Double.parseDouble(parsed[1]));
            mainApp.setzStep(Double.parseDouble(parsed[2]));

            parsed = xPlaneTf.getText().trim().split(" ");
            mainApp.setxStart(Double.parseDouble(parsed[0]));
            mainApp.setxEnd(Double.parseDouble(parsed[1]));
            mainApp.setxStep(Double.parseDouble(parsed[2]));

            mainApp.setDrawNormals(drawNormalsCb.isSelected());

            parsed = lightsTf.getText().trim().split(" ");
            mainApp.setLightsRangeAndPower(Double.parseDouble(parsed[0]), Double.parseDouble(parsed[1]));

            mainApp.redraw();
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
        }
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
