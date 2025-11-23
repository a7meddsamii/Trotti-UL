package ca.ulaval.glo4003.trotti.billing.api.ridepermit.dto.response;

public record RidePermitResponse(
		String ridePermitId,
		String riderId,
		String session,
		String maximumTravelingTimePerDay,
		String permitState,
		String balance
) {
}
