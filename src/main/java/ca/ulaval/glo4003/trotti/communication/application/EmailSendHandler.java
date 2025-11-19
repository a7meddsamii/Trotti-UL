package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;

public abstract class EmailSendHandler {

    protected final EmailService emailService;

    EmailSendHandler(EmailService emailService) {
        this.emailService = emailService;
    }
}
