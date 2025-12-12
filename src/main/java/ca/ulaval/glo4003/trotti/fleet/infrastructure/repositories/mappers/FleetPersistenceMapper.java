package ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.*;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records.BatteryRecord;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records.FleetRecord;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records.ScooterRecord;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records.StationRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FleetPersistenceMapper {
	
	public FleetRecord toRecord(Fleet fleet) {
		Set<StationRecord> stationRecords = fleet.getStations().values().stream()
				.map(this::toStationRecord)
				.collect(Collectors.toSet());
		
		Set<ScooterRecord> displacedScooterRecords = fleet.getDisplacedScooters().values().stream()
				.map(this::toScooterRecord)
				.collect(Collectors.toSet());
		
		return new FleetRecord(stationRecords, displacedScooterRecords);
	}
	
	public Fleet toDomain(FleetRecord fleetRecord) {
		Map<Location, Station> stations = fleetRecord.stationsRecords().stream()
				.map(this::toStationDomain)
				.collect(Collectors.toMap(Station::getLocation, station -> station));
		
		Map<ScooterId, Scooter> displacedScooters = fleetRecord.displacedScootersRecords().stream()
				.map(this::toScooterDomain)
				.collect(Collectors.toMap(Scooter::getScooterId, scooter -> scooter));
		
		return new Fleet(stations, displacedScooters);
	}
	
	private BatteryRecord toBatteryRecord(Battery battery) {
		return new BatteryRecord(
				battery.getBatteryLevel(),
				battery.getLastBatteryUpdate(),
				battery.getCurrentBatteryState()
		);
	}
	
	private Battery toBatteryDomain(BatteryRecord batteryRecord) {
		return new Battery(
				batteryRecord.BatteryLevel(),
				batteryRecord.lastBatteryUpdate(),
				batteryRecord.currentState()
		);
	}
	
	private ScooterRecord toScooterRecord(Scooter scooter) {
		return new ScooterRecord(
				scooter.getScooterId(),
				toBatteryRecord(scooter.getBattery()),
				scooter.getLocation()
		);
	}
	
	private Scooter toScooterDomain(ScooterRecord scooterRecord) {
		return new Scooter(
				scooterRecord.id(),
				toBatteryDomain(scooterRecord.batteryRecord()),
				scooterRecord.location()
		);
	}
	
	private StationRecord toStationRecord(Station station) {
		Map<SlotNumber, ScooterRecord> dockingAreaRecord = toDockingAreaRecord(station.getDockingArea());
		
		return new StationRecord(
				station.getLocation(),
				dockingAreaRecord,
				station.getMaintenanceStatus()
		);
	}
	
	private Station toStationDomain(StationRecord stationRecord) {
		DockingArea dockingArea = toDockingAreaDomain(stationRecord.dockingAreaRecord());
		
		return new Station(
				stationRecord.location(),
				dockingArea,
				stationRecord.maintenanceStatus()
		);
	}
	
	private Map<SlotNumber, ScooterRecord> toDockingAreaRecord(DockingArea dockingArea) {
		Map<SlotNumber, ScooterRecord> dockingAreaRecord = new HashMap<>();
		
		dockingArea.getScooterSlots().forEach((slotNumber, scooterSlot) -> {
			if (scooterSlot.isOccupied()) {
				dockingAreaRecord.put(slotNumber, toScooterRecord(scooterSlot.getDockedScooter()));
			}
			else {
				dockingAreaRecord.put(slotNumber, null);
			}
		});
		
		return dockingAreaRecord;
	}
	
	private DockingArea toDockingAreaDomain(Map<SlotNumber, ScooterRecord> dockingAreaRecord) {
		Map<SlotNumber, ScooterSlot> scooterSlots = new HashMap<>();
		
		dockingAreaRecord.forEach((slotNumber, scooterRecord) -> {
			if (scooterRecord != null) {
				scooterSlots.put(slotNumber, new ScooterSlot(slotNumber, toScooterDomain(scooterRecord)));
			}
			else {
				scooterSlots.put(slotNumber, new ScooterSlot(slotNumber));
			}
		});
		
		return new DockingArea(scooterSlots);
	}
}
