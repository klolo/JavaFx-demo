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

    final static float KNOW_LEVEL = 0.90f;

    public void increaseCorrectAnswersAmount() {
        correctAnswers++;
        changeLevelOfFamiliarity();
    }

    public void increaseIncorrectAnswersAmount() {
        incorrectAnswers++;
        changeLevelOfFamiliarity();
    }

    private void changeLevelOfFamiliarity() {
        if (incorrectAnswers != 0 && correctAnswers != 0) {
            levelOfFamiliarity = incorrectAnswers / correctAnswers;
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
