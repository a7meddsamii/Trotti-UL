package ca.ulaval.glo4003.trotti.communication.domain.exceptions;

public class EmailSendException extends RuntimeException {
    public EmailSendException(String description) {
        super(description);
    }
}
