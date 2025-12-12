package ca.ulaval.glo4003.trotti.fleet.application;

import ca.ulaval.glo4003.trotti.fleet.application.dto.RentScooterDto;
import ca.ulaval.glo4003.trotti.fleet.application.dto.ReturnScooterDto;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class FleetOperationsApplicationService {

    private final FleetRepository fleetRepository;
    private final Clock clock;

    public FleetOperationsApplicationService(FleetRepository fleetRepository, Clock clock) {
        this.fleetRepository = fleetRepository;
        this.clock = clock;
    }

    public ScooterId rentScooter(RentScooterDto rentScooterDto) {
        Fleet fleet = fleetRepository.getFleet();
        ScooterId scooterId =
                fleet.rentScooter(rentScooterDto.location(), rentScooterDto.slotNumber(), now());
        fleetRepository.save(fleet);

        return scooterId;
    }

    public void returnScooter(ReturnScooterDto returnScooterDto) {
        Fleet fleet = fleetRepository.getFleet();
        fleet.returnScooter(returnScooterDto.scooterId(), returnScooterDto.location(),
                returnScooterDto.slotNumber(), now());
        fleetRepository.save(fleet);
    }

    public List<SlotNumber> getAvailableSlots(Location location) {
        Fleet fleet = fleetRepository.getFleet();
        return fleet.getAvailableSlots(location);
    }

    public List<SlotNumber> getOccupiedSlots(Location location) {
        Fleet fleet = fleetRepository.getFleet();

        return fleet.getOccupiedSlots(location);
    }

    private LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
}
