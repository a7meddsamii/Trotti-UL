package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.commons.domain.gateways.EmployeeRegistryGateway;
import ca.ulaval.glo4003.trotti.commons.domain.gateways.SchoolSessionGateway;
import ca.ulaval.glo4003.trotti.order.domain.values.Semester;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.fixtures.TravelerFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

class EmployeeRiderPermitServiceTest {

    private static final Session A_SESSION =
            new Session(Semester.FALL, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 6, 30));

    private EmployeeRegistryGateway employeeRegistryGateway;
    private SchoolSessionGateway schoolSessionGateway;
    private Traveler traveler;
    private EmployeeRidePermitService service;

    @BeforeEach
    void setup() {
		employeeRegistryGateway = Mockito.mock(EmployeeRegistryGateway.class);
        schoolSessionGateway = Mockito.mock(SchoolSessionGateway.class);
        traveler = Mockito.spy(new TravelerFixture().build());
        service = new EmployeeRidePermitService(employeeRegistryGateway, schoolSessionGateway);
    }

    @Test
    void givenEmployeeIdulAndCurrentDateInSession_whenGiveFreePermitToEmployee_thenAddPermitToEmployee() {
        Mockito.when(employeeRegistryGateway.exist(traveler.getIdul())).thenReturn(true);
        Mockito.when(schoolSessionGateway.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.of(A_SESSION));

        service.giveFreePermitToEmployee(traveler);

        Mockito.verify(traveler).updateWallet(Mockito.anyList());
    }

    @Test
    void givenEmployeeThatAlreadyHasActivePermit_whenGiveFreePermitToEmployee_thenDoNotAddPermitToEmployee() {
        Mockito.when(employeeRegistryGateway.exist(traveler.getIdul())).thenReturn(true);
        Mockito.when(traveler.hasEmptyWallet()).thenReturn(false);
        Mockito.when(schoolSessionGateway.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.of(A_SESSION));

        service.giveFreePermitToEmployee(traveler);

        Mockito.verify(traveler, Mockito.never()).updateWallet(Mockito.anyList());
    }

    @Test
    void givenEmployeeIdulAndCurrentDateOutsideOfAnySession_whenGiveFreePermitToEmployee_thenDoNotAddPermitToEmployee() {
        Mockito.when(employeeRegistryGateway.exist(traveler.getIdul())).thenReturn(true);
        Mockito.when(schoolSessionGateway.getSession(Mockito.any(LocalDate.class)))
                .thenReturn(java.util.Optional.empty());

        service.giveFreePermitToEmployee(traveler);

        Mockito.verify(traveler, Mockito.never()).updateWallet(Mockito.anyList());
    }
}
