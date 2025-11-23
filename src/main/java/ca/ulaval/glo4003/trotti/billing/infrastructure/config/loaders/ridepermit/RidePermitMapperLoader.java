package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.ridepermit;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.mapper.RidePermitApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitAssembler;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class RidePermitMapperLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadRidePermitAssembler();
        loadRidePermitMapper();
	}
	
	private void loadRidePermitAssembler() {
		RidePermitAssembler ridePermitAssembler = new RidePermitAssembler();
		this.resourceLocator.register(RidePermitAssembler.class, ridePermitAssembler);
	}

    private void loadRidePermitMapper() {
        RidePermitApiMapper ridePermitApiMapper = new RidePermitApiMapper();
        this.resourceLocator.register(RidePermitApiMapper.class, ridePermitApiMapper);
    }

}
