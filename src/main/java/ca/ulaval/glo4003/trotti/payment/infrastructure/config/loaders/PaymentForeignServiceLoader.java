package ca.ulaval.glo4003.trotti.payment.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.loaders.Bootstrapper;
import ca.ulaval.glo4003.trotti.payment.domain.security.DataCodec;
import ca.ulaval.glo4003.trotti.payment.infrastructure.security.AesDataCodecAdapter;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class PaymentForeignServiceLoader extends Bootstrapper {

    @Override
    public void load() {
        this.resourceLocator.register(DataCodec.class,
                new AesDataCodecAdapter(generateSecretKey()));
    }

    private SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate secret key", e);
        }
    }
}
