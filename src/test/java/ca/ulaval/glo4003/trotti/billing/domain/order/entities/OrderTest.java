package ca.ulaval.glo4003.trotti.billing.domain.order.entities;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.*;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrderTest {

    private static final MaximumDailyTravelTime FIRST_MAXIMUM_TRAVEL_TIME =
            MaximumDailyTravelTime.from(Duration.ofHours(2));
    private static final MaximumDailyTravelTime SECOND_MAXIMUM_TRAVEL_TIME =
            MaximumDailyTravelTime.from(Duration.ofHours(5));
    private OrderItem firstItem;
    private OrderItem secondItem;
    private OrderItem duplicateOfFirst;
    private ItemId firstItemId;

    @BeforeEach
    void setUp() {
        Session session = Mockito.mock(Session.class);
        firstItem = new OrderItem(ItemId.randomId(), FIRST_MAXIMUM_TRAVEL_TIME, session,
                BillingFrequency.MONTHLY);
        secondItem = new OrderItem(ItemId.randomId(), SECOND_MAXIMUM_TRAVEL_TIME, session,
                BillingFrequency.PER_TRIP);
        duplicateOfFirst = new OrderItem(firstItem.getItemId(), FIRST_MAXIMUM_TRAVEL_TIME, session,
                BillingFrequency.MONTHLY);
        firstItemId = firstItem.getItemId();
    }

    @Test
    void givenItemsNotAlreadyInOrder_whenAdding_thenItemsAreAddedAndTrueReturned() {
        Order order = new Order();

        boolean added = order.add(firstItem, secondItem);

        assertTrue(added);
        assertEquals(List.of(firstItem, secondItem), order.getItems());
    }

    @Test
    void givenItemAlreadyInOrder_whenAddingDuplicate_thenFalseReturnedAndNoDuplicateAdded() {
        Order order = new Order();
        order.add(firstItem);

        boolean added = order.add(duplicateOfFirst);

        assertFalse(added);
        assertEquals(1, order.getItems().size());
    }

    @Test
    void givenExistingItem_whenRemoving_thenItemRemovedAndTrueReturned() {
        Order order = new Order();
        order.add(firstItem, secondItem);
        boolean removed = order.remove(firstItemId);
        assertTrue(removed);
        assertEquals(List.of(secondItem), order.getItems());
    }

    @Test
    void givenNonExistingItemId_whenRemoving_thenFalseReturnedAndItemsUnchanged() {
        Order order = new Order();
        order.add(firstItem);
        ItemId unknownId = ItemId.randomId();

        boolean removed = order.remove(unknownId);

        assertFalse(removed);
        assertEquals(List.of(firstItem), order.getItems());
    }

    @Test
    void givenOrderWithItems_whenClearing_thenItemsEmptied() {
        Order order = new Order();
        order.add(firstItem, secondItem);

        order.clear();

        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void givenOrderWithItems_whenGettingTotalCost_thenReturnsSumOfItemCosts() {
        Order order = new Order();
        order.add(firstItem, secondItem);
        Money expected = firstItem.getCost().plus(secondItem.getCost());

        Money total = order.getTotalCost();

        assertEquals(expected, total);
    }

    @Test
    void givenEmptyOrder_whenGettingTotalCost_thenReturnsZeroMoney() {
        Order order = new Order();

        Money total = order.getTotalCost();

        assertEquals(Money.zeroCad(), total);
    }
}
