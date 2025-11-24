package ca.ulaval.glo4003.trotti.commons.domain.events.account;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import java.util.Collections;
import java.util.List;

public class ApplyAdvantageRequestEvent extends Event {

    private final String advantage;
    private final List<Idul> eligibleUserIds;

    public ApplyAdvantageRequestEvent(String advantage, List<Idul> eligibleUserIds) {
        super(null, "account.apply.advantage.request");
        this.advantage = advantage;
        this.eligibleUserIds = eligibleUserIds;
    }

    public String getAdvantage() {
        return advantage;
    }

    public List<Idul> getEligibleUserIds() {
        return Collections.unmodifiableList(eligibleUserIds);
    }
}
