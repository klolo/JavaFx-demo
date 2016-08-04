package com.word_trainer.controller;

import com.word_trainer.application.StageSwitch;
import com.word_trainer.model.SummaryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Kontroler strony z podsumowaniem nauki.
 */
@Component
@Slf4j
public class SummaryController extends SummaryModel {

    @FXML
    public void initialize() {

    }

    public void goToDashboard(final ActionEvent event) throws IOException {
        final StageSwitch stageSwitch = new StageSwitch("/scenes/dashboard/dashboard.fxml");
        stageSwitch.load((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

}
