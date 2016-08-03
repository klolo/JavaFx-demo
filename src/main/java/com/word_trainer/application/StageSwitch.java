package com.word_trainer.application;

import com.word_trainer.Launcher;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Klasa pomocnicza do zmieniania scen z wykorzystaniem springa.
 */
@Slf4j
public class StageSwitch {

    private String scenePathToLoad;

    private SpringFxmlLoader loader = new SpringFxmlLoader();

    public StageSwitch(final String scenePathToLoad) {
        this.scenePathToLoad = scenePathToLoad;
    }

    public void load(final Stage node) throws IOException {
        log.info("Change stage to: {}", scenePathToLoad);

        final Scene newScene = new Scene(
                (Parent) loader.load(scenePathToLoad),
                Launcher.SCENE_WIDTH,
                Launcher.SCENE_HEIGHT
        );

        node.setScene(newScene);
    }

}
