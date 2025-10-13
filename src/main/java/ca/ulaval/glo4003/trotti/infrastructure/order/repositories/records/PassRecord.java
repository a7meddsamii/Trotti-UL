package ca.ulaval.glo4003.trotti.infrastructure.order.repositories.records;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;

public record PassRecord(Id passId, Idul owner, MaximumDailyTravelTime maximumDailyTravelTime, Session session, BillingFrequency billingFrequency) {
}
