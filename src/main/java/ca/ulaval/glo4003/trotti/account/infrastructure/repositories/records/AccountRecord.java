package ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Gender;
import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.account.domain.values.Password;
import java.time.LocalDate;

public record AccountRecord(Idul idul, String name, LocalDate birthDate, Gender gender, Email email, Password password) {
	
}