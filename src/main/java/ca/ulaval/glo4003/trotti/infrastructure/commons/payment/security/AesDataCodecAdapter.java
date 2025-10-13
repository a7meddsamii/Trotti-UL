package ca.ulaval.glo4003.trotti.infrastructure.commons.payment.security;

import ca.ulaval.glo4003.trotti.domain.commons.payment.exceptions.CodecException;
import ca.ulaval.glo4003.trotti.domain.commons.payment.security.DataCodec;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import org.apache.commons.lang3.math.NumberUtils;

public class AesDataCodecAdapter implements DataCodec {

    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int IV_LENGTH = 12;

    private final SecretKey secretKey;
    private final SecureRandom secureRandom = new SecureRandom();

    public AesDataCodecAdapter(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String encode(String data) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH, iv));

            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            byte[] combined = new byte[iv.length + encryptedBytes.length];

            System.arraycopy(iv, NumberUtils.INTEGER_ZERO, combined, NumberUtils.INTEGER_ZERO,
                    iv.length);
            System.arraycopy(encryptedBytes, NumberUtils.INTEGER_ZERO, combined, iv.length,
                    encryptedBytes.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new CodecException("Failed to encode data");
        }
    }

    @Override
    public String decode(String data) {
        try {
            byte[] decoded = Base64.getDecoder().decode(data);

            byte[] iv = new byte[IV_LENGTH];
            System.arraycopy(decoded, NumberUtils.INTEGER_ZERO, iv, NumberUtils.INTEGER_ZERO,
                    iv.length);

            byte[] ciphertext = new byte[decoded.length - iv.length];
            System.arraycopy(decoded, iv.length, ciphertext, NumberUtils.INTEGER_ZERO,
                    ciphertext.length);

            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH, iv));

            return new String(cipher.doFinal(ciphertext));
        } catch (Exception e) {
            throw new CodecException("Failed to decode data");
        }
    }
}
