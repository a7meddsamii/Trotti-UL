package ca.ulaval.glo4003.trotti.infrastructure.order.repository.records;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;

public record PassRecord(Id passId, Idul owner, MaximumDailyTravelTime maximumDailyTravelTime, Session session, BillingFrequency billingFrequency) {
}
