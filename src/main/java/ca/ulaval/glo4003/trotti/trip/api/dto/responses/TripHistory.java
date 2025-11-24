package ca.ulaval.glo4003.trotti.trip.api.dto.responses;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public record TripHistory(Duration totalDuration,
                          int numberOfTrips,
                          Duration averageTripDuration,
                          String favoriteStartStation,
                          String favoriteEndStation,
                          List<TripResponse> trips) {

    public record TripResponse(
            String tripId,
            String startLocation,
            String endLocation,
            LocalDateTime startDateTime,
            Duration duration
    ) {
    }
}
