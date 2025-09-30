package ca.ulaval.glo4003.trotti.domain.trip.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;

import java.util.List;

public interface TravelerRepository {

    List<Traveler> findAll();

    void update(Traveler traveler);
}
