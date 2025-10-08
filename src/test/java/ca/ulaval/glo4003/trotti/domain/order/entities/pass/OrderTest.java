package ca.ulaval.glo4003.trotti.domain.order.entities.pass;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.fixtures.PassFixture;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {

    private Idul idul;
    private List<Pass> passList;

    private Order order;

    @BeforeEach
    void setUp() {
        idul = Idul.from("A1234567");
        passList = List.of(new PassFixture().build(), new PassFixture().build());
        order = new Order(idul, passList);
    }

    @Test
    void whenGeneratingInvoice_thenInvoiceIsCorrectlyGenerated() {
        Invoice invoice = order.generateInvoice();

        Assertions.assertEquals(order.getId(), invoice.getContextId());
        Assertions.assertEquals(order.getIdul(), invoice.getBuyerIdul());
        Assertions.assertEquals(order.getPassList().size(), invoice.getLines().size());
        Assertions.assertEquals(order.calculateTotalAmount(), invoice.getTotalAmount());
    }

}
