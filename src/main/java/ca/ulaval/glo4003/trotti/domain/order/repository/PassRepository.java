package ca.ulaval.glo4003.trotti.domain.order.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import java.util.List;

public interface PassRepository {
    List<Pass> findAllByIdul(Idul idul);
}
