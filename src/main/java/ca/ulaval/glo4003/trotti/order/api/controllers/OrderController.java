package ca.ulaval.glo4003.trotti.order.api.controllers;

import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.api.dto.requests.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.order.api.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.order.application.OrderApplicationService;
import ca.ulaval.glo4003.trotti.order.application.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.order.application.dto.TransactionDto;
import jakarta.ws.rs.core.Response;

public class OrderController implements OrderResource {

    private final OrderApplicationService orderApplicationService;
    private final AuthenticationService authenticationService;
    private final OrderApiMapper orderApiMapper;

    public OrderController(
            OrderApplicationService orderApplicationService,
            AuthenticationService authenticationService,
            OrderApiMapper orderApiMapper) {
        this.orderApplicationService = orderApplicationService;
        this.authenticationService = authenticationService;
        this.orderApiMapper = orderApiMapper;
    }

    @Override
    public Response confirm(String tokenRequest, PaymentInfoRequest paymentInfoRequest) {
        AuthenticationToken token = AuthenticationToken.from(tokenRequest);
        Idul idul = authenticationService.authenticate(token);
        authenticationService.confirmStudent(idul);

        PaymentInfoDto paymentInfoDto = orderApiMapper.toDto(paymentInfoRequest);

        TransactionDto transactionDto = orderApplicationService.placeOrderFor(idul, paymentInfoDto);

        return Response.ok().entity(orderApiMapper.toTransactionResponse(transactionDto)).build();
    }
}
