package ca.ulaval.glo4003.trotti.account.application.dto;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Password;

import java.util.Set;

public record IdentityAccountDto(
		Idul idul,
		Email email,
		Password password,
		String role,
		Set<String> permissions
) {}