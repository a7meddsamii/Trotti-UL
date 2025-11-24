package ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.api.handler.AccountEventHandler;
import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.billing.api.order.handler.OrderEventHandler;
import ca.ulaval.glo4003.trotti.billing.api.order.mapper.OrderApiMapper;
import ca.ulaval.glo4003.trotti.billing.api.ridepermit.handler.RidePermitEventHandler;
import ca.ulaval.glo4003.trotti.billing.api.ridepermit.mapper.RidePermitApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountAdvantageRenewalEvent;
import ca.ulaval.glo4003.trotti.communication.application.*;
import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class HandlerLoader extends Bootstrapper {

    @Override
    public void load() {
		EmailMessageFactory emailMessageFactory = this.resourceLocator.resolve(EmailMessageFactory.class);
				EmailService emailService = this.resourceLocator.resolve(EmailService.class);
        OrderApiMapper orderApiMapper = this.resourceLocator.resolve(OrderApiMapper.class);
        OrderApplicationService orderApplicationService =
                this.resourceLocator.resolve(OrderApplicationService.class);
        RidePermitApiMapper ridePermitApiMapper =
                this.resourceLocator.resolve(RidePermitApiMapper.class);
        RidePermitApplicationService ridePermitApplicationService =
                this.resourceLocator.resolve(RidePermitApplicationService.class);
		AccountApplicationService accountApplicationService = this.resourceLocator.resolve(AccountApplicationService.class);
		
        this.resourceLocator.register(CommunicationAccountCreatedHandler.class,
                new CommunicationAccountCreatedHandler());
        this.resourceLocator.register(CommunicationMaintenanceRequestedHandler.class,
                new CommunicationMaintenanceRequestedHandler(emailService, emailMessageFactory));
        this.resourceLocator.register(EmailMessageFactory.class, new EmailMessageFactory());
        this.resourceLocator.register(CommunicationOrderPlacedHandler.class,
                new CommunicationOrderPlacedHandler(emailService, emailMessageFactory));
        this.resourceLocator.register(RidePermitActivationHandler.class,
                new RidePermitActivationHandler(emailService, emailMessageFactory));
        this.resourceLocator.register(RidePermitEventHandler.class,
                new RidePermitEventHandler(ridePermitApiMapper, ridePermitApplicationService));
        this.resourceLocator.register(CommunicationTransactionCompletedHandler.class,
                new CommunicationTransactionCompletedHandler(emailService, emailMessageFactory));
        this.resourceLocator.register(CommunicationUnlockCodeRequestedHandler.class,
                new CommunicationUnlockCodeRequestedHandler(emailService, emailMessageFactory));
        this.resourceLocator.register(OrderEventHandler.class,
                new OrderEventHandler(orderApplicationService, orderApiMapper));
		this.resourceLocator.register(AccountEventHandler.class, new AccountEventHandler(accountApplicationService));
    }
}
