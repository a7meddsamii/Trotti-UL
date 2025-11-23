package ca.ulaval.glo4003.trotti.trip.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.trip.api.dto.requests.RetrieveScooterRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.ReturnScooterRequest;
import ca.ulaval.glo4003.trotti.trip.api.gatewayentry.StationOperationEntry;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.ScooterRentalGateway;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;

public class ScooterRentalGatewayAdapter implements ScooterRentalGateway {
	private final StationOperationEntry stationOperationEntry;
	
	public ScooterRentalGatewayAdapter(StationOperationEntry stationOperationEntry) {
		this.stationOperationEntry = stationOperationEntry;
	}
	
	@Override
    public ScooterId retrieveScooter(Location location, SlotNumber slotNumber) {
		RetrieveScooterRequest retrieveScooterRequest = new RetrieveScooterRequest(location, slotNumber);
		return stationOperationEntry.retrieveScooter(retrieveScooterRequest);
    }

    @Override
    public void returnScooter(Location location, SlotNumber slotNumber, ScooterId scooterId) {
		ReturnScooterRequest returnScooterRequest = new ReturnScooterRequest(location, slotNumber, scooterId);
		stationOperationEntry.returnScooter(returnScooterRequest);
    }
}
