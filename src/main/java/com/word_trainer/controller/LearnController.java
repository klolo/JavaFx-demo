package com.word_trainer.controller;


import com.word_trainer.application.StageSwitch;
import com.word_trainer.dto.Word;
import com.word_trainer.learn.LearnEntity;
import com.word_trainer.learn.LearnMode;
import com.word_trainer.model.LearnModel;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.PopOver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.stream.IntStream;

@Slf4j
@Component
public class LearnController extends LearnModel {

    @FXML
    public void initialize() {
        setRadioVisibility(false);
        currentWordReverse.setVisible(false);

        timeTimer = new AnimationTimer() {
            int tics = 0;

            @Override
            public void handle(final long now) {
                tics++;
                if (tics > 60) {
                    seconds++;
                    tics = 0;

                    if (seconds % 60 == 0) {
                        minutes++;
                        seconds = 0;
                    }

                    timer.setText(
                            changeIntToTwoCharacterString(minutes) + ":" +
                                    changeIntToTwoCharacterString(seconds));
                }

            }
        };

        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT1: {
                    if (currentWordReverse.isVisible()) {
                        onKnowRadioClick();
                    }
                    break;
                }
                case DIGIT2: {
                    if (currentWordReverse.isVisible()) {
                        onNotKnowRadioClick();
                    }
                    break;
                }
                case DIGIT3: {
                    if (currentWordReverse.isVisible()) {
                        onLaterRadioClick();
                    }
                    break;
                }
                case ENTER: {
                    show();
                    break;
                }
            }
        });

        timeTimer.start();
    }

    private String changeIntToTwoCharacterString(int value) {
        return value > 9 ? String.valueOf(value) : "0" + value;
    }

    public void init() {
        correct = 0;
        incorrect = 0;
        learnList = new LinkedList<>();
        IntStream.range(0, WORDS_IN_LEARNING_SET).forEach(
                i -> {
                    final LearnEntity learnEntity = LearnEntity.fromWord(wordsList.get(getRandomInt(0, wordsList.size())));
                    log.info("adding: {}", learnEntity);
                    learnList.add(learnEntity);
                }
        );

        scoreLeft = (double) WORDS_IN_LEARNING_SET * (double) WORD_CORRECT_ANSWER_MIN;
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
        VBox confirmPopupContent = new VBox();
        confirmPopupContent.getChildren().add(new Label("Czy chcesz napewno wyjsc?"));
        confirmPopupContent.setPadding(new Insets(10, 10, 10, 10));

        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(10, 10, 10, 10));

        createConfirmButton(buttonBox);
        createFreeSpace(buttonBox);
        createRejectButton(confirmPopupContent, buttonBox);

        closeConfirmPopup = new PopOver(confirmPopupContent);
        closeConfirmPopup.show(back);
    }

    private void createFreeSpace(final HBox buttonBox) {
        Region freeSpace = new Region();
        freeSpace.setMinWidth(20);
        buttonBox.getChildren().add(freeSpace);
    }

    private void createRejectButton(final VBox confirmPopupContent, final HBox buttonBox) {
        final Button rejectButton = new Button("Nie");
        rejectButton.getStyleClass().add("button-raised");
        rejectButton.setOnMouseClicked(event1 -> {
            LOGGER.info("popup select: yes");
            closeConfirmPopup.hide();
        });
        rejectButton.setMaxWidth(80);
        buttonBox.getChildren().add(rejectButton);
        confirmPopupContent.getChildren().add(buttonBox);
    }

    private void createConfirmButton(final HBox buttonBox) {
        final Button confirmButton = new Button("Tak");
        confirmButton.getStyleClass().add("button-raised");
        confirmButton.setMaxWidth(80);
        confirmButton.setOnMouseClicked(event1 -> {
            LOGGER.info("popup select: yes");
            closeConfirmPopup.hide();
            wordsService.saveCurrentWords();
            final StageSwitch stageSwitch = new StageSwitch("/scenes/dashboard/dashboard.fxml");
            try {
                stageSwitch.load((Stage) ((Node) laterRadio).getScene().getWindow());
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        buttonBox.getChildren().add(confirmButton);
    }

    public void next() throws IOException {
        currentWordReverse.setVisible(false);
        setRadioVisibility(false);

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
                final StageSwitch stageSwitch = new StageSwitch("/scenes/summary/summary.fxml");
                stageSwitch.load((Stage) ((Node) currentWord).getScene().getWindow());
                break;
            }
        }

        setWordLabelOnView();
    }

    private void changeProgress() {
        final double maxScoreLevel = (double) WORDS_IN_LEARNING_SET * (double) WORD_CORRECT_ANSWER_MIN;
        final double progress = (maxScoreLevel - scoreLeft) / maxScoreLevel;
        log.info("Progress: {}, wordsIndex: {}", progress, wordIndex);
        learnProgress.setProgress(progress);
    }

    public void show() {
        setRadioVisibility(true);
        setSecondWordLabelOnView();
    }

    private void onRadioEvent() {
        setRadioVisibility(false);
        currentWordReverse.setVisible(false);
        changeProgress();
        try {
            next();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void onKnowRadioClick() {
        log.info("I know");
        knowRadio.setSelected(false);
        scoreLeft -= 1;
        getCurrentWord().increaseCorrectAnswersAmount();
        onRadioEvent();
        correct++;
    }

    public void onNotKnowRadioClick() {
        log.info("I do not know");
        notKnowRadio.setSelected(false);
        getCurrentWord().increaseIncorrectAnswersAmount();
        onRadioEvent();
        incorrect++;
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
