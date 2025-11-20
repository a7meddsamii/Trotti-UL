package ca.ulaval.glo4003.trotti.order.domain.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import java.util.List;

public interface PassRepository {

    void saveAll(List<Pass> passes);

    List<Pass> findAllByIdul(Idul idul);
}
