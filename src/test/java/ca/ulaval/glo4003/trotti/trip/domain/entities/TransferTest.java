package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.InsufficientScootersInTransitException;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.TechnicianNotInChargeException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.util.List;
import org.junit.jupiter.api.function.Executable;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransferTest {

    private static final Location SOURCE_STATION = Location.of("Building A");
    private static final Idul TECHNICIAN_ID = Idul.from("tech123");

    private Transfer transfer;
    private ScooterId scooterId1;
    private ScooterId scooterId2;

    @BeforeEach
    void setup() {
        scooterId1 = ScooterId.randomId();
        scooterId2 = ScooterId.randomId();
        Set<ScooterId> scooters = Set.of(scooterId1, scooterId2);
        transfer = Transfer.start(TECHNICIAN_ID, SOURCE_STATION, scooters);
    }

    @Test
    void givenWrongTechnician_whenUnload_thenThrowsException() {
        Idul wrongTechnicianId = Idul.from("wrongTech");

        Executable unloadWithWrongTechId = () -> transfer.unload(wrongTechnicianId, 1);

        Assertions.assertThrows(TechnicianNotInChargeException.class,
                unloadWithWrongTechId);
    }

    @Test
    void givenTransferWithTwoScooters_whenTryToUnloadThree_thenThrowsException() {
        int moreScootersThanTransferred = 3;

        Executable unloadWithMoreScootersThanTransferred = () -> transfer.unload(TECHNICIAN_ID, moreScootersThanTransferred);

        Assertions.assertThrows(InsufficientScootersInTransitException.class,
                unloadWithMoreScootersThanTransferred);
    }

    @Test
    void givenTransferWithScooters_whenUnload_thenReturnsUnloadedScooters() {
        List<ScooterId> unloadedScooters = transfer.unload(TECHNICIAN_ID, 2);
        
        Assertions.assertEquals(2, unloadedScooters.size());
        Assertions.assertTrue(unloadedScooters.contains(scooterId1));
        Assertions.assertTrue(unloadedScooters.contains(scooterId2));
    }
}
