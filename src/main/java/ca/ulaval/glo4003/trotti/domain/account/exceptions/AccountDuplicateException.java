package ca.ulaval.glo4003.trotti.domain.account.exceptions;

public class AccountDuplicateException extends RuntimeException {
    public AccountDuplicateException(String message) {
        super(message);
    }
}
