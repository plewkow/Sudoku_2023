package sudoku;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class SudokuFieldTextFormatter implements UnaryOperator<TextFormatter.Change> {
    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        String text = change.getControlNewText();

        if (text.matches("[1-9]")) {
            return change;
        } else if (text.equals(" ")) {
            change.setAnchor(0);
            change.setText("");
            return change;
        } else {
            return null;
        }
    }
}