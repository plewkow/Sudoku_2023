package model.exception;

public class SudokuBoardReadException extends DaoException {
    public SudokuBoardReadException(String message) {
        super(message);
    }
}