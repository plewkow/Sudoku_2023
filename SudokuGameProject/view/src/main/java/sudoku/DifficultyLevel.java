package sudoku;

public enum DifficultyLevel {

    easy(20),

    medium(30),

    hard(40);

    private final int removedCells;

    DifficultyLevel(int removedCells) {
        this.removedCells = removedCells;
    }

    public int numberOfRemovedCells() {
        return removedCells;
    }
}
