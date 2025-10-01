package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final Idul idul;
    private final List<Pass> passList;
    private final Id id;

    public Order(Idul idul, List<Pass> passList, Id id) {
        this.idul = idul;
        this.passList = passList;
        this.id = id;
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

    public Invoice generateInvoice() {
        List<String> passInvoiceList = new ArrayList<>();
        for (Pass pass : passList) {
            passInvoiceList.add(pass.generateInvoice());
        }
        // TODO: fill other fields
        return new Invoice("", passInvoiceList, "");
    }
}
