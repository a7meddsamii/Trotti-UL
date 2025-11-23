package ca.ulaval.glo4003.trotti.billing.application.order;

import ca.ulaval.glo4003.trotti.billing.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.RidePermitItemDto;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.RidePermitItem;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.RidePermitItemSnapshot;

import java.util.List;

public class OrderAssembler {
	public OrderDto assemble(Order order) {
		List<RidePermitItemDto> ridePermitItemDtos = order.getItems().stream()
				.map(this::assemble)
				.toList();
		return new OrderDto(
				order.getOrderId(),
				order.getBuyerId(),
				ridePermitItemDtos,
				order.getTotalCost(),
				order.getStatus()
		);
	}

    public List<RidePermitItemSnapshot> assembleRidePermitItemSnapshots(Order order) {
        return order.getItems().stream()
                .map(item -> new RidePermitItemSnapshot(
                        item.getSession(),
                        item.getMaximumTravelingTime().getValue(),
                        item.getBillingFrequency()
                ))
                .toList();

    }
	
	private RidePermitItemDto assemble(RidePermitItem ridePermitItem) {
		return new RidePermitItemDto(
				ridePermitItem.getItemId(),
				ridePermitItem.getMaximumTravelingTime(),
				ridePermitItem.getSession(),
				ridePermitItem.getBillingFrequency(),
				ridePermitItem.getCost()
		);
	}
}
