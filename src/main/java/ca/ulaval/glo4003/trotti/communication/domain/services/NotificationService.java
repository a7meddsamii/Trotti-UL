package ca.ulaval.glo4003.trotti.communication.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;

public interface NotificationService<T> {

    void notify(Email recipient, T content);
}
