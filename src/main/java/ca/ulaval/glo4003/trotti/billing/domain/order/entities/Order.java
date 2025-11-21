package ca.ulaval.glo4003.trotti.billing.domain.order.entities;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.ItemId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderStatus;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<OrderItem> items;
	private final OrderStatus status;

    public Order() {
        this.items = new ArrayList<>();
		this.status = OrderStatus.PENDING;
    }

    public Order(List<OrderItem> items, OrderStatus status) {
        this.items = new ArrayList<>(items);
		this.status = status;
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }
	
	public OrderStatus getStatus() {
		return status;
	}
	
	public boolean add(OrderItem... items) {
        if (alreadyContains(items)) {
            return false;
        }
		
        return this.items.addAll(List.of(items));
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

    private boolean alreadyContains(OrderItem... item) {
        for (OrderItem orderItem : item) {
            if (items.stream().anyMatch(existingItem -> existingItem.isItem(orderItem.getItemId()))) {
				return true;
			}
        }
		
		return false;
    }
}
