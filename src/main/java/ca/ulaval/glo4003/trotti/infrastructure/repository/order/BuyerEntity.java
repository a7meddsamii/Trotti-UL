package ca.ulaval.glo4003.trotti.infrastructure.repository.order;

import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Idul;

public record BuyerEntity(Idul idul, String name, Email emails) {
}
