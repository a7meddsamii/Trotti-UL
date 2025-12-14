package ca.ulaval.glo4003.trotti.fleet.api.mapper;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.*;
import ca.ulaval.glo4003.trotti.fleet.application.dto.*;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import java.util.List;

public class FleetApiMapper {

    public RentScooterDto toRentScooterDto(RetrieveScooterRequest request) {
        Location location = request.location();
        SlotNumber slotNumber = request.slotNumber();

        return new RentScooterDto(location, slotNumber);
    }

    public ReturnScooterDto toReturnScooterDto(ReturnScooterRequest request) {
        Location location = request.location();
        SlotNumber slotNumber = request.slotNumber();
        ScooterId scooterId = request.scooterId();

        return new ReturnScooterDto(scooterId, location, slotNumber);
    }

    public StartMaintenanceDto toStartMaintenanceDto(Idul technicianId,
            StartMaintenanceRequest request) {
        Location location = Location.of(request.location());

        return new StartMaintenanceDto(location, technicianId);
    }

    public EndMaintenanceDto toEndMaintenanceDto(Idul technicianId, EndMaintenanceRequest request) {
        Location location = Location.of(request.location());

        return new EndMaintenanceDto(location, technicianId);
    }

    public RequestMaintenanceDto toRequestMaintenanceDto(Idul requesterId,
            MaintenanceRequestRequest request) {
        Location location = Location.of(request.location());

        return new RequestMaintenanceDto(requesterId, location, request.message());
    }

    public StartTransferDto toStartTransferDto(Idul technicianId, StartTransferRequest request) {
        Location sourceStation = Location.of(request.sourceStation());
        List<SlotNumber> sourceSlots =
                request.sourceSlots().stream().map(SlotNumber::from).toList();

        return new StartTransferDto(technicianId, sourceStation, sourceSlots);
    }

    public UnloadTransferDto toUnloadTransferDto(Idul technicianId, String transferId,
            UnloadTransferRequest request) {
        TransferId id = TransferId.from(transferId);
        Location destinationStation = Location.of(request.destinationStation());
        List<SlotNumber> destinationSlots =
                request.destinationSlots().stream().map(SlotNumber::from).toList();

        return new UnloadTransferDto(id, technicianId, destinationStation, destinationSlots);
    }
}
