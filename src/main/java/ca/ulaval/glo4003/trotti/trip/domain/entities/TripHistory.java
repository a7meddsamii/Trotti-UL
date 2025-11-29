package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.trip.domain.values.Location;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TripHistory {

    private final List<CompletedTrip> completedTrips;

    public TripHistory(List<CompletedTrip> completedTrips) {
        this.completedTrips = completedTrips;
    }

    public Duration calculateTotalTripsDuration() {
        return completedTrips.stream()
                .map(CompletedTrip::calculateDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    public int calculateNumberOfTrips() {
        return completedTrips.size();
    }

    public Duration calculateAverageTripDuration() {
        int numberOfTrips = calculateNumberOfTrips();
        return numberOfTrips == 0 ? Duration.ZERO : calculateTotalTripsDuration().dividedBy(numberOfTrips);
    }

    public Location getFavoriteStartLocation() {
        return completedTrips.stream()
                .collect(Collectors.groupingBy(CompletedTrip::getStartLocation, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Location getFavoriteEndLocation() {
        return completedTrips.stream()
                .collect(Collectors.groupingBy(CompletedTrip::getEndLocation, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public List<CompletedTrip> getCompletedTrips() {
        return List.copyOf(completedTrips);
    }
}
