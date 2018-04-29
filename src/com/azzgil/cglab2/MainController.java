package com.azzgil.cglab2;

import javafx.fxml.FXML;

public class MainController {

    private Main mainApp;
    private double delta = 3f;

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

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
