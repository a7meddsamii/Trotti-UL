package ca.ulaval.glo4003.trotti.order.domain.repositories;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.buyer.Buyer;

public interface BuyerRepository {
    void update(Buyer buyer);

    Buyer findByEmail(Email email);

    Buyer findByIdul(Idul idul);
}
