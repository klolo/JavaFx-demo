package com.word_trainer.model;

import com.word_trainer.controller.LearnController;
import com.word_trainer.dto.Word;
import com.word_trainer.services.WordsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class DashboardModel {

    @Autowired
    protected LearnController learnController;

    @Autowired
    protected WordsService wordsService;

    @FXML
    protected TableView<Word> wordsTableView;

    @FXML
    protected TextField foreignWordField;

    @FXML
    protected TextField meaningField;

    @FXML
    protected TableColumn actionColumn;

}
