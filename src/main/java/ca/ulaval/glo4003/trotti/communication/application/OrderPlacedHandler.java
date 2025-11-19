package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.config.events.handlers.EventHandler;
import ca.ulaval.glo4003.trotti.order.domain.events.OrderPlacedEvent;

public class OrderPlacedHandler extends EmailSendHandler implements EventHandler<OrderPlacedEvent> {


    public OrderPlacedHandler(EmailService emailService) {
        super(emailService);
    }

    @Override
    public void handle(OrderPlacedEvent event) {

    }
}
