package model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

public class BacktrackingSudokuSolver implements SudokuSolver {

    private SudokuBoard boardReference;

    @Override
    public void solve(SudokuBoard board) {
        boardReference = board;
        fillBoard();
    }

    private void fillBoard() {
        for (int i = 0;i < SudokuBoard.SIZE;i++) {
            for (int j = 0;j < SudokuBoard.SIZE;j++) {
                boardReference.set(i,j,0);
            }
        }
        fillSquare(0,0);

    }

    private boolean fillSquare(int i, int j) {
        if (i == SudokuBoard.SIZE) {
            return true;
        }
        if (j == SudokuBoard.SIZE) {
            return fillSquare(i + 1, 0);
        }

        List<Integer> num = random();


        for (int k = num.size() - 1;k >= 0;k--) {

            if (isColumnGood(j,num.get(k)) && isRowGood(i,num.get(k)) && isSquareGood(i,j,num.get(k))) {
                boardReference.set(i,j,num.get(k));
                if (fillSquare(i, j + 1)) {
                    return true;
                }
            }

        }

        boardReference.set(i,j,0);
        return false;
    }

    private  List<Integer> random() {

        List<Integer> randomNumbers = new ArrayList<>() {{
            for (int i = 0; i < SudokuBoard.SIZE; i++) {
                add(i + 1);
            }
        }};
        Collections.shuffle(randomNumbers);

        return randomNumbers;
    }

    private boolean isRowGood(int i, int value) {
        //rzad
        for (int k = 0; k < SudokuBoard.SIZE; k++) {
            if (value == boardReference.get(i,k)) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnGood(int j,int value) {
        for (int m = 0; m < SudokuBoard.SIZE; m++) {
            if (value == boardReference.get(m,j)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSquareGood(int i, int j,int value) {
        //maly kwadrat
        int x = i / 3;
        int y = j / 3;
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                if (boardReference.get(3 * x + a,3 * y + b) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(29, 23)
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



        return new EqualsBuilder().isEquals();
    }

}