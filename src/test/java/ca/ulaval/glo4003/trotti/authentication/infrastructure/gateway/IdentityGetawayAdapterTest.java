package ca.ulaval.glo4003.trotti.authentication.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.account.application.dto.IdentityAccountDto;
import ca.ulaval.glo4003.trotti.account.application.port.AccountQuery;
import ca.ulaval.glo4003.trotti.authentication.domain.gateway.IdentityGateway;
import ca.ulaval.glo4003.trotti.commons.domain.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IdentityGetawayAdapterTest {
    private static final Email EMAIL = Email.from("johndoe@ulaval.ca");
    private IdentityMapper identityMapper;
    private AccountQuery accountQuery;
	
    private IdentityGateway identityGetawayAdapter;

    @BeforeEach
    void setup() {
        identityMapper = Mockito.mock(IdentityMapper.class);
        accountQuery = Mockito.mock(AccountQuery.class);
        identityGetawayAdapter = new IdentityGetawayAdapter(identityMapper, accountQuery);
    }

    @Test
    void givenEmail_whenFindByEmail_thenMapsToDomainEntity() {
        IdentityAccountDto identityAccountDto = Mockito.mock(IdentityAccountDto.class);
        Mockito.when(accountQuery.findByEmail(EMAIL)).thenReturn(identityAccountDto);

        identityGetawayAdapter.findByEmail(EMAIL);

        Mockito.verify(identityMapper).toDomain(identityAccountDto);
    }
}
