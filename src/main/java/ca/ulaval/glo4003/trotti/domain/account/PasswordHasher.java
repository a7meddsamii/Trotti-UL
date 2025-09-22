package ca.ulaval.glo4003.trotti.domain.account;

public interface PasswordHasher {
    String hash(String password);

    boolean matches(String password, String storedPassword);
}
