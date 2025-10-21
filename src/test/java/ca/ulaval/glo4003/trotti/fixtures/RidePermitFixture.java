package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import java.time.LocalDate;

public class RidePermitFixture {

    private static final Session A_SESSION = new Session(Semester.FALL,
            LocalDate.parse("2025-09-02"), LocalDate.parse("2025-12-12"));
    private static final Idul AN_IDUL = Idul.from("IDUL");
    private static final RidePermitId A_PERMIT_ID = RidePermitId.randomId();

    public RidePermit build() {
        return new RidePermit(A_PERMIT_ID, AN_IDUL, A_SESSION);
    }
}
