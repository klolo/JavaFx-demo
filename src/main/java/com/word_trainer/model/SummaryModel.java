package com.word_trainer.model;

import com.word_trainer.controller.LearnController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kamil on 04.08.16.
 */
public abstract class SummaryModel {

    @Autowired
    protected LearnController learnController;
}
