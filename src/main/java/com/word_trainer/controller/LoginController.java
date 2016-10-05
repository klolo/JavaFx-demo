package com.word_trainer.controller;

import com.word_trainer.application.SpringFxmlLoader;
import com.word_trainer.application.StageSwitch;
import com.word_trainer.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
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
        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> validateFieldOnBlur(passwordField, newValue));
        langCombobox.getSelectionModel().select(selectedLang);
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
        if (authorizationService.loginUser(loginField.getText(), passwordField.getText())) {
            final StageSwitch stageSwitch = new StageSwitch("/scenes/dashboard/dashboard.fxml");
            stageSwitch.load((Stage) ((Node) event.getSource()).getScene().getWindow());
        }
        else {
            validateFieldOnBlur(loginField, false);
            validateFieldOnBlur(passwordField, false);
        }
    }

    public void changeLang(ActionEvent actionEvent) throws IOException {
        final String selectedLangText = (String) langCombobox.getSelectionModel().getSelectedItem();
        switch (selectedLangText) {
            case "Polski" : {
                SpringFxmlLoader.changeLocale("pl");
                selectedLang = 0;
                break;
            }
            case "English" : {
                SpringFxmlLoader.changeLocale("en");
                selectedLang = 1;
                break;
            }
        }

        final StageSwitch stageSwitch = new StageSwitch("/scenes/login/login.fxml");
        stageSwitch.load((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }
}
