package ca.ulaval.glo4003.trotti.domain.commons;

public class EmailMessage {

    private final String to;
    private final String subject;
    private final String body;

    public EmailMessage(Email to, String subject, String body) {

        this.to = to.toString();
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public String getBody() {
        return body;
    }

}
