package ca.ulaval.glo4003.trotti.domain.communication.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;

public interface NotificationService<T> {

    void notify(Email recipient, T content);
}
