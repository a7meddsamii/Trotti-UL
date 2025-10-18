package ca.ulaval.glo4003.trotti.infrastructure.order.repositories.records;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.domain.order.values.PassId;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;

public record PassRecord(
    PassId passId,
    Idul owner,
    MaximumDailyTravelTime maximumDailyTravelTime,
    Session session,
    BillingFrequency billingFrequency) {
}
