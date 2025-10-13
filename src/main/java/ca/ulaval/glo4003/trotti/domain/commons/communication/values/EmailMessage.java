package ca.ulaval.glo4003.trotti.domain.commons.communication.values;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;

public class EmailMessage {

    private final Email recipient;
    private final String subject;
    private final String body;

    private EmailMessage(Email recipient, String subject, String body) {
        validate(recipient, subject);

        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public Email getRecipient() {
        return recipient;
    }

    public String getBody() {
        return body;
    }

    public static Builder builder() {
        return new Builder();
    }

    private void validate(Email recipient, String subject) {
        if (recipient == null) {
            throw new InvalidParameterException("Recipient cannot be null");
        }

        if (StringUtils.isBlank(subject)) {
            throw new InvalidParameterException("Subject cannot be null or empty");
        }

    }

    public static class Builder {
        private Email recipient;
        private String subject;
        private String body;

        public Builder withRecipient(Email recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public EmailMessage build() {
            return new EmailMessage(recipient, subject, body);
        }
    }

}
