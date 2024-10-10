package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import model.exception.DatabaseException;
import org.junit.jupiter.api.Test;

public class JdbcSudokuBoardDaoTest {
    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    String lang = Locale.getDefault().getLanguage();
    Locale locale = new Locale.Builder().setLanguage(lang).build();
    ResourceBundle rb = ResourceBundle
            .getBundle("messages", locale);


    @Test
    public void writeReadTest() {
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDatabaseDao("test_board1")) {
            board.solveGame();
            try {
                dao.write(board);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }

            SudokuBoard board1 = dao.read();
            assertEquals(board, board1);

        } catch (SQLException | DatabaseException e) {
            throw new RuntimeException(e);
        }
    }




    @Test
    public void negativeReadTest() {
        try (Dao<SudokuBoard> dao = (JdbcSudokuBoardDao) SudokuBoardDaoFactory
                .getDatabaseDao("\"--'--")) {

            assertThrows(DatabaseException.class, dao::read);

        } catch (DatabaseException ex) {
            assertEquals(ex.getLocalizedMessage(),
                    rb.getString("database"));
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void negativeWriteTest() {
        try (Dao<SudokuBoard> dao = (JdbcSudokuBoardDao) SudokuBoardDaoFactory
                .getDatabaseDao(null)) {
            assertThrows(DatabaseException.class, () -> {
                board.solveGame();
                dao.write(board);
            });
        } catch (DatabaseException ex) {
            assertEquals(ex.getLocalizedMessage(),
                     rb.getString("database"));
        } catch (Exception ex) {
            fail();
        }
    }
}
