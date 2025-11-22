package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;

import java.util.Objects;
import java.util.UUID;

public class RidePermitId {


	private final UUID value;
	
	private RidePermitId() {
		this.value = UUID.randomUUID();
	}
	
	private RidePermitId(String value) {
		try {
			this.value = UUID.fromString(value);
		} catch (IllegalArgumentException e) {
			throw new InvalidParameterException("ID is not valid");
		}
	}
	
	public static RidePermitId from(String value) {
		return new RidePermitId(value);
	}
	
	public static RidePermitId randomId() {
		return new RidePermitId();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		RidePermitId id = (RidePermitId) o;
		return value.equals(id.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
