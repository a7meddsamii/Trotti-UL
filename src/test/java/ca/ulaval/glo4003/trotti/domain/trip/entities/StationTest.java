package ca.ulaval.glo4003.trotti.domain.trip.entities;

class StationTest {
    // private static final Id A_SCOOTER_ID = Id.randomId();
    // private static final Id SECOND_SCOOTER_ID = Id.randomId();
    // private static final Id THIRD_SCOOTER_ID = Id.randomId();
    // private final List<ScooterSlot> A_SCOOTER_SLOT_LIST = new ArrayList<>();
    // private Station station;
    //
    // @BeforeEach
    // void setup() {
    // Location location = Mockito.mock(Location.class);
    // ScooterSlot FIRST_SCOOTER_SLOT = new ScooterSlot();
    // ScooterSlot SECOND_SCOOTER_SLOT = new ScooterSlot();
    // A_SCOOTER_SLOT_LIST.add(FIRST_SCOOTER_SLOT);
    // A_SCOOTER_SLOT_LIST.add(SECOND_SCOOTER_SLOT);
    // station = new Station(location, A_SCOOTER_SLOT_LIST);
    // }
    //
    // @Test
    // void givenStation_whenDockScooter_thenScooterIsAddedToStation() {
    // station.dockScooter(A_SCOOTER_ID);
    //
    // Assertions.assertTrue(this.contains(A_SCOOTER_ID));
    // }
    //
    // @Test
    // void givenScooterAlreadyInStation_whenDockScooter_thenThrowsException() {
    // station.dockScooter(A_SCOOTER_ID);
    //
    // Executable dock = () -> station.dockScooter(A_SCOOTER_ID);
    //
    // Assertions.assertThrows(DockingException.class, dock);
    // }
    //
    // @Test
    // void givenStationAtMaximumCapacity_whenDockScooter_thenThrowsException() {
    // station.dockScooter(A_SCOOTER_ID);
    // station.dockScooter(SECOND_SCOOTER_ID);
    //
    // Executable dock = () -> station.dockScooter(THIRD_SCOOTER_ID);
    //
    // Assertions.assertThrows(DockingException.class, dock);
    // }
    //
    // @Test
    // void givenStation_whenUndockScooter_thenScooterIsRemovedFromStation() {
    // station.dockScooter(A_SCOOTER_ID);
    //
    // station.undockScooter(A_SCOOTER_ID);
    //
    // Assertions.assertFalse(this.contains(A_SCOOTER_ID));
    // }
    //
    // @Test
    // void givenScooterNotInStation_whenUndockScooter_thenThrowsException() {
    // Executable undock = () -> station.undockScooter(A_SCOOTER_ID);
    //
    // Assertions.assertThrows(DockingException.class, undock);
    // }
    //
    // private boolean contains(Id scooterId) {
    // return A_SCOOTER_SLOT_LIST.stream().anyMatch(
    // slot -> slot.getScooterId().filter(id -> id.equals(scooterId)).isPresent());
    // }
}
