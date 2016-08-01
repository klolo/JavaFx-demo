package com.lolapp.controller;


import com.lolapp.dto.Word;
import com.lolapp.model.LearnModel;
import com.lolapp.util.LearnEntity;
import com.lolapp.util.LearnMode;
import com.lolapp.util.StageSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.stream.IntStream;

@Slf4j
public class LearnController extends LearnModel {


    @FXML
    public void initialize() {
        setRadioVisibility(false);
    }

    public void init() {
        IntStream.range(0, WORDS_IN_LEARNING_SET).forEach(
                i -> {
                    final LearnEntity learnEntity = LearnEntity.fromWord(wordsList.get(getRandomInt(0, wordsList.size())));
                    log.info("adding: {}", learnEntity);
                    learnList.add(learnEntity);
                }
        );

        setWordLabelOnView();
    }

    private void setRadioVisibility(final boolean visibility) {
        knowRadio.setVisible(visibility);
        laterRadio.setVisible(visibility);
        notKnowRadio.setVisible(visibility);
    }

    private void setWordLabelOnView() {
        final String firstWordLabel = getFirstWordLabel(learnList.get(wordIndex).getWord());
        currentWord.textProperty().setValue(firstWordLabel);
        currentWord.setVisible(true);
    }

    private void setSecondWordLabelOnView() {
        final String firstWordLabel = getSeconsWordLabel(learnList.get(wordIndex).getWord());
        currentWordReverse.textProperty().setValue(firstWordLabel);
        currentWordReverse.setVisible(true);
    }


    private String getFirstWordLabel(final Word word) {
        return learnMode.equals(LearnMode.FIRST_FOREIGN) ? word.getForeignWord() : word.getMeaning();
    }

    private String getSeconsWordLabel(final Word word) {
        return learnMode.equals(LearnMode.FIRST_FOREIGN) ? word.getMeaning() : word.getForeignWord();
    }

    public void goToDashboard(final ActionEvent event) throws IOException {
        final StageSwitch stageSwitch = new StageSwitch("/scenes/dashboard/dashboard.fxml");
        stageSwitch.load((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public void next(final ActionEvent event) throws IOException {
        final double progress = (double) wordIndex / (double) WORDS_IN_LEARNING_SET;
        log.info("Progress: {}, wordsIndex: {}", progress, wordIndex);
        learnProgress.setProgress(progress);
        wordIndex++;
        setWordLabelOnView();
    }

    public void show(final ActionEvent event) throws IOException {
        setRadioVisibility(true);
        setSecondWordLabelOnView();
    }

    private void onRadioEvent() {
        setRadioVisibility(false);
        currentWordReverse.setVisible(false);
    }

    public void onKnowRadioClick() {
        onRadioEvent();
        knowRadio.setSelected(false);
    }

    public void onNotKnowRadioClick() {
        onRadioEvent();
        notKnowRadio.setSelected(false);
    }

    public void onLaterRadioClick() {
        onRadioEvent();
        laterRadio.setSelected(false);
    }

    public static int getRandomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }
}
