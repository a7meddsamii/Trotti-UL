package ca.ulaval.glo4003.trotti.domain.order;

import java.util.Map;

public final class InvoiceLine {
    private final Map<String, String> meta;

    private InvoiceLine(Map<String, String> meta) {
        this.meta = meta == null ? Map.of() : Map.copyOf(meta);
    }

    public static InvoiceLine from(Map<String,String> meta) {
        return new InvoiceLine(meta);
    }

    public static InvoiceLine from(String line) {
        return new InvoiceLine(Map.of("Description:", line));
    }

    public Map<String, String> meta() { return meta; }
}