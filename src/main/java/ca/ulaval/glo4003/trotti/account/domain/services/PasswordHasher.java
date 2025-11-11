package ca.ulaval.glo4003.trotti.account.domain.services;

/**
 * @deprecated This is temporary to facilitate the merge between the account module and
 *             authentication module. This interface will be deleted later
 * 
 *             Use {@link ca.ulaval.glo4003.trotti.commons.domain.service.PasswordHasher} instead.
 */
public interface PasswordHasher
        extends ca.ulaval.glo4003.trotti.commons.domain.service.PasswordHasher {
    String hash(String plainPassword);

    boolean matches(String plainPassword, String hashedPassword);
}
