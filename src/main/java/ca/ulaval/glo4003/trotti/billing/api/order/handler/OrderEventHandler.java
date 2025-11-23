package ca.ulaval.glo4003.trotti.billing.api.order.handler;

import ca.ulaval.glo4003.trotti.billing.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.FreeRidePermitItemGrantDto;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountCreatedEvent;

public class OrderEventHandler {
	
	private final OrderApplicationService orderApplicationService;
	
	public OrderEventHandler(OrderApplicationService orderApplicationService) {
		this.orderApplicationService = orderApplicationService;
	}
	
	public void onAccountCreated(AccountCreatedEvent accountCreatedEvent) {
		FreeRidePermitItemGrantDto freeRidePermitItemGrantDto = null;
		orderApplicationService.grantFreeRidePermitItem(freeRidePermitItemGrantDto);
	}
}
