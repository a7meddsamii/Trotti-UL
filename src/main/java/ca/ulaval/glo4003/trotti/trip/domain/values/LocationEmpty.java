package ca.ulaval.glo4003.trotti.trip.domain.values;

final class LocationEmpty extends Location {
    private static final LocationEmpty INSTANCE = new LocationEmpty();

    private LocationEmpty() {
        super("", "");
    }

    static LocationEmpty getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "In transit";
    }
}
