package ca.ulaval.glo4003.trotti.domain.trip.exceptions;

public class InvalidLocation extends RuntimeException {
	public InvalidLocation(String message) {
		super(message);
	}
}