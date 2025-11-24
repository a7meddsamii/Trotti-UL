package ca.ulaval.glo4003.trotti.billing.application.ridepermit;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitSnapshot;
import java.util.ArrayList;
import java.util.List;

public class RidePermitAssembler {
    public RidePermitDto assemble(RidePermit ridePermit) {
        return new RidePermitDto(ridePermit.getId(), ridePermit.getRiderId(),
                ridePermit.getSession(), ridePermit.getMaximumTravelingTimePerDay(),
                ridePermit.getPermitState(), ridePermit.getBalance());
    }

    public List<RidePermitDto> assemble(List<RidePermit> ridePermits) {
        return ridePermits.stream().map(this::assemble).toList();
    }

    public List<RidePermitSnapshot> toRidePermitSnapshots(List<RidePermit> ridePermits) {
        List<RidePermitSnapshot> ridePermitSnapshots = new ArrayList<>();

        for (RidePermit ridePermit : ridePermits) {
            RidePermitSnapshot snapshot = new RidePermitSnapshot(ridePermit.getRiderId(),
                    ridePermit.getId().toString(), ridePermit.getSession().getSemester().toString(),
                    ridePermit.getSession().getStartDate(), ridePermit.getSession().getEndDate());
            ridePermitSnapshots.add(snapshot);
        }

        return ridePermitSnapshots;
    }
}
