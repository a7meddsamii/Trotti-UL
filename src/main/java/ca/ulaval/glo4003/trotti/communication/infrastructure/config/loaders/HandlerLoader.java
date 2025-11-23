package ca.ulaval.glo4003.trotti.communication.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.communication.application.*;
import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class HandlerLoader extends Bootstrapper {


    @Override
    public void load() {
        EmailMessageFactory emailMessageFactory = new EmailMessageFactory();
        EmailService emailService = this.resourceLocator.resolve(EmailService.class);

        this.resourceLocator.register(AccountCreatedHandler.class, new AccountCreatedHandler());
        this.resourceLocator.register(MaintenanceRequestedHandler.class, new MaintenanceRequestedHandler(emailService, emailMessageFactory));
        this.resourceLocator.register(EmailMessageFactory.class, new EmailMessageFactory());
        this.resourceLocator.register(OrderPlacedHandler.class, new OrderPlacedHandler(emailService, emailMessageFactory));
        this.resourceLocator.register(RidePermitActivationHandler.class, new RidePermitActivationHandler(emailService, emailMessageFactory));
        this.resourceLocator.register(TransactionCompletedHandler.class, new TransactionCompletedHandler(emailService, emailMessageFactory));
        this.resourceLocator.register(UnlockCodeRequestedHandler.class, new UnlockCodeRequestedHandler(emailService, emailMessageFactory));
    }
}
