package com.word_trainer.controller;

import com.word_trainer.Launcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Base class for all constroller
 */
public abstract class BaseController {

    protected void switchStage(final Node node, final String stageToLoad) throws IOException {
        final Stage rootStage = (Stage) node.getScene().getWindow();
        final Scene newScene = new Scene(
                FXMLLoader.load(getClass().getResource(stageToLoad)),
                Launcher.SCENE_WIDTH,
                Launcher.SCENE_HEIGHT
        );
        rootStage.setScene(newScene);
    }
}
