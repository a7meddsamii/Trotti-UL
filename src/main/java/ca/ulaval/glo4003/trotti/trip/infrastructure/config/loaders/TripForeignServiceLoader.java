package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.gatewayentry.RidePermitGatewayEntry;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.api.gatewayentry.StationOperationEntry;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitGateway;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.ScooterRentalGateway;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.trip.infrastructure.gateway.RidePermitGatewayAdapter;
import ca.ulaval.glo4003.trotti.trip.infrastructure.gateway.ScooterRentalGatewayAdapter;
import ca.ulaval.glo4003.trotti.trip.infrastructure.store.GuavaUnlockCodeStore;

public class TripForeignServiceLoader extends Bootstrapper {

    @Override
    public void load() {
        loadGuavaCachingStore();
        loadScooterRentalGateway();
        loadRidePermitGateway();
    }

    private void loadGuavaCachingStore() {
        UnlockCodeStore unlockCodeStore = new GuavaUnlockCodeStore();
        this.resourceLocator.register(UnlockCodeStore.class, unlockCodeStore);
    }

    private void loadScooterRentalGateway() {
        StationOperationEntry stationOperationEntry =
                this.resourceLocator.resolve(StationOperationEntry.class);
        ScooterRentalGateway scooterRentalGateway =
                new ScooterRentalGatewayAdapter(stationOperationEntry);
        this.resourceLocator.register(ScooterRentalGateway.class, scooterRentalGateway);
    }

    private void loadRidePermitGateway() {
        RidePermitGatewayEntry ridePermitGatewayEntry =
                this.resourceLocator.resolve(RidePermitGatewayEntry.class);
        RidePermitGateway ridePermitGateway = new RidePermitGatewayAdapter(ridePermitGatewayEntry);
        this.resourceLocator.register(RidePermitGateway.class, ridePermitGateway);
    }
}
