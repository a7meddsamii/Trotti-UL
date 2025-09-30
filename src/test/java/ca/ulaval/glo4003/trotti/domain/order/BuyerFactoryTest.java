package ca.ulaval.glo4003.trotti.domain.order;

import static ca.ulaval.glo4003.trotti.fixtures.AccountFixture.*;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class BuyerFactoryTest {
    private final BuyerFactory factory = new BuyerFactory();

    @Test
    void givenValidParams_whenCreateWithoutCart_thenReturnOrder() {
        Buyer buyer = factory.create(AN_IDUL, A_NAME, AN_EMAIL);

        Assertions.assertEquals(AN_IDUL, buyer.getIdul());
        Assertions.assertEquals(A_NAME, buyer.getName());
        Assertions.assertEquals(AN_EMAIL, buyer.getEmail());
    }

    @Test
    void givenValidParams_whenCreateWithCart_thenReturnOrder() {
        Cart A_CART = new Cart();

        Buyer buyer = factory.create(AN_IDUL, A_NAME, AN_EMAIL, A_CART);

        Assertions.assertEquals(AN_IDUL, buyer.getIdul());
        Assertions.assertEquals(A_NAME, buyer.getName());
        Assertions.assertEquals(AN_EMAIL, buyer.getEmail());
        Assertions.assertEquals(A_CART, buyer.getCart());
    }

    @Test
    void givenNullIdul_whenCreate_thenThrowsException() {
        Executable creation = () -> factory.create(null, A_NAME, AN_EMAIL);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenEmptyName_whenCreate_thenThrowsException() {
        Executable creation = () -> factory.create(AN_IDUL, "", AN_EMAIL);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenNullEmail_whenCreate_thenThrowsException() {
        Executable creation = () -> factory.create(AN_IDUL, A_NAME, null);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenNullCart_whenCreate_thenThrowsException() {
        Executable creation = () -> factory.create(AN_IDUL, A_NAME, AN_EMAIL, null);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }
}
