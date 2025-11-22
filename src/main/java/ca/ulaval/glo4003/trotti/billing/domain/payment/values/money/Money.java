package ca.ulaval.glo4003.trotti.billing.domain.payment.values.money;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
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

    public static Money of(double amount, Currency currency) {
        BigDecimal bigDecimalAmount = BigDecimal.valueOf(amount);
        return new Money(bigDecimalAmount, currency);
    }

    public Money plus(Money that) {
        validateSameCurrency(that);

        return new Money(amount.add(that.amount), currency);
    }

    public Money minus(Money that) {
        validateSameCurrency(that);

        return new Money(amount.subtract(that.amount), currency);
    }

    public Money multiply(double multiplier) {
        BigDecimal multiplierBigDecimal = BigDecimal.valueOf(multiplier);
        return new Money(amount.multiply(multiplierBigDecimal), currency);
    }

    public boolean isMoreThanOrEqual(Money that) {
        validateSameCurrency(that);

        return this.amount.compareTo(that.amount) >= 0;
    }

    public boolean isLessThan(Money that) {
        validateSameCurrency(that);

        return this.amount.compareTo(that.amount) < 0;
    }

    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
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
