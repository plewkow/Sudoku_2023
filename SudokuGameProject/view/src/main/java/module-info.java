module pl.comp.viewproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;
    requires org.slf4j;


    opens sudoku to javafx.fxml;
    exports sudoku;
}