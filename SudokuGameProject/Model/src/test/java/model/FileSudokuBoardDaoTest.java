package model;

import model.exception.SudokuBoardReadException;
import model.exception.SudokuBoardWriteException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest {
    @Test
    public void Test() throws Exception {
        SudokuSolver testSolver = new BacktrackingSudokuSolver();
        SudokuBoard testBoard1 = new SudokuBoard(testSolver);
        testBoard1.solveGame();

        try (Dao<SudokuBoard> testDao = SudokuBoardDaoFactory.getFileDao("testName")) {
            testDao.write(testBoard1);
            SudokuBoard testBoard2 = testDao.read();
            assertEquals(testBoard1, testBoard2);
        }
    }

    @Test
    public void readExceptionTest() {
        Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("abc");
        assertThrows(SudokuBoardReadException.class, fileSudokuBoardDao::read);
    }
    @Test
    public void writeExceptionTest() throws Exception {
        SudokuSolver testSolver = new BacktrackingSudokuSolver();
        SudokuBoard testBoard = new SudokuBoard(testSolver);

        String invalidFilePath = "testName/testName.dat";

        try (FileSudokuBoardDao testDao = new FileSudokuBoardDao(invalidFilePath)) {
            assertThrows(SudokuBoardWriteException.class, () -> testDao.write(testBoard));
        }
    }


    @AfterEach
    public void cleanup() {
        String filePath = "testName.dat";
        Path path = Paths.get(filePath);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
