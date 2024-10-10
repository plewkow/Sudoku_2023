package model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {


    @Test

    void setTest() {
        BacktrackingSudokuSolver solverTest1 = new BacktrackingSudokuSolver();
        SudokuBoard boardTest1 = new SudokuBoard(solverTest1);
        boardTest1.set(1,1,5);
        boardTest1.set(1,2,5);
        boardTest1.set(2,1,5);
        assert(boardTest1.get(1,2)!=5);
        assert(boardTest1.get(2,1)!=5);
        boardTest1.set(2,2,5);
        assert(boardTest1.get(2,2)!=5);
}
    @Test
    void getRowColBoxTest() {
        BacktrackingSudokuSolver solverTest1 = new BacktrackingSudokuSolver();
        SudokuBoard boardTest1 = new SudokuBoard(solverTest1);
        SudokuRow row= boardTest1.getRow(1);
        SudokuColumn col= boardTest1.getColumn(1);
        SudokuBox box= boardTest1.getBox(1,2);


    }
    @Test
    void toStringTest() {
        SudokuSolver testSolver = new BacktrackingSudokuSolver();
        SudokuBoard testBoard = new SudokuBoard(testSolver);
        testBoard.solveGame();
        String expectedString = "SudokuBoard[board={{";
        for (int i = 0; i < testBoard.SIZE; i++ ) {
            for (int j = 0; j < testBoard.SIZE; j++) {
                expectedString += "SudokuField[pole=" + testBoard.get(i,j) + "]";

                if(j < testBoard.SIZE - 1) {
                    expectedString +=",";
                }
            }
            if (i != testBoard.SIZE -1) {
                expectedString +="},{";
            }
        }
        expectedString +="}}]";
        assertEquals(expectedString,testBoard.toString());
    }
    @Test
    void hashCodeTest() {
        SudokuSolver testSolver = new BacktrackingSudokuSolver();
        SudokuBoard testBoard1 = new SudokuBoard(testSolver);
        SudokuBoard testBoard2 = new SudokuBoard(testSolver);
        SudokuBoard testBoard3 = new SudokuBoard(testSolver);
        testBoard1.set(1,1,1);
        testBoard2.set(1,1,1);


        assert(testBoard1.equals(testBoard2));
        assertEquals(testBoard1.hashCode(),testBoard2.hashCode());
    }

    @Test
    void equalsTest() {
        SudokuSolver testSolver = new BacktrackingSudokuSolver();
        SudokuBoard testBoard1 = new SudokuBoard(testSolver);
        SudokuBoard testBoard2 = new SudokuBoard(testSolver);
        SudokuBoard testBoard3 = new SudokuBoard(testSolver);
        SudokuBoard testBoard4 = new SudokuBoard(null);
        testBoard1.set(1,1,1);
        testBoard2.set(1,1,1);
        testBoard3.set(2,2,2);
        testBoard4.set(1,1,1);

        assert(testBoard1.equals(testBoard2));
        assertFalse(testBoard1.equals(testBoard3));
        assertFalse(testBoard1.equals(null));
        assertFalse(testBoard1.equals(testSolver));
        assert(testBoard1.equals(testBoard1));
        assertFalse(testBoard1.equals(testBoard4));
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard originalBoard = new SudokuBoard(solver);
        originalBoard.solveGame();

        SudokuBoard clonedBoard = null;
        clonedBoard = (SudokuBoard) originalBoard.clone();

        assertEquals(originalBoard, clonedBoard);
        assertNotSame(originalBoard, clonedBoard);
    }

    @Test
    public void testDifferentClone() throws CloneNotSupportedException {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard originalBoard = new SudokuBoard(solver);
        originalBoard.solveGame();
        SudokuBoard clonedBoard = (SudokuBoard) originalBoard.clone();

        clonedBoard.set(1,1,0);

        assertNotEquals(originalBoard, clonedBoard);
    }
}
