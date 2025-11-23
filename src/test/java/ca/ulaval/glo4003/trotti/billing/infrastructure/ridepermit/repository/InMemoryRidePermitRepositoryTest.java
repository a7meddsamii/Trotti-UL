package ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.repository;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.DailyBillingUsage;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRidePermitRepositoryTest {
    
    private RidePermit permit1;
    private RidePermit permit2;
    private RidePermitId permitId1;
    private RidePermitId permitId2;
    private Idul riderId1;
    private Idul riderId2;
    private Session session1;
    private Session session2;
    
    private InMemoryRidePermitRepository ridePermitRepository;
    
    @BeforeEach
    void setup() {
        ridePermitRepository = new InMemoryRidePermitRepository();
        
        permit1 = Mockito.mock(RidePermit.class);
        permit2 = Mockito.mock(RidePermit.class);
        permitId1 = Mockito.mock(RidePermitId.class);
        permitId2 = Mockito.mock(RidePermitId.class);
        riderId1 = Mockito.mock(Idul.class);
        riderId2 = Mockito.mock(Idul.class);
        session1 = Mockito.mock(Session.class);
        session2 = Mockito.mock(Session.class);
        
        Mockito.when(permit1.getId()).thenReturn(permitId1);
        Mockito.when(permit2.getId()).thenReturn(permitId2);
        Mockito.when(permit1.getRiderId()).thenReturn(riderId1);
        Mockito.when(permit2.getRiderId()).thenReturn(riderId2);
        Mockito.when(permit1.getSession()).thenReturn(session1);
        Mockito.when(permit2.getSession()).thenReturn(session2);
    }
    
    @Test
    void givenRidePermit_whenSave_thenFindByIdReturnsPermit() {
        ridePermitRepository.save(permit1);
        
        Optional<RidePermit> result = ridePermitRepository.findById(permitId1);
        
        assertTrue(result.isPresent());
        assertEquals(permit1, result.get());
    }
    
    @Test
    void givenUnknownPermitId_whenFindById_thenReturnsEmpty() {
        Optional<RidePermit> result = ridePermitRepository.findById(permitId1);
        
        assertTrue(result.isEmpty());
    }
    
    @Test
    void givenRidePermits_whenSaveAll_thenAllCanBeFoundById() {
        ridePermitRepository.saveAll(List.of(permit1, permit2));
        
        assertTrue(ridePermitRepository.findById(permitId1).isPresent());
        assertTrue(ridePermitRepository.findById(permitId2).isPresent());
    }
    
    @Test
    void givenMultiplePermits_whenFindAllByIdul_thenReturnsOnlyPermitsForThatRider() {
        ridePermitRepository.saveAll(List.of(permit1, permit2));
        
        List<RidePermit> result = ridePermitRepository.findAllByIdul(riderId1);
        
        assertEquals(1, result.size());
        assertTrue(result.contains(permit1));
        assertFalse(result.contains(permit2));
    }
    
    @Test
    void givenPermitsWithDailyUsage_whenFindAllByDate_thenReturnsOnlyPermitsHavingThatDate() {
        LocalDate date = LocalDate.of(2025, 1, 10);
        Map<LocalDate, DailyBillingUsage> usages1 = new HashMap<>();
        usages1.put(date, Mockito.mock(DailyBillingUsage.class));
        Mockito.when(permit1.getDailyBillingUsages()).thenReturn(usages1);
        Mockito.when(permit2.getDailyBillingUsages()).thenReturn(Map.of());
        
        ridePermitRepository.saveAll(List.of(permit1, permit2));
        List<RidePermit> result = ridePermitRepository.findAllByDate(date);
        
        assertEquals(1, result.size());
        assertTrue(result.contains(permit1));
        assertFalse(result.contains(permit2));
    }
    
    @Test
    void givenPermitsWithDifferentSessions_whenFindAllBySession_thenReturnsOnlyMatchingSession() {
        ridePermitRepository.saveAll(List.of(permit1, permit2));
        
        List<RidePermit> result = ridePermitRepository.findAllBySession(session1);
        
        assertEquals(1, result.size());
        assertTrue(result.contains(permit1));
        assertFalse(result.contains(permit2));
    }
}