package ca.ulaval.glo4003.trotti.infrastructure.order.repositories;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;

public record PassRecord(
        MaximumDailyTravelTime maximumTravelingTime,
        Session session,
        BillingFrequency billingFrequency,
        Id id,
        Idul idul)
{}
