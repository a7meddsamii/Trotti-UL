package ca.ulaval.glo4003.trotti.infrastructure.dataFactory;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Gender;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.account.values.Password;
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

        for (Account account : accounts) {
            if (!exists(account.getEmail())) {
                accountRepository.save(account);
            }
        }
    }

    private Account build(String name, LocalDate birthDate, Gender gender, Idul idul, Email email,
            String rawPassword) {
        Password password = Password.fromPlain(rawPassword, hasher);
        return new Account(name, birthDate, gender, idul, email, password);
    }

    private boolean exists(Email email) {
        return accountRepository.findByEmail(email).isPresent();
    }
}
