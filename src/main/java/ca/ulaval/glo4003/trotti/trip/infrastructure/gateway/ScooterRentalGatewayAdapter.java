package ca.ulaval.glo4003.trotti.trip.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.trip.domain.gateway.ScooterRentalGateway;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;

public class ScooterRentalGatewayAdapter implements ScooterRentalGateway {

    @Override
    public ScooterId retrieveScooter(Location location, SlotNumber slotNumber) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void returnScooter(Location location, ScooterId scooterId, SlotNumber slotNumber) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
