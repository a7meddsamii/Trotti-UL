package ca.ulaval.glo4003.trotti.domain.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.station.entities.Station;

public interface StationRepository {

    void save(Station station);

    Station findById(String stationId);

    void update(Station station);
}
