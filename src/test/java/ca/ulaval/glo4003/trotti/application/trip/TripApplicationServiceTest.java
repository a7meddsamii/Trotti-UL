package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TripApplicationServiceTest {
    private static final Idul TRAVELER_IDUL = Idul.from("ulaval");
    private static final RidePermitId RIDE_PERMIT_ID = RidePermitId.randomId();
    private static final String UNLOCK_CODE_VALUE = "123456";
    private static final Location STATION_LOCATION = Location.of("VACHON", "Entrée Vachon #1");
    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);
    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final LocalDateTime START_TIME = LocalDateTime.now();
    private static final LocalDateTime END_TIME = LocalDateTime.now().plusMinutes(10);

    private TravelerRepository travelerRepository;
    private StationRepository stationRepository;
    private ScooterRepository scooterRepository;
    private TripRepository tripRepository;
    private UnlockCodeService unlockCodeService;
    private TripApplicationService tripApplicationService;

    private Traveler traveler;
    private Scooter scooter;
    private Station station;

    @BeforeEach
    void setUp() {
        travelerRepository = Mockito.mock(TravelerRepository.class);
        stationRepository = Mockito.mock(StationRepository.class);
        scooterRepository = Mockito.mock(ScooterRepository.class);
        tripRepository = Mockito.mock(TripRepository.class);
        unlockCodeService = Mockito.mock(UnlockCodeService.class);

        traveler = Mockito.mock(Traveler.class);
        scooter = Mockito.mock(Scooter.class);
        station = Mockito.mock(Station.class);

        Mockito.when(travelerRepository.findByIdul(TRAVELER_IDUL)).thenReturn(traveler);
        Mockito.when(stationRepository.findByLocation(STATION_LOCATION)).thenReturn(station);

        Mockito.when(station.getScooter(SLOT_NUMBER)).thenReturn(SCOOTER_ID);
        Mockito.when(scooterRepository.findById(SCOOTER_ID)).thenReturn(scooter);

        tripApplicationService = new TripApplicationService(travelerRepository, stationRepository,
                scooterRepository, tripRepository, unlockCodeService);
    }

    @Test
    void givenValidUnlockCode_whenStartTrip_thenTravelerStartsTraveling() {

        tripApplicationService.startTrip(TRAVELER_IDUL, RIDE_PERMIT_ID, UNLOCK_CODE_VALUE,
                STATION_LOCATION, SLOT_NUMBER);
        Mockito.verify(traveler).startTraveling(Mockito.any(LocalDateTime.class),
                Mockito.eq(RIDE_PERMIT_ID), Mockito.eq(SCOOTER_ID));
    }

    @Test
    void givenValidUnlockCode_whenStartTrip_thenScooterIsUndocked() {
        tripApplicationService.startTrip(TRAVELER_IDUL, RIDE_PERMIT_ID, UNLOCK_CODE_VALUE,
                STATION_LOCATION, SLOT_NUMBER);

        Mockito.verify(scooter).undock(Mockito.any(LocalDateTime.class));
    }

    @Test
    void givenValidUnlockCode_whenStartTrip_thenUnlockCodeIsValidated() {
        tripApplicationService.startTrip(TRAVELER_IDUL, RIDE_PERMIT_ID, UNLOCK_CODE_VALUE,
                STATION_LOCATION, SLOT_NUMBER);

        Mockito.verify(unlockCodeService).validateCode(UNLOCK_CODE_VALUE, TRAVELER_IDUL);
    }

    @Test
    void givenValidUnlockCode_whenStartTrip_thenAllRepositoriesAreUpdated() {
        tripApplicationService.startTrip(TRAVELER_IDUL, RIDE_PERMIT_ID, UNLOCK_CODE_VALUE,
                STATION_LOCATION, SLOT_NUMBER);

        Mockito.verify(travelerRepository).update(traveler);
        Mockito.verify(scooterRepository).save(scooter);
        Mockito.verify(stationRepository).save(station);
    }
}
