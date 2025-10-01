package ca.ulaval.glo4003.trotti.domain.order.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;

public interface BuyerRepository {
    void save(Buyer buyer);

    Buyer findByEmail(Email email);

    Buyer findByIdul(Idul idul);
}
