package ca.ulaval.glo4003.trotti.trip.api.gatewayentry;

import ca.ulaval.glo4003.trotti.trip.api.dto.requests.RetrieveScooterRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.ReturnScooterRequest;
import ca.ulaval.glo4003.trotti.trip.api.mappers.StationApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.DockingAndUndockingApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.dto.DockScooterDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UndockScooterDto;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;

public class StationOperationEntry {
    private final DockingAndUndockingApplicationService dockingAndUndockingApplicationService;
    private final StationApiMapper stationApiMapper;

    public StationOperationEntry(
            DockingAndUndockingApplicationService dockingAndUndockingApplicationService,
            StationApiMapper stationApiMapper) {
        this.dockingAndUndockingApplicationService = dockingAndUndockingApplicationService;
        this.stationApiMapper = stationApiMapper;
    }

    public ScooterId retrieveScooter(RetrieveScooterRequest retrieveScooterRequest) {
        UndockScooterDto undockScooterDto =
                stationApiMapper.toUndockScooterDto(retrieveScooterRequest);
        return dockingAndUndockingApplicationService.undock(undockScooterDto);
    }

    public void returnScooter(ReturnScooterRequest returnScooterRequest) {
        DockScooterDto dockScooterDto = stationApiMapper.toDockScooterDto(returnScooterRequest);
        dockingAndUndockingApplicationService.dock(dockScooterDto);
    }
}
