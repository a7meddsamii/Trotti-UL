package ca.ulaval.glo4003.trotti.order.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.order.domain.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.order.domain.values.PassId;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;

public record PassRecord(
    PassId passId,
    Idul owner,
    MaximumDailyTravelTime maximumDailyTravelTime,
    Session session,
    BillingFrequency billingFrequency) {
}
