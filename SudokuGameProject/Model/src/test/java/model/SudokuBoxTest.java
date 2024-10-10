package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoxTest {

    @Test
    void toStringTest() {
        SudokuSolver testSolver = new BacktrackingSudokuSolver();
        SudokuBoard testBoard = new SudokuBoard(testSolver);
        testBoard.solveGame();
        SudokuBox testBox = testBoard.getBox(1, 1);
        String expectedString = "SudokuBox[grupa=[";
        for (int i = 0; i < Math.sqrt(testBoard.SIZE); i++) {
            for (int j = 0; j < Math.sqrt(testBoard.SIZE); j++) {
                expectedString += "SudokuField[pole=" + testBoard.get(i, j) + "]";
                if (i != Math.sqrt(testBoard.SIZE) - 1 || j != Math.sqrt(testBoard.SIZE) - 1) {
                    expectedString += ", ";
                }
            }
        }
        expectedString += "]]";
        assertEquals(expectedString, testBox.toString());
    }

    @Test
    void hashCodeTest() {
        SudokuField[] testFields1 = new SudokuField[9];
        SudokuField[] testFields2 = new SudokuField[9];
        SudokuField[] testFields3 = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            testFields1[i] = new SudokuField(i);
            testFields2[i] = new SudokuField(i);
            testFields3[i] = new SudokuField(0);
        }
        List<SudokuField> testList1 = Arrays.asList(testFields1);
        List<SudokuField> testList2 = Arrays.asList(testFields2);
        List<SudokuField> testList3 = Arrays.asList(testFields3);
        SudokuGroup box1 = new SudokuRow(testList1);
        SudokuGroup box2 = new SudokuRow(testList2);
        SudokuGroup box3 = new SudokuRow(testList3);

        assert(box1.equals(box2));
        assertEquals(box1.hashCode(), box2.hashCode());
    }

    @Test
    void equalsTest() {
        SudokuField[] testFields1 = new SudokuField[9];
        SudokuField[] testFields2 = new SudokuField[9];
        SudokuField[] testFields3 = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            testFields1[i] = new SudokuField(i);
            testFields2[i] = new SudokuField(i);
            testFields3[i] = new SudokuField(0);
        }
        List<SudokuField> testList1 = Arrays.asList(testFields1);
        List<SudokuField> testList2 = Arrays.asList(testFields2);
        List<SudokuField> testList3 = Arrays.asList(testFields3);
        SudokuGroup box1 = new SudokuBox(testList1);
        SudokuGroup box2 = new SudokuBox(testList2);
        SudokuGroup box3 = new SudokuBox(testList3);

        Object o = new Object();

        assert (box1.equals(box2));
        assert (box1.equals(box1));
        assertFalse(box1.equals(null));
        assertFalse(box1.equals(box3));
        assertFalse(box1.equals(o));
    }

    @Test
    void cloneTest() throws CloneNotSupportedException {
        SudokuField[] testFields1 = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            testFields1[i] = new SudokuField(i);
        }
        List<SudokuField> testList1 = Arrays.asList(testFields1);
        SudokuBox box1 = new SudokuBox(testList1);

        SudokuBox clonedBox = (SudokuBox) box1.clone();

        assertNotSame(box1, clonedBox);
        assertEquals(box1, clonedBox);
    }
}

