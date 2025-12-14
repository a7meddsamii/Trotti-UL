package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.application.FleetApplicationService;
import ca.ulaval.glo4003.trotti.fleet.application.FleetMaintenanceApplicationService;
import ca.ulaval.glo4003.trotti.fleet.application.FleetOperationsApplicationService;
import ca.ulaval.glo4003.trotti.fleet.application.TemporaryFleetApplicationService;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.TransferFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.TransferRepository;
import java.time.Clock;

public class FleetApplicationServiceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadFleetOperationsService();
        loadFleetMaintenanceService();
        loadFleetApplicationService();
    }

    private void loadFleetOperationsService() {
        FleetRepository fleetRepository = this.resourceLocator.resolve(FleetRepository.class);
        Clock clock = this.resourceLocator.resolve(Clock.class);

        FleetOperationsApplicationService service =
                new FleetOperationsApplicationService(fleetRepository, clock);

        this.resourceLocator.register(FleetOperationsApplicationService.class, service);
    }

    private void loadFleetMaintenanceService() {
        FleetRepository fleetRepository = this.resourceLocator.resolve(FleetRepository.class);
        TransferRepository transferRepository =
                this.resourceLocator.resolve(TransferRepository.class);
        TransferFactory transferFactory = this.resourceLocator.resolve(TransferFactory.class);
        EventBus eventBus = this.resourceLocator.resolve(EventBus.class);
        Clock clock = this.resourceLocator.resolve(Clock.class);

        FleetMaintenanceApplicationService service = new FleetMaintenanceApplicationService(
                fleetRepository, transferRepository, transferFactory, eventBus, clock);

        this.resourceLocator.register(FleetMaintenanceApplicationService.class, service);
    }

    /**
     * @deprecated replace TemporaryFleetApplicationService by FleetApplicationService when the
     *             implementation is ready
     */
    private void loadFleetApplicationService() {
        FleetRepository fleetRepository = this.resourceLocator.resolve(FleetRepository.class);
        Clock clock = this.resourceLocator.resolve(Clock.class);
        FleetApplicationService fleetApplicationService =
                new TemporaryFleetApplicationService(fleetRepository, clock);
        this.resourceLocator.register(FleetApplicationService.class, fleetApplicationService);
    }
}
