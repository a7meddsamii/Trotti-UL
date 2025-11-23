package ca.ulaval.glo4003.trotti.billing.domain.order.values;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.util.Objects;
import java.util.UUID;

public class ItemId {

    private final UUID value;

    private ItemId() {
        this.value = UUID.randomUUID();
    }

    private ItemId(String value) {
        try {
            this.value = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("ID is not valid");
        }
    }

    public static ItemId from(String value) {
        return new ItemId(value);
    }

    public static ItemId randomId() {
        return new ItemId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemId id = (ItemId) o;
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
