package ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.mappers.FleetPersistenceMapper;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records.FleetRecord;

public class InMemoryFleetRepository implements FleetRepository {
    private final FleetPersistenceMapper fleetPersistenceMapper;
    private FleetRecord fleetRecord;

    public InMemoryFleetRepository(
            FleetRecord fleetRecord,
            FleetPersistenceMapper fleetPersistenceMapper) {
        this.fleetRecord = fleetRecord;
        this.fleetPersistenceMapper = fleetPersistenceMapper;
    }

    @Override
    public Fleet getFleet() {
        return fleetPersistenceMapper.toDomain(fleetRecord);
    }

    @Override
    public void save(Fleet fleet) {
        fleetRecord = fleetPersistenceMapper.toRecord(fleet);
    }
}
