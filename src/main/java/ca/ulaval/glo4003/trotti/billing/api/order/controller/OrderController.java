package ca.ulaval.glo4003.trotti.billing.api.order.controller;

import ca.ulaval.glo4003.trotti.billing.api.order.dto.request.ItemRequest;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.request.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.response.ItemListResponse;
import ca.ulaval.glo4003.trotti.billing.api.order.mapper.OrderApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.AddItemDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.ConfirmOrderDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.ItemId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import jakarta.ws.rs.core.Response;

public class OrderController implements OrderResource {
    private final OrderApplicationService orderApplicationService;
    private final OrderApiMapper orderApiMapper;

    public OrderController(
            OrderApplicationService orderApplicationService,
            OrderApiMapper orderApiMapper) {
        this.orderApplicationService = orderApplicationService;
        this.orderApiMapper = orderApiMapper;
    }

    @Override
    public Response addItem(Idul userId, ItemRequest itemRequest) {
        AddItemDto addItemDto = orderApiMapper.toAddItemDto(itemRequest);

        OrderDto orderDto = orderApplicationService.addItem(userId, addItemDto);

        ItemListResponse itemListResponse = orderApiMapper.toItemListResponse(orderDto);

        return Response.ok(itemListResponse).build();
    }

    @Override
    public Response confirm(Idul userId, PaymentInfoRequest paymentInfoRequest) {
        ConfirmOrderDto confirmOrderDto = orderApiMapper.toConfirmOrderDto(paymentInfoRequest);

        orderApplicationService.confirm(userId, confirmOrderDto);

        return Response.noContent().build();
    }

    @Override
    public Response getOngoingOrder(Idul userId) {
        OrderDto orderDto = orderApplicationService.getOngoingOrder(userId);

        ItemListResponse itemListResponse = orderApiMapper.toItemListResponse(orderDto);

        return Response.ok(itemListResponse).build();
    }

    @Override
    public Response removeItem(Idul userId, String itemId) {
        OrderDto orderDto = orderApplicationService.removeItem(userId, ItemId.from(itemId));

        ItemListResponse itemListResponse = orderApiMapper.toItemListResponse(orderDto);

        return Response.ok(itemListResponse).build();
    }

    @Override
    public Response removeAllItems(Idul userId) {
        OrderDto orderDto = orderApplicationService.removeAllItems(userId);

        ItemListResponse itemListResponse = orderApiMapper.toItemListResponse(orderDto);

        return Response.ok(itemListResponse).build();
    }
}
