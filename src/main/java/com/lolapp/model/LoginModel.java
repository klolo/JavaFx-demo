package com.lolapp.model;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Data;

import java.util.function.Predicate;

/**
 * Model for login page.
 */
@Data
public class LoginModel {

    @FXML
    protected TextField loginField;

    @FXML
    protected TextField passwordField;

    protected static final int MIN_LOGIN_LENGTH = 3;

    protected static final int MAX_LOGIN_LENGTH = 20;

    protected Predicate<TextField> fieldCorrectPredicate =
            f -> f.getText() != null && f.getText().length() > MIN_LOGIN_LENGTH && f.getText().length() < MAX_LOGIN_LENGTH;

}
