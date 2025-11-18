package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.provider.EmployeeRegistryProvider;
import ca.ulaval.glo4003.trotti.order.domain.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.order.domain.values.Semester;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.fixtures.TravelerFixture;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EmployeeRiderPermitServiceTest {

    private static final Session A_SESSION =
            new Session(Semester.FALL, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 6, 30));

    private EmployeeRegistryProvider employeeRegistryProvider;
    private SchoolSessionProvider schoolSessionProvider;
    private Traveler traveler;
    private EmployeeRidePermitService service;

    @BeforeEach
    void setup() {
        employeeRegistryProvider = Mockito.mock(EmployeeRegistryProvider.class);
        schoolSessionProvider = Mockito.mock(SchoolSessionProvider.class);
        traveler = Mockito.spy(new TravelerFixture().build());
        service = new EmployeeRidePermitService(employeeRegistryProvider, schoolSessionProvider);
    }

    @Test
    void givenEmployeeIdulAndCurrentDateInSession_whenGiveFreePermitToEmployee_thenAddPermitToEmployee() {
        Mockito.when(employeeRegistryProvider.exists(traveler.getIdul())).thenReturn(true);
        Mockito.when(schoolSessionProvider.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.of(A_SESSION));

        service.giveFreePermitToEmployee(traveler);

        Mockito.verify(traveler).updateWallet(Mockito.anyList());
    }

    @Test
    void givenEmployeeThatAlreadyHasActivePermit_whenGiveFreePermitToEmployee_thenDoNotAddPermitToEmployee() {
        Mockito.when(employeeRegistryProvider.exists(traveler.getIdul())).thenReturn(true);
        Mockito.when(traveler.hasEmptyWallet()).thenReturn(false);
        Mockito.when(schoolSessionProvider.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.of(A_SESSION));

        service.giveFreePermitToEmployee(traveler);

        Mockito.verify(traveler, Mockito.never()).updateWallet(Mockito.anyList());
    }

    @Test
    void givenEmployeeIdulAndCurrentDateOutsideOfAnySession_whenGiveFreePermitToEmployee_thenDoNotAddPermitToEmployee() {
        Mockito.when(employeeRegistryProvider.exists(traveler.getIdul())).thenReturn(true);
        Mockito.when(schoolSessionProvider.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.empty());

        service.giveFreePermitToEmployee(traveler);

        Mockito.verify(traveler, Mockito.never()).updateWallet(Mockito.anyList());
    }
}
