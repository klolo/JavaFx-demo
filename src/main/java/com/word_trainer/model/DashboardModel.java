package com.word_trainer.model;

import com.jfoenix.controls.JFXComboBox;
import com.word_trainer.controller.BaseController;
import com.word_trainer.controller.LearnController;
import com.word_trainer.dto.Word;
import com.word_trainer.services.WordsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class DashboardModel extends BaseController {

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

    @FXML
    protected JFXComboBox learningModeChoice;

    @FXML
    protected BorderPane root;

    protected ObservableList<Word> wordsList;

}
