package ca.ulaval.glo4003.trotti.account.infrastructure.config;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Gender;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.account.domain.values.Password;
import java.time.LocalDate;
import java.util.List;

public final class AccountDevDataFactory {

    private final AccountRepository accountRepository;
    private final PasswordHasher hasher;

    public AccountDevDataFactory(
            AccountRepository accountRepository,
            PasswordHasher passwordHasher) {
        this.accountRepository = accountRepository;
        this.hasher = passwordHasher;
    }

    public void run() {
        List<Account> accounts = List.of(
                build("Alexandra Tremblay", LocalDate.of(2000, 5, 10), Gender.FEMALE,
                        Idul.from("111111111"), Email.from("alexandra@ulaval.ca"), "Ulaval2000$!"),
                build("Samuel LeGrand", LocalDate.of(1999, 2, 3), Gender.MALE,
                        Idul.from("222222222"), Email.from("samuel@ulaval.ca"), "Ulaval1997#"),
                build("Rihab Ulaval", LocalDate.of(2000, 9, 18), Gender.FEMALE,
                        Idul.from("333333333"), Email.from("rihab@ulaval.ca"), "UlavalRihab18@"));

        accounts.forEach(accountRepository::save);
    }

    private Account build(String name, LocalDate birthDate, Gender gender, Idul idul, Email email,
            String rawPassword) {
        Password password = Password.fromPlain(rawPassword, hasher);
        return new Account(name, birthDate, gender, idul, email, password);
    }
}
