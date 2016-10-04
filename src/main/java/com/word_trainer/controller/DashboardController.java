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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.Notifications;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DashboardController extends DashboardModel {

    @FXML
    @SuppressWarnings("unchecked")
    public void initialize() {
        wordsList = wordsService.getWordsList();
        wordsTableView.setItems(wordsList);

        actionColumn.setCellValueFactory(a ->
                new SimpleBooleanProperty(((TableColumn.CellDataFeatures<Word, Boolean>) a).getValue() != null)
        );

        actionColumn.setCellFactory(a -> new ButtonCell(this::deleteItem, "delete"));
    }

    private void deleteItem(final ActionEvent event) {
        final int rowIndex = ((TableRow) ((Button) event.getSource()).getParent().getParent()).getIndex();
        log.info("delete item, event: {}", rowIndex);

        Notifications.create()
                .title("Item deleted")
                .text("You did remove word: " + wordsList.get(rowIndex).getForeignWord())
                .showWarning();

        wordsList.remove(rowIndex);
    }

    public void addWord() {
        log.info("add new word: {} - {}", foreignWordField.getText(), meaningField.getText());
        boolean isDone = false;
        if (foreignWordField.getText().isEmpty() || meaningField.getText().isEmpty() || wordsList.contains(foreignWordField.getText())) {
            log.warn("cannot add word");
        }
        else {
            final Word newWord = new Word();
            newWord.setMeaning(meaningField.getText());
            newWord.setForeignWord(foreignWordField.getText());
            newWord.setId(wordsTableView.getItems().size() + 1);
            wordsTableView.getItems().add(newWord);
            isDone = true;
        }

        String messageKey = isDone ? "dashboard.add.ok" : "dashboard.add.error";
        Notifications.create()
                .title( getMessageForKey("dashboard.add.title"))
                .text(getMessageForKey(messageKey ))
                .showWarning();

    }

    public void startLearning(final ActionEvent event) throws IOException {
        final StageSwitch stageSwitch = new StageSwitch("/scenes/learn/learn.fxml");
        stageSwitch.load((Stage) ((Node) event.getSource()).getScene().getWindow());
        learnController.setWordsList(wordsList);

        final LearnMode selectedLearnMode =
                learningModeChoice.getSelectionModel().getSelectedIndex() == 0 ? LearnMode.FIRST_FOREIGN : LearnMode.FIRST_MEANING;
        learnController.setLearnMode(selectedLearnMode);
        learnController.init();
    }

}
