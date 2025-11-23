package ca.ulaval.glo4003.trotti.trip.api.mappers;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.TripHistory;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.TripDto;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TripApiMapper {

    public StartTripDto toStartTripDto(Idul idul, StartTripRequest request) {
        if (request == null) {
            throw new InvalidParameterException("StartTripRequest cannot be null");
        }
        RidePermitId ridePermitId = RidePermitId.from(request.ridePermitId());
        Location location = Location.of(request.location());
        SlotNumber slotNumber = parseSlotNumber(request.slotNumber());

        return new StartTripDto(idul, ridePermitId, String.valueOf(request.unlockCode()), location,
                slotNumber);
    }

    public EndTripDto toEndTripDto(Idul idul, EndTripRequest request) {
        if (request == null) {
            throw new InvalidParameterException("EndTripRequest cannot be null");
        }

        Location location = Location.of(request.location());
        SlotNumber slotNumber = parseSlotNumber(request.slotNumber());

        return new EndTripDto(idul, location, slotNumber);
    }

    public TripHistory toTripHistory(List<TripDto> tripDtos) {
        Duration totalDuration = tripDtos.stream()
                .map(d -> Duration.between(d.startTime(), d.endTime()))
                .reduce(Duration.ZERO, Duration::plus);

        int numberOfTrips = tripDtos.size();

        Duration averageDuration = numberOfTrips == 0
                ? Duration.ZERO
                : totalDuration.dividedBy(numberOfTrips);

        String favoriteStartStation = tripDtos.stream()
                .map(t -> t.startLocation().getBuilding() + " - " + t.startLocation().getSpotName())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No favorite start station for now");

        String favoriteEndStation = tripDtos.stream()
                .map(t -> t.endLocation().getBuilding() + " - " + t.endLocation().getSpotName())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No favorite end station for now");

        List<TripHistory.TripResponse> tripResponses =
                tripDtos.stream()
                        .map(t -> new TripHistory.TripResponse(
                                t.id().toString(),
                                t.startLocation().getBuilding() + " - " + t.startLocation().getSpotName(),
                                t.endLocation().getBuilding() + " - " + t.endLocation().getSpotName(),
                                t.startTime(),
                                Duration.between(t.startTime(), t.endTime())
                        ))
                        .toList();

        return new TripHistory(
                totalDuration,
                numberOfTrips,
                averageDuration,
                favoriteStartStation,
                favoriteEndStation,
                tripResponses
        );
    }

    private SlotNumber parseSlotNumber(String slotNumberValue) {
        try {
            int number = Integer.parseInt(slotNumberValue);
            return new SlotNumber(number);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Slot number must be an integer value");
        }
    }
}
