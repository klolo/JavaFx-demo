package com.word_trainer.controller;

import com.word_trainer.application.StageSwitch;
import com.word_trainer.component.ButtonCell;
import com.word_trainer.dto.Word;
import com.word_trainer.learn.LearnMode;
import com.word_trainer.model.DashboardModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DashboardController extends DashboardModel {


    @FXML
    @SuppressWarnings("unchecked")
    public void initialize() {
        wordsTableView.setItems(wordsService.getWordsList());

        actionColumn.setCellValueFactory(a ->
                new SimpleBooleanProperty(((TableColumn.CellDataFeatures<Word, Boolean>) a).getValue() != null)
        );

        actionColumn.setCellFactory(a -> new ButtonCell(this::deleteItem, "delete"));
    }

    private void deleteItem() {
        log.info("delete item from list");
    }

    public void addWord() {
        log.info("add new word: {} - {}", foreignWordField.getText(), meaningField.getText());
        if (foreignWordField.getText().isEmpty() || meaningField.getText().isEmpty()) {
            return;
        }

        final Word newWord = new Word();
        newWord.setMeaning(meaningField.getText());
        newWord.setForeignWord(foreignWordField.getText());
        newWord.setId(wordsTableView.getItems().size() + 1);
        wordsTableView.getItems().add(newWord);
    }

    public void startLearning(final ActionEvent event) throws IOException {
        final StageSwitch stageSwitch = new StageSwitch("/scenes/learn/learn.fxml");
        stageSwitch.load((Stage) ((Node) event.getSource()).getScene().getWindow());
        learnController.setWordsList(wordsService.getWordsList());
        learnController.setLearnMode(LearnMode.FIRST_MEANING);
        learnController.init();
    }

}
