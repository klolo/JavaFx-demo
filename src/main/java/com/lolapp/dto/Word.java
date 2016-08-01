package com.lolapp.dto;

import lombok.Data;

@Data
public class Word {

    public static final String csvWordsSeparator = "\\|";

    private int id;

    private String meaning;

    private String foreignWord;

    private float levelOfFamiliarity;

    public static Word fromString(final String data) {
        final Word result = new Word();
        String[] fields = data.split(csvWordsSeparator);
        result.setForeignWord(fields[0] + ", " + fields[1] + ", " + fields[2] + "");
        result.setLevelOfFamiliarity(0.0f);
        result.setMeaning(fields[3]);
        return result;
    }

}
