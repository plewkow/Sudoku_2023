package model.exception;

public abstract class ModelException extends Exception {
    protected ModelException(String message) {
        super(message);
    }
}
