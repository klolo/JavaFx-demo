package com.word_trainer;

import com.word_trainer.application.SpringFxmlLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Launcher extends Application {

    public static final int SCENE_WIDTH = 900;

    public static final int SCENE_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initPrimaryStage(primaryStage);

        final SpringFxmlLoader springFxmlLoader = new SpringFxmlLoader();
        final Scene scene = new Scene((Parent) springFxmlLoader.load("/scenes/login/login.fxml"), SCENE_WIDTH, SCENE_HEIGHT);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initPrimaryStage(final Stage primaryStage) {
        primaryStage.setTitle("Words trainer");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/common/images/icon.png")));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
    }

    public static void main(String[] args) {
        log.info("START");
        launch(args);
        log.info("END");
    }
}
