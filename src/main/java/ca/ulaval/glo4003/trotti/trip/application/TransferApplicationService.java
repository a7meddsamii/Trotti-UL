package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.trip.application.dto.InitiateTransferDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UnloadScootersDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.time.Clock;
import java.util.List;
import java.util.Set;

public class TransferApplicationService {
    private final TransferRepository transferRepository;
    private final StationRepository stationRepository;
    private final ScooterRepository scooterRepository;
    private final Clock clock;

    public TransferApplicationService(
            TransferRepository transferRepository,
            StationRepository stationRepository,
            ScooterRepository scooterRepository,
            Clock clock) {
        this.transferRepository = transferRepository;
        this.stationRepository = stationRepository;
        this.scooterRepository = scooterRepository;
        this.clock = clock;
    }

    public TransferId initiateTransfer(InitiateTransferDto initiateTransferDto) {
        Station station = stationRepository.findByLocation(initiateTransferDto.sourceStation());
        station.validateTechnicianInCharge(initiateTransferDto.technicianId());

        Set<ScooterId> retrievedScooters =
                station.retrieveScootersForTransfer(initiateTransferDto.sourceSlots());

        retrievedScooters.forEach(scooterId -> {
            Scooter scooter = scooterRepository.findById(scooterId);
            scooter.undock(clock.instant().atZone(clock.getZone()).toLocalDateTime());
            scooterRepository.save(scooter);
        });

        Transfer transfer = Transfer.start(initiateTransferDto.technicianId(),
                initiateTransferDto.sourceStation(), retrievedScooters);

        transferRepository.save(transfer);
        stationRepository.save(station);
        return transfer.getTransferId();
    }

    public int unloadScooters(UnloadScootersDto unloadScootersDto) {
        Transfer transfer = transferRepository.findById(unloadScootersDto.transferId())
                .orElseThrow(() -> new NotFoundException("Transfer not found"));
        Station station = stationRepository.findByLocation(unloadScootersDto.destinationStation());

        List<ScooterId> unloadedScooters = transfer.unload(unloadScootersDto.technicianId(),
                unloadScootersDto.destinationSlots().size());
        station.returnScooters(unloadScootersDto.destinationSlots(), unloadedScooters);

        unloadedScooters.forEach(scooterId -> {
            Scooter scooter = scooterRepository.findById(scooterId);
            scooter.dockAt(unloadScootersDto.destinationStation(),
                    clock.instant().atZone(clock.getZone()).toLocalDateTime());
            scooterRepository.save(scooter);
        });

        transferRepository.save(transfer);
        stationRepository.save(station);

        return transfer.getScootersInTransitCount();
    }
}
