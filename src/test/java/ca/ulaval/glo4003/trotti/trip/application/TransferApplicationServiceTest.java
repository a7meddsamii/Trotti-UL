package ca.ulaval.glo4003.trotti.trip.application;

import static org.assertj.core.api.Assertions.assertThat;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.application.dto.InitiateTransferDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UnloadScootersDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TransferApplicationServiceTest {

    private static final Idul TECHNICIAN_ID = Idul.from("tech123");
    private static final Location SOURCE_LOCATION = Location.of("Building A");
    private static final Location DESTINATION_LOCATION = Location.of("Building B");

    private TransferRepository transferRepository;
    private StationRepository stationRepository;
    private Station sourceStation;
    private Station destinationStation;
    private Transfer transfer;

    private TransferApplicationService transferApplicationService;

    @BeforeEach
    void setup() {
        transferRepository = Mockito.mock(TransferRepository.class);
        stationRepository = Mockito.mock(StationRepository.class);
        sourceStation = Mockito.mock(Station.class);
        destinationStation = Mockito.mock(Station.class);
        transfer = Mockito.mock(Transfer.class);

        transferApplicationService =
                new TransferApplicationService(transferRepository, stationRepository);
    }

    @Test
    void givenValidParameters_whenInitiateTransfer_thenTransferInitiated() {
        List<SlotNumber> sourceSlots = List.of(new SlotNumber(1), new SlotNumber(2));
        Set<ScooterId> scooters = Set.of(ScooterId.randomId());
        Mockito.when(stationRepository.findByLocation(SOURCE_LOCATION)).thenReturn(sourceStation);
        Mockito.when(sourceStation.retrieveScootersForTransfer(sourceSlots)).thenReturn(scooters);

        InitiateTransferDto dto = new InitiateTransferDto(SOURCE_LOCATION, TECHNICIAN_ID, sourceSlots);
        TransferId transferId = transferApplicationService.initiateTransfer(dto);

        Mockito.verify(transferRepository).save(Mockito.any(Transfer.class));
        assertThat(transferId).isNotNull();
    }

    @Test
    void givenValidParameters_whenUnloadScooters_thenTransferIsLoaded() {
        TransferId transferId = TransferId.randomId();
        List<SlotNumber> destinationSlots = List.of(new SlotNumber(1));
        UnloadScootersDto dto = new UnloadScootersDto(transferId, TECHNICIAN_ID, DESTINATION_LOCATION, destinationSlots);
        Mockito.when(transferRepository.findById(transferId)).thenReturn(transfer);
        Mockito.when(stationRepository.findByLocation(DESTINATION_LOCATION))
                .thenReturn(destinationStation);
        Mockito.when(transfer.unload(TECHNICIAN_ID, 1)).thenReturn(List.of());

        transferApplicationService.unloadScooters(dto);

        Mockito.verify(transferRepository).findById(transferId);
    }

    @Test
    void givenValidParameters_whenUnloadScooters_thenScootersAreReturnedToStation() {
        TransferId transferId = TransferId.randomId();
        List<SlotNumber> destinationSlots = List.of(new SlotNumber(1));
        UnloadScootersDto dto = new UnloadScootersDto(transferId, TECHNICIAN_ID, DESTINATION_LOCATION, destinationSlots);
        ScooterId scooterId = ScooterId.randomId();
        Mockito.when(transferRepository.findById(transferId)).thenReturn(transfer);
        Mockito.when(stationRepository.findByLocation(DESTINATION_LOCATION))
                .thenReturn(destinationStation);
        Mockito.when(transfer.unload(TECHNICIAN_ID, 1)).thenReturn(List.of(scooterId));

        transferApplicationService.unloadScooters(dto);

        Mockito.verify(destinationStation).returnScooter(new SlotNumber(1), scooterId);
    }

    @Test
    void givenValidParameters_whenUnloadScooters_thenTransferAndStationAreSaved() {
        TransferId transferId = TransferId.randomId();
        List<SlotNumber> destinationSlots = List.of(new SlotNumber(1));
        UnloadScootersDto dto = new UnloadScootersDto(transferId, TECHNICIAN_ID, DESTINATION_LOCATION, destinationSlots);
        Mockito.when(transferRepository.findById(transferId)).thenReturn(transfer);
        Mockito.when(stationRepository.findByLocation(DESTINATION_LOCATION))
                .thenReturn(destinationStation);
        Mockito.when(transfer.unload(TECHNICIAN_ID, 1)).thenReturn(List.of());

        transferApplicationService.unloadScooters(dto);

        Mockito.verify(transferRepository).save(transfer);
        Mockito.verify(stationRepository).save(destinationStation);
    }

    @Test
    void givenValidLocation_whenFindAvailableSlots_thenStationIsLoaded() {
        Mockito.when(stationRepository.findByLocation(DESTINATION_LOCATION))
                .thenReturn(destinationStation);

        transferApplicationService.findAvailableSlotsInStation(DESTINATION_LOCATION);

        Mockito.verify(stationRepository).findByLocation(DESTINATION_LOCATION);
        Mockito.verify(destinationStation).getAvailableSlots();
    }

    @Test
    void givenValidLocation_whenFindOccupiedSlots_thenStationIsLoaded() {
        Mockito.when(stationRepository.findByLocation(DESTINATION_LOCATION))
                .thenReturn(destinationStation);

        transferApplicationService.findOccupiedSlotsInStation(DESTINATION_LOCATION);

        Mockito.verify(stationRepository).findByLocation(DESTINATION_LOCATION);
        Mockito.verify(destinationStation).getOccupiedSlots();
    }
}
