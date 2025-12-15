package ca.ulaval.glo4003.trotti.fleet.application;

import ca.ulaval.glo4003.trotti.fleet.application.dto.RentScooterDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.ReturnScooterDto;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;

public interface ScooterRentalApplicationService {
    ScooterId rentScooter(RentScooterDto undockScooterDto);

    void returnScooter(ReturnScooterDto dockScooterDto);
}
