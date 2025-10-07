package ca.ulaval.glo4003.trotti.domain.communication.services;

import ca.ulaval.glo4003.trotti.domain.communication.values.EmailMessage;

public interface EmailService {
    void send(EmailMessage message);
}
