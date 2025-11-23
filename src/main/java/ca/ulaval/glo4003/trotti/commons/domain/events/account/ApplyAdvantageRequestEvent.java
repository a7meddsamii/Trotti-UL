package ca.ulaval.glo4003.trotti.commons.domain.events.account;

import ca.ulaval.glo4003.trotti.account.domain.values.Advantage;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

import java.util.Collections;
import java.util.List;

public class ApplyAdvantageRequestEvent extends Event {
	
	private final Advantage advantage;
	private final List<Idul> applicableIduls;
	
	public ApplyAdvantageRequestEvent(Advantage advantage, List<Idul> applicableIduls) {
		super(null, "account.apply.advantage.request");
		this.advantage = advantage;
		this.applicableIduls = applicableIduls;
	}
	
	public Advantage getAdvantage() {
		return advantage;
	}
	
	public List<Idul> getApplicableIduls() {
		return Collections.unmodifiableList(applicableIduls);
	}
}
