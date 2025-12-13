package ca.ulaval.glo4003.trotti.billing.api.order.handler;

import ca.ulaval.glo4003.trotti.billing.api.order.handler.OrderEventHandler;
import ca.ulaval.glo4003.trotti.billing.api.order.mapper.OrderApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.FreeRidePermitItemGrantDto;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountCreatedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.ApplyAdvantageRequestEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class OrderEventHandlerTest {

    private static final String FREE_RIDE_PERMIT_ADVANTAGE = "FREE_RIDE_PERMIT";
    private static final String OTHER_ADVANTAGE = "DISCOUNT_50";
    private static final Idul VALID_USER_IDUL = Idul.from("user123");
    private static final List<Idul> ELIGIBLE_USER_IDULS = List.of(
            Idul.from("user1"),
            Idul.from("user2")
    );

    private OrderApplicationService orderApplicationService;
    private OrderApiMapper orderApiMapper;
    private OrderEventHandler handler;

    @BeforeEach
    void setUp() {
        orderApplicationService = Mockito.mock(OrderApplicationService.class);
        orderApiMapper = Mockito.mock(OrderApiMapper.class);

        handler = new OrderEventHandler(orderApplicationService, orderApiMapper);
    }

    @Test
    void givenAccountCreatedWithFreeRidePermit_whenOnAccountCreated_thenGrantsFreeRidePermit() {
        AccountCreatedEvent event = Mockito.mock(AccountCreatedEvent.class);
        FreeRidePermitItemGrantDto dto = Mockito.mock(FreeRidePermitItemGrantDto.class);
        Mockito.when(event.getAdvantages()).thenReturn(List.of(FREE_RIDE_PERMIT_ADVANTAGE));
        Mockito.when(event.getIdul()).thenReturn(VALID_USER_IDUL);
        Mockito.when(orderApiMapper.toFreeRidePermitItemGrantDto(VALID_USER_IDUL))
                .thenReturn(dto);

        handler.onAccountCreated(event);

        Mockito.verify(orderApiMapper).toFreeRidePermitItemGrantDto(VALID_USER_IDUL);
        Mockito.verify(orderApplicationService).grantFreeRidePermitItem(dto);
    }

    @Test
    void givenAccountCreatedWithoutFreeRidePermit_whenOnAccountCreated_thenDoesNotGrantFreeRidePermit() {
        AccountCreatedEvent event = Mockito.mock(AccountCreatedEvent.class);
        Mockito.when(event.getAdvantages()).thenReturn(List.of(OTHER_ADVANTAGE));

        handler.onAccountCreated(event);

        Mockito.verify(orderApiMapper, Mockito.never()).toFreeRidePermitItemGrantDto(Mockito.any());
        Mockito.verify(orderApplicationService, Mockito.never()).grantFreeRidePermitItem(Mockito.any());
    }

    @Test
    void givenAccountCreatedWithNoAdvantages_whenOnAccountCreated_thenDoesNotGrantFreeRidePermit() {
        AccountCreatedEvent event = Mockito.mock(AccountCreatedEvent.class);
        Mockito.when(event.getAdvantages()).thenReturn(List.of());

        handler.onAccountCreated(event);

        Mockito.verify(orderApiMapper, Mockito.never()).toFreeRidePermitItemGrantDto(Mockito.any());
        Mockito.verify(orderApplicationService, Mockito.never()).grantFreeRidePermitItem(Mockito.any());
    }

    @Test
    void givenApplyAdvantageEventWithFreeRidePermit_whenOnApplyAdvantageRequestEvent_thenGrantsFreeRidePermitToAllEligibleUsers() {
        ApplyAdvantageRequestEvent event = Mockito.mock(ApplyAdvantageRequestEvent.class);
        FreeRidePermitItemGrantDto dto1 = Mockito.mock(FreeRidePermitItemGrantDto.class);
        FreeRidePermitItemGrantDto dto2 = Mockito.mock(FreeRidePermitItemGrantDto.class);
        List<FreeRidePermitItemGrantDto> dtos = List.of(dto1, dto2);

        Mockito.when(event.getAdvantage()).thenReturn(FREE_RIDE_PERMIT_ADVANTAGE);
        Mockito.when(event.getEligibleUserIds()).thenReturn(ELIGIBLE_USER_IDULS);
        Mockito.when(orderApiMapper.toFreeRidePermitItemGrantDtos(ELIGIBLE_USER_IDULS))
                .thenReturn(dtos);

        handler.onApplyAdvantageRequestEvent(event);

        Mockito.verify(orderApiMapper).toFreeRidePermitItemGrantDtos(ELIGIBLE_USER_IDULS);
        Mockito.verify(orderApplicationService).grantFreeRidePermitItem(dto1);
        Mockito.verify(orderApplicationService).grantFreeRidePermitItem(dto2);
    }

    @Test
    void givenApplyAdvantageEventWithOtherAdvantage_whenOnApplyAdvantageRequestEvent_thenDoesNotGrantFreeRidePermit() {
        ApplyAdvantageRequestEvent event = Mockito.mock(ApplyAdvantageRequestEvent.class);
        Mockito.when(event.getAdvantage()).thenReturn(OTHER_ADVANTAGE);

        handler.onApplyAdvantageRequestEvent(event);

        Mockito.verify(orderApiMapper, Mockito.never()).toFreeRidePermitItemGrantDtos(Mockito.any());
        Mockito.verify(orderApplicationService, Mockito.never()).grantFreeRidePermitItem(Mockito.any());
    }

}
