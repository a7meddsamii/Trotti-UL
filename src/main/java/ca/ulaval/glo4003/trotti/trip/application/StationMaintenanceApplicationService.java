package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndMaintenanceDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartMaintenanceDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class StationMaintenanceApplicationService {

    private final StationRepository stationRepository;
    private final ScooterRepository scooterRepository;
    private final EventBus eventBus;
    private final Clock clock;

    public StationMaintenanceApplicationService(
            StationRepository stationRepository,
            ScooterRepository scooterRepository,
            EventBus eventBus,
            Clock clock) {
        this.stationRepository = stationRepository;
        this.scooterRepository = scooterRepository;
        this.eventBus = eventBus;
        this.clock = clock;
    }

    public void startMaintenance(StartMaintenanceDto startMaintenanceDto) {
        Station station = stationRepository.findByLocation(startMaintenanceDto.location());
        List<Scooter> scooters = loadDockedScooters(station);
        LocalDateTime startTime = LocalDateTime.ofInstant(clock.instant(), clock.getZone());

        station.startMaintenance(startMaintenanceDto.technicianId());
        scooters.forEach(scooter -> scooter.pauseCharging(startTime));

        save(scooters, station);
    }

    public void endMaintenance(EndMaintenanceDto endMaintenanceDto) {
        Station station = stationRepository.findByLocation(endMaintenanceDto.location());
        List<Scooter> scooters = loadDockedScooters(station);
        LocalDateTime endTime = LocalDateTime.ofInstant(clock.instant(), clock.getZone());

        station.endMaintenance(endMaintenanceDto.technicianId());
        scooters.forEach(scooter -> scooter.resumeCharging(endTime));

        save(scooters, station);
    }

    public void requestMaintenanceService(Idul userId, String location, String message) {
        Station station = stationRepository.findByLocation(Location.of(location));
        station.validateNotUnderMaintenance();

        eventBus.publish(new MaintenanceRequestedEvent(userId, location, message));
    }

    private List<Scooter> loadDockedScooters(Station station) {
        List<ScooterId> ids = station.getAllScooterIds();
        return scooterRepository.findByIds(ids);
    }

    private void save(List<Scooter> scooters, Station station) {
        scooterRepository.saveAll(scooters);
        stationRepository.save(station);
    }
}
