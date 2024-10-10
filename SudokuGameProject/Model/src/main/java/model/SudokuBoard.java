
package model;

        import org.apache.commons.lang3.builder.EqualsBuilder;
        import org.apache.commons.lang3.builder.HashCodeBuilder;
        import org.apache.commons.lang3.builder.ToStringBuilder;
        import org.apache.commons.lang3.builder.ToStringStyle;

        import java.io.Serializable;
        import java.util.Arrays;
        import java.util.List;

public class SudokuBoard implements Serializable, Cloneable {
    public static final int SIZE = 9;
    private SudokuField[][] board = new SudokuField[SIZE][SIZE];
    private SudokuSolver solver;


    public SudokuBoard(SudokuSolver solver) {
        this.solver = solver;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = new SudokuField(0);
            }
        }
    }

    private SudokuBoard(SudokuBoard original) {
        this.solver = original.solver;
        this.board = new SudokuField[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = new SudokuField(original.board[i][j].getFieldValue());
            }
        }
    }

    public void set(int i, int j, int value) {
        int old = board[i][j].getFieldValue();
        board[i][j].setFieldValue(value);
        if (!this.checkBoard()) {
            board[i][j].setFieldValue(old);
        }
    }

    public int get(int i,int j) {
        return board[i][j].getFieldValue();

    }

    private boolean checkBoard() {

        for (int x = 0;x < SIZE; x++) {
            if (!getRow(x).verify() || !getColumn(x).verify()) {
                return false;
            }
        }

        for (int i = 0;i < SIZE; i += 3) {
            for (int j = 0;j < SIZE;j += 3) {
                if (!getBox(i,j).verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    public SudokuRow getRow(int y) {
        SudokuField[] rowArray = new SudokuField[SIZE];
        System.arraycopy(board[y], 0, rowArray, 0, SIZE);
        List<SudokuField> rowFields = Arrays.asList(rowArray);

        return new SudokuRow(rowFields);
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[] colArray = new SudokuField[SIZE];
        for (int i = 0; i < SIZE; i++) {
            colArray[i] = board[i][x];
        }
        List<SudokuField> colFields = Arrays.asList(colArray);

        return new SudokuColumn(colFields);
    }

    public SudokuBox getBox(int x, int y) {
        SudokuField[] boxArray = new SudokuField[SIZE];
        int startX = (int) (x / Math.sqrt(SIZE)) * (int) Math.sqrt(SIZE);
        int startY = (int) (x / Math.sqrt(SIZE)) * (int) Math.sqrt(SIZE);
        int index = 0;
        for (int i = startX; i < startX + Math.sqrt(SIZE); i++) {
            for (int j = startY; j < startY + Math.sqrt(SIZE); j++) {
                boxArray[index++] = board[i][j];
            }
        }
        List<SudokuField> boxFields = Arrays.asList(boxArray);
        return new SudokuBox(boxFields);
    }

    public void solveGame() {
        solver.solve(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("board", board).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(29, 23)
                .append(solver)
                .append(board)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder().append(solver,that.solver).append(board, that.board).isEquals();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new SudokuBoard(this);
    }

    public SudokuField getField(int i, int j) {
        return board[i][j];
    }
}