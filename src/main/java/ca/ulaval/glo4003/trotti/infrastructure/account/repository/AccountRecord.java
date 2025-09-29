package ca.ulaval.glo4003.trotti.infrastructure.account.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Gender;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.account.values.Password;
import java.time.LocalDate;

public record AccountRecord(Idul idul, String name, LocalDate birthDate, Gender gender, Email email, Password password) {
	
}