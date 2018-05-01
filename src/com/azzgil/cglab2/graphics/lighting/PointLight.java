package com.azzgil.cglab2.graphics.lighting;

import com.azzgil.cglab2.graphics.CommonHelper;
import javafx.scene.paint.Color;

import javax.vecmath.Point4d;
import javax.vecmath.Tuple4d;
import javax.vecmath.Vector4d;

public class PointLight extends Light {
    private Tuple4d position;
    private double range;
    private double power;

    public PointLight(Tuple4d position, double range, double power, Color color) {
        this.position = new Point4d(position);
        this.range = range;
        this.power = CommonHelper.clamp(power, 0, 1);
        setColor(color);
    }

    @Override
    public Color getColor() {
        return CommonHelper.mul(super.getColor(), power);
    }

    public Color getScaledColor(double distance) {
        return CommonHelper.mul(getColor(), Math.max(0, (range - distance) / range));
    }

    public Color getColorForPoint(Tuple4d point, Vector4d normal) {
        double factor = CommonHelper.dot(normal, CommonHelper.makeVectorNormalized(point, getPosition()));
        factor = Math.max(0, factor);
        return CommonHelper.mul(getScaledColor(CommonHelper.distance(point, getPosition())),
                factor);
    }

    public Tuple4d getPosition() {
        return position;
    }

    public void setPosition(Tuple4d position) {
        this.position = position;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }
}
