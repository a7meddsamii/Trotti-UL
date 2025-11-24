package ca.ulaval.glo4003.trotti.billing.api.order.handler;

import ca.ulaval.glo4003.trotti.billing.api.order.mapper.OrderApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.FreeRidePermitItemGrantDto;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountCreatedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.ApplyAdvantageRequestEvent;

import java.util.List;

public class OrderEventHandler {
    private static final String FREE_RIDE_PERMIT_ADVANTAGE = "FREE_RIDE_PERMIT";
    
    private final OrderApplicationService orderApplicationService;
    private final OrderApiMapper orderApiMapper;
    
    public OrderEventHandler(OrderApplicationService orderApplicationService, OrderApiMapper orderApiMapper) {
        this.orderApplicationService = orderApplicationService;
        this.orderApiMapper = orderApiMapper;
    }
    
    public void onAccountCreated(AccountCreatedEvent accountCreatedEvent) {
        if (accountCreatedEvent.getAdvantages().contains(FREE_RIDE_PERMIT_ADVANTAGE)) {
            FreeRidePermitItemGrantDto dto = orderApiMapper.toFreeRidePermitItemGrantDto(accountCreatedEvent.getIdul());
            orderApplicationService.grantFreeRidePermitItem(dto);
        }
    }
    
    public void onApplyAdvantageRequestEvent(ApplyAdvantageRequestEvent event) {
        if (FREE_RIDE_PERMIT_ADVANTAGE.equals(event.getAdvantage())) {
            List<FreeRidePermitItemGrantDto> dtos = orderApiMapper.toFreeRidePermitItemGrantDtos(event.getEligibleUserIds());
            dtos.forEach(orderApplicationService::grantFreeRidePermitItem);
        }
    }
}