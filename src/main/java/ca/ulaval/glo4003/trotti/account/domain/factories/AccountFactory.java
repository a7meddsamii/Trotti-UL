package ca.ulaval.glo4003.trotti.account.domain.factories;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.entities.userAccountCreationChain.UserAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.time.Clock;
import java.time.LocalDate;

public class AccountFactory {
    private static final int MINIMUM_AGE_YEARS = 16;

    private final Clock clock;
    private final UserAccountCreationNode master;

    public AccountFactory(Clock clock, UserAccountCreationNode master) {
        this.clock = clock;
        this.master = master;
    }
    
    public Account create(String name, LocalDate birthDate, Gender gender,
                          Idul idul, Email email, Password password, Role role) {
        
        validateBirthDate(birthDate);
        return master.CreateUserAccount(name, birthDate, gender, idul, email, password, role);
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