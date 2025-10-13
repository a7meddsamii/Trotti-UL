package ca.ulaval.glo4003.trotti.application.order.dto;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;

public record PassDto(
        MaximumDailyTravelTime maximumDailyTravelTime,
        Session session,
        BillingFrequency billingFrequency,
        Id id
) {
}
