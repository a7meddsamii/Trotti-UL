package ca.ulaval.glo4003.trotti.application.port;

import ca.ulaval.glo4003.trotti.domain.account.Idul;

public interface TokenPort {
    String generateToken(Idul accountId);
}
