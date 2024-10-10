package sudoku;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import model.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class DifficultyChoiceController {
    static final Logger logger = LoggerFactory.getLogger(DifficultyChoiceController.class);

    private Stage primaryStage;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }



    public void easyButtonClick(ActionEvent actionEvent) throws IOException, NoSuchMethodException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource_Bundle");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"), resourceBundle);
        Parent root = loader.load();
        BoardController controller = loader.getController();
        controller.initialize(DifficultyLevel.easy);
        logger.debug(resourceBundle.getString("boardLoadedEasy"));
        Scene scene = new Scene(root, 495, 526);
        controller.setPrimaryStage(primaryStage);

        primaryStage.setScene(scene);


        primaryStage.setTitle("Sudoku");


        primaryStage.show();

    }

    public void mediumButtonClick(ActionEvent actionEvent) throws IOException, NoSuchMethodException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource_Bundle");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"), resourceBundle);
        Parent root = loader.load();
        BoardController controller = loader.getController();
        controller.initialize(DifficultyLevel.medium);
        logger.debug(resourceBundle.getString("boardLoadedMedium"));
        Scene scene = new Scene(root, 495, 526);
        controller.setPrimaryStage(primaryStage);

        primaryStage.setScene(scene);


        primaryStage.setTitle("Sudoku");


        primaryStage.show();
    }

    public void hardButtonClick(ActionEvent actionEvent) throws IOException, NoSuchMethodException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource_Bundle");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"), resourceBundle);
        Parent root = loader.load();
        BoardController controller = loader.getController();
        controller.initialize(DifficultyLevel.hard);
        logger.debug(resourceBundle.getString("boardLoadedHard"));
        Scene scene = new Scene(root, 495, 526);
        controller.setPrimaryStage(primaryStage);

        primaryStage.setScene(scene);


        primaryStage.setTitle("Sudoku");


        primaryStage.show();
    }

    public void loadSavedGame(ActionEvent actionEvent) throws IOException {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose a Sudoku file");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.dat"));

            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (selectedFile != null) {
                FileSudokuBoardDao fileSudokuBoardDao = new FileSudokuBoardDao(selectedFile.getAbsolutePath());
                SudokuBoard savedGame = fileSudokuBoardDao.read();

                ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource_Bundle");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"), resourceBundle);
                final Parent root = loader.load();
                BoardController controller = loader.getController();
                controller.setPrimaryStage(primaryStage);
                controller.loadGame(savedGame);
                logger.debug(resourceBundle.getString("savedBoardLoaded"));

                Scene scene = new Scene(root, 495, 526);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Sudoku");
                primaryStage.show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getAuthors() throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("sudoku.AuthorsResources");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Authors.fxml"), resourceBundle);
        Stage stage = new Stage();
        stage.setResizable(false);
        Scene scene = new Scene(loader.load(), 200, 250);
        stage.setScene(scene);
        stage.show();
    }

    public void loadScene() throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource_Bundle");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"), resourceBundle);
        Parent root = loader.load();

        DifficultyChoiceController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();
    }

    public void polishButton() throws IOException {
        Locale locale = new Locale("pl", "PL");
        Locale.setDefault(locale);
        loadScene();
    }

    public void japaneseButton() throws IOException {
        Locale locale = new Locale("ja", "JP");
        Locale.setDefault(locale);
        loadScene();
    }

    public void close() {
        Platform.exit();
    }
    public void loadGameFromDatabase() throws DatabaseException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource_Bundle");
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setContentText(resourceBundle.getString("EnterDatabaseBoardName"));

        dialog.showAndWait();
        String result = dialog.getResult();

        try(Dao<SudokuBoard> dao = new JdbcSudokuBoardDao(result)) {
            SudokuBoard savedGame = dao.read();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"), resourceBundle);
            final Parent root = loader.load();
            BoardController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.loadGame(savedGame);
            logger.debug(resourceBundle.getString("savedBoardLoaded"));

            Scene scene = new Scene(root, 495, 526);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sudoku");
            primaryStage.show();
        } catch (Exception e) {
            logger.error(resourceBundle.getString("databaseReadException"));
            throw new DatabaseException((resourceBundle.getString("databaseReadException")));
        }
        logger.info(resourceBundle.getString("databaseReadInfo"));
    }
}