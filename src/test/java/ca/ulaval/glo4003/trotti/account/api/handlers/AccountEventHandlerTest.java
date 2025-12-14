package ca.ulaval.glo4003.trotti.account.api.handlers;

import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.domain.values.Advantage;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountAdvantageRenewalEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountEventHandlerTest {

    private static final String ADVANTAGE_STRING = "FREE_RIDE_PERMIT";
    private static final Advantage STUDENT_DISCOUNT_ADVANTAGE = Advantage.FREE_RIDE_PERMIT;

    private AccountApplicationService accountApplicationService;
    private AccountEventHandler accountEventHandler;

    @BeforeEach
    void setup() {
        accountApplicationService = Mockito.mock(AccountApplicationService.class);
        accountEventHandler = new AccountEventHandler(accountApplicationService);
    }

    @Test
    void givenAccountAdvantageRenewalEvent_whenOnAccountAdvantageRenewal_thenCallsRenewAdvantage() {
        AccountAdvantageRenewalEvent event = new AccountAdvantageRenewalEvent(ADVANTAGE_STRING);

        accountEventHandler.onAccountAdvantageRenewal(event);

        Mockito.verify(accountApplicationService).renewAdvantage(STUDENT_DISCOUNT_ADVANTAGE);
    }
}
