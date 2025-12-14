package ca.ulaval.glo4003.trotti.fleet.api;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.*;
import ca.ulaval.glo4003.trotti.fleet.api.mapper.FleetApiMapper;
import ca.ulaval.glo4003.trotti.fleet.application.FleetMaintenanceApplicationService;
import ca.ulaval.glo4003.trotti.fleet.application.FleetOperationsApplicationService;
import ca.ulaval.glo4003.trotti.fleet.application.dto.*;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class FleetController implements FleetResource {

    private final FleetMaintenanceApplicationService maintenanceService;
    private final FleetOperationsApplicationService operationsService;
    private final FleetApiMapper fleetApiMapper;

    public FleetController(
            FleetMaintenanceApplicationService maintenanceService,
            FleetOperationsApplicationService operationsService,
            FleetApiMapper fleetApiMapper) {
        this.maintenanceService = maintenanceService;
        this.operationsService = operationsService;
        this.fleetApiMapper = fleetApiMapper;
    }

    @Override
    public Response startTransfer(Idul technicianId, StartTransferRequest request) {
        StartTransferDto startTransferDto =
                fleetApiMapper.toStartTransferDto(technicianId, request);
        TransferId transferId = maintenanceService.startTransfer(startTransferDto);

        return Response.ok(transferId).build();
    }

    @Override
    public Response unloadTransfer(Idul technicianId, String transferId,
            UnloadTransferRequest request) {
        UnloadTransferDto unloadTransferDto =
                fleetApiMapper.toUnloadTransferDto(technicianId, transferId, request);
        maintenanceService.unloadTransfer(unloadTransferDto);

        return Response.ok().build();
    }

    @Override
    public Response startMaintenance(Idul technicianId, StartMaintenanceRequest request) {
        StartMaintenanceDto startMaintenanceDto =
                fleetApiMapper.toStartMaintenanceDto(technicianId, request);
        maintenanceService.startMaintenance(startMaintenanceDto);

        return Response.ok().build();
    }

    @Override
    public Response endMaintenance(Idul technicianId, EndMaintenanceRequest request) {
        EndMaintenanceDto endMaintenanceDto =
                fleetApiMapper.toEndMaintenanceDto(technicianId, request);
        maintenanceService.endMaintenance(endMaintenanceDto);

        return Response.ok().build();
    }

    @Override
    public Response requestMaintenance(Idul requesterId, MaintenanceRequestRequest request) {
        RequestMaintenanceDto requestMaintenanceDto =
                fleetApiMapper.toRequestMaintenanceDto(requesterId, request);
        maintenanceService.requestMaintenance(requestMaintenanceDto);

        return Response.noContent().build();
    }

    @Override
    public Response getAvailableSlots(String location) {
        Location station = Location.of(location);
        List<SlotNumber> availableSlots = operationsService.getAvailableSlots(station);

        return Response.ok(availableSlots).build();
    }

    @Override
    public Response getOccupiedSlots(String location) {
        Location station = Location.of(location);
        List<SlotNumber> occupiedSlots = operationsService.getOccupiedSlots(station);

        return Response.ok(occupiedSlots).build();
    }
}
