package ca.ulaval.glo4003.trotti.trip.api.mappers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.*;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.TransferResponse;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.UnloadScootersResponse;
import ca.ulaval.glo4003.trotti.trip.application.dto.*;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import java.util.List;

public class StationApiMapper {

    public InitiateTransferDto toInitiateTransferDto(Idul idul, InitiateTransferRequest request) {
        Location sourceStation = Location.of(request.sourceStation());
        List<SlotNumber> sourceSlots = request.sourceSlots().stream().map(SlotNumber::new).toList();

        return new InitiateTransferDto(sourceStation, idul, sourceSlots);
    }

    public UnloadScootersDto toUnloadScootersDto(Idul idul, String transferId,
            UnloadScootersRequest request) {
        TransferId id = TransferId.from(transferId);
        Location destinationStation = Location.of(request.destinationStation());
        List<SlotNumber> destinationSlots =
                request.destinationSlots().stream().map(SlotNumber::new).toList();

        return new UnloadScootersDto(id, idul, destinationStation, destinationSlots);
    }

    public StartMaintenanceDto toStartMaintenanceDto(Idul idul, StartMaintenanceRequest request) {
        Location location = Location.of(request.location());
        return new StartMaintenanceDto(location, idul);
    }

    public EndMaintenanceDto toEndMaintenanceDto(Idul idul, EndMaintenanceRequest request) {
        Location location = Location.of(request.location());
        return new EndMaintenanceDto(location, idul);
    }

    public TransferResponse toTransferResponse(TransferId transferId) {
        return new TransferResponse(transferId.toString());
    }

    public UnloadScootersResponse toUnloadScootersResponse(int scootersInTransit) {
        return new UnloadScootersResponse(scootersInTransit);
    }

    public UndockScooterDto toUndockScooterDto(RetrieveScooterRequest retrieveScooterRequest) {
        return new UndockScooterDto(retrieveScooterRequest.location(),
                retrieveScooterRequest.slotNumber());
    }

    public DockScooterDto toDockScooterDto(ReturnScooterRequest retrieveScooterRequest) {
        return new DockScooterDto(retrieveScooterRequest.location(),
                retrieveScooterRequest.slotNumber(), retrieveScooterRequest.scooterId());
    }
}
