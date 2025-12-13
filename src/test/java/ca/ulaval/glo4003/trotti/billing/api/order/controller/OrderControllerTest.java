package ca.ulaval.glo4003.trotti.billing.api.order.controller;

import ca.ulaval.glo4003.trotti.billing.api.order.controller.OrderController;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.request.ItemRequest;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.request.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.response.ItemListResponse;
import ca.ulaval.glo4003.trotti.billing.api.order.mapper.OrderApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.AddItemDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.ConfirmOrderDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.ItemId;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class OrderControllerTest {

    private static final Idul VALID_USER_IDUL = Idul.from("user123");
    private static final String VALID_ITEM_ID_VALUE = "123e4567-e89b-12d3-a456-426614174000";
    private static final int HTTP_STATUS_OK = 200;
    private static final int HTTP_STATUS_NO_CONTENT = 204;

    private OrderApplicationService orderApplicationService;
    private OrderApiMapper orderApiMapper;
    private OrderController controller;

    private ItemRequest itemRequest;
    private PaymentInfoRequest paymentInfoRequest;

    @BeforeEach
    void setUp() {
        orderApplicationService = Mockito.mock(OrderApplicationService.class);
        orderApiMapper = Mockito.mock(OrderApiMapper.class);

        controller = new OrderController(orderApplicationService, orderApiMapper);

        itemRequest = Mockito.mock(ItemRequest.class);
        paymentInfoRequest = Mockito.mock(PaymentInfoRequest.class);
    }

    @Test
    void givenValidRequest_whenAddItem_thenReturnsOkResponseWithItemList() {
        AddItemDto addItemDto = Mockito.mock(AddItemDto.class);
        OrderDto orderDto = Mockito.mock(OrderDto.class);
        ItemListResponse itemListResponse = Mockito.mock(ItemListResponse.class);
        Mockito.when(orderApiMapper.toAddItemDto(itemRequest))
                .thenReturn(addItemDto);
        Mockito.when(orderApplicationService.addItem(VALID_USER_IDUL, addItemDto))
                .thenReturn(orderDto);
        Mockito.when(orderApiMapper.toItemListResponse(orderDto))
                .thenReturn(itemListResponse);

        Response response = controller.addItem(VALID_USER_IDUL, itemRequest);

        Assertions.assertEquals(HTTP_STATUS_OK, response.getStatus());
        Assertions.assertEquals(itemListResponse, response.getEntity());
        Mockito.verify(orderApiMapper).toAddItemDto(itemRequest);
        Mockito.verify(orderApplicationService).addItem(VALID_USER_IDUL, addItemDto);
        Mockito.verify(orderApiMapper).toItemListResponse(orderDto);
    }

    @Test
    void givenValidRequest_whenConfirm_thenReturnsNoContentResponse() {
        ConfirmOrderDto confirmOrderDto = Mockito.mock(ConfirmOrderDto.class);
        Mockito.when(orderApiMapper.toConfirmOrderDto(paymentInfoRequest))
                .thenReturn(confirmOrderDto);

        Response response = controller.confirm(VALID_USER_IDUL, paymentInfoRequest);

        Assertions.assertEquals(HTTP_STATUS_NO_CONTENT, response.getStatus());
        Mockito.verify(orderApiMapper).toConfirmOrderDto(paymentInfoRequest);
        Mockito.verify(orderApplicationService).confirm(VALID_USER_IDUL, confirmOrderDto);
    }

    @Test
    void givenValidUserId_whenGetOngoingOrder_thenReturnsOkResponseWithItemList() {
        OrderDto orderDto = Mockito.mock(OrderDto.class);
        ItemListResponse itemListResponse = Mockito.mock(ItemListResponse.class);
        Mockito.when(orderApplicationService.getOngoingOrder(VALID_USER_IDUL))
                .thenReturn(orderDto);
        Mockito.when(orderApiMapper.toItemListResponse(orderDto))
                .thenReturn(itemListResponse);

        Response response = controller.getOngoingOrder(VALID_USER_IDUL);

        Assertions.assertEquals(HTTP_STATUS_OK, response.getStatus());
        Assertions.assertEquals(itemListResponse, response.getEntity());
        Mockito.verify(orderApplicationService).getOngoingOrder(VALID_USER_IDUL);
        Mockito.verify(orderApiMapper).toItemListResponse(orderDto);
    }

    @Test
    void givenValidItemId_whenRemoveItem_thenReturnsOkResponseWithItemList() {
        OrderDto orderDto = Mockito.mock(OrderDto.class);
        ItemListResponse itemListResponse = Mockito.mock(ItemListResponse.class);
        Mockito.when(orderApplicationService.removeItem(VALID_USER_IDUL, ItemId.from(VALID_ITEM_ID_VALUE)))
                .thenReturn(orderDto);
        Mockito.when(orderApiMapper.toItemListResponse(orderDto))
                .thenReturn(itemListResponse);

        Response response = controller.removeItem(VALID_USER_IDUL, VALID_ITEM_ID_VALUE);

        Assertions.assertEquals(HTTP_STATUS_OK, response.getStatus());
        Assertions.assertEquals(itemListResponse, response.getEntity());
        Mockito.verify(orderApplicationService).removeItem(
                Mockito.eq(VALID_USER_IDUL),
                Mockito.any(ItemId.class)
        );
        Mockito.verify(orderApiMapper).toItemListResponse(orderDto);
    }

    @Test
    void givenValidUserId_whenRemoveAllItems_thenReturnsOkResponseWithEmptyItemList() {
        OrderDto orderDto = Mockito.mock(OrderDto.class);
        ItemListResponse itemListResponse = Mockito.mock(ItemListResponse.class);
        Mockito.when(orderApplicationService.removeAllItems(VALID_USER_IDUL))
                .thenReturn(orderDto);
        Mockito.when(orderApiMapper.toItemListResponse(orderDto))
                .thenReturn(itemListResponse);

        Response response = controller.removeAllItems(VALID_USER_IDUL);

        Assertions.assertEquals(HTTP_STATUS_OK, response.getStatus());
        Assertions.assertEquals(itemListResponse, response.getEntity());
        Mockito.verify(orderApplicationService).removeAllItems(VALID_USER_IDUL);
        Mockito.verify(orderApiMapper).toItemListResponse(orderDto);
    }
}
