package ca.ulaval.glo4003.trotti.order.domain.entities.buyer;

import ca.ulaval.glo4003.trotti.commons.domain.values.ids.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.values.PassId;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Pass> passList;

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

    public boolean remove(PassId id) {
        return passList.removeIf(pass -> pass.getId().equals(id));
    }

    public void clear() {
        passList.clear();
    }

    public List<Pass> linkPassesToBuyer(Idul idul) {
        List<Pass> linkedPasses = new ArrayList<>();
        passList.forEach(pass -> linkedPasses.add(pass.linkToBuyer(idul)));
        return List.copyOf(linkedPasses);
    }

    public Money calculateAmount() {
        Money total = Money.zeroCad();
        for (Pass pass : passList) {
            total = total.plus(pass.calculateAmount());
        }

        return total;
    }

    public boolean isEmpty() {
        return passList.isEmpty();
    }
}
