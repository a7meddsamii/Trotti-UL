package ca.ulaval.glo4003.trotti.billing.domain.payment.values;

import ca.ulaval.glo4003.trotti.commons.domain.Id;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.util.Objects;
import java.util.UUID;

public class TransactionId extends Id {

    private final UUID value;

    private TransactionId() {
        this.value = UUID.randomUUID();
    }

    private TransactionId(String value) {
        try {
            this.value = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Transaction Id is not valid");
        }
    }

    public static TransactionId from(String value) {
        return new TransactionId(value);
    }

    public static TransactionId randomId() {
        return new TransactionId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionId id = (TransactionId) o;
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
