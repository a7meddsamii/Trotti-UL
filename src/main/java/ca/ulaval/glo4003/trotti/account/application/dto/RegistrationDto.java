package ca.ulaval.glo4003.trotti.account.application.dto;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Gender;
import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;

public record RegistrationDto(String name,
                              LocalDate birthDate,
                              Gender gender,
                              Idul idul,
                              Email email,
                              String password,
                              Role role) {}
