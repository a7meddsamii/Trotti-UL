package ca.ulaval.glo4003.trotti.billing.domain.order.values;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.UUID;

public class ItemIdTest {
	private static final String VALID_VALUE = UUID.randomUUID().toString();
	private static final String INVALID_VALUE = "invalid_uuid";
	
	@Test
	void givenValidValue_whenFrom_thenDoesNotThrowException() {
		Executable from = () -> ItemId.from(VALID_VALUE);
		
		Assertions.assertDoesNotThrow(from);
	}
	
	@Test
	void givenValidValue_whenFrom_thenReturnsItemId() {
		ItemId itemId = ItemId.from(VALID_VALUE);
		
		Assertions.assertNotNull(itemId);
	}
	
	@Test
	void givenInvalidValue_whenFrom_thenThrowsException() {
		Executable from = () -> ItemId.from(INVALID_VALUE);
		
		Assertions.assertThrows(InvalidParameterException.class, from);
	}
}
