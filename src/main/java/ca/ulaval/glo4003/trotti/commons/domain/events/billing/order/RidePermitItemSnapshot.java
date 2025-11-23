package ca.ulaval.glo4003.trotti.commons.domain.events.billing.order;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;

import java.time.Duration;

public record RidePermitItemSnapshot(Session session,
        Duration maxDailyTravelTime,
        BillingFrequency billingFrequency) {}
