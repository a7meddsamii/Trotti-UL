package ca.ulaval.glo4003.trotti.fleet.application;

import ca.ulaval.glo4003.trotti.fleet.application.dto.DockScooterDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.UndockScooterDto;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * @deprecated this is a temporary implementation, to be removed once we have a proper application
 *             service layer the goal is to keep the trip module operational while we refactor the
 *             fleet module DO NOT expand its responsibilities, make completely new application
 *             service then delete this class
 */
public class TemporaryFleetApplicationService implements FleetApplicationService {
    private final FleetRepository fleetRepository;
    private final Clock clock;

    public TemporaryFleetApplicationService(FleetRepository fleetRepository, Clock clock) {
        this.fleetRepository = fleetRepository;
        this.clock = clock;
    }

    public ScooterId retrieveScooter(UndockScooterDto undockScooterDto) {
        Fleet fleet = fleetRepository.getFleet();
        LocalDateTime now = LocalDateTime.now(clock);
        ScooterId scooterId =
                fleet.rentScooter(undockScooterDto.location(), undockScooterDto.slotNumber(), now);
        fleetRepository.save(fleet);

        return scooterId;
    }

    public void returnScooter(DockScooterDto dockScooterDto) {
        Fleet fleet = fleetRepository.getFleet();
        LocalDateTime now = LocalDateTime.now(clock);
        fleet.returnScooter(dockScooterDto.scooterId(), dockScooterDto.location(),
                dockScooterDto.slotNumber(), now);
        fleetRepository.save(fleet);
    }
}
