package ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.repository;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

import java.time.LocalDate;
import java.util.*;

public class InMemoryRidePermitRepository implements RidePermitRepository {
    private final Map<RidePermitId, RidePermit> database = new HashMap<>();

    @Override
    public void save(RidePermit ridePermit) {
        database.put(ridePermit.getId(), ridePermit);
    }

    @Override
    public void saveAll(List<RidePermit> ridePermits) {
        ridePermits.forEach(this::save);
    }

    @Override
    public Optional<RidePermit> findById(RidePermitId ridePermitId) {
        return Optional.ofNullable(database.get(ridePermitId));
    }

    @Override
    public Optional<RidePermit> findByRiderIdAndRidePermitId(Idul riderId,
            RidePermitId ridePermitId) {
        return database.values().stream().filter(permit -> permit.getId().equals(ridePermitId)
                && permit.getRiderId().equals(riderId)).findFirst();
    }

    @Override
    public List<RidePermit> findAllByIdul(Idul idul) {
        return database.values().stream().filter(permit -> permit.getRiderId().equals(idul))
                .toList();
    }

    @Override
    public List<RidePermit> findAllByDate(LocalDate date) {
        return database.values().stream().filter(permit -> permit.getSession().contains(date))
                .toList();
    }

    @Override
    public List<RidePermit> findAllBySession(Session session) {
        return database.values().stream().filter(permit -> permit.getSession().equals(session))
                .toList();
    }
}
