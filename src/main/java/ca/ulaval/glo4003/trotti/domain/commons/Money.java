package ca.ulaval.glo4003.trotti.domain.commons;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    private final BigDecimal value;

    public static Money from(BigDecimal value) {
        return new Money(value);
    }

    private Money(BigDecimal value) {
        this.value = value;
    }

    public Money add(Money other) {
        return new Money(this.value.add(other.value));
    }

    public Money multiply(BigDecimal multiplier) {
        return new Money(this.value.multiply(multiplier));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Money money = (Money) o;
        return value.equals(money.value);
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
