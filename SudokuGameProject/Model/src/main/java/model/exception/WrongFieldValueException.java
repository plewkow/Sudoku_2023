package model.exception;


public class WrongFieldValueException extends IllegalArgumentException {
    public WrongFieldValueException(final String message) {
        super(message);
    }
}
