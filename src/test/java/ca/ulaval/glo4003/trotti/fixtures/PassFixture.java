package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;

public class PassFixture {
    public static final MaximumDailyTravelTime A_MAXIMUM_TRAVELING_TIME =
            MaximumDailyTravelTime.from(Duration.ofMinutes(30));
    public static final Session A_SESSION = new Session(Semester.FALL,
            LocalDate.of(2025, Month.SEPTEMBER, 1), LocalDate.of(2025, Month.DECEMBER, 31));
    public static final BillingFrequency A_BILLING_FREQUENCY = BillingFrequency.PER_TRIP;

    public Pass build() {
        return new Pass(A_MAXIMUM_TRAVELING_TIME, A_SESSION, A_BILLING_FREQUENCY);
    }
}
