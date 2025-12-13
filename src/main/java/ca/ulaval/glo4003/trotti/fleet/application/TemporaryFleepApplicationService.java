package ca.ulaval.glo4003.trotti.fleet.application;

import ca.ulaval.glo4003.trotti.fleet.application.dto.DockScooterDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.UndockScooterDto;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;

import java.time.Clock;
import java.time.LocalDateTime;

public class TemporaryFleepApplicationService {
	private final FleetRepository fleetRepository;
	private final Clock clock;
	
	public TemporaryFleepApplicationService(FleetRepository fleetRepository, Clock clock) {
		this.fleetRepository = fleetRepository;
		this.clock = clock;
	}
	
	public ScooterId retrieveScooter(UndockScooterDto undockScooterDto) {
		Fleet fleet = fleetRepository.getFleet();
		LocalDateTime now = LocalDateTime.now(clock);
		ScooterId scooterId = fleet.rentScooter(undockScooterDto.location(), undockScooterDto.slotNumber(), now);
		fleetRepository.save(fleet);
		
		return scooterId;
	}
	
	public void returnScooter(DockScooterDto dockScooterDto) {
		Fleet fleet = fleetRepository.getFleet();
		LocalDateTime now = LocalDateTime.now(clock);
		fleet.returnScooter(dockScooterDto.scooterId(), dockScooterDto.location(), dockScooterDto.slotNumber(), now);
		fleetRepository.save(fleet);
	}
}
