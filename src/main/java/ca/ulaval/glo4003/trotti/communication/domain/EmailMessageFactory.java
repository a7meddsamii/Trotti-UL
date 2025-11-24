package ca.ulaval.glo4003.trotti.communication.domain;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitSnapshot;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;

public class EmailMessageFactory {

    private static final String END_TEXT = "\n\nBest regards," + "\nTrottiUL - Team 10";

    public EmailMessage createOrderConfirmationMessage(Email recipientEmail, String orderId) {
        String subject = "Your Order #" + orderId;
        String body = "Dear Customer," + "\n\n" + "Thank you for your order!" + END_TEXT;

        return EmailMessage.builder().withRecipient(recipientEmail).withSubject(subject)
                .withBody(body).build();
    }

    public EmailMessage createTransactionCompletedMessage(Email recipientEmail,
            String transactionId, String status, String transactionDescription) {
        String subject = "Transaction Completed: " + transactionId;
        String body = "Dear Customer," + "\n\n" + "Your transaction with ID " + transactionId
                + " is " + status + ".\n" + "Details: " + transactionDescription + END_TEXT;

        return EmailMessage.builder().withRecipient(recipientEmail).withSubject(subject)
                .withBody(body).build();
    }

    public EmailMessage createUnlockCodeMessage(Email recipientEmail, String name,
            String unlockCode) {
        String body = "Hello " + name + ", \n" + "Here is your code to unlock your scooter : \n"
                + unlockCode + "\n" + "Have a safe ride!\n" + END_TEXT;

        return EmailMessage.builder().withSubject("Unlock Code for your trip").withBody(body)
                .withRecipient(recipientEmail).build();
    }

    public EmailMessage createMaintenanceMessage(Email recipientEmail, String location,
            String message) {
        return EmailMessage.builder().withSubject("Maintenance requested - " + location)
                .withBody(message).withRecipient(recipientEmail).build();
    }

    public EmailMessage createRidePermitActivationMessage(Email email,
            RidePermitSnapshot ridePermitSnapshot) {
        String body = "Your ride permit with ID: " + ridePermitSnapshot.ridePermitId()
                + " has been activated.\n\n" + "Valid for the session: "
                + ridePermitSnapshot.session() + "\nEffective period: " + "\n\t- From: "
                + ridePermitSnapshot.sessionStartDate() + "\n\t- To: "
                + ridePermitSnapshot.sessionExpirationDate() + ".\n\nEnjoy your rides!" + END_TEXT;

        return EmailMessage.builder().withSubject("Ride Permit Activation").withBody(body)
                .withRecipient(email).build();
    }
}
