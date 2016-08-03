package com.word_trainer.services;

import com.word_trainer.dto.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Service odpowiedzialny za pobieranie i zapisywanie listy slow.
 */
@Component
@Slf4j
public class WordsService {

    private final static String csvGermanWordsFile = "/data/germanIrregularList.csv";

    @Getter
    protected ObservableList<Word> wordsList = FXCollections.observableArrayList();

    public WordsService() {
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

        log.info("Loaded wordsTableView: {}", wordsList.size());
    }

}
