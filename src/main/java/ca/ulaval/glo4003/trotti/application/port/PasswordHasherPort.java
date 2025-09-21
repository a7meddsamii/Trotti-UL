package ca.ulaval.glo4003.trotti.application.port;

public interface PasswordHasherPort {
    String hash(char[] password);

    boolean verify(char[] password, String storedPassword);
}
