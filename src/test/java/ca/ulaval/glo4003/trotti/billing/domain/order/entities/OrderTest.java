package ca.ulaval.glo4003.trotti.billing.domain.order.entities;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.*;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderStatus;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {

    private static final MaximumDailyTravelTime FIRST_MAXIMUM_TRAVEL_TIME_DURATION =
            MaximumDailyTravelTime.from(Duration.ofHours(2));
    private static final MaximumDailyTravelTime SECOND_MAXIMUM_TRAVEL_TIME_DURATION =
            MaximumDailyTravelTime.from(Duration.ofHours(5));
    private static final Idul VALID_IDUL = Idul.from("user123");
    private static final OrderId VALID_ORDER_ID = OrderId.randomId();

    private RidePermitItem firstItem;
    private RidePermitItem secondItem;
    private RidePermitItem duplicateOfFirst;
    private ItemId firstItemId;
    private Order order;

    @BeforeEach
    void setUp() {
        Session session = createSession();
        firstItem = new RidePermitItem(ItemId.randomId(), FIRST_MAXIMUM_TRAVEL_TIME_DURATION,
                session, BillingFrequency.MONTHLY);
        secondItem = new RidePermitItem(ItemId.randomId(), SECOND_MAXIMUM_TRAVEL_TIME_DURATION,
                session, BillingFrequency.PER_TRIP);
        duplicateOfFirst = new RidePermitItem(firstItem.getItemId(),
                FIRST_MAXIMUM_TRAVEL_TIME_DURATION, session, BillingFrequency.MONTHLY);
        firstItemId = firstItem.getItemId();
        order = new Order(VALID_ORDER_ID, VALID_IDUL);
    }

    @Test
    void givenItemsNotAlreadyInOrder_whenAdding_thenItemsAreAddedAndTrueReturned() {
        boolean added = order.add(firstItem);

        Assertions.assertTrue(added);
        Assertions.assertEquals(List.of(firstItem), order.getItems());
    }

    @Test
    void givenItemAlreadyInOrder_whenAddingDuplicate_thenFalseReturnedAndNoDuplicateAdded() {
        order.add(firstItem);

        boolean added = order.add(duplicateOfFirst);

        Assertions.assertFalse(added);
        Assertions.assertEquals(1, order.getItems().size());
    }

    @Test
    void givenExistingItem_whenRemoving_thenItemRemovedAndTrueReturned() {
        order.add(firstItem);
        order.add(secondItem);

        boolean removed = order.remove(firstItemId);

        Assertions.assertTrue(removed);
        Assertions.assertEquals(List.of(secondItem), order.getItems());
    }

    @Test
    void givenNonExistingItemId_whenRemoving_thenFalseReturnedAndItemsUnchanged() {
        order.add(firstItem);
        ItemId unknownId = ItemId.randomId();

        boolean removed = order.remove(unknownId);

        Assertions.assertFalse(removed);
        Assertions.assertEquals(List.of(firstItem), order.getItems());
    }

    @Test
    void givenOrderWithItems_whenClearing_thenItemsEmptied() {
        order.add(firstItem);
        order.add(secondItem);

        order.clear();

        Assertions.assertTrue(order.getItems().isEmpty());
    }

    @Test
    void givenOrderWithItems_whenGettingTotalCost_thenReturnsSumOfItemCosts() {
        order.add(firstItem);
        order.add(secondItem);
        Money expected = firstItem.getCost().plus(secondItem.getCost());

        Money total = order.getTotalCost();

        Assertions.assertEquals(expected, total);
    }

    @Test
    void givenEmptyOrder_whenGettingTotalCost_thenReturnsZeroMoney() {

        Money total = order.getTotalCost();

        Assertions.assertEquals(Money.zeroCad(), total);
    }

    @Test
    void givenPendingOrder_whenConfirming_thenStatusBecomesCompleted() {
        Assertions.assertEquals(OrderStatus.PENDING, order.getStatus());

        order.confirm();

        Assertions.assertEquals(OrderStatus.COMPLETED, order.getStatus());
    }

    private Session createSession() {
        return new Session(Semester.WINTER, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 4, 30));
    }
}
