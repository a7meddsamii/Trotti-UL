package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;
import java.util.Random;

public class UnlockCode {

    private static final int LIFE_SPAN_IN_SECONDS = 60;
    private static final int MINIMUM_CODE_VALUE = 1000;
    private static final int MAXIMUM_CODE_VALUE = 999999;

    private final Id ridePermitId;
    private final String code;
    private final Instant expiresAt;
    private final Clock clock;

    private UnlockCode(String code, Instant expiresAt, Id ridePermitId, Clock clock) {
        this.code = code;
        this.expiresAt = expiresAt;
        this.ridePermitId = ridePermitId;
        this.clock = clock;
    }

    public static UnlockCode generateFromRidePermit(Id id, Clock clock) {
        Random random = new SecureRandom();

        int code = MINIMUM_CODE_VALUE + random.nextInt(MAXIMUM_CODE_VALUE - MINIMUM_CODE_VALUE + 1);
        Instant expiresAt = clock.instant().plusSeconds(LIFE_SPAN_IN_SECONDS);

        return new UnlockCode(String.valueOf(code), expiresAt, id, clock);
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public String getCode() {
        return code;
    }

    public Id getRidePermitId() {
        return ridePermitId;
    }

    public boolean isExpired() {
        return clock.instant().isAfter(expiresAt);
    }
}
