package ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountCreatedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.OrderPlacedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.payment.TransactionCompletedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitActivatedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.UnlockCodeRequestedEvent;
import ca.ulaval.glo4003.trotti.communication.application.*;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class EventSubscriptionLoader extends Bootstrapper {

    @Override
    public void load() {
        EventBus eventBus = this.resourceLocator.resolve(EventBus.class);

        AccountCreatedHandler accountCreatedHandler = this.resourceLocator.resolve(AccountCreatedHandler.class);
        MaintenanceRequestedHandler maintenanceRequestedHandler = this.resourceLocator.resolve(MaintenanceRequestedHandler.class);
        OrderPlacedHandler orderPlacedHandler = this.resourceLocator.resolve(OrderPlacedHandler.class);
        TransactionCompletedHandler transactionCompletedHandler = this.resourceLocator.resolve(TransactionCompletedHandler.class);
        UnlockCodeRequestedHandler unlockCodeRequestedHandler = this.resourceLocator.resolve(UnlockCodeRequestedHandler.class);
        RidePermitActivationHandler ridePermitActivationHandler = this.resourceLocator.resolve(RidePermitActivationHandler.class);

        eventBus.subscribe(AccountCreatedEvent.class, accountCreatedHandler::handle);
        eventBus.subscribe(MaintenanceRequestedEvent.class, maintenanceRequestedHandler::handle);
        eventBus.subscribe(OrderPlacedEvent.class, orderPlacedHandler::handle);
        eventBus.subscribe(TransactionCompletedEvent.class, transactionCompletedHandler::handle);
        eventBus.subscribe(UnlockCodeRequestedEvent.class, unlockCodeRequestedHandler::handle);
        eventBus.subscribe(RidePermitActivatedEvent.class, ridePermitActivationHandler::handle);
    }

}
