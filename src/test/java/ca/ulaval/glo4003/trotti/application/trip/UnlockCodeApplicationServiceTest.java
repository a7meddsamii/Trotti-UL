package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.UnlockCodeService;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class UnlockCodeApplicationServiceTest {

    private static final Idul A_IDUL = Idul.from("ABC123");
    private static final Id A_RIDE_PERMIT_ID = Id.randomId();
    private static final Email AN_EMAIL = Email.from("ahsam@ulaval.ca");

    private TravelerRepository travelerRepository;
    private NotificationService<UnlockCode> notificationService;
    private UnlockCodeService unlockCodeService;
    private Traveler traveler;

    private UnlockCodeApplicationService unlockCodeApplicationService;

    @BeforeEach
    void setup() {
        notificationService = Mockito.mock(NotificationService.class);
        travelerRepository = Mockito.mock(TravelerRepository.class);
        unlockCodeService = Mockito.mock(UnlockCodeService.class);

        unlockCodeApplicationService = new UnlockCodeApplicationService(unlockCodeService,
                travelerRepository, notificationService);
    }

    @Test
    void givenTravelerWithPassId_whenGenerateUnlockCode_thenUnlockCodeServiceRequestsCode() {
        traveler = Mockito.mock(Traveler.class);
        UnlockCode unlockCode = Mockito.mock(UnlockCode.class);
        Mockito.when(traveler.hasRidePermit(A_RIDE_PERMIT_ID)).thenReturn(true);
        Mockito.when(travelerRepository.findByIdul(A_IDUL)).thenReturn(traveler);
        Mockito.when(unlockCodeService.requestUnlockCode(A_RIDE_PERMIT_ID)).thenReturn(unlockCode);

        unlockCodeApplicationService.generateUnlockCode(A_IDUL, A_RIDE_PERMIT_ID);

        Mockito.verify(unlockCodeService).requestUnlockCode(A_RIDE_PERMIT_ID);
    }

    @Test
    void givenTravelerWithPassId_whenGenerateUnlockCode_thenNotificationServiceSendsUnlockCode() {
        traveler = Mockito.mock(Traveler.class);
        Mockito.when(traveler.getEmail()).thenReturn(AN_EMAIL);
        UnlockCode unlockCode = Mockito.mock(UnlockCode.class);
        Mockito.when(traveler.hasRidePermit(A_RIDE_PERMIT_ID)).thenReturn(true);
        Mockito.when(travelerRepository.findByIdul(A_IDUL)).thenReturn(traveler);
        Mockito.when(unlockCodeService.requestUnlockCode(A_RIDE_PERMIT_ID)).thenReturn(unlockCode);

        unlockCodeApplicationService.generateUnlockCode(A_IDUL, A_RIDE_PERMIT_ID);

        Mockito.verify(notificationService).notify(traveler.getEmail(), unlockCode);
    }

    @Test
    void givenTravelerWithInvalidPassId_whenGenerateUnlockCode_thenThrowsNotFoundException() {
        traveler = Mockito.mock(Traveler.class);
        Mockito.when(travelerRepository.findByIdul(A_IDUL)).thenReturn(traveler);
        Mockito.when(traveler.getRidePermits()).thenReturn(Collections.emptyList());

        Executable action =
                () -> unlockCodeApplicationService.generateUnlockCode(A_IDUL, A_RIDE_PERMIT_ID);

        Assertions.assertThrows(NotFoundException.class, action);
    }

}
