package ca.ulaval.glo4003.trotti.domain.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.util.Optional;

public interface StationRepository {

    void save(Station station);

    Optional<Station> findByLocation(Location location);

    Optional<Station> findByScooterId(Id scooterId);

}
