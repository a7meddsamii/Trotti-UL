package ca.ulaval.glo4003.trotti.domain.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.util.List;

public interface TravelerRepository {

    List<Traveler> findAll();

    Traveler findByIdul(Idul idul);

    void update(Traveler traveler);
}
