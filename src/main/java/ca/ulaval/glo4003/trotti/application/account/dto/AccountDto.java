package ca.ulaval.glo4003.trotti.application.account.dto;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Gender;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.account.values.Password;
import java.time.LocalDate;

public record AccountDto(
    String name,
    LocalDate birthDate,
    Gender gender,
    Idul idul,
    Email email,
    Password password
) {}
