package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class UnlockCode {

    private static final int LIFE_SPAN_IN_SECONDS = 60;
    private static final int MINIMUM_CODE_VALUE = 1000;
    private static final int MAXIMUM_CODE_VALUE = 999999;

    private final Idul travelerId;
    private final String code;
    private final Instant expiresAt;
    private final Clock clock;

    private UnlockCode(String code, Instant expiresAt, Idul travelerId, Clock clock) {
        this.code = code;
        this.expiresAt = expiresAt;
        this.travelerId = travelerId;
        this.clock = clock;
    }

    public static UnlockCode generateFromTravelerId(Idul id, Clock clock) {
        Random random = new SecureRandom();

        int code = MINIMUM_CODE_VALUE + random.nextInt(MAXIMUM_CODE_VALUE - MINIMUM_CODE_VALUE + 1);
        Instant expiresAt = clock.instant().plusSeconds(LIFE_SPAN_IN_SECONDS);

        return new UnlockCode(String.valueOf(code), expiresAt, id, clock);
    }

    public Duration getRemainingTime() {
        return Duration.between(clock.instant(), expiresAt);
    }

    public String getCode() {
        return code;
    }

    public Idul getTravelerId() {
        return travelerId;
    }

    public boolean isExpired() {
        return clock.instant().isAfter(expiresAt);
    }

    public boolean isCorrectValue(String codeValue) {
        return codeValue.equals(code);
    }
}
