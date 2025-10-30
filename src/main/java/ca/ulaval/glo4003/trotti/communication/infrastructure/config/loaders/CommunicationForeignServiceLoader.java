package ca.ulaval.glo4003.trotti.communication.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.infrastructure.config.mail.JakartaMailServiceConfiguration;
import ca.ulaval.glo4003.trotti.communication.infrastructure.services.JakartaEmailServiceAdapter;
import ca.ulaval.glo4003.trotti.config.loaders.Bootstrapper;

public class CommunicationForeignServiceLoader extends Bootstrapper {

    private static final String EMAIL_SMTP_USER = "SMTP_USER";
    private static final String EMAIL_SMTP_PASSWORD = "SMTP_PASS";
    private static final String EMAIL_SMTP_HOST = "SMTP_HOST";
    private static final String EMAIL_SMTP_PORT = "SMTP_PORT";

    @Override
    public void load() {
        loadEmailService();
    }

    private void loadEmailService() {
        String username = System.getenv(EMAIL_SMTP_USER);
        String password = System.getenv(EMAIL_SMTP_PASSWORD);
        String host = System.getenv(EMAIL_SMTP_HOST);
        String port = System.getenv(EMAIL_SMTP_PORT);

        JakartaMailServiceConfiguration emailConfiguration =
                JakartaMailServiceConfiguration.create(username, password, host, port);
        EmailService emailService = new JakartaEmailServiceAdapter(emailConfiguration.connect());
        this.resourceLocator.register(EmailService.class, emailService);
    }
}
