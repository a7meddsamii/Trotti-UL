package ca.ulaval.glo4003.trotti.order.api.controllers;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.order.api.dto.requests.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.order.api.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.order.application.OrderApplicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrderControllerTest {

    private static final String AUTH_HEADER = "Bearer test.jwt.token";

    private OrderApplicationService orderApplicationService;
    private OrderApiMapper orderApiMapper;

    private OrderResource resource;

    @BeforeEach
    void setup() {
        orderApplicationService = Mockito.mock(OrderApplicationService.class);
        orderApiMapper = Mockito.mock(OrderApiMapper.class);

        resource = new OrderController(orderApplicationService, orderApiMapper);
    }

    @Test
    void givenInvalidPaymentInfo_whenConfirm_thenExceptionIsThrown() {
        PaymentInfoRequest invalidRequest = Mockito.mock(PaymentInfoRequest.class);

        Mockito.when(orderApiMapper.toDto(invalidRequest))
                .thenThrow(new InvalidParameterException("Invalid payment info"));

        Assertions.assertThrows(InvalidParameterException.class,
                () -> resource.confirm(invalidRequest));

        Mockito.verify(orderApplicationService, Mockito.never()).placeOrderFor(Mockito.any(),
                Mockito.any());
        Mockito.verify(orderApiMapper, Mockito.never()).toTransactionResponse(Mockito.any());
    }
}
