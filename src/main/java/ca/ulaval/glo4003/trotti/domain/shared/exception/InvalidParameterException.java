package ca.ulaval.glo4003.trotti.domain.shared.exception;

public class InvalidParameterException extends RuntimeException {
    private final ErrorType errorType;

    public InvalidParameterException(ErrorType errorType, String description) {
        super(description);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
