package ca.ulaval.glo4003.trotti.domain.account.services;

public interface PasswordHasher {
    String hash(String plainPassword);
}
