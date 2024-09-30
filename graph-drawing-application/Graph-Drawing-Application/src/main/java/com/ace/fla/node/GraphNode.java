package com.ace.fla.node;

import com.ace.fla.pair.SimplePair;
import com.ace.fla.shape.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GraphNode extends Shape {

    public GraphNode() {
        super();
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(super.getColor());
        graphicsContext.fillOval(super.getLeft(),super.getTop(),
                super.getWidth(), super.getHeight());
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeOval(super.getLeft(),super.getTop(),
                super.getWidth(), super.getHeight());

        Text label = super.getLabel();
        graphicsContext.strokeText(label.getText(),
                label.getX() + 54, label.getY() + 40);
    }

    @Override
    public boolean containsPoint(int x, int y) {
        double rx = super.getWidth() / 2.0;
        double ry = super.getHeight() / 2.0;
        double cx = super.getLeft() + rx;
        double cy = super.getTop() + ry;

        return (ry * (x-cx)) * (ry*(x-cx)) + (rx * (y-cy)) *
                (rx * (y-cy)) <= rx * rx * ry * ry;
    }

    @Override
    public SimplePair<Double, Double> getCenter() {
        double cx = super.getLeft() + super.getWidth() / 2.0;
        double cy = super.getTop() + super.getHeight() / 2.0;

        return new SimplePair<>(cx, cy);
    }
}
