package ca.ulaval.glo4003.trotti.api.order.controllers;

import ca.ulaval.glo4003.trotti.order.api.controllers.OrderController;
import ca.ulaval.glo4003.trotti.order.api.controllers.OrderResource;
import ca.ulaval.glo4003.trotti.order.api.dto.requests.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.order.api.dto.responses.TransactionResponse;
import ca.ulaval.glo4003.trotti.order.api.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.order.application.OrderApplicationService;
import ca.ulaval.glo4003.trotti.order.application.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.order.application.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.commons.exceptions.InvalidParameterException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

class OrderControllerTest {

    private static final String AUTH_HEADER = "Bearer test.jwt.token";

    private OrderApplicationService orderApplicationService;
    private AuthenticationService authenticationService;
    private OrderApiMapper orderApiMapper;

    private OrderResource resource;

    @BeforeEach
    void setup() {
        orderApplicationService = Mockito.mock(OrderApplicationService.class);
        authenticationService = Mockito.mock(AuthenticationService.class);
        orderApiMapper = Mockito.mock(OrderApiMapper.class);

        resource =
                new OrderController(orderApplicationService, authenticationService, orderApiMapper);
    }

    @Test
    void givenValidPaymentInfo_whenConfirm_thenReturns200AndResponse() {
        Idul idul = Mockito.mock(Idul.class);
        PaymentInfoRequest validRequest = Mockito.mock(PaymentInfoRequest.class);
        PaymentInfoDto paymentInfoDto = Mockito.mock(PaymentInfoDto.class);
        TransactionDto transactionDto = Mockito.mock(TransactionDto.class);
        TransactionResponse expectedResponse = Mockito.mock(TransactionResponse.class);

        Mockito.when(authenticationService.authenticate(ArgumentMatchers.any())).thenReturn(idul);
        Mockito.when(orderApiMapper.toDto(validRequest)).thenReturn(paymentInfoDto);
        Mockito.when(orderApplicationService.placeOrderFor(idul, paymentInfoDto))
                .thenReturn(transactionDto);
        Mockito.when(orderApiMapper.toTransactionResponse(transactionDto))
                .thenReturn(expectedResponse);

        Response response = resource.confirm(AUTH_HEADER, validRequest);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(expectedResponse, response.getEntity());

        Mockito.verify(authenticationService).authenticate(ArgumentMatchers.any());
        Mockito.verify(orderApiMapper).toDto(validRequest);
        Mockito.verify(orderApplicationService).placeOrderFor(idul, paymentInfoDto);
        Mockito.verify(orderApiMapper).toTransactionResponse(transactionDto);
    }

    @Test
    void givenInvalidPaymentInfo_whenConfirm_thenExceptionIsThrown() {
        PaymentInfoRequest invalidRequest = Mockito.mock(PaymentInfoRequest.class);

        Mockito.when(authenticationService.authenticate(ArgumentMatchers.any()))
                .thenReturn(Mockito.mock(Idul.class));
        Mockito.when(orderApiMapper.toDto(invalidRequest))
                .thenThrow(new InvalidParameterException("Invalid payment info"));

        Assertions.assertThrows(InvalidParameterException.class,
                () -> resource.confirm(AUTH_HEADER, invalidRequest));

        Mockito.verify(orderApplicationService, Mockito.never()).placeOrderFor(Mockito.any(),
                Mockito.any());
        Mockito.verify(orderApiMapper, Mockito.never()).toTransactionResponse(Mockito.any());
    }
}
