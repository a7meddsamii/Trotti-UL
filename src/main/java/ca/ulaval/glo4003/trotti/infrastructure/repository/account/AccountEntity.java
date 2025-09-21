package ca.ulaval.glo4003.trotti.infrastructure.repository.account;

import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Gender;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.Password;

public record AccountEntity(Idul idul, String name, Gender gender, Email email, Password password) {
	
}
