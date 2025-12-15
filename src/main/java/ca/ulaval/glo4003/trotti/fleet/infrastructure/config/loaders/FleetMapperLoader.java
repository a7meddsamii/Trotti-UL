package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.api.mapper.FleetApiMapper;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.mappers.FleetPersistenceMapper;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.mappers.TransferPersistenceMapper;

public class FleetMapperLoader extends Bootstrapper {
    @Override
    public void load() {
        loadPersistenceMappers();
        loadApiMappers();
    }

    private void loadPersistenceMappers() {
        TransferPersistenceMapper transferPersistenceMapper = new TransferPersistenceMapper();
        FleetPersistenceMapper fleetPersistenceMapper = new FleetPersistenceMapper();
        this.resourceLocator.register(TransferPersistenceMapper.class, transferPersistenceMapper);
        this.resourceLocator.register(FleetPersistenceMapper.class, fleetPersistenceMapper);
    }

    private void loadApiMappers() {
        FleetApiMapper fleetApiMapper = new FleetApiMapper();
        this.resourceLocator.register(FleetApiMapper.class, fleetApiMapper);
    }
}
