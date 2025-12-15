package ca.ulaval.glo4003.trotti.communication.domain;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitSnapshot;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailMessageFactoryTest {

    private static final Email RECIPIENT_EMAIL = Email.from("customer@ulaval.ca");
    private static final String ORDER_ID = "ORDER-12345";
    private static final String TRANSACTION_ID = "TXN-67890";
    private static final String TRANSACTION_STATUS = "COMPLETED";
    private static final String TRANSACTION_DESCRIPTION = "Monthly pass payment";
    private static final String CUSTOMER_NAME = "John Doe";
    private static final String UNLOCK_CODE = "ABC123";
    private static final String MAINTENANCE_LOCATION = "VACHON Building";
    private static final String MAINTENANCE_MESSAGE = "Scooter needs battery replacement";
    private static final Idul USER_IDUL = Idul.from("jdoe123");
    private static final String RIDE_PERMIT_ID = "RP-12345";
    private static final String SESSION = "Fall 2024";
    private static final LocalDate SESSION_START_DATE = LocalDate.of(2024, 9, 1);
    private static final LocalDate SESSION_EXPIRATION_DATE = LocalDate.of(2024, 12, 31);

    private EmailMessageFactory emailMessageFactory;

    @BeforeEach
    void setup() {
        emailMessageFactory = new EmailMessageFactory();
    }

    @Test
    void givenOrderId_whenCreateOrderConfirmationMessage_thenReturnsEmailWithCorrectContent() {
        EmailMessage result =
                emailMessageFactory.createOrderConfirmationMessage(RECIPIENT_EMAIL, ORDER_ID);

        Assertions.assertEquals(RECIPIENT_EMAIL, result.getRecipient());
        Assertions.assertEquals("Your Order #" + ORDER_ID, result.getSubject());
        Assertions.assertTrue(result.getBody().contains("Thank you for your order!"));
        Assertions.assertTrue(result.getBody().contains("TrottiUL - Team 10"));
    }

    @Test
    void givenTransactionDetails_whenCreateTransactionCompletedMessage_thenReturnsEmailWithCorrectContent() {
        EmailMessage result = emailMessageFactory.createTransactionCompletedMessage(RECIPIENT_EMAIL,
                TRANSACTION_ID, TRANSACTION_STATUS, TRANSACTION_DESCRIPTION);

        Assertions.assertEquals(RECIPIENT_EMAIL, result.getRecipient());
        Assertions.assertEquals("Transaction Completed: " + TRANSACTION_ID, result.getSubject());
        Assertions.assertTrue(result.getBody().contains(TRANSACTION_ID));
        Assertions.assertTrue(result.getBody().contains(TRANSACTION_STATUS));
        Assertions.assertTrue(result.getBody().contains(TRANSACTION_DESCRIPTION));
    }

    @Test
    void givenNameAndUnlockCode_whenCreateUnlockCodeMessage_thenReturnsEmailWithCorrectContent() {
        EmailMessage result = emailMessageFactory.createUnlockCodeMessage(RECIPIENT_EMAIL,
                CUSTOMER_NAME, UNLOCK_CODE);

        Assertions.assertEquals(RECIPIENT_EMAIL, result.getRecipient());
        Assertions.assertEquals("Unlock Code for your trip", result.getSubject());
        Assertions.assertTrue(result.getBody().contains(CUSTOMER_NAME));
        Assertions.assertTrue(result.getBody().contains(UNLOCK_CODE));
        Assertions.assertTrue(result.getBody().contains("Have a safe ride!"));
    }

    @Test
    void givenLocationAndMessage_whenCreateMaintenanceMessage_thenReturnsEmailWithCorrectContent() {
        EmailMessage result = emailMessageFactory.createMaintenanceMessage(RECIPIENT_EMAIL,
                MAINTENANCE_LOCATION, MAINTENANCE_MESSAGE);

        Assertions.assertEquals(RECIPIENT_EMAIL, result.getRecipient());
        Assertions.assertEquals("Maintenance requested - " + MAINTENANCE_LOCATION,
                result.getSubject());
        Assertions.assertEquals(MAINTENANCE_MESSAGE, result.getBody());
    }

    @Test
    void givenRidePermitSnapshot_whenCreateRidePermitActivationMessage_thenReturnsEmailWithCorrectContent() {
        RidePermitSnapshot snapshot = new RidePermitSnapshot(USER_IDUL, RIDE_PERMIT_ID, SESSION,
                SESSION_START_DATE, SESSION_EXPIRATION_DATE);

        EmailMessage result =
                emailMessageFactory.createRidePermitActivationMessage(RECIPIENT_EMAIL, snapshot);

        Assertions.assertEquals(RECIPIENT_EMAIL, result.getRecipient());
        Assertions.assertEquals("Ride Permit Activation", result.getSubject());
        Assertions.assertTrue(result.getBody().contains(RIDE_PERMIT_ID));
        Assertions.assertTrue(result.getBody().contains(SESSION));
        Assertions.assertTrue(result.getBody().contains(SESSION_START_DATE.toString()));
        Assertions.assertTrue(result.getBody().contains(SESSION_EXPIRATION_DATE.toString()));
        Assertions.assertTrue(result.getBody().contains("Enjoy your rides!"));
    }
}
