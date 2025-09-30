package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.fixtures.BuyerFixture;
import ca.ulaval.glo4003.trotti.fixtures.CreditCardFixture;
import ca.ulaval.glo4003.trotti.fixtures.OrderFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderTest {
    @Test
    void givenOrder_whenGenerateInvoice_thenContainsCompleteInvoice() {
        Order order = new OrderFixture().build();
        Buyer buyer = new BuyerFixture().buildWithPaymentMethod();
        CreditCard creditCard = new CreditCardFixture().build();

        Invoice invoice = order.generateInvoice(creditCard.generateInvoice());
        String rendered = invoice.render(buyer.getEmail(), buyer.getName());

        Assertions.assertTrue(rendered.contains(buyer.getName()));
        Assertions.assertTrue(rendered.contains(buyer.getEmail().toString()));
        Assertions.assertTrue(rendered.contains(order.getIdul().toString()));
        Assertions.assertTrue(rendered.contains(order.getId().toString()));
        order.getPassList().forEach(pass -> {
            Assertions.assertTrue(rendered.contains(pass.getId().toString()));
            Assertions.assertTrue(rendered.contains(pass.getBillingFrequency().toString()));
            Assertions.assertTrue(rendered.contains(pass.getSession().toString()));
            Assertions.assertTrue(rendered.contains(pass.calculateAmount().toString()));
        });
        Assertions.assertTrue(rendered.contains(creditCard.generateInvoice()));
    }
}
