package ca.ulaval.glo4003.trotti.authentication.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.authentication.domain.entities.Identity;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;
import ca.ulaval.glo4003.trotti.commons.domain.Password;
import ca.ulaval.glo4003.trotti.commons.domain.service.PasswordHasher;
import java.util.HashSet;

public class IdentityMapper {

    PasswordHasher passwordHasher;
	
	// TODO we will change this after Roles and Permissions are implemented in Account
    public Identity toDomain(AccountDto accountDto) {
        return new Identity(accountDto.idul(), Role.fromString("STUDENT"),
                new HashSet<>(),
                Password.fromHashed(accountDto.password().toString(), passwordHasher));
    }
}
