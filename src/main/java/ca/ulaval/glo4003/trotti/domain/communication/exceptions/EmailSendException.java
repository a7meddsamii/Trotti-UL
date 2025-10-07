package ca.ulaval.glo4003.trotti.domain.communication.exceptions;

public class EmailSendException extends RuntimeException {
    public EmailSendException(String description) {
        super(description);
    }
}
