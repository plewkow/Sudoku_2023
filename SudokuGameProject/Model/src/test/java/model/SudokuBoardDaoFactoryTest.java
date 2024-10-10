package model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SudokuBoardDaoFactoryTest {
    @Test
    public void getFileDaoTest() {
        assertNotNull(SudokuBoardDaoFactory.getFileDao("essa"));
    }
}