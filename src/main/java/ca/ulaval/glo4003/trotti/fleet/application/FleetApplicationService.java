package ca.ulaval.glo4003.trotti.fleet.application;

import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.fleet.application.dto.*;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import java.time.Clock;
import java.time.LocalDateTime;

public class FleetApplicationService {

    private final FleetRepository fleetRepository;
    private final EventBus eventBus;
    private final Clock clock;

    public FleetApplicationService(
            FleetRepository fleetRepository,
            EventBus eventBus,
            Clock clock) {
        this.fleetRepository = fleetRepository;
        this.eventBus = eventBus;
        this.clock = clock;
    }

    public ScooterId rentScooter(RentScooterDto rentScooterDto) {
        Fleet fleet = fleetRepository.getFleet();
        ScooterId scooterId =
                fleet.rentScooter(rentScooterDto.location(), rentScooterDto.slotNumber(), now());
        fleetRepository.save(fleet);

        return scooterId;
    }

    public void returnScooter(ReturnScooterDto dto) {
        Fleet fleet = fleetRepository.getFleet();
        fleet.returnScooter(dto.scooterId(), dto.location(), dto.slotNumber(), now());

        fleetRepository.save(fleet);
    }

    public void startMaintenance(StartMaintenanceDto startMaintenanceDto) {
        Fleet fleet = fleetRepository.getFleet();
        fleet.startMaintenance(startMaintenanceDto.location(), startMaintenanceDto.technicianId(),
                now());

        fleetRepository.save(fleet);
    }

    public void endMaintenance(EndMaintenanceDto endMaintenanceDto) {
        Fleet fleet = fleetRepository.getFleet();
        fleet.endMaintenance(endMaintenanceDto.location(), endMaintenanceDto.technicianId(), now());

        fleetRepository.save(fleet);
    }

    public void requestMaintenance(RequestMaintenanceDto requestMaintenanceDto) {
        Fleet fleet = fleetRepository.getFleet();
        fleet.ensureStationNotUnderMaintenance(requestMaintenanceDto.location());
        eventBus.publish(new MaintenanceRequestedEvent(requestMaintenanceDto.requesterId(),
                requestMaintenanceDto.location().toString(), requestMaintenanceDto.message()));
    }

    public void startTransfer(StartTransferDto startTransferDto) {
        Fleet fleet = fleetRepository.getFleet();
        fleet.startTransfer(startTransferDto.technicianId(), startTransferDto.sourceStation(),
                startTransferDto.sourceSlots());

        fleetRepository.save(fleet);
    }

    public void unloadTransfer(UnloadTransferDto dto) {
        Fleet fleet = fleetRepository.getFleet();

        fleet.unloadTransfer(dto.technicianId(), dto.destinationStation(), dto.destinationSlots(),
                now());

        fleetRepository.save(fleet);
    }

    private LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

}
