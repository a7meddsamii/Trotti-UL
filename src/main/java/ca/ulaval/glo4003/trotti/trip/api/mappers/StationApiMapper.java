package ca.ulaval.glo4003.trotti.trip.api.mappers;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.InitiateTransferRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.UnloadScootersRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.TransferResponse;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndMaintenanceDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.InitiateTransferDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartMaintenanceDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UnloadScootersDto;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.List;

public class StationApiMapper {

    public InitiateTransferDto toInitiateTransferDto(Idul idul, InitiateTransferRequest request) {
        Location sourceStation = Location.of(request.sourceStation());
        List<SlotNumber> sourceSlots = request.sourceSlots().stream()
                .map(SlotNumber::new)
                .toList();
        
        return new InitiateTransferDto(sourceStation, idul, sourceSlots);
    }

    public UnloadScootersDto toUnloadScootersDto(Idul idul, String transferId, UnloadScootersRequest request) {
        TransferId id = TransferId.from(transferId);
        Location destinationStation = Location.of(request.destinationStation());
        List<SlotNumber> destinationSlots = request.destinationSlots().stream()
                .map(SlotNumber::new)
                .toList();
        
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
}