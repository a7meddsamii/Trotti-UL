package ca.ulaval.glo4003.trotti.account.application.dto;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Gender;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.account.domain.values.Password;
import java.time.LocalDate;

public record AccountDto(
    String name,
    LocalDate birthDate,
    Gender gender,
    Idul idul,
    Email email,
    Password password
) {}
