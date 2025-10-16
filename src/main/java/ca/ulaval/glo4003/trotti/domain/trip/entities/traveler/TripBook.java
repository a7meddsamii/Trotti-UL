package ca.ulaval.glo4003.trotti.domain.trip.entities.traveler;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.TripBookException;
import java.time.LocalDateTime;
import java.util.List;

public class TripBook {

    private final List<Trip> unfinishedTrips;

    public TripBook(List<Trip> unfinishedTrips) {
        this.unfinishedTrips = unfinishedTrips;
    }

    public void add(Trip startTrip) {
        if (startTrip == null) {
            throw new TripBookException("Trip cannot be null");
        }
        if (unfinishedTrips.contains(startTrip)) {
            throw new TripBookException("Trip already exists");
        }
        unfinishedTrips.add(startTrip);
    }

    public Trip endTrip(Id tripId, LocalDateTime endDateTime) {
        Trip endtrip = unfinishedTrips.stream().filter(trip -> trip.getId().equals(tripId))
                .findFirst().orElse(null);
        if (endtrip == null) {
            throw new TripBookException("Trip does not exist");
        }
        unfinishedTrips.remove(endtrip);
        return endtrip.end(endDateTime);
    }

    public List<Trip> getTrips() {
        return List.copyOf(unfinishedTrips);
    }

}
