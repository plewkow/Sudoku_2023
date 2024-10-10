package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BacktrackingSudokuSolverTest {

    @Test
    void fillBoardDifferent() {
        BacktrackingSudokuSolver solverTest1 = new BacktrackingSudokuSolver();
        SudokuBoard boardTest1 = new SudokuBoard(solverTest1);
        SudokuBoard boardTest2 = new SudokuBoard(solverTest1);
        boardTest1.solveGame();
        boardTest2.solveGame();

        boolean boardEqual = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; i < 9; i++) {
                if (boardTest1.get(i, j) != boardTest2.get(i, j)) {
                    boardEqual = false;
                    break;
                }

            }
        }
        assertFalse(boardEqual);
    }

    @Test
    void fillBoardValid() {
        BacktrackingSudokuSolver solverTest1 = new BacktrackingSudokuSolver();
        SudokuBoard boardTest1 = new SudokuBoard(solverTest1);
        boardTest1.solveGame();

        boolean boardValid = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //rzad
                for (int k = 0; k < 9; k++) {
                    if (boardTest1.get(i, j) == boardTest1.get(i, k) && k != j) {
                        boardValid = false;
                    }
                }
                //kolumna
                for (int m = 0; m < 9; m++) {
                    if (boardTest1.get(i, j) == boardTest1.get(m, j) && m != i) {
                        boardValid = false;
                    }
                }
                //kwadrat
                int a = i / 3;
                int b = j / 3;

                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        if (boardTest1.get(3 * a + x, 3 * b + y) == boardTest1.get(i, j)
                                && (3 * a + x) != i && (3 * b + y) != j) {
                            boardValid = false;
                        }
                    }
                }
            }
        }
        assertTrue(boardValid);
    }
    @Test
    void toStringTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolverTest1 = new BacktrackingSudokuSolver();
        Assertions.assertEquals("BacktrackingSudokuSolver[]", backtrackingSudokuSolverTest1.toString());
    }
    @Test
    void EqualsTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolverTest1 = new BacktrackingSudokuSolver();
        BacktrackingSudokuSolver backtrackingSudokuSolverTest2 = new BacktrackingSudokuSolver();
        Assertions.assertTrue(backtrackingSudokuSolverTest1.equals(backtrackingSudokuSolverTest2));
        Assertions.assertTrue(backtrackingSudokuSolverTest1.equals(backtrackingSudokuSolverTest1));
        Assertions.assertFalse(backtrackingSudokuSolverTest1.equals(null));
        Assertions.assertFalse(backtrackingSudokuSolverTest1.equals(new Object()));
    }
    @Test
    void hashCodeTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolverTest1 = new BacktrackingSudokuSolver();
        BacktrackingSudokuSolver backtrackingSudokuSolverTest2 = new BacktrackingSudokuSolver();
        Assertions.assertTrue(backtrackingSudokuSolverTest1.equals(backtrackingSudokuSolverTest2));
        Assertions.assertEquals(backtrackingSudokuSolverTest1.hashCode(), backtrackingSudokuSolverTest2.hashCode());

    }

}