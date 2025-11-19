package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
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

    public TransferId initiateTransfer(Location sourceStation, Idul technicianId,
            List<SlotNumber> sourceSlots) {
        Station station = stationRepository.findByLocation(sourceStation);
        Transfer transfer = Transfer.start(technicianId, sourceStation,
                station.retrieveScootersForTransfer(sourceSlots));

        transferRepository.save(transfer);
        return transfer.getTransferId();
    }

    public void unloadScooters(TransferId transferId, Idul technicianId,
            Location destinationStation, List<SlotNumber> destinationSlots) {
        Transfer transfer = transferRepository.findById(transferId);
        Station station = stationRepository.findByLocation(destinationStation);

        List<ScooterId> unloadedScooters = transfer.unload(technicianId, destinationSlots.size());
        for (int i = 0; i < unloadedScooters.size(); i++) {
            station.returnScooter(destinationSlots.get(i), unloadedScooters.get(i));
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
