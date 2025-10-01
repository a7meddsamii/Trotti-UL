package ca.ulaval.glo4003.trotti.infrastructure.payment;

import ca.ulaval.glo4003.trotti.domain.payment.exceptions.EncodingException;
import ca.ulaval.glo4003.trotti.domain.payment.services.DataEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AesDataEncoderAdapter implements DataEncoder {

    private static final int IV_SIZE = 12;
    private static final int TAG_LENGTH = 128;
    private final SecretKey key;
    private final SecureRandom random = new SecureRandom();

    public AesDataEncoderAdapter(byte[] keyBytes) {
        this.key = new SecretKeySpec(keyBytes, "AES");
    }

    @Override
    public String encode(String data) {
        try {
            byte[] iv = new byte[IV_SIZE];
            random.nextBytes(iv);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, iv));
            byte[] cipherText = cipher.doFinal(data.getBytes());
            byte[] out = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, out, 0, iv.length);
            System.arraycopy(cipherText, 0, out, iv.length, cipherText.length);
            return Base64.getEncoder().encodeToString(out);
        } catch (Exception e) {
            throw new EncodingException("Failed to encode.");
        }
    }

    @Override
    public String decode(String data) {
        try {
            byte[] all = Base64.getDecoder().decode(data);
            byte[] iv = new byte[IV_SIZE];
            System.arraycopy(all, 0, iv, 0, iv.length);
            byte[] cipherText = new byte[all.length - iv.length];
            System.arraycopy(all, iv.length, cipherText, 0, cipherText.length);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, iv));
            return new String(cipher.doFinal(cipherText));
        } catch (Exception e) {
            throw new EncodingException("Failed to decode.");
        }
    }
}
