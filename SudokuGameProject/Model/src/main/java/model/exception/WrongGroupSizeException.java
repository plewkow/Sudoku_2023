package model.exception;


public class WrongGroupSizeException extends IllegalArgumentException {
    public WrongGroupSizeException(final String message) {
        super(message);
    }
}
