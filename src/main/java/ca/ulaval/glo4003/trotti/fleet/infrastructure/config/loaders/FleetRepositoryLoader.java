package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.InMemoryFleetRepository;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.InMemoryTransferRepository;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.mappers.FleetPersistenceMapper;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.mappers.TransferPersistenceMapper;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records.TransferRecord;
import java.util.HashMap;
import java.util.Map;

public class FleetRepositoryLoader extends Bootstrapper {
    @Override
    public void load() {
        loadTransferRepository();
        loadFleetRepository();
    }

    private void loadTransferRepository() {
        TransferPersistenceMapper mapper =
                this.resourceLocator.resolve(TransferPersistenceMapper.class);
        Map<TransferId, TransferRecord> storage = new HashMap<>();
        TransferRepository transferRepository = new InMemoryTransferRepository(storage, mapper);
        this.resourceLocator.register(TransferRepository.class, transferRepository);
    }

    private void loadFleetRepository() {
        FleetPersistenceMapper mapper = this.resourceLocator.resolve(FleetPersistenceMapper.class);
        FleetRepository fleetRepository = new InMemoryFleetRepository(mapper);
        this.resourceLocator.register(FleetRepository.class, fleetRepository);
    }
}
