package ca.ulaval.glo4003.trotti.domain.pass;

import ca.ulaval.glo4003.trotti.domain.account.Idul;
import java.util.List;

public class Order {
    private final Idul idul;
    private final List<Pass> passList;

    public Order(Idul idul, List<Pass> passList) {
        this.idul = idul;
        this.passList = passList;
    }

    public Idul getIdul() {
        return idul;
    }

    public List<Pass> getPassList() {
        return passList;
    }
}
