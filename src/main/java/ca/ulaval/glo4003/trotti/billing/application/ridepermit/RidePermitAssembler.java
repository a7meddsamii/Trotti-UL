package ca.ulaval.glo4003.trotti.billing.application.ridepermit;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;

import java.util.List;

public class RidePermitAssembler {
	public RidePermitDto assemble(RidePermit ridePermit) {
		return null;
	}
	
	public List<RidePermitDto> assemble(List<RidePermit> ridePermits) {
		return ridePermits.stream()
				.map(this::assemble)
				.toList();
	}
}
