package model;

import model.exception.WrongGroupSizeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class SudokuGroupTest {

    @Test
    void SudokuGroupVerifyTest() {
        SudokuSolver solverTest = new BacktrackingSudokuSolver();
        SudokuBoard boardTest = new SudokuBoard(solverTest);
        boardTest.solveGame();
        assertTrue(boardTest.getBox(1, 5).verify());
        assertTrue(boardTest.getRow(3).verify());
        assertTrue(boardTest.getColumn(2).verify());
    }

    @Test
    void SudokuGroupVerifyNegativeTest() {
        SudokuField[] testFieldsArray = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            testFieldsArray[i] = new SudokuField(i);
        }
        List<SudokuField> testFields = Arrays.asList(testFieldsArray);

        SudokuField testField1 = new SudokuField(1);
        testFields.set(8, testField1);

        SudokuGroup testGroup1 = new SudokuRow(testFields);
        SudokuGroup testGroup2 = new SudokuColumn(testFields);
        SudokuGroup testGroup3 = new SudokuBox(testFields);
        assertFalse(testGroup1.verify());
        assertFalse(testGroup2.verify());
        assertFalse(testGroup3.verify());
    }

    @Test
    void SudokuGroupUnsolvedBoardTest() {
        SudokuSolver solverTest = new BacktrackingSudokuSolver();
        SudokuBoard boardTest = new SudokuBoard(solverTest);
        for (int i = 0; i < 9; i++)
            boardTest.set(1, i, i);
        assertTrue(boardTest.getBox(0, 5).verify());
        assertTrue(boardTest.getColumn(1).verify());
        assertTrue(boardTest.getRow(1).verify());
    }

    @Test
    void SudokuGroupWrongParameter() {
        SudokuField[] testFields = new SudokuField[15];
        List<SudokuField> testList = Arrays.asList(testFields);
        assertThrows(WrongGroupSizeException.class, () -> {
            SudokuGroup row = new SudokuRow(testList);
        });
    }
}
