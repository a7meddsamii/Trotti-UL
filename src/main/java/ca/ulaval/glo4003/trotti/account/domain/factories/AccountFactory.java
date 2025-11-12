package ca.ulaval.glo4003.trotti.account.domain.factories;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.Permission;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.RolePermissionsRegistry;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Set;

public class AccountFactory {
    private static final int MINIMUM_AGE_YEARS = 16;

    private final Clock clock;
    private final RolePermissionsRegistry registry;

    public AccountFactory(Clock clock, RolePermissionsRegistry registry) {
        this.clock = clock;
        this.registry = registry;
    }
    
    public Account create(String name, LocalDate birthDate, Gender gender,
                          Idul idul, Email email, Password password, Role role) {
        
        validateBirthDate(birthDate);
        Set<Permission> permission = registry.get(role);
        return new Account(name, birthDate, gender, idul, email, password, role, permission);
    }
    
    //this needs to be moved elsewhere
    private void validateBirthDate(LocalDate birthDate) {
        LocalDate today = LocalDate.now(clock);
        LocalDate minimumValidBirthDate = today.minusYears(MINIMUM_AGE_YEARS);
        if (birthDate.isAfter(minimumValidBirthDate)) {
            throw new InvalidParameterException(
                    "User must be at least " + MINIMUM_AGE_YEARS + " years old.");
        }
    }
    
}