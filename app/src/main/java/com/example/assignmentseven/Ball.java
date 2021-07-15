package com.example.assignmentseven;
import android.graphics.Color;

public class Ball {
    //Data Fields
    protected float _diameter;
    protected float _x;
    protected float _y;
    protected float _xSpeed;
    protected float _ySpeed;
    protected int _color;

    public Ball(float diameter, float x, float y, float xSpeed, float ySpeed, int color) {
        this._diameter = diameter;
        this._x = x;
        this._y = y;
        this._xSpeed = xSpeed;
        this._ySpeed = ySpeed;
        this._color = color;
    }

    //getters and setters
    public float get_diameter() {
        return _diameter;
    }

    public void set_diameter(float _diameter) {
        this._diameter = _diameter;
    }

    public float get_x() {
        return _x;
    }

    public void set_x(float _x) {
        this._x = _x;
    }

    public float get_y() {
        return _y;
    }

    public void set_y(float _y) {
        this._y = _y;
    }

    public float get_xSpeed() {
        return _xSpeed;
    }

    public void set_xSpeed(float _xSpeed) {
        this._xSpeed = _xSpeed;
    }

    public float get_ySpeed() {
        return _ySpeed;
    }

    public void set_ySpeed(float _ySpeed) {
        this._ySpeed = _ySpeed;
    }

    public int get_color() {
        return _color;
    }

    public void set_color(int _color) {
        this._color = _color;
    }
}
