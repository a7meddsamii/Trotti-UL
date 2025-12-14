package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.StationFactory;

public class FleetFactoryLoader extends Bootstrapper {
    @Override
    public void load() {
        loadStationFactory();
        loadScooterFactory();
    }

    private void loadStationFactory() {
        this.resourceLocator.register(StationFactory.class, new StationFactory());
    }

    private void loadScooterFactory() {
        this.resourceLocator.register(ScooterFactory.class, new ScooterFactory());
    }
}
