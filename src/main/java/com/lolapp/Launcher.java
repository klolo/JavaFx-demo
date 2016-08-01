package com.lolapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class Launcher extends Application {

    public static final int SCENE_WIDTH = 1000;

    public static final int SCENE_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initPrimaryStage(primaryStage);

        final FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("common//bundles//messages", new Locale("pl")));
        final InputStream fxmlStream = getClass().getResource("/scenes/login/login.fxml").openStream();
        BorderPane root = loader.load(fxmlStream);

        root.setStyle("-fx-background-radius: 20px;-fx-background-color:transparent;");
        final Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
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
