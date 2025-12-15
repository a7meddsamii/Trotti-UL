package ca.ulaval.glo4003.trotti.billing.api.order.mapper;

import ca.ulaval.glo4003.trotti.billing.api.order.dto.request.ItemRequest;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.request.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.response.ItemListResponse;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.*;
import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethodType;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class OrderApiMapper {

    private final SchoolSessionProvider schoolSessionProvider;
    private final Clock clock;

    public OrderApiMapper(SchoolSessionProvider schoolSessionProvider, Clock clock) {
        this.schoolSessionProvider = schoolSessionProvider;
        this.clock = clock;
    }

    public ItemListResponse toItemListResponse(OrderDto orderDto) {
        List<ItemListResponse.ItemResponse> itemResponses =
                orderDto.ridePermitItems().stream().map(this::toItemResponse).toList();

        return new ItemListResponse(orderDto.orderId().toString(), orderDto.totalCost().toString(),
                orderDto.status().toString(), itemResponses);
    }

    private ItemListResponse.ItemResponse toItemResponse(RidePermitItemDto itemDto) {
        return new ItemListResponse.ItemResponse(itemDto.itemId().toString(),
                itemDto.maximumDailyTravelTime().toString(), itemDto.session().toString(),
                itemDto.billingFrequency().toString(), itemDto.cost().toString());
    }

    public AddItemDto toAddItemDto(ItemRequest itemRequest) {
        MaximumDailyTravelTime maximumDailyTravelTime = MaximumDailyTravelTime
                .from(Duration.ofMinutes(itemRequest.maximumDailyTravelTime()));
        Session session = schoolSessionProvider.getSession(itemRequest.session()).orElseThrow(
                () -> new NotFoundException("Session " + itemRequest.session() + " not found"));
        BillingFrequency billingFrequency =
                BillingFrequency.fromString(itemRequest.billingFrequency());

        return new AddItemDto(maximumDailyTravelTime, session, billingFrequency);
    }

    public ConfirmOrderDto toConfirmOrderDto(PaymentInfoRequest paymentInfoRequest) {

        return new ConfirmOrderDto(PaymentMethodType.CREDIT_CARD, paymentInfoRequest.cardNumber(),
                paymentInfoRequest.cardHolderName(), paymentInfoRequest.expirationDate());
    }

    public FreeRidePermitItemGrantDto toFreeRidePermitItemGrantDto(Idul buyerId) {
        LocalDate date = LocalDate.now(clock);
        Session session = schoolSessionProvider.getSession(date)
                .orElseThrow(() -> new NotFoundException("No Session found for date " + date));
        return new FreeRidePermitItemGrantDto(buyerId, session);
    }

    public List<FreeRidePermitItemGrantDto> toFreeRidePermitItemGrantDtos(List<Idul> buyerIds) {
        return buyerIds.stream().map(this::toFreeRidePermitItemGrantDto).toList();
    }
}
