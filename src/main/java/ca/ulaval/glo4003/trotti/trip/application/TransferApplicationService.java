package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.factories.TransferFactory;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.List;

public class TransferApplicationService {
    private final TransferRepository transferRepository;
    private final StationRepository stationRepository;
    private final TransferFactory transferFactory;

    public TransferApplicationService(TransferRepository transferRepository, StationRepository stationRepository, TransferFactory transferFactory) {
        this.transferRepository = transferRepository;
        this.stationRepository = stationRepository;
        this.transferFactory = transferFactory;
    }

    public TransferId initiateTransfer(Location sourceStation, Idul technicianId, List<SlotNumber> sourceSlots) {
        Station station = stationRepository.findByLocation(sourceStation);
        Transfer transfer = transferFactory.createTransfer(technicianId, station, sourceSlots);
        
        transferRepository.save(transfer);
        return transfer.getTransferId();
    }

    public void unloadScooters(TransferId transferId, Location destinationStation, List<SlotNumber> destinationSlots) {
        Transfer transfer = transferRepository.findById(transferId);
        Station station = stationRepository.findByLocation(destinationStation);
        
        transfer.unload(station, destinationSlots);
        
        transferRepository.save(transfer);
        stationRepository.save(station);
    }
}