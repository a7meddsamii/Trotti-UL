package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.commons.domain.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.commons.domain.SessionEnum;
import ca.ulaval.glo4003.trotti.order.domain.values.Semester;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.fixtures.TravelerFixture;
import java.time.LocalDate;

import ca.ulaval.glo4003.trotti.trip.domain.services.EmployeeRidePermitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EmployeeRiderPermitServiceTest {

    private static final Session A_SESSION =
            new Session(Semester.FALL, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 6, 30));

    private EmployeeRegistry employeeRegistry;
    private SessionEnum sessionEnum;
    private Traveler traveler;
    private EmployeeRidePermitService service;

    @BeforeEach
    void setup() {
        employeeRegistry = Mockito.mock(EmployeeRegistry.class);
        sessionEnum = Mockito.mock(SessionEnum.class);
        traveler = Mockito.spy(new TravelerFixture().build());
        service = new EmployeeRidePermitService(employeeRegistry, sessionEnum);
    }

    @Test
    void givenEmployeeIdulAndCurrentDateInSession_whenGiveFreePermitToEmployee_thenAddPermitToEmployee() {
        Mockito.when(employeeRegistry.isEmployee(traveler.getIdul())).thenReturn(true);
        Mockito.when(sessionEnum.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.of(A_SESSION));

        service.giveFreePermitToEmployee(traveler);

        Mockito.verify(traveler).updateWallet(Mockito.anyList());
    }

    @Test
    void givenEmployeeThatAlreadyHasActivePermit_whenGiveFreePermitToEmployee_thenDoNotAddPermitToEmployee() {
        Mockito.when(employeeRegistry.isEmployee(traveler.getIdul())).thenReturn(true);
        Mockito.when(traveler.hasEmptyWallet()).thenReturn(false);
        Mockito.when(sessionEnum.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.of(A_SESSION));

        service.giveFreePermitToEmployee(traveler);

        Mockito.verify(traveler, Mockito.never()).updateWallet(Mockito.anyList());
    }

    @Test
    void givenEmployeeIdulAndCurrentDateOutsideOfAnySession_whenGiveFreePermitToEmployee_thenDoNotAddPermitToEmployee() {
        Mockito.when(employeeRegistry.isEmployee(traveler.getIdul())).thenReturn(true);
        Mockito.when(sessionEnum.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.empty());

        service.giveFreePermitToEmployee(traveler);

        Mockito.verify(traveler, Mockito.never()).updateWallet(Mockito.anyList());
    }
}
