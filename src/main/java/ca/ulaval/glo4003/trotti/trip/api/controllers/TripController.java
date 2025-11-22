package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.UnlockCodeResponse;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.TripApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import jakarta.ws.rs.core.Response;


public class TripController implements TripResource {

    private final TripApplicationService tripApplicationService;
    private final TripApiMapper tripApiMapper;

    public TripController(
            TripApplicationService tripApplicationService,
            TripApiMapper tripApiMapper) {
        this.tripApplicationService = tripApplicationService;
        this.tripApiMapper = tripApiMapper;
    }

    @Override
    public Response startTrip(Idul userId, StartTripRequest request) {

        StartTripDto startTripDto = tripApiMapper.toStartTripDto(userId, request);

        tripApplicationService.startTrip(startTripDto);

        return Response.ok().build();
    }

    @Override
    public Response endTrip(Idul userId, EndTripRequest request) {

        EndTripDto endTripDto = tripApiMapper.toEndTripDto(userId, request);

        tripApplicationService.endTrip(endTripDto);

        return Response.ok().build();
    }
	
	@Override
	public Response requestUnlockCode(Idul userId, String ridePermitIdValue) {
		RidePermitId ridePermitId = RidePermitId.from(ridePermitIdValue);
		tripApplicationService.generateUnlockCode(userId, ridePermitId);
		
		return Response.ok().entity(new UnlockCodeResponse("Unlock Code is generated successfully and sent by e-mail.")).build();
	}
}
