package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.billing.domain.payment.security.DataCodec;
import ca.ulaval.glo4003.trotti.billing.infrastructure.payment.AesDataCodecAdapter;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class BillingForeignServiceLoader extends Bootstrapper {

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
