package com.lolapp.model;

import com.lolapp.dto.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public abstract class DashboardModel {

    @FXML
    protected TableView<Word> words;

    @FXML
    protected TextField foreignWordField;

    @FXML
    protected TextField meaningField;

    protected final String csvGermanWordsFile = "/data/germanIrregularList.csv";

    protected ObservableList<Word> wordsList = FXCollections.observableArrayList();

}
