package ca.ulaval.glo4003.trotti.domain.account;

public interface PasswordHasher {
    String hash(String plainPassword);

    boolean verify(String rawPassword, String hashedPassword);
}
