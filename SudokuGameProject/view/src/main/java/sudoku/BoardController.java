package sudoku;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import model.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;


public class BoardController {
    @FXML
    private GridPane sudokuGridPane;

    protected final IntegerProperty[][] objectProperties = new IntegerProperty[9][9];

    final Logger logger = LoggerFactory.getLogger(BoardController.class);
    Locale locale = new Locale("pl","PL");

    ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource_Bundle_pl_PL", locale);
    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    DifficultyLevel difficulty;

    private Stage primaryStage;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize(DifficultyLevel difficulty) throws NoSuchMethodException {
        sudokuGridPane.setAlignment(Pos.CENTER);
        sudokuGridPane.setStyle("-fx-background-color: #4f4f4f;");
        this.difficulty = difficulty;
        fillSudokuBoard();
        fillSudokuGrid();
    }



    private void fillSudokuGrid() throws NoSuchMethodException {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = board.get(row, col);

                TextField text;
                if (value == 0) {
                    text = new TextField();
                } else {
                    text = new TextField(String.valueOf(value));
                    text.setEditable(false);
                }
                text.setMinSize(50, 50);
                text.setMaxSize(50, 50);
                text.setAlignment(Pos.CENTER);
                sudokuGridPane.add(text, row, col);

                TextFormatter<Integer> textFormatter = new TextFormatter<>(
                        new SudokuFieldTextConverter(), 0, new SudokuFieldTextFormatter()
                );

                ObjectProperty<Integer> sudokuProperty = JavaBeanIntegerPropertyBuilder
                        .create()
                        .bean(board.getField(row,col))
                        .name("fieldValue").build().asObject();

                objectProperties[row][col] = IntegerProperty.integerProperty(sudokuProperty);
                text.setTextFormatter(textFormatter);
                textFormatter.valueProperty().bindBidirectional(sudokuProperty);

            }
        }
    }

    private void fillSudokuBoard() {
        board.solveGame();

        List<Integer> random1 = new ArrayList<>();
        List<Integer> random2 = new ArrayList<>();
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            random1.add(i);
            random2.add(i);
        }

        Collections.shuffle(random1);
        Collections.shuffle(random2);

        int deletes = difficulty.numberOfRemovedCells();
        int k = 0;
        HashSet<Integer> usedFields = new HashSet<>();
        while (deletes > 0) {
            if (k >= SudokuBoard.SIZE) {
                k = 0;
                SecureRandom secureRandom = new SecureRandom();
                Collections.shuffle(random1, secureRandom);
                Collections.shuffle(random2, secureRandom);
            }
            int col = random1.get(k);
            int row = random2.get(k);
            if (usedFields.add(row * 10 + col)) {
                board.set(row, col, 0);
                deletes--;
            }
            k++;
        }
    }

    public void loadGame(SudokuBoard board) throws NoSuchMethodException {
        sudokuGridPane.setAlignment(Pos.CENTER);
        sudokuGridPane.setStyle("-fx-background-color: #4f4f4f;");
        this.board = board;
       fillSudokuGrid();
        logger.debug(resourceBundle.getString("gameLoaded"));
    }

    public void saveGame(ActionEvent actionEvent) throws IOException {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Game");


            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sudoku Files (*.dat)", "*.dat");
            fileChooser.getExtensionFilters().add(extFilter);


            Stage stage = (Stage) sudokuGridPane.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);
            logger.debug(resourceBundle.getString("gameSaved"));

            if (file != null) {
                FileSudokuBoardDao fileSudokuBoardDao = new FileSudokuBoardDao(file.getAbsolutePath());
                fileSudokuBoardDao.write(board);
            }
        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    public void showAuthors() {
        try {
            DifficultyChoiceController difficultyChoiceController = new DifficultyChoiceController();
            difficultyChoiceController.getAuthors();
            logger.debug(resourceBundle.getString("authorsWindow"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToMenu() throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource_Bundle");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"), resourceBundle);
        Parent root = loader.load();
        DifficultyChoiceController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();
        logger.debug(resourceBundle.getString("menuOpened"));
    }

    public void saveToDatabase() throws DatabaseException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource_Bundle");
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setContentText(resourceBundle.getString("EnterDatabaseBoardName"));

        dialog.showAndWait();
        String result = dialog.getResult();

        try (Dao<SudokuBoard> dao =SudokuBoardDaoFactory.getDatabaseDao(result)) {
            if (board != null) {
                dao.write(board);
            } else
                throw new RuntimeException(resourceBundle.getString("databaseWriteException"));
        } catch (Exception e) {
            logger.error(resourceBundle.getString("databaseWriteException"));
            throw new DatabaseException((resourceBundle.getString("databaseWriteException")));
        }
        logger.info(resourceBundle.getString("databaseWriteInfo"));
    }


}