package ca.ulaval.glo4003.trotti.trip.domain.repositories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;

public interface StationRepository {

    void save(Station station);

    Station findByLocation(Location location);

}
