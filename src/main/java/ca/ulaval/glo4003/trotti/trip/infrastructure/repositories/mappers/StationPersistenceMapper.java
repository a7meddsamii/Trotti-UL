package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.entities.DockingArea;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.StationRecord;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StationPersistenceMapper {

    public Station toDomain(StationRecord stationRecord) {
        DockingArea dockingArea = toDomainDockingArea(stationRecord.slots());
        return new Station(stationRecord.location(), dockingArea);
    }

    public StationRecord toRecord(Station station) {
        Map<SlotNumber, ScooterId> slots = new HashMap<>();
        station.getDockingArea().getScooterSlots().forEach((slotNumber, optionalScooterId) -> {
            slots.put(slotNumber, optionalScooterId.orElse(null));
        });
        return new StationRecord(station.getLocation(), slots);
    }

    private DockingArea toDomainDockingArea(Map<SlotNumber, ScooterId> scooterSlotsRecord) {
        Map<SlotNumber, Optional<ScooterId>> slots = new HashMap<>();
        scooterSlotsRecord.forEach((slotNumber, scooterId) -> {
            slots.put(slotNumber, Optional.ofNullable(scooterId));
        });
        return new DockingArea(slots);
    }
}
