package ca.ulaval.glo4003.trotti.order.fixtures;

import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.Semester;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.values.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;

public class PassFixture {
    public static final MaximumDailyTravelTime A_MAXIMUM_TRAVELING_TIME =
            MaximumDailyTravelTime.from(Duration.ofMinutes(30));
    public static final Session A_SESSION = new Session(
			Semester.FALL,
			LocalDate.of(2025, Month.SEPTEMBER, 1), LocalDate.of(2025, Month.DECEMBER, 31));
    public static final BillingFrequency A_BILLING_FREQUENCY = BillingFrequency.PER_TRIP;

    public static final PassId AN_PASSID = PassId.randomId();
    private Idul idul = Idul.from("A1234567");
    private PassId id = AN_PASSID;

    public PassFixture withIdul(Idul idul) {
        this.idul = idul;
        return this;
    }

    public PassFixture withId(PassId id) {
        this.id = id;
        return this;
    }

    public Pass build() {
        return new Pass(A_MAXIMUM_TRAVELING_TIME, A_SESSION, A_BILLING_FREQUENCY, id, idul);
    }
}
