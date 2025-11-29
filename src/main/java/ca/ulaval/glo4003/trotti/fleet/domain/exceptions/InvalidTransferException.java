package ca.ulaval.glo4003.trotti.fleet.domain.exceptions;

public class InvalidTransferException extends RuntimeException {
    public InvalidTransferException(String message) {
        super(message);
    }
}
