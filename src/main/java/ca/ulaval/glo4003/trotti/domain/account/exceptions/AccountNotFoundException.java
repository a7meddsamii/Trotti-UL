package ca.ulaval.glo4003.trotti.domain.account.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("Account has not been found");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
