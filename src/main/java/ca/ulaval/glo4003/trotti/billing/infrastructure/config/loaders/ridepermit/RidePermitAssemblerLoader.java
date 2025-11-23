package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.ridepermit;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitAssembler;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class RidePermitAssemblerLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadRidePermitAssembler();
	}
	
	private void loadRidePermitAssembler() {
		RidePermitAssembler ridePermitAssembler = new RidePermitAssembler();
		this.resourceLocator.register(RidePermitAssembler.class, ridePermitAssembler);
	}
}
