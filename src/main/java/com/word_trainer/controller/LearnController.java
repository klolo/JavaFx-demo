package com.word_trainer.controller;


import com.word_trainer.dto.Word;
import com.word_trainer.model.LearnModel;
import com.word_trainer.learn.LearnEntity;
import com.word_trainer.learn.LearnMode;
import com.word_trainer.application.StageSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.IntStream;

@Slf4j
@Component
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

        scoreLeft = (WORDS_IN_LEARNING_SET * WORD_CORRECT_ANSWER_MIN);
        setWordLabelOnView();
    }

    private void setRadioVisibility(final boolean visibility) {
        knowRadio.setVisible(visibility);
        laterRadio.setVisible(visibility);
        notKnowRadio.setVisible(visibility);
    }

    private void setWordLabelOnView() {
        final String firstWordLabel = getFirstWordLabel(getCurrentWord());
        currentWord.textProperty().setValue(firstWordLabel);
        currentWord.setVisible(true);
    }

    private void setSecondWordLabelOnView() {
        final String firstWordLabel = getSeconsWordLabel(getCurrentWord());
        currentWordReverse.textProperty().setValue(firstWordLabel);
        currentWordReverse.setVisible(true);
    }

    private Word getCurrentWord() {
        return learnList.get(wordIndex).getWord();
    }

    private String getFirstWordLabel(final Word word) {
        return learnMode.equals(LearnMode.FIRST_FOREIGN) ? word.getForeignWord() : word.getMeaning();
    }

    private String getSeconsWordLabel(final Word word) {
        return learnMode.equals(LearnMode.FIRST_FOREIGN) ? word.getMeaning() : word.getForeignWord();
    }

    public void goToDashboard(final ActionEvent event) throws IOException {
        wordsService.saveCurrentWords();
        final StageSwitch stageSwitch = new StageSwitch("/scenes/dashboard/dashboard.fxml");
        stageSwitch.load((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public void next() {
        int tryCount = 0;
        while (true) {
            int tmpWordIndex = wordIndex + 1;
            if (tmpWordIndex >= learnList.size()) {
                tmpWordIndex = 0;
            }

            if (learnList.get(tmpWordIndex).getWord().getCorrectAnswers() <= WORD_CORRECT_ANSWER_MIN) {
                wordIndex = tmpWordIndex;
                break;
            }
            else {
                tryCount++;
            }

            if (tryCount == learnList.size()) {
                log.info("Learing is done");
                break;
            }
        }

        setWordLabelOnView();
    }

    private void changeProgress() {
        final double maxScoreLevel = (double) WORDS_IN_LEARNING_SET * (double) WORD_CORRECT_ANSWER_MIN;
        final double progress = (maxScoreLevel - (double)scoreLeft) / maxScoreLevel;
        log.info("Progress: {}, wordsIndex: {}", progress, wordIndex);
        learnProgress.setProgress(progress);
    }

    public void show() throws IOException {
        setRadioVisibility(true);
        setSecondWordLabelOnView();
    }

    private void onRadioEvent() {
        setRadioVisibility(false);
        currentWordReverse.setVisible(false);
        changeProgress();
        next();
    }

    public void onKnowRadioClick() {
        log.info("I know");
        knowRadio.setSelected(false);
        scoreLeft -= 1;
        getCurrentWord().increaseCorrectAnswersAmount();
        onRadioEvent();
    }

    public void onNotKnowRadioClick() {
        log.info("I do not know");
        notKnowRadio.setSelected(false);
        getCurrentWord().increaseIncorrectAnswersAmount();
        onRadioEvent();
    }

    public void onLaterRadioClick() {
        log.info("I need some time");
        onRadioEvent();
        laterRadio.setSelected(false);
    }

    public static int getRandomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

}
