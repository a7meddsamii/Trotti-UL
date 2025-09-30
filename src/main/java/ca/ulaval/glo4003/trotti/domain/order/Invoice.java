package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import java.util.List;

public class Invoice {
    private final String orderInvoice;
    private final List<String> passInvoiceList;
    private final String paymentMethodInvoice;

    public Invoice(String orderInvoice, List<String> passInvoiceList, String paymentMethodInvoice) {
        this.orderInvoice = orderInvoice;
        this.paymentMethodInvoice = paymentMethodInvoice;
        this.passInvoiceList = passInvoiceList;
    }

    public String toString(Email buyerEmail, String buyerName) {
        StringBuilder invoice =
                new StringBuilder("Name : " + buyerName + "\n" + "Email : " + buyerEmail + "\n");
        invoice.append(orderInvoice);
        for (String passInvoice : passInvoiceList) {
            invoice.append(passInvoice);
        }
        invoice.append(paymentMethodInvoice);

        return invoice.toString();
    }
}
