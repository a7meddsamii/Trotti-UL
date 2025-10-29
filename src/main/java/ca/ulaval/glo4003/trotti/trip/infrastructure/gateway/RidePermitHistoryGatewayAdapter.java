package ca.ulaval.glo4003.trotti.trip.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
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
        return new RidePermit(RidePermitId.from(pass.getId().toString()), pass.getBuyerIdul(),
                pass.getSession());
    }
}
