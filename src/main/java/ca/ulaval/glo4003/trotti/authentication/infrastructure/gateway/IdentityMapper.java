package ca.ulaval.glo4003.trotti.authentication.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.authentication.domain.entities.Identity;
import ca.ulaval.glo4003.trotti.commons.domain.service.PasswordHasher;
import ca.ulaval.glo4003.trotti.commons.domain.values.Password;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;

import java.util.HashSet;

public class IdentityMapper {
	
	PasswordHasher passwordHasher;
	
	public Identity toDomain(AccountDto accountDto) {
		return new Identity(
				accountDto.idul(),
				Role.fromString("STUDENT"), // TODO we will change this after roles are implemented in Account
				new HashSet<>(), // TODO we will change this after Permissions are implemented in Account
				Password.fromHashed(accountDto.password().toString(), passwordHasher)
		);
	}
}
