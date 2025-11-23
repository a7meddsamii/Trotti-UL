package ca.ulaval.glo4003.trotti.billing.api.order.mapper;

import ca.ulaval.glo4003.trotti.billing.api.order.dto.request.ItemRequest;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.request.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.response.ItemListResponse;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.AddItemDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.ConfirmOrderDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.RidePermitItemDto;
import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethodType;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;

import java.time.Duration;
import java.time.YearMonth;
import java.util.List;

public class OrderApiMapper {
	
	private final SchoolSessionProvider schoolSessionProvider;
	
	public OrderApiMapper(SchoolSessionProvider schoolSessionProvider) {
		this.schoolSessionProvider = schoolSessionProvider;
	}
	
	public ItemListResponse toItemListResponse(OrderDto orderDto) {
		List<ItemListResponse.ItemResponse> itemResponses = orderDto.ridePermitItems().stream()
				.map(this::toItemResponse)
				.toList();
		
		return new ItemListResponse(
				orderDto.orderId().toString(),
				orderDto.totalCost().toString(),
				orderDto.status().toString(),
				itemResponses
				);
	}
	
	private ItemListResponse.ItemResponse toItemResponse(RidePermitItemDto itemDto) {
		return new ItemListResponse.ItemResponse(
				itemDto.itemId().toString(),
				itemDto.maximumDailyTravelTime().toString(),
				itemDto.session().toString(),
				itemDto.billingFrequency().toString(),
				itemDto.cost().toString()
		);
	}
	
	public AddItemDto toAddItemDto(ItemRequest itemRequest) {
		MaximumDailyTravelTime maximumDailyTravelTime = MaximumDailyTravelTime.from(Duration.ofMinutes(itemRequest.maximumDailyTravelTime()));
		Session session = schoolSessionProvider.getSession(itemRequest.session())
				.orElseThrow(() -> new NotFoundException("Session " + itemRequest.session() + " not found"));
		BillingFrequency billingFrequency = BillingFrequency.fromString(itemRequest.billingFrequency());
		
		return new AddItemDto(
				maximumDailyTravelTime,
				session,
				billingFrequency
		);
	}

    public ConfirmOrderDto toConfirmOrderDto(PaymentInfoRequest paymentInfoRequest) {
        //TODO
        if (paymentInfoRequest == null) {
            throw new InvalidParameterException("Payment information is required to confirm the order");
        }

        return new ConfirmOrderDto(
                PaymentMethodType.CREDIT_CARD,
                paymentInfoRequest.cardNumber(),
                paymentInfoRequest.cardHolderName(),
                YearMonth.parse(paymentInfoRequest.expirationDate()) //TODO validation
        );
    }
}
