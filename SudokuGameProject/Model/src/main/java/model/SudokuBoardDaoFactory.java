/*package model;

import model.exception.DaoException;
import model.exception.JdbcDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class SudokuBoardDaoFactory {
    private static final Logger logger = LoggerFactory.getLogger(SudokuBoardDaoFactory.class);
    private final transient ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    public static FileSudokuBoardDao getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public JdbcSudokuBoardDao getJdbcDao(String filename){
        JdbcSudokuBoardDao jdbcSudokuBoardDao = null;
        try {
            jdbcSudokuBoardDao = new JdbcSudokuBoardDao(filename);
        } catch (JdbcDaoException e) {
            logger.error(resourceBundle.getString("jdbcLoaded"), e);
        }
        logger.debug(resourceBundle.getString("factoryError"));
        return jdbcSudokuBoardDao;
    }
}*/

package model;

import model.exception.DatabaseException;

public class SudokuBoardDaoFactory {
    private SudokuBoardDaoFactory() {

    }

    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public static Dao<SudokuBoard> getDatabaseDao(String boardName) throws DatabaseException {
        return new JdbcSudokuBoardDao(boardName);
    }
}
