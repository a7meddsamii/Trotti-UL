package ca.ulaval.glo4003.trotti.trip.infrastructure.store;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import java.util.Objects;

public class UnlockCodeKey {

    private final Idul idul;
    private final RidePermitId ridePermitId;

    private UnlockCodeKey(Idul idul, RidePermitId ridePermitId) {
        this.idul = idul;
        this.ridePermitId = ridePermitId;
    }

    public static UnlockCodeKey from(Idul idul, RidePermitId ridePermitId) {
        return new UnlockCodeKey(idul, ridePermitId);
    }

    public Idul getIdul() {
        return idul;
    }

    public RidePermitId getRidePermitId() {
        return ridePermitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UnlockCodeKey that = (UnlockCodeKey) o;

        return Objects.equals(idul, that.idul) && Objects.equals(ridePermitId, that.ridePermitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idul, ridePermitId);
    }
}
