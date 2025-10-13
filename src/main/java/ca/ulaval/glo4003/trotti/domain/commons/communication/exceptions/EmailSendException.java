package ca.ulaval.glo4003.trotti.domain.commons.communication.exceptions;

public class EmailSendException extends RuntimeException {
    public EmailSendException(String description) {
        super(description);
    }
}
