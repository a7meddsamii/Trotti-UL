package ca.ulaval.glo4003.trotti.commons.infrastructure.services;

import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class Argon2PasswordHasherAdapter implements PasswordHasher {

    private final int memoryCost;
    private final int iterations;
    private final int threads;
    private final Argon2 argon2;

    public Argon2PasswordHasherAdapter(int memoryCost, int iterations, int threads) {
        this.memoryCost = memoryCost;
        this.iterations = iterations;
        this.threads = threads;
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    @Override
    public String hash(String plainPassword) {
        char[] material = plainPassword.toCharArray();
        try {
            return argon2.hash(iterations, memoryCost, threads, material);
        } finally {
            Arrays.fill(material, '\0');
        }
    }

    @Override
    public boolean matches(String plainPassword, String hashedPassword) {
        if (StringUtils.isBlank(hashedPassword))
            return false;
        char[] material = plainPassword.toCharArray();
        try {
            return argon2.verify(hashedPassword, material);
        } finally {
            Arrays.fill(material, '\0');
        }
    }
}
