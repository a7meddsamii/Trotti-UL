package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.application.dto.InitiateTransferDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UnloadScootersDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TransferApplicationServiceTest {

    private static final Idul TECHNICIAN_ID = Idul.from("tech123");
    private static final Location SOURCE_LOCATION = Location.of("Building A");
    private static final Location DESTINATION_LOCATION = Location.of("Building B");

    private TransferRepository transferRepository;
    private StationRepository stationRepository;
    private ScooterRepository scooterRepository;
    private Station sourceStation;
    private Station destinationStation;
    private Transfer transfer;
    private Clock clock;

    private TransferApplicationService transferApplicationService;

    @BeforeEach
    void setup() {
        transferRepository = Mockito.mock(TransferRepository.class);
        stationRepository = Mockito.mock(StationRepository.class);
        scooterRepository = Mockito.mock(ScooterRepository.class);
        sourceStation = Mockito.mock(Station.class);
        destinationStation = Mockito.mock(Station.class);
        transfer = Mockito.mock(Transfer.class);
        clock = Clock.systemDefaultZone();

        transferApplicationService = new TransferApplicationService(transferRepository,
                stationRepository, scooterRepository, clock);
    }

    @Test
    void givenValidParameters_whenInitiateTransfer_thenTransferInitiated() {
        List<SlotNumber> sourceSlots = List.of(new SlotNumber(1), new SlotNumber(2));
        ScooterId scooterId = ScooterId.randomId();
        Set<ScooterId> scooters = Set.of(scooterId);
        Scooter scooter = Mockito.mock(Scooter.class);

        Mockito.when(stationRepository.findByLocation(SOURCE_LOCATION)).thenReturn(sourceStation);
        Mockito.when(sourceStation.retrieveScootersForTransfer(sourceSlots)).thenReturn(scooters);
        Mockito.when(scooterRepository.findById(scooterId)).thenReturn(scooter);

        InitiateTransferDto dto =
                new InitiateTransferDto(SOURCE_LOCATION, TECHNICIAN_ID, sourceSlots);
        TransferId transferId = transferApplicationService.initiateTransfer(dto);

        Mockito.verify(sourceStation).validateTechnicianInCharge(TECHNICIAN_ID);
        Mockito.verify(transferRepository).save(Mockito.any(Transfer.class));
        Assertions.assertThat(transferId).isNotNull();
    }

    @Test
    void givenValidParameters_whenUnloadScooters_thenNumberOfScootersInTransitIsReturned() {
        TransferId transferId = TransferId.randomId();
        List<SlotNumber> destinationSlots = List.of(new SlotNumber(1));
        UnloadScootersDto dto = new UnloadScootersDto(transferId, TECHNICIAN_ID,
                DESTINATION_LOCATION, destinationSlots);
        Mockito.when(transferRepository.findById(transferId)).thenReturn(Optional.of(transfer));
        Mockito.when(stationRepository.findByLocation(DESTINATION_LOCATION))
                .thenReturn(destinationStation);
        Mockito.when(transfer.unload(TECHNICIAN_ID, 1)).thenReturn(List.of());
        Mockito.when(transfer.getScootersInTransitCount()).thenReturn(5);

        int result = transferApplicationService.unloadScooters(dto);

        Assertions.assertThat(result).isEqualTo(5);
    }

    @Test
    void givenValidParameters_whenUnloadScooters_thenScootersAreReturnedToStation() {
        TransferId transferId = TransferId.randomId();
        List<SlotNumber> destinationSlots = List.of(new SlotNumber(1));
        UnloadScootersDto dto = new UnloadScootersDto(transferId, TECHNICIAN_ID,
                DESTINATION_LOCATION, destinationSlots);
        ScooterId scooterId = ScooterId.randomId();
        Scooter scooter = Mockito.mock(Scooter.class);

        Mockito.when(transferRepository.findById(transferId)).thenReturn(Optional.of(transfer));
        Mockito.when(stationRepository.findByLocation(DESTINATION_LOCATION))
                .thenReturn(destinationStation);
        Mockito.when(transfer.unload(TECHNICIAN_ID, 1)).thenReturn(List.of(scooterId));
        Mockito.when(transfer.getScootersInTransitCount()).thenReturn(3);
        Mockito.when(scooterRepository.findById(scooterId)).thenReturn(scooter);

        transferApplicationService.unloadScooters(dto);

        Mockito.verify(destinationStation).returnScooters(destinationSlots, List.of(scooterId));
    }

    @Test
    void givenValidParameters_whenUnloadScooters_thenTransferAndStationAreSaved() {
        TransferId transferId = TransferId.randomId();
        List<SlotNumber> destinationSlots = List.of(new SlotNumber(1));
        UnloadScootersDto dto = new UnloadScootersDto(transferId, TECHNICIAN_ID,
                DESTINATION_LOCATION, destinationSlots);
        Mockito.when(transferRepository.findById(transferId)).thenReturn(Optional.of(transfer));
        Mockito.when(stationRepository.findByLocation(DESTINATION_LOCATION))
                .thenReturn(destinationStation);
        Mockito.when(transfer.unload(TECHNICIAN_ID, 1)).thenReturn(List.of());
        Mockito.when(transfer.getScootersInTransitCount()).thenReturn(2);

        transferApplicationService.unloadScooters(dto);

        Mockito.verify(transferRepository).save(transfer);
        Mockito.verify(stationRepository).save(destinationStation);
    }

    @Test
    void givenValidParameters_whenInitiateTransfer_thenScootersAreUndocked() {
        List<SlotNumber> sourceSlots = List.of(new SlotNumber(1));
        ScooterId scooterId = ScooterId.randomId();
        Set<ScooterId> scooters = Set.of(scooterId);
        Scooter scooter = Mockito.mock(Scooter.class);
        Mockito.when(stationRepository.findByLocation(SOURCE_LOCATION)).thenReturn(sourceStation);
        Mockito.when(sourceStation.retrieveScootersForTransfer(sourceSlots)).thenReturn(scooters);
        Mockito.when(scooterRepository.findById(scooterId)).thenReturn(scooter);

        InitiateTransferDto dto =
                new InitiateTransferDto(SOURCE_LOCATION, TECHNICIAN_ID, sourceSlots);
        transferApplicationService.initiateTransfer(dto);

        Mockito.verify(scooter).undock(Mockito.any(LocalDateTime.class));
        Mockito.verify(scooterRepository).save(scooter);
    }

    @Test
    void givenValidParameters_whenUnloadScooters_thenScootersAreDockedAtDestination() {
        TransferId transferId = TransferId.randomId();
        List<SlotNumber> destinationSlots = List.of(new SlotNumber(1));
        ScooterId scooterId = ScooterId.randomId();
        Scooter scooter = Mockito.mock(Scooter.class);
        Mockito.when(transferRepository.findById(transferId)).thenReturn(Optional.of(transfer));
        Mockito.when(stationRepository.findByLocation(DESTINATION_LOCATION))
                .thenReturn(destinationStation);
        Mockito.when(transfer.unload(TECHNICIAN_ID, 1)).thenReturn(List.of(scooterId));
        Mockito.when(scooterRepository.findById(scooterId)).thenReturn(scooter);

        UnloadScootersDto dto = new UnloadScootersDto(transferId, TECHNICIAN_ID,
                DESTINATION_LOCATION, destinationSlots);
        transferApplicationService.unloadScooters(dto);

        Mockito.verify(scooter).dockAt(Mockito.eq(DESTINATION_LOCATION),
                Mockito.any(LocalDateTime.class));
        Mockito.verify(scooterRepository).save(scooter);
    }
}
