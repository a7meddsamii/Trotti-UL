package ca.ulaval.glo4003.trotti.domain.order;

import static ca.ulaval.glo4003.trotti.fixtures.AccountFixture.AN_IDUL;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.fixtures.PassFixture;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class OrderFactoryTest {
    private final OrderFactory factory = new OrderFactory();

    @Test
    void givenValidParams_whenCreate_thenReturnOrder() {
        List<Pass> passList = List.of(new PassFixture().build());

        Order order = factory.create(AN_IDUL, passList);

        Assertions.assertEquals(AN_IDUL, order.getIdul());
        Assertions.assertEquals(passList, order.getPassList());
        Assertions.assertNotNull(order.getId());
    }

    @Test
    void givenNullIdul_whenCreate_thenThrowsException() {
        Executable creation = () -> factory.create(null, List.of(new PassFixture().build()));

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenEmptyPassList_whenCreate_thenThrowsException() {
        Executable creation = () -> factory.create(AN_IDUL, List.of());

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }
}
