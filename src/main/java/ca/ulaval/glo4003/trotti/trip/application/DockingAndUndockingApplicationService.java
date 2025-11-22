package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.trip.application.dto.DockScooterDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UndockScooterDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class DockingAndUndockingApplicationService {

    private final StationRepository stationRepository;
    private final ScooterRepository scooterRepository;
    private final Clock clock;

    public DockingAndUndockingApplicationService(
            StationRepository stationRepository,
            ScooterRepository scooterRepository,
            Clock clock) {
        this.stationRepository = stationRepository;
        this.scooterRepository = scooterRepository;
        this.clock = clock;
    }

    public ScooterId undock(UndockScooterDto undockScooterDto) {
        Station station = stationRepository.findByLocation(undockScooterDto.location());

        ScooterId scooterId = station.getScooter(undockScooterDto.slotNumber());
        Scooter scooter = scooterRepository.findById(scooterId);

        scooter.undock(LocalDateTime.now(clock));

        stationRepository.save(station);
        scooterRepository.save(scooter);

        return scooterId;
    }

    public void dock(DockScooterDto dockScooterDto) {
        Station station = stationRepository.findByLocation(dockScooterDto.location());
        Scooter scooter = scooterRepository.findById(dockScooterDto.scooterId());

        station.returnScooter(dockScooterDto.slotNumber(), dockScooterDto.scooterId());
        scooter.dockAt(dockScooterDto.location(), LocalDateTime.now(clock));

        stationRepository.save(station);
        scooterRepository.save(scooter);
    }

    public List<SlotNumber> findAvailableSlotsInStation(Location destinationStation) {
        Station station = stationRepository.findByLocation(destinationStation);

        return station.getAvailableSlots();
    }

    public List<SlotNumber> findOccupiedSlotsInStation(Location destinationStation) {
        Station station = stationRepository.findByLocation(destinationStation);

        return station.getOccupiedSlots();
    }
}
