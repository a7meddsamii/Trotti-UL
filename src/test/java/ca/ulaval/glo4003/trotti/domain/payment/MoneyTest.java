package ca.ulaval.glo4003.trotti.domain.payment;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.payment.values.money.Currency;
import ca.ulaval.glo4003.trotti.domain.payment.values.money.Money;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MoneyTest {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final String EXPECTED_STRING_REPRESENTATION = "100.00 CAD";

    private Money money;

    @BeforeEach
    void setup() {
        money = Money.of(ONE_HUNDRED, Currency.CAD);
    }

    @Test
    void whenZeroCad_thenReturnMoneyInstance() {
        Money zeroCad = Money.zeroCad();

        Assertions.assertEquals(Money.of(BigDecimal.ZERO, Currency.CAD), zeroCad);
    }

    @Test
    void whenInstantiateWithNullAmount_thenThrowException() {
        Executable executable = () -> Money.of(null, Currency.CAD);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    @Test
    void whenInstantiateWithNullCurrency_thenThrowException() {
        Executable executable = () -> Money.of(ONE_HUNDRED, null);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    @Test
    void givenMoneyWithDifferentCurrency_whenPlus_thenThrowException() {
        Money moneyInDifferentCurrency = Money.of(ONE_HUNDRED, Currency.OTHER);

        Executable executable = () -> money.plus(moneyInDifferentCurrency);

        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void givenMoneyWithDifferentCurrency_whenMinus_thenThrowException() {
        Money moneyInDifferentCurrency = Money.of(ONE_HUNDRED, Currency.OTHER);

        Executable executable = () -> money.minus(moneyInDifferentCurrency);

        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void whenPlus_thenReturnSum() {
        Money other = Money.of(ONE_HUNDRED, Currency.CAD);

        Money result = money.plus(other);

        Assertions.assertEquals(Money.of(BigDecimal.valueOf(200), Currency.CAD), result);
    }

    @Test
    void whenPositiveOrZero_thenReturnTrue() {
        Assertions.assertTrue(money.isPositiveOrZero());
    }

    @Test
    void whenNegative_thenReturnFalse() {
        Assertions.assertFalse(money.isNegative());
    }

    @Test
    void whenMinus_thenReturnDifference() {
        Money fiftyCad = Money.of(BigDecimal.valueOf(50), Currency.CAD);

        Money result = money.minus(fiftyCad);

        Assertions.assertEquals(Money.of(BigDecimal.valueOf(50), Currency.CAD), result);
    }

    @Test
    void whenMultiply_thenReturnProduct() {
        Money result = money.multiply(BigDecimal.TWO);

        Assertions.assertEquals(Money.of(BigDecimal.valueOf(200), Currency.CAD), result);
    }

    @Test
    void whenToString_thenReturnStringRepresentation() {
        String result = money.toString();

        Assertions.assertEquals(EXPECTED_STRING_REPRESENTATION, result);
    }

    @Test
    void givenSameAmountOfMoneySameCurrency_whenEquals_thenReturnTrue() {
        Money anotherMoney = Money.of(ONE_HUNDRED, Currency.CAD);

        Assertions.assertEquals(money, anotherMoney);
    }

    @Test
    void givenDifferentAmountOfMoneySameCurrency_whenEquals_thenReturnFalse() {
        Money anotherMoney = Money.of(BigDecimal.TEN, Currency.CAD);

        Assertions.assertNotEquals(money, anotherMoney);
    }

    @Test
    void givenSameAmountOfMoneyDifferentCurrency_whenEquals_thenReturnFalse() {
        Money anotherMoney = Money.of(ONE_HUNDRED, Currency.OTHER);

        Assertions.assertNotEquals(money, anotherMoney);
    }

    @Test
    void givenDifferentAmountOfMoneyDifferentCurrency_whenEquals_thenReturnFalse() {
        Money anotherMoney = Money.of(BigDecimal.TEN, Currency.OTHER);

        Assertions.assertNotEquals(money, anotherMoney);
    }

    @Test
    void givenAnotherMoneyWithLowerAmount_whenIsGreaterOrEqualThan_thenReturnTrue() {
        Money anotherMoney = Money.of(BigDecimal.TEN, Currency.CAD);

        Assertions.assertTrue(money.isMoreThanOrEqual(anotherMoney));
    }

    @Test
    void givenAnotherMoneyWithHigherAmount_whenIsGreaterOrEqualThan_thenReturnFalse() {
        Money anotherMoney = Money.of(BigDecimal.valueOf(200), Currency.CAD);

        Assertions.assertFalse(money.isMoreThanOrEqual(anotherMoney));
    }

    @Test
    void givenAnotherMoneyWithSameAmount_whenIsGreaterOrEqualThan_thenReturnTrue() {
        Money anotherMoney = Money.of(ONE_HUNDRED, Currency.CAD);

        Assertions.assertTrue(money.isMoreThanOrEqual(anotherMoney));
    }

    @Test
    void givenAnotherMoneyWithLowerAmount_whenIsLessOrEqualThan_thenReturnFalse() {
        Money anotherMoney = Money.of(BigDecimal.TEN, Currency.CAD);

        Assertions.assertFalse(money.isLessThan(anotherMoney));
    }
}
