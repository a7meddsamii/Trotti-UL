package ca.ulaval.glo4003.trotti.billing.domain.payment.values.money;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MoneyTest {

    private static final double AMOUNT_100 = 100.0;
    private static final double AMOUNT_50 = 50.0;
    private static final double AMOUNT_200 = 200.0;
    private static final double AMOUNT_ZERO = 0.0;
    private static final double AMOUNT_NEGATIVE = -10.0;
    private static final double MULTIPLIER_TWO = 2.0;

    @Test
    void whenZeroCad_thenReturnMoneyInstance() {
        Money zeroCad = Money.zeroCad();

        Assertions.assertEquals(Money.of(AMOUNT_ZERO, Currency.CAD), zeroCad);
    }

    @Test
    void givenSameAmountAndCurrency_whenEquals_thenReturnsTrue() {
        Money money1 = Money.of(AMOUNT_100, Currency.CAD);
        Money money2 = Money.of(AMOUNT_100, Currency.CAD);
        Money different = Money.of(AMOUNT_50, Currency.CAD);

        Assertions.assertEquals(money1, money2);
        Assertions.assertEquals(money1.hashCode(), money2.hashCode());
        Assertions.assertNotEquals(money1, different);
    }

    @Test
    void givenMoneyWithDifferentCurrency_whenPlus_thenThrowException() {
        Money moneyInDifferentCurrency = Money.of(AMOUNT_100, Currency.OTHER);
        Money moneyCad = Money.of(AMOUNT_100, Currency.CAD);

        Executable executable = () -> moneyCad.plus(moneyInDifferentCurrency);

        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void givenSameCurrency_whenPlus_thenReturnsSum() {
        Money money1 = Money.of(AMOUNT_100, Currency.CAD);
        Money money2 = Money.of(AMOUNT_100, Currency.CAD);

        Money result = money1.plus(money2);

        Assertions.assertEquals(Money.of(AMOUNT_200, Currency.CAD), result);
    }

    @Test
    void givenSameCurrency_whenMinus_thenReturnsDifference() {
        Money money1 = Money.of(AMOUNT_100, Currency.CAD);
        Money money2 = Money.of(AMOUNT_50, Currency.CAD);

        Money result = money1.minus(money2);

        Assertions.assertEquals(Money.of(AMOUNT_50, Currency.CAD), result);
    }

    @Test
    void givenDifferentCurrencies_whenMinus_thenThrowsException() {
        Money moneyInDifferentCurrency = Money.of(AMOUNT_100, Currency.OTHER);
        Money moneyCad = Money.of(AMOUNT_100, Currency.CAD);

        Executable executable = () -> moneyCad.minus(moneyInDifferentCurrency);

        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void givenMultiplier_whenMultiply_thenReturnsProduct() {
        Money money100 = Money.of(AMOUNT_100, Currency.CAD);

        Money result = money100.multiply(MULTIPLIER_TWO);

        Assertions.assertEquals(Money.of(AMOUNT_200, Currency.CAD), result);
    }

    @Test
    void givenDifferentAmounts_whenIsMoreThanOrEqual_thenReturnsCorrectResult() {
        Money money100 = Money.of(AMOUNT_100, Currency.CAD);
        Money money50 = Money.of(AMOUNT_50, Currency.CAD);
        Money money200 = Money.of(AMOUNT_200, Currency.CAD);

        Assertions.assertTrue(money100.isMoreThanOrEqual(money50));
        Assertions.assertTrue(money100.isMoreThanOrEqual(money100));
        Assertions.assertFalse(money100.isMoreThanOrEqual(money200));
    }

    @Test
    void givenDifferentAmounts_whenIsLessThan_thenReturnsCorrectResult() {
        Money money100 = Money.of(AMOUNT_100, Currency.CAD);
        Money money50 = Money.of(AMOUNT_50, Currency.CAD);
        Money money200 = Money.of(AMOUNT_200, Currency.CAD);

        Assertions.assertFalse(money100.isLessThan(money50));
        Assertions.assertFalse(money100.isLessThan(money100));
        Assertions.assertTrue(money100.isLessThan(money200));
    }

    @Test
    void givenDifferentCurrencies_whenIsMoreThanOrEqual_thenThrowsException() {
        Money money100 = Money.of(AMOUNT_100, Currency.CAD);
        Money money50 = Money.of(AMOUNT_50, Currency.OTHER);

        Executable executable = () -> money100.isMoreThanOrEqual(money50);

        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void givenDifferentCurrencies_whenIsLessThan_thenThrowsException() {
        Money money100 = Money.of(AMOUNT_100, Currency.CAD);
        Money money200 = Money.of(AMOUNT_200, Currency.OTHER);

        Executable executable = () -> money100.isLessThan(money200);

        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void givenDifferentAmounts_whenIsZero_thenReturnsCorrectResult() {
        Money money100 = Money.of(AMOUNT_100, Currency.CAD);
        Money zero = Money.zeroCad();

        Assertions.assertTrue(zero.isZero());
        Assertions.assertFalse(money100.isZero());
    }

    @Test
    void givenDifferentAmounts_whenIsPositiveOrZero_thenReturnsCorrectResult() {
        Money zero = Money.zeroCad();
        Money negative = Money.of(AMOUNT_NEGATIVE, Currency.CAD);
        Money money100 = Money.of(AMOUNT_100, Currency.CAD);

        Assertions.assertTrue(money100.isPositiveOrZero());
        Assertions.assertTrue(zero.isPositiveOrZero());
        Assertions.assertFalse(negative.isPositiveOrZero());
    }

    @Test
    void givenDifferentAmounts_whenIsNegative_thenReturnsCorrectResult() {
        Money negative = Money.of(AMOUNT_NEGATIVE, Currency.CAD);
        Money zero = Money.zeroCad();
        Money money100 = Money.of(AMOUNT_100, Currency.CAD);

        Assertions.assertTrue(negative.isNegative());
        Assertions.assertFalse(money100.isNegative());
        Assertions.assertFalse(zero.isNegative());
    }

}
