package ca.ulaval.glo4003.trotti.account.application.port;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.commons.domain.values.Email;

public interface AccountQuery {
	AccountDto findByEmail(Email email);
}
