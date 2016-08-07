package com.word_trainer.model;

import com.word_trainer.controller.LearnController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Model dla widoku podsumowania.
 */
public abstract class SummaryModel {

    @Autowired
    protected LearnController learnController;

    @FXML
    protected Label correctField;

    @FXML
    protected Label incorrectField;

    @FXML
    protected Label timeField;
}
