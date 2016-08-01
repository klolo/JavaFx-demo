package com.lolapp.util;

import com.lolapp.Launcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class StageSwitch {

    private String scenePathToLoad;

    private FXMLLoader loader = new FXMLLoader();

    public StageSwitch(final String scenePathToLoad) {
        this.scenePathToLoad = scenePathToLoad;
    }

    public void load(final Stage node) throws IOException {
        log.info("Change stage to: {}", scenePathToLoad);
        loader = new FXMLLoader(getClass().getResource(scenePathToLoad));
        loader.setResources(ResourceBundle.getBundle("common//bundles//messages", new Locale("pl")));

        final Scene newScene = new Scene(
                loader.load(),
                Launcher.SCENE_WIDTH,
                Launcher.SCENE_HEIGHT
        );

        node.setScene(newScene);
    }

    public Object getController() {
        return loader.getController();
    }

}
