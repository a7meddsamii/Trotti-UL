package ca.ulaval.glo4003.trotti.domain.order.factories;

import static ca.ulaval.glo4003.trotti.fixtures.AccountFixture.AN_IDUL;

import ca.ulaval.glo4003.trotti.fixtures.PassFixture;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Order;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.exceptions.InvalidOrderException;
import ca.ulaval.glo4003.trotti.order.domain.factories.OrderFactory;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class OrderFactoryTest {
    private final OrderFactory factory = new OrderFactory();

    @Test
    void givenValidParams_whenCreate_thenReturnOrder() {
        List<Pass> aPassList = List.of(new PassFixture().build());

        Order order = factory.create(AN_IDUL, aPassList);

        Assertions.assertEquals(AN_IDUL, order.getIdul());
        Assertions.assertEquals(aPassList, order.getPassList());
        Assertions.assertNotNull(order.getId());
    }

    @Test
    void givenNullIdul_whenCreate_thenThrowsException() {
        Executable creation = () -> factory.create(null, List.of(new PassFixture().build()));

        Assertions.assertThrows(InvalidOrderException.class, creation);
    }

    @Test
    void givenEmptyPassList_whenCreate_thenThrowsException() {
        Executable creation = () -> factory.create(AN_IDUL, List.of());

        Assertions.assertThrows(InvalidOrderException.class, creation);
    }
}
