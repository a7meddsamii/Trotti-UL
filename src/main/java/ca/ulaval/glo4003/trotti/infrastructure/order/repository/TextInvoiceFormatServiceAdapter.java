package ca.ulaval.glo4003.trotti.infrastructure.order.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.*;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.domain.payment.services.InvoiceFormatService;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class TextInvoiceFormatServiceAdapter implements InvoiceFormatService<String> {

    @Override
    public String format(Invoice invoice) {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice ID: ").append(invoice.getId()).append("\n");
        sb.append("Order ID: ").append(invoice.getContextId()).append("\n");
        sb.append("Buyer: ").append(invoice.getBuyerIdul()).append("\n");
        sb.append("Issue Date: ").append(invoice.getIssueDate()).append("\n\n");

        sb.append("Items:\n");
        for (InvoiceLine line : invoice.getLines()) {
            sb.append(line.getDescription()).append("\n");
            sb.append("\n");
        }

        sb.append("Total: ").append(invoice.getTotalAmount()).append("\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        Pass pass = new Pass(MaximumDailyTravelTime.from(Duration.ofMinutes(60)),
                new Session(Semester.FALL, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 12, 31)),
                BillingFrequency.MONTHLY);
        Pass pass2 = new Pass(MaximumDailyTravelTime.from(Duration.ofMinutes(50)),
                new Session(Semester.FALL, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 12, 31)),
                BillingFrequency.MONTHLY);
        Pass pass3 = new Pass(MaximumDailyTravelTime.from(Duration.ofMinutes(40)),
                new Session(Semester.FALL, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 12, 31)),
                BillingFrequency.MONTHLY);

        Order order = new Order(Idul.from("AHSAM2"), List.of(pass, pass2, pass3));

        InvoiceFormatService<String> formatService = new TextInvoiceFormatServiceAdapter();

        System.out.println(formatService.format(order.generateInvoice()));
    }
}
