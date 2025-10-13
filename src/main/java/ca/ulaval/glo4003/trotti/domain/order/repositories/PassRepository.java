package ca.ulaval.glo4003.trotti.domain.order.repositories;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;
import java.util.List;

public interface PassRepository {

    void saveAll(List<Pass> passes);

    List<Pass> findAllByIdul(Idul idul);
}
