package ca.ulaval.glo4003.trotti.account.api.handler;

import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.domain.values.Advantage;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountAdvantageRenewalEvent;

public class AccountEventHandler {
	private final AccountApplicationService accountApplicationService;
	
	public AccountEventHandler(AccountApplicationService accountApplicationService) {
		this.accountApplicationService = accountApplicationService;
	}
	
	public void onAccountAdvantageRenewal(AccountAdvantageRenewalEvent event) {
		Advantage advantage = Advantage.valueOf(event.getAdvantage());
		accountApplicationService.renewAdvantage(advantage);
	}
}
