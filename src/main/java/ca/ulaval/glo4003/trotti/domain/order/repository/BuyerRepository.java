package ca.ulaval.glo4003.trotti.domain.order.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import java.util.Optional;

public interface BuyerRepository {
    void update(Buyer buyer);

    Optional<Buyer> findByEmail(Email email);

    Optional<Buyer> findByIdul(Idul idul);
}
