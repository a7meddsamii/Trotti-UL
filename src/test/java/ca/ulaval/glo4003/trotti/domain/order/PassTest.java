package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.fixtures.PassFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PassTest {
    @Test
    void givenPass_whenCalculateAmount_thenReturnMoney() {
        Pass pass = new PassFixture().build();

        Assertions.assertNotNull(pass.calculateAmount());
    }

    @Test
    void givenPass_whenGenerateInvoice_thenContainsIdAndBillingInfo() {
        Pass pass = new PassFixture().build();

        String invoice = pass.generateInvoice();

        Assertions.assertTrue(invoice.contains(pass.getId().toString()));
        Assertions.assertTrue(invoice.contains(pass.getBillingFrequency().toString()));
        Assertions.assertTrue(invoice.contains(pass.getSession().toString()));
        Assertions.assertTrue(invoice.contains(pass.calculateAmount().toString()));
    }
}
