package com.word_trainer.component;

import com.word_trainer.dto.Word;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import lombok.extern.slf4j.Slf4j;

/**
 * Klasa uzywana przy dodanwaniu buttonow do komorek TableView
 */
@Slf4j
public class ButtonCell extends TableCell<Word, Boolean> {

    private Button button;

    public ButtonCell(final Runnable callbackConsumer, final String buttonLabel) {
        button = new Button(buttonLabel);
        button.setOnAction(e -> callbackConsumer.run());
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
