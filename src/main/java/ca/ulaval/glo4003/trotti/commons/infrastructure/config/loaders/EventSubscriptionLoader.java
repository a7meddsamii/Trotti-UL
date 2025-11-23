package ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.handler.RidePermitEventHandler;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountCreatedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.OrderPlacedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.payment.TransactionCompletedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitActivatedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.TripCompletedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.UnlockCodeRequestedEvent;
import ca.ulaval.glo4003.trotti.communication.application.*;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class EventSubscriptionLoader extends Bootstrapper {

    @Override
    public void load() {
        EventBus eventBus = this.resourceLocator.resolve(EventBus.class);

        CommunicationAccountCreatedHandler communicationAccountCreatedHandler = this.resourceLocator.resolve(CommunicationAccountCreatedHandler.class);
        CommunicationMaintenanceRequestedHandler communicationMaintenanceRequestedHandler = this.resourceLocator.resolve(CommunicationMaintenanceRequestedHandler.class);
        CommunicationOrderPlacedHandler communicationOrderPlacedHandler = this.resourceLocator.resolve(CommunicationOrderPlacedHandler.class);
        CommunicationTransactionCompletedHandler communicationTransactionCompletedHandler = this.resourceLocator.resolve(CommunicationTransactionCompletedHandler.class);
        CommunicationUnlockCodeRequestedHandler communicationUnlockCodeRequestedHandler = this.resourceLocator.resolve(CommunicationUnlockCodeRequestedHandler.class);
        RidePermitActivationHandler ridePermitActivationHandler = this.resourceLocator.resolve(RidePermitActivationHandler.class);
        RidePermitEventHandler ridePermitEventHandler = this.resourceLocator.resolve(RidePermitEventHandler.class);

        eventBus.subscribe(AccountCreatedEvent.class, communicationAccountCreatedHandler::handle);
        eventBus.subscribe(MaintenanceRequestedEvent.class, communicationMaintenanceRequestedHandler::handle);
        eventBus.subscribe(OrderPlacedEvent.class, communicationOrderPlacedHandler::handle);
        eventBus.subscribe(TransactionCompletedEvent.class, communicationTransactionCompletedHandler::handle);
        eventBus.subscribe(UnlockCodeRequestedEvent.class, communicationUnlockCodeRequestedHandler::handle);
        eventBus.subscribe(RidePermitActivatedEvent.class, ridePermitActivationHandler::handle);
        eventBus.subscribe(OrderPlacedEvent.class, ridePermitEventHandler::onOrderPlaced);
		eventBus.subscribe(TripCompletedEvent.class, ridePermitEventHandler::onTripCompleted);
    }
}
