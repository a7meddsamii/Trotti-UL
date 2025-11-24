package ca.ulaval.glo4003.trotti.commons.domain.events.trip;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import java.time.LocalDateTime;

public class UnlockCodeRequestedEvent extends Event {

    private final String ridePermitId;
    private final String unlockCode;
    private final LocalDateTime expirationTime;

    public UnlockCodeRequestedEvent(
            Idul idul,
            String ridePermitId,
            String unlockCode,
            LocalDateTime expirationTime) {
        super(idul, "trip.unlock_code.requested");
        this.ridePermitId = ridePermitId;
        this.unlockCode = unlockCode;
        this.expirationTime = expirationTime;
    }

    public String getRidePermitId() {
        return ridePermitId;
    }

    public String getUnlockCode() {
        return unlockCode;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }
}
