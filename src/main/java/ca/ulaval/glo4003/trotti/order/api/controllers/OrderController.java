package ca.ulaval.glo4003.trotti.order.api.controllers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.api.dto.requests.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.order.api.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.order.application.OrderApplicationService;
import ca.ulaval.glo4003.trotti.order.application.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.order.application.dto.TransactionDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

public class OrderController implements OrderResource {

    private final OrderApplicationService orderApplicationService;
    private final OrderApiMapper orderApiMapper;
    @Inject
    private Idul userId;

    public OrderController(
            OrderApplicationService orderApplicationService,
            OrderApiMapper orderApiMapper) {
        this.orderApplicationService = orderApplicationService;
        this.orderApiMapper = orderApiMapper;
    }

    @Override
    public Response confirm(PaymentInfoRequest paymentInfoRequest) {

        PaymentInfoDto paymentInfoDto = orderApiMapper.toDto(paymentInfoRequest);

        TransactionDto transactionDto =
                orderApplicationService.placeOrderFor(userId, paymentInfoDto);

        return Response.ok().entity(orderApiMapper.toTransactionResponse(transactionDto)).build();
    }
}
