package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.application.trip.mappers.UnlockCodeMapper;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.UnlockCodeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

class UnlockCodeApplicationServiceTest {

    private static final Idul A_IDUL = Idul.from("ABC123");
    private static final Id A_RIDE_PERMIT_ID = Id.randomId();
    
    private TravelerRepository travelerRepository;
    private UnlockCodeMapper unlockCodeMapper;
    private UnlockCodeService unlockCodeService;
    private Traveler traveler;
    private RidePermit ridePermit;

    private UnlockCodeApplicationService unlockCodeApplicationService;

    @BeforeEach
    void setup() {
        travelerRepository = Mockito.mock(TravelerRepository.class);
        unlockCodeMapper = Mockito.mock(UnlockCodeMapper.class);
        unlockCodeService = Mockito.mock(UnlockCodeService.class);
        ridePermit = Mockito.mock(RidePermit.class);

        unlockCodeApplicationService = new UnlockCodeApplicationService(
                unlockCodeService,
                travelerRepository,
                unlockCodeMapper
        );
    }

    @Test
    void givenTravelerWithPassId_whenGenerateUnlockCode_thenUnlockCodeServiceRequestsCode() {
        traveler = Mockito.mock(Traveler.class);
        UnlockCode unlockCode = Mockito.mock(UnlockCode.class);
        Mockito.when(traveler.getRidePermits()).thenReturn(List.of(ridePermit));
        Mockito.when(ridePermit.matches(A_RIDE_PERMIT_ID)).thenReturn(true);
        Mockito.when(travelerRepository.findByIdul(A_IDUL)).thenReturn(traveler);
        Mockito.when(unlockCodeService.requestUnlockCode(A_RIDE_PERMIT_ID)).thenReturn(unlockCode);

        unlockCodeApplicationService.generateUnlockCode(A_IDUL, A_RIDE_PERMIT_ID);

        Mockito.verify(unlockCodeService).requestUnlockCode(A_RIDE_PERMIT_ID);
    }

    @Test
    void givenTravelerWithInvalidPassId_whenGenerateUnlockCode_thenThrowsNotFoundException() {
        traveler = Mockito.mock(Traveler.class);
        Mockito.when(travelerRepository.findByIdul(A_IDUL)).thenReturn(traveler);
        Mockito.when(traveler.getRidePermits()).thenReturn(Collections.emptyList());

        Executable action = () -> unlockCodeApplicationService.generateUnlockCode(A_IDUL, A_RIDE_PERMIT_ID);

        Assertions.assertThrows(NotFoundException.class, action);
    }

}