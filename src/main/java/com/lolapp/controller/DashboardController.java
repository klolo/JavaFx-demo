package com.lolapp.controller;

import com.lolapp.dto.Word;
import com.lolapp.model.DashboardModel;
import com.lolapp.util.LearnMode;
import com.lolapp.util.StageSwitch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class DashboardController extends DashboardModel {

    @FXML
    public void initialize() {
        log.info("initialize word list");
        initWordList();
        words.setItems(wordsList);
    }

    private void initWordList() {
        final InputStreamReader inputStreamReader = new InputStreamReader(this.getClass().getResourceAsStream(csvGermanWordsFile));

        try (BufferedReader br = new BufferedReader(inputStreamReader)) {
            String line;
            int index = 0;

            while ((line = br.readLine()) != null) {
                final Word w = Word.fromString(line);
                w.setId(++index);
                wordsList.add(w);
            }
        }
        catch (final IOException e) {
            log.error("Cannot read file", e);
        }

        log.info("Loaded words: {}", wordsList.size());
    }

    public void addWord() {
        log.info("add new word: {} - {}", foreignWordField.getText(), meaningField.getText());
        if (foreignWordField.getText().isEmpty() || meaningField.getText().isEmpty()) {
            return;
        }

        final Word newWord = new Word();
        newWord.setMeaning(meaningField.getText());
        newWord.setForeignWord(foreignWordField.getText());
        newWord.setId(words.getItems().size() + 1);
        words.getItems().add(newWord);
    }

    public void startLearning(final ActionEvent event) throws IOException {
        final StageSwitch stageSwitch = new StageSwitch("/scenes/learn/learn.fxml");
        stageSwitch.load((Stage) ((Node) event.getSource()).getScene().getWindow());

        final LearnController learnController = (LearnController) stageSwitch.getController();
        learnController.setWordsList(wordsList);
        learnController.setLearnMode(LearnMode.FIRST_MEANING);
        learnController.init();
    }

}
