package model;

import model.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    private final String database = "jdbc:sqlite:Database";
    private final String boardName;
    private int sudokuBoardId;
    private Connection conn;

    private final String createSudokuBoardsTable =
            "CREATE TABLE IF NOT EXISTS SudokuBoards "
                    + "(board_id int primary key, name varchar(50) not null);";
    private final String createSudokuFieldsTable = "CREATE TABLE IF NOT EXISTS SudokuFields "
            + "(board_id int not null, value int not null, row int not null, column int not null"
            + ", CONSTRAINT FK_board FOREIGN KEY (board_id) REFERENCES SudokuBoards(board_id));";
    private final String boardInserts = "INSERT INTO SudokuBoards VALUES(?,?);";
    private final String fieldInserts = "INSERT INTO SudokuFields(board_id, value, "
            + "row, column) VALUES(?,?,?,?);";
    private final String boardId = "SELECT COUNT(*) AS 'max_id' FROM SudokuBoards;";

    public JdbcSudokuBoardDao(String boardName) throws DatabaseException {
        this.boardName = boardName;
        try {
            conn = DriverManager.getConnection(database);
            conn.setAutoCommit(false);

            Statement statement = conn.createStatement();
            statement.execute(createSudokuBoardsTable);
            statement.execute(createSudokuFieldsTable);
            conn.commit();
        } catch (SQLException e) {
            logger.error(resourceBundle.getString("database"), e);
            System.out.println(e.getMessage());
            throw new DatabaseException(resourceBundle.getString("database"));
        }
    }

    @Override
    public SudokuBoard read() throws DatabaseException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        String select = "SELECT * FROM SudokuFields WHERE"
                + " board_id = (SELECT MAX(board_id) FROM SudokuBoards WHERE name = '"
                + boardName + "');";

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(select);) {
            while (resultSet.next()) {
                sudokuBoard.set(resultSet.getInt("row"),
                        resultSet.getInt("column"),
                        resultSet.getInt("value"));
            }
        } catch (SQLException e) {
            logger.error(resourceBundle.getString("database"), e);
            System.out.println(e.getMessage());
            throw new DatabaseException(resourceBundle.getString("database"));
        }

        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) throws DatabaseException {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(boardId);) {
            sudokuBoardId = resultSet.getInt("max_id") + 1;

            PreparedStatement preparedStatementBoard = conn.prepareStatement(boardInserts);
            preparedStatementBoard.setInt(1,sudokuBoardId);
            preparedStatementBoard.setString(2, boardName);
            preparedStatementBoard.executeUpdate();

            PreparedStatement preparedStatementField = conn.prepareStatement(fieldInserts);

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    preparedStatementField.setInt(1, sudokuBoardId);
                    preparedStatementField.setInt(2, obj.get(i, j));
                    preparedStatementField.setInt(3, i);
                    preparedStatementField.setInt(4, j);

                    preparedStatementField.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            logger.error(resourceBundle.getString("database"), e);
            System.out.println(e.getMessage());
            throw new DatabaseException(resourceBundle.getString("database"));
        }
    }


    @Override
    public void close() throws SQLException {
        conn.close();
    }
}