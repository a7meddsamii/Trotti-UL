package ca.ulaval.glo4003.trotti.domain.payment.values.money;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {

    private static final int HASH_SEED = 31;
    private static final int DECIMALS_PRECISION = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private final BigDecimal amount;
    private final Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money zeroCad() {
        return new Money(BigDecimal.ZERO, Currency.CAD);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        if (currency == null || amount == null) {
            throw new InvalidParameterException("Amount or currency cannot be null");
        }

        return new Money(amount, currency);
    }

    public Money plus(Money that) {
        validateSameCurrency(that);

        return Money.of(amount.add(that.amount), currency);
    }

    public Money minus(Money that) {
        validateSameCurrency(that);

        return Money.of(amount.subtract(that.amount), currency);
    }

    public Money multiply(BigDecimal multiplier) {
        return Money.of(amount.multiply(multiplier), currency);
    }

    public boolean isMoreThanOrEqual(Money that) {
        validateSameCurrency(that);

        return this.amount.compareTo(that.amount) >= 0;
    }

    public boolean isLessThan(Money that) {
        validateSameCurrency(that);

        return this.amount.compareTo(that.amount) < 0;
    }

    public boolean isPositiveOrZero() {
        return this.amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    public boolean isNegative() {
        return this.amount.compareTo(BigDecimal.ZERO) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Money money = (Money) o;

        return amount.compareTo(money.amount) == 0 && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return HASH_SEED * amount.stripTrailingZeros().hashCode() + currency.hashCode();
    }

    @Override
    public String toString() {
        return amount.setScale(DECIMALS_PRECISION, ROUNDING_MODE) + " " + currency;
    }

    private void validateSameCurrency(Money that) {
        if (!this.currency.equals(that.currency)) {
            throw new IllegalArgumentException(
                    "Currency mismatch: " + this.currency + " vs " + that.currency);
        }
    }

}
