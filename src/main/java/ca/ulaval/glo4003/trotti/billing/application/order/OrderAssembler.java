package ca.ulaval.glo4003.trotti.billing.application.order;

import ca.ulaval.glo4003.trotti.billing.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;

public class OrderAssembler {
    public OrderDto assemble(Order order) {
        return new OrderDto();
    }
}
