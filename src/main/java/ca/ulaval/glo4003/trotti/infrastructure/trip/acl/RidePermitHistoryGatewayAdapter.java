package ca.ulaval.glo4003.trotti.infrastructure.trip.acl;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.domain.order.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.acl.RidePermitHistoryGateway;
import java.util.List;

public class RidePermitHistoryGatewayAdapter implements RidePermitHistoryGateway {
    private final PassRepository passRepository;

    public RidePermitHistoryGatewayAdapter(PassRepository passRepository) {
        this.passRepository = passRepository;
    }

    @Override
    public List<RidePermit> getFullHistory(Idul idul) {
        return passRepository.findAllByIdul(idul).stream().map(this::translate).toList();
    }

    private RidePermit translate(Pass pass) {
        return new RidePermit(pass.getId(), pass.getBuyerIdul(), pass.getSession());
    }
}
