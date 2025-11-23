package ca.ulaval.glo4003.trotti.billing.application.order.dto;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderStatus;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

import java.util.List;

public record OrderDto(
		OrderId orderId,
		Idul buyerId,
		List<RidePermitItemDto> ridePermitItems,
		Money totalCost,
		OrderStatus status) {
}
