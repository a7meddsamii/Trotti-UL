package ca.ulaval.glo4003.trotti.domain.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;

public interface StationRepository {

    void save(Station station);

    Station findByLocation(Location location);

    Station findByScooterId(ScooterId scooterId);

}
