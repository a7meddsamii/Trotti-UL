package ca.ulaval.glo4003.trotti.domain.trip.communication.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.EmailService;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.commons.communication.values.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UnlockCodeNotificationServiceTest {

    private static final String A_CODE = "123456";
    private static final Email AN_EMAIL = Email.from("abcdef@ulaval.ca");
    private static final String A_SUBJECT = "Unlock Code for your trip";

    private EmailMessage emailMessage;
    private EmailService emailService;

    private NotificationService<UnlockCode> notificationService;

    @BeforeEach
    void setUp() {
        emailMessage = Mockito.mock(EmailMessage.class);
        emailService = Mockito.mock(EmailService.class);
        Mockito.when(emailMessage.getRecipient()).thenReturn(AN_EMAIL);
        Mockito.when(emailMessage.getBody()).thenReturn(A_CODE);

        notificationService = new UnlockCodeNotificationService(emailService);
    }

    @Test
    void givenUnlockCode_whenNotify_thenEmailServiceIsCalled() {
        UnlockCode unlockCode = Mockito.mock(UnlockCode.class);
        Mockito.when(unlockCode.getCode()).thenReturn(A_CODE);

        notificationService.notify(AN_EMAIL, unlockCode);

        Mockito.verify(emailService)
                .send(Mockito.argThat(emailMessage -> emailMessage.getRecipient().equals(AN_EMAIL)
                        && emailMessage.getSubject().equals(A_SUBJECT)
                        && emailMessage.getBody().equals(unlockCode.getCode())));
    }
}
