package com.word_trainer.model;

import com.jfoenix.controls.JFXComboBox;
import com.word_trainer.repository.UserRepository;
import com.word_trainer.services.AuthorizationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Predicate;

/**
 * Model for login page.
 */
public class LoginModel {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AuthorizationService authorizationService;

    @FXML
    protected TextField loginField;

    @FXML
    protected TextField passwordField;

    @Setter
    @Getter
    @FXML
    protected JFXComboBox langCombobox;

    protected int selectedLang;
}
