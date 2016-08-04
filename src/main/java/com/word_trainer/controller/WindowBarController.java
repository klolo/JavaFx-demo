package com.word_trainer.controller;

import com.word_trainer.services.WordsService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WindowBarController {

    @Autowired
    private WordsService wordsService;

    @FXML
    private HBox toolbar;

    private double initialX = 0;

    private double initialY = 0;

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
        wordsService.saveCurrentWords();
        Platform.exit();
    }
}
