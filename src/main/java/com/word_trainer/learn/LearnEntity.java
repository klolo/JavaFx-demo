package com.word_trainer.learn;

import com.word_trainer.dto.Word;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@ToString
public class LearnEntity {

    private Word word;

    private boolean isDone;

    public static LearnEntity fromWord(final Word word) {
        final LearnEntity learnEntity = new LearnEntity();
        learnEntity.setWord(word);
        return learnEntity;
    }
}
