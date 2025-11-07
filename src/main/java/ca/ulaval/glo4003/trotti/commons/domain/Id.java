package ca.ulaval.glo4003.trotti.commons.domain;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.util.Objects;
import java.util.UUID;

public abstract class Id {

    private final UUID value;

    protected Id() {
        this.value = UUID.randomUUID();
    }

    protected Id(String value) {
        try {
            this.value = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("ID is not valid");
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
