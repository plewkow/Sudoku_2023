package model;

public class SudokuBoardPrototypeFactory {
    private static final SudokuBoard sudokuBoardPrototype = new SudokuBoard(new BacktrackingSudokuSolver());

    public SudokuBoard createPrototype() throws CloneNotSupportedException {
        return (SudokuBoard) sudokuBoardPrototype.clone();
    }
}
