package ca.ulaval.glo4003.trotti.trip.domain.gateway;

import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;

public interface ScooterRentalGateway {

    ScooterId retrieveScooter(Location location, SlotNumber slotNumber);

    void returnScooter(Location location, SlotNumber slotNumber, ScooterId scooterId);
}
