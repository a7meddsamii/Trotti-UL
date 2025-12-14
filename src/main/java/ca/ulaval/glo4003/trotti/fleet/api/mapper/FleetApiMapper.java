package ca.ulaval.glo4003.trotti.fleet.api.mapper;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.DropOffScooterRequest;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.EndMaintenanceRequest;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.MaintenanceRequestRequest;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.RentScooterRequest;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.StartMaintenanceRequest;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.StartTransferRequest;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.UnloadTransferRequest;
import ca.ulaval.glo4003.trotti.fleet.application.dto.EndMaintenanceDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.RentScooterDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.RequestMaintenanceDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.ReturnScooterDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.StartMaintenanceDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.StartTransferDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.UnloadTransferDto;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import java.util.List;

public class FleetApiMapper {

    public RentScooterDto toRentScooterDto(RentScooterRequest request) {
        Location location = Location.of(request.location());
        SlotNumber slotNumber = new SlotNumber(request.slotNumber());

        return new RentScooterDto(location, slotNumber);
    }

    public ReturnScooterDto toReturnScooterDto(DropOffScooterRequest request) {
        Location location = Location.of(request.location());
        SlotNumber slotNumber = new SlotNumber(Integer.parseInt(request.slotNumber()));
        ScooterId scooterId = ScooterId.from(request.scooterId());

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
        List<SlotNumber> sourceSlots = request.sourceSlots().stream().map(SlotNumber::new).toList();

        return new StartTransferDto(technicianId, sourceStation, sourceSlots);
    }

    public UnloadTransferDto toUnloadTransferDto(Idul technicianId, String transferId,
            UnloadTransferRequest request) {
        TransferId id = TransferId.from(transferId);
        Location destinationStation = Location.of(request.destinationStation());
        List<SlotNumber> destinationSlots =
                request.destinationSlots().stream().map(SlotNumber::new).toList();

        return new UnloadTransferDto(id, technicianId, destinationStation, destinationSlots);
    }
}
