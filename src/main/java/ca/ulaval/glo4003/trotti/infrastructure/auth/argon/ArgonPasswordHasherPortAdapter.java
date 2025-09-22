package ca.ulaval.glo4003.trotti.infrastructure.auth.argon;

import ca.ulaval.glo4003.trotti.application.port.PasswordHasherPort;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.util.Arrays;
import org.eclipse.jetty.util.StringUtil;

public class ArgonPasswordHasherPortAdapter implements PasswordHasherPort {

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
    public String hash(char[] password) {
        char[] material = password.clone();
        try {
            return argon2.hash(iterations, memoryCost, threads,
                    material);
        } finally {
            Arrays.fill(material, '\0');
        }
    }

    @Override
    public boolean matches(char[] password, String storedHashedPassword) {
        if (StringUtil.isBlank(storedHashedPassword))
            return false;
        if (password == null || password.length == 0)
            return false;
        char[] material = password.clone();
        try {
            return argon2.verify(storedHashedPassword, material);
        } finally {
            Arrays.fill(material, '\0');
        }
    }
}
