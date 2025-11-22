package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Objects;
import java.util.Random;

public class UnlockCode {

    private static final int MINIMUM_CODE_VALUE = 1000;
    private static final int MAXIMUM_CODE_VALUE = 999999;
    private static final TemporalAmount SIXTY_SECONDS = Duration.ofSeconds(60);

    private final Idul travelerId;
    private final String code;
    private final RidePermitId ridePermitId;
    private final LocalDateTime expiresAt;

    private UnlockCode(
            String code,
            Idul travelerId,
            RidePermitId ridePermitId,
            LocalDateTime expiresAt) {
        this.code = code;
        this.travelerId = travelerId;
        this.ridePermitId = ridePermitId;
        this.expiresAt = expiresAt;
    }

    public static UnlockCode generate(Idul id, RidePermitId ridePermitId, Clock clock) {
        Random random = new SecureRandom();

        int code = MINIMUM_CODE_VALUE + random.nextInt(MAXIMUM_CODE_VALUE - MINIMUM_CODE_VALUE + 1);

        return new UnlockCode(String.valueOf(code), id, ridePermitId,
                LocalDateTime.now(clock).plus(SIXTY_SECONDS));
    }

    public String getCode() {
        return code;
    }

    public Idul getTravelerId() {
        return travelerId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public RidePermitId getRidePermitId() {
        return ridePermitId;
    }

    public boolean matches(Idul idul, RidePermitId ridePermitId, String code) {
        return this.travelerId.equals(idul) && this.ridePermitId.equals(ridePermitId)
                && this.code.equals(code);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        UnlockCode that = (UnlockCode) o;

        return this.code.equals(that.code) && this.travelerId.equals(that.travelerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(travelerId, code);
    }
}
