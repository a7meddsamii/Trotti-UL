package ca.ulaval.glo4003.trotti.trip.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.fleet.api.dto.RetrieveScooterRequest;
import ca.ulaval.glo4003.trotti.fleet.api.dto.ReturnScooterRequest;
import ca.ulaval.glo4003.trotti.fleet.api.gatewayentry.StationOperationEntry;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.ScooterRentalGateway;

public class ScooterRentalGatewayAdapter implements ScooterRentalGateway {
    private final StationOperationEntry stationOperationEntry;

    public ScooterRentalGatewayAdapter(StationOperationEntry stationOperationEntry) {
        this.stationOperationEntry = stationOperationEntry;
    }

    @Override
    public ScooterId retrieveScooter(Location location, SlotNumber slotNumber) {
        RetrieveScooterRequest retrieveScooterRequest =
                new RetrieveScooterRequest(location, slotNumber);
        return stationOperationEntry.retrieveScooter(retrieveScooterRequest);
    }

    @Override
    public void returnScooter(Location location, SlotNumber slotNumber, ScooterId scooterId) {
        ReturnScooterRequest returnScooterRequest =
                new ReturnScooterRequest(location, slotNumber, scooterId);
        stationOperationEntry.returnScooter(returnScooterRequest);
    }
}
