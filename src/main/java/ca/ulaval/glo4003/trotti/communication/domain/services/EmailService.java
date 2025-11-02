package ca.ulaval.glo4003.trotti.communication.domain.services;

import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;

public interface EmailService {
    void send(EmailMessage message);
}
