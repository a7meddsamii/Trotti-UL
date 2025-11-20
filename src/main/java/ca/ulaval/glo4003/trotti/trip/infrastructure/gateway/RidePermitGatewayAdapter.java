package ca.ulaval.glo4003.trotti.trip.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitGateway;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import java.util.List;

public class RidePermitGatewayAdapter implements RidePermitGateway {
    private final PassRepository passRepository;

    public RidePermitGatewayAdapter(PassRepository passRepository) {
        this.passRepository = passRepository;
    }

    @Override
    public List<RidePermit> findAllByIdul(Idul idul) {
        return passRepository.findAllByIdul(idul).stream().map(this::translate).toList();
    }

    @Override
    public boolean isOwnerOfRidePermit(Idul idul, RidePermitId ridePermitId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private RidePermit translate(Pass pass) {
        return new RidePermit(RidePermitId.from(pass.getId().toString()), pass.getBuyerIdul(),
                pass.getSession());
    }
}
