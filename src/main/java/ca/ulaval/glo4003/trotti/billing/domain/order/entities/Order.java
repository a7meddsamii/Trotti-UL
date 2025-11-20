package ca.ulaval.glo4003.trotti.billing.domain.order.entities;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.ItemId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderItem;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<OrderItem> items;

    public Order() {
        this.items = new ArrayList<>();
    }

    public Order(List<OrderItem> items) {
        this.items = new ArrayList<>(items);
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    public boolean add(OrderItem item) {
        if (alreadyContains(item)) {
            return false;
        }
		
        return items.add(item);
    }

    public boolean remove(ItemId itemid) {
        return items.removeIf(item -> item.getItemId().equals(itemid));
    }

    public void clear() {
        items.clear();
    }

    public Money getTotalCost() {
        Money total = Money.zeroCad();
        for (OrderItem pass : items) {
            total = total.plus(pass.getCost());
        }

        return total;
    }

    private boolean alreadyContains(OrderItem item) {
        return items.stream().anyMatch(i -> i.getItemId().equals(item.getItemId()));
    }
}
