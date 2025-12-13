package ca.ulaval.glo4003.trotti.fleet.api.gatewayentry;

import ca.ulaval.glo4003.trotti.fleet.application.TemporaryFleepApplicationService;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.api.dto.RetrieveScooterRequest;
import ca.ulaval.glo4003.trotti.fleet.api.dto.ReturnScooterRequest;
import ca.ulaval.glo4003.trotti.trip.api.mappers.StationApiMapper;
import ca.ulaval.glo4003.trotti.fleet.application.dto.DockScooterDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.UndockScooterDto;

public class StationOperationEntry {
    private final StationApiMapper stationApiMapper;
	private final TemporaryFleepApplicationService temporaryFleepApplicationService;

    public StationOperationEntry(StationApiMapper stationApiMapper, TemporaryFleepApplicationService temporaryFleepApplicationService) {
        this.stationApiMapper = stationApiMapper;
		this.temporaryFleepApplicationService = temporaryFleepApplicationService;
	}

    public ScooterId retrieveScooter(RetrieveScooterRequest retrieveScooterRequest) {
        UndockScooterDto undockScooterDto =
                stationApiMapper.toUndockScooterDto(retrieveScooterRequest);
        return temporaryFleepApplicationService.retrieveScooter(undockScooterDto);
    }

    public void returnScooter(ReturnScooterRequest returnScooterRequest) {
        DockScooterDto dockScooterDto = stationApiMapper.toDockScooterDto(returnScooterRequest);
		temporaryFleepApplicationService.returnScooter(dockScooterDto);
    }
}
