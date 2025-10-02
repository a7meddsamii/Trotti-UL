package ca.ulaval.glo4003.trotti.infrastructure.trip.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.domain.order.repository.PassRepository;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitHistoryGateway;
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
        return new RidePermit(pass.getId(), pass.getIdul(), pass.getSession());
    }
}
