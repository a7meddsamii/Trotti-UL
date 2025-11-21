package ca.ulaval.glo4003.trotti.account.application.dto;

import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;

public record AccountDto(
    String name,
    LocalDate birthDate,
    Gender gender,
    Idul idul,
    Email email,
    Password password,
    Role role
) {}
