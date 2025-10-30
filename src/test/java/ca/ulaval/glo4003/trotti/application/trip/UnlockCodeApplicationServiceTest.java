package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.commons.api.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import java.util.Collections;

import ca.ulaval.glo4003.trotti.trip.application.UnlockCodeApplicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class UnlockCodeApplicationServiceTest {

    private static final Idul A_TRAVELER_ID = Idul.from("ABC123");
    private static final RidePermitId A_RIDE_PERMIT_ID = RidePermitId.randomId();
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
        Mockito.when(traveler.walletHasPermit(A_RIDE_PERMIT_ID)).thenReturn(true);
        Mockito.when(traveler.getIdul()).thenReturn(A_TRAVELER_ID);
        Mockito.when(travelerRepository.findByIdul(A_TRAVELER_ID)).thenReturn(traveler);
        Mockito.when(unlockCodeService.requestUnlockCode(A_TRAVELER_ID)).thenReturn(unlockCode);

        unlockCodeApplicationService.generateUnlockCode(A_TRAVELER_ID, A_RIDE_PERMIT_ID);

        Mockito.verify(unlockCodeService).requestUnlockCode(A_TRAVELER_ID);
    }

    @Test
    void givenTravelerWithPassId_whenGenerateUnlockCode_thenNotificationServiceSendsUnlockCode() {
        traveler = Mockito.mock(Traveler.class);
        Mockito.when(traveler.getEmail()).thenReturn(AN_EMAIL);
        UnlockCode unlockCode = Mockito.mock(UnlockCode.class);
        Mockito.when(traveler.walletHasPermit(A_RIDE_PERMIT_ID)).thenReturn(true);
        Mockito.when(traveler.getIdul()).thenReturn(A_TRAVELER_ID);
        Mockito.when(travelerRepository.findByIdul(A_TRAVELER_ID)).thenReturn(traveler);
        Mockito.when(unlockCodeService.requestUnlockCode(A_TRAVELER_ID)).thenReturn(unlockCode);

        unlockCodeApplicationService.generateUnlockCode(A_TRAVELER_ID, A_RIDE_PERMIT_ID);

        Mockito.verify(notificationService).notify(traveler.getEmail(), unlockCode);
    }

    @Test
    void givenTravelerWithInvalidPassId_whenGenerateUnlockCode_thenThrowsNotFoundException() {
        traveler = Mockito.mock(Traveler.class);
        Mockito.when(travelerRepository.findByIdul(A_TRAVELER_ID)).thenReturn(traveler);
        Mockito.when(traveler.getWalletPermits()).thenReturn(Collections.emptyList());

        Executable action = () -> unlockCodeApplicationService.generateUnlockCode(A_TRAVELER_ID,
                A_RIDE_PERMIT_ID);

        Assertions.assertThrows(NotFoundException.class, action);
    }

}
