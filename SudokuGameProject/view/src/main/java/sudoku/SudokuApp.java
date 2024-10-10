package sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class SudokuApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        final Logger logger = LoggerFactory.getLogger(SudokuApp.class);
        Locale locale = new Locale("pl","PL");
        Locale.setDefault(locale);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource_Bundle", locale);

        primaryStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"),resourceBundle);
        logger.debug(resourceBundle.getString("menuOpened"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();

        DifficultyChoiceController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
