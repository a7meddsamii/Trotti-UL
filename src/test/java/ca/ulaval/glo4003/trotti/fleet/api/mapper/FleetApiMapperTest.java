package ca.ulaval.glo4003.trotti.fleet.api.mapper;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.*;
import ca.ulaval.glo4003.trotti.fleet.application.dto.*;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FleetApiMapperTest {

    private static final String LOCATION_STRING = "STATION-A";
    private static final String MESSAGE = "maintenance-message";

    private static final int SLOT_ZERO = 0;
    private static final int SLOT_ONE = 1;
    private static final List<Integer> INTEGER_SLOTS = List.of(SLOT_ZERO, SLOT_ONE);
    private static final List<SlotNumber> SLOT_NUMBERS =
            List.of(SlotNumber.from(SLOT_ZERO), SlotNumber.from(SLOT_ONE));
    private static final TransferId TRANSFER_ID = TransferId.randomId();
    private static final Idul TECHNICIAN_IDUL = Idul.from("tech-123");
    private static final Idul REQUESTER_IDUL = Idul.from("user-456");

    private FleetApiMapper fleetApiMapper;

    @BeforeEach
    void setUp() {
        fleetApiMapper = new FleetApiMapper();
    }

    @Test
    void givenStartMaintenanceRequest_whenMapped_thenDtoContainsMappedValues() {
        StartMaintenanceRequest request = new StartMaintenanceRequest(LOCATION_STRING);

        StartMaintenanceDto dto = fleetApiMapper.toStartMaintenanceDto(TECHNICIAN_IDUL, request);

        Assertions.assertEquals(Location.of(LOCATION_STRING), dto.location());
        Assertions.assertEquals(TECHNICIAN_IDUL, dto.technicianId());
    }

    @Test
    void givenEndMaintenanceRequest_whenMapped_thenDtoContainsMappedValues() {
        EndMaintenanceRequest request = new EndMaintenanceRequest(LOCATION_STRING);

        EndMaintenanceDto dto = fleetApiMapper.toEndMaintenanceDto(TECHNICIAN_IDUL, request);

        Assertions.assertEquals(Location.of(LOCATION_STRING), dto.location());
        Assertions.assertEquals(TECHNICIAN_IDUL, dto.technicianId());
    }

    @Test
    void givenMaintenanceRequest_whenMapped_thenDtoContainsMappedValues() {
        MaintenanceRequestRequest request = new MaintenanceRequestRequest(LOCATION_STRING, MESSAGE);

        RequestMaintenanceDto dto = fleetApiMapper.toRequestMaintenanceDto(REQUESTER_IDUL, request);

        Assertions.assertEquals(REQUESTER_IDUL, dto.requesterId());
        Assertions.assertEquals(Location.of(LOCATION_STRING), dto.location());
        Assertions.assertEquals(MESSAGE, dto.message());
    }

    @Test
    void givenStartTransferRequest_whenMapped_thenDtoContainsMappedValues() {
        StartTransferRequest request = new StartTransferRequest(LOCATION_STRING, INTEGER_SLOTS);

        StartTransferDto dto = fleetApiMapper.toStartTransferDto(TECHNICIAN_IDUL, request);

        Assertions.assertEquals(TECHNICIAN_IDUL, dto.technicianId());
        Assertions.assertEquals(Location.of(LOCATION_STRING), dto.sourceStation());
        Assertions.assertEquals(SLOT_NUMBERS, dto.sourceSlots());
    }

    @Test
    void givenUnloadTransferRequest_whenMapped_thenDtoContainsMappedValues() {
        UnloadTransferRequest request =
                new UnloadTransferRequest(LOCATION_STRING, INTEGER_SLOTS);

        UnloadTransferDto dto = fleetApiMapper.toUnloadTransferDto(TECHNICIAN_IDUL,
                TRANSFER_ID.toString(), request);

        Assertions.assertEquals(TRANSFER_ID, dto.transferId());
        Assertions.assertEquals(TECHNICIAN_IDUL, dto.technicianId());
        Assertions.assertEquals(Location.of(LOCATION_STRING), dto.destinationStation());
        Assertions.assertEquals(SLOT_NUMBERS, dto.destinationSlots());
    }
}
