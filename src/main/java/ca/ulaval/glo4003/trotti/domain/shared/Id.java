package ca.ulaval.glo4003.trotti.domain.shared;

import java.util.Objects;
import java.util.UUID;

public class Id {
	
	private final UUID value;
	
	public Id from(String value) {
		return new Id(value);
	}
	
	public static Id randomId() {
		return new Id();
	}
	
	private Id() {
		this.value = UUID.randomUUID();
	}
	
	private Id(String value) {
		try {
			this.value = UUID.fromString(value);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("ID is not valid");
		}
	}
	
	public String getValue() {
		return value.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Id id = (Id) o;
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
