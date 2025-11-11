package ca.ulaval.glo4003.trotti.account.application.port;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;

public interface AccountService extends AccountQuery{
	Idul createAccount(AccountDto registration);
}
