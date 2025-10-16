package ca.ulaval.glo4003.trotti.domain.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Traveler.Traveler;
import java.util.List;

public interface TravelerRepository {

    List<Traveler> findAll();

    void update(Traveler traveler);
}
