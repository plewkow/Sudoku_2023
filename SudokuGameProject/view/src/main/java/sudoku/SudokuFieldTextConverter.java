package sudoku;

import javafx.util.converter.IntegerStringConverter;

import java.util.Objects;

public class SudokuFieldTextConverter extends IntegerStringConverter {

    @Override
    public String toString(Integer value) {
        if (value != 0) {
            return super.toString(value);
        } else {
            return "";
        }
    }

    @Override
    public Integer fromString(String s) {
        if (Objects.equals(s, "")) {
            return 0;
        } else {
            return super.fromString(s);
        }
    }
}