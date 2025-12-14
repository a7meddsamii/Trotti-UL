package ca.ulaval.glo4003.trotti.fleet.api.gatewayentry;

import ca.ulaval.glo4003.trotti.fleet.api.dto.request.RetrieveScooterRequest;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.ReturnScooterRequest;
import ca.ulaval.glo4003.trotti.fleet.api.mapper.FleetApiMapper;
import ca.ulaval.glo4003.trotti.fleet.application.ScooterRentalApplicationService;
import ca.ulaval.glo4003.trotti.fleet.application.dto.RentScooterDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.ReturnScooterDto;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;

public class StationOperationEntry {
    private final FleetApiMapper fleetApiMapper;
    private final ScooterRentalApplicationService temporaryScooterRentalApplicationService;

    public StationOperationEntry(
            FleetApiMapper fleetApiMapper,
            ScooterRentalApplicationService temporaryScooterRentalApplicationService
	) {
        this.fleetApiMapper = fleetApiMapper;
        this.temporaryScooterRentalApplicationService = temporaryScooterRentalApplicationService;
    }

    public ScooterId retrieveScooter(RetrieveScooterRequest retrieveScooterRequest) {
		RentScooterDto undockScooterDto =
                fleetApiMapper.toRentScooterDto(retrieveScooterRequest);
        return temporaryScooterRentalApplicationService.rentScooter(undockScooterDto);
    }

    public void returnScooter(ReturnScooterRequest returnScooterRequest) {
		ReturnScooterDto dockScooterDto = fleetApiMapper.toReturnScooterDto(returnScooterRequest);
        temporaryScooterRentalApplicationService.returnScooter(dockScooterDto);
    }
}