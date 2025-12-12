package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidStationOperation;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidTransferException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Fleet {
    private final Map<Location, Station> stations;
    private final Map<ScooterId, Scooter> displacedScooters;

    public Fleet(Map<Location, Station> stations, Map<ScooterId, Scooter> displacedScooters) {
        this.stations = stations;
        this.displacedScooters = displacedScooters;
    }

    public ScooterId rentScooter(Location location, SlotNumber slotNumber,
            LocalDateTime rentStartTime) {
        Station station = stations.get(location);
        Scooter scooter = station.takeScooter(slotNumber, rentStartTime);
        displacedScooters.put(scooter.getScooterId(), scooter);

        return scooter.getScooterId();
    }

    public void returnScooter(ScooterId scooterId, Location location, SlotNumber slotNumber,
            LocalDateTime returnTime) {
        Scooter scooter = displacedScooters.remove(scooterId);

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

    public List<ScooterId> retrieveScooters(Location location, List<SlotNumber> slotNumbers) {
        Station station = stations.get(location);
        List<Scooter> scooters = station.retrieveScootersForTransfer(slotNumbers);
        scooters.forEach(scooter -> displacedScooters.put(scooter.getScooterId(), scooter));

        return scooters.stream().map(Scooter::getScooterId).toList();
    }

    public void depositScooters(Location location, List<SlotNumber> slotNumbers,
            List<ScooterId> scooterIds, LocalDateTime dockTime) {
        if (slotNumbers.size() != scooterIds.size()) {
            throw new InvalidTransferException("Slot count and scooter count must match");
        }

        List<Scooter> scootersToDeposit = getDisplacedScooters(scooterIds);
        Station station = stations.get(location);
        station.parkScooters(slotNumbers, scootersToDeposit, dockTime);
    }

    public Map<Location, Station> getStations() {
        return Collections.unmodifiableMap(stations);
    }

    public Map<ScooterId, Scooter> getDisplacedScooters() {
        return Collections.unmodifiableMap(displacedScooters);
    }

    private List<Scooter> getDisplacedScooters(List<ScooterId> scooterIds) {
        List<Scooter> scootersToDeposit = new ArrayList<>();

        for (ScooterId scooterId : scooterIds) {
            Scooter scooter = displacedScooters.remove(scooterId);
            if (scooter == null) {
                throw new InvalidTransferException(
                        "Scooter with ID " + scooterId + " is not displaced");
            }
            scootersToDeposit.add(scooter);
        }

        return scootersToDeposit;
    }

    public List<SlotNumber> getAvailableSlots(Location location) {
        Station station = stations.get(location);
        return station.getAvailableSlots();
    }

    public List<SlotNumber> getOccupiedSlots(Location location) {
        Station station = stations.get(location);
        return station.getOccupiedSlots();
    }

    public void ensureStationNotUnderMaintenance(Location location) {
        stations.get(location).ensureNotUnderMaintenance();
    }
}
