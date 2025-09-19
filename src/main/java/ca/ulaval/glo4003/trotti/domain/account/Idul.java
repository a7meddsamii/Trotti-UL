package ca.ulaval.glo4003.trotti.domain.account;

import java.util.Objects;

public class Idul {
	
	private final String value;
	
	public static Idul from(String value) {
		return new Idul(value);
	}
	
	private Idul(String value) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("IDUL cannot be null or empty");
		}
		
		this.value = value;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Idul idul = (Idul) o;
		return value.equals(idul.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
