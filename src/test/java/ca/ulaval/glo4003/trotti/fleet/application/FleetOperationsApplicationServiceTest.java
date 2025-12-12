package ca.ulaval.glo4003.trotti.fleet.application;

import ca.ulaval.glo4003.trotti.fleet.application.dto.*;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FleetOperationsApplicationServiceTest {
    private static final Instant FIXED_INSTANT = Instant.parse("2025-01-01T10:00:00Z");
    private static final LocalDateTime EXPECTED_TIME =
            LocalDateTime.ofInstant(FIXED_INSTANT, ZoneOffset.UTC);

    private static final Location A_LOCATION = Location.of("PEPS", "Station A");
    private static final SlotNumber A_SLOT = new SlotNumber(1);
    private static final ScooterId A_SCOOTER_ID = Mockito.mock(ScooterId.class);

    private FleetRepository fleetRepository;
    private Fleet fleet;

    private FleetOperationsApplicationService fleetOperationsApplicationService;

    @BeforeEach
    void setup() {
        Clock clock = Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);

        fleetRepository = Mockito.mock(FleetRepository.class);
        fleet = Mockito.mock(Fleet.class);

        Mockito.when(fleetRepository.getFleet()).thenReturn(fleet);

        fleetOperationsApplicationService =
                new FleetOperationsApplicationService(fleetRepository, clock);
    }

    @Test
    void givenRentScooterDto_whenRentScooter_thenFleetIsSaved() {
        RentScooterDto rentScooterDto = new RentScooterDto(A_LOCATION, A_SLOT);
        Mockito.when(fleet.rentScooter(A_LOCATION, A_SLOT, EXPECTED_TIME)).thenReturn(A_SCOOTER_ID);

        fleetOperationsApplicationService.rentScooter(rentScooterDto);

        Mockito.verify(fleetRepository).save(fleet);
    }

    @Test
    void givenRentScooterDto_whenRentScooter_thenFleetRentScooterIsCalled() {
        RentScooterDto rentScooterDto = new RentScooterDto(A_LOCATION, A_SLOT);
        Mockito.when(fleet.rentScooter(A_LOCATION, A_SLOT, EXPECTED_TIME)).thenReturn(A_SCOOTER_ID);

        fleetOperationsApplicationService.rentScooter(rentScooterDto);

        Mockito.verify(fleet).rentScooter(A_LOCATION, A_SLOT, EXPECTED_TIME);
    }

    @Test
    void givenReturnScooterDto_whenReturnScooter_thenFleetReturnScooterIsCalled() {
        ReturnScooterDto returnScooterDto = new ReturnScooterDto(A_SCOOTER_ID, A_LOCATION, A_SLOT);

        fleetOperationsApplicationService.returnScooter(returnScooterDto);

        Mockito.verify(fleet).returnScooter(A_SCOOTER_ID, A_LOCATION, A_SLOT, EXPECTED_TIME);
    }

    @Test
    void givenReturnScooterDto_whenReturnScooter_thenFleetIsSaved() {
        ReturnScooterDto returnScooterDto = new ReturnScooterDto(A_SCOOTER_ID, A_LOCATION, A_SLOT);

        fleetOperationsApplicationService.returnScooter(returnScooterDto);

        Mockito.verify(fleetRepository).save(fleet);
    }

    @Test
    void givenRentScooterDto_whenRentScooter_thenScooterIdIsReturned() {
        RentScooterDto rentScooterDto = new RentScooterDto(A_LOCATION, A_SLOT);
        Mockito.when(fleet.rentScooter(A_LOCATION, A_SLOT, EXPECTED_TIME)).thenReturn(A_SCOOTER_ID);

        ScooterId result = fleetOperationsApplicationService.rentScooter(rentScooterDto);

        Assertions.assertEquals(A_SCOOTER_ID, result);
    }

    @Test
    void givenLocation_whenGetAvailableSlots_thenAvailableSlotsAreReturned() {
        List<SlotNumber> availableSlots = List.of(new SlotNumber(2), new SlotNumber(3));
        Mockito.when(fleet.getAvailableSlots(A_LOCATION)).thenReturn(availableSlots);

        List<SlotNumber> result = fleetOperationsApplicationService.getAvailableSlots(A_LOCATION);

        Assertions.assertEquals(availableSlots, result);
    }

    @Test
    void givenLocation_whenGetOccupiedSlots_thenOccupiedSlotsAreReturned() {
        List<SlotNumber> occupiedSlots = List.of(new SlotNumber(1));
        Mockito.when(fleet.getOccupiedSlots(A_LOCATION)).thenReturn(occupiedSlots);

        List<SlotNumber> result = fleetOperationsApplicationService.getOccupiedSlots(A_LOCATION);

        Assertions.assertEquals(occupiedSlots, result);
    }
}
