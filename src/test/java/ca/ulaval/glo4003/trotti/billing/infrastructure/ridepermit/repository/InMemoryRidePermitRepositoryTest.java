package ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.repository;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InMemoryRidePermitRepositoryTest {

    private static final String RIDER_1_IDUL = "rider001";
    private static final String RIDER_2_IDUL = "rider002";
    private static final Duration DAILY_LIMIT = Duration.ofMinutes(60);

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

        riderId1 = Idul.from(RIDER_1_IDUL);
        riderId2 = Idul.from(RIDER_2_IDUL);

        session1 = new Session(
                Semester.WINTER,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 4, 30)
        );

        session2 = new Session(
                Semester.FALL,
                LocalDate.of(2025, 9, 1),
                LocalDate.of(2025, 12, 20)
        );

        permitId1 = RidePermitId.randomId();
        permit1 = new RidePermit(permitId1, riderId1, session1, DAILY_LIMIT);

        permitId2 = RidePermitId.randomId();
        permit2 = new RidePermit(permitId2, riderId2, session2, DAILY_LIMIT);
    }

    @Test
    void givenRidePermit_whenSave_thenFindByIdReturnsPermit() {
        ridePermitRepository.save(permit1);

        Optional<RidePermit> result = ridePermitRepository.findById(permitId1);

        Assertions.assertEquals(permit1.getId(), result.get().getId());
        Assertions.assertEquals(permit1.getRiderId(), result.get().getRiderId());
    }

    @Test
    void givenUnknownPermitId_whenFindById_thenReturnsEmpty() {
        RidePermitId unknownId = RidePermitId.randomId();

        Optional<RidePermit> result = ridePermitRepository.findById(unknownId);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void givenRidePermits_whenSaveAll_thenAllCanBeFoundById() {
        ridePermitRepository.saveAll(List.of(permit1, permit2));

        Assertions.assertTrue(ridePermitRepository.findById(permitId1).isPresent());
        Assertions.assertTrue(ridePermitRepository.findById(permitId2).isPresent());
    }

    @Test
    void givenMultiplePermits_whenFindAllByIdul_thenReturnsOnlyPermitsForThatRider() {
        ridePermitRepository.saveAll(List.of(permit1, permit2));

        List<RidePermit> result = ridePermitRepository.findAllByIdul(riderId1);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(permit1.getId(), result.get(0).getId());

    }

    @Test
    void givenPermitsWithDifferentSessions_whenFindAllBySession_thenReturnsOnlyMatchingSession() {
        ridePermitRepository.saveAll(List.of(permit1, permit2));

        List<RidePermit> result = ridePermitRepository.findAllBySession(session1);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(permit1.getId(), result.get(0).getId());

    }
}
