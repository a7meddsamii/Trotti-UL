package ca.ulaval.glo4003.trotti.infrastructure.repository.account;

import ca.ulaval.glo4003.trotti.domain.account.Gender;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.Password;
import ca.ulaval.glo4003.trotti.domain.commons.Email;
import java.time.LocalDate;

public record AccountRecord(Idul idul, String name, LocalDate birthDate, Gender gender, Email email, Password password) {
	
}