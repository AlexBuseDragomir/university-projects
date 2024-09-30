package com.ace.fla.shape;

import com.ace.fla.pair.SimplePair;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public abstract class Shape {

    private int left;
    private int top;
    private int width;
    private int height;
    private Color color;
    private Text label;

    public Shape() {}

    public abstract void draw(GraphicsContext graphicsContext);

    public abstract SimplePair<Double, Double> getCenter();

    public void redraw(int left, int top, int width, int height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    public void moveBy(int dx, int dy) {
        this.left += dx;
        this.top += dy;
        this.label.setX(this.label.getX() + dx);
        this.label.setY(this.label.getY() + dy);
    }

    public boolean containsPoint(int x, int y) {
        return x >= this.left && x < this.left + this.width &&
                y >= this.top && y < this.top + this.height;
    }

    public int getLeft() {
        return this.left;
    }

    public int getTop() {
        return this.top;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLabel(Text label) {
        this.label = label;
    }

    public Text getLabel() {
        return this.label;
    }
}
