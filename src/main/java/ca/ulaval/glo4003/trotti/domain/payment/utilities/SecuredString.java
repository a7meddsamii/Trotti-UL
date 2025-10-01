package ca.ulaval.glo4003.trotti.domain.payment.utilities;

import ca.ulaval.glo4003.trotti.domain.payment.services.DataEncoder;

public final class SecuredString {

    private static final int COUNT_OF_VISIBLE_CHARACTERS = 4;

    private final String encodedValue;
    private final String safeToShow;

    private SecuredString(String encodedValue, String safeToShow) {
        this.encodedValue = encodedValue;
        this.safeToShow = safeToShow;
    }

    public static SecuredString fromPlain(String plain, DataEncoder encoder) {
        String encoded = encoder.encode(plain);
        String safeToShow = plain.substring(plain.length() - COUNT_OF_VISIBLE_CHARACTERS);
        return new SecuredString(encoded, safeToShow);
    }

    public String getEncoded() {
        return encodedValue;
    }

    public String getMasked() {
        return safeToShow;
    }
}
