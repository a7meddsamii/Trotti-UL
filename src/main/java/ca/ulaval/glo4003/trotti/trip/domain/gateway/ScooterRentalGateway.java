package ca.ulaval.glo4003.trotti.trip.domain.gateway;

import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;

public interface ScooterRentalGateway {

    ScooterId retrieveScooter(Location location, SlotNumber slotNumber);

    void returnScooter(Location location, ScooterId scooterId, SlotNumber slotNumber);
}
