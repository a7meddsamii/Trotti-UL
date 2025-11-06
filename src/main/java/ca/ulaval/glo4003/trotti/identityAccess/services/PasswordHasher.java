package ca.ulaval.glo4003.trotti.identityAccess.services;

public interface PasswordHasher {
    String hash(String plainPassword);

    boolean matches(String plainPassword, String hashedPassword);
}
