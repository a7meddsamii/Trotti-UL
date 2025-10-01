package ca.ulaval.glo4003.trotti.fixtures;

import static ca.ulaval.glo4003.trotti.fixtures.AccountFixture.AN_IDUL;
import static ca.ulaval.glo4003.trotti.fixtures.PassFixture.AN_ID;

import ca.ulaval.glo4003.trotti.domain.order.Order;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import java.util.ArrayList;
import java.util.List;

public class OrderFixture {
    private static final List<Pass> A_PASS_LIST = new ArrayList<>();

    public Order build() {
        A_PASS_LIST.add(new PassFixture().build());
        return new Order(AN_IDUL, A_PASS_LIST, AN_ID);
    }
}
