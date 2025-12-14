package ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records;

import java.util.Set;

public record FleetRecord(Set<StationRecord> stationsRecords, Set<ScooterRecord> displacedScootersRecords) {
}
