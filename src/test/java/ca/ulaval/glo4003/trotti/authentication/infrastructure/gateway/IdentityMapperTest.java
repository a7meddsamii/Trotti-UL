package ca.ulaval.glo4003.trotti.authentication.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.account.application.dto.IdentityAccountDto;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.authentication.domain.entities.Identity;
import ca.ulaval.glo4003.trotti.commons.domain.service.PasswordHasher;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IdentityMapperTest {
    private static final Idul AN_IDUL = Idul.from("jdoe");
    private static final String HASHED_PASSWORD = "hashedPassword";
    private static final Set<String> PERMISSIONS = Set.of("CART_MODIFICATION", "ORDER_CONFIRM");
    private static final String ROLE = "STUDENT";

    private PasswordHasher passwordHasher;
    private IdentityMapper identityMapper;

    @BeforeEach
    void setup() {
        passwordHasher = Mockito.mock(PasswordHasher.class);
        identityMapper = new IdentityMapper();
    }

    @Test
    void givenIdentityAccountInfo_whenToDomain_thenReturnsIdentity() {
        IdentityAccountDto identityAccountDto = makeIdentityAccountDto();

        Identity identity = identityMapper.toDomain(identityAccountDto);

        Assertions.assertEquals(identity.getIdul(), identityAccountDto.idul());
        Assertions.assertEquals(identity.getRole().toString(), identityAccountDto.role());
        identity.getPermissions().forEach(permission -> Assertions
                .assertTrue(identityAccountDto.permissions().contains(permission.name())));
    }

    private IdentityAccountDto makeIdentityAccountDto() {
        return new IdentityAccountDto(AN_IDUL, ROLE, PERMISSIONS, HASHED_PASSWORD, passwordHasher);
    }
}
