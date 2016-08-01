package com.lolapp.controller.components;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class WindowBarController {

    @FXML
    private HBox toolbar;

    private double initialX = 0;

    private double initialY = 0;

    public String applicationState ="stae";


    private void addDragListeners(final Node n) {
        n.setOnMousePressed(me -> mousePressedProcess(n, me));
        n.setOnMouseDragged(me -> mouseDraggedProcess(n, me));
    }

    private void mouseDraggedProcess(final Node n, final MouseEvent me) {
        if (me.getButton() != MouseButton.MIDDLE) {
            n.getScene().getWindow().setX(me.getScreenX() - initialX);
            n.getScene().getWindow().setY(me.getScreenY() - initialY);
        }
    }

    private void mousePressedProcess(final Node n, final MouseEvent me) {
        if (me.getButton() != MouseButton.MIDDLE) {
            initialX = me.getSceneX();
            initialY = me.getSceneY();
        }
        else {
            n.getScene().getWindow().centerOnScreen();
            initialX = n.getScene().getWindow().getX();
            initialY = n.getScene().getWindow().getY();
        }
    }

    @FXML
    public void initialize() {
        addDragListeners(toolbar);
    }

    public void closeApplication() {
        Platform.exit();
    }
}
