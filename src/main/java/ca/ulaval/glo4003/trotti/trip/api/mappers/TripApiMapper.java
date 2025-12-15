package ca.ulaval.glo4003.trotti.trip.api.mappers;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.TripQueryRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.TripHistoryResponse;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.CompletedTrip;
import ca.ulaval.glo4003.trotti.trip.domain.entities.TripHistory;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripHistorySearchCriteria;

public class TripApiMapper {

    private static final String NOTHING = "N/A";

    public StartTripDto toStartTripDto(Idul idul, StartTripRequest request) {
        RidePermitId ridePermitId = RidePermitId.from(request.ridePermitId());
        Location location = Location.of(request.location());
        SlotNumber slotNumber = SlotNumber.from(request.slotNumber());

        return new StartTripDto(idul, ridePermitId, String.valueOf(request.unlockCode()), location,
                slotNumber);
    }

    public TripHistorySearchCriteria toTripHistorySearchCriteria(Idul idul,
            TripQueryRequest request) {
        return TripHistorySearchCriteria.builder().withIdul(idul)
                .withStartDate(request.startDate()).withEndDate(request.endDate()).build();
    }

    public EndTripDto toEndTripDto(Idul idul, EndTripRequest request) {
        Location location = Location.of(request.location());
        SlotNumber slotNumber = SlotNumber.from(request.slotNumber());

        return new EndTripDto(idul, location, slotNumber);
    }

    public TripHistoryResponse toTripHistoryResponse(TripHistory tripHistory) {
        return new TripHistoryResponse(tripHistory.calculateTotalTripsDuration(),
                tripHistory.calculateNumberOfTrips(), tripHistory.calculateAverageTripDuration(),
                tripHistory.getFavoriteStartLocation() != null
                        ? tripHistory.getFavoriteStartLocation().getBuilding()
                        : NOTHING,
                tripHistory.getFavoriteEndLocation() != null
                        ? tripHistory.getFavoriteEndLocation().getBuilding()
                        : NOTHING,
                tripHistory.getCompletedTrips().stream().map(this::toTripDtoResponse).toList());
    }

    private TripHistoryResponse.TripResponse toTripDtoResponse(CompletedTrip trip) {
        return new TripHistoryResponse.TripResponse(trip.getTripId().toString(),
                trip.getStartLocation().getBuilding(), trip.getEndLocation().getBuilding(),
                trip.getStartTime(), trip.getEndTime(), trip.calculateDuration().toMinutes());
    }
}
