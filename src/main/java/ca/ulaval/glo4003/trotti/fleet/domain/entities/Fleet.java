package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidStationOperation;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidTransferException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Fleet {
    private final Map<Location, Station> stations;
    private final Map<ScooterId, Scooter> rentedScooters;
    private final Map<Idul, Transfer> ongoingTransfers;

    public Fleet(
            Map<Location, Station> stations,
            Map<ScooterId, Scooter> rentedScooters,
            Map<Idul, Transfer> ongoingTransfers) {
        this.stations = stations;
        this.rentedScooters = rentedScooters;
        this.ongoingTransfers = ongoingTransfers;
    }

    public ScooterId rentScooter(Location location, SlotNumber slotNumber,
            LocalDateTime rentStartTime) {
        Station station = stations.get(location);
        Scooter scooter = station.takeScooter(slotNumber, rentStartTime);
        rentedScooters.put(scooter.getScooterId(), scooter);
		
        return scooter.getScooterId();
    }

    public void returnScooter(ScooterId scooterId, Location location, SlotNumber slotNumber,
            LocalDateTime returnTime) {
        Scooter scooter = rentedScooters.remove(scooterId);
		
		if (scooter == null) {
			throw new InvalidStationOperation("Current scooter does not seem to have been rented");
		}
		
		Station station = stations.get(location);
		station.parkScooter(slotNumber, scooter, returnTime);
    }

    public void startMaintenance(Location location, Idul technicianId, LocalDateTime startTime) {
        Station station = stations.get(location);
        station.startMaintenance(technicianId, startTime);
    }

    public void endMaintenance(Location location, Idul technicianId, LocalDateTime endTime) {
        Station station = stations.get(location);
        station.endMaintenance(technicianId, endTime);
    }

    public void startTransfer(Idul technicianId, Location location, List<SlotNumber> slotNumbers) {
        if (ongoingTransfers.containsKey(technicianId)) {
            throw new InvalidTransferException(
                    "Technician " + technicianId + " already has an ongoing transfer");
        }

        Station station = stations.get(location);
        List<Scooter> scooters = station.retrieveScootersForTransfer(slotNumbers);
        Transfer transfer = new Transfer(technicianId, scooters);
        ongoingTransfers.put(technicianId, transfer);
    }

    public void unloadTransfer(Idul technicianId, Location location, List<SlotNumber> slotNumbers,
            LocalDateTime dockTime) {
        Transfer transfer = ongoingTransfers.get(technicianId);

        if (transfer == null) {
            throw new InvalidTransferException(
                    "Technician " + technicianId + " does not have an ongoing transfer");
        }

        List<Scooter> scootersToDeposit = transfer.unload(technicianId, slotNumbers.size());
        Station station = stations.get(location);
        station.parkScooters(slotNumbers, scootersToDeposit, dockTime);

        if (transfer.isCompleted()) {
            ongoingTransfers.remove(technicianId);
        }
    }
}
