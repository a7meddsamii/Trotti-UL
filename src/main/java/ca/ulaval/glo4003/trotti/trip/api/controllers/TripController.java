package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.TripQueryRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.UnlockCodeResponse;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.TripCommandApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.TripQueryApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.TripHistory;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripHistorySearchCriteria;
import jakarta.ws.rs.core.Response;

public class TripController implements TripResource {

    private final TripCommandApplicationService tripCommandApplicationService;
    private final TripQueryApplicationService tripQueryApplicationService;
    private final TripApiMapper tripApiMapper;

    public TripController(
            TripCommandApplicationService tripCommandApplicationService,
            TripQueryApplicationService tripQueryApplicationService,
            TripApiMapper tripApiMapper) {
        this.tripCommandApplicationService = tripCommandApplicationService;
        this.tripQueryApplicationService = tripQueryApplicationService;
        this.tripApiMapper = tripApiMapper;
    }

    @Override
    public Response startTrip(Idul userId, StartTripRequest request) {

        StartTripDto startTripDto = tripApiMapper.toStartTripDto(userId, request);

        tripCommandApplicationService.startTrip(startTripDto);

        return Response.ok().build();
    }

    @Override
    public Response endTrip(Idul userId, EndTripRequest request) {
        EndTripDto endTripDto = tripApiMapper.toEndTripDto(userId, request);

        tripCommandApplicationService.endTrip(endTripDto);

        return Response.ok().build();
    }

    @Override
    public Response requestUnlockCode(Idul userId, String ridePermitIdValue) {
        RidePermitId ridePermitId = RidePermitId.from(ridePermitIdValue);
        tripCommandApplicationService.generateUnlockCode(userId, ridePermitId);

        return Response.ok().entity(
                new UnlockCodeResponse("Unlock Code is generated successfully and sent by e-mail."))
                .build();
    }

    @Override
    public Response getTripHistory(Idul userId, TripQueryRequest request) {
        TripHistorySearchCriteria searchCriteria = tripApiMapper.toTripHistorySearchCriteria(userId, request);

        TripHistory tripHistory = tripQueryApplicationService.getTripHistory(searchCriteria);

        return Response.ok().entity(tripApiMapper.toTripHistoryResponse(tripHistory)).build();
    }

}
