package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.commons.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.domain.commons.SessionRegistry;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Traveler;
import ca.ulaval.glo4003.trotti.fixtures.TravelerFixture;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EmployeeRiderPermitServiceTest {

    private static final Session A_SESSION =
            new Session(Semester.FALL, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 6, 30));

    private EmployeeRegistry employeeRegistry;
    private SessionRegistry sessionRegistry;
    private Traveler traveler;
    private EmployeeRidePermitService service;

    @BeforeEach
    void setup() {
        employeeRegistry = Mockito.mock(EmployeeRegistry.class);
        sessionRegistry = Mockito.mock(SessionRegistry.class);
        traveler = Mockito.spy(new TravelerFixture().build());
        service = new EmployeeRidePermitService(employeeRegistry, sessionRegistry);
    }

    @Test
    void givenEmployeeIdulAndCurrentDateInSession_whenHandleEmployeeRidePermit_thenAddPermitToEmployee() {
        Mockito.when(employeeRegistry.isEmployee(traveler.getIdul())).thenReturn(true);
        Mockito.when(sessionRegistry.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.of(A_SESSION));

        service.handleEmployeeRidePermit(traveler);

        Mockito.verify(traveler).updateWallet(Mockito.anyList());
    }

    @Test
    void givenEmployeeThatAlreadyHasActivePermit_whenHandleEmployeeRidePermit_thenDoNotAddPermitToEmployee() {
        Mockito.when(employeeRegistry.isEmployee(traveler.getIdul())).thenReturn(true);
        Mockito.when(traveler.isWalletEmpty()).thenReturn(true);
        Mockito.when(sessionRegistry.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.of(A_SESSION));

        service.handleEmployeeRidePermit(traveler);

        Mockito.verify(traveler, Mockito.never()).updateWallet(Mockito.anyList());
    }

    @Test
    void givenEmployeeIdulAndCurrentDateOutsideOfAnySession_whenHandleEmployeeRidePermit_thenDoNotAddPermitToEmployee() {
        Mockito.when(employeeRegistry.isEmployee(traveler.getIdul())).thenReturn(true);
        Mockito.when(sessionRegistry.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.empty());

        service.handleEmployeeRidePermit(traveler);

        Mockito.verify(traveler, Mockito.never()).updateWallet(Mockito.anyList());
    }
}
