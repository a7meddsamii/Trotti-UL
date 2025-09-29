package ca.ulaval.glo4003.trotti.infrastructure.repository.order;

import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Email;

public record BuyerEntity(Idul idul, String name, Email emails) {
}
