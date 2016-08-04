package com.word_trainer.dto;

import lombok.Data;

@Data
public class Word {

    public static final String csvWordsSeparator = "\\|";

    private int id;

    private String meaning;

    private String foreignWord;

    private float levelOfFamiliarity;

    private int incorrectAnswers;

    private int correctAnswers;

    private boolean isInSaveFile = false;

    final static int MINIMAL_CORRECT_ANSWER = 10;

    final static float KNOW_LEVEL = 0.90f;

    public String getProgressIndicator() {
        return correctAnswers + "/" + incorrectAnswers;
    }

    public String getPercentProgress() {
        String percentProgress = String.valueOf(levelOfFamiliarity * 100);
        return percentProgress.substring(0, percentProgress.indexOf(".")) + " %";
    }


    public void increaseCorrectAnswersAmount() {
        correctAnswers++;
        changeLevelOfFamiliarity();
    }

    public void increaseIncorrectAnswersAmount() {
        incorrectAnswers++;
        changeLevelOfFamiliarity();
    }

    public void changeLevelOfFamiliarity() {
        if (correctAnswers < MINIMAL_CORRECT_ANSWER) {
            return;
        }

        if (incorrectAnswers != 0 && correctAnswers != 0) {
            levelOfFamiliarity = (float) incorrectAnswers / (float) correctAnswers;
        }

        if (levelOfFamiliarity > KNOW_LEVEL) {
            levelOfFamiliarity = 1.0f;
        }
    }

    public static Word fromString(final String data) {
        final Word result = new Word();
        String[] fields = data.split(csvWordsSeparator);
        result.setForeignWord(fields[0] + ", " + fields[1] + ", " + fields[2] + "");
        result.setLevelOfFamiliarity(0.0f);
        result.setMeaning(fields[3]);
        return result;
    }

}
