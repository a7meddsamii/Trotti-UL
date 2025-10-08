package ca.ulaval.glo4003.trotti.domain.order.entities.pass;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.domain.order.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.entities.invoice.InvoiceLine;
import ca.ulaval.glo4003.trotti.domain.order.entities.invoice.Invoiceable;
import java.util.List;

public class Order implements Invoiceable {
    private final Idul idul;
    private final List<Pass> passList;
    private final Id id;

    public Order(Idul idul, List<Pass> passList) {
        this.idul = idul;
        this.passList = passList;
        this.id = Id.randomId();
    }

    public Idul getIdul() {
        return idul;
    }

    public List<Pass> getPassList() {
        return passList;
    }

    public Id getId() {
        return id;
    }

    public Money calculateTotalAmount() {
        Money totalAmount = Money.zeroCad();

        for (Pass pass : passList) {
            totalAmount = totalAmount.plus(pass.calculateAmount());
        }

        return totalAmount;
    }

    @Override
    public Invoice generateInvoice() {
        Invoice.Builder invoiceBuilder = Invoice.builder().id(id).buyer(idul);

        for (Pass pass : passList) {
            invoiceBuilder = invoiceBuilder.line(InvoiceLine.from(pass.toString()));
        }

        return invoiceBuilder.totalAmount(calculateTotalAmount()).build();
    }
}
