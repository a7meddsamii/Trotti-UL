package ca.ulaval.glo4003.trotti.authentication.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.port.AccountQuery;
import ca.ulaval.glo4003.trotti.authentication.domain.entities.Identity;
import ca.ulaval.glo4003.trotti.commons.domain.Email;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class IdentityGetawayAdapterTest {
    private static final Email email = Email.from("johndoe@ulaval.ca");

    private IdentityMapper identityMapper;
    private AccountQuery accountQuery;
    private IdentityGetawayAdapter identityGetawayAdapter;

    @BeforeEach
    void setup() {
        identityMapper = Mockito.mock(IdentityMapper.class);
        accountQuery = Mockito.mock(AccountQuery.class);
        identityGetawayAdapter = new IdentityGetawayAdapter(identityMapper, accountQuery);
    }

    @Test
    void givenEmail_whenFindByEmail_thenReturnsIdentity() {
        AccountDto accountDto = Mockito.mock(AccountDto.class);
        Identity expectedIdentity = Mockito.mock(Identity.class);
        Mockito.when(accountQuery.findByEmail(email)).thenReturn(accountDto);
        Mockito.when(identityMapper.toDomain(accountDto)).thenReturn(expectedIdentity);

        Identity result = identityGetawayAdapter.findByEmail(email);

        Assertions.assertEquals(expectedIdentity, result);
    }

}
