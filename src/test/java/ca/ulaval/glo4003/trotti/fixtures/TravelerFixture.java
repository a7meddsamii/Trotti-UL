package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;
import java.util.List;

public class TravelerFixture {

    private static final Idul AN_IDUL = Idul.from("IDUL");
    private static final Email AN_EMAIL = Email.from("john.doe@ulaval.ca");

    public Traveler buildWithNoRidePermit() {
        return new Traveler(AN_IDUL, AN_EMAIL, List.of());
    }

    public Traveler buildWithRidePermit() {
        return new Traveler(AN_IDUL, AN_EMAIL, List.of(new RidePermitFixture().build()));
    }
}
