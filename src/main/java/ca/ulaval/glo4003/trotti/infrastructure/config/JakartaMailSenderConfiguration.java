package ca.ulaval.glo4003.trotti.infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import java.util.Properties;

public class JakartaMailSenderConfiguration {
    private static final Dotenv dotenv = Dotenv.load();

    public static final String USERNAME = dotenv.get("SMTP_USER");
    public static final String PASSWORD = dotenv.get("SMTP_PASS");

    public static final String MAIL_SMTP_USERNAME = "FromMail";
    public static final String MAIL_SMTP_USERNAME_VALUE = USERNAME;

    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String MAIL_TRANSPORT_PROTOCOL_VALUE = "smtp";

    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_AUTH_VALUE = "true";

    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_STARTTLS_ENABLE_VALUE = "true";

    private static final String MAIL_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
    private static final String MAIL_SMTP_STARTTLS_REQUIRED_VALUE = "true";

    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_HOST_VALUE = "smtp.office365.com";

    private static final String MAIL_SMTP_PORT= "mail.smtp.port";
    private static final String MAIL_SMTP_PORT_VALUE = "587";

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(MAIL_TRANSPORT_PROTOCOL, MAIL_TRANSPORT_PROTOCOL_VALUE);
        props.put(MAIL_SMTP_AUTH, MAIL_SMTP_AUTH_VALUE);
        props.put(MAIL_SMTP_STARTTLS_ENABLE, MAIL_SMTP_STARTTLS_ENABLE_VALUE);
        props.put(MAIL_SMTP_STARTTLS_REQUIRED, MAIL_SMTP_STARTTLS_REQUIRED_VALUE);
        props.put(MAIL_SMTP_HOST, MAIL_SMTP_HOST_VALUE);
        props.put(MAIL_SMTP_PORT, MAIL_SMTP_PORT_VALUE);
        props.put(MAIL_SMTP_USERNAME,MAIL_SMTP_USERNAME_VALUE);
        return props;
    }

    public static Session getSession(){
       return Session.getInstance(JakartaMailSenderConfiguration.getProperties(),new Authenticator() {
           @Override
           protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(
                       JakartaMailSenderConfiguration.USERNAME,
                       JakartaMailSenderConfiguration.PASSWORD
               );
           }
       });
    }

}
