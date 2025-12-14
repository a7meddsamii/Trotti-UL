package ca.ulaval.glo4003.trotti.fleet.application;

import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.fleet.application.dto.*;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.TransferNotFoundException;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.TransferFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class FleetMaintenanceApplicationService {

    private final FleetRepository fleetRepository;
    private final TransferRepository transferRepository;
    private final TransferFactory transferFactory;
    private final EventBus eventBus;
    private final Clock clock;

    public FleetMaintenanceApplicationService(
            FleetRepository fleetRepository,
            TransferRepository transferRepository,
            TransferFactory transferFactory,
            EventBus eventBus,
            Clock clock) {
        this.fleetRepository = fleetRepository;
        this.transferRepository = transferRepository;
        this.transferFactory = transferFactory;
        this.eventBus = eventBus;
        this.clock = clock;
    }

    public void startMaintenance(StartMaintenanceDto startMaintenanceDto) {
        Fleet fleet = fleetRepository.find();
        fleet.startMaintenance(startMaintenanceDto.location(), startMaintenanceDto.technicianId(),
                now());
        fleetRepository.save(fleet);
    }

    public void endMaintenance(EndMaintenanceDto endMaintenanceDto) {
        Fleet fleet = fleetRepository.find();
        fleet.endMaintenance(endMaintenanceDto.location(), endMaintenanceDto.technicianId(), now());
        fleetRepository.save(fleet);
    }

    public void requestMaintenance(RequestMaintenanceDto requestMaintenanceDto) {
        Fleet fleet = fleetRepository.find();
        fleet.ensureStationNotUnderMaintenance(requestMaintenanceDto.location());

        eventBus.publish(new MaintenanceRequestedEvent(requestMaintenanceDto.requesterId(),
                requestMaintenanceDto.location().toString(), requestMaintenanceDto.message()));
    }

    public TransferId startTransfer(StartTransferDto startTransferDto) {
        Fleet fleet = fleetRepository.find();
        List<ScooterId> scooterIds = fleet.retrieveScooters(startTransferDto.sourceStation(),
                startTransferDto.sourceSlots());
        Transfer transfer = transferFactory.create(startTransferDto.technicianId(), scooterIds);
        transferRepository.save(transfer);
        fleetRepository.save(fleet);

        return transfer.getTransferId();
    }

    public void unloadTransfer(UnloadTransferDto dto) {
        Transfer transfer = transferRepository.findById(dto.transferId()).orElseThrow(() -> new TransferNotFoundException(dto.transferId()));
        List<ScooterId> scooterIdsToDeposit =
                transfer.unload(dto.technicianId(), dto.destinationSlots().size());
        Fleet fleet = fleetRepository.find();
        fleet.depositScooters(dto.destinationStation(), dto.destinationSlots(), scooterIdsToDeposit,
                now());
        transferRepository.save(transfer);
        fleetRepository.save(fleet);
    }

    private LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
}
