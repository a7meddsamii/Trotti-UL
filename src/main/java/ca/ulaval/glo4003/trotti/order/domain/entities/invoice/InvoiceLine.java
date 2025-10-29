package ca.ulaval.glo4003.trotti.order.domain.entities.invoice;

public final class InvoiceLine {
    private final String description;

    private InvoiceLine(String description) {
        this.description = description;
    }

    public static InvoiceLine from(String line) {
        return new InvoiceLine(line);
    }

    public String getDescription() {
        return description;
    }
}
