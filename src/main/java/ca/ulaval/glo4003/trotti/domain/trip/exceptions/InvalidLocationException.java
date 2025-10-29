package ca.ulaval.glo4003.trotti.domain.trip.exceptions;

public class InvalidLocationException extends RuntimeException {
    public InvalidLocationException(String message) {
        super(message);
    }
}
