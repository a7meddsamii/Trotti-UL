package ca.ulaval.glo4003.trotti.fleet.application;

import ca.ulaval.glo4003.trotti.fleet.application.dto.DockScooterDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.UndockScooterDto;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;

public interface FleetApplicationService {
	ScooterId retrieveScooter(UndockScooterDto undockScooterDto);
	
	void returnScooter(DockScooterDto dockScooterDto);
}
