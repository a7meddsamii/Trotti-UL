package ca.ulaval.glo4003.trotti.infrastructure.auth.argon;

import ca.ulaval.glo4003.trotti.domain.account.PasswordHasher;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.util.Arrays;
import org.eclipse.jetty.util.StringUtil;

public class ArgonPasswordHasherPortAdapter implements PasswordHasher {

    private final int memoryCost;
    private final int iterations;
    private final int threads;
    private final Argon2 argon2;

    public ArgonPasswordHasherPortAdapter(int memoryCost, int iterations, int threads) {
        this.memoryCost = memoryCost;
        this.iterations = iterations;
        this.threads = threads;
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    @Override
    public String hash(String password) {
        char[] material = password.toCharArray();
        try {
            return argon2.hash(iterations, memoryCost, threads, material);
        } finally {
            Arrays.fill(material, '\0');
        }
    }

    @Override
    public boolean matches(String password, String storedHashedPassword) {
        if (StringUtil.isBlank(storedHashedPassword))
            return false;
        char[] material = password.toCharArray();
        try {
            return argon2.verify(storedHashedPassword, material);
        } finally {
            Arrays.fill(material, '\0');
        }
    }
}
