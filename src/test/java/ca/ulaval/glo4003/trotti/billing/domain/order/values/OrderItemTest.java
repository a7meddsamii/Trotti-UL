package ca.ulaval.glo4003.trotti.billing.domain.order.values;

import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrderItemTest {
	
	private final ItemId itemId = Mockito.mock(ItemId.class);
	private final MaximumDailyTravelTime maximumTravelingTime = Mockito.mock(MaximumDailyTravelTime.class);
	private final Session session = Mockito.mock(Session.class);
	private final BillingFrequency billingFrequency = BillingFrequency.MONTHLY;
	
	@Test
	void givenTwoIdenticalOrderItems_whenEquals_thenReturnTrue() {
		OrderItem orderItem1 = new OrderItem(itemId, maximumTravelingTime, session, billingFrequency);
		OrderItem orderItem2 = new OrderItem(itemId, maximumTravelingTime, session, billingFrequency);
		
		boolean response = orderItem1.equals(orderItem2);
		
		Assertions.assertTrue(response);
	}
	
	@Test
	void givenTwoOrderItemsWithDifferentItemId_whenEquals_thenReturnFalse() {
		OrderItem orderItem1 = new OrderItem(itemId, maximumTravelingTime, session, billingFrequency);
		ItemId differentItemId = Mockito.mock(ItemId.class);
		OrderItem orderItem2 = new OrderItem(differentItemId, maximumTravelingTime, session, billingFrequency);
		
		boolean response = orderItem1.equals(orderItem2);
		
		Assertions.assertFalse(response);
	}
	
	@Test
	void givenTwoOrderItemsWithDifferentMaximumTravelingTime_whenEquals_thenReturnFalse() {
		OrderItem orderItem1 = new OrderItem(itemId, maximumTravelingTime, session, billingFrequency);
		MaximumDailyTravelTime differentTime = Mockito.mock(MaximumDailyTravelTime.class);
		OrderItem orderItem2 = new OrderItem(itemId, differentTime, session, billingFrequency);
		
		boolean response = orderItem1.equals(orderItem2);
		
		Assertions.assertFalse(response);
	}
	
	@Test
	void givenTwoOrderItemsWithDifferentSession_whenEquals_thenReturnFalse() {
		OrderItem orderItem1 = new OrderItem(itemId, maximumTravelingTime, session, billingFrequency);
		Session differentSession = Mockito.mock(Session.class);
		OrderItem orderItem2 = new OrderItem(itemId, maximumTravelingTime, differentSession, billingFrequency);
		
		boolean response = orderItem1.equals(orderItem2);
		
		Assertions.assertFalse(response);
	}
	
	@Test
	void givenTwoOrderItemsWithDifferentBillingFrequency_whenEquals_thenReturnFalse() {
		OrderItem orderItem1 = new OrderItem(itemId, maximumTravelingTime, session, billingFrequency);
		BillingFrequency differentFrequency = BillingFrequency.PER_TRIP;
		OrderItem orderItem2 = new OrderItem(itemId, maximumTravelingTime, session, differentFrequency);
		
		boolean response = orderItem1.equals(orderItem2);
		
		Assertions.assertFalse(response);
	}
	
	@Test
	void givenOrderItem_whenGetCost_thenReturnCalculatedCostFromMaximumTravelingTime() {
		Money expectedCost = Mockito.mock(Money.class);
		Mockito.when(maximumTravelingTime.calculateAmount()).thenReturn(expectedCost);
		OrderItem orderItem = new OrderItem(itemId, maximumTravelingTime, session, billingFrequency);
		
		Money actualCost = orderItem.getCost();
		
		Assertions.assertEquals(expectedCost, actualCost);
	}
}