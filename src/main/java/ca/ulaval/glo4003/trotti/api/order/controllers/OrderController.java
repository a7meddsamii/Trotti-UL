package ca.ulaval.glo4003.trotti.api.order.controllers;

import ca.ulaval.glo4003.trotti.api.order.dto.requests.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.api.order.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.application.order.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.application.order.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
    public Response confirm(String tokenRequest,
            PaymentInfoRequest paymentInfoRequest) {
        AuthenticationToken token = AuthenticationToken.from(tokenRequest);
        Idul idul = authenticationService.authenticate(token);
        authenticationService.confirmStudent(idul);

        PaymentInfoDto paymentInfoDto = orderApiMapper.toDto(paymentInfoRequest);

        TransactionDto transactionDto = orderApplicationService.placeOrderFor(idul, paymentInfoDto);

        return Response.ok().entity(orderApiMapper.toTransactionResponse(transactionDto)).build();
    }
}
