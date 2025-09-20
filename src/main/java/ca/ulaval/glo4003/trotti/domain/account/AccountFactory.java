package ca.ulaval.glo4003.trotti.domain.account;

import java.time.LocalDate;

public class AccountFactory {

    public Account create(String name, LocalDate birthDate, Gender gender, Idul idul, Email email,
            Password password) {
        return new Account(name, birthDate, gender, idul, email, password);
    }
}
