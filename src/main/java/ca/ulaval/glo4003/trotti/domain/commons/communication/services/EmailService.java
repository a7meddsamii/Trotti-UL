package ca.ulaval.glo4003.trotti.domain.commons.communication.services;

import ca.ulaval.glo4003.trotti.domain.commons.communication.values.EmailMessage;

public interface EmailService {
    void send(EmailMessage message);
}
