package ca.ulaval.glo4003.trotti.billing.domain.payment.values;

import ca.ulaval.glo4003.trotti.billing.domain.payment.security.DataCodec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class SecuredStringTest {

    private static final String VALID_CARD_NUMBER = "4111111111111111";
    private static final String EXPECTED_MASKED_CARD = "1111";
    private static final String ENCODED_VALUE = "ENCODED_DATA";
    private static final String SHORT_STRING = "123";
    private static final String FOUR_CHARACTERS_STRING = "1234";

    private DataCodec codec;

    @BeforeEach
    void setUp() {
        codec = Mockito.mock(DataCodec.class);
        Mockito.when(codec.encode(Mockito.anyString())).thenReturn(ENCODED_VALUE);
    }

    @Test
    void givenValidCardNumber_whenFromPlain_thenMasksLastFourDigits() {
        SecuredString result = SecuredString.fromPlain(VALID_CARD_NUMBER, codec);

        Assertions.assertEquals(EXPECTED_MASKED_CARD, result.getMasked());
        Assertions.assertEquals(ENCODED_VALUE, result.getEncoded());
    }

    @Test
    void givenExactlyFourCharacters_whenFromPlain_thenShowsAllCharacters() {
        SecuredString result = SecuredString.fromPlain(FOUR_CHARACTERS_STRING, codec);

        Assertions.assertEquals(FOUR_CHARACTERS_STRING, result.getMasked());
    }

    @Test
    void givenLessThanFourCharacters_whenFromPlain_thenThrowsException() {
        Executable executable = () -> SecuredString.fromPlain(SHORT_STRING, codec);

        Assertions.assertThrows(StringIndexOutOfBoundsException.class, executable);
    }

}
