package ca.ulaval.glo4003.trotti.trip.api.mappers;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.InitiateTransferRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.UnloadScootersRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.TransferResponse;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndMaintenanceDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.InitiateTransferDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartMaintenanceDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UnloadScootersDto;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StationApiMapperTest {

    private static final String STATION_LOCATION = "VACHON";
    private static final String TRANSFER_ID = TransferId.randomId().toString();
    private static final String IDUL_VALUE = "tech01";
    private static final List<Integer> SLOT_NUMBERS = List.of(1, 2, 3);

    private StationApiMapper mapper;
    private Idul idul;

    @BeforeEach
    void setUp() {
        mapper = new StationApiMapper();
        idul = Idul.from(IDUL_VALUE);
    }

    @Test
    void givenInitiateTransferRequest_whenToInitiateTransferDto_thenMapsCorrectly() {
        InitiateTransferRequest request = new InitiateTransferRequest(STATION_LOCATION, SLOT_NUMBERS);

        InitiateTransferDto result = mapper.toInitiateTransferDto(idul, request);

        assertEquals(Location.of(STATION_LOCATION), result.sourceStation());
        assertEquals(idul, result.technicianId());
        assertEquals(SLOT_NUMBERS.size(), result.sourceSlots().size());
        assertEquals(new SlotNumber(1), result.sourceSlots().get(0));
        assertEquals(new SlotNumber(2), result.sourceSlots().get(1));
        assertEquals(new SlotNumber(3), result.sourceSlots().get(2));
    }

    @Test
    void givenUnloadScootersRequest_whenToUnloadScootersDto_thenMapsCorrectly() {
        UnloadScootersRequest request = new UnloadScootersRequest(STATION_LOCATION, SLOT_NUMBERS);

        UnloadScootersDto result = mapper.toUnloadScootersDto(idul, TRANSFER_ID, request);

        assertEquals(TransferId.from(TRANSFER_ID), result.transferId());
        assertEquals(idul, result.technicianId());
        assertEquals(Location.of(STATION_LOCATION), result.destinationStation());
        assertEquals(SLOT_NUMBERS.size(), result.destinationSlots().size());
        assertEquals(new SlotNumber(1), result.destinationSlots().get(0));
        assertEquals(new SlotNumber(2), result.destinationSlots().get(1));
        assertEquals(new SlotNumber(3), result.destinationSlots().get(2));
    }

    @Test
    void givenStartMaintenanceRequest_whenToStartMaintenanceDto_thenMapsCorrectly() {
        StartMaintenanceRequest request = new StartMaintenanceRequest(STATION_LOCATION);

        StartMaintenanceDto result = mapper.toStartMaintenanceDto(idul, request);

        assertEquals(Location.of(STATION_LOCATION), result.location());
        assertEquals(idul, result.technicianId());
    }

    @Test
    void givenEndMaintenanceRequest_whenToEndMaintenanceDto_thenMapsCorrectly() {
        EndMaintenanceRequest request = new EndMaintenanceRequest(STATION_LOCATION);

        EndMaintenanceDto result = mapper.toEndMaintenanceDto(idul, request);

        assertEquals(Location.of(STATION_LOCATION), result.location());
        assertEquals(idul, result.technicianId());
    }

    @Test
    void givenTransferId_whenToTransferResponse_thenMapsCorrectly() {
        TransferId transferId = TransferId.from(TRANSFER_ID);

        TransferResponse result = mapper.toTransferResponse(transferId);

        assertEquals(TRANSFER_ID, result.transferId());
    }
}