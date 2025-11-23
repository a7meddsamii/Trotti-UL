package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.ridepermit;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.controller.RidePermitController;
import ca.ulaval.glo4003.trotti.billing.api.ridepermit.controller.RidePermitResource;
import ca.ulaval.glo4003.trotti.billing.api.ridepermit.mapper.RidePermitMapper;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class RidePermitResourceLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadRidePermitResource();
	}
	
	private void loadRidePermitResource() {
        RidePermitApplicationService ridePermitApplicationService = this.resourceLocator.resolve(RidePermitApplicationService.class);
        RidePermitMapper ridePermitMapper = this.resourceLocator.resolve(RidePermitMapper.class);
        RidePermitResource ridePermitResource = new RidePermitController(ridePermitApplicationService, ridePermitMapper);
        this.resourceLocator.register(RidePermitResource.class, ridePermitResource);
	}
}
