package ca.ulaval.glo4003.trotti.billing.domain.order.entities;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.ItemId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderStatus;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final OrderId orderId;
    private final Idul idul;
    private final List<RidePermitItem> items;
    private final OrderStatus status;

    public Order(OrderId orderId, Idul buyerId) {
        this.orderId = orderId;
        this.idul = buyerId;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    public Order(OrderId orderId, Idul buyerId, List<RidePermitItem> items, OrderStatus status) {
        this.orderId = orderId;
        this.idul = buyerId;
        this.items = new ArrayList<>(items);
        this.status = status;
    }

    public List<RidePermitItem> getItems() {
        return List.copyOf(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public boolean add(RidePermitItem item) {
        if (alreadyContains(item)) {
            return false;
        }

        return this.items.add(item);
    }

    public boolean remove(ItemId itemid) {
        return items.removeIf(item -> item.getItemId().equals(itemid));
    }

    public void clear() {
        items.clear();
    }

    public Money getTotalCost() {
        Money total = Money.zeroCad();
        for (RidePermitItem pass : items) {
            total = total.plus(pass.getCost());
        }

        return total;
    }

    private boolean alreadyContains(RidePermitItem item) {
        return items.stream().anyMatch(inCartItem -> inCartItem.isItem(item.getItemId()));
    }
}
