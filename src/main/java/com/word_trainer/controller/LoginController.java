package com.word_trainer.controller;

import com.word_trainer.application.SpringFxmlLoader;
import com.word_trainer.application.StageSwitch;
import com.word_trainer.model.LoginModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Odpowiada za zalogowania uzytkownika oraz za zmiane jezyka.
 */
@Component
@Slf4j
public class LoginController extends LoginModel {

    @FXML
    public void initialize() {
        loginField.focusedProperty().addListener((observable, oldValue, newValue) -> validateFieldOnBlur(loginField, newValue));

        Platform.runLater(() -> loginField.requestFocus());

        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> validateFieldOnBlur(passwordField, newValue));
        langCombobox.getSelectionModel().select(selectedLang);

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                try {
                    login(loginField.getScene());
                }
                catch (IOException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void validateFieldOnBlur(final TextField field, final boolean focused) {
        if (focused) {
            return;
        }

        if (!authorizationService.fieldCorrectPredicate.test(field.getText())) {
            field.getStyleClass().add("incorrectField");
        }
        else {
            field.getStyleClass().remove("incorrectField");
        }
    }

    public void login(final ActionEvent event) throws IOException, NoSuchAlgorithmException {
        login(((Node) event.getSource()).getScene());
    }

    public void login(final Scene scene) throws IOException, NoSuchAlgorithmException {
        if (authorizationService.loginUser(loginField.getText(), passwordField.getText())) {
            final StageSwitch stageSwitch = new StageSwitch("/scenes/dashboard/dashboard.fxml");
            stageSwitch.load((Stage) scene.getWindow());
        }
        else {
            validateFieldOnBlur(loginField, false);
            validateFieldOnBlur(passwordField, false);
        }
    }

    public void changeLang(ActionEvent actionEvent) throws IOException {
        final String selectedLangText = (String) langCombobox.getSelectionModel().getSelectedItem();
        switch (selectedLangText) {
            case "Polski": {
                SpringFxmlLoader.changeLocale("pl");
                selectedLang = 0;
                break;
            }
            case "English": {
                SpringFxmlLoader.changeLocale("en");
                selectedLang = 1;
                break;
            }
        }

        final StageSwitch stageSwitch = new StageSwitch("/scenes/login/login.fxml");
        stageSwitch.load((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }
}
