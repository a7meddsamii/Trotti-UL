package ca.ulaval.glo4003.trotti.commons.domain.service;

public interface PasswordHasher {
    String hash(String plainPassword);

    boolean matches(String plainPassword, String hashedPassword);
}
