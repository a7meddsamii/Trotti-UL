package ca.ulaval.glo4003.trotti.infrastructure.payment.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.entities.invoice.InvoiceLine;
import ca.ulaval.glo4003.trotti.domain.order.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.domain.payment.values.money.Money;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TextInvoiceFormatServiceAdapterTest {

    private static final Idul A_BUYER_IDUL = Idul.from("johnDoe99");

    private InvoiceFormatService<String> invoiceFormatService =
            new TextInvoiceFormatServiceAdapter();

    @Test
    void whenFormat_thenReturnTextInvoice() {
        Invoice invoice = Invoice.builder().build();

        String formattedInvoice = invoiceFormatService.format(invoice);

        org.junit.jupiter.api.Assertions.assertNotNull(formattedInvoice);
    }

    @Test
    void givenInvoiceWithSingleLine_whenFormat_thenIncludesAllFields() {
        InvoiceLine line = InvoiceLine.from("Pass 1 - 30 minutes");
        Invoice invoice = buildInvoiceWithLines(List.of(line));

        String result = invoiceFormatService.format(invoice);

        Assertions.assertThat(result).contains("Invoice ID: " + invoice.getId())
                .contains("Order ID: " + invoice.getContextId())
                .contains("Buyer: " + invoice.getBuyerIdul())
                .contains("Issue Date: " + invoice.getIssueDate()).contains("Items:")
                .contains(line.getDescription()).contains("Total: " + invoice.getTotalAmount());
    }

    @Test
    void givenInvoiceWithMultipleLines_whenFormat_thenAllLinesAreIncluded() {
        InvoiceLine line1 = InvoiceLine.from("Pass A - 10 trips");
        InvoiceLine line2 = InvoiceLine.from("Pass B - 30 minutes");
        Invoice invoice = buildInvoiceWithLines(List.of(line1, line2));

        String result = invoiceFormatService.format(invoice);

        Assertions.assertThat(result).contains(line1.getDescription())
                .contains(line2.getDescription());
    }

    @Test
    void givenInvoiceWithoutLines_whenFormat_thenItemsSectionStillPresent() {
        Invoice invoice = buildInvoiceWithLines(List.of());

        String result = invoiceFormatService.format(invoice);

        Assertions.assertThat(result).contains("Items:")
                .contains("Total: " + invoice.getTotalAmount());
    }

    @Test
    void givenInvoice_whenFormat_thenOutputIsHumanReadable() {
        InvoiceLine line = InvoiceLine.from("Test Ride Pass");
        Invoice invoice = buildInvoiceWithLines(List.of(line));

        String result = invoiceFormatService.format(invoice);

        Assertions.assertThat(result).startsWith("Invoice ID:");
        Assertions.assertThat(result).containsPattern("Issue Date: \\d{4}-\\d{2}-\\d{2}");
        Assertions.assertThat(result).contains("Items:");
        Assertions.assertThat(result).endsWith("\n");
    }

    private Invoice buildInvoiceWithLines(List<InvoiceLine> lines) {
        return Invoice.builder().id(Id.randomId()).buyer(A_BUYER_IDUL).totalAmount(Money.zeroCad())
                .lines(lines.isEmpty() ? null : lines).build();
    }
}
