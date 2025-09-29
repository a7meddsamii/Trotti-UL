package ca.ulaval.glo4003.trotti.infrastructure.order.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;

public record BuyerRecord(Idul idul, String name, Email emails) {
}
