package model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardPrototypeFactoryTest {
    @Test
    void createPrototypeTest() {
        SudokuBoardPrototypeFactory factory = new SudokuBoardPrototypeFactory();

        try {
            SudokuBoard prototype1 = factory.createPrototype();
            SudokuBoard prototype2 = factory.createPrototype();

            assertNotSame(prototype1, prototype2);
            assertEquals(prototype1.toString(), prototype2.toString());

            prototype1.set(0, 0, 5);
            assertNotEquals(prototype1.toString(), prototype2.toString());
        } catch (CloneNotSupportedException e) {
            fail("Clone not supported");
        }
    }
}
