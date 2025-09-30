package ca.ulaval.glo4003.trotti.domain.order;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.trotti.fixtures.PassFixture;
import org.junit.jupiter.api.Test;

class PassTest {
    @Test
    void givenPass_whenCalculateAmount_thenReturnMoney() {
        Pass pass = new PassFixture().build();

        assertNotNull(pass.calculateAmount());
    }

    @Test
    void givenPass_whenGenerateInvoice_thenContainsIdAndBillingInfo() {
        Pass pass = new PassFixture().build();

        String invoice = pass.generateInvoice();

        assertTrue(invoice.contains(pass.getId().toString()));
        assertTrue(invoice.contains(pass.getBillingFrequency().toString()));
        assertTrue(invoice.contains(pass.getSession().toString()));
        assertTrue(invoice.contains(pass.calculateAmount().toString()));
    }
}
