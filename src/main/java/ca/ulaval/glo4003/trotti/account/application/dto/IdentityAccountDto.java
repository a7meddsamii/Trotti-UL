package ca.ulaval.glo4003.trotti.account.application.dto;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.service.PasswordHasher;
import java.util.Set;

public record IdentityAccountDto(
		Idul idul,
		String role,
		Set<String> permissions,
		String hashedPassword,
		PasswordHasher hasher
) {
}