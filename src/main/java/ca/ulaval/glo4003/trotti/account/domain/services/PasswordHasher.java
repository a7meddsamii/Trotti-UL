package ca.ulaval.glo4003.trotti.account.domain.services;

public interface PasswordHasher {
    String hash(String plainPassword);

    boolean matches(String plainPassword, String hashedPassword);
}
