package com.word_trainer.component;

import com.jfoenix.controls.JFXButton;
import com.word_trainer.dto.Word;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * Klasa uzywana przy dodanwaniu buttonow do komorek TableView
 */
@Slf4j
public class ButtonCell extends TableCell<Word, Boolean> {

    private JFXButton button;

    public ButtonCell(final Consumer<ActionEvent> callbackConsumer, final String buttonLabel) {
        button = new JFXButton(buttonLabel);
        button.getStyleClass().add("button-raised");
        button.setOnAction(callbackConsumer::accept);
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void updateItem(final Boolean t, final boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            setGraphic(button);
        }
    }
}
