package ca.ulaval.glo4003.trotti.billing.api.order.controller;

import ca.ulaval.glo4003.trotti.billing.api.order.dto.request.ItemRequest;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.response.ItemListResponse;
import ca.ulaval.glo4003.trotti.billing.api.order.mapper.OrderApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.AddItemDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import jakarta.ws.rs.core.Response;

public class OrderController implements OrderResource {
	private final OrderApplicationService orderApplicationService;
	private final OrderApiMapper orderApiMapper;
	
	public OrderController(
			OrderApplicationService orderApplicationService,
			OrderApiMapper orderApiMapper
	) {
		this.orderApplicationService = orderApplicationService;
		this.orderApiMapper = orderApiMapper;
	}
	
	@Override
	public Response addItem(Idul userId, ItemRequest itemRequest) {
		AddItemDto addItemDto = null;
		
		OrderDto orderDto = orderApplicationService.addItem(userId, addItemDto);
		
		ItemListResponse itemListResponse = orderApiMapper.toItemListResponse(orderDto);
		
		return Response.ok(itemListResponse).build();
	}
	
	@Override
	public Response getOngoingOrder(Idul userId) {
		return null;
	}
	
	@Override
	public Response removeItem(Idul userId, String itemId) {
		return null;
	}
	
	@Override
	public Response removeAllItems(Idul userId) {
		return null;
	}
}
