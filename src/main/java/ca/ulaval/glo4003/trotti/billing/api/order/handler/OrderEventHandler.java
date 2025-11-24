package ca.ulaval.glo4003.trotti.billing.api.order.handler;

import ca.ulaval.glo4003.trotti.billing.api.order.mapper.OrderApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.FreeRidePermitItemGrantDto;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountCreatedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.ApplyAdvantageRequestEvent;

import java.util.List;

public class OrderEventHandler {
	private static final List<String> ADVANTAGES_RESPONSIBILITIES = List.of("FREE_RIDE_PERMIT");
	
	private final OrderApplicationService orderApplicationService;
	private final OrderApiMapper orderApiMapper;
	
	public OrderEventHandler(OrderApplicationService orderApplicationService, OrderApiMapper orderApiMapper) {
		this.orderApplicationService = orderApplicationService;
		this.orderApiMapper = orderApiMapper;
	}
	
	public void onAccountCreated(AccountCreatedEvent accountCreatedEvent) {
		FreeRidePermitItemGrantDto freeRidePermitItemGrantDto = orderApiMapper.toFreeRidePermitItemGrantDto(accountCreatedEvent.getIdul());
		orderApplicationService.grantFreeRidePermitItem(freeRidePermitItemGrantDto);
	}
	
	public void onApplyAdvantageRequestEvent(ApplyAdvantageRequestEvent applyAdvantageRequestEvent) {
		if (ADVANTAGES_RESPONSIBILITIES.contains(applyAdvantageRequestEvent.getAdvantage())) {
			List<FreeRidePermitItemGrantDto> freeRidePermitItemGrantDtos = orderApiMapper.toFreeRidePermitItemGrantDtos(applyAdvantageRequestEvent.getEligibleUserIds());
			freeRidePermitItemGrantDtos.forEach(orderApplicationService::grantFreeRidePermitItem);
		}
	}
}
