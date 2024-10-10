package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SudokuColumnTest {
    @Test
    void toStringTest() {
        SudokuSolver testSolver = new BacktrackingSudokuSolver();
        SudokuBoard testBoard = new SudokuBoard(testSolver);
        SudokuColumn testColumn= testBoard.getColumn(1);
        String expectedString = "SudokuColumn[grupa=[";
        for ( int i = 0; i < testBoard.SIZE; i++) {
            expectedString += "SudokuField[pole=" + testBoard.get(i,1) + "]";
            if (i !=testBoard.SIZE -1) {
                expectedString += ", ";
            }
        }
        expectedString += "]]";
        assertEquals(expectedString,testColumn.toString());
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
        SudokuGroup col1 = new SudokuColumn(testList1);
        SudokuGroup col2 = new SudokuColumn(testList2);
        SudokuGroup col3 = new SudokuColumn(testList3);

        assert(col1.equals(col2));
        assertEquals(col1.hashCode(),col2.hashCode());

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
        SudokuGroup col1 = new SudokuColumn(testList1);
        SudokuGroup col2 = new SudokuColumn(testList2);
        SudokuGroup col3 = new SudokuColumn(testList3);

        Object o = new Object();

        assert(col1.equals(col2));
        assert(col1.equals(col1));
        assertFalse(col1.equals(null));
        assertFalse(col1.equals(col3));
        assertFalse(col1.equals(o));
    }

    @Test
    void cloneTest() throws CloneNotSupportedException {
        SudokuField[] testFields1 = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            testFields1[i] = new SudokuField(i);
        }
        List<SudokuField> testList1 = Arrays.asList(testFields1);
        SudokuColumn column1 = new SudokuColumn(testList1);

        SudokuColumn clonedColumn = (SudokuColumn) column1.clone();

        assertNotSame(column1, clonedColumn);
        assertEquals(column1, clonedColumn);
    }
}
