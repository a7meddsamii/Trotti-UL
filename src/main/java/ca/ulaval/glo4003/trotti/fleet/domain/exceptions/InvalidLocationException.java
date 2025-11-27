package ca.ulaval.glo4003.trotti.fleet.domain.exceptions;

public class InvalidLocationException extends RuntimeException {
    public InvalidLocationException(String message) {
        super(message);
    }
}
