package ca.ulaval.glo4003.trotti.domain.communication.strategies;

import static ca.ulaval.glo4003.trotti.fixtures.AccountFixture.AN_EMAIL;
import static ca.ulaval.glo4003.trotti.fixtures.AccountFixture.A_NAME;

import ca.ulaval.glo4003.trotti.domain.order.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.Order;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.fixtures.CreditCardFixture;
import ca.ulaval.glo4003.trotti.fixtures.OrderFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderInvoiceEmailStrategyTest {

    @Test
    void givenInvoice_whenGetMethods_thenReturnCorrectValues() {
        Order order = new OrderFixture().build();
        Invoice invoice = order.generateInvoice();

        OrderInvoiceEmailStrategy strategy =
                new OrderInvoiceEmailStrategy(AN_EMAIL, A_NAME, invoice);

        Assertions.assertEquals(AN_EMAIL, strategy.getRecipient());
        Assertions.assertEquals("Invoice for Trotti-ul", strategy.getSubject());
        Assertions.assertEquals(invoice.render(AN_EMAIL, A_NAME), strategy.getBody());
    }
}
