package ca.ulaval.glo4003.trotti.infrastructure.config;

import ca.ulaval.glo4003.trotti.infrastructure.config.binders.ServerResourceInstantiation;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class JakartaMailServiceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(JakartaMailServiceConfiguration.class);
    private static final String MAIL_SMTP_USERNAME = "FromMail";
    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String MAIL_TRANSPORT_PROTOCOL_VALUE = "smtp";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_AUTH_VALUE = "true";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_STARTTLS_ENABLE_VALUE = "true";
    private static final String MAIL_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
    private static final String MAIL_SMTP_STARTTLS_REQUIRED_VALUE = "true";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT= "mail.smtp.port";

    private final String username;
    private final String password;
    private final String host;
    private final String port;
    private Session session;


    private JakartaMailServiceConfiguration(String username, String password,  String host, String port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public static JakartaMailServiceConfiguration create(String username, String password,  String host, String port){
        return new JakartaMailServiceConfiguration(username, password, host, port);
    }


    private Properties getProperties() {
        Properties props = new Properties();
        props.put(MAIL_TRANSPORT_PROTOCOL, MAIL_TRANSPORT_PROTOCOL_VALUE);
        props.put(MAIL_SMTP_AUTH, MAIL_SMTP_AUTH_VALUE);
        props.put(MAIL_SMTP_STARTTLS_ENABLE, MAIL_SMTP_STARTTLS_ENABLE_VALUE);
        props.put(MAIL_SMTP_STARTTLS_REQUIRED, MAIL_SMTP_STARTTLS_REQUIRED_VALUE);
        props.put(MAIL_SMTP_HOST, this.host);
        props.put(MAIL_SMTP_PORT, this.port);
        props.put(MAIL_SMTP_USERNAME,this.username);
        return props;
    }

    public  Session connect(){
        try{
            if (session == null){
                this.session = Session.getInstance(getProperties(),new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                username,
                                password
                        );
                    }
                });
            }

            return this.session;
        }catch (Exception exception){
            LOGGER.error("Failed to setup email service, please check your environment variables");
            throw exception;
        }
    }

}
