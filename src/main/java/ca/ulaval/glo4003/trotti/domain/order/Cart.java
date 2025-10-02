package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Pass> passList;

    public Cart() {
        this.passList = new ArrayList<>();
    }

    public Cart(List<Pass> passList) {
        this.passList = new ArrayList<>(passList);
    }

    public List<Pass> getPasses() {
        return List.copyOf(passList);
    }

    public boolean add(Pass pass) {
        return passList.add(pass);
    }

    public boolean remove(Id id) {
        return passList.removeIf(pass -> pass.getId().equals(id));
    }

    public void clear() {
        passList.clear();
    }

    public Money calculateAmount() {
        Money total = Money.zeroCad();
        for (Pass pass : passList) {
            total = total.plus(pass.calculateAmount());
        }

        return total;
    }
}
