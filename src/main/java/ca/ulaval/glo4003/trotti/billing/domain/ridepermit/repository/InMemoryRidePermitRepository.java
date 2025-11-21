package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

import java.time.LocalDate;
import java.util.*;

public class InMemoryRidePermitRepository implements RidePermitRepository{
    private final Map<RidePermitId, RidePermit> database = new HashMap<>();
    
    @Override
    public void save(RidePermit ridePermit) {
        database.put(ridePermit.getId(), ridePermit);
    }
    
    @Override
    public Optional<RidePermit> findById(RidePermitId id) {
        return Optional.ofNullable(database.get(id));
    }
    
    @Override
    public List<RidePermit> findAllByIdul(Idul idul) {
        return database.values().stream()
                .filter(permit -> permit.getIdul().equals(idul))
                .toList();
    }
    
    @Override
    public List<RidePermit> findAllByDate(LocalDate date) {
        return database.values().stream()
                .filter(permit -> permit.getDailyBillingUsages().containsKey(date))
                .toList();
    }
    
}