package ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records;


import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.Set;

public record AccountRecord(Idul idul, String name, LocalDate birthDate, Gender gender, Email email, Role role, Set<Permission> permissions, Set<Advantage> advantages) {}