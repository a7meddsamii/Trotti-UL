package ca.ulaval.glo4003.trotti.domain.commons.exceptions;

public class EmailSendException extends RuntimeException {
    public EmailSendException(String description) {
        super(description);
    }
}
