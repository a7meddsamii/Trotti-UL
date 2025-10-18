package ca.ulaval.glo4003.trotti.domain.commons;

import java.util.Objects;
import java.util.UUID;

/**
 * @deprecated A following PR will make this class abstract to enforce the use of specific ID classes.
 *  so from this point on, please use or create specific ID classes (e.g., OrderId, TransactionId, etc.) that extend this class.
 *  as an example, you can refer to {@link ca.ulaval.glo4003.trotti.domain.order.values.OrderId}.
 */
public class Id {

    private final UUID value;

	@Deprecated
    public static Id from(String value) {
        return new Id(value);
    }

	@Deprecated
    public static Id randomId() {
        return new Id();
    }

    protected Id() {
        this.value = UUID.randomUUID();
    }
	
	protected Id(String value) {
        try {
            this.value = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID is not valid");
        }
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
