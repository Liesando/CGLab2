package com.azzgil.cglab2.graphics.lighting;

import javafx.scene.paint.Color;

public abstract class Light {
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
