package model;

import model.exception.WrongFieldValueException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class SudokuFieldTest {
    @Test
    void setTest() {
        SudokuField fieldTest = new SudokuField(0);
        fieldTest.setFieldValue(5);
        assertEquals(5, fieldTest.getFieldValue());
        assertThrows(WrongFieldValueException.class, () -> {
            fieldTest.setFieldValue(10);
        });
        assertThrows(WrongFieldValueException.class, () -> {
            fieldTest.setFieldValue(-1);
        });
    }

    @Test
    void toStringTest(){
        SudokuField testField = new SudokuField(7);
        String expectedString = "SudokuField[pole=" + testField.getFieldValue() + "]";
        assertEquals(testField.toString(),expectedString);
    }

    @Test
    void hashCodeTest() {
        SudokuField testField1 = new SudokuField(1);
        SudokuField testField2 = new SudokuField(1);
        SudokuField testField3 = new SudokuField(2);

        assert(testField1.equals(testField2));
        assertEquals(testField1.hashCode(),testField2.hashCode());
    }

    @Test
    void equalsTest() {
        SudokuField testField1 = new SudokuField(1);
        SudokuField testField2 = new SudokuField(1);
        SudokuField testField3 = new SudokuField(2);
        Object o = new Object();
        assert(testField1.equals(testField2));
        assert(testField1.equals(testField1));
        assertFalse(testField1.equals(testField3));
        assertFalse(testField1.equals(null));
        assertFalse(testField1.equals(o));
    }

    @Test
    void compareToTest() {
        SudokuField testField1 = new SudokuField(1);
        SudokuField testField2 = new SudokuField(1);

        assertEquals(0, testField1.compareTo(testField2));
        assertEquals(0, testField1.compareTo(testField2));

        testField1.setFieldValue(2);
        assertTrue(testField1.compareTo(testField2) > 0);

        testField1.setFieldValue(0);
        assertTrue(testField1.compareTo(testField2) < 0);

    }

    @Test
    void compareToNullTest() {
        SudokuField testField = new SudokuField(1);
        assertThrows(NullPointerException.class, () -> {
            testField.compareTo(null);
        });
    }

    @Test
    void cloneTest() {
        SudokuField testField1 = new SudokuField(1);
        SudokuField testField2 = null;
        try {
            testField2 = (SudokuField) testField1.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(testField1.equals(testField2)
                && testField2.equals(testField1));

    }
}