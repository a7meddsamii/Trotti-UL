package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RidePermitActivationFilterTest {

    private static final LocalDate CURRENT_DATE = LocalDate.of(2025, 4, 16);
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    @Mock
    private SchoolSessionProvider schoolSessionProvider;

    private Clock clock;
    private RidePermitActivationFilter filter;

    @BeforeEach
    void setup() {
        clock = Clock.fixed(CURRENT_DATE.atStartOfDay(ZONE_ID).toInstant(), ZONE_ID);
        filter = new RidePermitActivationFilter(clock, schoolSessionProvider);
    }

    @Test
    void whenGetActivatedRidePermits_thenFilterPermitsBasedOnCurrentSession() {
        Session session = mock(Session.class);
        when(schoolSessionProvider.getSession(CURRENT_DATE)).thenReturn(Optional.of(session));

        RidePermit activablePermit = mock(RidePermit.class);
        RidePermit nonActivablePermit = mock(RidePermit.class);
        when(activablePermit.activate(session)).thenReturn(true);
        when(nonActivablePermit.activate(session)).thenReturn(false);

        List<RidePermit> result =
                filter.getActivatedRidePermits(List.of(activablePermit, nonActivablePermit));

        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.contains(activablePermit));
    }

    @Test
	void givenNoCurrentSession_whenGettingActivatedRidePermits_thenThrowException() {
		when(schoolSessionProvider.getSession(CURRENT_DATE)).thenReturn(Optional.empty());
		RidePermit permit = mock(RidePermit.class);
		List<RidePermit> permits = List.of(permit);
		
		Executable executable = () -> filter.getActivatedRidePermits((permits));
		
		Assertions.assertThrows(NotFoundException.class, executable);
	}

    @Test
    void whenGetDeactivatedRidePermits_thenFilterPermitsBasedOnCurrentSession() {
        Session session =
                new Session(Semester.WINTER, LocalDate.of(2024, 9, 7), LocalDate.of(2024, 12, 29));
        when(schoolSessionProvider.getPreviousSession(CURRENT_DATE))
                .thenReturn(Optional.of(session));

        RidePermit deactivablePermit = mock(RidePermit.class);
        RidePermit nonDeactivablePermit = mock(RidePermit.class);
        when(deactivablePermit.deactivate(session)).thenReturn(true);
        when(nonDeactivablePermit.deactivate(session)).thenReturn(false);

        List<RidePermit> result =
                filter.getDeactivatedRidePermits(List.of(deactivablePermit, nonDeactivablePermit));

        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.contains(deactivablePermit));
    }

    @Test
    void givenNoCurrentSession_whenGettingDeactivatedRidePermits_thenThrowException() {
        RidePermit permit = mock(RidePermit.class);
        List<RidePermit> permits = List.of(permit);

        Executable executable = () -> filter.getDeactivatedRidePermits((permits));

        Assertions.assertThrows(NotFoundException.class, executable);
    }

    @Test
    void whenGetCurrentSessionDate_thenReturnCurrentDate() {
        LocalDate result = filter.getCurrentSessionDate();

        Assertions.assertEquals(CURRENT_DATE, result);
    }

    @Test
    void whenGetPreviousSession_thenReturnPreviousSessionFromSchoolSessionProvider() {
        Session previousSession = mock(Session.class);
        when(schoolSessionProvider.getPreviousSession(CURRENT_DATE))
                .thenReturn(Optional.of(previousSession));

        Session result = filter.getPreviousSession();

        Assertions.assertEquals(previousSession, result);
    }

    @Test
	void givenNoPreviousSession_whenGettingPreviousSession_thenThrowException() {
		when(schoolSessionProvider.getPreviousSession(CURRENT_DATE)).thenReturn(Optional.empty());
		
		Executable executable = () -> filter.getPreviousSession();
		
		Assertions.assertThrows(NotFoundException.class, executable);
	}
}
