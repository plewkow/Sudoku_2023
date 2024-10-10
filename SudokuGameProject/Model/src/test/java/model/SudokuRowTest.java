package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class SudokuRowTest {

    @Test
    void toStringTest() {
        SudokuSolver testSolver = new BacktrackingSudokuSolver();
        SudokuBoard testBoard = new SudokuBoard(testSolver);
        SudokuRow testRow = testBoard.getRow(1);
        String expectedString = "SudokuRow[grupa=[";
        for ( int i = 0; i < testBoard.SIZE; i++) {
            expectedString += "SudokuField[pole=" + testBoard.get(1,i) + "]";
            if (i !=testBoard.SIZE -1) {
                expectedString += ", ";
            }
        }
        expectedString += "]]";
        assertEquals(expectedString,testRow.toString());
    }

    @Test
    void hashCodeTest() {
        SudokuField[] testFields1 = new SudokuField[9];
        SudokuField[] testFields2 = new SudokuField[9];
        SudokuField[] testFields3 = new SudokuField[9];
        for (int i = 0; i < 9; i++){
            testFields1[i] = new SudokuField(i);
            testFields2[i] = new SudokuField(i);
            testFields3[i] = new SudokuField(0);
        }
        List<SudokuField> testList1 = Arrays.asList(testFields1);
        List<SudokuField> testList2 = Arrays.asList(testFields2);
        List<SudokuField> testList3 = Arrays.asList(testFields3);
        SudokuGroup row1 = new SudokuRow(testList1);
        SudokuGroup row2 = new SudokuRow(testList2);
        SudokuGroup row3 = new SudokuRow(testList3);

        assert(row1.equals(row2));
        assertEquals(row1.hashCode(),row2.hashCode());
    }

    @Test
    void equalsTest() {
        SudokuField[] testFields1 = new SudokuField[9];
        SudokuField[] testFields2 = new SudokuField[9];
        SudokuField[] testFields3 = new SudokuField[9];
        for (int i = 0; i < 9; i++){
            testFields1[i] = new SudokuField(i);
            testFields2[i] = new SudokuField(i);
            testFields3[i] = new SudokuField(0);
        }
        List<SudokuField> testList1 = Arrays.asList(testFields1);
        List<SudokuField> testList2 = Arrays.asList(testFields2);
        List<SudokuField> testList3 = Arrays.asList(testFields3);
        SudokuGroup row1 = new SudokuRow(testList1);
        SudokuGroup row2 = new SudokuRow(testList2);
        SudokuGroup row3 = new SudokuRow(testList3);
        Object o = new Object();

        assert(row1.equals(row2));
        assert(row1.equals(row1));
        assertFalse(row1.equals(null));
        assertFalse(row1.equals(row3));
        assertFalse(row1.equals(o));
    }
    /*@Test
    void equalsTest() {
        SudokuField[] testFields1 = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            testFields1[i] = new SudokuField(i);
        }
        List<SudokuField> testList1 = Arrays.asList(testFields1);
        SudokuRow row1 = new SudokuRow(testList1);
        SudokuRow row2 = new SudokuRow(testList1);

        assertEquals(row1, row2);
        assertEquals(row1, row1);
        assertFalse(row1.equals(null));
        assertFalse(row1.equals(new Object()));
    }*/

    @Test
    void cloneTest() throws CloneNotSupportedException {
        SudokuField[] testFields1 = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            testFields1[i] = new SudokuField(i);
        }
        List<SudokuField> testList1 = Arrays.asList(testFields1);
        SudokuRow row1 = new SudokuRow(testList1);

        SudokuRow clonedRow = (SudokuRow) row1.clone();

        assertNotSame(row1, clonedRow);
        assertEquals(row1, clonedRow);
    }

}
