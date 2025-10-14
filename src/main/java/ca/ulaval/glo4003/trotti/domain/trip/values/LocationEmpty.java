package ca.ulaval.glo4003.trotti.domain.trip.values;

non-sealed class LocationEmpty extends Location {
	private static final LocationEmpty INSTANCE = new LocationEmpty();
	
	private LocationEmpty() {
		super("", "");
	}
	
	protected static LocationEmpty getInstance() {
		return INSTANCE;
	}
	
	@Override
	public String toString() {
		return "In transit";
	}
}