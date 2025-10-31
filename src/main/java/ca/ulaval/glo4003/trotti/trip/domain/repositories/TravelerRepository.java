package ca.ulaval.glo4003.trotti.trip.domain.repositories;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import java.util.List;

public interface TravelerRepository {

    List<Traveler> findAll();

    Traveler findByIdul(Idul idul);

    void update(Traveler traveler);
}
