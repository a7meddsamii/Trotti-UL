package ca.ulaval.glo4003.trotti.infrastructure.trip.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.domain.order.repository.PassRepository;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitHistoryGateway;
import java.util.ArrayList;
import java.util.List;

public class RidePermitHistoryGatewayImpl implements RidePermitHistoryGateway {
    private final PassRepository passRepository;

    public RidePermitHistoryGatewayImpl(PassRepository passRepository) {
        this.passRepository = passRepository;
    }

    @Override
    public List<RidePermit> getFullHistory(Idul idul) {
        List<Pass> passes = this.passRepository.getAllPasses(idul);
        List<RidePermit> ridePermits = new ArrayList<>();
        passes.forEach(pass -> ridePermits.add(this.convert(pass)));
        return ridePermits;
    }

    private RidePermit convert(Pass pass) {
        return new RidePermit(pass.getId(), pass.getIdul(), pass.getSession());
    }
}
