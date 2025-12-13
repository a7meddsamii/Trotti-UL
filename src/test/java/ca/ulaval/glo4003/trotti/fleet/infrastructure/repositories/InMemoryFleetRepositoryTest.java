package ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.mappers.FleetPersistenceMapper;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records.FleetRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class InMemoryFleetRepositoryTest {

    private Fleet fleet;
    private FleetRecord fleetRecord;
    private FleetPersistenceMapper fleetPersistenceMapper;

    private FleetRepository fleetRepository;

    @BeforeEach
    public void setup() {
        fleet = Mockito.mock(Fleet.class);
        fleetRecord = Mockito.mock(FleetRecord.class);
        fleetPersistenceMapper = Mockito.mock(FleetPersistenceMapper.class);
        fleetRepository = new InMemoryFleetRepository(fleetPersistenceMapper);
    }

    @Test
    void givenNoFleet_whenFind_thenExceptionIsThrown() {
        Executable getFleetAction = () -> fleetRepository.find();
        Assertions.assertThrows(NotFoundException.class, getFleetAction);
    }

    @Test
    void givenFleet_whenFindIsReturned() {
        Mockito.when(fleetPersistenceMapper.toDomain(fleetRecord)).thenReturn(fleet);
        Mockito.when(fleetPersistenceMapper.toRecord(fleet)).thenReturn(fleetRecord);
        fleetRepository.save(fleet);

        Fleet retrievedFleet = fleetRepository.find();

        Assertions.assertEquals(fleet, retrievedFleet);
    }

    @Test
    void givenFleet_whenSave_thenFleetIsSaved() {
        Mockito.when(fleetPersistenceMapper.toRecord(fleet)).thenReturn(fleetRecord);

        fleetRepository.save(fleet);

        Mockito.verify(fleetPersistenceMapper).toRecord(fleet);
    }
}
