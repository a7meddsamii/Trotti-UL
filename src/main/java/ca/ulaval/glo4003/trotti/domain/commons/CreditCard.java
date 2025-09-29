package ca.ulaval.glo4003.trotti.domain.commons;

import java.util.Objects;

public class CreditCard {
    private final int value;

    public static CreditCard from(int value) {
        return new CreditCard(value);
    }

    private CreditCard(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditCard creditCard = (CreditCard) o;
        return value == creditCard.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
