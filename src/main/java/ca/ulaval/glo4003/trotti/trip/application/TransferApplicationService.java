package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.application.dto.InitiateTransferDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UnloadScootersDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.List;

public class TransferApplicationService {
    private final TransferRepository transferRepository;
    private final StationRepository stationRepository;

    public TransferApplicationService(
            TransferRepository transferRepository,
            StationRepository stationRepository) {
        this.transferRepository = transferRepository;
        this.stationRepository = stationRepository;
    }

    public TransferId initiateTransfer(InitiateTransferDto initiateTransferDto) {
        Station station = stationRepository.findByLocation(initiateTransferDto.sourceStation());
        Transfer transfer = Transfer.start(initiateTransferDto.technicianId(),
                initiateTransferDto.sourceStation(),
                station.retrieveScootersForTransfer(initiateTransferDto.sourceSlots()));

        transferRepository.save(transfer);
        return transfer.getTransferId();
    }

    public void unloadScooters(UnloadScootersDto unloadScootersDto) {
        Transfer transfer = transferRepository.findById(unloadScootersDto.transferId())
                .orElseThrow(() -> new NotFoundException("Transfer not found"));
        Station station = stationRepository.findByLocation(unloadScootersDto.destinationStation());

        List<ScooterId> unloadedScooters = transfer.unload(unloadScootersDto.technicianId(),
                unloadScootersDto.destinationSlots().size());
        for (int i = 0; i < unloadedScooters.size(); i++) {
            station.returnScooter(unloadScootersDto.destinationSlots().get(i),
                    unloadedScooters.get(i));
        }
        transferRepository.save(transfer);
        stationRepository.save(station);
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
