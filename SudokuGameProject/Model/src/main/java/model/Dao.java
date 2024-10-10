package model;

import model.exception.*;

public interface Dao<T> extends AutoCloseable {

    T read() throws SudokuBoardReadException, DaoException, DatabaseException;

    void write(T obj) throws SudokuBoardWriteException, DaoException, DatabaseException;
    }