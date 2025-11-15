package ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records;


import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.Permission;

import java.time.LocalDate;
import java.util.Set;

public record AccountRecord(Idul idul, String name, LocalDate birthDate, Gender gender, Email email, Password password, Role role, Set<Permission> permissions) {}