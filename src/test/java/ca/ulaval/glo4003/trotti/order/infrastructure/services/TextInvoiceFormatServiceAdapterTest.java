package ca.ulaval.glo4003.trotti.order.infrastructure.services;

import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.order.domain.values.OrderId;
import ca.ulaval.glo4003.trotti.payment.domain.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.payment.domain.entities.invoice.InvoiceLine;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextInvoiceFormatServiceAdapterTest {

    private static final Idul A_BUYER_IDUL = Idul.from("johnDoe99");

    private InvoiceFormatService<String> invoiceFormatService;

    @BeforeEach
    void setup() {
        invoiceFormatService = new TextInvoiceFormatServiceAdapter();
    }

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

        Assertions.assertThat(result).contains(invoice.getId().toString())
                .contains(invoice.getContextId().toString())
                .contains(invoice.getBuyerIdul().toString())
                .contains(invoice.getIssueDate().toString()).contains(line.getDescription())
                .contains(invoice.getTotalAmount().toString());
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

        Assertions.assertThat(result).contains(invoice.getTotalAmount().toString());
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
        return Invoice.builder().id(OrderId.randomId()).buyer(A_BUYER_IDUL)
                .totalAmount(Money.zeroCad()).lines(lines.isEmpty() ? null : lines).build();
    }
}
