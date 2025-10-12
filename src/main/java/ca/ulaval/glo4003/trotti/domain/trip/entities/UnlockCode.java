package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;

public class UnlockCode {

    private static final int LIFE_SPAN_IN_SECONDS = 60;
    private static final int MINIMUM_CODE_VALUE = 1000;
    private static final int MAXIMUM_CODE_VALUE = 999999;

    private final Id id;
    private final String code;
    private final Instant expiresAt;

    private UnlockCode(String code, Instant expiresAt, Id id) {
        this.code = code;
        this.expiresAt = expiresAt;
        this.id = id;
    }

    public static UnlockCode generateFromPass(Id id) {
        Random random = new SecureRandom();

        int code = MINIMUM_CODE_VALUE + random.nextInt(MAXIMUM_CODE_VALUE - MINIMUM_CODE_VALUE + 1);
        Instant expiresAt = Instant.now().plusSeconds(LIFE_SPAN_IN_SECONDS);

        return new UnlockCode(String.valueOf(code), expiresAt, id);
    }

    public String getCode() {
        return code;
    }

    public Id getLinkedPass() {
        return id;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnlockCode that = (UnlockCode) o;

        if (!id.equals(that.id)) return false;
        if (!code.equals(that.code)) return false;
        return expiresAt.equals(that.expiresAt);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + expiresAt.hashCode();
        return result;
    }
}
