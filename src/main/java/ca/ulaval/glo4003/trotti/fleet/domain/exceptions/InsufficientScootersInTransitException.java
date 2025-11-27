package ca.ulaval.glo4003.trotti.fleet.domain.exceptions;

public class InsufficientScootersInTransitException extends RuntimeException {
    public InsufficientScootersInTransitException(String message) {
        super(message);
    }
}
