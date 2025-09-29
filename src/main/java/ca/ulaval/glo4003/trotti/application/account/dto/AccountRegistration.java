package ca.ulaval.glo4003.trotti.application.account.dto;

import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Gender;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.Password;
import java.time.LocalDate;

public record AccountRegistration(
  String name,
  LocalDate birthDate,
  Gender gender,
  Idul idul,
  Email email,
  Password password
) {}
