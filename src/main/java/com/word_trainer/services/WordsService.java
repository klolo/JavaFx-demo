package com.word_trainer.services;

import com.word_trainer.dto.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.stream.Collectors;

/**
 * Service odpowiedzialny za pobieranie i zapisywanie listy slow.
 */
@Component
@Lazy
@Slf4j
public class WordsService {
    @Autowired
    private AuthorizationService authorizationService;

    private final static String csvGermanWordsFile = "/data/germanIrregularList.csv";

    private final static String SAVE_FILE_LOCALIZATION = "./save_";

    protected ObservableList<Word> wordsList = null;

    public ObservableList<Word> getWordsList() {
        if (wordsList != null) {
            return wordsList;
        }

        try {
            wordsList = FXCollections.observableArrayList();
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
            processLoadedWords();
            return wordsList;
        }
        catch (final Exception e) {
            log.error("Error while ", e);
            return null;
        }
    }

    private void processLoadedWords() throws IOException {
        final File saveFile = new File(SAVE_FILE_LOCALIZATION + authorizationService.getUserId());

        if (saveFile.exists()) {
            log.info("reading save file");

            final FileReader fw = new FileReader(saveFile.getAbsoluteFile());
            try (BufferedReader reader = new BufferedReader(fw)) {
                reader.lines()
                        .map(String::valueOf)
                        .forEach(this::processInputLine);
            }
            catch (final IOException e) {
                log.error("Error in processing file", e);
            }

            log.info("remove not exists in file words");
            wordsList.parallelStream()
                    .filter(w -> w != null)
                    .filter(w -> !w.isInSaveFile())
                    .forEach(w -> wordsList.remove(w));
        }
    }

    private void processInputLine(final String line) {
        wordsList.parallelStream()
                .filter(w -> isTheSameWord(line, w))
                .forEach(w -> loadWordFromSaveFile(line, w));
    }

    private void loadWordFromSaveFile(final String line, final Word word) {
        final String[] args = line.split("\\|");
        word.setCorrectAnswers(Integer.parseInt(args[1]));
        word.setIncorrectAnswers(Integer.parseInt(args[2]));
        word.setLevelOfFamiliarity(Float.parseFloat(args[3]));
        word.changeLevelOfFamiliarity();
        word.setInSaveFile(true);
    }

    private boolean isTheSameWord(final String line, final Word word) {
        final String[] args = line.split("\\|");
        if (args[0].equals(authorizationService.getTextHash(word.getForeignWord()))) {
            return true;
        }
        return false;
    }

    private String changeWordToSaveFile(final Word word) {
        return authorizationService.getTextHash(word.getForeignWord())
                + "|" + word.getCorrectAnswers()
                + "|" + word.getIncorrectAnswers()
                + "|" + word.getLevelOfFamiliarity();
    }

    public void saveCurrentWords() {
        log.info("save word list");

        try {
            final File file = new File(SAVE_FILE_LOCALIZATION + authorizationService.getUserId());
            if (!file.exists()) {
                file.createNewFile();
            }

            final FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                wordsList.parallelStream().map(this::changeWordToSaveFile).collect(Collectors.toList()).forEach(line -> {
                    try {
                        bw.write(line + "\n");
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        }
        catch (final Exception e) {
            log.error("Error while ", e);
        }
    }
}
