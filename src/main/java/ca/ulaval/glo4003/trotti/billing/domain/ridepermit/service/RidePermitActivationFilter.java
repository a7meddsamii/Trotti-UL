package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.service;

import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public class RidePermitActivationFilter {
    private final Clock clock;
    private final SchoolSessionProvider schoolSessionProvider;

    public RidePermitActivationFilter(Clock clock, SchoolSessionProvider schoolSessionProvider) {
        this.clock = clock;
        this.schoolSessionProvider = schoolSessionProvider;
    }

    public List<RidePermit> getActivatedRidePermits(List<RidePermit> ridePermits) {
        Session currentSchoolSession = getCurrentSession();
        return ridePermits.stream().filter(ridePermit -> ridePermit.activate(currentSchoolSession))
                .toList();
    }

    public List<RidePermit> getDeactivatedRidePermits(List<RidePermit> ridePermits) {
        Session currentSchoolSession = getCurrentSession();
        return ridePermits.stream()
                .filter(ridePermit -> ridePermit.deactivate(currentSchoolSession)).toList();
    }

    public LocalDate getCurrentSessionDate() {
        return clock.instant().atZone(clock.getZone()).toLocalDate();
    }

    public Session getPreviousSession() {
        return schoolSessionProvider.getPreviousSession(getCurrentSessionDate()).orElseThrow(
                () -> new NotFoundException("Could not find a previous school session"));
    }

    private Session getCurrentSession() {
        LocalDate now = getCurrentSessionDate();
        return schoolSessionProvider.getSession(now).orElseThrow(
                () -> new NotFoundException("Could not find an active school session"));
    }
}
