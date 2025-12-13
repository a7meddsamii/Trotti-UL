package ca.ulaval.glo4003.trotti.billing.infrastructure.payment.security;

import ca.ulaval.glo4003.trotti.billing.domain.payment.exceptions.CodecException;
import ca.ulaval.glo4003.trotti.billing.domain.payment.security.DataCodec;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import ca.ulaval.glo4003.trotti.billing.infrastructure.payment.AesDataCodecAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AesDataCodecAdapterTest {

    private static final String RAW_STRING = "This is a test string.";
    private static final String INVALID_ENCODED_STRING = "InvalidEncodedString";
    private DataCodec aesDataCodec;

    @BeforeEach
    void setup() {
        aesDataCodec = new AesDataCodecAdapter(generateSecretKey());
    }

    @Test
    void givenString_whenEncode_thenReturnEncodedString() {
        String encodedString = aesDataCodec.encode(RAW_STRING);

        Assertions.assertNotEquals(RAW_STRING, encodedString);
    }

    @Test
    void givenEncodedString_whenDecode_thenReturnOriginalString() {
        String encodedString = aesDataCodec.encode(RAW_STRING);

        String decodedString = aesDataCodec.decode(encodedString);

        Assertions.assertEquals(RAW_STRING, decodedString);
    }

    @Test
    void givenNullString_whenEncode_thenThrowException() {
        Executable encodeAction = () -> aesDataCodec.encode(null);

        Assertions.assertThrows(CodecException.class, encodeAction);
    }

    @Test
    void givenInvalidEncodedString_whenDecode_thenThrowException() {
        Executable decodeAction = () -> aesDataCodec.decode(INVALID_ENCODED_STRING);

        Assertions.assertThrows(CodecException.class, decodeAction);
    }

    private SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate secret key for tests", e);
        }
    }
}
