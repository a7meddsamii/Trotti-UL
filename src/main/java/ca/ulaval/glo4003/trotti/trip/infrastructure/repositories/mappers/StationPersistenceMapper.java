package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.DockingArea;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.StationRecord;
import java.util.Map;

public class StationPersistenceMapper {

    public Station toDomain(StationRecord stationRecord) {
        DockingArea dockingArea = toDomainDockingArea(stationRecord.slots());
        return null;
    }

    public StationRecord toRecord(Station station) {
        return null;
    }

    private DockingArea toDomainDockingArea(Map<SlotNumber, ScooterId> scooterSlotsRecord) {
        return null;
    }
}
