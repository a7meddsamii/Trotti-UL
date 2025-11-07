package ca.ulaval.glo4003.trotti.order.domain.entities.pass;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.fixtures.PassFixture;
import ca.ulaval.glo4003.trotti.payment.domain.entities.invoice.Invoice;
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
