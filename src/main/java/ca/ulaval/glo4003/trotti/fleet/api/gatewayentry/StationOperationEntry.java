package ca.ulaval.glo4003.trotti.fleet.api.gatewayentry;

import ca.ulaval.glo4003.trotti.fleet.api.dto.RetrieveScooterRequest;
import ca.ulaval.glo4003.trotti.fleet.api.dto.ReturnScooterRequest;
import ca.ulaval.glo4003.trotti.fleet.application.FleetApplicationService;
import ca.ulaval.glo4003.trotti.fleet.application.dto.DockScooterDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.UndockScooterDto;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.api.mappers.FleetApiMapper;

public class StationOperationEntry {
    private final FleetApiMapper fleetApiMapper;
    private final FleetApplicationService temporaryFleetApplicationService;

    public StationOperationEntry(
            FleetApiMapper fleetApiMapper,
            FleetApplicationService temporaryFleetApplicationService) {
        this.fleetApiMapper = fleetApiMapper;
        this.temporaryFleetApplicationService = temporaryFleetApplicationService;
    }

    public ScooterId retrieveScooter(RetrieveScooterRequest retrieveScooterRequest) {
        UndockScooterDto undockScooterDto =
                fleetApiMapper.toUndockScooterDto(retrieveScooterRequest);
        return temporaryFleetApplicationService.retrieveScooter(undockScooterDto);
    }

    public void returnScooter(ReturnScooterRequest returnScooterRequest) {
        DockScooterDto dockScooterDto = fleetApiMapper.toDockScooterDto(returnScooterRequest);
        temporaryFleetApplicationService.returnScooter(dockScooterDto);
    }
}
