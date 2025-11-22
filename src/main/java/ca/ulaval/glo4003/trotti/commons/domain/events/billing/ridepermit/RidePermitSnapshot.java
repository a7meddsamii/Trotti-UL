package ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit;

import java.time.LocalDate;

public record RidePermitSnapshot(String riderId,
								 String ridePermitId,
								 String session,
								 LocalDate sessionStartDate,
								 LocalDate sessionExpirationDate) {
	
}
