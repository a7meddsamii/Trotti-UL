package ca.ulaval.glo4003.trotti.billing.application.order.dto;

import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentMethodType;

public record ConfirmOrderDto(
		PaymentMethodType paymentMethodType,
		String paymentMethodInfo
) {
}
