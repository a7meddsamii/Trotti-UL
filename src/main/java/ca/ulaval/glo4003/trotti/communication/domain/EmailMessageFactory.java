package ca.ulaval.glo4003.trotti.communication.domain;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import java.util.List;

public class EmailMessageFactory {

    public EmailMessage createOrderConfirmationMessage(Email recipientEmail, String orderId,
            List<String> ridePermitsIds) {
        String subject = "Your Order #" + orderId;
        String body = "Dear Customer," + "\n\n"
                + "Thank you for your order! Your order is confirmed " + orderId
                + " with the following ride permits IDs: " + String.join(", ", ridePermitsIds)
                + ".\n\nBest regards," + "\nTrotti Team";

        return EmailMessage.builder().withRecipient(recipientEmail).withSubject(subject)
                .withBody(body).build();
    }

    public EmailMessage createTransactionCompletedMessage(Email recipientEmail,
            String transactionId, String status, String transactionDescription) {
        String subject = "Transaction Completed: " + transactionId;
        String body = "Dear Customer," + "\n\n" + "Your transaction with ID " + transactionId
                + " is " + status + ".\n" + "Details: " + transactionDescription
                + "\n\nBest regards," +

                "\nTrotti-UL Team 10";

        return EmailMessage.builder().withRecipient(recipientEmail).withSubject(subject)
                .withBody(body).build();
    }
}
