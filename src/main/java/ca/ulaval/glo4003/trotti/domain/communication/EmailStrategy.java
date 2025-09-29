package ca.ulaval.glo4003.trotti.domain.communication;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;

public interface EmailStrategy {
    Email getRecipient();

    String getSubject();

    String getBody();
}
