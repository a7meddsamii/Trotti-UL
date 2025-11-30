package ca.ulaval.glo4003.trotti.trip.api.gatewayentry;

import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.RetrieveScooterRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.ReturnScooterRequest;
import ca.ulaval.glo4003.trotti.trip.api.mappers.StationApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.dto.DockScooterDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UndockScooterDto;

/**
 * @deprecated This class will be reimplemented
 */
public class StationOperationEntry {
    private final StationApiMapper stationApiMapper;

    public StationOperationEntry(StationApiMapper stationApiMapper) {
        this.stationApiMapper = stationApiMapper;
    }

    public ScooterId retrieveScooter(RetrieveScooterRequest retrieveScooterRequest) {
        UndockScooterDto undockScooterDto =
                stationApiMapper.toUndockScooterDto(retrieveScooterRequest);
        return null;
    }

    public void returnScooter(ReturnScooterRequest returnScooterRequest) {
        DockScooterDto dockScooterDto = stationApiMapper.toDockScooterDto(returnScooterRequest);

    }
}
