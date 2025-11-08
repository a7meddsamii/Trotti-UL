package ca.ulaval.glo4003.trotti.account.application.port;

import ca.ulaval.glo4003.trotti.account.application.dto.IdentityAccountDto;
import ca.ulaval.glo4003.trotti.commons.domain.Email;

public interface AccountQuery {
    IdentityAccountDto findByEmail(Email email);
}
