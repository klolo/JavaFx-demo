package com.word_trainer.model;

import com.word_trainer.services.AuthorizationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Predicate;

/**
 * Model for login page.
 */
@Data
public class LoginModel {

    @Autowired
    protected AuthorizationService authorizationService;

    @FXML
    protected TextField loginField;

    @FXML
    protected TextField passwordField;

}
