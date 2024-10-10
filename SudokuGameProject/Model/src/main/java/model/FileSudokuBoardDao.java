package model;

import model.exception.SudokuBoardReadException;
import model.exception.SudokuBoardWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    static final Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class);
    transient ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    private String filename;

    public FileSudokuBoardDao(String filename) {
        this.filename = filename;
    }

    @Override
    public SudokuBoard read() throws SudokuBoardReadException {
        SudokuBoard obj = null;

        try (FileInputStream fileInputStream = new FileInputStream(filename);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            obj = (SudokuBoard) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            logger.error(resourceBundle.getString("FileSudokuBoardReadException"));
            throw new SudokuBoardReadException(resourceBundle.getString("FileSudokuBoardReadException"));
        }

        return obj;
    }

    @Override
    public void write(SudokuBoard obj) throws SudokuBoardWriteException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            logger.error(resourceBundle.getString("FileSudokuBoardWriteException"));
            throw new SudokuBoardWriteException(resourceBundle.getString("FileSudokuBoardWriteException"));
        }
    }


    @Override
    public void close() throws Exception {

    }
}