package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.DockingArea;
import ca.ulaval.glo4003.trotti.domain.trip.entities.ScooterSlot;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.StationRecord;
import java.util.HashMap;
import java.util.Map;

public class StationPersistenceMapper {

    public Station toDomain(StationRecord stationRecord) {
        DockingArea dockingArea = toDomainDockingArea(stationRecord.slots());
        return new Station(stationRecord.location(), dockingArea);
    }

    public StationRecord toRecord(Station station) {
        Map<SlotNumber, ScooterId> slots = new HashMap<>();

        station.getDockingArea().getScooterSlots().forEach((slotNumber, slot) -> {
            slots.put(slotNumber, slot.getDockedScooter().orElse(null));
        });

        return new StationRecord(station.getLocation(), slots);
    }

    private DockingArea toDomainDockingArea(Map<SlotNumber, ScooterId> scooterSlotsRecord) {
        Map<SlotNumber, ScooterSlot> scooterSlots = new HashMap<>();

        scooterSlotsRecord.forEach((slotNumber, scooterId) -> {
            ScooterSlot slot = new ScooterSlot(slotNumber, scooterId);
            scooterSlots.put(slotNumber, slot);
        });

        return new DockingArea(scooterSlots);
    }
}
