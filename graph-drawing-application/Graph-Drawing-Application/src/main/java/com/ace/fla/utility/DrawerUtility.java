package com.ace.fla.utility;

import com.ace.fla.node.GraphNode;
import com.ace.fla.pair.SimplePair;
import com.ace.fla.shape.Shape;
import com.ace.fla.style.StyleUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class DrawerUtility {

    private Shape[] shapes;
    private Text[] labels;
    private List<SimplePair<Shape, Shape>> nodePairs;
    private int nodeCount;
    private Canvas canvas;
    private Color currentColor;
    private GraphicsContext graphicsContext;
    private int prevDragX;
    private int prevDragY;
    private Shape shapeBeingDragged;

    private TextField labelTextField;

    private TextField lineTextField1;
    private TextField lineTextField2;

    private TextField editTextField;
    private TextField newValueTextField;

    private static final int MAX_NUMBER_OF_NODES = 50;

    {
        this.shapes = new Shape[MAX_NUMBER_OF_NODES];
        this.labels = new Text[MAX_NUMBER_OF_NODES];
        this.nodeCount = 0;
        this.canvas = this.makeCanvas();
        this.currentColor = Color.ORANGE;
        this.shapeBeingDragged = null;
        this.nodePairs = new ArrayList<>();
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Canvas makeCanvas() {
        Canvas canvas = new Canvas(1000,500);
        canvas.setOnMousePressed(this::mousePressed);
        canvas.setOnMouseReleased(this::mouseReleased);
        canvas.setOnMouseDragged(this::mouseDragged);
        return canvas;
    }

    public FlowPane makeToolPanel() {
        this.labelTextField = new TextField("your label");
        this.labelTextField.setStyle(StyleUtility.TEXT_FIELD_STYLE);

        this.lineTextField1 = new TextField("first node");
        this.lineTextField1.setStyle(StyleUtility.TEXT_FIELD_STYLE);

        this.lineTextField2 = new TextField("first node");
        this.lineTextField2.setStyle(StyleUtility.TEXT_FIELD_STYLE);

        this.editTextField = new TextField("initial label");
        this.editTextField.setStyle(StyleUtility.TEXT_FIELD_STYLE);

        this.newValueTextField = new TextField("updated label");
        this.newValueTextField.setStyle(StyleUtility.TEXT_FIELD_STYLE);

        Button nodeButton = new Button("New node");
        nodeButton.setStyle(StyleUtility.DARK_BLUE_BUTTON);
        nodeButton.setOnAction(e -> this.addShape(new GraphNode()));

        Button lineButton = new Button("New line");
        lineButton.setStyle(StyleUtility.DARK_BLUE_BUTTON);
        lineButton.setOnAction(e -> this.addLine(this.getLineNode1(), this.getLineNode2()));

        Button deleteButton = new Button("Clear screen");
        deleteButton.setStyle(StyleUtility.ALERT_RED_BUTTON);
        deleteButton.setOnAction(e -> this.clearScreen());

        Button editButton = new Button("Edit label ");
        editButton.setStyle(StyleUtility.DARK_BLUE_BUTTON);
        editButton.setOnAction(e -> this.changeLabel(this.getContentOfEditTextField(),
                this.getContentOfNewValueTextField()));

        ComboBox<String> combobox = new ComboBox<>();
        combobox.setStyle(StyleUtility.COMBO_BOX_STYLE);
        combobox.setEditable(false);

        Color[] colors = { Color.ORANGE, Color.YELLOW, Color.RED, Color.CYAN };
        String[] colorNames = { "Orange", "Yellow", "Red", "Cyan"};
        combobox.getItems().addAll(colorNames);
        combobox.setValue("Orange");
        combobox.setOnAction(
                color -> currentColor = colors[combobox.getSelectionModel().getSelectedIndex()] );

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(7);
        flowPane.setVgap(5);
        flowPane.getChildren().add(nodeButton);
        flowPane.getChildren().add(combobox);
        flowPane.getChildren().add(this.labelTextField);
        flowPane.getChildren().add(lineButton);
        flowPane.getChildren().add(this.lineTextField1);
        flowPane.getChildren().add(this.lineTextField2);
        flowPane.getChildren().add(deleteButton);
        flowPane.getChildren().add(editButton);
        flowPane.getChildren().add(this.editTextField);
        flowPane.getChildren().add(this.newValueTextField);
        flowPane.setStyle(StyleUtility.HBOX_STYLE);

        return flowPane;
    }

    public void paintCanvas() {
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(0,0, this.canvas.getWidth(), this.canvas.getHeight());

        for (int i = 0; i < this.nodeCount; i++) {
            Shape shape = this.shapes[i];
            shape.draw(this.graphicsContext);
        }

        for (SimplePair<Shape, Shape> simplePair: this.nodePairs) {
            Line line = new Line();

            line.setStartX(simplePair.getFirst().getCenter().getFirst());
            line.setStartY(simplePair.getFirst().getCenter().getSecond());
            line.setEndX(simplePair.getSecond().getCenter().getFirst());
            line.setEndY(simplePair.getSecond().getCenter().getSecond());

            graphicsContext.strokeLine(line.getStartX(), line.getStartY(),
                    line.getEndX(), line.getEndY());
        }
    }

    private void addShape(Shape shape) {
        shape.setColor(this.currentColor);
        shape.redraw(10,10,100,80);

        String labelText = this.labelTextField.getText();
        Text label = new Text(labelText);
        shape.setLabel(label);

        this.shapes[this.nodeCount] = shape;
        this.labels[this.nodeCount] = label;
        this.nodeCount++;
        this.paintCanvas();
    }

    private void mousePressed(MouseEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();

        for (int i = this.nodeCount - 1; i >= 0; i-- ) {
            Shape shape = this.shapes[i];

            if (shape.containsPoint(x,y)) {
                this.shapeBeingDragged = shape;
                this.prevDragX = x;
                this.prevDragY = y;
            }
        }
    }

    private void mouseDragged(MouseEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();

        if (this.shapeBeingDragged != null) {
            this.shapeBeingDragged.moveBy(x - prevDragX, y - prevDragY);
            this.prevDragX = x;
            this.prevDragY = y;
            this.paintCanvas();
        }
    }

    private void mouseReleased(MouseEvent event) {
        this.shapeBeingDragged = null;
    }

    private String getLineNode1() {
        return this.lineTextField1.getText();
    }

    private String getLineNode2() {
        return this.lineTextField2.getText();
    }

    private String getContentOfEditTextField() {
        return this.editTextField.getText();
    }

    private String getContentOfNewValueTextField() {
        return this.newValueTextField.getText();
    }

    private void addLine(String node1, String node2) {
        Shape shape1 = null;
        Shape shape2 = null;

        for (int i = 0; i < this.nodeCount; i ++) {
            Shape shape = this.shapes[i];

            if (shape.getLabel().getText().trim().equals(node1)) {
                shape1 = shape;
            }
            if (shape.getLabel().getText().trim().equals(node2)) {
                shape2 = shape;
            }
        }

        if (shape1 != null && shape2 != null) {
            this.nodePairs.add(new SimplePair<>(shape1, shape2));
            this.paintCanvas();
        }
    }

    private void clearScreen() {
        this.graphicsContext.clearRect(0,0,
                this.canvas.getWidth(), this.canvas.getHeight());
        this.shapes = new Shape[MAX_NUMBER_OF_NODES];
        this.labels = new Text[MAX_NUMBER_OF_NODES];
        this.nodePairs = new ArrayList<>();
        this.nodeCount = 0;
        this.paintCanvas();
    }

    private void changeLabel(String currentLabel, String newLabel) {
        for (int i = 0; i < this.nodeCount; i ++) {
            Shape shape = this.shapes[i];

            if (shape.getLabel().getText().equals(currentLabel)) {
                shape.getLabel().setText(newLabel);
                paintCanvas();
                break;
            }
        }
    }
}