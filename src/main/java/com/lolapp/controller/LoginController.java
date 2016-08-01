package com.lolapp.controller;

import com.lolapp.model.LoginModel;
import com.lolapp.util.StageSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LoginController extends LoginModel {

    @FXML
    public void initialize() {
        loginField.focusedProperty().addListener((observable, oldValue, newValue) -> validateFieldOnBlur(loginField, newValue));
        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> validateFieldOnBlur(passwordField, newValue));
    }

    public void login(final ActionEvent event) throws IOException {
     //   if (fieldCorrectPredicate.test(loginField) && fieldCorrectPredicate.test(passwordField)) {
            log.info("Login user: {} with password: {}", loginField.getText(), passwordField.getText());
            final StageSwitch stageSwitch = new StageSwitch("/scenes/dashboard/dashboard.fxml");
            stageSwitch.load((Stage) ((Node) event.getSource()).getScene().getWindow());
      //  }
    }

    private void validateFieldOnBlur(final TextField field, final boolean focused) {
        if (focused) {
            return;
        }

        if (!fieldCorrectPredicate.test(field)) {
            field.getStyleClass().add("incorrectField");
        }
        else {
            field.getStyleClass().remove("incorrectField");
        }
    }

}
