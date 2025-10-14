package ca.ulaval.glo4003.trotti.domain.trip.values;

public non-sealed class EmptyLocation extends Location {
	private static final EmptyLocation INSTANCE = new EmptyLocation();
	
	private EmptyLocation() {
		super("", "");
	}
	
	public static EmptyLocation getInstance() {
		return INSTANCE;
	}
	
	@Override
	public String toString() {
		return "In transit";
	}
}