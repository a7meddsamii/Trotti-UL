package ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;

public record RidePermitSnapshot(Idul idul,
                                 String ridePermitId,
                                 String session,
                                 LocalDate sessionStartDate,
                                 LocalDate sessionExpirationDate) {
	
}
