package ca.ulaval.glo4003.trotti.order.application.dto;

import ca.ulaval.glo4003.trotti.order.domain.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.order.domain.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.order.domain.values.PassId;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;

public record PassDto(
        MaximumDailyTravelTime maximumDailyTravelTime,
        Session session,
        BillingFrequency billingFrequency,
        PassId id
) {
}
