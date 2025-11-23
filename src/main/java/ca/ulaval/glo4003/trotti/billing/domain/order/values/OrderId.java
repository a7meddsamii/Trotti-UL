package ca.ulaval.glo4003.trotti.billing.domain.order.values;

import ca.ulaval.glo4003.trotti.commons.domain.Id;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.util.Objects;
import java.util.UUID;

public class OrderId extends Id {

    private final UUID value;

    private OrderId() {
        this.value = UUID.randomUUID();
    }

    private OrderId(String value) {
        try {
            this.value = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Transaction Id is not valid");
        }
    }

    public static OrderId from(String value) {
        return new OrderId(value);
    }

    public static OrderId randomId() {
        return new OrderId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderId id = (OrderId) o;
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
