package ca.ulaval.glo4003.trotti.infrastructure.auth.argon;

import ca.ulaval.glo4003.trotti.application.port.PasswordHasherPort;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.util.Arrays;
import org.eclipse.jetty.util.StringUtil;

public class ArgonPasswordHasherPortAdapter implements PasswordHasherPort {

    private final ArgonHasherConfig config;
    private final Argon2 argon2;

    public ArgonPasswordHasherPortAdapter(ArgonHasherConfig config) {
        this.config = config;
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    @Override
    public String hash(char[] password) {
        if (password == null || password.length == 0) {
            throw new IllegalArgumentException(" Password must not be empty");
        }
        char[] material = password.clone();
        try {
            return argon2.hash(config.iterations(), config.memoryCost(), config.threads(),
                    material);
        } finally {
            Arrays.fill(material, '\0');
        }
    }

    @Override
    public boolean verify(char[] password, String storedHashedPassword) {
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
